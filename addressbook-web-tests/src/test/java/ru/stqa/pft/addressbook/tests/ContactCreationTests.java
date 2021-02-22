package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoAddContactForm();
    app.getContactHelper().createContact(new ContactData("Kira", "Kivanova", null, "89009003355", "kiraiv@mail.ru", "test11"));
    app.getNavigationHelper().gotoHomePage();
  }

}
