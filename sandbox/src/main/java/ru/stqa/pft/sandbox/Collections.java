package ru.stqa.pft.sandbox;

import java.util.Arrays;
import java.util.List;

public class Collections {

  public static void main(String[] args) {
    //String[] langs = {"JAVA", "C#", "PHP", "Python"};
    List<String> languages = Arrays.asList("JAVA", "C#", "PHP", "Python");

    for (String l: languages) {
      System.out.println("Я хочу выучить " + l);
    }
  }

}
