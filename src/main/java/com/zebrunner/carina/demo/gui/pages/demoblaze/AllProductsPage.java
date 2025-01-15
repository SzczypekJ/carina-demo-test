package com.zebrunner.carina.demo.gui.pages.demoblaze;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.NavigationBar;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.ProductComponent;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@Getter
public class AllProductsPage extends AbstractPage {

    @FindBy(xpath = "//div[@class='card h-100']")
    private List<ProductComponent> productList;

    @FindBy(css = "#sign-username")
    private ExtendedWebElement signInUsername;

    @FindBy(css = "#sign-password")
    private ExtendedWebElement signInPassword;

    @FindBy(xpath = "//button[@onclick='register()']")
    private ExtendedWebElement registerButton;

    @FindBy(xpath = "//nav")
    private NavigationBar navigationBar;

    public AllProductsPage(WebDriver driver) {
        super(driver);
    }

    public void selectProductByIndex(int index) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("Product is not visible in the list!")
                .until(ExpectedConditions.visibilityOf(productList.get(index).getRootExtendedElement().getElement()));

        productList.get(index).clickOnProductLink();
    }

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

    public void SignIn(String username, String password) {
        signInUsername.type(username);
        signInPassword.type(password);
        registerButton.click();
    }
}
