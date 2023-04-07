package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.utils.PasswordGenerator;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;


public class ChangePasswordTests extends TestBase{

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException {

        UserData userChangePwd = app.dbHelper().users().stream().filter(u -> u.getUsername() != "administrator").findAny().get();
        String user = userChangePwd.getUsername();
        app.changePwd().byAdmin(user);
        String generatePwd =  new PasswordGenerator.PasswordGeneratorBuilder().useLower(true).useUpper(true)
                .build().generate(8);
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmayionLink = findConfirmayionLink(mailMessages, user + "email@localhosc.localdomain");
        app.changePwd().acceptByUser(confirmayionLink, user, generatePwd);
        assertTrue (app.newSession().login(user, generatePwd));
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
