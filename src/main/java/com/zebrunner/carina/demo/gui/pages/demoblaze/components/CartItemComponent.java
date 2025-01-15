package com.zebrunner.carina.demo.gui.pages.demoblaze.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@Getter
public class CartItemComponent extends AbstractUIObject {

    @FindBy(css = "td:nth-child(1) img")
    private ExtendedWebElement productImage;

    @FindBy(css = "td:nth-child(2)")
    private ExtendedWebElement productName;

    @FindBy(css = "td:nth-child(3)")
    private ExtendedWebElement productPrice;

    @FindBy(css = "td:nth-child(4) a")
    private ExtendedWebElement deleteButton;

    public CartItemComponent(WebDriver driver) {
        super(driver);
    }

    public CartItemComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getProductPrice() {
        return productPrice.getText();
    }

    public void clickDeleteButton() {
        deleteButton.click();
    }

    public ExtendedWebElement getProductNameElement() {
        return productName;
    }

    public ExtendedWebElement getProductPriceElement() {
        return productPrice;
    }
}
