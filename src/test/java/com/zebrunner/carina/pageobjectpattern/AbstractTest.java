package com.zebrunner.carina.pageobjectpattern;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.demo.utils.TestingMethods;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;

public abstract class AbstractTest implements IAbstractTest {

    protected void loginUser(String userKey) {
        WebDriver driver = getDriver();
        String username = R.CONFIG.get(userKey);
        String password = R.CONFIG.get("passwordGlobal");
        TestingMethods testingMethods = new TestingMethods();
        testingMethods.login(driver, username, password);
    }

    protected void loginUser(String userKey, String password) {
        WebDriver driver = getDriver();
        String username = R.CONFIG.get(userKey);
        TestingMethods testingMethods = new TestingMethods();
        testingMethods.login(driver, username, password);
    }
}
