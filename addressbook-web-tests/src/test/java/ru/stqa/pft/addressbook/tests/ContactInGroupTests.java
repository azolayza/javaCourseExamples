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
    Contacts contacts = app.db().contacts();
    if(contacts.stream().iterator().next().getGroups().size() == 0) {
      app.contact().selectContactById(contacts.iterator().next().getId());
      app.contact().selectGroup(contacts);
      app.contact().addContactToGroup();
      app.goTo().homePage();
    }
  }

  @Test
  public void testContactAddToGroup(){
    Contacts before = app.db().contacts();
    Groups groups = app.db().groups();
    app.contact().selectContactById(before.iterator().next().getId());
    app.contact().selectGroup(before);
    app.contact().addContactToGroup();
    Contacts after = app.db().contacts();
    assertThat(before.iterator().next(), equalTo(after.iterator().next()));
    verifyContactListUI();
  }

  @Test
  public void testContactRemoveFromGroup() {
    Contacts contacts = app.db().contacts();
    app.contact().selectGroupPage(contacts);
    Contacts before = app.db().contacts();
    app.contact().removeContactFromGroup();
    Contacts after = app.db().contacts();
    assertThat(contacts.iterator().next(), equalTo(after.iterator().next()));
    verifyContactListUI();
  }
}
