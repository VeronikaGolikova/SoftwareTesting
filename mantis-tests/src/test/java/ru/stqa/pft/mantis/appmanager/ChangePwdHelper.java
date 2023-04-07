package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;
import ru.stqa.pft.mantis.model.Users;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ChangePwdHelper extends HelperBase {

    public ChangePwdHelper(ApplicationManager app) {
        super(app);
    }

    public void byAdmin(String user) throws IOException {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.id("username"), "administrator");
        click(By.xpath("//input[@type=\"submit\"]"));
        type(By.id("password"), "root");
        click(By.xpath("//input[@type=\"submit\"]"));
        wd.get(app.getProperty("web.baseUrl") + "/manage_user_page.php");
        click(By.xpath("//*[.='" + user + "']"));
        click(By.xpath("//input[@value='Сбросить пароль']"));
    }

    public void acceptByUser(String confirmayionLink, String username, String password ) {
        wd.get(confirmayionLink);
        type(By.id("realname"), username);
        type(By.name("password"), password);
        type(By.name("password_confirm"), password);
        click(By.xpath("//button"));
    }
}
