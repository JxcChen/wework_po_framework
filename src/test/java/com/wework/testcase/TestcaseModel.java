package com.wework.testcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wework.step.TestStepModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: TestcaseModel
 * date: 2021/1/26 9:30
 *
 * @author JJJJ
 * Description:
 */
public class TestcaseModel {

    public HashMap<String, TestStepModel> testcaseModel;


    /**
     * 对yaml进行反序列化
     * @param path yaml文件路径
     * @return TestCaseModel
     * @throws IOException
     */
    public static TestcaseModel load(String path){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TestcaseModel testCaseModel = null;
        try {
            testCaseModel = mapper.readValue(TestcaseModel.class.getResourceAsStream(path), TestcaseModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testCaseModel;
    }

    /**
     * 调用具体的测试步骤进行测试并进行断言
     * @param stepName 要执行的用例名
     * @return
     */
    public ArrayList<TestStepModel> run(String stepName){

        if (stepName.equals("init") || stepName.equals("quit")){
            testcaseModel.get(stepName).run();
            return null;
        }

        ArrayList<TestStepModel> testStepModels = testcaseModel.get(stepName).testcaseGenerate();
        return testStepModels;
    }
}
