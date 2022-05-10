package com.misson.Javacv;

import org.bytedeco.javacpp.opencv_core.Mat;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class cvOperate {
    public static String getGaryAndBinary(String imgUrl){
        Mat image=imread(imgUrl);
        if(image.empty())
        {
            System.out.println("图像加载错误，请检查图片路径！");
            return "";
        }
        Mat gray=new Mat();
        cvtColor(image,gray,COLOR_RGB2GRAY);		//彩色图像转为灰度图像 把它转成RGB格式的图像

        Mat bin=new Mat();
        // 给的阈值是120 阈值的最大值是255 我试过阈值在100到150之间，120是二值化之后看见比较清晰的，阈值的作用是让图像矩阵化后里面的元素凡是小于120的
        // 都会变成0 大于120的变成255. 就变成了黑白两色的图片。容易进行识别。
        threshold(gray,bin,120,255,THRESH_TOZERO); 	//图像二值化

        imwrite("src/main/resources/pic/imgCvOperate.jpg",bin); // 将灰化，二值化的图片存放在本地resources目录下
        return "src/main/resources/pic/imgCvOperate.jpg";
    }
}
