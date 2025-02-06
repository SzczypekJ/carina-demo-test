package com.zebrunner.carina.demo.gui.pages.demoblaze.android;

import com.zebrunner.carina.demo.gui.pages.common.demoblaze.CheckoutPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.FooterMobile;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.NavigationBarMobile;
import com.zebrunner.carina.demo.utils.Person;
import com.zebrunner.carina.utils.factory.DeviceType;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = CheckoutPageBase.class)
@Getter
public class CheckoutPageAndroid extends CheckoutPageBase {

    @FindBy(xpath = "//nav")
    private NavigationBarMobile navigationBar;

    @FindBy(id = "footc")
    private FooterMobile footer;

    public CheckoutPageAndroid(WebDriver driver) {
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
    public NavigationBarMobile getNavigationBar() {
        return navigationBar;
    }

    @Override
    public FooterMobile getFooter() {
        return footer;
    }
}
