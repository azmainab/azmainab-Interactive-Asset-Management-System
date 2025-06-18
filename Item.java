package group2.group2remake;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Item {
    public String name;
    private Rectangle rect = new Rectangle();
    private double xCord;
    private double yCord;
    private double height;
    private double width;
    private double price;
    private Label itemLable = new Label();

    private Color defaultColor = Color.RED;


    public Item(String itemName,double xCord,double yCord, double height, double width, double price){
        this.name = itemName;

        this.xCord = xCord;
        this.yCord = yCord;
        this.height = height;

        this.width = width; 
        this.price=price;

        //get coordinates direction (positive or negative)
        //y is reversed because the properties of layoyt
        int xdirection = 1;
        int ydirection =-1;
        if(xCord<0){
            xdirection = -1;
        }
        //y is reversed direction on the layout
        if(yCord<0){
            ydirection= 1;
        }


        double layoutX = (342)+(xCord*xdirection);
        double layoutY = (328)+(yCord*ydirection);


        //logic that restrain the visualization within the grid Image
        if(xCord<-333){

           layoutX =9;

        }
        //since the rectangle is drew based on using layout coordinates as its left upper corner
        //then if it is exceeding grid image by placing the rectangle on the right side of the grid
        //this logic would prevent the rectangle drew outside of the grid
        if(xCord>333){

           layoutX = 661;

        }
        //same logic, prevent rectangle exceeds the visualization section
        if(yCord<-333){
            layoutY=-2;
        }
        if(yCord>333){
            layoutY=650;
        }

        itemLable.setLayoutX(layoutX);
        itemLable.setLayoutY(layoutY);
        itemLable.setText(this.name);

        setWidth(width);
        setHeight(height);
        rect.setFill(Color.TRANSPARENT);
        rect.setStroke(Color.RED);
        rect.setLayoutX(layoutX);
        rect.setLayoutY(layoutY);
        setxCord(xCord);
        setyCord(yCord);
    }
    public Rectangle getRect() {
        return rect;
    }

    public Label getItemLable() {
        return itemLable;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public double getxCord() {
        return xCord;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getyCord() {
        return yCord;
    }


    public void setDefaultColor(Color color){
        this.defaultColor = color;
        this.rect.setStroke(color);
    }
    public void restoreColor(){
        this.rect.setStroke(this.defaultColor);
    }


    //y layout range is -2 to 650, center is 328
    public void setHeight(double height) {
        this.height = height;

        if(this.yCord>333){
            this.rect.setHeight((Math.abs(yCord-height)));
        }
        else{
            this.rect.setHeight(height);

        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.itemLable.setText(name);
    }

    //x 342 max
    //y 348

    public void setWidth(double width) {
        this.width = width;
        if(this.xCord<-333){
            this.rect.setWidth(Math.abs(xCord+width));

        }
        else{
            this.rect.setWidth(width);
        }
    }

    public void setxCord(double xCord) {
        this.xCord = xCord;
        this.itemLable.setLayoutX(342+xCord);
        this.rect.setLayoutX(342+xCord);
        //make sure the shape and lable isn't exceed the visualization section
        if(xCord>=333){
            this.rect.setLayoutX(661);
            this.itemLable.setLayoutX(661);
        }
        if(xCord<=-333){
            this.rect.setLayoutX(0);
            this.itemLable.setLayoutX(0);
        }
        setWidth(width);
    }

    public void setyCord(double yCord) {
        this.yCord = yCord;
        //make sure the shape isn't exceed the visualization section
        this.itemLable.setLayoutY(328+(-1*yCord));
        this.rect.setLayoutY(328+(-1*yCord));
        //make sure the shape and label isn't exceed the visualization section
        if(yCord>=333){
            this.rect.setLayoutY(-2);
            this.itemLable.setLayoutY(-2);
        }
        if(yCord<=-333){
            this.rect.setLayoutY(650);
            this.itemLable.setLayoutY(650);
        }
        setHeight(height);
    }

    @Override
    public String toString() {
        return this.name;
    }

}