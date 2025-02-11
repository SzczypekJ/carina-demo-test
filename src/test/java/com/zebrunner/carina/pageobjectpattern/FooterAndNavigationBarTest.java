package com.zebrunner.carina.pageobjectpattern;

import com.zebrunner.carina.core.IAbstractTest;
import com.zebrunner.carina.demo.gui.pages.common.demoblaze.AllProductsPageBase;
import com.zebrunner.carina.demo.gui.pages.common.demoblaze.CartPageBase;
import com.zebrunner.carina.demo.gui.pages.common.demoblaze.ProductPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.CartItemComponentBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.ProductComponentBase;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FooterAndNavigationBarTest extends AbstractTest implements IAbstractTest {

    @Test
    public void testFooterAndNavigationBarVisibility() {
        WebDriver driver = getDriver();

        AllProductsPageBase<? extends ProductComponentBase> allProductsPage = initPage(driver, AllProductsPageBase.class);
        allProductsPage.open();
        Assert.assertTrue(allProductsPage.getFooter().isUIObjectPresent(), "Footer is not visible on AllProductsPage!");
        Assert.assertTrue(allProductsPage.getNavigationBar().isUIObjectPresent(), "Navigation is not visible on AllProductsPage!");
        allProductsPage.getNavigationBar().goToCart();

        CartPageBase<? extends CartItemComponentBase> cartPage = initPage(driver, CartPageBase.class);
        Assert.assertTrue(cartPage.getFooter().isUIObjectPresent(), "Footer is not visible on CartPage!");
        Assert.assertTrue(cartPage.getNavigationBar().isUIObjectPresent(), "Navigation bar is not visible on AllProductsPage!");
        cartPage.getNavigationBar().goToHome();

        waitForProductsOnMainPage(driver, allProductsPage);
        allProductsPage.selectProductByIndex(0);
        ProductPageBase productPage = initPage(driver, ProductPageBase.class);
        Assert.assertTrue(productPage.getFooter().isUIObjectPresent(), "Footer is not visible on ProductPage!");
        Assert.assertTrue(productPage.getNavigationBar().isUIObjectPresent(), "Navigation bar is not visible on AllProductsPage!");
    }
}
