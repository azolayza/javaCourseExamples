package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.Users;
import java.io.IOException;
import java.util.List;
import static org.testng.AssertJUnit.assertTrue;

public class ChangeUserPasswordTests extends TestBase{

  @BeforeSuite(alwaysRun = true)
  public void setUp() throws IOException {
    app.init();
  }

  @BeforeMethod
  public void startMailServer(){
    app.mail().start();
  }

  @Test
  public void testChangeUserPassword() throws Exception {
  String user = "liza";
  String email = "liza@localhost";
  String password = "password";
  String passwordNew = "password2";
  //авторизация админа через веб и смена пароля юзеру с id-4
  app.session().login("Administrator", "root");
  app.session().usersList();
  app.session().selectUser(String.valueOf(mantisUser.getId()));
  app.session().changePassword();
  //Ожидаем письмо и берем ссылку для сброса пароля
  List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
  String confirmationLink = app.session().findConfirmationLink(mailMessages, email);
  app.session().updateUserPassword(confirmationLink, user, passwordNew);
  //Логин по http протоколу юзером с новым паролем
  HttpSession httpSession = app.newSession();
  assertTrue(httpSession.login(user, passwordNew));
  assertTrue(httpSession.isLoggedInAs("liza"));
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer(){
    app.mail().stop();
  }
}
