package ap161.mihailov;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.fxml.FXML;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DrawShapes  {
    ArrayList<Shape> removeShapes = new ArrayList<>(1000);
    private double xPos, yPos, hPos, wPos;

    public static DrawShapes getInstance(){
        return new DrawShapes();
    }
  public void drawShape(ImageView imgView, AnchorPane ancRoot, double xPos, double yPos, double penSize, Color mColor){
        imgView.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_DRAGGED, new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent me) {


                int nShape = 0;

                Shape shape = new Circle(xPos, yPos, 10);
                shape = new Circle(xPos, yPos, penSize);

                // shape.setStroke(mColor);
                shape.setFill(mColor);

                ancRoot.getChildren().add(shape);
                removeShapes.add(shape);
                me.consume();

            }
        });


    }


}
