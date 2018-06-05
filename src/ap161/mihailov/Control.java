package ap161.mihailov;

import javafx.beans.binding.StringBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Control implements Initializable {


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
    @FXML
    ToggleGroup drwToggle;
    private ArrayList<Shape> removeShapes = new ArrayList<>(1000);

    public enum Pen {
        CIR, SQR, LINE, DROP, REC ,ELLPS
    }
    @FXML
    private ImageView imgView;
    @FXML
    private TabPane tabPane;
    @FXML
    private ToggleButton drwCircle, drwPixel, drwDrop, drwLine,drwRec,drwEllipse;
    private Color mColor = Color.BLACK;
    private double xPos, yPos, hPos, wPos,xScroll,yScroll;
    private double penSize = 5;
    private Pen penStyle = Pen.CIR;


    @FXML
    private void createFile(ActionEvent event) {
        Tab focused = ImageControl.getInstance().getTabOnFocus();
        ImageView im = new ImageView();
        ScrollPane sc = new ScrollPane();
        AnchorPane an = new AnchorPane();
//        im.setFitWidth(800);
//        im.setFitHeight(600);
        sc.setContent(im);
        sc.setPrefWidth(800);
        sc.setPrefHeight(600);
        an.setRightAnchor(sc,0.);
        an.setBottomAnchor(sc,0.);
        an.setTopAnchor(sc,0.);
        an.setLeftAnchor(sc,0.);
        an.getChildren().add(sc);
        focused.setContent(an);
        ImageControl.getInstance().setAncPane(an);
        ImageControl.getInstance().setImgView(im);
        ImageControl.getInstance().setScrollPane(sc);
        addListener(event);
    }


    @FXML
    public void addTab(ActionEvent event) {
        Tab tab = new Tab();
        createFile(event);
        tab.setText("Unnamed tab");
//        AnchorPane an= new AnchorPane();
//        ScrollPane sc=new ScrollPane();
//        ImageView im=new ImageView();
//
//        sc.setContent(im);
//        an.getChildren().add(sc);

//        tab.setContent(sc);

        this.tabPane.getTabs().add(tab);
        this.tabPane.getSelectionModel().select(tab);
//        ImageControl.getInstance().setImgView(im);
//        ImageControl.getInstance().setAncPane(an);
    }
    @FXML
    public void renameTab(){
        Tab currentTab = ImageControl.getInstance().getTabOnFocus();

        String string= new String();
        final Stage stage = new Stage();
        Group rootGroup = new Group();
        Scene scene = new Scene(rootGroup, 188, 80, Color.WHITESMOKE);
        stage.setScene(scene);
        stage.setTitle("Rename Tab");
        stage.centerOnScreen();
        stage.show();
        TextField datafield = new TextField();
        Button btn = new Button("Enter");
        btn.setOnAction(event -> stage.close());
        datafield.setPromptText("Enter tab name");
        datafield.setMaxHeight(TextField.USE_PREF_SIZE);
        datafield.setMaxWidth(TextField.USE_PREF_SIZE);
        VBox vBox= new VBox(7);
        vBox.setPadding(new Insets(12));
        vBox.getChildren().addAll(datafield,btn);
        vBox.setAlignment(Pos.CENTER);
        rootGroup.getChildren().add(vBox);
        currentTab.textProperty().bind(datafield.textProperty());
    }

//    @FXML
//    public void undo(){
//        if(!removeShapes.isEmpty()) {
//            AnchorPane an= ImageControl.getInstance().getAncPane();
//            Shape shape=removeShapes.get(removeShapes.size()-1);
//            an.getChildren().remove(shape);
//            shape=null;
//
//            for (int i=0;0<removeShapes.size()-1;i++) {
//                System.out.println(removeShapes.get(i));
//
//            }
//        }
//    }

    @FXML
    private  void mnuOpenAction(ActionEvent event) {
        ImageControl.getInstance().setImgView(this.imgView);
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showOpenDialog(null);

        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            createFile(new ActionEvent());
            //   ImageControl.getInstance().setImgView(this.imgView);
            ImageControl.getInstance().setImg(SwingFXUtils.toFXImage(bufferedImage, null));
            addListener(event);
        } catch (IOException ex) {
            System.err.println(ex.getCause());
        }

    }

    @FXML
    private void mnuSaveAction(ActionEvent event) {


        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(ImageControl.getInstance().getImg(), null), "png", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    private void zoomIn() {
        ImageView im = ImageControl.getInstance().getImgView();
        double valueX = im.getScaleX();
        double valueY = im.getScaleY();
        im.setScaleX(valueX + 0.15);
        im.setScaleY(valueY + 0.15);



    }

    @FXML
    private void zoomOut() {
        ImageView im = ImageControl.getInstance().getImgView();
        double valueX = im.getScaleX();
        double valueY = im.getScaleY();
        im.setScaleX(valueX - 0.15);
        im.setScaleY(valueY - 0.15);


    }

    @FXML
    private void mnuGrayscale(ActionEvent event) {
        if (ImageControl.getInstance().getImg() == null)
            return;
        Image greyImage = Transform.transform(ImageControl.getInstance().getImg(), Color::grayscale);
        ImageControl.getInstance().setImg(greyImage);


    }

    @FXML
    private void mnuBrightscale(ActionEvent event) {
        if (ImageControl.getInstance().getImg() == null)
            return;
        Image brightImage = Transform.transform(ImageControl.getInstance().getImg(), Color::brighter);
        ImageControl.getInstance().setImg(brightImage);


    }

    @FXML
    private void mnuDarkscale(ActionEvent event) {
        if (ImageControl.getInstance().getImg() == null)
            return;
        Image brightImage = Transform.transform(ImageControl.getInstance().getImg(), Color::darker);
        ImageControl.getInstance().setImg(brightImage);


    }

    @FXML
    private void mnuBlur(ActionEvent event) {

        if (ImageControl.getInstance().getImg() == null)
            return;
        BoxBlur bb = new BoxBlur();
        bb.setHeight(5);
        bb.setWidth(5);
        bb.setIterations(3);
        ImageView im = ImageControl.getInstance().getImgView();
        im.setEffect(bb);
        ImageControl.getInstance().setImgView(im);

    }
    @FXML
    private void mnuSepia(ActionEvent event) {
        if (ImageControl.getInstance().getImg() == null)
            return;
        SepiaTone sepiaTone = new SepiaTone(.7);
        ImageControl.getInstance().getImgView().setEffect(sepiaTone);
    }

    @FXML
    private void addListener(ActionEvent event) {

        ImageView im = ImageControl.getInstance().getImgView();
        AnchorPane currentAnc = ImageControl.getInstance().getAncPane();
        ScrollPane currentScroll = ImageControl.getInstance().getScrollPane();
        currentScroll.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                yScroll=((double)newValue/4)*im.getBoundsInLocal().getHeight();
//                System.out.println("Vertical slider= "+ yScroll);
                for (Shape s:removeShapes) {
                    s.setLayoutY(-yScroll);
                }
            }
        });

        currentScroll.hvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                xScroll=((double)newValue/4)*im.getBoundsInLocal().getWidth();
//                System.out.println("Horizontal slider= "+ xScroll);
                for (Shape s:removeShapes) {
                    s.setLayoutX(-xScroll);
                }
            }
        });

        im.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_PRESSED, event1 -> {

            xPos = (int) event1.getX();
            yPos = (int) event1.getY();
            event1.consume();
        });

        im.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_RELEASED, event12 -> {
            hPos = event12.getX();
            wPos = event12.getY();

            if (penStyle == Pen.REC) {

                Rectangle rec = new Rectangle(xPos, yPos, hPos - xPos, wPos - yPos);
                rec.setStroke(mColor);
                rec.setStrokeWidth(brushSize.getValue());
                rec.setFill(null);
                currentAnc.getChildren().add(rec);
                removeShapes.add(rec);
            }
            if (penStyle == Pen.ELLPS) {

                Ellipse ell = new Ellipse(xPos, yPos, hPos - xPos, wPos - yPos);
                ell.setStroke(mColor);
                ell.setStrokeWidth(brushSize.getValue());
                ell.setFill(null);
                currentAnc.getChildren().add(ell);
                removeShapes.add(ell);
            }
            if (penStyle == Pen.LINE) {

                Line line = new Line(xPos, yPos, hPos, wPos);
                line.setStroke(mColor);
                line.setFill(mColor);
                line.setStrokeWidth(brushSize.getValue());
                currentAnc.getChildren().add(line);
                removeShapes.add(line);
            }
//            if (penStyle == Pen.CIR || penStyle == Pen.SQR || penStyle == Pen.LINE) {
//                Image image = ImageControl.getInstance().getImg();
//                WritableImage wi;
//                SnapshotParameters parameters = new SnapshotParameters();
//                wi = new WritableImage((int) image.getWidth(), (int) image.getHeight());
//
//                currentAnc.snapshot(parameters, wi);
//                ImageControl.getInstance().setImg(wi);
//                currentAnc.getChildren().removeAll(removeShapes);
//                removeShapes.clear();
//
//
//            }
        });

        im.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_DRAGGED, me -> {


                if (penStyle == Pen.CIR) {
                    xPos = me.getX();
                    yPos = me.getY();
                    int nShape = 0;

                    Shape shape = new Circle(xPos, yPos, 10);
                    shape = new Circle(xPos, yPos, penSize);
                    // shape.setStroke(mColor);
                    shape.setFill(mColor);
                    currentAnc.getChildren().add(shape);
                    removeShapes.add(shape);
                    me.consume();
                }
                if (penStyle == Pen.SQR) {
                    xPos = me.getX();
                    yPos = me.getY();
                    Shape shape = new Circle(xPos, yPos, 10);
                    shape = new Circle(xPos, yPos, 2);

                    // shape.setStroke(mColor);
                    shape.setFill(mColor);

                    currentAnc.getChildren().add(shape);
                    removeShapes.add(shape);
                    me.consume();
                }
                if (penStyle == Pen.DROP) {
                    xPos = me.getX();
                    yPos = me.getY();
                    Color pixelColor;
                    PixelReader pixelReader = ImageControl.getInstance().getImg().getPixelReader();
                    pixelColor = pixelReader.getColor(((int) xPos), ((int) yPos));
                    System.out.println(pixelColor);
                    clrPick.setValue(pixelColor);
                    mColor = clrPick.getValue();
                }

        });

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ImageControl.getInstance().setTabOnFocus(mainTab);
        ImageControl.getInstance().setImgView(this.imgView);
        brushSize.setValue(5);

        drwToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == drwCircle) {
                penStyle = Pen.CIR;
                System.out.println("Initialize: resources = " + resources);
            } else if (newValue == drwPixel) {
                penStyle = Pen.SQR;
            } else if (newValue == drwDrop) {
                penStyle = Pen.DROP;
            } else if (newValue == drwLine) {
                penStyle = Pen.LINE;
            } else if (newValue == drwRec) {
                penStyle = Pen.REC;
            } else if (newValue == drwEllipse) {
                penStyle = Pen.ELLPS;
            }
        });
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ImageControl.getInstance().setTabOnFocus(newValue);
        });

        clrPick.setOnAction(event -> mColor = clrPick.getValue());
        brushSize.setOnMouseClicked(event -> penSize = brushSize.getValue());

       /* ImageControl.getInstance().getImgView().addEventFilter(javafx.scene.input.MouseEvent.MOUSE_DRAGGED, me -> {

            if (penStyle == Pen.CIR) {
                xPos = me.getX();
                yPos = me.getY();

                int nShape = 0;
//                DrawShapes.getInstance().drawShape(imgView,ancRoot,xPos,yPos,penSize,mColor);
                Shape shape = new Circle(xPos, yPos, 10);
                shape = new Circle(xPos, yPos, penSize);

//                     shape.setStroke(mColor);
                shape.setFill(mColor);

                ancRoot.getChildren().add(shape);
                removeShapes.add(shape);
                me.consume();
            }
            if (penStyle == Pen.SQR) {

                xPos = me.getX();
                yPos = me.getY();

                Shape shape = new Circle(xPos, yPos, 10);
                shape = new Circle(xPos, yPos, 2);
                //  shape.setStroke(mColor);
                shape.setFill(mColor);
                ancRoot.getChildren().add(shape);
//                    removeShapes.add(shape);
                me.consume();
            }
            if (penStyle == Pen.DROP) {
                xPos = me.getX();
                yPos = me.getY();
                Color pixelColor;
                PixelReader pixelReader = ImageControl.getInstance().getImg().getPixelReader();
                pixelColor = pixelReader.getColor(((int) xPos), ((int) yPos));
                System.out.println(pixelColor);
                clrPick.setValue(pixelColor);
                mColor = clrPick.getValue();
            }
        });

    */
    }
    @FXML
    private void showHelp(ActionEvent event) {
        final Stage stage = new Stage();
        Group rootGroup = new Group();
        Scene scene = new Scene(rootGroup, 700, 500, Color.WHITESMOKE);
        stage.setScene(scene);
        stage.setTitle("Info");
        stage.centerOnScreen();
        stage.show();
        Text text = new Text(100, 240, "FxEditor IKAES Group");
        text.setFill(Color.DODGERBLUE);
        text.setEffect(new Lighting());
        text.setFont(Font.font(Font.getDefault().getFamily(), 50));
        rootGroup.getChildren().add(text);
        stage.centerOnScreen();
    }

    @FXML
    private void exit() {
        System.exit(0);
    }
}
