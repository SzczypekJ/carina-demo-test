package com.zebrunner.carina.demo.gui.pages.demoblaze.android;

import com.zebrunner.carina.demo.gui.pages.common.demoblaze.AllProductsPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.FooterMobile;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.NavigationBarMobile;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.ProductComponentMobile;
import com.zebrunner.carina.utils.factory.DeviceType;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = AllProductsPageBase.class)
@Getter
public class AllProductsPageAndroid extends AllProductsPageBase<ProductComponentMobile> {

    @FindBy(xpath = "//nav")
    private NavigationBarMobile navigationBar;

    @FindBy(id = "footc")
    private FooterMobile footer;

    @FindBy(xpath = "//div[@class='card h-100']")
    private List<ProductComponentMobile> productList;

    public AllProductsPageAndroid(WebDriver driver) {
        super(driver);
    }

    @Override
    public NavigationBarMobile getNavigationBar() {
        return navigationBar;
    }

    @Override
    public FooterMobile getFooter() {
        return footer;
    }

    @Override
    public List<String> getProductNames() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("Product is not visible in the list!")
                .until(ExpectedConditions.visibilityOf(productList.getFirst()));

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("Product is not visible in the list!")
                .until(ExpectedConditions.visibilityOf(productList.getFirst().getRootExtendedElement().getElement()));


        return productList.stream()
                .map(ProductComponentMobile::getProductName)
                .toList();
    }


    @Override
    public List<ProductComponentMobile> getProductList() {
        return productList;
    }
}
