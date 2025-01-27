package com.zebrunner.carina.demo.gui.pages.demoblaze.components.base;

import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import com.zebrunner.carina.webdriver.gui.AbstractUIObject;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@Getter
public abstract class FooterBase extends AbstractUIObject {
    public FooterBase(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }

    @FindBy(css = "#fotcont > div:nth-child(1) > div > div > h4")
    protected ExtendedWebElement aboutUsTitle;

    @FindBy(css = "#fotcont > div:nth-child(1) > div > div > p")
    protected ExtendedWebElement aboutUsDescription;

    @FindBy(css = "#fotcont > div:nth-child(2) > div > div > h4")
    protected ExtendedWebElement getInTouchTitle;

    @FindBy(xpath = ".//div[@id='fotcont']//div[2]//p[contains(text(), 'Address')]")
    protected ExtendedWebElement address;

    @FindBy(xpath = ".//div[@id='fotcont']//div[2]//p[contains(text(), 'Phone')]")
    protected ExtendedWebElement phone;

    @FindBy(xpath = ".//div[@id='fotcont']//div[2]//p[contains(text(), 'Email')]")
    protected ExtendedWebElement email;

    @FindBy(css = "#fotcont > div:nth-child(3) > div > div > h4")
    protected ExtendedWebElement productStoreTitle;

    public abstract String getAboutUsTitle();

    public abstract String getAboutUsDescription();

    public abstract String getGetInTouchTitle();

    public abstract String getAddress();

    public abstract String getPhone();

    public abstract String getEmail();

    public abstract String getProductStoreTitle();
}
