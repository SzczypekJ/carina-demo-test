package com.zebrunner.carina.demo.gui.pages.demoblaze.components.base;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

@Getter
public abstract class CartItemComponentBase extends AbstractUIObject {

    @FindBy(css = "td:nth-child(1) img")
    protected ExtendedWebElement productImage;

    @FindBy(css = "td:nth-child(2)")
    protected ExtendedWebElement productName;

    @FindBy(css = "td:nth-child(3)")
    protected ExtendedWebElement productPrice;

    @FindBy(css = "td:nth-child(4) a")
    protected ExtendedWebElement deleteButton;

    public CartItemComponentBase(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public abstract String getProductName();

    public abstract String getProductPrice();

    public abstract void clickDeleteButton();

    public abstract ExtendedWebElement getProductNameElement();

    public abstract ExtendedWebElement getProductPriceElement();
}
