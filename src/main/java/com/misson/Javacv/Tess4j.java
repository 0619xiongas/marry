package com.misson.Javacv;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.io.File;

public class Tess4j {
    public static String executeOcr(String imgUrl){
        String resCode = "";
        try{
            ITesseract instance = new Tesseract();
            //instance.setLanguage("chi_sim"); // 带有中文的验证码;
            File imgDir = new File(imgUrl);
            resCode = instance.doOCR(imgDir);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resCode;
    }
}
