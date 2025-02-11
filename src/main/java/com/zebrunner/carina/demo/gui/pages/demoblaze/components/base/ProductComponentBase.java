package com.zebrunner.carina.demo.gui.pages.demoblaze.components.base;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@Getter
public abstract class ProductComponentBase extends AbstractUIObject {

    public ProductComponentBase(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @FindBy(xpath = ".//h4/a")
    protected ExtendedWebElement productName;

    @FindBy(xpath = ".//div[contains(@class, 'card-block')]//h5")
    protected ExtendedWebElement productPrice;

    @FindBy(xpath = ".//a[@class='hrefch']")
    protected ExtendedWebElement productLink;

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
