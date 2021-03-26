package ru.stqa.pft.mantis.appmanager;


import org.openqa.selenium.WebDriver;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ApplicationManager {
  private final String browser;
  private final Properties properties;
  WebDriver wd;

  public ApplicationManager(String browser) {
    this.browser = browser;
    properties = new Properties();
  }

  public void init() throws IOException {
    String target = System.getProperty("target", "local");
    properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

    wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
    wd.get(properties.getProperty("web.baseUrl"));
  }
    public void stop() {
      wd.quit();
    }

    public HttpSession newSession(){
    return new HttpSession(this);
    }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }
}
