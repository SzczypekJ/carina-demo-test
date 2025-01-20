package com.zebrunner.carina.demo.gui.pages.common;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.Footer;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.NavigationBar;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.ProductComponent;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class AllProductsPageBase extends AbstractPage {

    @FindBy(xpath = "//div[@class='card h-100']")
    protected List<ProductComponent> productList;

    @FindBy(css = "#sign-username")
    protected ExtendedWebElement signInUsername;

    @FindBy(css = "#sign-password")
    protected ExtendedWebElement signInPassword;

    @FindBy(xpath = "//button[@onclick='register()']")
    protected ExtendedWebElement registerButton;

    @FindBy(xpath = "//nav")
    protected NavigationBar navigationBar;

    @FindBy(id = "footc")
    protected Footer footer;

    public AllProductsPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract NavigationBar getNavigationBar();

    public abstract Footer getFooter();

    public abstract void selectProductByIndex(int index);

    public abstract List<String> getProductNames();


    public abstract void SignIn(String username, String password);

    public abstract List<ProductComponent> getProductList();
}
