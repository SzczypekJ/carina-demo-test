package com.zebrunner.carina.demo.gui.pages.demoblaze;

import com.zebrunner.carina.demo.gui.pages.common.AllProductsPageBase;
import com.zebrunner.carina.demo.gui.pages.common.LoginPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.Footer;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.NavigationBar;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.ProductComponent;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = AllProductsPageBase.class)
public class AllProductsPage extends AllProductsPageBase {

    public AllProductsPage(WebDriver driver) {
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
    public void selectProductByIndex(int index) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("Product is not visible in the list!")
                .until(ExpectedConditions.visibilityOf(productList.get(index).getRootExtendedElement().getElement()));

        productList.get(index).clickOnProductLink();
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
                .map(ProductComponent::getProductName)
                .toList();
    }

    @Override
    public void SignIn(String username, String password) {
        signInUsername.type(username);
        signInPassword.type(password);
        registerButton.click();
    }

    @Override
    public List<ProductComponent> getProductList() {
        return productList;
    }
}
