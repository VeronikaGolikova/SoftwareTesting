package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class NavigationHelper {
    private FirefoxDriver wd;

    public NavigationHelper(FirefoxDriver wd) {
    this.wd = wd;
    }

    public void gotoPage(String groups) {
      wd.findElement(By.linkText(groups)).click();
    }
}
