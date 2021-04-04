package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class ChangeUserPasswordTests extends TestBase{

  @BeforeMethod
  public void startMailServer(){
    app.mail().start();
  }

  @Test (enabled = false)
  public void testChangeUserPassword() throws Exception {
  //авторизация админа через веб и смена пароля юзеру
  app.session().login("Administrator", "root");
  app.session().usersList();
  //выбираем юзера
  UserData selectedUser = app.db().mantisUser().withEmail("liza@localhost");
  Thread.sleep(2000);
  app.session().selectUser(String.valueOf(selectedUser.getId()));
  app.session().changePassword();
  String userName = selectedUser.getUsername();
     boolean userExist = app.james().doesUserExist(userName);
      if (!userExist) {
        app.james().createUser(userName, "password");
      } else {
        app.james().drainEmail(userName, "password");
      }
  //Ожидаем письмо и берем ссылку для сброса пароля
  List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
  String confirmationLink = app.session().findConfirmationLink(mailMessages,  userName + "@localhost");
  app.session().changePassword(confirmationLink, "password1");
  app.session().updateUserPassword(confirmationLink, userName, "password1");
    //Логин по http протоколу юзером с новым паролем
  HttpSession httpSession = app.newSession();
  assertTrue(httpSession.login(userName, "password1"));
  }


  @Test
  public void testChangePasswordByAdmin() throws Exception {
    String email = "user1@localhost";
    String userName = "user1";
    String newPassword = "password1";
    app.session().login("Administrator", "root");
    app.session().usersList();
    app.session().openUserPage(userName);
    app.session().changePassword();
    //Ожидаем письмо и берем ссылку для сброса пароля
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    String confirmationLink = app.session().findConfirmationLink(mailMessages,  email);
    app.session().updateUserPassword(confirmationLink, userName, newPassword);
    //Логин по http протоколу юзером с новым паролем
    HttpSession httpSession = app.newSession();
    assertTrue(httpSession.login(userName, newPassword));
  }


  @AfterMethod(alwaysRun = true)
  public void stopMailServer(){
    app.mail().stop();
  }
}
