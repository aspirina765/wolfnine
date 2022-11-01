package com.wolfnine.backend.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wolfnine.backend.entity.shopee.*;
import com.wolfnine.backend.entity.shopee.brand.ShopeeBrandResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class ShopeeUtil {
    private static final String host = "https://partner.shopeemobile.com";
    private static final String tmp_partner_key = "0b27ea12b0a4a37084e4f5b81bdfdd02a0d03301f47af6027511fa8bc253a05f";
    private static final long partner_id = 842499;

    //generate auth url
    public static String shopAuth(String redirectUrl) {
        long timest = System.currentTimeMillis() / 1000L;
        String path = "/api/v2/shop/auth_partner";
        String tmp_base_string = String.format("%s%s%s", partner_id, path, timest);
        byte[] partner_key;
        byte[] base_string;
        String sign = "";
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = tmp_partner_key.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = String.format("%064x", new BigInteger(1, mac.doFinal(base_string)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s&redirect=%s", partner_id, timest, sign, redirectUrl);
        System.out.println(url);
        return url;
    }

    //shop request for access token for the first time
    public static ShopeeCredential getTokenShopLevel(String code, long partner_id, String tmp_partner_key, long shop_id) throws ParseException, IOException {
        String[] res = new String[2];
        long timest = System.currentTimeMillis() / 1000L;
        String path = "/api/v2/auth/token/get";
        String tmp_base_string = String.format("%s%s%s", partner_id, path, timest);
        byte[] partner_key;
        byte[] base_string;
        BigInteger sign = null;
        String result = "";
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = tmp_partner_key.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = new BigInteger(1, mac.doFinal(base_string));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String tmp_url = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s", partner_id, timest, String.format("%032x", sign));
        URL url = new URL(tmp_url);
        HttpURLConnection conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        ShopeeCredential shopeeCredential = new ShopeeCredential();
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Map<String, Object> map = new HashMap<>();
            map.put("code", code);
            map.put("shop_id", shop_id);
            map.put("partner_id", partner_id);
            Gson gson = new Gson();
            String json = gson.toJson(map);
            conn.connect();
            out = new PrintWriter(conn.getOutputStream());
            out.print(json);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null) {
                result += line;
            }
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(result).getAsJsonObject();
            res[0] = jsonObject.get("access_token").getAsString();
            res[1] = jsonObject.get("refresh_token").getAsString();
            shopeeCredential = gson.fromJson(jsonObject, ShopeeCredential.class);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        for (String item : res
             ) {
            System.out.println(item);
        }
        return shopeeCredential;
    }

    //shop refresh the access token
    public static ShopeeCredential getRefreshTokenShopLevel(String refresh_token,long partner_id,String tmp_partner_key,long shop_id) throws ParseException,IOException{
        String[] res = new String[2];
        long timest = System.currentTimeMillis() / 1000L;
        String path = "/api/v2/auth/access_token/get";
        String tmp_base_string = String.format("%s%s%s", partner_id, path, timest);
        byte[] partner_key;
        byte[] base_string;
        BigInteger sign = null;
        String result = "";
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = tmp_partner_key.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = new BigInteger(1,mac.doFinal(base_string));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String tmp_url = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s", partner_id,timest, String.format("%032x",sign));
        URL url = new URL(tmp_url);
        HttpURLConnection conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        ShopeeCredential shopeeCredential = new ShopeeCredential();
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Map<String,Object> map = new HashMap<>();
            map.put("refresh_token",refresh_token);
            map.put("shop_id",shop_id);
            map.put("partner_id",partner_id);
            Gson gson = new Gson();
            String json = gson.toJson(map);
            conn.connect();
            out = new PrintWriter(conn.getOutputStream());
            out.print(json);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while((line=in.readLine())!=null){
                result +=line;
            }
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(result).getAsJsonObject();
            res[0] = jsonObject.get("access_token").getAsString();
            res[1] = jsonObject.get("refresh_token").getAsString();
            shopeeCredential = gson.fromJson(jsonObject, ShopeeCredential.class);
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(out != null){
                    out.close();
                }
                if(in != null){
                    in.close();
                }
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
        return shopeeCredential;
    }


    //main account request for the access token for the first time
    public static String[] get_token_account_level(String code, long partner_id, String tmp_partner_key, long main_account_id) throws ParseException, IOException {
        String[] res = new String[2];
        long timest = System.currentTimeMillis() / 1000L;
        String path = "/api/v2/auth/token/get";
        String tmp_base_string = String.format("%s%s%s", partner_id, path, timest);
        byte[] partner_key;
        byte[] base_string;
        BigInteger sign = null;
        String result = "";
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = tmp_partner_key.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = new BigInteger(1, mac.doFinal(base_string));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String tmp_url = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s", partner_id, timest, String.format("%032x", sign));
        URL url = new URL(tmp_url);
        HttpURLConnection conn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Map<String, Object> map = new HashMap<>();
            map.put("code", code);
            map.put("amin_account_id", main_account_id);
            map.put("partner_id", partner_id);
            Gson gson = new Gson();
            String json = gson.toJson(map);
            conn.connect();
            out = new PrintWriter(conn.getOutputStream());
            out.print(json);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            while ((line = in.readLine()) != null) {
                result += line;
            }
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = jsonParser.parse(result).getAsJsonObject();
            res[0] = jsonObject.get("access_token").getAsString();
            res[1] = jsonObject.get("refresh_token").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return res;
    }

    public static String addProductItem(String accessToken, long partnerId, long shopId, String partnerKey) {
        long timest = System.currentTimeMillis() / 1000L;
        System.out.println("timest " + timest);
        String path = "/api/v2/product/add_item";
        String tmp_base_string = String.format("%s%s%s%s%s", partnerId, path, timest, accessToken, shopId);
        byte[] partner_key;
        byte[] base_string;
        BigInteger sign = null;
        String result = "";
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = partnerKey.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = new BigInteger(1, mac.doFinal(base_string));
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = String.format("%032x", sign);
        System.out.println(result);
        String apiUrl = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s&access_token=%s&shop_id=%s", partner_id, timest, String.format("%032x", sign), accessToken, shopId);
        return apiUrl;
    }
    public static ShopeeShopInfo getShopInfo(String accessToken, long partnerId, long shopId, String partnerKey) throws ParseException, IOException {
        long timest = System.currentTimeMillis() / 1000L;
        System.out.println("timest " + timest);
        String path = "/api/v2/shop/get_shop_info";
        String tmp_base_string = String.format("%s%s%s%s%s", partnerId, path, timest, accessToken, shopId);
        byte[] partner_key;
        byte[] base_string;
        BigInteger sign = null;
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = partnerKey.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = new BigInteger(1, mac.doFinal(base_string));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String apiUrl = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s&access_token=%s&shop_id=%s", partner_id, timest, String.format("%032x", sign), accessToken, shopId);
        RestTemplate restTemplate = new RestTemplate();
        ShopeeShopInfo shopeeShopInfo = restTemplate.getForObject(apiUrl, ShopeeShopInfo.class);
        return shopeeShopInfo;
    }


    public static void getShopCategoryList(String accessToken, long partnerId, long shopId, String partnerKey) {
        long timest = System.currentTimeMillis() / 1000L;
        System.out.println("timest " + timest);
        String path = "/api/v2/shop_category/get_shop_category_list";
        String tmp_base_string = String.format("%s%s%s%s%s", partnerId, path, timest, accessToken, shopId);
        byte[] partner_key;
        byte[] base_string;
        BigInteger sign = null;
        String result = "";
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = partnerKey.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = new BigInteger(1, mac.doFinal(base_string));
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = String.format("%032x", sign);
        System.out.println(result);
    }

    public static void addShopCategory(String accessToken, long partnerId, long shopId, String partnerKey) {
        long timest = System.currentTimeMillis() / 1000L;
        System.out.println("timest " + timest);
        String path = "/api/v2/shop_category/add_shop_category";
        String tmp_base_string = String.format("%s%s%s%s%s", partnerId, path, timest, accessToken, shopId);
        byte[] partner_key;
        byte[] base_string;
        BigInteger sign = null;
        String result = "";
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = partnerKey.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = new BigInteger(1, mac.doFinal(base_string));
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = String.format("%032x", sign);
        System.out.println(result);
    }

    public static ShopeeCategoryResponse getProductCategoryList(
            String accessToken,
            long partnerId,
            long shopId,
            String partnerKey,
            int page,
            int limit
    ) {
        long timest = System.currentTimeMillis() / 1000L;
        System.out.println("timest " + timest);
        String path = "/api/v2/product/get_category";
        String tmp_base_string = String.format("%s%s%s%s%s", partnerId, path, timest, accessToken, shopId);
        byte[] partner_key;
        byte[] base_string;
        BigInteger sign = null;
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = partnerKey.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = new BigInteger(1, mac.doFinal(base_string));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String apiUrl = host + path + String.format(
                "?partner_id=%s&timestamp=%s&sign=%s&access_token=%s&shop_id=%s&page_size=%s&page_no=%s",
                partner_id,
                timest,
                String.format("%032x", sign),
                accessToken,
                shopId,
                limit,
                page
        );
        System.out.println(apiUrl);
        RestTemplate restTemplate = new RestTemplate();
        ShopeeCategoryResponse response = restTemplate.getForObject(apiUrl, ShopeeCategoryResponse.class);
        return response;
    }

    public static ShopeeLogisticChannelResponse getLogisticChannelList(String accessToken, long partnerId, long shopId, String partnerKey) {
        long timest = System.currentTimeMillis() / 1000L;
        System.out.println("timest " + timest);
        String path = "/api/v2/logistics/get_channel_list";
        String tmp_base_string = String.format("%s%s%s%s%s", partnerId, path, timest, accessToken, shopId);
        byte[] partner_key;
        byte[] base_string;
        BigInteger sign = null;
        String result = "";
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = partnerKey.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = new BigInteger(1, mac.doFinal(base_string));
            String apiUrl = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s&access_token=%s&shop_id=%s", partner_id, timest, String.format("%032x", sign), accessToken, shopId);
            System.out.println(apiUrl);
            RestTemplate restTemplate = new RestTemplate();
            ShopeeLogisticChannelResponse logisticChannelResponse = restTemplate.getForObject(apiUrl, ShopeeLogisticChannelResponse.class);
            return logisticChannelResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String uploadImage(long partnerId, String partnerKey, String imagePath) {
        long timest = System.currentTimeMillis() / 1000L;
        System.out.println("timest " + timest);
        String path = "/api/v2/media_space/upload_image";
        String tmp_base_string = String.format("%s%s%s", partnerId, path, timest);
        byte[] partner_key;
        byte[] base_string;
        BigInteger sign = null;
        String result = "";
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = partnerKey.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = new BigInteger(1, mac.doFinal(base_string));
            String apiUrl = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s", partner_id, timest, String.format("%032x", sign));
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
            MultiValueMap<String, Object> body
                    = new LinkedMultiValueMap<>();
            body.add("image", new FileSystemResource(imagePath));
            HttpEntity<MultiValueMap<String, Object>> requestEntity
                    = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate
                    .postForEntity(apiUrl, requestEntity, String.class);
            FileUtil.deleteFile(imagePath);
            System.out.println(response);
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonRes = jsonParser.parse(response.getBody()).getAsJsonObject();
            if(jsonRes != null) {
                System.out.println(jsonRes.get("response"));
                return jsonRes.get("response").getAsJsonObject().get("image_info").getAsJsonObject().get("image_id").getAsString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String uploadImageV2(long partnerId, String partnerKey, BufferedImage image) {
        long timest = System.currentTimeMillis() / 1000L;
        System.out.println("timest " + timest);
        String path = "/api/v2/media_space/upload_image";
        String tmp_base_string = String.format("%s%s%s", partnerId, path, timest);
        byte[] partner_key;
        byte[] base_string;
        BigInteger sign = null;
        String result = "";
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = partnerKey.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = new BigInteger(1, mac.doFinal(base_string));
            String apiUrl = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s", partner_id, timest, String.format("%032x", sign));
            URL url = new URL(apiUrl);
            HttpURLConnection conn = null;
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setDoInput(true);
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(10000);
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=----WebKitFormBoundaryyrV7KO0BoCBuDbTL");
                conn.setRequestProperty("Accept", "multipart/form-data");
                Map<String, Object> map = new HashMap<>();
                map.put("image", image);
                conn.connect();
                out = new PrintWriter(conn.getOutputStream());
                out.print(map);
                out.flush();
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = "";
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(result).getAsJsonObject();
                System.out.println(jsonObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ShopeeBrandResponse getBrandList(
            String accessToken,
            long partnerId,
            long shopId,
            String partnerKey,
            int shopeeCategoryId,
            int pageSize,
            int offSet
    ) {
        long timest = System.currentTimeMillis() / 1000L;
        System.out.println("timest " + timest);
        String path = "/api/v2/product/get_brand_list";
        String tmp_base_string = String.format("%s%s%s%s%s", partnerId, path, timest, accessToken, shopId);
        byte[] partner_key;
        byte[] base_string;
        BigInteger sign = null;
        String result = "";
        try {
            base_string = tmp_base_string.getBytes("UTF-8");
            partner_key = partnerKey.getBytes("UTF-8");
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(partner_key, "HmacSHA256");
            mac.init(secret_key);
            sign = new BigInteger(1, mac.doFinal(base_string));
            String apiUrl = host + path + String.format(
                    "?partner_id=%s&timestamp=%s&sign=%s&access_token=%s&shop_id=%s&category_id=%s&page_size=%s&offset=%s&status=%s",
                    partner_id, timest, String.format("%032x", sign), accessToken, shopId, shopeeCategoryId, pageSize, offSet, 1
            );
            System.out.println(apiUrl);
            RestTemplate restTemplate = new RestTemplate();
            ShopeeBrandResponse shopeeBrandResponse = restTemplate.getForObject(apiUrl, ShopeeBrandResponse.class);
            return shopeeBrandResponse;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }
}
