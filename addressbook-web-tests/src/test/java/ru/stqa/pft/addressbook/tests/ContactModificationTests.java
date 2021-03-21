package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions(){
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      app.contact().addForm();
      app.contact().create(new ContactData()
              .withFirstName("Mila")
              .withLastName( "Ivanova")
              .withEmail("kiraiv@mail.ru"));
      app.goTo().homePage();
    }
  }

  @Test
  public void testContactModification(){
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId())
            .withFirstName("Nina")
            .withLastName("Bаvanova")
            .withAddress("Orlova str")
            .withHomePhone("456649")
            .withEmail("rai@mail.ru");
    app.contact().modify(contact);
    app.goTo().homePage();
    assertEquals(app.contact().count(),before.size());
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListUI();
  }
}
