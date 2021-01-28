package com.wework.test;

import com.wework.step.TestStepModel;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.ObjectHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ClassName: StepsTest
 * date: 2021/1/21 14:37
 *
 * @author JJJJ
 * Description:
 */
public class StepsTest {




    @Test
    public void test(){
        TestStepModel stepModel = new TestStepModel();
        ArrayList<HashMap<String,Object>> steps = new ArrayList<>();
        HashMap<String, Object> init1 = new HashMap<>();
        ArrayList<String> value = new ArrayList<>();
        value.add("mainPage");
        value.add("src/test/resources/po/main_page.yaml");
        init1.put("init",value);
        HashMap<String, Object> init2 = new HashMap<>();
        init2.put("indexPage.getContactPage","");
        HashMap<String, Object> addMember = new HashMap<>();
        addMember.put("contactPage.addMember","${addMember}");
        steps.add(init1);
        steps.add(init2);
        steps.add(addMember);
        List<HashMap<String, Object>> data = new ArrayList<>();
        HashMap<String,Object> d = new HashMap<>();
        ArrayList<String> dValue = new ArrayList<>();
        dValue.add("小司马");
        dValue.add("11");
        d.put("addMember",dValue);
        data.add(d);

        stepModel.setData(data);
        stepModel.setSteps(steps);
        stepModel.run();
    }
}
