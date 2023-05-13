package com.example.tfg_profes;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



public class conexionBDWebService extends Worker {

    private String URL_BASE = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/sgarcia216/WEB/";

    String contra;
    private String usuario;

    public conexionBDWebService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }


    @NonNull
    @Override
    public Result doWork() {
        String tipo = getInputData().getString("tipo");

        switch (tipo) {
            case "registroUsu":
                String usu = getInputData().getString("usu");
                String contraReg = getInputData().getString("contra");
                String nom = getInputData().getString("nom");
                String tel = getInputData().getString("tel");
                String per = getInputData().getString("perfil");
                String token = getInputData().getString("token");
                System.out.println("usu:"+usu);
                System.out.println("per:"+per);
                System.out.println("token:"+token);

                registroUsu(usu,contraReg,nom,tel,per,token);
                Data resreg = new Data.Builder()
                        .putString("res",usuario)
                        .build();
                return Result.success(resreg);
            case "login":
                String usuInt = getInputData().getString("usuario");
                System.out.println("lo que llega es: "+usuInt);
                login(usuInt);
                Data resultados = new Data.Builder()
                        .putString("res",contra)
                        .build();
                return Result.success(resultados);
            default:
                return Result.failure();
        }

    }


    private void registroUsu(String usuintro,String contraintro,String nombreintro,String telintro,String perintro,String tokenintro) {
        String url = URL_BASE + "registro_usuarios.php";
        System.out.println(url);

        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("usuario", usuintro);
            parametrosJSON.put("contra", contraintro);
            parametrosJSON.put("nom", nombreintro);
            parametrosJSON.put("tel", telintro);
            parametrosJSON.put("perfil", perintro);
            parametrosJSON.put("token",tokenintro);
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametrosJSON.toString());
            out.close();

            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String line, result = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();

                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {
                    usuario = jsonArray.getJSONObject(i).getString("res");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }


    private void login(String usuInt) {
        String url = URL_BASE + "login.php?usuario="+usuInt;
        System.out.println("url: "+url);
        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");

            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
                String line, result = "";

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();

                JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {
                    contra = jsonArray.getJSONObject(i).getString("res");
                }


            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}

