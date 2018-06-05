package ap161.mihailov;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sun.plugin.javascript.navig.Anchor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ImageControl {
//    private ImageView imgView;
    private static ImageControl state;
    private Tab tabOnFocus;
    private Map tabImgViews= new HashMap<Tab,ImageView>();
    private Map tabAncPanes= new HashMap<Tab,AnchorPane>();
    private Map tabScrollPanes= new HashMap<Tab,ScrollPane>();
    private AnchorPane ancPane;

    public ScrollPane getScrollPane() {
        return (ScrollPane)tabScrollPanes.get(tabOnFocus);
    }

    public void setScrollPane(ScrollPane sc) { tabScrollPanes.put(tabOnFocus,sc);
    }

    public AnchorPane getAncPane() {
        return (AnchorPane)tabAncPanes.get(tabOnFocus);
    }

    public void setAncPane(AnchorPane ancPane) {
        tabAncPanes.put(tabOnFocus,ancPane);
    }

    public Tab getTabOnFocus() {
        return tabOnFocus;
    }

    public void setTabOnFocus(Tab tabOnFocus) {
        this.tabOnFocus = tabOnFocus;
    }

    public static ImageControl getInstance(){
        if(state == null){
            state=new ImageControl();
        }
        return state;
    }
    public Image getImg() {
        return  getImgView().getImage();
    }

    public void setImg(Image img) {
        ImageView view= getImgView();
        view.setImage(img);
    }

    public void setImgView(ImageView imgView) {
        tabImgViews.put(tabOnFocus,imgView);
    }
    public ImageView getImgView() {
        return (ImageView)tabImgViews.get(tabOnFocus);

    }

//    public void setImgToView(Image img){
//        System.out.println(img);
//        this.imgView.setImage(img); //added view
//  //      list.add(img);
//        System.out.println("Image Control - img добавлен в view");
//    }
}
