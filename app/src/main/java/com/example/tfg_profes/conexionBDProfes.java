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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class conexionBDProfes extends Worker {
    private String URL_BASE = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/sgarcia216/WEB/";
    private String usua="",usua_conf="",nombre="",precio="",asig="",cursos="",idiomas="",exp="",punt="",loc="",lat="",lng="",asignaturas="";

    public conexionBDProfes(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String tipo = getInputData().getString("tipo");

        switch (tipo) {
            case "infoLista":

                infoLista();
                Data resultados = new Data.Builder()
                        .putString("usu",usua)
                        .putString("nombre",nombre)
                        .putString("precio",precio)
                        .putString("punt",punt)
                        .putString("usu_conf",usua_conf)
                        .putString("loc",loc)
                        .putString("lat",lat)
                        .putString("lng",lng)
                        .build();
                return Result.success(resultados);
            case "insertProf":
                String usuario=getInputData().getString("usuario");
                insertProf(usuario);
                return Result.success();
            case "infoProfe":
                String usu=getInputData().getString("usuario");
                infoProfe(usu);
                Data res = new Data.Builder()
                        .putString("asig",asig)
                        .putString("cursos",cursos)
                        .putString("idiomas",idiomas)
                        .putString("exp",exp)
                        .build();
                return Result.success(res);
            default:
                return Result.failure();
        }

    }

    private void infoProfe(String usuario) {
        String url = URL_BASE + "infoProfesor.php?usuario="+usuario;
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
                asig = jsonArray.getJSONObject(0).getString("asig");
                cursos = jsonArray.getJSONObject(0).getString("cursos");
                idiomas = jsonArray.getJSONObject(0).getString("idiomas");
                exp = jsonArray.getJSONObject(0).getString("exp");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }


    private void insertProf(String usuario) {
        String url = URL_BASE + "addLoc.php";

        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("usuario", usuario);

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


    private void infoLista() {
        String url = URL_BASE + "lisProfes.php";
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
                Profesor.lisProfes=new ArrayList<Profesor>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    usua = jsonArray.getJSONObject(i).getString("idProfe");
                    precio = jsonArray.getJSONObject(i).getString("precio");
                    punt = jsonArray.getJSONObject(i).getString("punt");
                    usua_conf = jsonArray.getJSONObject(i).getString("usu");
                    nombre = jsonArray.getJSONObject(i).getString("nombre");
                    asignaturas = jsonArray.getJSONObject(i).getString("asignaturas");
                    Imagenes imagenes=new Imagenes(jsonArray.getJSONObject(i).getString("usu"),jsonArray.getJSONObject(i).getString("imagen"));
                    Imagenes.lisimagenesProfes.add(imagenes);
                    loc = jsonArray.getJSONObject(i).getString("loc");
                    lat = jsonArray.getJSONObject(i).getString("lat");
                    lng = jsonArray.getJSONObject(i).getString("lng");
                    Profesor profesor=new Profesor(usua,nombre,asignaturas,Float.parseFloat(punt),loc,precio,lat,lng);
                    Profesor.lisProfes.add(profesor);
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
