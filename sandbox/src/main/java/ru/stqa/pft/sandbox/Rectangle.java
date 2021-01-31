package ru.stqa.pft.sandbox;

public class Rectangle {
  double height;
  double width;

  public Rectangle(double height, double width){
    this.height = height;
    this.width = width;
  }

  public double area(){
    return this.height * this.width;
  }
}
