package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
    private final Properties properties;
    private WebDriver wd;

    public Browser browser;
    private RegistrationHelper registrationHalper;
    private FtpHelper ftp;
    private MailHelper mailHelper;
    private DbHelper dbHelper;
    private ChangePwdHelper changePwd;
    private SoapHelper soapHelper;

    public ApplicationManager(Browser browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
    }

    public void stop() {
        if (wd != null) {
            wd.quit();
        }
    }

    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public RegistrationHelper registration() {
        if (registrationHalper == null) {
            registrationHalper = new RegistrationHelper(this);
        }
        return registrationHalper;
    }

    public ChangePwdHelper changePwd() {
        if (changePwd == null) {
            changePwd = new ChangePwdHelper(this);
        }
        return changePwd;
    }

    public FtpHelper ftp(){
        if (ftp == null) {
            ftp = new FtpHelper(this);
        }
        return ftp;
    }

    public WebDriver getDriver() {
        if (wd == null) {
            if (browser.equals(Browser.FIREFOX)) {
                wd = new FirefoxDriver();
            } else if (browser .equals(Browser.CHROME)) {
                wd = new ChromeDriver();
            } else if (browser .equals(Browser.SAFARI)) {
                wd = new SafariDriver();
            }
            wd.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            wd.get(properties.getProperty("web.baseUrl"));
        }
        return wd;
    }

    public MailHelper mail(){
        if (mailHelper == null) {
            mailHelper = new MailHelper(this);
        }
        return mailHelper;
    }


    public DbHelper dbHelper(){
        if (dbHelper == null) {
            dbHelper = new DbHelper(this);
        }
        return dbHelper;
    }

    public SoapHelper soapHelper(){
        if (soapHelper == null) {
            soapHelper = new SoapHelper(this);
        }
        return soapHelper;
    }
}
