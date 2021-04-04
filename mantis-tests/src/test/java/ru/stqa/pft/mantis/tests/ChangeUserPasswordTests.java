package ru.stqa.pft.mantis.tests;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import java.io.IOException;
import java.util.List;

import static org.testng.AssertJUnit.assertTrue;

public class ChangeUserPasswordTests extends TestBase{

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws IOException {
    app.init();
  }

  //@BeforeMethod
  public void startMailServer(){
    app.mail().start();
  }

  @Test
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

  //@AfterMethod(alwaysRun = true)
  public void stopMailServer(){
    app.mail().stop();
  }
}
