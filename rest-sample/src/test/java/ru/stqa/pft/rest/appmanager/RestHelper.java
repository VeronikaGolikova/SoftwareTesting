package ru.stqa.pft.rest.appmanager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.SkipException;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestHelper {

    private ApplicationManager app;

    public RestHelper(ApplicationManager applicationManager) {
        this.app = applicationManager;
    }

    public Executor getExecutor() {
        return Executor.newInstance().auth("b31e382ca8445202e66b03aaf31508a3", "");
    }

    public Set<Issue> getIssues() throws IOException {
        String json = app.restHelper().getExecutor().execute(Request.Get((app.getProperty("rest.api.url" ) )))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set <Issue>>(){}.getType());
    }

    public int createIssue(Issue newIssue) throws IOException {
        String json = app.restHelper().getExecutor().execute(Request.Post((app.getProperty("rest.api.url") ))
                        .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                                new BasicNameValuePair("description", newIssue.getDescription())))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

}
