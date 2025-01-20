package com.zebrunner.carina.demo.gui.pages.common;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.Footer;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.NavigationBar;
import com.zebrunner.carina.demo.utils.Person;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;


public abstract class CheckoutPageBase extends AbstractPage {
    @FindBy(xpath = "//input[@id='name']")
    protected ExtendedWebElement nameField;

    @FindBy(xpath = "//input[@id='country']")
    protected ExtendedWebElement countryField;

    @FindBy(xpath = "//input[@id='city']")
    protected ExtendedWebElement cityField;

    @FindBy(xpath = "//input[@id='card']")
    protected ExtendedWebElement cardField;

    @FindBy(xpath = "//input[@id='month']")
    protected ExtendedWebElement monthField;

    @FindBy(xpath = "//input[@id='year']")
    protected ExtendedWebElement yearField;

    @FindBy(xpath = "//button[@onclick='purchaseOrder()']")
    protected ExtendedWebElement purchaseButton;

    @FindBy(css = "p.lead.text-muted")
    protected ExtendedWebElement purchaseDetails;

    @FindBy(xpath = "//button[@class='confirm btn btn-lg btn-primary']")
    protected ExtendedWebElement confirmButton;

    @FindBy(xpath = "//nav")
    protected NavigationBar navigationBar;

    @FindBy(id = "footc")
    protected Footer footer;

    public CheckoutPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract NavigationBar getNavigationBar();

    public abstract Footer getFooter();

    public abstract void fillCheckoutDetails(Person person);

    public abstract void completePurchase();

    public abstract String getPurchaseDetails();

    public abstract ExtendedWebElement getPurchaseDetailsElement();

    public abstract void confirmPurchase();
}
