package com.wework.test;

import com.wework.testcase.TestcaseModel;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * ClassName: TestcaseModelTest
 * date: 2021/1/26 20:24
 * @author JJJJ
 * Description:
 */
public class TestcaseModelTest {


    @Test
    public void test() throws IOException {
        TestcaseModel model = TestcaseModel.load("/test/contact_page_test");
        model.run("init");
        model.run("addMember");
    }




}
