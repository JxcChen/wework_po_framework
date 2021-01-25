package com.wework.test;

import com.wework.page.PageObjectModel;
import com.wework.page_action.PageActionModel;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * ClassName: PageActionModelTest
 * date: 2021/1/14 9:33
 * @author JJJJ
 * Description:
 */
public class PageObjectModelTest {
    Logger logger = LoggerFactory.getLogger(PageObjectModelTest.class);
    @Test
    public void test() throws IOException {


        PageObjectModel instance = PageObjectModel.getInstance();
        instance.initPO("mainPage","src/test/resources/po/main_page.yaml");
        instance.runActions("indexPage","getContactPage",null);
        logger.info("debug");
    }
}
