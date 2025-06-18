package group2.group2remake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.css.Size;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;


public class Drone {
    //if there are multiple drones' droneID will be used to locate speciic drone
    private int DroneID;
    //store coordinate position, the index 0 stores x and index 1 stores the y
    private double[] Position = new double[2];
    //angles, may be used
    private double angleInDegree;
    //speed for how drone is moving on visualization
    private double constantSpeed = 10;
    //track the circle on the interface
    private ImageView indicator;
    //price, for implementation part 2
    private double price = 100;



    //for patrolling visualization use, will not be accessed by outside
    private int count = 0;
    private double SizeX = 0.8;
    //most used constructor when drone is added in interface
    public Drone(int ID, double xCord, double yCord,ImageView circle){
        this.DroneID = ID;
        this.Position[0]=xCord;
        this.Position[1]=yCord;
        this.angleInDegree=0;
        this.indicator = circle;
    }
    //constructor with angle, same as other consturctor but set the angle too;
    public Drone(int ID,double xCord,double yCord,double degree,ImageView circle){
        this.DroneID = ID;
        this.Position[0]=xCord;
        this.Position[1]=yCord;
        this.angleInDegree=degree;
        this.indicator = circle;
    }
    //return droneID
    public int getDroneID() {
        return this.DroneID;
    }

    //get Position
    public double[] getPosition() {
        return this.Position;
    }

    //get drone's current angle in degree
    public double getAngleInDegree(){
        return this.angleInDegree;}
    //get drone's current angle in radian
    public double getAngleInRadian(){
        return Math.toRadians(this.angleInDegree);}

    //get drone price
    public double getPrice(){
        return this.price;
    }
    //set drone price
    public void setPrice(double price) {
        this.price = price;
    }

    //change DroneID
    public void setDroneID(int i) {
        this.DroneID = i;
    }
    //return x coordinate
    public double getX(){
        return this.Position[0];
    }
    //return y coordinate
    public double getY(){
        return  this.Position[1];
    }
    //only accept double array with size of 2, the index 0should be x coordinate and index 1 should be y coordinate
    //set the drone's position to a specific value
    public void goTo(double[] i) {
        //called to compute absolute length between current position and destination
        double length = ComputeLength(this.Position,i);
        ComputePath(i,length);
    }
    //set x coordinate individually
    public void setX(double x){
        this.Position[0]=x;
    }
    //set y coordinate individually
    public void setY(double y){
        this.Position[1]=y;
    }
    //set drone's current angle
    public void setAngleInDegree(double angle){
        this.angleInDegree = angle;
    }
    //compute the length using Pythagorean proposition
    private double ComputeLength(double[] startPoint, double[] destination){
        double disX = destination[0]-startPoint[0];
        double disY = destination[1]-startPoint[1];
        return Math.sqrt(disX*disX+disY*disY);
    }
    //Compute the x's and y's direction of coordinates system
    //compute steps needs to take based on current speed
    //compute how much that x and y needs to move for each step
    private void ComputePath(double[] destination,double length){
        int xDirection;
        int yDirection;
        if(destination[0]<=0){
            xDirection = -1;
        } else {
            xDirection = 1;
        }
        if(destination[1]<=0){
            yDirection =-1;
        } else {
            yDirection = 1;
        }
        double disX = Math.abs((destination[0] - this.Position[0]));
        double disY = Math.abs((destination[1] - this.Position[1]));

        int numSteps = (int)(10*(length/constantSpeed));


        double stepSizeX = disX / numSteps*xDirection;
        double stepSizeY = disY / numSteps*yDirection;


        ExecutePath(numSteps,stepSizeX,stepSizeY,length);
    }

    //set up a timeline for visualization
    //a forloop that change both locations of drone's and its visual indicator's layout position
    private void ExecutePath(int numSteps,double stepSizeX,double stepSizeY,double length){
        Controller.droneRun();

        // Check for zero length and numSteps
        if (length == 0 || numSteps == 0) {
            System.out.println("Length or NumSteps is zero, cannot execute path");
            return;
        }

        // Using JavaFX Timeline for animation
        Timeline timeline = new Timeline();
        timeline.setCycleCount(numSteps);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), // Duration per step (adjust as needed)
                event -> {

                    // Update position
                    this.Position[0] += stepSizeX;
                    this.Position[1] += stepSizeY;

                    if(this.indicator.getLayoutX()>9&&this.indicator.getLayoutX()<661){
                        this.indicator.setLayoutX(this.indicator.getLayoutX()+stepSizeX);
                    }
                    if (this.indicator.getLayoutY() > -2 && this.indicator.getLayoutY() < 650) {
                        this.indicator.setLayoutY(this.indicator.getLayoutY() + (stepSizeY*(-1)));
                    }



                }));


        timeline.play();
        timeline.setOnFinished(event ->{
            Controller.droneStop();
        });
    }



    //toString method that are used for display on TreeItem
    public String toString(){
        return "Drone:"+this.DroneID;
    }

    //for now it just turns its indicator invisible
    public void remove(){
        this.indicator.setOpacity(0);
    }
    //abandoned method, spend three days in a row on this, causing troubles
    //thread, timeLine, countDownLatch, everything tried, it is :
    /*thread blocking, or otherwise the interface didn't update indicator's layout even though the platform.runLater() is used
     * or it is just not moving at all. for loop is not executed for some reason, which is not logical
     * or multiple function is executing on same indicator at same time
     * or setup a timeline.setFOnfinished, the method inside it's never been called
     * or the method inside is called but never taken effect on current indicator
     * this function looks simple but felt cursed when implementing */
    public void patrol() {
        //0:NumsStep
        //1:stepSizeX
        //2:stepSizeY
        //3:length
        Controller.droneRun();
        double[] leftUpperCorner = patrolPathCalculator(new double[] {-333,333},ComputeLength(this.Position,new double[]{-333,333}));

        Timeline timeline = new Timeline();
        timeline.setCycleCount((int)leftUpperCorner[0]);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(10), // Duration per step (adjust as needed)
                event -> {
                    // Update position
                    this.Position[0] += leftUpperCorner[1];
                    this.Position[1] += leftUpperCorner[2];

                    if(this.indicator.getLayoutX()>9&&this.indicator.getLayoutX()<661){
                        this.indicator.setLayoutX(this.indicator.getLayoutX()+leftUpperCorner[1]);
                    }
                    if (this.indicator.getLayoutY() > -2 && this.indicator.getLayoutY() < 650) {
                        this.indicator.setLayoutY(this.indicator.getLayoutY() + (leftUpperCorner[2]*(-1)));
                    }


                }));
        timeline.play();

        int horizontalSteps = 815;

        Timeline scan = new Timeline();
        scan.setCycleCount(52975);
        scan.getKeyFrames().add(new KeyFrame(Duration.millis(10),
                event -> {
                    if (this.count == horizontalSteps) {
                        this.SizeX *= (-1);
                        this.count = 0;
                        this.Position[1] -= 10;
                        this.indicator.setLayoutY(this.indicator.getLayoutY() + 10);
                        System.out.println("OneRound");
                    } else {
                        count++;
                        this.Position[0] += SizeX;
                        this.indicator.setLayoutX(this.indicator.getLayoutX() + SizeX);
                    }
                }));
        scan.setOnFinished(event -> {
            Controller.droneStop();
        });
        timeline.setOnFinished(event -> {
            System.out.println("I have finished!");
            scan.play();

        });


     /*

        for (int down = 0; down < verticalRepetitions; down++) {
            // Move right
            for (int right = 0; right < horizontalSteps; right++) {
                this.Position[0]+=horizontalSteps;
                this.indicator.setLayoutX(this.indicator.getLayoutX()+horizontalSteps);
            }
            this.indicator.setLayoutY(this.indicator.getLayoutY()+verticalStepSize);
            // Move left
            for (int left = 0; left < horizontalSteps; left++) {
                this.Position[0]-=horizontalSteps;
                this.indicator.setLayoutX(this.indicator.getLayoutX()-horizontalSteps);
            }
        }*/

    }

    private double[] patrolPathCalculator(double[] destination,double length){
        int xDirection;
        int yDirection;
        if(destination[0]<=0){
            xDirection = -1;
        } else {
            xDirection = 1;
        }
        if(destination[1]<=0){
            yDirection =-1;
        } else {
            yDirection = 1;
        }
        double disX = Math.abs((destination[0] - this.Position[0]));
        double disY = Math.abs((destination[1] - this.Position[1]));

        int numSteps = (int)(10*(length/constantSpeed));


        double stepSizeX = disX / numSteps*xDirection;
        double stepSizeY = disY / numSteps*yDirection;


        return new double[]{numSteps, stepSizeX, stepSizeY, length};
    }

}