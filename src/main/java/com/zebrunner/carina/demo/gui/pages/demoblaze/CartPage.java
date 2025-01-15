package com.zebrunner.carina.demo.gui.pages.demoblaze;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.CartItemComponent;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.NavigationBar;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@Getter
public class CartPage extends AbstractPage {

    @FindBy(css = "#tbodyid > tr")
    private List<CartItemComponent> cartItems;

    @FindBy(xpath = "//button[@data-target='#orderModal']")
    private ExtendedWebElement placeOrderButton;

    @FindBy(css = "#totalp")
    private ExtendedWebElement totalPrice;

    @FindBy(id = "tbodyid")
    private ExtendedWebElement cartTable;

    @FindBy(xpath = "//nav")
    private NavigationBar navigationBar;

    public CartPage(WebDriver driver) {
        super(driver);
    }


    public List<String> getProductNamesInCart() {
        waitForProductNamesInCart();
        return cartItems.stream()
                .map(CartItemComponent::getProductName)
                .toList();
    }

    public List<ExtendedWebElement> getProductNamesInCartElement() {
        waitForProductNamesInCart();
        return cartItems.stream()
                .map(CartItemComponent::getProductNameElement)
                .toList();
    }

    public List<String> getProductPricesInCart() {
        waitForProductPricesInCart();

        return cartItems.stream()
                .map(CartItemComponent::getProductPrice)
                .map(rawPrice -> rawPrice.replaceAll("[^0-9]", ""))
                .toList();
    }

    public List<ExtendedWebElement> getProductPricesInCartElement() {
        waitForProductPricesInCart();
        return cartItems.stream()
                .map(CartItemComponent::getProductPriceElement)
                .toList();
    }

    public void placeOrder() {
        placeOrderButton.click();
    }

    public WebElement getProductNameInTheCartElement() {
        waitForProductNamesInCart();
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("No product names found in the cart!");
        }
        return cartItems.get(0).getProductNameElement().getElement();
    }

    public WebElement getProductPriceInTheCartElement() {
        waitForProductPricesInCart();
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("No product prices found in the cart!");
        }
        return cartItems.get(0).getProductPriceElement().getElement();
    }

    public String getTotalPrice() {
        return totalPrice.getText();
    }


    public void deleteAllItems() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (CartItemComponent item : cartItems) {
            item.clickDeleteButton();
            wait.until(ExpectedConditions.stalenessOf(item.getRootExtendedElement().getElement()));
        }

        boolean isEmpty = cartTable.findExtendedWebElements(By.xpath("./tr")).isEmpty();
        if (!isEmpty) {
            throw new AssertionError("Cart is not empty after deleting items!");
        }
    }


    private void waitForProductNamesInCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.withMessage("Product names are not visible in the cart!")
                .until(driver -> {
                    if (cartItems.isEmpty()) {
                        System.out.println("Cart items list is empty!");
                        return false;
                    }

                    boolean allVisible = cartItems.stream()
                            .map(item -> item.getProductNameElement().getElement())
                            .allMatch(WebElement::isDisplayed);

                    if (!allVisible) {
                        System.out.println("Not all product names are visible yet!");
                    }

                    System.out.println(allVisible);
                    return allVisible;
                });
    }

    private void waitForProductPricesInCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.withMessage("Product prices are not visible in the cart!")
                .until(ExpectedConditions.visibilityOfAllElements(
                        cartItems.stream()
                                .map(item -> item.getProductPriceElement().getElement())
                                .toList()));
    }
}
