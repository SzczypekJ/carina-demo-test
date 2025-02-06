package com.zebrunner.carina.demo.gui.pages.demoblaze.components.android;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.NavigationBarBase;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

@Getter
public class NavigationBarMobile extends NavigationBarBase {

    public NavigationBarMobile(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
