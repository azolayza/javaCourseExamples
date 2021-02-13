package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase{
  public SessionHelper(WebDriver wd)  {
    super(wd);
  }
  public void login(String username, String password) {
    type("user", username);
    type("pass", password);
    click(By.xpath("//input[@value='Login']"));
  }
  public void logout() {
    click(By.linkText("Logout"));
  }
}
