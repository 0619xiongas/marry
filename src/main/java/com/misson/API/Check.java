package com.misson.API;

import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Check {
    private static final String URL_T = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic?access_token=" + AuthService.getAuth();
    private static final String URL_T_H = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate?access_token=" + AuthService.getAuth();
    private static final String URL_IMAGE = "https://aip.baidubce.com/rest/2.0/ocr/v1/webimage?access_token=" + AuthService.getAuth();
    private static final String URL_IMAGE_H = "https://aip.baidubce.com/rest/2.0/ocr/v1/webimage_loc?access_token=" + AuthService.getAuth();

    /**
     * 识别本地图片的文字
     *
     * @param path 本地图片地址
     * @return 识别结果，为json格式
     * @throws URISyntaxException URI打开异常
     * @throws IOException        io流异常
     */
    public static String checkFile(String path) throws URISyntaxException, IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new NullPointerException("图片不存在");
        }
        String image = BaseImg64.getImageStrFromPath(path);
        String param = "image=" + image;
        return post(param);
    }

    /**
     * 通过传递参数：url和image进行文字识别
     * @param param 区分是url还是image识别
     * @return 识别结果
     * @throws URISyntaxException URI打开异常
     * @throws IOException        IO流异常x
     */
    private static String post(String param) throws URISyntaxException, IOException {
        //开始搭建post请求
        URI url = new URI(URL_IMAGE_H);
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(globalConfig).build();
        HttpPost post = new HttpPost();

        post.setURI(url);
        //设置请求头，请求头必须为application/x-www-form-urlencoded，因为是传递一个很长的字符串，不能分段发送
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");
        StringEntity entity = new StringEntity(param);
        post.setEntity(entity);
        CloseableHttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == 200) {
            String str;
            try {
                /*读取服务器返回过来的json字符串数据*/
                str = EntityUtils.toString(response.getEntity());
                return str;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static String getResult(String json){
        int start = json.indexOf("words\":\"");
        return json.substring(start+8,start+12);
    }
}
