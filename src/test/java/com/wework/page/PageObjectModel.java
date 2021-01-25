package com.wework.page;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wework.page_action.PageActionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * ClassName: PageObjectModel
 * date: 2021/1/12 17:45
 * @author JJJJ
 * Description: 初始化PO 储存操作后的响应信息
 */
public class PageObjectModel {
    static Logger logger = LoggerFactory.getLogger(PageObjectModel.class);

    private String name;
    private HashMap<String, PageActionModel> actions;

    public static PageObjectModel instance;
    public static HashMap<String,PageObjectModel> page = new HashMap<>();
    // 储存返回的实际结果  用于断言
    public static String actualResult;


    /**
     * 获取PageObjectModel实例
     * @return PageObjectModel
     */
    public static PageObjectModel getInstance(){
        if (instance == null){
            instance = new PageObjectModel();
        }
        return instance;
    }

    /**
     * 储存页面
     * @param pageName 页面名
     * @param pageObjectModel 页面
     */
    public void addPage(String pageName,PageObjectModel pageObjectModel){
        page.put(pageName,pageObjectModel);
        logger.info("新增页面："+pageName);
    }

    /**
     * 通过页面名字获取页面
     * @param pageName 页面名
     * @return
     */
    public PageObjectModel getPage(String pageName){
        PageObjectModel pageObject = page.get(pageName);
        return pageObject;
    }

    /**
     * 初始化PO
     * @param pageName 页面名
     * @param path 页面对应的yaml文件路径
     * @throws IOException
     */
    public void initPO(String pageName,String path) throws IOException {
        // 获取PO页面
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        PageObjectModel pageObjectModel = mapper.readValue(new File(path), PageObjectModel.class);
        addPage(pageName,pageObjectModel);
        // todo: 运行PO的init方法进行初始化
        runActions(pageName,"init",null);
    }

    public void runActions(String pageName, String methodName, HashMap<String,String> actualParam) {
        PageObjectModel pageObjectModel = page.get(pageName);
        pageObjectModel.actions.get(methodName).run(actualParam);
    }


    public String getActualResult() {
        return actualResult;
    }

    public void setActualResult(String actualResult) {
        this.actualResult = actualResult;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, PageActionModel> getActions() {
        return actions;
    }

    public void setActions(HashMap<String, PageActionModel> actions) {
        this.actions = actions;
    }
}
