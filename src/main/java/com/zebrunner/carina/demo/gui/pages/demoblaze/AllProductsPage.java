package com.zebrunner.carina.demo.gui.pages.demoblaze;

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

    @FindBy(xpath = "//a[@class='hrefch']")
    private List<ExtendedWebElement> productList;

    @FindBy(css = "#navbarExample > ul > li.nav-item.active > a")
    private ExtendedWebElement Home;

    @FindBy(xpath = "//a[@id='cartur']")
    private ExtendedWebElement cartButton;

    @FindBy(xpath = "//a[@id='logout2']")
    private ExtendedWebElement logoutButton;

    public AllProductsPage(WebDriver driver) {
        super(driver);
    }

    public void selectProductByIndex(int index) {
//        pause(2);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement product = new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("Product is not visible in the list!")
                .until(ExpectedConditions.visibilityOf(productList.get(index)));
//        // Re-fetch the product list to avoid stale references
//        List<WebElement> updatedProductList = wait.until(
//                ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//a[@class='hrefch']")));
//
//        if (index >= updatedProductList.size()) {
//            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
//        }
//
//        WebElement product = wait.withMessage("Product is not visible in the list!")
//                .until(ExpectedConditions.elementToBeClickable(updatedProductList.get(index)));

        product.click();
    }

    public List<String> getProductNames() {
//        pause(2);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .withMessage("Product is not visible in the list!")
                .until(ExpectedConditions.visibilityOf(productList.getFirst()));

        return productList.stream()
                .map(WebElement::getText)
                .toList();
    }

    public void goToHome() {
        Home.click();
    }


    public void goToCart() {
        cartButton.click();
    }
}
