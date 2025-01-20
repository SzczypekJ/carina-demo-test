package com.zebrunner.carina.demo.gui.pages.demoblaze;

import com.zebrunner.carina.demo.gui.pages.common.LoginPageBase;
import com.zebrunner.carina.demo.gui.pages.common.ProductPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.Footer;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.NavigationBar;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ProductPageBase.class)
public class ProductPage extends ProductPageBase {


    public ProductPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public NavigationBar getNavigationBar() {
        return navigationBar;
    }

    @Override
    public Footer getFooter() {
        return footer;
    }

    @Override
    public String getProductPrice() {
        String rawPrice = priceContainer.getText();
        return rawPrice.replaceAll("[^0-9]", "");
    }

    @Override
    public void addToCart() {
        addToCartButton.click();
    }

    @Override
    public ExtendedWebElement getProductPriceElement() {
        return priceContainer;
    }
}