package group2.group2remake;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;




//the center coordinates of visualization is x= 342, y = 328 make it as the origin(0,0)

//range of grid image lay out is x= 14, y = 0, to x = 653, y = 642
//the range of x is -333 to 333, or for layout is 9 to 661
//the range of y is -333 to 333, or for layout is -2 to 650
public class Controller implements Initializable {
    @FXML
    private TreeView assetTree;
    @FXML
    private AnchorPane visualAchor;

    //display variables
    @FXML
    private Text nameDisplay;
    @FXML
    private Text xDisplay;
    @FXML
    private Text yDisplay;
    @FXML
    private Text priceDisplay;
    @FXML
    private Text totalPriceDisplay;
    @FXML
    private Text widthDisplay;
    @FXML
    private Text heightDisplay;
    //end of display variables
    @FXML
    private ListView optionList;

    private TreeItem currentSelectItem;
    private Item lastItem;
    @FXML
    private ImageView droneImage;
    private Drone onlyDrone;

    private static boolean droneRunning = false;

    //initialize the interface
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.onlyDrone = new Drone(810,0,0,droneImage);
        Item root = new Item("root",0,0,100,100,0);
        root.setDefaultColor(Color.GREEN);
        TreeItem rootItem = new TreeItem(root);
        assetTree.setRoot(rootItem);
        visualAchor.getChildren().add(root.getRect());
        visualAchor.getChildren().add(root.getItemLable());

    }


    //when item is selected in the treeView, do something here
    public void selectItem(){
        TreeItem item = (TreeItem)assetTree.getSelectionModel().getSelectedItem();
        if(item!=null){
            if (lastItem != null) {
                lastItem.restoreColor();
            }

            lastItem = (Item)item.getValue();
            lastItem.getRect().setStroke(Color.BLUE);
            this.currentSelectItem=item;
            if(item.equals(assetTree.getRoot())){
                //clear the option first then adding new item
                optionList.getItems().clear();
                optionList.getItems().addAll("Add Item", "Change X", "Change Y","Change Width","Change Height");

            }
            else if(item.getValue() instanceof Item){
                //clear the option first then adding new item
                optionList.getItems().clear();
                optionList.getItems().addAll("Change Name","Add Item", "Change X", "Change Y","Change Width","Change Height","Change Price","Delete Item");
                Item displayUpdate = (Item)item.getValue();
                this.nameDisplay.setText(displayUpdate.getName());
                this.xDisplay.setText(String.valueOf(displayUpdate.getxCord()));
                this.yDisplay.setText(String.valueOf(displayUpdate.getyCord()));
                this.widthDisplay.setText(String.valueOf(displayUpdate.getWidth()));
                this.heightDisplay.setText(String.valueOf(displayUpdate.getHeight()));
                this.priceDisplay.setText(String.valueOf(displayUpdate.getPrice()));
                this.totalPriceDisplay.setText(String.valueOf(getTotalPrice(currentSelectItem)+ lastItem.getPrice()));
            }

        }
    }


    //basically, this will retrieve the option selected from the ListView optionList
    // and then it will use if-else statement to identify which option that user has chosed
    //then it will executed corresponding function
    //TDDO: add identify logic, and implement corresonding function
    //Functions: 1. change Name, 2. Change price, 3. Change X .4 Change Y, 5.change width 6. change height7. delete items 8. add items

    public void optionSelect(){
        OptionSelect(optionList.getSelectionModel().getSelectedItem().toString());
    }
    public void OptionSelect(String selectedOption) {
        switch (selectedOption) {
            case "Change Name":
                // Call the function to change the name
                changeName();
                break;
            case "Change Price":
                // Call the function to change the price
                changePrice();
                break;
            case "Change X":
                // Call the function to change X
                changeX();
                break;
            // Add more cases for other options
            case "Change Y":
                changeY();
                break;
            case "Add Item":
                addItem();
                break;
            case "Change Width":
                changeWidth();
                break;
            case "Change Height":
                changeHeight();
                break;
            case "Delete Item":
                deleteItem();
                break;

            default:
                // Handle default case or do nothing
                break;
        }
    }

// Implement your corresponding functions here


    //thses function below requires a pop window that retrieve one input value from the user
    //they will closed upon user finish enter the input
    public void changeName() {
        Stage changeNameWindow = new Stage();
        changeNameWindow.initModality(Modality.APPLICATION_MODAL);
        changeNameWindow.setTitle("Change name");
        Label label = new Label("Enter Name:");
        TextField inputField = new TextField();
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String userInput = inputField.getText();
            changeNameWindow.close();

            if(currentSelectItem.getValue() instanceof Item && userInput!=null){
                Item item = (Item)currentSelectItem.getValue();
                item.setName(userInput);
                //this refreshed the treeItem and make the changes appear immediately
                currentSelectItem.setValue(null);
                currentSelectItem.setValue(item);
                //this would update the display value
                selectItem();
            }


        });

        VBox layout = new VBox(10, label, inputField, submitButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 250, 150);
        changeNameWindow.setScene(scene);
        changeNameWindow.showAndWait();

    }

    public void changePrice() {
        // Logic to change the price
        Stage changePriceWindow = new Stage();
        changePriceWindow.initModality(Modality.APPLICATION_MODAL);
        changePriceWindow.setTitle("Change Price");
        Label label = new Label("Enter Price:");
        TextField inputField = new TextField();
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String userInput = inputField.getText();
            changePriceWindow.close();

            if(currentSelectItem.getValue() instanceof Item && userInput!=null){
                Item item = (Item)currentSelectItem.getValue();
                item.setPrice(Double.parseDouble(userInput));
                //this refreshed the treeItem and make the changes appear immediately
                currentSelectItem.setValue(null);
                currentSelectItem.setValue(item);
                //this would update the display value
                selectItem();
            }


        });

        VBox layout = new VBox(10, label, inputField, submitButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 250, 150);
        changePriceWindow.setScene(scene);
        changePriceWindow.showAndWait();
    }

    public void changeX() {
        // Logic to change X
        Stage changeXWindow = new Stage();
        changeXWindow.initModality(Modality.APPLICATION_MODAL);
        changeXWindow.setTitle("Change X coordinate");
        Label label = new Label("Enter x coordinate:");
        TextField inputField = new TextField();
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String userInput = inputField.getText();
            changeXWindow.close();

            if(currentSelectItem.getValue() instanceof Item && userInput!=null){
                Item item = (Item)currentSelectItem.getValue();
                item.setxCord(Double.parseDouble(userInput));
                //this refreshed the treeItem and make the changes appear immediately
                currentSelectItem.setValue(null);
                currentSelectItem.setValue(item);
                //this would update the display value
                selectItem();
            }


        });

        VBox layout = new VBox(10, label, inputField, submitButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 250, 150);
        changeXWindow.setScene(scene);
        changeXWindow.showAndWait();
    }
    public void changeY(){
        // Logic to change Y
        Stage chnageYWindow = new Stage();
        chnageYWindow.initModality(Modality.APPLICATION_MODAL);
        chnageYWindow.setTitle("Change Y coordinate");
        Label label = new Label("Enter Y coordinate:");
        TextField inputField = new TextField();
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String userInput = inputField.getText();
            chnageYWindow.close();

            if(currentSelectItem.getValue() instanceof Item && userInput!=null){
                Item item = (Item)currentSelectItem.getValue();
                item.setyCord(Double.parseDouble(userInput));
                //this refreshed the treeItem and make the changes appear immediately
                currentSelectItem.setValue(null);
                currentSelectItem.setValue(item);
                //this would update the display value
                selectItem();
            }


        });

        VBox layout = new VBox(10, label, inputField, submitButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 250, 150);
        chnageYWindow.setScene(scene);
        chnageYWindow.showAndWait();
    }

    public void changeWidth(){
        Stage changeWidthWindow = new Stage();
        changeWidthWindow.initModality(Modality.APPLICATION_MODAL);
        changeWidthWindow.setTitle("Change Width");
        Label label = new Label("Enter Width:");
        TextField inputField = new TextField();
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String userInput = inputField.getText();
            changeWidthWindow.close();
            if(currentSelectItem.getValue() instanceof Item && userInput!=null){
                Item item = (Item)currentSelectItem.getValue();
                item.setWidth(Double.parseDouble(userInput));
                //this refreshed the treeItem and make the changes appear immediately
                currentSelectItem.setValue(null);
                currentSelectItem.setValue(item);
                //this would update the display value
                selectItem();
            }


        });

        VBox layout = new VBox(10, label, inputField, submitButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 250, 150);
        changeWidthWindow.setScene(scene);
        changeWidthWindow.showAndWait();

    }
    public void changeHeight(){
        Stage changeHeightWindow = new Stage();
        changeHeightWindow.initModality(Modality.APPLICATION_MODAL);
        changeHeightWindow.setTitle("Change Height");
        Label label = new Label("Enter Height:");
        TextField inputField = new TextField();
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String userInput = inputField.getText();
            changeHeightWindow.close();

            if(currentSelectItem.getValue() instanceof Item && userInput!=null){
                Item item = (Item)currentSelectItem.getValue();
                item.setHeight(Double.parseDouble(userInput));
                //this refreshed the treeItem and make the changes appear immediately
                currentSelectItem.setValue(null);
                currentSelectItem.setValue(item);
                //this would update the display value
                selectItem();
            }


        });

        VBox layout = new VBox(10, label, inputField, submitButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 250, 150);
        changeHeightWindow.setScene(scene);
        changeHeightWindow.showAndWait();
    }
    //thses function above requires a pop window that retrieve one input value from the user

    //pops up a window that let user input name,price,x,y,width,height and create a Item class object based on that data
    // then it will add it onto a TreeItem, the it will add this treeItem as Children of selected Item
    public void addItem(){
        Stage changeNameWindow = new Stage();
        changeNameWindow.initModality(Modality.APPLICATION_MODAL);
        changeNameWindow.setTitle("AddItem");
        Label labelName = new Label("Enter Name:");
        TextField inputNameField = new TextField();
        Label labelX = new Label("Enter XCord:");
        TextField inputXField = new TextField();
        Label labelY = new Label("Enter Ycord:");
        TextField inputYField = new TextField();
        Label labelWidth = new Label("Enter Width:");
        TextField inputWidthField = new TextField();
        Label labelHeight = new Label("Enter Height:");
        TextField inputHeightField = new TextField();
        Label labelPrice = new Label("Enter Price:");
        TextField inputPriceField = new TextField();


        Button submitButton = new Button("Submit");
        try {

            submitButton.setOnAction(e -> {
                double xCordInput =0;
                double yCordInput =0;
                double heightInput =0;
                double widthInput =0;
                double priceInput =0;

                String nameInput = inputNameField.getText();
                xCordInput = Double.parseDouble(inputXField.getText());
                yCordInput = Double.parseDouble(inputYField.getText());
                heightInput = Double.parseDouble(inputHeightField.getText());
                widthInput = Double.parseDouble(inputWidthField.getText());
                priceInput = Double.parseDouble(inputPriceField.getText());
                changeNameWindow.close();

                if (nameInput != null && xCordInput != 0 && yCordInput != 0 && heightInput != 0 && widthInput!= 0 && priceInput != 0) {
                    Item item = new Item(nameInput,xCordInput,yCordInput,heightInput,widthInput,priceInput);
                    TreeItem newItem = new TreeItem(item);

                    currentSelectItem.getChildren().add(newItem);
                    this.visualAchor.getChildren().add(item.getItemLable());
                    this.visualAchor.getChildren().add(item.getRect());

                    selectItem();
                }
            });


            VBox layout = new VBox(10, labelName, inputNameField,labelX,inputXField,labelY,inputYField,labelHeight,inputHeightField,labelWidth,inputWidthField,labelPrice,inputPriceField, submitButton);
            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout, 250, 550);
            changeNameWindow.setScene(scene);
            changeNameWindow.showAndWait();
        }
        catch(Exception ex){
            System.out.println("Something wrong within user's input");
            ex.printStackTrace();
        }
    }
    public void deleteItem(){
        if(currentSelectItem!=null){
            Item deleteItem = (Item)currentSelectItem.getValue();
            this.visualAchor.getChildren().remove(deleteItem.getRect());
            this.visualAchor.getChildren().remove(deleteItem.getItemLable());
            currentSelectItem.getParent().getChildren().remove(currentSelectItem);

        }
    }

    public void Patrol(){
        if(!droneRunning) {
            this.onlyDrone.patrol();
        }
        else{
            System.out.println("Drone is executing last mission!");
        }
    }
    public void visitSelectItem(ActionEvent e){
        if(!droneRunning) {
            if (currentSelectItem != null) {
                Item vistThis = (Item) currentSelectItem.getValue();
                double x = vistThis.getxCord();
                double y = vistThis.getyCord();
                double[] visitItemPosition = {x, y};
                this.onlyDrone.goTo(visitItemPosition);
            }
        }
        else{
            System.out.println("Drone is executing last mission!");
        }
    }
    public static void droneRun(){
        droneRunning = true;
    }
    public static void droneStop(){
        droneRunning = false;
    }

    // Implement other functions as needed
    public double getTotalPrice(TreeItem ItemList){
        double totalPrice = 0;
        ObservableList<TreeItem> children = ItemList.getChildren();
        //if have other inside
        if(!children.isEmpty()){
            for (TreeItem child : children ) {
                ObservableList<TreeItem> childrenSChildren = child.getChildren();
                if(!childrenSChildren.isEmpty()){
                    totalPrice+=getTotalPrice(child);
                }
                Item childItem =(Item) child.getValue();
                totalPrice+=childItem.getPrice();
            }
        }

        return totalPrice;

    }




}