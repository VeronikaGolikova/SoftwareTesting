package ru.stqa.pft.rest.tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;
import ru.stqa.pft.rest.appmanager.ApplicationManager;

import java.io.IOException;

public class TestBase {
    protected static ApplicationManager app = new ApplicationManager();
    private boolean isBugHere;

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    boolean isIssueOpen(int issueId) throws IOException {
        app.restAssuredHelper().init();
        String json = RestAssured.get(app.getProperty("rest.getIssueById") + issueId + ".json").asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issue = parsed.getAsJsonObject().get("issues");
        JsonElement element = issue.getAsJsonArray().get(0);
        String status = element.getAsJsonObject().get("state_name").getAsString();
        if (status.equals("closed")) {
            return isBugHere = false;
        } else {
            return isBugHere = true;
        }
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            System.out.println("Ignored because of issue " + issueId);
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

}