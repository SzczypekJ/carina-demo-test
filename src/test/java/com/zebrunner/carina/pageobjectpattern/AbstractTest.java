package com.zebrunner.carina.pageobjectpattern;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.demo.gui.pages.common.demoblaze.*;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.CartItemComponentBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.ProductComponentBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.ProductComponentDesktop;
import com.zebrunner.carina.demo.utils.Person;
import com.zebrunner.carina.utils.R;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTest implements IAbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(AbstractTest.class);

    public void loginUser(String userKey) {
        WebDriver driver = getDriver();
        String userName = R.CONFIG.get(userKey);
        String password = R.CONFIG.get("passwordGlobal");
        login(driver, userName, password);
    }

    public void purchaseProduct(WebDriver driver, String totalPrice) {
        CartPageBase<?> cartPage = initPage(driver, CartPageBase.class);
        cartPage.placeOrder();

//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.withMessage("The element Name field was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(checkoutPage.getNameField()));
//
//        wait.withMessage("The element Country field was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(checkoutPage.getCountryField()));
//
//        wait.withMessage("The element City field was not found in 10 seconds!").until(ExpectedConditions.visibilityOf(checkoutPage.getCityField()));
//
//        wait.withMessage("The element Card field was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(checkoutPage.getCardField()));
//
//        wait.withMessage("The element Month field was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(checkoutPage.getMonthField()));
//
//        wait.withMessage("The element Year field was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(checkoutPage.getYearField()));

        CheckoutPageBase checkoutPage = initPage(driver, CheckoutPageBase.class);
        Assert.assertTrue(checkoutPage.getNameField().isVisible(), "The element Name field was not found!");
        Assert.assertTrue(checkoutPage.getCountryField().isVisible(), "The element Country field was not found!");
        Assert.assertTrue(checkoutPage.getCityField().isVisible(), "The element City field was not found!");
        Assert.assertTrue(checkoutPage.getCardField().isVisible(), "The element Card field was not found!");
        Assert.assertTrue(checkoutPage.getMonthField().isVisible(), "The element Month field was not found!");
        Assert.assertTrue(checkoutPage.getYearField().isVisible(), "The element Year field was not found!");


        // Filling the purchase details
        Person person = new Person("Jakub", "Poland", "Cracow", "411111111111", "December", "2025");

        checkoutPage.fillCheckoutDetails(person);

//        wait.withMessage("The element Purchase button was not found in 10 seconds!")
//                .until(ExpectedConditions.elementToBeClickable(checkoutPage.getPurchaseButton()));
        Assert.assertTrue(checkoutPage.getPurchaseButton().isVisible(), "The element Purchase button was not found!");

        checkoutPage.completePurchase();
//        wait.withMessage("The element Purchase details was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(checkoutPage.getPurchaseDetailsElement()));
        Assert.assertTrue(checkoutPage.getPurchaseButton().isVisible(), "The element Purchase button was not found!");

        // Check if the details of purchase are okay
        assertPurchaseDetails(driver, totalPrice, person.getCreditCard(), person.getName());

        // Confirm the purchase
        checkoutPage.confirmPurchase();
    }

    public void assertPurchaseDetails(WebDriver driver, String totalPrice, String creditCard, String name) {
        CheckoutPageBase checkoutPage = initPage(driver, CheckoutPageBase.class);

        // Validate the purchase summary details
        String purchaseDetails = checkoutPage.getPurchaseDetails();
        Assert.assertTrue(purchaseDetails.contains("Id:"), "Purchase details missing ID!");
        Assert.assertTrue(purchaseDetails.contains("Amount: " + totalPrice), "Purchase amount mismatch!");
        Assert.assertTrue(purchaseDetails.contains("Card Number: " + creditCard), "Credit card mismatch!");
        Assert.assertTrue(purchaseDetails.contains("Name: " + name), "Name mismatch in purchase details!");

        logger.info("Test passed: Purchase details are correct!");
    }

    public void login(WebDriver driver, String username, String password) {
        LoginPageBase loginPage = initPage(driver, LoginPageBase.class);
        loginPage.open();
        loginPage.getNavigationBar().goToLogin();

//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.withMessage("The element Username field was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(loginPage.getUsernameField()));

        Assert.assertTrue(loginPage.getUsernameField().isDisplayed(), "The element Username field was not found in 10 seconds!");


        loginPage.login(username, password);
    }

    public void assertLogin(WebDriver driver, String username) {
        LoginPageBase loginPage = initPage(driver, LoginPageBase.class);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The element Welcome text was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(loginPage.getWelcomeTextElement()));

        // If I don't use WebDriverWait I got an error
        Assert.assertTrue(loginPage.getWelcomeTextElement().isDisplayed(),
                "The element Welcome text was not found in 10 seconds!");

        Assert.assertEquals(loginPage.getWelcomeText(), "Welcome " + username,
                "Login failed or welcome message is incorrect!");
    }

    public List<String> addProductToCartByIndex(WebDriver driver, int index)  {
        AllProductsPageBase<? extends ProductComponentBase> allProductsPage = initPage(driver, AllProductsPageBase.class);

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The element at index " + index + " was not found or not visible in 20 seconds!")
                .until(driver1 -> {
                    // Refresh the list of elements from DOM
                    List<? extends ProductComponentBase> productList = allProductsPage.getProductList();

                    // Check the size of product List
                    logger.info("Size of product list: " + (productList == null ? "null" : productList.size()));

                    // Check if the list is loaded
                    if (productList == null || productList.isEmpty()) {
                        logger.info("Waiting for loaded the list of product");
                        return null; // wait if not loaded
                    }

                    // Check if the index is in the bounds
                    if (index >= productList.size()) {
                        logger.info("Index " + index + " out of bounds for product list size " + productList.size());
                        Assert.fail(
                                "Index " + index + " out of bounds for product list size " + productList.size());
                    }

                    // Get the element specify by the index
                    try {
                        WebElement element = productList.get(index).getElement();

                        // Check if the element is displayed
                        boolean isDisplayed = element.isDisplayed();
                        logger.info("Element at index " + index + " displayed: " + isDisplayed);
                        return isDisplayed ? element : null; // Wait if it's not displayed
                    } catch (IndexOutOfBoundsException e) {
                        logger.info("Caught IndexOutOfBoundsException: " + e.getMessage());
                        return null;
                    }
                });

        // I cannot change the webdriverwait which I have above - I will get error
        List<? extends ProductComponentBase> productList = allProductsPage.getProductList();
        Assert.assertNotNull(productList, "Product list is null after waiting.");
        Assert.assertFalse(productList.isEmpty(), "Product list is empty after waiting.");
        Assert.assertTrue(index < productList.size(), "Index " + index + " is out of bounds for product list size " + productList.size());
        Assert.assertTrue(productList.get(index).getElement().isDisplayed(),
                "Element at index " + index + " is not visible after waiting.");

        ProductPageBase productPage = initPage(driver, ProductPageBase.class);
        String productName = allProductsPage.getProductList().get(index).getText().split("\n")[0];
        allProductsPage.selectProductByIndex(index);

//        new WebDriverWait(driver, Duration.ofSeconds(10))
//                .pollingEvery(Duration.ofMillis(500))
//                .withMessage("The element Product price was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(productPage.getProductPriceElement().getElement()));

        Assert.assertTrue(productPage.getProductPriceElement().isVisible(),
                "The element Product price was not found or not visible in 10 seconds!");

        String price = productPage.getProductPrice();
        productPage.addToCart();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The alert was not found in 10 seconds!")
                .until(ExpectedConditions.alertIsPresent());

        // I cannot change waiting for this alert

        driver.switchTo().alert().accept();

        allProductsPage.getNavigationBar().goToHome();

        List<String> informations = new ArrayList<>();
        informations.add(productName);
        informations.add(price);

        return informations;
    }

    public void assertsInCart(WebDriver driver, String productName, String price) {
        CartPageBase<? extends CartItemComponentBase> cartPage = initPage(driver, CartPageBase.class);

//        new WebDriverWait(driver, Duration.ofSeconds(10))
//                .pollingEvery(Duration.ofMillis(500))
//                .withMessage("The element Product name in the cart was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(cartPage.getProductNameInTheCartElement()));

        Assert.assertTrue(cartPage.getProductNameInTheCartElement().isDisplayed(),
                "The element Product name in the cart was not found in 10 seconds!");

//        new WebDriverWait(driver, Duration.ofSeconds(10))
//                .pollingEvery(Duration.ofMillis(500))
//                .withMessage("The element Product price in the cart was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(cartPage.getProductPriceInTheCartElement()));

        Assert.assertTrue(cartPage.getProductPriceInTheCartElement().isDisplayed(),
                "The element Product price in the cart was not found in 10 seconds!");


        logger.info("The product name is: " +  cartPage.getProductNamesInCart().getFirst());
        Assert.assertEquals(cartPage.getProductNamesInCart().getFirst(), productName,
                "Product name in the cart does not match the selected product!");
        Assert.assertTrue(price.contains(cartPage.getProductPricesInCart().getFirst()),
                "Product price in the cart does not match the selected product!");
    }

    public void deleteProductsInTheCartAndCheckIt(WebDriver driver) {
        CartPageBase<? extends CartItemComponentBase> cartPage = initPage(driver, CartPageBase.class);
        cartPage.deleteAllItems();
        Assert.assertTrue(cartPage.getCartItems().isEmpty(),
                "Cart is not empty after removing all items");
    }

    public List<String> loginAndAddProductToCart(WebDriver driver, String username, int productIndex) {
        login(driver, username, R.CONFIG.get("passwordGlobal"));
        return addProductToCartByIndex(driver, productIndex);
    }

    public void navigateToCartAndVerify(WebDriver driver, List<String> expectedProductNames, List<String> expectedPrices) {
        ProductPageBase productPage = initPage(driver, ProductPageBase.class);
        productPage.getNavigationBar().goToCart();

        CartPageBase<? extends CartItemComponentBase> cartPage = initPage(driver, CartPageBase.class);

        Assert.assertTrue(cartPage.getProductNamesInCartElement().stream()
                .allMatch(element -> element.getElement().isDisplayed()), "The cart is not loaded properly!");

        List<String> cartProductNames = cartPage.getProductNamesInCart();
        List<String> cartProductPrices = cartPage.getProductPricesInCart();

        // Sort lists for comparison
        expectedProductNames = expectedProductNames.stream().sorted().toList();
        cartProductNames = cartProductNames.stream().sorted().toList();
        expectedPrices = expectedPrices.stream().sorted().toList();
        cartProductPrices = cartProductPrices.stream().sorted().toList();

        Assert.assertEquals(cartProductNames, expectedProductNames, "Product names in cart do not match expected!");
        Assert.assertEquals(cartProductPrices, expectedPrices, "Product prices in cart do not match expected!");
    }

    public void waitForProductsOnMainPage (WebDriver driver, AllProductsPageBase<? extends ProductComponentBase> allProductsPage) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("No products found on the page!")
                .until(driver1 -> {
                    List<? extends ProductComponentBase> productList = allProductsPage.getProductList();

                    logger.info("The size of list of products: " + (productList == null ? "null" : productList.size()));

                    if (productList == null || productList.isEmpty()) {
                        logger.info("Wait for loading a list of products");
                        return null; // Keep waiting
                    }

                    boolean allDisplayed = productList.stream()
                            .allMatch(element -> element.getElement().isDisplayed());

                    logger.info("If all of the products are displayed: " + allDisplayed);
                    return allDisplayed ? productList : null; // Keep waiting if false
                });
    }

    public void waitForProductsOnCartPage (WebDriver driver, CartPageBase<? extends CartItemComponentBase> cartPage) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The elements Product names and prices in the cart were not found in 10 seconds!")
                .until(driver1 -> {
                    List<ExtendedWebElement> productNames = cartPage.getProductNamesInCartElement();
                    List<ExtendedWebElement> productPrices = cartPage.getProductPricesInCartElement();

                    if ((productNames == null || productNames.isEmpty()) ||
                            (productPrices == null || productPrices.isEmpty())) {
                        logger.info("Waiting for product names and prices to be loaded...");
                        return null;
                    }

                    boolean allNamesDisplayed = productNames.stream().allMatch(element -> element.getElement().isDisplayed());
                    boolean allPricesDisplayed = productPrices.stream().allMatch(element -> element.getElement().isDisplayed());

                    logger.info("Are all product names visible: " + allNamesDisplayed);
                    logger.info("Are all product prices visible: " + allPricesDisplayed);

                    return (allNamesDisplayed && allPricesDisplayed) ? true : null;
                });
    }

    public Pair<List<String>, List<String>> addMultipleProductsToCart(WebDriver driver, List<Integer> indexes, AllProductsPageBase<? extends ProductComponentBase> allProductsPage, int size) {
        List<String> selectedProductNames = new ArrayList<>();
        List<String> selectedProductPrices = new ArrayList<>();

        List<String> allProductNames = allProductsPage.getProductNames();

        for (int index : indexes) {
            if (index < size) {
                List<String> informations = addProductToCartByIndex(driver, index);
                selectedProductNames.add(allProductNames.get(index));
                selectedProductPrices.add(informations.get(1));
            } else {
                Assert.fail("Index " + index + " is out of bounds for product list size: " + size);
            }
        }

        return Pair.of(selectedProductNames, selectedProductPrices);
    }

    public void validateCartContents(CartPageBase<? extends CartItemComponentBase> cartPage, List<String> selectedProductNames, List<String> cartProductNames, List<String> selectedProductPrices, List<String> cartProductPrices) {

        // Sort lists before comparison
        selectedProductNames = selectedProductNames.stream().sorted().toList();
        cartProductNames = cartProductNames.stream().sorted().toList();
        selectedProductPrices = selectedProductPrices.stream().sorted().toList();
        cartProductPrices = cartProductPrices.stream().sorted().toList();

        Assert.assertEquals(cartProductNames, selectedProductNames,
                "Product names in the cart do not match the selected products!");
        Assert.assertEquals(cartProductPrices, selectedProductPrices,
                "Product prices in the cart do not match the selected products!");
    }

    public void validateTotalPrice(CartPageBase<? extends CartItemComponentBase> cartPage, List<String> expectedPrices, String totalPrice) {
        int actualTotalPrice = Integer.parseInt(totalPrice);

        int expectedTotalPrice = expectedPrices.stream()
                .mapToInt(Integer::parseInt)
                .sum();

        Assert.assertEquals(actualTotalPrice, expectedTotalPrice, "Total price mismatch!");
    }
}
