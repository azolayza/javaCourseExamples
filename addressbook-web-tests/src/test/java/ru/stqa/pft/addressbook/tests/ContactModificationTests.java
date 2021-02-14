package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{
  @Test
  public void testContactModification(){
    if (! app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().gotoAddContactForm();
      app.getContactHelper().createContact(new ContactData("Mila", "Ivanova", null, "89009003311", "kiraiv@mail.ru", "test11"));
      app.getNavigationHelper().gotoHomePage();
    }
    app.getContactHelper().clickEditContact();
    app.getContactHelper().fillContactForm(new ContactData("Lena", "Ivanova", "Lenina 43-2", "89009003351", "lena@mail.ru", null), false);
    app.getContactHelper().submitUpdateContactForm();
    app.getNavigationHelper().gotoHomePage();
  }
}
