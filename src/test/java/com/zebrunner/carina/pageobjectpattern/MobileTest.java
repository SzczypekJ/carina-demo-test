package com.zebrunner.carina.pageobjectpattern;

import com.zebrunner.carina.core.IAbstractTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;


public class MobileTest implements IAbstractTest {
    public static final Logger logger = LoggerFactory.getLogger(MobileTest.class);

    @Test
    public void testMobile() {
        getDriver().get("https://www.demoblaze.com");
    }
}
