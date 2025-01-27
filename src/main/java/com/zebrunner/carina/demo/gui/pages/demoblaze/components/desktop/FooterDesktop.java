package com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.FooterBase;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;


public class FooterDesktop extends FooterBase {

    public FooterDesktop(WebDriver driver, SearchContext searchContext) {
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
