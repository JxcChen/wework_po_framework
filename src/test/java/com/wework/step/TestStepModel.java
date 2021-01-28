package com.wework.step;

import com.wework.page.PageObjectModel;
import org.junit.jupiter.api.function.Executable;
import utils.PlaceholderUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * ClassName: TestStepModel
 * date: 2021/1/21 13:29
 * @author JJJJ
 * Description:执行测试步骤方法
 */
public class TestStepModel {

    private ArrayList<HashMap<String,Object>> steps;
    private ArrayList<AssertModel> asserts;
    private List<HashMap<String, String>> assertExpected;
    private List<HashMap<String, Object>> data;




    private ArrayList<Executable> assertList = new ArrayList<>();
    public int index = 0;

    /**
     * 将数据根据参数个数进行裂变
     * @return 裂变后的 case集合
     */
    public List<TestStepModel> testcaseGenerate() {
        List<TestStepModel> testCaseList = new ArrayList<>();
        if (data!=null&& data.size()>0){
            for (int i = 0; i < data.size(); i++) {
                TestStepModel testStep = new TestStepModel();
                testStep.data = data;
                testStep.steps = steps;
                testStep.index = i;
                testStep.asserts = asserts;
                testStep.assertExpected = assertExpected;
                testCaseList.add(testStep);
            }
        }else {
            TestStepModel testStep = new TestStepModel();
            testStep.data = data;
            testStep.steps = steps;
            testCaseList.add(testStep);
        }

        return testCaseList;
    }

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
                if (step.get(key).toString().contains("$") && data != null && data.size()>0 && data.get(index) != null){
                    ArrayList<String> valueList = (ArrayList<String>) PlaceholderUtils.resolveStringToObject((String) step.get(key), data.get(index));
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
            if (key.equals("assert")){
                // 将断言封装到assertList中 进行软断言
                int i = (int) step.get(key);
                String expectedValue = PlaceholderUtils.resolveString(asserts.get(i).getExpected(), assertExpected.get(index));
                String reason = asserts.get(i).getReason();
                String matcher = asserts.get(i).getMatcher();
                String actualResult = PageObjectModel.getInstance().getActualResult();
                if (matcher.equals("equalTo")) {
                    assertList.add(() -> {
                        assertThat(reason, actualResult, equalTo(expectedValue));
                    });
                }
                if (matcher.equals("containsString")) {
                    assertList.add(() -> {
                        assertThat(reason, actualResult, containsString(expectedValue));
                    });
                }
                if (matcher.equals("not_containsString")) {
                    assertList.add(() -> {
                        assertThat(reason, actualResult, not(containsString(expectedValue)));
                    });
                }
            }
        });

        assertAll("全局断言",assertList.stream());
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

    public List<HashMap<String, String>> getAssertExpected() {
        return assertExpected;
    }

    public void setAssertExpected(List<HashMap<String, String>> assertExpected) {
        this.assertExpected = assertExpected;
    }

    public ArrayList<AssertModel> getAsserts() {
        return asserts;
    }

    public void setAsserts(ArrayList<AssertModel> asserts) {
        this.asserts = asserts;
    }
}
