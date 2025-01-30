package com.zebrunner.carina.pageobjectpattern;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.demo.gui.pages.common.demoblaze.CartPageBase;
import com.zebrunner.carina.demo.gui.pages.common.demoblaze.ProductPageBase;
import com.zebrunner.carina.demo.utils.TestingMethods;
import com.zebrunner.carina.utils.R;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends AbstractTest implements IAbstractTest {

    public static final Logger logger = LoggerFactory.getLogger(CartTest.class);

    @Test
    public void testAddProductToCart() {
        WebDriver driver = getDriver();

        // Log in - the helpful method to log in - the reuse of code
        loginUser("username2");

        TestingMethods testingMethods = new TestingMethods();
        // Adds product to the cart - helpful method which reduce amount of code
        List<String> informations = testingMethods.addProductToCartByIndex(driver, 0);
        Assert.assertNotNull(informations, "Product information should not be null!");
        Assert.assertFalse(informations.isEmpty(), "Product information should not be empty!");

        String productName  = informations.get(0);
        logger.info("productName is:" + productName);
        String price = informations.get(1);
        logger.info("price is:" + price);

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

        // Check if everything in the Cart is okey
        testingMethods.assertsInCart(driver, productName, price);

        // Delete the products from the cart
        testingMethods.deleteProductsInTheCartAndCheckIt(driver);
    }
}
