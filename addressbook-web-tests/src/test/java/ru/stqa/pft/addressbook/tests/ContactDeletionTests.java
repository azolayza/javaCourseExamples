package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactDeletionTests extends TestBase{
  @Test
  public void testContactDeletion(){
    if (! app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().gotoAddContactForm();
      app.getContactHelper().createContact(new ContactData("Mila", "Ivanova", null, "89009003311", "kiraiv@mail.ru", "test11"));
      app.getNavigationHelper().gotoHomePage();
    }
    List<ContactData> before = app.getContactHelper().getContactList();
    app.getContactHelper().selectedContact(before.size() -1);
    app.getContactHelper().submitDeleteContact();
    app.getNavigationHelper().gotoHomePage();
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size()-1);

    before.remove(before.size()-1);
    Comparator<? super ContactData> byId = (q1, q2) -> Integer.compare(q1.getId(), q2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }
}
