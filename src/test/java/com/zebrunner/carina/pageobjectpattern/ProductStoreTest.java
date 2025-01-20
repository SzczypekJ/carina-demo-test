package com.zebrunner.carina.pageobjectpattern;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.demo.gui.pages.common.*;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.ProductComponent;
import com.zebrunner.carina.demo.utils.Person;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.zebrunner.carina.utils.R;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class ProductStoreTest implements IAbstractTest {

    public static final Logger logger = LoggerFactory.getLogger(ProductStoreTest.class);

    @Test
    public void testLogin() {
        logger.info("Test Login");
        WebDriver driver = getDriver();
        String username = R.CONFIG.get("username1");
        login(driver, username, R.CONFIG.get("passwordGlobal"));
        assertLogin(driver, username);
    }

    @Test
    public void testAddProductToCart() {
        WebDriver driver = getDriver();
        // Log in - the helpful method to log in - the reuse of code
        String username = R.CONFIG.get("username2");
        login(driver, username, R.CONFIG.get("passwordGlobal"));

        // Adds product to the cart - helpful method which reduce amount of code
        List<String> informations = addProductToCartByIndex(driver, 0);
        Assert.assertNotNull(informations, "Product information should not be null!");
        Assert.assertFalse(informations.isEmpty(), "Product information should not be empty!");

        String productName  = informations.get(0);
        logger.info("productName to:" + productName);
        String price = informations.get(1);

        ProductPageBase productPage = initPage(driver, ProductPageBase.class);
        productPage.getNavigationBar().goToCart();

        CartPageBase cartPage = initPage(driver, CartPageBase.class);

        // Better approach if the website is dynamic
//        new WebDriverWait(driver, Duration.ofSeconds(10))
//                .pollingEvery(Duration.ofMillis(500))
//                .withMessage("The cart is not loaded properly!")
//                .until(driver1 -> {
//                    List<ExtendedWebElement> productNamesInCart = cartPage.getProductNamesInCartElement();
//
//                    if (productNamesInCart.isEmpty()) {
//                        return null;
//                    }
//
//                    List<WebElement> webElements = productNamesInCart.stream()
//                            .map(ExtendedWebElement::getElement)
//                            .collect(Collectors.toList());
//
//                    return webElements.stream()
//                            .allMatch(WebElement::isDisplayed) ? webElements : null;
//                });

        // Better approach if the website is static - it works so I am using more readable option
        Assert.assertTrue(cartPage.getProductNamesInCartElement().stream()
                .allMatch(element -> element.getElement().isDisplayed()), "The cart is not loaded properly!");

        assertsInCart(driver, productName, price);

        deleteProductsInTheCartAndCheckIt(driver);
    }

    @Test
    public void testAddSingleProductPurchase() {
        WebDriver driver = getDriver();
        String username = R.CONFIG.get("username3");
        login(driver, username, R.CONFIG.get("passwordGlobal"));

        AllProductsPageBase allProductsPage = initPage(driver, AllProductsPageBase.class);

        List<ProductComponent> productList = new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("No products found on the page!")
                .until(driver1 -> {
                   List<ProductComponent> products = allProductsPage.getProductList();

                   logger.info("The size of list of products: " + (products == null ? "null" : products.size()));

                   if (products == null || products.isEmpty()) {
                       logger.info("Wait for loading a list of product");
                       return null;
                   }

                   boolean allDisplayed = products.stream()
                           .allMatch(element -> element.getElement().isDisplayed());

                    logger.info("If all of the product are displayed: " + allDisplayed);
                    return allDisplayed ? products : null;
                });

        boolean isProductListValid = productList != null && !productList.isEmpty();
        Assert.assertTrue(isProductListValid, "Product list is empty even after waiting!");

        int size = productList.size();

        logger.info("Final size of product list: " + size);

        int index = 3;

        if (index < size) {
            List<String> informations = addProductToCartByIndex(driver, 3);

            String price = informations.get(1);
            ProductPageBase productPage = initPage(driver, ProductPageBase.class);
            productPage.getNavigationBar().goToCart();

            PurchaseProduct(driver, price);
        } else {
            Assert.fail("Index is out of bounds: " + index + " for list of size: " + size + "!");
        }
    }


    @Test
    public void testPurchaseProductWithList() {
        WebDriver driver = getDriver();

        String username = R.CONFIG.get("username4");
        login(driver, username, R.CONFIG.get("passwordGlobal"));
        AllProductsPageBase allProductsPage = initPage(driver, AllProductsPageBase.class);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("No products found on the page!")
                .until(driver1 -> {
                    List<ProductComponent> productList = allProductsPage.getProductList();

                    logger.info("The size of list of product: " + (productList == null ? "null" : productList.size()));

                    if (productList == null || productList.isEmpty()) {
                        logger.info("Wait for loading a list of product");
                        return null;
                    }

                    boolean allDisplayed = productList.stream()
                            .allMatch(element -> element.getElement().isDisplayed());

                    logger.info("If all of the product are displayed: " + allDisplayed);
                    return allDisplayed ? productList : null;
                });


        List<String> allNamesOfProducts  = allProductsPage.getProductNames();
        int size = allNamesOfProducts .size();

        List<String> selectedProductNames = new ArrayList<>();
        List<String> pricesOfProducts = new ArrayList<>();
        List<Integer> indexes = List.of(0, 1, 5);

        for (int index : indexes) {
            if (index < size) {
                List<String> informations = addProductToCartByIndex(driver, index);

                selectedProductNames.add(allNamesOfProducts.get(index));
                pricesOfProducts.add(informations.get(1));
            } else {
                Assert.fail("Index " + index + " is out of bounds for product list size: " + size);
            }
        }

        Assert.assertEquals(pricesOfProducts.size(), indexes.size(), "Not all products were added to the cart!");

        allProductsPage.getNavigationBar().goToCart();

        CartPageBase cartPage = initPage(driver, CartPageBase.class);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The elements Product names in the cart were not found in 10 seconds!")
                .until(driver1 -> {
                    List<ExtendedWebElement> productNames = cartPage.getProductNamesInCartElement();

                    if (productNames == null || productNames.isEmpty()) {
                        logger.info("Waiting for product names to be loaded...");
                        return null;
                    }

                    boolean allDisplayed = productNames.stream()
                            .allMatch(element -> element.getElement().isDisplayed());

                    logger.info("Are all product names visible: " + allDisplayed);
                    return allDisplayed ? productNames : null;
                });

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The elements Product prices in the cart were not found in 10 seconds!")
                .until(driver1 -> {
                    List<ExtendedWebElement> productPrices = cartPage.getProductPricesInCartElement();

                    if (productPrices == null || productPrices.isEmpty()) {
                        logger.info("Waiting for product prices to be loaded...");
                        return null;
                    }

                    boolean allDisplayed = productPrices.stream()
                            .allMatch(element -> element.getElement().isDisplayed());

                    logger.info("Are all product prices visible: " + allDisplayed);
                    return allDisplayed ? productPrices : null;
                });



        List<String> cartProductNames = cartPage.getProductNamesInCart();
        List<String> cartProductPrices = cartPage.getProductPricesInCart();

        // Sort lists using stream
        selectedProductNames = selectedProductNames.stream().sorted().toList();
        cartProductNames = cartProductNames.stream().sorted().toList();
        pricesOfProducts = pricesOfProducts.stream().sorted().toList();
        cartProductPrices = cartProductPrices.stream().sorted().toList();

        // Validate that the cart matches the added products
        Assert.assertEquals(cartProductNames, selectedProductNames,
                "Product names in the cart do not match the selected products!");
        Assert.assertEquals(cartProductPrices, pricesOfProducts,
                "Product prices in the cart do not match the selected products!");

        // Validate total price
        String totalPrice = cartPage.getTotalPrice();
        int actualTotalPrice = Integer.parseInt(totalPrice);

        int expectedTotalPrice = cartProductPrices.stream()
                .mapToInt(Integer::parseInt)
                .sum();

        Assert.assertEquals(actualTotalPrice, expectedTotalPrice, "Total price mismatch!");

        PurchaseProduct(driver, totalPrice);
    }

    @Test
    public void testPurchaseAllProductsFromTheWebsite() {
        WebDriver driver = getDriver();

        String username = R.CONFIG.get("username5");
        login(driver, username, R.CONFIG.get("passwordGlobal"));

        AllProductsPageBase allProductsPage = initPage(driver, AllProductsPageBase.class);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("No products found on the page!")
                .until(driver1 -> {
                    List<ProductComponent> productList = allProductsPage.getProductList();

                    logger.info("The size of list of product: " + (productList == null ? "null" : productList.size()));

                    if (productList == null || productList.isEmpty()) {
                        logger.info("Wait for loading a list of product");
                        return null;
                    }

                    boolean allDisplayed = productList.stream()
                            .allMatch(element -> element.getElement().isDisplayed());

                    logger.info("If all of the product are displayed: " + allDisplayed);
                    return allDisplayed ? productList : null;
                });


        List<String> allNamesOfProducts  = allProductsPage.getProductNames();

        int size = allNamesOfProducts.size();
        List<String> selectedProductNames = new ArrayList<>();
        List<String> pricesOfProducts = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            List<String> informations = addProductToCartByIndex(driver, i);

            selectedProductNames.add(allNamesOfProducts.get(i));
            pricesOfProducts.add(informations.get(1));
        }

        Assert.assertEquals(pricesOfProducts.size(), size, "Not all products were added to the cart!");

        allProductsPage.getNavigationBar().goToCart();

        CartPageBase cartPage = initPage(driver, CartPageBase.class);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The elements Product names in the cart were not found in 10 seconds!")
                .until(driver1 -> {
                    List<ExtendedWebElement> productNames = cartPage.getProductNamesInCartElement();

                    if (productNames == null || productNames.isEmpty()) {
                        logger.info("Waiting for product names to be loaded...");
                        return null;
                    }

                    boolean allDisplayed = productNames.stream()
                            .allMatch(element -> element.getElement().isDisplayed());

                    logger.info("Are all product names visible: " + allDisplayed);
                    return allDisplayed ? productNames : null;
                });

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The elements Product prices in the cart were not found in 10 seconds!")
                .until(driver1 -> {
                    List<ExtendedWebElement> productPrices = cartPage.getProductPricesInCartElement();

                    if (productPrices == null || productPrices.isEmpty()) {
                        logger.info("Waiting for product prices to be loaded...");
                        return null;
                    }

                    boolean allDisplayed = productPrices.stream()
                            .allMatch(element -> element.getElement().isDisplayed());

                    logger.info("Are all product prices visible: " + allDisplayed);
                    return allDisplayed ? productPrices : null;
                });

        List<String> cartProductNames = cartPage.getProductNamesInCart();
        List<String> cartProductPrices = cartPage.getProductPricesInCart();

        // Sort lists using stream
        selectedProductNames = selectedProductNames.stream().sorted().toList();
        cartProductNames = cartProductNames.stream().sorted().toList();
        pricesOfProducts = pricesOfProducts.stream().sorted().toList();
        cartProductPrices = cartProductPrices.stream().sorted().toList();

        Assert.assertEquals(cartProductNames, selectedProductNames,
                "Product names in the cart do not match the selected products!");
        Assert.assertEquals(cartProductPrices, pricesOfProducts,
                "Product prices in the cart do not match the selected products!");

        String totalPrice = cartPage.getTotalPrice();
        int actualTotalPrice = Integer.parseInt(totalPrice);

        int expectedTotalPrice = cartProductPrices.stream()
                .mapToInt(Integer::parseInt)
                .sum();

        Assert.assertEquals(actualTotalPrice, expectedTotalPrice, "Total price mismatch!");

        PurchaseProduct(driver, totalPrice);
    }

    @Test
    public void createNewUser() {
        WebDriver driver = getDriver();

        LoginPageBase loginPage = initPage(driver, LoginPageBase.class);
        loginPage.open();

        String newUserName = RandomStringUtils.random(15, true, true);
        String newPassword = RandomStringUtils.random(15, true, true);

        AllProductsPageBase allProductsPage = initPage(driver, AllProductsPageBase.class);
        allProductsPage.getNavigationBar().goToSignIn();

        allProductsPage.SignIn(newUserName, newPassword);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The alert was not found in 10 seconds!")
                .until(ExpectedConditions.alertIsPresent());


        driver.switchTo().alert().accept();

//        new WebDriverWait(driver, Duration.ofSeconds(10))
//                .pollingEvery(Duration.ofMillis(500))
//                .withMessage("The login button was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(loginPage.getNavigationBar().getLoginButton().getElement()));

        Assert.assertTrue(loginPage.getNavigationBar().getLoginButton().isVisible(), "The login button was not found in 10 seconds!");

        loginPage.getNavigationBar().goToLogin();

//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.withMessage("The element Username field was not found in 10 seconds!")
//                .until(ExpectedConditions.visibilityOf(loginPage.getUsernameField()));

        Assert.assertTrue(loginPage.getUsernameField().isVisible(), "The element Username field was not found in 10 seconds!");

        loginPage.login(newUserName, newPassword);

        assertLogin(driver, newUserName);
    }

    @Test
    public void testFooterAndNavigationBarVisibility() {
        WebDriver driver = getDriver();

        AllProductsPageBase allProductsPage = initPage(driver, AllProductsPageBase.class);
        allProductsPage.open();
        Assert.assertTrue(allProductsPage.getFooter().isUIObjectPresent(), "Footer is not visible on AllProductsPage!");
        Assert.assertTrue(allProductsPage.getNavigationBar().isUIObjectPresent(), "Navigation is not visible on AllProductsPage!");
        allProductsPage.getNavigationBar().goToCart();

        CartPageBase cartPage = initPage(driver, CartPageBase.class);
        Assert.assertTrue(cartPage.getFooter().isUIObjectPresent(), "Footer is not visible on CartPage!");
        Assert.assertTrue(cartPage.getNavigationBar().isUIObjectPresent(), "Navigation bar is not visible on AllProductsPage!");
        cartPage.getNavigationBar().goToHome();

        allProductsPage.selectProductByIndex(0);
        ProductPageBase productPage = initPage(driver, ProductPageBase.class);
        Assert.assertTrue(productPage.getFooter().isUIObjectPresent(), "Footer is not visible on ProductPage!");
        Assert.assertTrue(productPage.getNavigationBar().isUIObjectPresent(), "Navigation bar is not visible on AllProductsPage!");
    }


    public void PurchaseProduct(WebDriver driver, String totalPrice) {

        CartPageBase cartPage = initPage(driver, CartPageBase.class);
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
        AllProductsPageBase allProductsPage = initPage(driver, AllProductsPageBase.class);

        new WebDriverWait(driver, Duration.ofSeconds(20))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The element at index " + index + " was not found or not visible in 20 seconds!")
                .until(driver1 -> {
                    // Refresh the list of elements from DOM
                    List<ProductComponent> productList = allProductsPage.getProductList();

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
                    WebElement element = productList.get(index).getElement();

                    // Check if the element is displayed
                    boolean isDisplayed = element.isDisplayed();
                    logger.info("Element at index " + index + " displayed: " + isDisplayed);
                    return isDisplayed ? element : null; // Wait if it's not displayed
                });

        // I cannot change the webdriverwait which I have above - I will get error
        List<ProductComponent> productList = allProductsPage.getProductList();
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
        CartPageBase cartPage = initPage(driver, CartPageBase.class);

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

        System.out.println("Nazwa produktu to: " +  cartPage.getProductNamesInCart().getFirst());
        logger.info("The product name is: " +  cartPage.getProductNamesInCart().getFirst());
        Assert.assertEquals(cartPage.getProductNamesInCart().getFirst(), productName,
                "Product name in the cart does not match the selected product!");
        Assert.assertTrue(price.contains(cartPage.getProductPricesInCart().getFirst()),
                "Product price in the cart does not match the selected product!");
    }

    public void deleteProductsInTheCartAndCheckIt(WebDriver driver) {
        CartPageBase cartPage = initPage(driver, CartPageBase.class);
        cartPage.deleteAllItems();
        Assert.assertTrue(cartPage.getCartItems().isEmpty(),
                "Cart is not empty after removing all items");
    }
}