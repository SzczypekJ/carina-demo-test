package com.zebrunner.carina.demo.gui.pages.common;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.Footer;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.NavigationBar;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public abstract class ProductPageBase extends AbstractPage {

    @FindBy(xpath = "//h3[@class='price-container']")
    protected ExtendedWebElement priceContainer;

    @FindBy(xpath = "//a[@class='btn btn-success btn-lg']")
    protected ExtendedWebElement addToCartButton;

    @FindBy(xpath = "//nav")
    protected NavigationBar navigationBar;

    @FindBy(id = "footc")
    protected Footer footer;

    public ProductPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract String getProductPrice();

    public abstract void addToCart();

    public abstract ExtendedWebElement getProductPriceElement();

    public abstract NavigationBar getNavigationBar();

    public abstract ExtendedWebElement getFooter();
}
