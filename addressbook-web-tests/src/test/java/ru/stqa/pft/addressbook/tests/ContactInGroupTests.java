package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactInGroupTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.db().contacts().size() == 0) {
      app.contact().addForm();
      app.contact().create(new ContactData()
              .withFirstName("Mila")
              .withLastName("Ivanova")
              .withEmail("kiraiv@mail.ru")
              .withHomePhone("893211")
              .withAddress("Mira pr. 154-22"));
      app.goTo().homePage();
    }
    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData()
              .withName("test11")
              .withFooter("testgroup"));
    }
  }

  @Test
  public void testContactAddToGroup(){
    Contacts before = app.db().contacts();
    Groups groups = app.db().groups();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .inGroup(groups.iterator().next());;
    app.contact().addContactToGroup(contact);
    app.goTo().contactsInGroupAtHome(contact, true);
    assertEquals(app.contact().count(),before.size());
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    verifyContactListUI();
  }

  @Test
  public void testContactRemoveFromGroup() {
    ContactData contact = new ContactData();
    app.contact().selectGroupPage();
    Contacts before = app.db().contacts();
    app.contact().removeContactFromGroup(contact);
    assertEquals(app.contact().count(),before.size()-1);
    verifyContactListUI();
  }
}
