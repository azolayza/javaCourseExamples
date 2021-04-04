package ru.stqa.pft.rest;

import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static org.testng.Assert.assertEquals;

public class RestTests extends TestBase{

  @Test
  public void testCreateIssue() throws IOException {
    skipIfNotFixed(605);
    Set<Issue> oldIssues =IssueHelper.getIssues();
    System.out.println(oldIssues);
    Issue newIssue = new Issue().withSubject("test issue").withDescription("new test issue");
    int issueId = IssueHelper.createIssue(newIssue);
    oldIssues.add(newIssue.withId(issueId));
    Set<Issue> newIssues = IssueHelper.getIssues();
    assertEquals(newIssues,oldIssues);
  }


}
