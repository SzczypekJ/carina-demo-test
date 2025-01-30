package com.zebrunner.carina.pageobjectpattern;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.demo.gui.pages.common.demoblaze.AllProductsPageBase;
import com.zebrunner.carina.demo.gui.pages.common.demoblaze.LoginPageBase;
import com.zebrunner.carina.demo.utils.TestingMethods;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class UserTest extends AbstractTest implements IAbstractTest {

    @Test
    public void createNewUser() {
        WebDriver driver = getDriver();

        LoginPageBase loginPage = initPage(driver, LoginPageBase.class);
        loginPage.open();

        String newUserName = RandomStringUtils.random(20, true, true);
        String newPassword = RandomStringUtils.random(20, true, true);

        AllProductsPageBase allProductsPage = initPage(driver, AllProductsPageBase.class);
        allProductsPage.getNavigationBar().goToSignIn();

        allProductsPage.SignIn(newUserName, newPassword);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The alert was not found in 10 seconds!")
                .until(ExpectedConditions.alertIsPresent());


        driver.switchTo().alert().accept();

//        new WebDriverWait(driver, Duration.ofSeconds(10))
//                .pollingEvery(Duration.ofMillis(500))
//                .withMessage("The login button was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(loginPage.getNavigationBar().getLoginButton().getElement()));

        Assert.assertTrue(loginPage.getNavigationBar().getLoginButton().isVisible(), "The login button was not found in 10 seconds!");

        loginPage.getNavigationBar().goToLogin();

//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.withMessage("The element Username field was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(loginPage.getUsernameField()));

        Assert.assertTrue(loginPage.getUsernameField().isVisible(), "The element Username field was not found in 10 seconds!");

        // Log in
        loginUser(newUserName, newPassword);

        TestingMethods testingMethods = new TestingMethods();
        testingMethods.assertLogin(driver, newUserName);
    }

}
