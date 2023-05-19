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
    private String latObt,lngObt;

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
            case "addProf":
                String usuProf = getInputData().getString("usuario");
                String exp = getInputData().getString("exp");
                Float prec = getInputData().getFloat("prec", 0.0F);
                String idiom = getInputData().getString("idiom");
                String cursos = getInputData().getString("cursos");
                String asig = getInputData().getString("asig");
                String loc = getInputData().getString("loc");
                String lat = getInputData().getString("lat");
                String lng = getInputData().getString("lng");

                System.out.println("lo que llega es: "+lat);
                addProf(usuProf,exp,prec,idiom,cursos,asig,loc,lat,lng);
                return Result.success();
            case "selectLoc":
                String usuLocInt = getInputData().getString("usuario");

                selectLoc(usuLocInt);
                Data loca = new Data.Builder()
                        .putString("lat",latObt)
                        .putString("lng",lngObt)
                        .build();
                return Result.success(loca);
            default:
                return Result.failure();
        }

    }

    private void addProf(String usuProf, String exp,Float prec, String idiom, String cursos, String asig, String loc,String lat, String lng) {
        String url = URL_BASE + "addLoc.php";
        System.out.println(url);

        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("usuario", usuProf);
            parametrosJSON.put("exp", exp);
            parametrosJSON.put("prec", prec);
            parametrosJSON.put("idiom", idiom);
            parametrosJSON.put("asig", asig);
            parametrosJSON.put("cursos", cursos);
            parametrosJSON.put("loc", loc);
            parametrosJSON.put("lat", lat);
            parametrosJSON.put("lng", lng);
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

    private void selectLoc(String usuLocInt) {
        String url = URL_BASE + "selectLoc.php?usuario="+usuLocInt;
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

                latObt = jsonArray.getJSONObject(0).getString("lat");
                lngObt = jsonArray.getJSONObject(0).getString("lng");


            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

    }

    private void add(String usuLoc, String lat, String lng) {

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

