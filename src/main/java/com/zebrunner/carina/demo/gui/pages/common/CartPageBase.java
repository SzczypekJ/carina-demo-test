package com.zebrunner.carina.demo.gui.pages.common;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.CartItemComponent;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.Footer;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.NavigationBar;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public abstract class CartPageBase extends AbstractPage {

    @FindBy(css = "#tbodyid > tr")
    protected List<CartItemComponent> cartItems;

    @FindBy(xpath = "//button[@data-target='#orderModal']")
    protected ExtendedWebElement placeOrderButton;

    @FindBy(css = "#totalp")
    protected ExtendedWebElement totalPrice;

    @FindBy(id = "tbodyid")
    protected ExtendedWebElement cartTable;

    @FindBy(xpath = "//nav")
    protected NavigationBar navigationBar;

    @FindBy(id = "footc")
    protected Footer footer;

    public CartPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract NavigationBar getNavigationBar();

    public abstract Footer getFooter();

    public abstract List<String> getProductNamesInCart();

    public abstract List<ExtendedWebElement> getProductNamesInCartElement();

    public abstract List<String> getProductPricesInCart();

    public abstract List<ExtendedWebElement> getProductPricesInCartElement();

    public abstract void placeOrder();

    public abstract WebElement getProductNameInTheCartElement();

    public abstract WebElement getProductPriceInTheCartElement();

    public abstract String getTotalPrice();

    public abstract void deleteAllItems();


    public abstract void waitForProductNamesInCart();

    public abstract void waitForProductPricesInCart();

    public abstract List<CartItemComponent> getCartItems();
}
