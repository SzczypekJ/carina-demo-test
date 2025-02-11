package com.zebrunner.carina.demo.gui.pages.demoblaze.android;

import com.zebrunner.carina.demo.gui.pages.common.demoblaze.CartPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.CartItemComponentMobile;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.FooterMobile;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.NavigationBarMobile;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = CartPageBase.class)
public class CartPageAndroid extends CartPageBase<CartItemComponentMobile> {

    public static final Logger logger = LoggerFactory.getLogger(CartPageAndroid.class);

    @FindBy(xpath = "//nav")
    private NavigationBarMobile navigationBar;

    @FindBy(id = "footc")
    private FooterMobile footer;

    @FindBy(css = "#tbodyid > tr")
    protected List<CartItemComponentMobile> cartItems;

    public CartPageAndroid(WebDriver driver) {
        super(driver);
    }

    @Override
    public NavigationBarMobile getNavigationBar() {
        return navigationBar;
    }

    @Override
    public FooterMobile getFooter() {
        return footer;
    }

    @Override
    public List<String> getProductNamesInCart() {
        waitForProductNamesInCart();
        Assert.assertFalse(cartItems.isEmpty(), "Cart is empty, no product names found!");
        return cartItems.stream()
                .map(CartItemComponentMobile::getProductName)
                .toList();
    }

    @Override
    public List<ExtendedWebElement> getProductNamesInCartElement() {
        waitForProductNamesInCart();
        // Don't work with to assert
        Assert.assertFalse(cartItems.isEmpty(), "Cart is empty, no product name elements found!");
        return cartItems.stream()
                .map(CartItemComponentMobile::getProductNameElement)
                .toList();
    }

    @Override
    public List<String> getProductPricesInCart() {
        waitForProductPricesInCart();
        Assert.assertFalse(cartItems.isEmpty(), "Cart is empty, no product prices found!");
        return cartItems.stream()
                .map(CartItemComponentMobile::getProductPrice)
                .map(rawPrice -> rawPrice.replaceAll("[^0-9]", ""))
                .toList();
    }

    @Override
    public List<ExtendedWebElement> getProductPricesInCartElement() {
        waitForProductPricesInCart();
        Assert.assertFalse(cartItems.isEmpty(), "Cart is empty, no product prices found!");
        return cartItems.stream()
                .map(CartItemComponentMobile::getProductPriceElement)
                .toList();
    }

    @Override
    public void placeOrder() {
        placeOrderButton.click();
    }

    @Override
    public WebElement getProductNameInTheCartElement() {
        waitForProductNamesInCart();
        Assert.assertFalse(cartItems.isEmpty(), "No product names found in the cart!");
        return cartItems.getFirst().getProductNameElement().getElement();
    }

    @Override
    public WebElement getProductPriceInTheCartElement() {
        waitForProductPricesInCart();
        Assert.assertFalse(cartItems.isEmpty(), "No product prices found in the cart!");
        return cartItems.getFirst().getProductPriceElement().getElement();
    }

    @Override
    public String getTotalPrice() {
        Assert.assertTrue(totalPrice.isElementPresent(), "Total price element is not present in the cart!");
        return totalPrice.getText();
    }

    @Override
    public void deleteAllItems() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (CartItemComponentMobile item : cartItems) {
            item.clickDeleteButton();
            wait.until(ExpectedConditions.stalenessOf(item.getRootExtendedElement().getElement()));
        }

        Assert.assertTrue(cartTable.findExtendedWebElements(By.xpath("./tr")).isEmpty(),
                "Cart is not empty after deleting items!");
    }

    @Override
    public void waitForProductNamesInCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.withMessage("Product names are not visible in the cart!")
                .until(driver -> {
                    if (cartItems.isEmpty()) {
                        logger.info("Cart items list is empty!");
                        return false;
                    }

                    boolean allVisible = cartItems.stream()
                            .map(item -> item.getProductNameElement().getElement())
                            .allMatch(WebElement::isDisplayed);

                    if (!allVisible) {
                        logger.info("Not all product names are visible yet!");
                    }
                    
                    logger.info(String.valueOf(allVisible));
                    return allVisible;
                });
    }

    @Override
    public void waitForProductPricesInCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.withMessage("Product prices are not visible in the cart!")
                .until(ExpectedConditions.visibilityOfAllElements(
                        cartItems.stream()
                                .map(item -> item.getProductPriceElement().getElement())
                                .toList()));
    }

    @Override
    public List<CartItemComponentMobile> getCartItems() {
        return cartItems;
    }
}
