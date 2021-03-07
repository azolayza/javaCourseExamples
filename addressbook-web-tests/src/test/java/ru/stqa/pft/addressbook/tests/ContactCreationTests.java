package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    ContactData contact = new ContactData()
            .withFirstName("Lina")
            .withLastName("Menina")
            .withPhone("89009002215")
            .withEmail("lena@mail.ru")
            .withGroup("new3");
    app.goTo().addForm();
    app.contact().create(contact);
    app.goTo().homePage();
    assertEquals(app.contact().count(),before.size()+1);
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before
            .withAdded(contact.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt()))));
  }

}
