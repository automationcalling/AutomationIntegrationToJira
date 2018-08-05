package com.automationcalling.testsuites.jirautomation;
import com.automationcalling.utils.jira.JiraUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.automationcalling.utils.common.Constant.APPURL;

public class GoogleHomePage_Test {

    private WebDriver driver;
    private JiraUtil jiraUtil;

    @BeforeTest
    public void init() {
        try {
            jiraUtil = new JiraUtil();
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get(APPURL);
            Assert.assertNotNull(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getGoogleTitle() {
        try {
            Assert.assertTrue(driver.getTitle().equalsIgnoreCase("Googles"));
        } catch (AssertionError e) {
            System.out.println(jiraUtil.createBug("TES", this.getClass().getSimpleName() + "Functionality Broken_"
                    + java.time.LocalDate.now()+"_"+java.time.LocalTime.now(), e.getMessage()));
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
