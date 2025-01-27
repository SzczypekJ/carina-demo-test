package com.zebrunner.carina.demo.gui.pages.demoblaze.components.android;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.FooterBase;
import com.zebrunner.carina.utils.factory.DeviceType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = FooterBase.class)
public class FooterMobile extends FooterBase {

    public FooterMobile(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @Override
    public String getAboutUsTitle() {
        return aboutUsTitle.getText();
    }

    @Override
    public String getAboutUsDescription() {
        return aboutUsDescription.getText();
    }

    @Override
    public String getGetInTouchTitle() {
        return getInTouchTitle.getText();
    }

    @Override
    public String getAddress() {
        return address.getText();
    }

    @Override
    public String getPhone() {
        return phone.getText();
    }

    @Override
    public String getEmail() {
        return email.getText();
    }

    @Override
    public String getProductStoreTitle() {
        return productStoreTitle.getText();
    }
}
