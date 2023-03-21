package ru.stqa.pft.addressbook.tests;

import org.openqa.selenium.remote.Browser;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.addressbook.appmanager.ApplicationManager;

public class TestBase {

    protected final static ApplicationManager app = new ApplicationManager(new Browser() {
        @Override
        public String browserName() {
            return System.getProperty("browser", Browser.FIREFOX.browserName());
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Browser) {
                return this.browserName().equals(((Browser)obj).browserName());
            } else
                return false;
        }
    });

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

}
