package com.zebrunner.carina.pageobjectpattern;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.demo.utils.TestingMethods;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class LoginPageTest extends AbstractTest implements IAbstractTest {

    public static final Logger logger = LoggerFactory.getLogger(LoginPageTest.class);

    @Test
    public void testLogin() {
        logger.info("Test Login");
        WebDriver driver = getDriver();
        String username = R.CONFIG.get("username1");
        loginUser(username);

        TestingMethods testingMethods = new TestingMethods();
        // Check if log in is okey
        testingMethods.assertLogin(driver, username);
    }
}