package com.wework.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class GetCookie {
    static Logger logger = LoggerFactory.getLogger(GetCookie.class);
    /**
     *获取扫码后的cookie
     */

    public static void getWeiXinCookie(WebDriver driver) throws InterruptedException, IOException {
        // 1、打开企业微信页面
        driver.get("https://work.weixin.qq.com/wework_admin/frame");
        // 2、休眠10秒等待扫码
        Thread.sleep(10000);
        // 3、获取到扫码后页面的cookie
        Set<Cookie> cookies = driver.manage().getCookies();
        // 4、将获取到的cookie以yaml格式存到本地
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(new File("cookies.yaml"),cookies);
        logger.info("获取企业微信cookie，不执行测试");
        driver.close();
        System.exit(0);

    }
}
