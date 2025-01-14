package com.zebrunner.carina.demo.gui.pages.demoblaze;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class ProductPage extends AbstractPage {

    @FindBy(xpath = "//h3[@class='price-container']")
    private ExtendedWebElement priceContainer;

    @FindBy(xpath = "//a[@class='btn btn-success btn-lg']")
    private ExtendedWebElement addToCartButton;

    @FindBy(xpath = "//a[@id='cartur']")
    private ExtendedWebElement cartButton;

    public ProductPage(WebDriver driver) {
        super(driver);
    }


    public String getProductPrice() {
        String rawPrice = priceContainer.getText();
        return rawPrice.replaceAll("[^0-9]", "");
    }

    public void addToCart() {
        addToCartButton.click();
    }

    public void goToCart() {
        cartButton.click();
    }

    public ExtendedWebElement getProductPriceElement() {
        return priceContainer;
    }
}