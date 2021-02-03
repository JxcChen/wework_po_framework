package com.wework.test;

import com.wework.step.TestStepModel;
import com.wework.testcase.TestcaseModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: AddMemberTest
 * date: 2021/2/2 8:58
 *
 * @author JJJJ
 * Description:
 */
public class AddMemberTest {
    static TestcaseModel model = TestcaseModel.load("/test/contact_page_test");

    @BeforeEach
    public void init(){
        model.run("init");
    }

    @ParameterizedTest
    @MethodSource
    public void addMemberTest(TestStepModel testStepModel){
        testStepModel.run();
    }


    public static ArrayList<TestStepModel> addMemberTest(){

        ArrayList<TestStepModel> step = model.run("addMember");
        return step;
    }


}
