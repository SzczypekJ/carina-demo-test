package com.zebrunner.carina.demo.gui.pages.common.demoblaze;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.ProductComponentDesktop;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.FooterBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.NavigationBarBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public abstract class AllProductsPageBase extends AbstractPage {

    @FindBy(xpath = "//div[@class='card h-100']")
    protected List<ProductComponentDesktop> productList;

    @FindBy(css = "#sign-username")
    protected ExtendedWebElement signInUsername;

    @FindBy(css = "#sign-password")
    protected ExtendedWebElement signInPassword;

    @FindBy(xpath = "//button[@onclick='register()']")
    protected ExtendedWebElement registerButton;


    public AllProductsPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract NavigationBarBase getNavigationBar();

    public abstract FooterBase getFooter();

    public abstract void selectProductByIndex(int index);

    public abstract List<String> getProductNames();


    public abstract void SignIn(String username, String password);

    public abstract List<ProductComponentDesktop> getProductList();
}
