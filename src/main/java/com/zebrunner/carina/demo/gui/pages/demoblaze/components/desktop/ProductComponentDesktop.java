package com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.ProductComponentBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = ProductComponentBase.class)
public class ProductComponentDesktop extends ProductComponentBase {

    public ProductComponentDesktop(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @Override
    public String getProductName() {
        return productName.getText();
    }

    @Override
    public String getProductPrice() {
        return productPrice.getText();
    }

    @Override
    public String getProductLink() {
        return productLink.getText();
    }

    @Override
    public void clickOnProductLink() {
        productLink.click();
    }
}
