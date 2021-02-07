package ru.stqa.pft.addressbook;

import org.testng.annotations.*;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    gotoAddContactForm();
    fillAddContactForm(new ContactData("Kira", "Ivanova", "Lenina 23-2", "89009003311", "kiraiv@mail.ru"));
    submitAddContactForm();
    gotoHomePage();
    logout();
  }

}
