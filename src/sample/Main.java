package sample;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    //Loading the OpenCV core library
    // https://medium.com/@aadimator/how-to-set-up-opencv-in-intellij-idea-6eb103c1d45c
    static{ System.loadLibrary( Core.NATIVE_LIBRARY_NAME );}

    @Override
    public void start(Stage primaryStage) throws Exception{

        Rect size = new Rect();

        //Group imageGroup1 = loadImageIntoGroup("Z:\\FIRST\\2020-2021\\pictures\\NoRings_innerLine_center.png", size);
        Group imageGroup1 = loadImageIntoGroup("Z:\\FIRST\\2020-2021\\pictures\\1Ring_InnerLine_Centered.png", size);
        //Group imageGroup3 = loadImageIntoGroup("Z:\\FIRST\\2020-2021\\pictures\\4Rings_InnerLine_Centered.png",size);
        //Group imageGroup = new Group(imageGroup1, imageGroup2, imageGroup3);
        Group root = new Group(imageGroup1);

        primaryStage.setTitle("openCvExample");
        primaryStage.setScene(new Scene(root, size.width, size.height));
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }

    public Group loadImageIntoGroup(String filename, Rect rect) throws IOException {
        //Loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

        //Reading the Image from the file and storing it in to a Matrix object
        Mat image = Imgcodecs.imread(filename);
        Size size = image.size();

        List<Mat> planes = new ArrayList<Mat>();
        Core.split(image, planes);

        //Encoding the image
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", image, matOfByte);

        //Storing the encoded Mat in a byte array
        byte[] byteArray = matOfByte.toArray();

        //Displaying the image
        InputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage bufImage = ImageIO.read(in);

        System.out.println("Image Loaded");
        WritableImage writableImage = SwingFXUtils.toFXImage(bufImage, null);

        ImageView imageView1 = new ImageView(writableImage);
        imageView1.setX(0);
        imageView1.setY(rect.height);
        imageView1.setFitHeight(size.height);
        imageView1.setFitWidth(size.width);
        imageView1.setPreserveRatio(true);
        Group imageGroup = new Group(imageView1);
        rect.height += size.height;
        rect.width = (int)size.width;

        return imageGroup;
    }

}
