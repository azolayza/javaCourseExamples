package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase{
  public ContactHelper(WebDriver wd)  {
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

  public void selectedContact() {
    click(By.name("selected[]"));
  }

  public void submitDeleteContact() {
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void clickEditContact() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void submitUpdateContactForm() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }
}
