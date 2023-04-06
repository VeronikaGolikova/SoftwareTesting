package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class RegistrationTests extends TestBase{

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testRegistration() throws IOException {
        String user = "user13";
        String password = "password";
        String email = "user13email@localhosc.localdomain";
        app.registration().start(user, email);
        List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
        String confirmayionLink = findConfirmayionLink(mailMessages, email);
        app.registration().finish(confirmayionLink, user, password);
        assertTrue (app.newSession().login(user, password));

    }

    private String findConfirmayionLink(List<ru.stqa.pft.mantis.model.MailMessage> mailMessages, String email) {
        ru.stqa.pft.mantis.model.MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
