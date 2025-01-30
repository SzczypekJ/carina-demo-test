package com.zebrunner.carina.demo.gui.pages.common.demoblaze;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.FooterBase;
import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.NavigationBarBase;
import com.zebrunner.carina.webdriver.gui.AbstractPage;
import org.openqa.selenium.WebDriver;

public abstract class MainPageBase extends AbstractPage {
    public MainPageBase(WebDriver driver) {
        super(driver);
    }

    public abstract NavigationBarBase getNavigationBar();

    public abstract FooterBase getFooter();
}
