package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.Users;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;


public class ChangePasswordTests extends TestBase{

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
        app.db().start();
    }

    @Test
    public void testChangePassword() throws IOException {

        //сюда передать юзера из БД
        String user = "user1";
        Users users = app.db().users();
        app.changePwd().byAdmin();

        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmayionLink = findConfirmayionLink(mailMessages, user + "email@localhosc.localdomain");
        app.changePwd().acceptByUser(confirmayionLink, user, "passwordNew");

        assertTrue (app.newSession().login(user, "passwordNew"));

    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
    public void stopDbConnection() {
        app.db().stop();
    }
}
