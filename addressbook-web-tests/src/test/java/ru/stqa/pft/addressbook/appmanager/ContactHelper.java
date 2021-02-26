package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase{
  public ContactHelper(WebDriver wd)  {
    super(wd);
  }

  public void submitAddContactForm() {
    click(By.xpath("(//input[@name='submit'])[2]"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type("firstname", contactData.getFirstName());
    type("lastname", contactData.getLastName());
    type("address", contactData.getAddress());
    type("home", contactData.getPhone());
    type("email", contactData.getEmail());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }

  }

  public void selectedContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void submitDeleteContact() {
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void clickEditContact(int index) {
      wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  public void submitUpdateContactForm() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }

  public void createContact(ContactData contact) {
    fillContactForm(contact, true);
    submitAddContactForm();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public List<ContactData> getContactList() {
    // Создание списка
    List<ContactData> contacts = new ArrayList<ContactData>();
    // Поиск строки, из которой берем имя и фамилию
    List<WebElement> rows = wd.findElements(By.cssSelector("tr[name='entry']"));
    // Цикл прохода по этим строкам в цикле и берём имя и фамилию
    for (WebElement row : rows) {
      int id = Integer.parseInt(row.findElement(By.cssSelector("td:nth-child(1) input")).getAttribute("value"));
      String lastname = row.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String firstname = row.findElement(By.cssSelector("td:nth-child(3)")).getText();


      // Создаём объект типа ContactData
      ContactData contact = new ContactData(id, firstname, lastname, null,null, null, null);
      // И добавляем в этот объект текст, который прочитали в строках
      contacts.add(contact);
    }
    return contacts;
  }
}
