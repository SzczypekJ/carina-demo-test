package com.zebrunner.carina.demo.gui.pages.common.demoblaze;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.ProductComponentBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.ProductComponentDesktop;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class AllProductsPageBase<T extends ProductComponentBase> extends MainPageBase {

    @FindBy(xpath = "//div[@class='card h-100']")
    protected List<T> productList;

    @FindBy(css = "#sign-username")
    protected ExtendedWebElement signInUsername;

    @FindBy(css = "#sign-password")
    protected ExtendedWebElement signInPassword;

    @FindBy(xpath = "//button[@onclick='register()']")
    protected ExtendedWebElement registerButton;


    public AllProductsPageBase(WebDriver driver) {
        super(driver);
    }

    public void selectProductByIndex(int index) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("Product is not visible in the list!")
                .until(ExpectedConditions.visibilityOf(getProductList().get(index).getRootExtendedElement().getElement()));

        getProductList().get(index).clickOnProductLink();
    }

    public abstract List<String> getProductNames();

    public void SignIn(String username, String password) {
        signInUsername.type(username);
        signInPassword.type(password);
        registerButton.click();
    }

    public abstract List<T> getProductList();
}
