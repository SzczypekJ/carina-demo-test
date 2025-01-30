package com.zebrunner.carina.demo.gui.pages.common.demoblaze;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.FooterBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.NavigationBarBase;
import com.zebrunner.carina.demo.utils.Person;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;


@Getter
public abstract class CheckoutPageBase extends MainPageBase {
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

    public CheckoutPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract void fillCheckoutDetails(Person person);

    public abstract void completePurchase();

    public abstract String getPurchaseDetails();

    public abstract ExtendedWebElement getPurchaseDetailsElement();

    public abstract void confirmPurchase();

}
