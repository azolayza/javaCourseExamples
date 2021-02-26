package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase{

  @Test (enabled = false)
  public void testContactCreation() throws Exception {
    app.goTo().gotoHomePage();
    List<ContactData> before = app.contact().getContactList();
    app.goTo().gotoAddContactForm();
    ContactData contact = new ContactData("Kira", "Kivanova", null, "89009003355", "kiraiv@mail.ru", "test11");
    app.contact().createContact(contact);
    app.goTo().gotoHomePage();
    List<ContactData>after = app.contact().getContactList();
    Assert.assertEquals(after.size(),before.size()+1);

    before.add(contact);
    Comparator<? super ContactData> byId = (q1, q2) -> Integer.compare(q1.getId(), q2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
