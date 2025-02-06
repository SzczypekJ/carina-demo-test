package com.zebrunner.carina.demo.gui.pages.demoblaze.components.android;

import com.zebrunner.carina.demo.gui.pages.demoblaze.components.base.CartItemComponentBase;
import com.zebrunner.carina.webdriver.decorator.ExtendedWebElement;
import lombok.Getter;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

@Getter
public class CartItemComponentMobile extends CartItemComponentBase {

    public CartItemComponentMobile(WebDriver driver, SearchContext searchContext) {
        super(driver, searchContext);
    }
}
