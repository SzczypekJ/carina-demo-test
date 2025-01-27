package com.zebrunner.carina.demo.gui.pages.demoblaze.desktop;

import com.zebrunner.carina.demo.gui.pages.common.demoblaze.ProductPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.FooterDesktop;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.NavigationBarDesktop;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ProductPageBase.class)
public class ProductPageDesktop extends ProductPageBase {

    @FindBy(xpath = "//nav")
    private NavigationBarDesktop navigationBar;

    @FindBy(id = "footc")
    private FooterDesktop footer;


    public ProductPageDesktop(WebDriver driver) {
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