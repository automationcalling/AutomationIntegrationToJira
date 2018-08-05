package com.automationcalling.testsuites.jirautomation;
import com.automationcalling.utils.jira.JiraUtil;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.automationcalling.utils.common.Constant.AMAZONURL;
import static com.automationcalling.utils.common.Constant.APPURL;
public class Amazon_TitleVerify {

    /**
     * SubTask No: TES-35
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
            driver.get(AMAZONURL);
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
            /*TransitionID: 111 is default for all Done subTask to Reopen Task
             *This is I verified like this http://localhost:8080/rest/api/2/issue/TES-36/transitions
             */
            jiraUtil.changeJiraWorkFlowStatus("TES-35", "Reopened", "111");
            Assert.fail("Test Execution Failed");
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }

}
