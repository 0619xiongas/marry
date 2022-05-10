package com.ocr.OcrImage;

import com.ocr.OtherDemo.Tess4J;
import com.ocr.Utils.ImgUtils;

public class OcrCtrl {

    public static void main(String[] args){

        //原始验证码地址
        String OriginalImg = "src/main/resources/pic/test5.jpg";
        //识别样本输出地址
        String ocrResult = "src/main/resources/pic/test5.jpg";
        String code = Tess4J.executeTess4J(ocrResult);
        //输出识别结果
        System.out.println("Ocr识别结果: \n" + code);

    }

}
