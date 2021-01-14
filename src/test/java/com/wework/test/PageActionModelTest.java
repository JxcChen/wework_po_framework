package com.wework.test;

import com.wework.page_action.PageActionModel;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ClassName: PageActionModelTest
 * date: 2021/1/14 9:33
 * @author JJJJ
 * Description:
 */
public class PageActionModelTest {
    Logger logger = LoggerFactory.getLogger(PageActionModelTest.class);
    @Test
    public void test(){
        PageActionModel pageActionModel = new PageActionModel();

        pageActionModel.setDriver("chrome");

        pageActionModel.setGet("https://work.weixin.qq.com/wework_admin/frame");

        pageActionModel.setImplicitlyWait(15);

        pageActionModel.setMaximize("{}");

        pageActionModel.setSetCookies("cookies.yaml");

        ArrayList<String> returnList = new ArrayList<>();
        returnList.add("indexPage");
        returnList.add("src\\test\\resources\\index_page.yaml");
        pageActionModel.setReturnPage(returnList);


        ArrayList<HashMap<String,Object>> operationList = new ArrayList();
        HashMap<String,Object> operationMap1 = new HashMap<>();
        HashMap<String,Object> operationMap2 = new HashMap<>();
        HashMap<String,String> locator = new HashMap<>();
        locator.put("xpath","//a[@node-type='addmember']");
        operationMap1.put("find",locator);
        operationMap2.put("click","{}");
        operationList.add(operationMap1);
        operationList.add(operationMap2);
        pageActionModel.setOperations(operationList);

        pageActionModel.run();
        logger.info("debug");
    }
}
