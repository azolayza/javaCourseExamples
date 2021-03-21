package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase{
  public ContactHelper(WebDriver wd)  {
    super(wd);
  }

  public void addForm() {
    if (isElementPresent(By.name("submit"))) {
      return;
    }
    click(By.linkText("add new"));
  }

  public void submitAddContactForm() {
    click(By.name("submit"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type("firstname", contactData.getFirstName());
    type("lastname", contactData.getLastName());
    type("address", contactData.getAddress());
    type("home", contactData.getHomePhone());
    type("email", contactData.getEmail());
    //attach("photo", contactData.getPhoto());

    if (creation){
      if (contactData.getGroups().size()>0) {
        Assert.assertTrue(contactData.getGroups().size()==1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(
                contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void submitDelete() {
    click(By.xpath("//input[@value='Delete']"));
    wd.switchTo().alert().accept();
  }

  public void clickEditContact(int index) {
      wd.findElements(By.xpath("//img[@alt='Edit']")).get(index).click();
  }

  public void returnToHomePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home"));
  }

  public void clickEditContactById(int id) {
    wd.findElement(By.xpath("//a[@href='edit.php?id=" + id +"']")).click();
  }

  public void submitUpdateContactForm() {
    click(By.xpath("(//input[@name='update'])[2]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public void create(ContactData contact) {
    addForm();
    fillContactForm(contact, true);
    submitAddContactForm();
    contactCache = null;
  }

  public void modify(ContactData contact) {
    clickEditContactById(contact.getId());
    fillContactForm(contact, true);
    submitUpdateContactForm();
    contactCache = null;
  }

  public void delete(int index) {
    selectContact(index);
    submitDelete();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    submitDelete();
    contactCache = null;
    returnToHomePage();
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public List<ContactData> list() {
    // Создание списка
    List<ContactData> contacts = new ArrayList<ContactData>();
    // Поиск строки, из которой берем имя и фамилию
    List<WebElement> rows = wd.findElements(By.cssSelector("tr[name='entry']"));
    // Цикл прохода по этим строкам в цикле и берём имя и фамилию
    for (WebElement row : rows) {
      int id = Integer.parseInt(row.findElement(By.cssSelector("td:nth-child(1) input")).getAttribute("value"));
      String lastname = row.findElement(By.cssSelector("td:nth-child(2)")).getText();
      String firstname = row.findElement(By.cssSelector("td:nth-child(3)")).getText();
      // добавляем в этот объект текст, который прочитали в строках
      contacts.add(new ContactData().withId(id).withFirstName(firstname).withLastName(lastname));
    }
    return contacts;
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    // Создание списка
    contactCache = new Contacts();
    // Поиск строки, из которой берем имя и фамилию
    List<WebElement> rows = wd.findElements(By.name("entry"));
    // Цикл прохода по этим строкам в цикле и берём имя и фамилию
    for (WebElement row : rows) {
      List<WebElement> cells = row.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
      String lastname = cells.get(1).getText();
      String firstname = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      /*Извлекаем данные со списком номеров телефонов и делим их на строки
      String[] phones = cells.get(5).getText().split("\n"); */
      // добавляем в этот объект текст, который прочитали в строках, включая разбитые на строки содержимое телефонов
      contactCache.add(new ContactData().withId(id)
              .withFirstName(firstname)
              .withLastName(lastname)
              .withAddress(address)
              .withAllEmails(allEmails)
              .withAllPhones(allPhones));
    }
    return new Contacts(contactCache);
  }

  //Метод для извлечения данных из формы редактирования контакта
  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId())
            .withFirstName(firstname)
            .withLastName(lastname)
            .withHomePhone(home)
            .withMobilePhone(mobile)
            .withWorkPhone(work)
            .withAddress(address)
            .withEmail(email)
            .withEmail2(email2)
            .withEmail3(email3);
  }

  public void addContactToGroup() {
    click(By.name("add"));
    contactCache = null;
    returnToHomePage();
  }

  public void removeContactFromGroup() {
    click(By.name("remove"));
    contactCache = null;
    returnToHomePage();
  }

  public void selectGroupPage(Contacts contactData){
    if (contactData.iterator().next().getGroups().size() > 0) {
      Assert.assertTrue(contactData.iterator().next().getGroups().size() == 1);
      new Select(wd.findElement(By.name("group"))).selectByVisibleText(contactData.iterator().next().getGroups().iterator().next().getName());
    }
  }

  public void selectGroup(Contacts contactData) {
    if (contactData.iterator().next().getGroups().size() > 1) {
      Assert.assertTrue(contactData.iterator().next().getGroups().size() == 1);
      new Select(wd.findElement(By.name("group"))).selectByVisibleText(contactData.iterator().next().getGroups().iterator().next().getName());
    }
  }
  //Метод инициализации кнопки для открытия формы редактирования контакта
 private void initContactModificationById(int id){
   wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s'", id))).click();

    /* Варианты нахождения локатора для кнопки-ссылки открытия формы редактирования контакта
    Вариант длинный с прохождением вглубь
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = wd.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();
    Варианты  короче:
    wd.findElement(By.xpath(String.format("//input[value='%s']/../../td[8]/a", id))).click();
    wd.findElement(By.xpath(String.format("//tr[.//input[value='%s']]/td[8]/a", id))).click();
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s'", id))).click(); */
 }

}
