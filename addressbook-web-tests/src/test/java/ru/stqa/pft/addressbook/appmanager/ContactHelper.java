package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase{
  public ContactHelper(FirefoxDriver wd)  {
    super(wd);
  }

  public void submitAddContactForm() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillAddContactForm(ContactData contactData) {
    type("firstname", contactData.getFirstName());
    type("lastname", contactData.getLastName());
    type("address", contactData.getAddress());
    type("home", contactData.getPhone());
    type("email", contactData.getEmail());
  }
}
