package ru.stqa.pft.sandbox;

public class my1program {
  public static void main(String[] args) {
    hello("World");
    hello("Elizabeth");
    hello("user");

    Square s = new Square(5);
    System.out.println("Площадь квадрата со стороной " + s.l + " = " + area(s));

    Rectangle r = new Rectangle(4,6);
    System.out.println("Площадь прямоугольника со сторонами " + r.height + " и " + r.width + " = " + area(r));
  }

  public static void hello(String user){
    System.out.println("Hello, " + user + "!");
  }

  public static double area(Square s){
    return s.l * s.l;
  }

  public static double area(Rectangle r){
    return r.height * r.width;
  }
}