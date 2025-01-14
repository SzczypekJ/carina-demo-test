package com.zebrunner.carina.demo.gui.pages.demoblaze;

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

public class CartPage extends AbstractPage {

    @FindBy(css = "#tbodyid > tr > td:nth-child(2)")
    private List<ExtendedWebElement> productNamesInCart;

    @FindBy(css = "#tbodyid > tr > td:nth-child(3)")
    private List<ExtendedWebElement> productPricesInCart;

    @FindBy(xpath = "//button[@data-target='#orderModal']")
    private ExtendedWebElement placeOrderButton;

    @FindBy(css = "#totalp")
    private ExtendedWebElement totalPrice;

    @FindBy(xpath = "//a[@id='logout2']")
    private ExtendedWebElement logoutButton;

    @Getter
    @FindBy(xpath = "//a[contains(@onclick, 'deleteItem')]")
    private List<ExtendedWebElement> deleteButtons;

    @FindBy(id = "tbodyid")
    private ExtendedWebElement cartTable;

    public CartPage(WebDriver driver) {
        super(driver);
    }


    public List<String> getProductNamesInCart() {
        waitForProductNamesInCart();
        return productNamesInCart.stream()
                .map(ExtendedWebElement::getText)
                .toList();
    }


    public List<ExtendedWebElement> getProductNamesInCartElement() {
        return productNamesInCart;
    }

    public List<String> getProductPricesInCart() {
        waitForProductPricesInCart();

        return productPricesInCart.stream()
                .map(WebElement::getText)
                .map(rawPrice -> rawPrice.replaceAll("[^0-9]", ""))
                .toList();
    }

    public List<ExtendedWebElement> getProductPricesInCartElement() {
        return productPricesInCart;
    }

    public void placeOrder() {
        placeOrderButton.click();
    }

    public WebElement getProductNameInTheCartElement() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("No product names found in the cart!")
                .until(driver1 -> productNamesInCart != null && !productNamesInCart.isEmpty() &&
                        productNamesInCart.stream().allMatch(ExtendedWebElement::isElementPresent));

        if (productNamesInCart.isEmpty()) {
            throw new IllegalStateException("No product names found in the cart!");
        }

        return productNamesInCart.get(0).getElement();
    }

    public WebElement getProductPriceInTheCartElement() {
        return productPricesInCart.get(0);
    }

    public String getTotalPrice() {
        return totalPrice.getText();
    }


    public void deleteAllItems() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        for (ExtendedWebElement deleteButton : deleteButtons) {
            deleteButton.click();
            wait.until(ExpectedConditions.stalenessOf(deleteButton.getElement()));
        }

        boolean isEmpty = cartTable.findExtendedWebElements(By.xpath("./tr")).isEmpty();
        if (!isEmpty) {
            throw new AssertionError("Cart is not empty after deleting items!");
        }
    }


    private void waitForProductNamesInCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.withMessage("Product names are not visible in the cart!")
                .until(ExpectedConditions.visibilityOfAllElements(
                        productNamesInCart.stream().map(ExtendedWebElement::getElement).toList()));
    }

    private void waitForProductPricesInCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.withMessage("Product prices are not visible in the cart!")
                .until(ExpectedConditions.visibilityOfAllElements(
                        productPricesInCart.stream().map(ExtendedWebElement::getElement).toList()));
    }
}
