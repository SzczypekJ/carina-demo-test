package com.zebrunner.carina.demo.gui.pages.demoblaze.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ProductComponent extends AbstractUIObject {

    @FindBy(xpath = ".//h4/a")
    private ExtendedWebElement productName;

    @FindBy(xpath = ".//div[contains(@class, 'card-block')]//h5")
    private ExtendedWebElement productPrice;

    @FindBy(xpath = ".//a[@class='hrefch']")
    private ExtendedWebElement productLink;

    public ProductComponent(WebDriver driver) {
        super(driver);
    }

    public ProductComponent(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getProductPrice() {
        return productPrice.getText();
    }

    public String getProductLink() {
        return productLink.getText();
    }

    public void clickOnProductLink() {
        productLink.click();
    }
}
