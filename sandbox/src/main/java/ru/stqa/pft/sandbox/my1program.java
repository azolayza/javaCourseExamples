package ru.stqa.pft.sandbox;

public class my1program {
  public static void main(String[] args) {
    hello("World");
    hello("Elizaveta");
    hello("user");

    double len = 5;
    System.out.println("Площадь квадрата со стороной " + len + " = " + area(len));

    double height = 4;
    double width = 6;
    System.out.println("Площадь прямоугольника со сторонами " + height + " и " + width + " = " + area(height, width));
  }

  public static void hello(String user){
    System.out.println("Hello, " + user + "!");
  }

  public static double area(double l){
    return l * l;
  }

  public static double area(double height, double width){
    return height*width;
  }
}