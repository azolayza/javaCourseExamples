package ru.stqa.pft.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase{

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    try (BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json"))))
    {
      String json = "";
      String line = reader.readLine();
      while (line != null) {
        json += line;
        line = reader.readLine();
      }
      Gson gson = new Gson();
      List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
      return  contacts.stream().map((g)-> new Object [] {g}).collect(Collectors.toList()).iterator();
    }
  }

  @Test (dataProvider = "validContactsFromJson")
  public void testContactCreationWithFile(ContactData contact) throws Exception {
    app.goTo().homePage();
    Contacts before = app.db().contacts();
    app.contact().create(contact);
    app.contact().returnToHomePage();
    assertEquals(app.contact().count(),before.size()+1);
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before
            .withAdded(contact.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt()))));
    verifyContactListUI();
  }

  @Test
  public void testContactCreation() throws Exception {
    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();
    ContactData newContact = new ContactData()
            .withFirstName("LInna")
            .withLastName("Vetrova")
            .withAddress("Mirova 23-44")
            .withHomePhone("442211")
            .withEmail("petro@mail.ru")
            .inGroup(groups.iterator().next());
    app.contact().create(newContact);
    app.contact().returnToHomePage();
    assertEquals(app.contact().count(),before.size()+1);
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before
            .withAdded(newContact.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt()))));
    verifyContactListUI();
  }
  //тест на определение рабочей директории и наличие нужного файла в ней
  @Test (enabled = false)
  public void testCurrentDir(){
    File currentDir = new File(".");
    System.out.println(currentDir);
    File photo = new File("src\\test\\resources\\layza.jpg");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }
}
