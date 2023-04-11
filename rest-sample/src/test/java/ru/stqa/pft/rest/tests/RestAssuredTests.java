package ru.stqa.pft.rest.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class RestAssuredTests extends TestBase{

    @Test
    public void testCreateIssue() {
        Set<Issue> oldIssues = app.restAssuredHelper().getIssues();
        Issue newIssue = new Issue().withSubject("New V Issue").withDescription("V desc");
        int issueId = app.restAssuredHelper().createIssue(newIssue);
        Set<Issue> newIssues = app.restAssuredHelper().getIssues();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }

}
