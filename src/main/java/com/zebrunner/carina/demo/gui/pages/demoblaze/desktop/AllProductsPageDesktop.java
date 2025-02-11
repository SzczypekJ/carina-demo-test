package com.zebrunner.carina.demo.gui.pages.demoblaze.desktop;

import com.zebrunner.carina.demo.gui.pages.common.demoblaze.AllProductsPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.FooterDesktop;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.NavigationBarDesktop;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.ProductComponentDesktop;
import com.zebrunner.carina.utils.factory.DeviceType;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AllProductsPageBase.class)
@Getter
public class AllProductsPageDesktop extends AllProductsPageBase<ProductComponentDesktop> {

    @FindBy(xpath = "//nav")
    private NavigationBarDesktop navigationBar;

    @FindBy(id = "footc")
    private FooterDesktop footer;

    @FindBy(xpath = "//div[@class='card h-100']")
    private List<ProductComponentDesktop> productList;

    public AllProductsPageDesktop(WebDriver driver) {
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
                .map(ProductComponentDesktop::getProductName)
                .toList();
    }

    @Override
    public List<ProductComponentDesktop> getProductList() {
        return productList;
    }
}
