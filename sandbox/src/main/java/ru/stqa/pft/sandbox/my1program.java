package ru.stqa.pft.sandbox;

public class my1program {
  public static void main(String[] args) {
    hello("World");
    hello("Elizabeth");
    hello("user");

    Square s = new Square(5);
    System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

    Rectangle r = new Rectangle(4,6);
    System.out.println("Площадь прямоугольника со сторонами " + r.height + " и " + r.width + " = " + r.area());

    //ДЗ2 - вывод значения расстояния между точками р1 и р2
    Point p1 = new Point(1,3);
    Point p2 = new Point(5,8);
    System.out.println("Расстояние между точками p1 и р2 " + " = " + p1.distance(p1, p2));
  }

  public static void hello(String user){
    System.out.println("Hello, " + user + "!");
  }
}