package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{
  @Test
  public void testContactModification(){
    app.getContactHelper().clickEditContact();
    app.getContactHelper().fillAddContactForm(new ContactData("Lena", "Ivanova", "Lenina 43-2", "89009003351", "lena@mail.ru"));
    app.getContactHelper().submitUpdateContactForm();
    app.getNavigationHelper().gotoHomePage();
    app.logout();
  }
}
