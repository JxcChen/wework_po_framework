package com.wework.page;

import com.wework.page_action.PageActionModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * ClassName: PageObjectModel
 * date: 2021/1/12 17:45
 * @author JJJJ
 * Description:
 */
public class PageObjectModel {
    static Logger logger = LoggerFactory.getLogger(PageObjectModel.class);

    private String name;
    private HashMap<String, PageActionModel> actions;

    public static PageObjectModel instance;
    public static HashMap<String,PageObjectModel> page = new HashMap<>();
    public static String actualResult;

    public static PageObjectModel getInstance(){
        if (instance == null){
            instance = new PageObjectModel();
        }
        return instance;
    }

    public void addPage(String pageName,PageObjectModel pageObjectModel){
        page.put(pageName,pageObjectModel);
        logger.info("新增页面："+pageName);
    }

    private PageObjectModel getPage(String pageName){
        PageObjectModel pageObject = page.get(pageName);
        return pageObject;
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
