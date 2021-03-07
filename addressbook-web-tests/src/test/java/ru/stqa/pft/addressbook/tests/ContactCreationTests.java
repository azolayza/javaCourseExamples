package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    File photo = new File("src/test/resources/photo.png");
    ContactData contact = new ContactData()
            .withFirstName("Alena")
            .withLastName("Minina")
            .withEmail("lena@mail.ru")
            .withPhoto(photo)
            .withGroup("new3");
    app.contact().create(contact);
    app.contact().returnToHomePage();
    assertEquals(app.contact().count(),before.size()+1);
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before
            .withAdded(contact.withId(after.stream().mapToInt((g)-> g.getId()).max().getAsInt()))));
  }

  //тест на определение рабочей директории и наличие нужного файла в ней
  @Test
  public void testCurrentDir(){
    File currentDir = new File(".");
    System.out.println(currentDir);
    File photo = new File("src\\test\\resources\\layza.jpg");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());
  }
}
