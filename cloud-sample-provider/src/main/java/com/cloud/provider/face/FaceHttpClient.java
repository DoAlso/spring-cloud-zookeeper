package com.cloud.provider.face;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import com.cloud.sdk.http.HttpMethodName;

/**
 * @ClassName FaceHttpClient
 * @Description TODO
 * @Author Administrator
 * @DATE 2018/11/6 11:16
 */
public class FaceHttpClient {

    /**
     *
     * @param serviceName
     * @param region
     * @param ak
     * @param sk
     * @param requestUrl
     * @param putBody
     */
    public static void put(String serviceName,String region,String ak, String sk, String requestUrl,String putBody) {
        AccessService accessService = null;
        try {
            Map<String, String> header = new HashMap<>(2);
            header.put("Content-Type", "application/json");
            accessService = new AccessServiceImpl(serviceName, region, ak, sk);
            URL url = new URL(requestUrl);
            HttpMethodName httpMethod = HttpMethodName.PUT;

            InputStream content = new ByteArrayInputStream(putBody.getBytes());
            HttpResponse response = accessService.access(url, header, content,
                    (long) putBody.getBytes().length, httpMethod);

            System.out.println(response.getStatusLine().getStatusCode());


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            accessService.close();
        }

    }


    /**
     *
     * @param serviceName
     * @param region
     * @param ak
     * @param sk
     * @param requestUrl
     * @param putBody
     */
    public static void patch(String serviceName,String region,String ak, String sk, String requestUrl, String putBody) {

        AccessService accessService = null;
        try {
            Map<String, String> header = new HashMap<>(2);
            header.put("Content-Type", "application/json");
            accessService = new AccessServiceImpl(serviceName, region, ak, sk);
            URL url = new URL(requestUrl);
            HttpMethodName httpMethod = HttpMethodName.PATCH;
            InputStream content = new ByteArrayInputStream(putBody.getBytes());
            HttpResponse response = accessService.access(url, header, content,
                    (long) putBody.getBytes().length, httpMethod);

            System.out.println(convertStreamToString(response.getEntity()
                    .getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            accessService.close();
        }

    }

    /**
     *
     * @param serviceName
     * @param region
     * @param ak
     * @param sk
     * @param requestUrl
     */
    public static void delete(String serviceName,String region,String ak, String sk, String requestUrl) {

        AccessService accessService = null;

        try {
            Map<String, String> header = new HashMap<>(2);
            header.put("Content-Type", "application/json");
            accessService = new AccessServiceImpl(serviceName, region, ak, sk);
            URL url = new URL(requestUrl);
            HttpMethodName httpMethod = HttpMethodName.DELETE;

            HttpResponse response = accessService.access(url, header, httpMethod);
            System.out.println(convertStreamToString(response.getEntity()
                    .getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            accessService.close();
        }

    }

    /**
     *
     * @param serviceName
     * @param region
     * @param ak
     * @param sk
     * @param requestUrl
     */
    public static void get(String serviceName,String region,String ak, String sk, String requestUrl) {

        AccessService accessService = null;

        try {
            Map<String, String> header = new HashMap<>(2);
            header.put("Content-Type", "application/json");
            accessService = new AccessServiceImpl(serviceName, region, ak, sk);
            URL url = new URL(requestUrl);
            HttpMethodName httpMethod = HttpMethodName.GET;
            HttpResponse response;
            response = accessService.access(url, header, httpMethod);
            System.out.println(convertStreamToString(response.getEntity()
                    .getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            accessService.close();
        }

    }

    /**
     *
     * @param serviceName
     * @param region
     * @param ak
     * @param sk
     * @param requestUrl
     * @param postbody
     */
    public static void post(String serviceName,String region,String ak, String sk, String requestUrl,
                            String postbody) {

        AccessService accessService = new AccessServiceImpl(serviceName,
                region, ak, sk);
        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStream content = new ByteArrayInputStream(postbody.getBytes());
        HttpMethodName httpMethod = HttpMethodName.POST;
        HttpResponse response;

        try {
            Map<String, String> header = new HashMap<>(2);
            header.put("Content-Type", "application/json");
            response = accessService.access(url, header, content,
                    (long) postbody.getBytes().length, httpMethod);
            System.out.println(convertStreamToString(response.getEntity()
                    .getContent()));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            accessService.close();
        }
    }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
