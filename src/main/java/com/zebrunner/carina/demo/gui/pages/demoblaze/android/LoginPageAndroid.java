package com.zebrunner.carina.demo.gui.pages.demoblaze.android;

import com.zebrunner.carina.demo.gui.pages.common.demoblaze.LoginPageBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.FooterMobile;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.android.NavigationBarMobile;
import com.zebrunner.carina.utils.factory.DeviceType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

@DeviceType(pageType = DeviceType.Type.ANDROID_PHONE, parentClass = LoginPageBase.class)
public class LoginPageAndroid extends LoginPageBase {

    @FindBy(xpath = "//nav")
    private NavigationBarMobile navigationBar;

    @FindBy(id = "footc")
    private FooterMobile footer;

    public LoginPageAndroid(WebDriver driver) {
        super(driver);
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
