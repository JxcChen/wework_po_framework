package com.wework.step;

/**
 * ClassName: AssertModel
 * date: 2021/1/26 12:26
 *
 * @author JJJJ
 * Description:
 */
public class AssertModel {

    private String matcher;
    private String reason;
    private String expected;




    public String getMatcher() {
        return matcher;
    }

    public void setMatcher(String matcher) {
        this.matcher = matcher;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }
}
