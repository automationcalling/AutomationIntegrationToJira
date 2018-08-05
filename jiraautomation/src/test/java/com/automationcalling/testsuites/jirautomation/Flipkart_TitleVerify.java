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
import static com.automationcalling.utils.common.Constant.FLIPKARTURL;
public class Flipkart_TitleVerify {

    /**
     * SubTask No: TES-37
     * Verifying FlipKart Title
     */

    private WebDriver driver;
    private JiraUtil jiraUtil;

    @BeforeTest
    public void init() {
        try {
            jiraUtil = new JiraUtil();
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.get(FLIPKARTURL);
            Assert.assertNotNull(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getGoogleTitle() {
        try {
            Assert.assertTrue(driver.getTitle().contains("Online Shopping"));
        } catch (AssertionError e) {
            /*TransitionID: 111 is default for all Done subTask to Reopen Task
             *This is I verified like this http://localhost:8080/rest/api/2/issue/TES-36/transitions
             */
            jiraUtil.changeJiraWorkFlowStatus("TES-37", "Reopened", "111");
            Assert.fail("Test Execution Failed");
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
