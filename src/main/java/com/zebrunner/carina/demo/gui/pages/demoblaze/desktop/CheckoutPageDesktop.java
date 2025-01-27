package com.zebrunner.carina.demo.gui.pages.demoblaze.desktop;

import com.zebrunner.carina.demo.gui.pages.common.demoblaze.CheckoutPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.FooterDesktop;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop.NavigationBarDesktop;
import com.zebrunner.carina.demo.utils.Person;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.DESKTOP, parentClass = CheckoutPageBase.class)
@Getter
public class CheckoutPageDesktop extends CheckoutPageBase {

    @FindBy(xpath = "//nav")
    private NavigationBarDesktop navigationBar;

    @FindBy(id = "footc")
    private FooterDesktop footer;

    public CheckoutPageDesktop(WebDriver driver) {
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
    public NavigationBarDesktop getNavigationBar() {
        return navigationBar;
    }

    @Override
    public FooterDesktop getFooter() {
        return footer;
    }
}
