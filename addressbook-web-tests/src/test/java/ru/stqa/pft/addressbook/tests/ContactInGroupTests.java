package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

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
              .withName("test4")
              .withFooter("testgroup"));
    }

    if (app.db().contactWithoutGroups().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withFirstName("Lena")
              .withLastName("Popova")
              .withEmail("lenap@mail.ru")
              .withHomePhone("834211555")
              .withAddress("Butovo str. 154-22"));
    }
    if (app.db().contactWithGroups().size() == 0) {
      ContactData before = app.db().contactWithoutGroup();
      Groups groups = app.db().groups();
      GroupData group = groups.iterator().next();
      app.goTo().homePage();
      app.contact().selectContactWithoutGroup(before);
      app.contact().selectGroup(group);
      app.contact().addContactToGroup();
    }
  }

  @Test
  public void testContactAddToGroup(){
    ContactData before = app.db().contactWithoutGroup();
    Groups groups = app.db().groups();
    GroupData group = groups.iterator().next();
    app.goTo().homePage();
    app.contact().selectContactWithoutGroup(before);
    app.contact().selectGroup(group);
    app.contact().addContactToGroup();
    ContactData after = app.db().contactById(before.getId());
    assertTrue(after.getGroups().contains(group));   //У контакта есть группа в которую его добавили
    verifyContactListUI();
  }

  @Test
  public void testContactRemoveFromGroup() {
    ContactData before = app.db().contactInGroup();
    GroupData group = before.getGroups().iterator().next();
    app.goTo().homePage();
    app.contact().getGroupData(group);
    app.contact().selectContact(before);
    app.contact().removeContactFromGroup();
    ContactData after = app.db().contactById(before.getId());
    assertFalse(after.getGroups().contains(group)); //У контакта после удаления нет той группы из которой его удаляли
    verifyContactListUI();
  }
}
