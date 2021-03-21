package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class NavigationHelper extends HelperBase{
  private WebDriver wd;

  public NavigationHelper(WebDriver wd) {
    super(wd);
  }

  public void GroupPage() {
     /*if (isElementPresent(By.tagName("h1"))
            && wd.findElement(By.tagName("h1")).getText().equals("Groups")
            && isElementPresent(By.name("new"))) {
      return;
    }*/
    click(By.linkText("groups"));
  }

  public void homePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home page"));
  }

  public void contactsInGroupAtHome(ContactData contactData, boolean addGroups) {
    click(By.name("group"));
    if (addGroups) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("group")));
      click(By.xpath("//option[@value='1']"));
    }
  }
}
