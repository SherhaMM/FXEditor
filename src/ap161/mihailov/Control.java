package ap161.mihailov;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Control implements Initializable {

    @FXML
    private ImageView imgView;
    @FXML
    private TabPane tabPane;
    @FXML
    Button btn1,btn2,btn3;
    @FXML
    Tab mainTab;
    @FXML
    AnchorPane ancRoot;
    @FXML
    ColorPicker clrPick;
    @FXML
    AnchorPane leftAncPane;
    @FXML
    Slider brushSize;
    @FXML
    ScrollPane scrollPane;
    private Color mColor = Color.BLACK;
    private double xPos, yPos, hPos, wPos;
    @FXML
    ToggleGroup drwToggle;
    private double penSize=20;
    int tabNumber=0;

    @FXML
    void createFile(ActionEvent event){
        Tab focused= ImageControl.getInstance().getTabOnFocus();
        ScrollPane sc=new ScrollPane();
        ImageView im=new ImageView();
        sc.setContent(im);
        focused.setContent(sc);
        this.imgView=im;
        ImageControl.getInstance().setImgView(im);
    }

    @FXML
    void addTab(ActionEvent event){
        Tab tab = new Tab();
        ScrollPane sc=new ScrollPane();
        ImageView im=new ImageView();
        sc.setContent(im);
        tab.setText("Unnamed tab");
        tab.setContent(sc);
        this.tabPane.getTabs().add(tab);
        this.tabPane.getSelectionModel().select(tab);
        tabNumber++;
        this.imgView=im;
        ImageControl.getInstance().setImgView(im);
    }
    @FXML
    void mnuOpenAction(ActionEvent event) {
        ImageControl.getInstance().setImgView(this.imgView);
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            createFile(new ActionEvent());
            ImageControl.getInstance().setImg(SwingFXUtils.toFXImage(bufferedImage, null));
//            imgView.setImage(SwingFXUtils.toFXImage(bufferedImage, null));
            ImageControl.getInstance().setImgView(this.imgView);
            ImageControl.getInstance().setImg(SwingFXUtils.toFXImage(bufferedImage, null));
//        ImageControl.getInstance().setImgToView(ImageControl.getInstance().getImg());
        } catch (IOException ex) {
        //   ex.printStackTrace();
        }

    }
    @FXML
    void mnuSaveAction(ActionEvent event){


        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(ImageControl.getInstance().getImg(),null),"png",file);
//                ImageIO.write(SwingFXUtils.fromFXImage(ImageControl.getInstance().getImgView().getImage(),
//                        null), "png", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    private void zoomIn(){
        //TODO
        Image n;
        double height,width;
        n=this.imgView.getImage();
        height=n.getHeight();
        width=n.getWidth();
        System.out.println(height+"- высота, "+width+"- ширина");
    }
    @FXML
    private void zoomOut(){
    // TODO: 06.05.18
    }

    @FXML
    void mnuGrayscale(ActionEvent event) {

        if (ImageControl.getInstance().getImg() == null)
            return;
//         ImageControl.getInstance().setImgView(this.imgView);
        Image greyImage = Transform.transform(ImageControl.getInstance().getImg(), Color::grayscale);
//        this.imgView.setImage(greyImage);
        ImageControl.getInstance().setImg(greyImage);


    }
    @FXML
    void mnuBrightscale(ActionEvent event) {
        if (ImageControl.getInstance().getImg() == null)
            return;

//        ImageControl.getInstance().setImgView(this.imgView);
        Image brightImage = Transform.transform(ImageControl.getInstance().getImg(), Color::brighter);
//        this.imgView.setImage(brightImage);
        ImageControl.getInstance().setImg(brightImage);


    }
    @FXML
    void mnuDarkscale(ActionEvent event) {
        if (ImageControl.getInstance().getImg() == null)
            return;
//        ImageControl.getInstance().setImgView(this.imgView);
        Image brightImage = Transform.transform(ImageControl.getInstance().getImg(), Color::darker);
//        this.imgView.setImage(brightImage);
        ImageControl.getInstance().setImg(brightImage);


    }

    @FXML
    private ToggleButton drwCircle,drwPixel;
    ArrayList<Shape> removeShapes = new ArrayList<>(1000);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize set img");
        ImageControl.getInstance().setTabOnFocus(mainTab);
        ImageControl.getInstance().setImgView(this.imgView);
        drwCircle.setToggleGroup(drwToggle);//TODO
        drwPixel.setToggleGroup(drwToggle);

        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
            ImageControl.getInstance().setTabOnFocus(newValue);
            System.out.println("Tab Listner выбрана вкладка - " + newValue);

            }
        });

                clrPick.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        mColor = clrPick.getValue();
                    }
                });

        brushSize.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                penSize = brushSize.getValue();
            }
        });
//        imgView.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, new EventHandler<javafx.scene.input.MouseEvent>() {
//            @Override
//            public void handle(javafx.scene.input.MouseEvent event) {
//
//                xPos= (int) event.getX();
//                yPos= (int) event.getY();
//                event.consume();
//            }
//        });
//        imgView.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_RELEASED, new EventHandler<javafx.scene.input.MouseEvent>() {
//            @Override
//            public void handle(javafx.scene.input.MouseEvent event) {
//                System.out.println("Mouse released"+event.getSource());
//                SnapshotParameters snapshotParameters= new SnapshotParameters();
//                snapshotParameters.setViewport(new Rectangle2D(0,0,imgView.getFitWidth(),imgView.getFitHeight()));
//                Image snapshot= ancRoot.snapshot(snapshotParameters, null);
//                ImageControl.getInstance().setImgToView(snapshot);
//                ancRoot.getChildren().removeAll(removeShapes);
//                removeShapes.clear();
//
//            }
//        });


        imgView.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_DRAGGED, new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent me) {

                if(drwCircle.isSelected()) {
                    xPos = me.getX();
                    yPos = me.getY();

//                    int nShape = 0;
//                DrawShapes.getInstance().drawShape(imgView,ancRoot,xPos,yPos,penSize,mColor);
                    Shape shape = new Circle(xPos, yPos, 10);
                    shape = new Circle(xPos, yPos, penSize);

                    // shape.setStroke(mColor);
                    shape.setFill(mColor);

                     ancRoot.getChildren().add(shape);
//                    removeShapes.add(shape);
                    me.consume();
                }
                if(drwPixel.isSelected()){

                    xPos = me.getX();
                    yPos = me.getY();

                    Shape shape = new Circle(xPos, yPos, 10);
                    shape = new Circle(xPos, yPos, 2);

                    // shape.setStroke(mColor);
                    shape.setFill(mColor);

                    ancRoot.getChildren().add(shape);

//                    removeShapes.add(shape);
                    me.consume();
                }
            }

        });

    }

    @FXML
    void showHelp(ActionEvent event){
    final Stage stage= new Stage();
       Group rootGroup= new Group();
        Scene scene= new Scene(rootGroup,700,500, Color.WHITESMOKE);
        stage.setScene(scene);
        stage.setTitle("Help blyat pomogi eptA");
        stage.centerOnScreen();
        stage.show();
        Text text= new Text(100,240,"FxEditor IKAES Group");
        text.setFill(Color.DODGERBLUE);
        text.setEffect(new Lighting());
        text.setFont(Font.font(Font.getDefault().getFamily(),50));
        rootGroup.getChildren().add(text);
        stage.centerOnScreen();
    }

    @FXML
    private void exit(){
        System.exit(0);
    }
}
