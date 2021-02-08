package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoAddContactForm();
    app.getContactHelper().fillAddContactForm(new ContactData("Kira", "Ivanova", "Lenina 23-2", "89009003311", "kiraiv@mail.ru"));
    app.getContactHelper().submitAddContactForm();
    app.getNavigationHelper().gotoHomePage();
    app.logout();
  }

}
