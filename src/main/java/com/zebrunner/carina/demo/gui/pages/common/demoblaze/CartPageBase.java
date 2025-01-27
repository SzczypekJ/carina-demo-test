package com.zebrunner.carina.demo.gui.pages.common.demoblaze;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.CartItemComponentDesktop;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.FooterBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.NavigationBarBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public abstract class CartPageBase extends AbstractPage {

    @FindBy(css = "#tbodyid > tr")
    protected List<CartItemComponentDesktop> cartItems;

    @FindBy(xpath = "//button[@data-target='#orderModal']")
    protected ExtendedWebElement placeOrderButton;

    @FindBy(css = "#totalp")
    protected ExtendedWebElement totalPrice;

    @FindBy(id = "tbodyid")
    protected ExtendedWebElement cartTable;

    public CartPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract NavigationBarBase getNavigationBar();

    public abstract FooterBase getFooter();

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

    public abstract List<CartItemComponentDesktop> getCartItems();
}
