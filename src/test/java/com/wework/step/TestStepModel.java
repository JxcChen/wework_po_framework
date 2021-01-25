package com.wework.step;

import com.wework.page.PageObjectModel;
import utils.PlaceholderUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: TestStepModel
 * date: 2021/1/21 13:29
 * @author JJJJ
 * Description:执行测试步骤方法
 */
public class TestStepModel {


    public List<HashMap<String, String>> assertExpected;
    public int index = 0;

    private List<HashMap<String, Object>> data;
    private ArrayList<HashMap<String,Object>> steps;

    public void run(){
        steps.forEach(step -> {
            String key = step.keySet().iterator().next();
            if (key.equals("init")){
                // 进行初始化
                // 对存在的占位符进行参数化
                ArrayList<String> init = (ArrayList<String>) step.get(key);
                try {
                    PageObjectModel.getInstance().initPO(init.get(0),init.get(1));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (key.contains(".")){
                // 进行方法调用
                // 对可能存在的占位符进行参数化
                HashMap<String,String> actualParam = new HashMap<>();
                if (step.get(key).toString().contains("$") && data != null && data.size()>0 && data.get(0) != null){
                    ArrayList<String> valueList = (ArrayList<String>) PlaceholderUtils.resolveStringToObject((String) step.get(key), data.get(0));
                    if (valueList != null && valueList.size() >0){
                        for (int i = 0; i < valueList.size(); i++) {
                            actualParam.put("param"+i,valueList.get(i));
                        }
                    }
                }

                String[] pageMethod = key.split("\\.");
                String pageName = pageMethod[0];
                String methodName = pageMethod[1];
                PageObjectModel.getInstance().getPage(pageName).runActions(pageName,methodName,actualParam);
            }
        });
    }


    /**
     * 将yaml里的数据进行参数化替换
     *
     * @param step       执行的步骤
     * @param methodName 需要进行参数化的方法名
     * @return 进行参数化替换后的数据
     */
    public Object getValue(HashMap<String, Object> step, String methodName,List<HashMap<String, Object>> data) {
        Object value = step.get(methodName);
        ArrayList<String> dataList = new ArrayList<>();
        // 如果是字符串则说明可能存在 需要替换的参数
        // 如果是列表则说明可能有多个参数需要替换
        if (value instanceof String) {
            String strValue = (String) value;
            if (strValue.contains("$")){
                dataList = (ArrayList<String>) PlaceholderUtils.resolveStringToObject(strValue, data.get(0));
            }

        }
            return dataList;
        }



    public ArrayList<HashMap<String, Object>> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<HashMap<String, Object>> steps) {
        this.steps = steps;
    }


    public List<HashMap<String, Object>> getData() {
        return data;
    }

    public void setData(List<HashMap<String, Object>> data) {
        this.data = data;
    }
}
