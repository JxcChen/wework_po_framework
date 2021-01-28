package com.wework.testcase;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wework.step.TestStepModel;

import java.io.IOException;
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
    public static TestcaseModel load(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TestcaseModel testCaseModel = mapper.readValue(TestcaseModel.class.getResourceAsStream(path), TestcaseModel.class);
        return testCaseModel;
    }

    /**
     * 调用具体的测试步骤进行测试并进行断言
     * @param stepName 要执行的用例名
     */
    public void run(String stepName){
        List<TestStepModel> testStepModels = testcaseModel.get(stepName).testcaseGenerate();
        for (TestStepModel step:testStepModels
             ) {
            step.run();
        }
    }
}
