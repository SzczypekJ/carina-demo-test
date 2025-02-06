package com.zebrunner.carina.demo.gui.pages.demoblaze.components.desktop;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.NavigationBarBase;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;

@Getter
public class NavigationBarDesktop extends NavigationBarBase {

    public NavigationBarDesktop(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
