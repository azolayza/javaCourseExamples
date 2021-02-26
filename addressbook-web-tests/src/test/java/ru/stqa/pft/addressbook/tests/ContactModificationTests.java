package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase{
  @Test (enabled = false)
  public void testContactModification(){
      app.goTo().gotoHomePage();
    if (! app.contact().isThereAContact()) {
      app.goTo().gotoAddContactForm();
      app.contact().createContact(new ContactData("Mila", "Ivanova", null, "89009003311", "kiraiv@mail.ru", "test11"));
      app.goTo().gotoHomePage();
    }
    List<ContactData> before = app.contact().getContactList();
    app.contact().clickEditContact(before.size()-1);
    ContactData contact = new ContactData(before.get(before.size()-1).getId(),"Lena", "Ivanova", "Lenina 43-2", "89009003351", "lena@mail.ru", null);
    app.contact().fillContactForm(contact, false);
    app.contact().submitUpdateContactForm();
    app.goTo().gotoHomePage();
    List<ContactData> after = app.contact().getContactList();
    Assert.assertEquals(after.size(),before.size());

    before.remove(before.size()-1);
    before.add(contact);
    Comparator<? super ContactData> byId = (q1, q2) -> Integer.compare(q1.getId(), q2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }
}
