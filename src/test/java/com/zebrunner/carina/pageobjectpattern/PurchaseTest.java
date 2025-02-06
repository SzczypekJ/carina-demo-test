package com.zebrunner.carina.pageobjectpattern;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.demo.gui.pages.common.demoblaze.*;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.CartItemComponentBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.ProductComponentBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.ProductComponentDesktop;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.zebrunner.carina.utils.R;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PurchaseTest extends AbstractTest implements IAbstractTest {

    public static final Logger logger = LoggerFactory.getLogger(PurchaseTest.class);

    @Test
    public void testAddSingleProductPurchase() {
        WebDriver driver = getDriver();

        // Log in
        loginUser("username3");

        AllProductsPageBase<? extends ProductComponentBase> allProductsPage = initPage(driver, AllProductsPageBase.class);

        // Wait for loading the products on the page
        waitForProductsOnMainPage(driver, allProductsPage);

        // Get the product list from the page
        List<? extends ProductComponentBase> productList = allProductsPage.getProductList();

        int size = productList.size();

        logger.info("Final size of product list: " + size);

        int index = Integer.parseInt(R.CONFIG.get("index"));

        if (index < size) {
            List<String> informations = addProductToCartByIndex(driver, 3);

            String price = informations.get(1);
            ProductPageBase productPage = initPage(driver, ProductPageBase.class);
            productPage.getNavigationBar().goToCart();

            CartPageBase<? extends CartItemComponentBase> cartPage = initPage(driver, CartPageBase.class);
            String totalPrice = cartPage.getTotalPrice();
            List<String> cartProductPrices = cartPage.getProductPricesInCart();

            // Validate total price
            validateTotalPrice(cartPage, cartProductPrices, totalPrice);

            // Buy products
            purchaseProduct(driver, price);
        } else {
            Assert.fail("Index is out of bounds: " + index + " for list of size: " + size + "!");
        }
    }


    @Test
    public void testPurchaseProductWithList() {
        WebDriver driver = getDriver();

        // Log in
        loginUser("username4");

        AllProductsPageBase<? extends ProductComponentBase> allProductsPage = initPage(driver, AllProductsPageBase.class);
        // Wait for loading the products on the page
        waitForProductsOnMainPage(driver, allProductsPage);

        List<String> allNamesOfProducts  = allProductsPage.getProductNames();

        int size = allNamesOfProducts.size();

        List<Integer> indexes = Arrays.stream(R.CONFIG.get("indexes").split(","))
                .map(Integer::parseInt)
                .toList();

        Pair<List<String>, List<String>> result = addMultipleProductsToCart(driver, indexes, allProductsPage, size);

        List<String> selectedProductNames = result.getLeft();
        List<String> selectedProductPrices = result.getRight();

        Assert.assertEquals(selectedProductPrices.size(), indexes.size(), "Not all products were added to the cart!");

        allProductsPage.getNavigationBar().goToCart();

        CartPageBase<? extends CartItemComponentBase> cartPage = initPage(driver, CartPageBase.class);

        // Waits for load product names and prices on the cart page
        waitForProductsOnCartPage(driver, cartPage);

        List<String> cartProductNames = cartPage.getProductNamesInCart();
        List<String> cartProductPrices = cartPage.getProductPricesInCart();

        // Sort and validate the Cart content
        validateCartContents(cartPage, selectedProductNames, cartProductNames, selectedProductPrices, cartProductPrices);

        String totalPrice = cartPage.getTotalPrice();

        // Validate total price
        validateTotalPrice(cartPage, cartProductPrices, totalPrice);

        // Buy products
        purchaseProduct(driver, totalPrice);
    }

    @Test
    public void testPurchaseAllProductsFromTheWebsite() {
        WebDriver driver = getDriver();

        // Log in
        loginUser("username5");

        AllProductsPageBase<? extends ProductComponentBase> allProductsPage = initPage(driver, AllProductsPageBase.class);

        // Wait for loading the products on the page
        waitForProductsOnMainPage(driver, allProductsPage);

        List<String> allNamesOfProducts  = allProductsPage.getProductNames();

        int size = allNamesOfProducts.size();
        List<String> selectedProductNames = new ArrayList<>();
        List<String> selectedProductPrices = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            List<String> informations = addProductToCartByIndex(driver, i);

            selectedProductNames.add(allNamesOfProducts.get(i));
            selectedProductPrices.add(informations.get(1));
        }

        Assert.assertEquals(selectedProductPrices.size(), size, "Not all products were added to the cart!");

        allProductsPage.getNavigationBar().goToCart();

        CartPageBase<? extends CartItemComponentBase> cartPage = initPage(driver, CartPageBase.class);

        // Waits for load product names and prices on the cart page
        waitForProductsOnCartPage(driver, cartPage);

        List<String> cartProductNames = cartPage.getProductNamesInCart();
        List<String> cartProductPrices = cartPage.getProductPricesInCart();

        // Sort and validate the Cart content
        validateCartContents(cartPage, selectedProductNames, cartProductNames, selectedProductPrices, cartProductPrices);

        String totalPrice = cartPage.getTotalPrice();

        // Validate total price
        validateTotalPrice(cartPage, cartProductPrices, totalPrice);

        // Buy products
        purchaseProduct(driver, totalPrice);
    }

}