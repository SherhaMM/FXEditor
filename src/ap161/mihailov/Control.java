package ap161.mihailov;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Control {

    @FXML
    private ImageView imgView;
    @FXML
    private TabPane tabPane;
    @FXML
    Button btn1,btn2,btn3;
    @FXML
    Button addPaneBtn;
    // for mouse clicks
    private double xPos, yPos, hPos, wPos;



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
        ImageControl.getInstance().setImg(SwingFXUtils.toFXImage(bufferedImage, null));
        ImageControl.getInstance().setImgViewRefresh(ImageControl.getInstance().getImg());
        } catch (IOException ex) {
           ex.printStackTrace();
        }

    }
    @FXML
    void mnuSaveAction(ActionEvent event){
        ImageControl.getInstance().setImgView(this.imgView);
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
            //    ImageIO.write(SwingFXUtils.fromFXImage(ImageControl.getInstance().getImg(),
                ImageIO.write(SwingFXUtils.fromFXImage(ImageControl.getInstance().getImgView().getImage(),
                        null), "png", file);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    @FXML
    void mnuGrayscale(ActionEvent event) {

        if (ImageControl.getInstance().getImg() == null)
            return;
        ImageControl.getInstance().setImgView(this.imgView);
        Image greyImage = Transform.transform(ImageControl.getInstance().getImg(), Color::grayscale);
        this.imgView.setImage(greyImage);
        ImageControl.getInstance().setImgViewRefresh(greyImage);


    }
    @FXML
    void mnuBrightscale(ActionEvent event) {


        if (ImageControl.getInstance().getImg() == null)
            return;

        ImageControl.getInstance().setImgView(this.imgView);
        Image brightImage = Transform.transform(ImageControl.getInstance().getImg(), Color::brighter);
        this.imgView.setImage(brightImage);
        ImageControl.getInstance().setImgViewRefresh(brightImage);


    }
    @FXML
    void mnuDarkscale(ActionEvent event) {


        if (ImageControl.getInstance().getImg() == null)
            return;

        ImageControl.getInstance().setImgView(this.imgView);
        Image brightImage = Transform.transform(ImageControl.getInstance().getImg(), Color::darker);
        this.imgView.setImage(brightImage);
        ImageControl.getInstance().setImgViewRefresh(brightImage);


    }
    @FXML
    void addTab(ActionEvent event){
        Tab n = new Tab();
        n.setText("Unnamed Tab");
        n.setContent(new ImageView());
        this.tabPane.getTabs().add(n);
    }

    @FXML
    void showHelp(ActionEvent event){
    final Stage stage= new Stage();
       Group rootGroup= new Group();
        Scene scene= new Scene(rootGroup,700,500, Color.WHITESMOKE);
        stage.setScene(scene);
        stage.setTitle("Help");
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
    };
}
