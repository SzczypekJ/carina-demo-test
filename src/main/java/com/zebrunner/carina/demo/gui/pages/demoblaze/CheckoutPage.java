package com.zebrunner.carina.demo.gui.pages.demoblaze;

import com.zebrunner.carina.demo.gui.pages.common.CartPageBase;
import com.zebrunner.carina.demo.gui.pages.common.CheckoutPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.Footer;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.NavigationBar;
import com.zebrunner.carina.demo.utils.Person;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CheckoutPageBase.class)
@Getter
public class CheckoutPage extends CheckoutPageBase {

    @FindBy(xpath = "//input[@id='name']")
    private ExtendedWebElement nameField;

    @FindBy(xpath = "//input[@id='country']")
    private ExtendedWebElement countryField;

    @FindBy(xpath = "//input[@id='city']")
    private ExtendedWebElement cityField;

    @FindBy(xpath = "//input[@id='card']")
    private ExtendedWebElement cardField;

    @FindBy(xpath = "//input[@id='month']")
    private ExtendedWebElement monthField;

    @FindBy(xpath = "//input[@id='year']")
    private ExtendedWebElement yearField;

    @FindBy(xpath = "//button[@onclick='purchaseOrder()']")
    private ExtendedWebElement purchaseButton;

    @FindBy(css = "p.lead.text-muted")
    private ExtendedWebElement purchaseDetails;

    @FindBy(xpath = "//button[@class='confirm btn btn-lg btn-primary']")
    private ExtendedWebElement confirmButton;

    @FindBy(xpath = "//nav")
    private NavigationBar navigationBar;

    @FindBy(id = "footc")
    private Footer footer;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void fillCheckoutDetails(Person person) {
        nameField.type(person.getName());
        countryField.type(person.getCountry());
        cityField.type(person.getCity());
        cardField.type(person.getCreditCard());
        monthField.type(person.getMonth());
        yearField.type(person.getYear());
    }

    @Override
    public void completePurchase() {
        purchaseButton.click();
    }

    @Override
    public String getPurchaseDetails() {
        return purchaseDetails.getText();
    }

    @Override
    public ExtendedWebElement getPurchaseDetailsElement() {
        return purchaseDetails;
    }

    @Override
    public void confirmPurchase() {
    	confirmButton.click();
    }

    @Override
    public NavigationBar getNavigationBar() {
        return navigationBar;
    }

    @Override
    public Footer getFooter() {
        return footer;
    }
}
