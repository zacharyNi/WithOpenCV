package com.nzd.photo;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Scanner;


/**
 * @author 11633
 * @date 2018/4/21 8:46
 */
public class Test {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void showImg(Mat imread, int len) {
        HighGui.namedWindow("test", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("test", imread);
        HighGui.waitKey(len); System.out.println("1");
    }

    public static void ShowPhote(Mat imread, int choice) {
        switch (choice){
            case 1:showImg(PhotoFactory.Filter.disFog(imread, 20), 0);break;
            case 2:showImg(PhotoFactory.Filter.oilPainting(imread), 0);break;
            case 3:showImg(PhotoFactory.Filter.sharpen(imread, 10, 2), 0);break;
            case 4:showImg(PhotoFactory.Filter.edgeDetection(imread), 0);break;
            default:break;
        }
        return;
    }
}