package com.wolfnine.backend.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class ShopeeUtil {
    private static final String host = "https://partner.shopeemobile.com";
    private static final String tmp_partner_key = "0b27ea12b0a4a37084e4f5b81bdfdd02a0d03301f47af6027511fa8bc253a05f";
    private static final long partner_id = 842499;

    //generate auth url
    public static String shopAuth() {
        long timest = System.currentTimeMillis() / 1000L;
        String path = "/api/v2/shop/auth_partner";
        String redirect_url = "https://www.google.com/";
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
        String url = host + path + String.format("?partner_id=%s&timestamp=%s&sign=%s&redirect=%s", partner_id, timest, sign, redirect_url);
        System.out.println(url);
        return url;
    }

    //shop request for access token for the first time
    public static String[] getTokenShopLevel(String code, long partner_id, String tmp_partner_key, long shop_id) throws ParseException, IOException {
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
        return res;
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

    public static void addProductItem(String accessToken, long partnerId, long shopId, String partnerKey) {
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

    public static void getProductCategoryList(String accessToken, long partnerId, long shopId, String partnerKey) {
        long timest = System.currentTimeMillis() / 1000L;
        System.out.println("timest " + timest);
        String path = "/api/v2/product/get_category";
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

    public static void getLogisticChannelList(String accessToken, long partnerId, long shopId, String partnerKey) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = String.format("%032x", sign);
        System.out.println(result);
    }

    public static void uploadImage(long partnerId, String partnerKey) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = String.format("%032x", sign);
        System.out.println(result);
    }

    public static void getBrandList(String accessToken, long partnerId, long shopId, String partnerKey) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = String.format("%032x", sign);
        System.out.println(result);
    }
}
