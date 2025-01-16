package com.zebrunner.carina.demo.gui.pages.demoblaze.components;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

public class Footer extends AbstractUIObject {

    @FindBy(css = "#fotcont > div:nth-child(1) > div > div > h4")
    private ExtendedWebElement aboutUsTitle;

    @FindBy(css = "#fotcont > div:nth-child(1) > div > div > p")
    private ExtendedWebElement aboutUsDescription;

    @FindBy(css = "#fotcont > div:nth-child(2) > div > div > h4")
    private ExtendedWebElement getInTouchTitle;

    @FindBy(xpath = "//div[@id='fotcont']//div[2]//p[contains(text(), 'Address')]")
    private ExtendedWebElement address;

    @FindBy(xpath = "//div[@id='fotcont']//div[2]//p[contains(text(), 'Phone')]")
    private ExtendedWebElement phone;

    @FindBy(xpath = "//div[@id='fotcont']//div[2]//p[contains(text(), 'Email')]")
    private ExtendedWebElement email;

    @FindBy(css = "#fotcont > div:nth-child(3) > div > div > h4")
    private ExtendedWebElement productStoreTitle;

    public Footer(WebDriver driver) {
        super(driver);
    }

    public Footer(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    public String getAboutUsTitle() {
        return aboutUsTitle.getText();
    }

    public String getAboutUsDescription() {
        return aboutUsDescription.getText();
    }

    public String getGetInTouchTitle() {
        return getInTouchTitle.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public String getPhone() {
        return phone.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getProductStoreTitle() {
        return productStoreTitle.getText();
    }
}
