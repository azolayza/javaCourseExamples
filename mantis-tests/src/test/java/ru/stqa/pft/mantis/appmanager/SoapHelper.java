package ru.stqa.pft.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import ru.stqa.pft.mantis.model.Issue;
import ru.stqa.pft.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SoapHelper {
  private ApplicationManager app;

  public SoapHelper(ApplicationManager app){
    this.app = app;
  }

  public Set<Project> getProjects() throws RemoteException, MalformedURLException, ServiceException {
    MantisConnectPortType mc = getMantisConnect();
    ProjectData[] projects = mc.mc_projects_get_user_accessible("Administrator", "root");
    return Arrays.asList(projects).stream()
            .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName())).collect(Collectors.toSet());
  }

  public MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
    MantisConnectPortType mc = new MantisConnectLocator()
            .getMantisConnectPort(new URL(app.getProperty("soap.URL")));
    return mc;
  }

  public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    String[] categories = mc.mc_project_get_categories(
            app.getProperty("soap.adminlogin"),
            app.getProperty("soap.adminpassword"),
            BigInteger.valueOf(issue.getProject().getId()));
    IssueData issueData = new IssueData();
    issueData.setSummary(issue.getSummary());
    issueData.setDescription(issue.getDescription());
    issueData.setProject(
            new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
    issueData.setCategory(categories[0]);
    BigInteger issueId = mc.mc_issue_add(
            app.getProperty("soap.adminlogin"),
            app.getProperty("soap.adminpassword"),
            issueData);
    IssueData newIssueData = mc.mc_issue_get(
            app.getProperty("soap.adminlogin"),
            app.getProperty("soap.adminpassword"),
            issueId);
    return new Issue().withId(newIssueData.getId().intValue())
            .withSummary(newIssueData.getSummary())
            .withDescription(newIssueData.getDescription())
            .withProject(new Project().withId(newIssueData.getId().intValue())
            .withName(newIssueData.getProject().getName()));
  }

  public Issue getIssue(int issueId) throws MalformedURLException, ServiceException, RemoteException {
    MantisConnectPortType mc = getMantisConnect();
    IssueData issueData = mc.mc_issue_get(app.getProperty("soap.adminlogin"), app.getProperty("soap.adminpassword"), BigInteger.valueOf(issueId));
    Issue issue = new Issue().withId(issueData.getId().intValue())
            .withSummary(issueData.getSummary())
            .withDescription(issueData.getDescription())
            .withProject(new Project().withId(issueData.getProject().getId().intValue()))
            .withName(issueData.getProject().getName())
            .withStatus(issueData.getStatus().getName());
    return issue;
  }
}
