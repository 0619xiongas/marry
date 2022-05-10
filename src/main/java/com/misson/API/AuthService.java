package com.misson.API;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthService {
    public static String getAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = "LIZEuPQ23t3hW8n8OfUQv45k";
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = "VTdFyUt2p4NU2yYwLMpj2diHVzQ4e4dO";
        return getAuth(clientId, clientSecret);
    }

    private static String getAuth(String ak, String sk) {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                + "grant_type=client_credentials"
                + "&client_id=" + ak
                + "&client_secret=" + sk;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            JSONObject jsonObject = new JSONObject(result.toString());
            return jsonObject.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return null;
    }
}
