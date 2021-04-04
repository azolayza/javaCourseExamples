package ru.stqa.pft.rest;

import org.testng.SkipException;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

public class TestBase {
  public boolean isIssueOpen(int issueId) throws IOException, IOException {
    Set<Issue> issues =IssueHelper.getIssues();
    Issue issue = issues.stream().filter(Issue -> Objects.equals(issueId, Issue.getId())).findFirst().get();
    if(issue.getStateName().equals("Closed")){
      return false;
    } else {
      return true;
    }
  }
  public void skipIfNotFixed(int issueId) throws IOException {
    if (isIssueOpen(issueId)) {
      throw new SkipException("Ignored because of issue " + issueId);
    }
  }
}
