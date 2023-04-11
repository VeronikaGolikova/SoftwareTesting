package ru.stqa.pft.rest.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class RestTests extends TestBase{

    @Test
    public void testCreateIssue() throws IOException {
        skipIfNotFixed(23);
        Set<Issue> oldIssues = app.restHelper().getIssues();
        Issue newIssue = new Issue().withSubject("New V Issue").withDescription("V desc");
        int issueId = app.restHelper().createIssue(newIssue);
        Set<Issue> newIssues = app.restHelper().getIssues();
        oldIssues.add(newIssue.withId(issueId));
        assertEquals(newIssues, oldIssues);
    }

}
