package com.zebrunner.carina.demo.gui.pages.demoblaze.desktop;

import com.zebrunner.carina.demo.gui.pages.common.demoblaze.CartPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.CartItemComponentMobile;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.CartItemComponentDesktop;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.FooterDesktop;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.NavigationBarDesktop;
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

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CartPageBase.class)
public class CartPageDesktop extends CartPageBase<CartItemComponentDesktop> {

    public static final Logger logger = LoggerFactory.getLogger(CartPageDesktop.class);

    @FindBy(xpath = "//nav")
    private NavigationBarDesktop navigationBar;

    @FindBy(id = "footc")
    private FooterDesktop footer;

    @FindBy(css = "#tbodyid > tr")
    protected List<CartItemComponentDesktop> cartItems;

    public CartPageDesktop(WebDriver driver) {
        super(driver);
    }

    @Override
    public NavigationBarDesktop getNavigationBar() {
        return navigationBar;
    }

    @Override
    public FooterDesktop getFooter() {
        return footer;
    }

    @Override
    public List<String> getProductNamesInCart() {
        waitForProductNamesInCart();
        Assert.assertFalse(cartItems.isEmpty(), "Cart is empty, no product names found!");
        return cartItems.stream()
                .map(CartItemComponentDesktop::getProductName)
                .toList();
    }

    @Override
    public List<ExtendedWebElement> getProductNamesInCartElement() {
        waitForProductNamesInCart();
        // Don't work with to assert
        Assert.assertFalse(cartItems.isEmpty(), "Cart is empty, no product name elements found!");
        return cartItems.stream()
                .map(CartItemComponentDesktop::getProductNameElement)
                .toList();
    }

    @Override
    public List<String> getProductPricesInCart() {
        waitForProductPricesInCart();
        Assert.assertFalse(cartItems.isEmpty(), "Cart is empty, no product prices found!");
        return cartItems.stream()
                .map(CartItemComponentDesktop::getProductPrice)
                .map(rawPrice -> rawPrice.replaceAll("[^0-9]", ""))
                .toList();
    }

    @Override
    public List<ExtendedWebElement> getProductPricesInCartElement() {
        waitForProductPricesInCart();
        Assert.assertFalse(cartItems.isEmpty(), "Cart is empty, no product prices found!");
        return cartItems.stream()
                .map(CartItemComponentDesktop::getProductPriceElement)
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

        for (CartItemComponentDesktop item : cartItems) {
            item.clickDeleteButton();
            wait.until(ExpectedConditions.stalenessOf(item.getRootExtendedElement().getElement()));
        }

        Assert.assertTrue(cartTable.findExtendedWebElements(By.xpath("./tr")).isEmpty(),
                "Cart is not empty after deleting items!");
    }

    @Override
    public void waitForProductNamesInCart() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("Product names are not visible in the cart!")
                .until(driver1 -> {
                    List<CartItemComponentDesktop> items = getCartItems();
                    if (items == null || items.isEmpty()) {
                        logger.info("Cart items list is empty! Waiting for them to load...");
                        return null; // Kontynuuj oczekiwanie
                    }
                    List<ExtendedWebElement> nameElements = cartItems.stream()
                            .map(item -> {
                                item.getProductNameElement().refresh();
                                return item.getProductNameElement();
                            })
                            .toList();
                    if (nameElements.isEmpty()) {
                        logger.info("No product name elements found yet. Waiting...");
                        return null;
                    }
                    boolean allVisible = nameElements.stream()
                            .allMatch(element -> element.getElement().isDisplayed());
                    logger.info("Are all product name elements displayed: " + allVisible);
                    return allVisible ? true : null;
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
    public List<CartItemComponentDesktop> getCartItems() {
        return cartItems;
    }
}
