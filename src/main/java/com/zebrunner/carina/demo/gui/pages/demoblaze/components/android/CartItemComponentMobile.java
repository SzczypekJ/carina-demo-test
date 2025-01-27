package com.zebrunner.carina.demo.gui.pages.demoblaze.components.android;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.CartItemComponentBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

@Getter
public class CartItemComponentMobile extends CartItemComponentBase {

    public CartItemComponentMobile(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @Override
    public String getProductName() {
        return productName.getText();
    }

    @Override
    public String getProductPrice() {
        return productPrice.getText();
    }

    @Override
    public void clickDeleteButton() {
        Assert.assertTrue(deleteButton.isVisible(), "Delete button is not visible");
        deleteButton.click();
    }

    @Override
    public ExtendedWebElement getProductNameElement() {
        return productName;
    }

    @Override
    public ExtendedWebElement getProductPriceElement() {
        return productPrice;
    }
}
