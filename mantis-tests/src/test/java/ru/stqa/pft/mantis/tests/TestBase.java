package ru.stqa.pft.mantis.tests;

import org.openqa.selenium.remote.Browser;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.ApplicationManager;

import java.io.File;
import java.util.List;

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
        app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.ftp().restore("config_inc.php.bak", "config_inc.php");
        app.stop();
    }

    protected static String findConfirmayionLink(List<ru.stqa.pft.mantis.model.MailMessage> mailMessages, String email) {
        ru.stqa.pft.mantis.model.MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }
}
