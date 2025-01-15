package com.zebrunner.carina.pageobjectpattern;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.demo.gui.pages.demoblaze.*;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.ProductComponent;
import com.zebrunner.carina.demo.utils.Person;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductStoreTest implements IAbstractTest {

    @Test
    public void testLogin() {
        WebDriver driver = getDriver();
        login(driver, "jakubszczypek", "1234");
        assertLogin(driver, "jakubszczypek");
    }

    @Test
    public void testAddProductToCart() {
        WebDriver driver = getDriver();
        // Log in - the helpful method to log in - the reuse of code
        login(driver, "jakubszczypek2", "1234");

        // Adds product to the cart - helpful method which reduce amount of code
        List<String> informations = addProductToCartByIndex(driver, 0);
        Assert.assertNotNull(informations, "Product information should not be null!");
        Assert.assertFalse(informations.isEmpty(), "Product information should not be empty!");

        String productName  = informations.get(0);
        System.out.println("productName to:" + productName);
        String price = informations.get(1);

        ProductPage productPage = new ProductPage(driver);
        productPage.getNavigationBar().goToCart();

        CartPage cartPage = new CartPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The cart is not loaded properly!")
                .until(driver1 -> {
                    List<ExtendedWebElement> productNamesInCart = cartPage.getProductNamesInCartElement();

                    if (productNamesInCart.isEmpty()) {
                        return null;
                    }

                    List<WebElement> webElements = productNamesInCart.stream()
                            .map(ExtendedWebElement::getElement)
                            .collect(Collectors.toList());

                    return webElements.stream()
                            .allMatch(WebElement::isDisplayed) ? webElements : null;
                });

        assertsInCart(driver, productName, price);

        deleteProductsInTheCartAndCheckIt(driver);
    }

    @Test
    public void testAddSingleProductPurchase() {
        WebDriver driver = getDriver();
        login(driver, "jakubszczypek3", "1234");

        AllProductsPage allProductsPage = new AllProductsPage(driver);

        List<ProductComponent> productList = new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("No products found on the page!")
                .until(driver1 -> {
                   List<ProductComponent> products = allProductsPage.getProductList();

                   System.out.println("The size of list of products: " + (products == null ? "null" : products.size()));

                   if (products == null || products.isEmpty()) {
                       System.out.println("Wait for loading a list of product");
                       return null;
                   }

                   boolean allDisplayed = products.stream()
                           .allMatch(element -> element.getElement().isDisplayed());

                    System.out.println("If all of the product are displayed: " + allDisplayed);
                    return allDisplayed ? products : null;
                });

        if (productList == null || productList.isEmpty()) {
            throw new RuntimeException("Product list is empty even after waiting!");
        } // Use assert instead

        int size = productList.size();

        // change to logger
        System.out.println("Final size of product list: " + size);

        int index = 3;

        if (index < size) {
            List<String> informations = addProductToCartByIndex(driver, 3);

            String price = informations.get(1);
            ProductPage productPage = new ProductPage(driver);
            productPage.getNavigationBar().goToCart();

            PurchaseProduct(driver, price);
        } else {
            throw new IndexOutOfBoundsException("Index is out of bounds: " + index + " for list of size: " + size + "!"); //Change to assertion
        }
    }


    @Test
    public void testPurchaseProductWithList() throws InterruptedException {
        WebDriver driver = getDriver();

        login(driver, "jakubszczypek4", "1234");
        AllProductsPage allProductsPage = new AllProductsPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("No products found on the page!")
                .until(driver1 -> {
                    List<ProductComponent> productList = allProductsPage.getProductList();

                    System.out.println("The size of list of product: " + (productList == null ? "null" : productList.size()));

                    if (productList == null || productList.isEmpty()) {
                        System.out.println("Wait for loading a list of product");
                        return null;
                    }

                    boolean allDisplayed = productList.stream()
                            .allMatch(element -> element.getElement().isDisplayed());

                    System.out.println("If all of the product are displayed: " + allDisplayed);
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
                System.out.println("Index " + index + " is out of bounds for list of size: " + size);
            }
        }

        if (pricesOfProducts.size() < indexes.size()) {
            throw new IllegalStateException("Not all products were added to the cart!");
        }

        allProductsPage.getNavigationBar().goToCart();

        CartPage cartPage = new CartPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The elements Product names in the cart were not found in 10 seconds!")
                .until(driver1 -> {
                    List<ExtendedWebElement> productNames = cartPage.getProductNamesInCartElement();

                    if (productNames == null || productNames.isEmpty()) {
                        System.out.println("Waiting for product names to be loaded...");
                        return null;
                    }

                    boolean allDisplayed = productNames.stream()
                            .allMatch(element -> element.getElement().isDisplayed());
                    System.out.println("Are all product names visible: " + allDisplayed);
                    return allDisplayed ? productNames : null;
                });

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The elements Product prices in the cart were not found in 10 seconds!")
                .until(driver1 -> {
                    List<ExtendedWebElement> productPrices = cartPage.getProductPricesInCartElement();

                    if (productPrices == null || productPrices.isEmpty()) {
                        System.out.println("Waiting for product prices to be loaded...");
                        return null;
                    }

                    boolean allDisplayed = productPrices.stream()
                            .allMatch(element -> element.getElement().isDisplayed());
                    System.out.println("Are all product prices visible: " + allDisplayed);
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
    public void testPurchaseAllProductsFromTheWebsite() {
        WebDriver driver = getDriver();

        login(driver, "jakubszczypek5", "1234");

        AllProductsPage allProductsPage = new AllProductsPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("No products found on the page!")
                .until(driver1 -> {
                    List<ProductComponent> productList = allProductsPage.getProductList();

                    System.out.println("The size of list of product: " + (productList == null ? "null" : productList.size()));

                    if (productList == null || productList.isEmpty()) {
                        System.out.println("Wait for loading a list of product");
                        return null;
                    }

                    boolean allDisplayed = productList.stream()
                            .allMatch(element -> element.getElement().isDisplayed());

                    System.out.println("If all of the product are displayed: " + allDisplayed);
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

        if (pricesOfProducts.size() < size) {
            throw new IllegalStateException("Not all products were added to the cart!");
        }

        allProductsPage.getNavigationBar().goToCart();

        CartPage cartPage = new CartPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The elements Product names in the cart were not found in 10 seconds!")
                .until(driver1 -> {
                    List<ExtendedWebElement> productNames = cartPage.getProductNamesInCartElement();

                    if (productNames == null || productNames.isEmpty()) {
                        System.out.println("Waiting for product names to be loaded...");
                        return null;
                    }

                    boolean allDisplayed = productNames.stream()
                            .allMatch(element -> element.getElement().isDisplayed());
                    System.out.println("Are all product names visible: " + allDisplayed);
                    return allDisplayed ? productNames : null;
                });

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The elements Product prices in the cart were not found in 10 seconds!")
                .until(driver1 -> {
                    List<ExtendedWebElement> productPrices = cartPage.getProductPricesInCartElement();

                    if (productPrices == null || productPrices.isEmpty()) {
                        System.out.println("Waiting for product prices to be loaded...");
                        return null;
                    }

                    boolean allDisplayed = productPrices.stream()
                            .allMatch(element -> element.getElement().isDisplayed());
                    System.out.println("Are all product prices visible: " + allDisplayed);
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

        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();


        String newUserName = RandomStringUtils.random(15, true, true);
        String newPassword = RandomStringUtils.random(15, true, true);

        AllProductsPage allProductsPage = new AllProductsPage(driver);
        allProductsPage.getNavigationBar().goToSignIn();

        allProductsPage.SignIn(newUserName, newPassword);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The alert was not found in 10 seconds!")
                .until(ExpectedConditions.alertIsPresent());


        driver.switchTo().alert().accept();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The login button was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(loginPage.getNavigationBar().getLoginButton().getElement()));

        loginPage.getNavigationBar().goToLogin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.withMessage("The element Username field was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(loginPage.getUsernameField()));

        loginPage.login(newUserName, newPassword);

        assertLogin(driver, newUserName);
    }


    public void PurchaseProduct(WebDriver driver, String totalPrice) {
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        CartPage cartPage = new CartPage(driver);
        cartPage.placeOrder();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.withMessage("The element Name field was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(checkoutPage.getNameField()));

        wait.withMessage("The element Country field was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(checkoutPage.getCountryField()));

        wait.withMessage("The element City field was not found in 10 seconds!").until(ExpectedConditions.visibilityOf(checkoutPage.getCityField()));

        wait.withMessage("The element Card field was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(checkoutPage.getCardField()));

        wait.withMessage("The element Month field was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(checkoutPage.getMonthField()));

        wait.withMessage("The element Year field was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(checkoutPage.getYearField()));

        // Filling the purchase details
        Person person = new Person("Jakub", "Poland", "Cracow", "411111111111", "December", "2025");

        checkoutPage.fillCheckoutDetails(person);

        wait.withMessage("The element Purchase button was not found in 10 seconds!")
                .until(ExpectedConditions.elementToBeClickable(checkoutPage.getPurchaseButton()));

        checkoutPage.completePurchase();
        wait.withMessage("The element Purchase details was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(checkoutPage.getPurchaseDetailsElement()));

        // Check if the details of purchase are okay
        assertPurchaseDetails(driver, totalPrice, person.getCreditCard(), person.getName());

        // Confirm the purchase
        checkoutPage.confirmPurchase();
    }

    public void assertPurchaseDetails(WebDriver driver, String totalPrice, String creditCard, String name) {
        CheckoutPage checkoutPage = new CheckoutPage(driver);

        // Validate the purchase summary details
        String purchaseDetails = checkoutPage.getPurchaseDetails();
        Assert.assertTrue(purchaseDetails.contains("Id:"), "Purchase details missing ID!");
        Assert.assertTrue(purchaseDetails.contains("Amount: " + totalPrice), "Purchase amount mismatch!");
        Assert.assertTrue(purchaseDetails.contains("Card Number: " + creditCard), "Credit card mismatch!");
        Assert.assertTrue(purchaseDetails.contains("Name: " + name), "Name mismatch in purchase details!");

        System.out.println("Test passed: Purchase details are correct!");
    }

    public void login(WebDriver driver, String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.getNavigationBar().goToLogin();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.withMessage("The element Username field was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(loginPage.getUsernameField()));

        loginPage.login(username, password);
    }

    public void assertLogin(WebDriver driver, String username) {
        LoginPage loginPage = new LoginPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The element Welcome text was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(loginPage.getWelcomeTextElement()));

        Assert.assertEquals(loginPage.getWelcomeText(), "Welcome " + username,
                "Login failed or welcome message is incorrect!");
    }

    public List<String> addProductToCartByIndex(WebDriver driver, int index)  {
        AllProductsPage allProductsPage = new AllProductsPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(20)) // Zwiększony timeout
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The element at index " + index + " was not found or not visible in 20 seconds!")
                .until(driver1 -> {
                    // Refresh the list of elements from DOM
                    List<ProductComponent> productList = allProductsPage.getProductList();

                    // Check the size of product List
                    System.out.println("Size of product list: " + (productList == null ? "null" : productList.size()));

                    // Check if the list is loaded
                    if (productList == null || productList.isEmpty()) {
                        System.out.println("Waiting for loaded the list of product");
                        return null; // wait if not loaded
                    }

                    // Check if the index is in the bounds
                    if (index >= productList.size()) {
                        System.out.println("Index " + index + " out of bounds for product list size " + productList.size());
                        throw new IndexOutOfBoundsException(
                                "Index " + index + " out of bounds for product list size " + productList.size());
                    }

                    // Get the element specify by the index
                    WebElement element = productList.get(index).getElement();

                    // Check if the element is displayed
                    boolean isDisplayed = element.isDisplayed();
                    System.out.println("Element na indeksie " + index + " widoczny: " + isDisplayed);
                    return isDisplayed ? element : null; // Wait if it's not displayed
                });

        ProductPage productPage = new ProductPage(driver);
        String productName = allProductsPage.getProductList().get(index).getText().split("\n")[0];
        allProductsPage.selectProductByIndex(index);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The element Product price was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(productPage.getProductPriceElement().getElement()));

        String price = productPage.getProductPrice();
        productPage.addToCart();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The alert was not found in 10 seconds!")
                .until(ExpectedConditions.alertIsPresent());


        driver.switchTo().alert().accept();

        allProductsPage.getNavigationBar().goToHome();

        List<String> informations = new ArrayList<>();
        informations.add(productName);
        informations.add(price);

        return informations;
    }

    public void assertsInCart(WebDriver driver, String productName, String price) {
        CartPage cartPage = new CartPage(driver);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The element Product name in the cart was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(cartPage.getProductNameInTheCartElement()));

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("The element Product price in the cart was not found in 10 seconds!")
                .until(ExpectedConditions.visibilityOf(cartPage.getProductPriceInTheCartElement()));

        System.out.println("Nazwa produktu to: " +  cartPage.getProductNamesInCart().getFirst());
        Assert.assertEquals(cartPage.getProductNamesInCart().getFirst(), productName,
                "Product name in the cart does not match the selected product!");
        Assert.assertTrue(price.contains(cartPage.getProductPricesInCart().getFirst()),
                "Product price in the cart does not match the selected product!");
    }

    public void deleteProductsInTheCartAndCheckIt(WebDriver driver) {
        CartPage cartPage = new CartPage(driver);
        cartPage.deleteAllItems();
        Assert.assertTrue(cartPage.getCartItems().isEmpty(),
                "Cart is not empty after removing all items");
    }
}