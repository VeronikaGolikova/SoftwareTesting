package ru.stqa.pft.rest.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestAssuredHelper {

    private ApplicationManager app;
    private boolean isBugHere;

    public RestAssuredHelper(ApplicationManager applicationManager) {
        this.app = applicationManager;
    }

    @BeforeClass
    public void init() {
        RestAssured.authentication = RestAssured.basic("b31e382ca8445202e66b03aaf31508a3", "");
    }

    public Set<Issue> getIssues(){
        String json = RestAssured.get(app.getProperty("rest.api.url")).asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set <Issue>>(){}.getType());
    }

    public int createIssue(Issue newIssue){
        String json = RestAssured.given()
                .param("subject", newIssue.getSubject())
                .param("description", newIssue.getDescription())
                .post((app.getProperty("rest.api.url"))).asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

}
