package com.example.demo.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ConnectionPerfomance {

    public ConnectionPerfomance(){}

    public static void excecutePost(String link, JSONObject jsonObject) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoOutput(true);
        System.out.println(jsonObject);
        try(OutputStream os = httpURLConnection.getOutputStream()) {
            byte[] input =  URLEncoder.encode(jsonObject.toString(), StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }

    public static JSONArray excecuteManyGET(String link) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        try (var reader = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null;) {
                result.append(line);
            }
        }
        return new JSONArray(result.toString());
    }

    public static JSONObject excecuteOnlyGET(String link, String param, String exctract) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url;
        if (param!=null){
            url = new URL( link+URLEncoder.encode(param, StandardCharsets.UTF_8));
        }else{
            url = new URL(link);
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        try (var reader = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null;) {
                result.append(line);
            }
        }
        if(result.toString().isEmpty()){
            return new JSONObject();
        }else{
            return new JSONObject(result.toString().replace(exctract, "").replace("=", ":"));
        }
    }

    public static String excecuteValidation(String link) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        try (var reader = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
            return result.toString();
        }catch (Exception e){
            return "exception";
        }
    }

    public static void excecuteDELETE(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestProperty("Content-Type", "utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestMethod("DELETE");
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void excecutePUT(String link, JSONObject jsonObject) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("PUT");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoOutput(true);
        System.out.println(jsonObject);
        try(OutputStream os = httpURLConnection.getOutputStream()) {
            byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        }
    }
}
