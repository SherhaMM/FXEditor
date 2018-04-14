package ap161.mihailov;

import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;


public class ImageControl {
    private Stage MainStage;
    private Image img;
    private ImageView imgView;
    private static ImageControl state;
    private TabPane tabPane;

    public TabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(TabPane tabPane) {
        this.tabPane = tabPane;
    }

    public Stage getMainStage() {
        return MainStage;
    }

    public void setMainStage(Stage mainStage) {
        MainStage = mainStage;
    }

    public Image getImg() {
        return img;
    }

    public static ImageControl getInstance(){
        if(state == null){
            state=new ImageControl();
        }
        return state;
    }
    public void setImg(Image img) {
        this.img = img;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public void setImgView(ImageView imgView) {
        this.imgView = imgView;
    }

    public void setImgViewRefresh(Image img){
        System.out.println(img);
        this.imgView.setImage(img);
        this.img=img; //added
    }





}
