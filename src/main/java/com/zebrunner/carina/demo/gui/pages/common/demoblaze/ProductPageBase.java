package com.zebrunner.carina.demo.gui.pages.common.demoblaze;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.FooterBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.NavigationBarBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public abstract class ProductPageBase extends MainPageBase {

    @FindBy(xpath = "//h3[@class='price-container']")
    protected ExtendedWebElement priceContainer;

    @FindBy(xpath = "//a[@class='btn btn-success btn-lg']")
    protected ExtendedWebElement addToCartButton;

    public ProductPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract String getProductPrice();

    public abstract void addToCart();

    public abstract ExtendedWebElement getProductPriceElement();

}
