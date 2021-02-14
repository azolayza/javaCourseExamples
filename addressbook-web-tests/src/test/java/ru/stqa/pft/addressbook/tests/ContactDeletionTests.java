package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase{
  @Test
  public void testContactDeletion(){
    if (! app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().gotoAddContactForm();
      app.getContactHelper().createContact(new ContactData("Mila", "Ivanova", null, "89009003311", "kiraiv@mail.ru", "test11"));
      app.getNavigationHelper().gotoHomePage();
    }
    app.getContactHelper().selectedContact();
    app.getContactHelper().submitDeleteContact();
  }
}
