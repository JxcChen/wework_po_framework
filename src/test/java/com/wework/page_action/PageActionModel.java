package com.wework.page_action;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.wework.page.PageObjectModel;
import com.wework.utils.GetCookie;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PlaceholderUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: PageActionModel
 * date: 2021/1/12 17:47
 * @author JJJJ
 * Description: 页面中各方法执行
 *
 * 1 yaml文件步骤属性进行反序列化
 * 2 声明run方法执行具体action
 *     2.1 对driver进行初始化
 *     2.2 执行具体的action,对具体operation进行判断执行
 *     2.3 对获取的结果做相应保存
 */
public class PageActionModel extends BasePageAction {


    private String driver;
    private int implicitlyWait;
    private String get;
    private String maximize;
    private String setCookies;
    private ArrayList<String> returnPage;
    private ArrayList<HashMap<String, String>> returnResults;
    private String quit;
    private ArrayList<HashMap<String, Object>> operations;



    public void run(HashMap<String,String> actual) {
        // 初始化驱动
        if (driver != null) {
            if (driver.equals("chrome")) {
                webDriver = new ChromeDriver();
            } else if (driver.equals("fireFox")) {
                webDriver = new FirefoxDriver();
            }
        }
        // 设置隐形等待
        if (implicitlyWait != 0) {
            webDriver.manage().timeouts().implicitlyWait(implicitlyWait, TimeUnit.SECONDS);
        }

        // 企业微信需要先获取cookie
        if (setCookies != null) {
            File file = new File(setCookies);
            if (!file.exists()) {
                // 获取cookies
                try {
                    GetCookie.getWeiXinCookie(webDriver);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                // 打开网页
                if (get != null) {
                    webDriver.get(get);
                }
                // 网页最大化
                if (maximize != null) {
                    webDriver.manage().window().maximize();
                }
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                TypeReference typeReference = new TypeReference<List<HashMap<String, Object>>>() {
                };

                List<HashMap<String, Object>> cookies = (List<HashMap<String, Object>>) mapper.readValue(file, typeReference);

                cookies.forEach(cookieMap -> {
                    webDriver.manage().addCookie(new Cookie(cookieMap.get("name").toString(), cookieMap.get("value").toString()));
                });

                // 3、刷新页面
                webDriver.navigate().refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 具体操作
        if (operations != null && operations.size() > 0) {
            operations.forEach(operation -> {
                if (operation.containsKey("find")) {
                    HashMap<String, String> find = (HashMap<String, String>) operation.get("find");
                    if (find.containsKey("id")){
                        locator = By.id(find.get("id"));
                    }else if (find.containsKey("xpath")){
                        locator = By.xpath(find.get("xpath"));
                    }

                    setLocator(find);
                    currentElement = webDriver.findElement(locator);
                }
                if (operation.containsKey("click")) {
                    clickElement(locator);
                }
                if (operation.containsKey("send_key")) {
                    // 进行对占位符的参数化
                    String sendKey = PlaceholderUtils.resolveString(operation.get("send_key").toString(),actual);
                    sendKey(locator, sendKey);
                }
            });
        }
        // 返回页面
        if (returnPage != null && returnPage.size() > 0) {

            try {
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                PageObjectModel page = mapper.readValue(new File(returnPage.get(1)), PageObjectModel.class);
//                page.getActions().get()
                PageObjectModel.getInstance().addPage(returnPage.get(0), page);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 获取实际值进行断言
        if (returnResults != null) {
            returnResults.forEach(result -> {
                setLocator(result);
                if (result.containsKey("attribute")) {
                    PageObjectModel.getInstance().setActualResult(getElementAttribute(locator, result.get("attribute")));
                }
            });
        }
        if (quit != null) {
            webDriver.quit();
        }
    }

    /**
     * 判断定位方式并设置定位符
     * @param locatorMap 定位符集合
     */
    private void setLocator(HashMap<String, String> locatorMap) {
        if (locatorMap.containsKey("id")) {
            locator = By.id(locatorMap.get("id"));
        } else if (locatorMap.containsKey("name")) {
            locator = By.name(locatorMap.get("name"));
        } else if (locatorMap.containsKey("xpath")) {
            locator = By.xpath(locatorMap.get("xpath"));
        }
    }





    /**
     * 获取元素属性值
     * @param locator       元素定位表达是
     * @param attributeName 元素的属性名
     */
    public String getElementAttribute(By locator, String attributeName) {
        String value = findElementVisibility(locator).getAttribute(attributeName);
        return value;
    }


    public ArrayList<HashMap<String, Object>> getOperations() {
        return operations;
    }

    public void setOperations(ArrayList<HashMap<String, Object>> operations) {
        this.operations = operations;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public int getImplicitlyWait() {
        return implicitlyWait;
    }

    public void setImplicitlyWait(int implicitlyWait) {
        this.implicitlyWait = implicitlyWait;
    }

    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    public String getMaximize() {
        return maximize;
    }

    public void setMaximize(String maximize) {
        this.maximize = maximize;
    }

    public String getSetCookies() {
        return setCookies;
    }

    public void setSetCookies(String setCookies) {
        this.setCookies = setCookies;
    }

    public ArrayList<String> getReturnPage() {
        return returnPage;
    }

    public void setReturnPage(ArrayList<String> returnPage) {
        this.returnPage = returnPage;
    }

    public String getQuit() {
        return quit;
    }

    public void setQuit(String quit) {
        this.quit = quit;
    }
}
