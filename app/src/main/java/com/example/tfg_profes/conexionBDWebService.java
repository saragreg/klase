package com.example.tfg_profes;

import android.content.Context;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.tfg_profes.utils.FileUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;


public class conexionBDWebService extends Worker {

    private String URL_BASE = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/sgarcia216/WEB/";

    private String contra;
    private String perfil;
    private String usuario;
    private String latObt,lngObt;
    private String idCli="",val="",comentario="",fecha="";
    private int[] resGraf=new int[4];
    private String usupend="",usuacept="";
    private String nombres="",imgs="",img="",filePath="";
    private String asigPet="",nombrePet="",fotoperPet="",duracionPet="",fechahoraPet="",intensivoPet="",diasPet="",idProfe="",idUsu="",feccrea="";
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
                        .putString("per",perfil)
                        .build();
                return Result.success(resultados);
            case "addProf":
                String usuProf = getInputData().getString("usuario");
                String exp = getInputData().getString("exp");
                Float prec = getInputData().getFloat("prec", 0.0F);
                String idiom = getInputData().getString("idiom");
                String cursos = getInputData().getString("cursos");
                String asig = getInputData().getString("asig");
                addProf(usuProf,exp,prec,idiom,cursos,asig);
                return Result.success();
            case "addLocUsu":
                String usuario = getInputData().getString("usuario");
                String locU = getInputData().getString("loc");
                String latU = getInputData().getString("lat");
                String lngU = getInputData().getString("lng");

                addLocUsu(usuario,locU,latU,lngU);
                return Result.success();
            case "addCliente":
                String usuCli = getInputData().getString("padre");
                int numhijos = getInputData().getInt("numHijos",1);
                addCliente(usuCli,numhijos);
                return Result.success();
            case "addAlu":
                String padre = getInputData().getString("padre");
                String nomshijos = getInputData().getString("hijos");
                addAlumno(padre,nomshijos);
                return Result.success();
            case "registrarHorario":
                String profeHorario = getInputData().getString("usu");
                String l = getInputData().getString("l");
                String m = getInputData().getString("m");
                String x = getInputData().getString("x");
                String j = getInputData().getString("j");
                String vie = getInputData().getString("v");
                String s = getInputData().getString("s");
                String d = getInputData().getString("d");
                String desc = getInputData().getString("desc");
                registrarHorario(profeHorario,l,m,x,j,vie,s,d,desc);
                return Result.success();
            case "selectLoc":
                String usuLocInt = getInputData().getString("usuario");

                selectLoc(usuLocInt);
                Data loca = new Data.Builder()
                        .putString("lat",latObt)
                        .putString("lng",lngObt)
                        .build();
                return Result.success(loca);
            case "grafica":
                String asiggraf = getInputData().getString("asignatura");
                grafica(asiggraf);
                Data graficares = new Data.Builder()
                        .putIntArray("resGraf",resGraf).build();
                return Result.success(graficares);
            case "pendientes":
                String profe = getInputData().getString("profe");
                pendientes(profe);
                Data pendientes = new Data.Builder()
                        .putString("usupend",usupend)
                        .build();
                return Result.success(pendientes);
            case "aceptados":
                String profe2 = getInputData().getString("profe");
                aceptados(profe2);
                Data aceptados = new Data.Builder()
                        .putString("usuacept",usuacept)
                        .build();
                return Result.success(aceptados);
            case "ListaPeticiones":
                String user = getInputData().getString("user");
                listaPeticiones(user);
                Data petis = new Data.Builder()
                        .putString("idProfe",idProfe)
                        .putString("idUsu",idUsu)
                        .putString("feccrea",feccrea)
                        .putString("asig",asigPet)
                        .putString("nombre",nombrePet)
                        .putString("fotoper",fotoperPet)
                        .putString("duracion",duracionPet)
                        .putString("fechahora",fechahoraPet)
                        .putString("intensivo",intensivoPet)
                        .putString("dias",diasPet)
                        .build();
                return Result.success(petis);
            case "updateEstadoPeticion":
                String userUPD = getInputData().getString("user");
                String idUsuUPD = getInputData().getString("idUsu");
                String idfechaUPD = getInputData().getString("idFecha");
                String estado= getInputData().getString("estado");
                updateEstadoPeticion(userUPD,idUsuUPD,idfechaUPD,estado);
                return Result.success();
            case "insertarImagen":
                usu = getInputData().getString("usuario");
                insertImagen(usu);
                return Result.success();
            case "cargarEventos":
                usu = getInputData().getString("user");
                cargarEventos(usu);
                return Result.success();
            case "addEvento":
                usu = getInputData().getString("usu");
                String fechaEvento = getInputData().getString("fecha");
                String descrEvento = getInputData().getString("descr");
                addEvento(usu,fechaEvento,descrEvento);
                return Result.success();
            case "addContacto":
                String usu1 = getInputData().getString("usu1");
                String usu2 = getInputData().getString("usu2");
                addContacto(usu1,usu2);
                return Result.success();
            case "obtenerContactos":
                String idUser = getInputData().getString("idUser");
                lisContactos(idUser);
                Data contactos = new Data.Builder()
                        .putString("nombres",nombres)
                        .build();
                return Result.success(contactos);
            case "cargarFotoPerfil":
                String usuPer = getInputData().getString("user");
                cargarFotoPerfil(usuPer);
                String filepath;
                Data fotoPer = new Data.Builder()
                        .putString("img",filePath)
                        .build();
                return Result.success(fotoPer);
            case "cargarDatosUsu":
                String usuDatos = getInputData().getString("user");
                cargarDatosUsu(usuDatos);
                return Result.success();
            case "infoProfeDatos":
                String profeDatos = getInputData().getString("user");
                infoProfeDatos(profeDatos);
                return Result.success();
            case "infoHorario":
                String horarioDatos = getInputData().getString("user");
                infoHorario(horarioDatos);
                return Result.success();
            case "infoAluDatos":
                String aluDatos = getInputData().getString("user");
                infoAluDatos(aluDatos);
                return Result.success();
            case "updateUsuario":
                String usuarioDatosAnti = getInputData().getString("usuAnti");
                String usuarioDatos = getInputData().getString("usu");
                String nomDatos = getInputData().getString("nom");
                String telDatos = getInputData().getString("tel");
                updateUsuario(usuarioDatosAnti,usuarioDatos,nomDatos,telDatos);
                return Result.success();
            case "updateContra":
                String usuarioContra = getInputData().getString("usu");
                String contraHasheada = getInputData().getString("contra");
                updateContra(usuarioContra,contraHasheada);
                return Result.success();
            case "addSolicitud":
                String profeKlase = getInputData().getString("idProfe");
                String clieKlase = getInputData().getString("idCli");
                String fechaActualKlase = getInputData().getString("fechaActual");
                String fechaKlase = getInputData().getString("fecha");
                String horaKlase = getInputData().getString("hora");
                String diasKlase = getInputData().getString("dias");
                String puntualKlase = getInputData().getString("puntual");
                String asigKlase = getInputData().getString("asig");
                String durKlase = getInputData().getString("dur");
                addSolicitud(profeKlase,clieKlase,fechaActualKlase,fechaKlase,horaKlase,diasKlase,puntualKlase,asigKlase,durKlase);
                return Result.success();
            case "ListaResennas":
                String profeRes = getInputData().getString("profe");
                ListaResennas(profeRes);
                Data resenas = new Data.Builder()
                        .putString("idCli",idCli)
                        .putString("val",val)
                        .putString("comentario",comentario)
                        .putString("fecha",fecha)
                        .build();
                return Result.success(resenas);
            case "escribir_resenna":
                String profeRese = getInputData().getString("idProfe");
                String clieRes = getInputData().getString("idClie");
                String fechaActualRes = getInputData().getString("FechaHora");
                String comentario = getInputData().getString("comentario");
                String valoracion = getInputData().getString("valoracion");
                escribir_resenna(profeRese,clieRes,fechaActualRes,comentario,valoracion);
                return Result.success();
            case "updateValoracion":
                String profeVal = getInputData().getString("idProfe");
                float val = getInputData().getFloat("val",0.0f);
                updateValoracion(profeVal,val);
                return Result.success();
            default:
                return Result.failure();
        }

    }

    private void grafica(String asiggraf) {
        String url = URL_BASE + "estadistica.php?asig="+asiggraf;
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
                resGraf[0]=Integer.parseInt(jsonArray.getJSONObject(0).getString("2020"));
                resGraf[1]=Integer.parseInt(jsonArray.getJSONObject(1).getString("2021"));
                resGraf[2]=Integer.parseInt(jsonArray.getJSONObject(2).getString("2022"));
                resGraf[3]=Integer.parseInt(jsonArray.getJSONObject(3).getString("2023"));

            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void registrarHorario(String profeH, String l, String m, String x, String j, String vie, String s, String d, String desc) {
        String url = URL_BASE + "registro_horario.php";

        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("usuario", "Miguel");
            parametrosJSON.put("lunes", l);
            parametrosJSON.put("martes", m);
            parametrosJSON.put("miercoles", x);
            parametrosJSON.put("jueves", j);
            parametrosJSON.put("viernes", vie);
            parametrosJSON.put("sabado", s);
            parametrosJSON.put("domingo", d);
            parametrosJSON.put("descr", desc);
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

    private void infoHorario(String horarioDatos) {
        String url = URL_BASE + "select_horario.php?user="+horarioDatos;
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

                Horario.horario.setLun(jsonArray.getJSONObject(0).getString("lun"));
                Horario.horario.setMar(jsonArray.getJSONObject(0).getString("mar"));
                Horario.horario.setMie(jsonArray.getJSONObject(0).getString("mie"));
                Horario.horario.setJue(jsonArray.getJSONObject(0).getString("jue"));
                Horario.horario.setVie(jsonArray.getJSONObject(0).getString("vie"));
                Horario.horario.setSab(jsonArray.getJSONObject(0).getString("sab"));
                Horario.horario.setDom(jsonArray.getJSONObject(0).getString("dom"));
                Horario.horario.setDescr(jsonArray.getJSONObject(0).getString("descr"));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void infoAluDatos(String aluDatos) {
        String url = URL_BASE + "infoAluDatos.php?user="+aluDatos;
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
                String numAlu = jsonArray.getJSONObject(0).getString("numHijos");
                obtenerHijos(aluDatos,numAlu);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void obtenerHijos(String aluDatos,String numHijos) {
        String url = URL_BASE + "infoAluHijos.php?user="+aluDatos;
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
                nombres="";
                for (int i = 0; i < jsonArray.length(); i++) {
                    nombres = nombres+jsonArray.getJSONObject(i).getString("nombre")+",";
                }
                Alumno.alumno.setIdCliente(aluDatos);
                Alumno.alumno.setNumHijos(Integer.parseInt(numHijos));
                Alumno.alumno.setNombres(nombres);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void infoProfeDatos(String profeDatos) {
        String url = URL_BASE + "infoProfeDatos.php?user="+profeDatos;
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

                String idProfe = jsonArray.getJSONObject(0).getString("idProfe");
                String punt = jsonArray.getJSONObject(0).getString("punt");
                String experiencia = jsonArray.getJSONObject(0).getString("experiencia");
                String precio = jsonArray.getJSONObject(0).getString("precio");
                String idiomas = jsonArray.getJSONObject(0).getString("idiomas");
                String asignaturas = jsonArray.getJSONObject(0).getString("asignaturas");
                String cursos = jsonArray.getJSONObject(0).getString("cursos");
                Profesor.profesor.setIdProfe(idProfe);
                Profesor.profesor.setVal(Float.parseFloat(punt));
                Profesor.profesor.setExperiencia(experiencia);
                Profesor.profesor.setPrecio(precio);
                Profesor.profesor.setIdiomas(idiomas);
                Profesor.profesor.setAsig(asignaturas);
                Profesor.profesor.setCursos(cursos);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void lisContactos(String idUser) {
        String url = URL_BASE + "lisContactos.php?idUser="+idUser;
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
                nombres="";
                for (int i = 0; i < jsonArray.length(); i++) {
                    String idUser1 = jsonArray.getJSONObject(i).getString("idUser1");
                    String idUser2 = jsonArray.getJSONObject(i).getString("idUser2");
                    if (idUser1.equals(idUser)){
                        nombres=nombres+idUser2+",";
                    }else{
                        nombres=nombres+idUser1+",";
                    }
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

    private void updateValoracion(String profeVal, float val) {
        String url = URL_BASE + "update_valoracion.php?idProfe="+profeVal+"&val="+val;

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

    private void escribir_resenna(String profeRese, String clieRes, String fechaActualRes, String comentario, String valoracion) {
        String url = URL_BASE + "escribir_resenna.php";

        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("idProfe", profeRese);
            parametrosJSON.put("idClie", clieRes);
            parametrosJSON.put("FechaHora", fechaActualRes);
            parametrosJSON.put("valoracion", valoracion);
            parametrosJSON.put("comentario", comentario);
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

    private void ListaResennas(String profeRes) {
        Resenna.resennasLis=new ArrayList<>();
        String url = URL_BASE + "lisResennas.php?idProfe="+profeRes;
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
                    idCli = jsonArray.getJSONObject(i).getString("idCli");
                    val = jsonArray.getJSONObject(i).getString("val");
                    if (jsonArray.getJSONObject(i).getString("comentario").equals("")) {
                        comentario = "";
                    }else{
                        comentario = jsonArray.getJSONObject(i).getString("comentario") ;
                    }
                    fecha = jsonArray.getJSONObject(i).getString("fecha");
                    Resenna resenna = new Resenna(profeRes, idCli,Float.parseFloat(val),comentario,fecha);
                    Resenna.resennasLis.add(resenna);
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

    private void addSolicitud(String profeKlase, String clieKlase, String fechaActualKlase, String fechaKlase, String horaKlase, String diasKlase, String puntualKlase, String asigKlase, String durKlase) {
        String url = URL_BASE + "addSolicitud.php";

        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("idProfe", profeKlase);
            parametrosJSON.put("idCli", clieKlase);
            parametrosJSON.put("FechaHora", fechaActualKlase);
            parametrosJSON.put("fechaClase", fechaKlase);
            parametrosJSON.put("horaClase", horaKlase);
            parametrosJSON.put("dias",diasKlase);
            parametrosJSON.put("puntual", puntualKlase);
            parametrosJSON.put("asignaturas", asigKlase);
            parametrosJSON.put("duracion", durKlase);
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

    private void updateContra(String usuarioContra, String contraHasheada) {
        String url = URL_BASE + "updateContra.php";
        System.out.println(url);

        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("usuario", usuarioContra);
            parametrosJSON.put("contra", contraHasheada);
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

    private void updateUsuario(String usuarioDatosAnti,String usuarioDatos, String nomDatos, String telDatos) {
        String url = URL_BASE + "updateDatosUsu.php";
        System.out.println(url);

        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("usuAnti", usuarioDatosAnti);
            parametrosJSON.put("usu", usuarioDatos);
            parametrosJSON.put("nom", nomDatos);
            parametrosJSON.put("tel", telDatos);
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

    private void cargarDatosUsu(String usuDatos) {
        String url = URL_BASE + "cargarDatosUsu.php?user="+usuDatos;
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
                    String nombre = jsonArray.getJSONObject(i).getString("nombre");
                    String tel = jsonArray.getJSONObject(i).getString("tel");
                    String loc = jsonArray.getJSONObject(i).getString("loc");
                    String foto="default";
                    Usuario usu=new Usuario(usuDatos,nombre,tel,loc,foto);
                    Usuario.usuariosLis=new ArrayList<>();
                    Usuario.usuariosLis.add(usu);
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

    private void cargarFotoPerfil(String usu) {
        String url = URL_BASE + "selectImagenToken.php?usuario="+usu;
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
                img = jsonArray.getJSONObject(0).getString("imagen");
                FileUtils fileUtils= new FileUtils();
                String user= fileUtils.readFile(getApplicationContext(), "config.txt");
                if (!usu.equals(user)) {
                    Imagenes imagenes = new Imagenes(usu, img);
                    Imagenes.lisContactos.add(imagenes);
                }else {
                    Usuario.usuariosLis.get(0).setImagen(img);
                    Imagenes.perfilusuario=new Imagenes(user,img);
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

    private void addContacto(String usu1, String usu2) {
        String url = URL_BASE + "addContacto.php?iduser1="+usu1+"&iduser2="+usu2;
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
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void addEvento(String usu, String fechaEvento, String descrEvento) {
        String url = URL_BASE + "addEvento.php";
        System.out.println(url);

        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("usuario", usu);
            parametrosJSON.put("fecha", fechaEvento);
            parametrosJSON.put("descr", descrEvento);
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

    private void cargarEventos(String user) {
        String url = URL_BASE + "cargarEventos.php?user="+user;
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
                    String fecha = jsonArray.getJSONObject(i).getString("fecha");
                    String desc = jsonArray.getJSONObject(i).getString("desc");
                    Evento evento=new Evento(desc, LocalDate.parse(fecha));
                    Evento.eventosLis.add(evento);
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

    private void updateEstadoPeticion(String userUPD, String idUsuUPD, String idfechaUPD, String estado) {
        String url = URL_BASE + "updateEstadoPeticion.php";
        System.out.println(url);

        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("usuario", userUPD);
            parametrosJSON.put("idUsu", idUsuUPD);
            parametrosJSON.put("idFecha", idfechaUPD);
            parametrosJSON.put("estado", estado);
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

    private void listaPeticiones(String user) {
        String url = URL_BASE + "lisPeticiones.php?user="+user;
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
                    idUsu = idUsu+jsonArray.getJSONObject(i).getString("idUsu")+",";
                    feccrea = feccrea+jsonArray.getJSONObject(i).getString("feccrea")+",";
                    asigPet = asigPet+jsonArray.getJSONObject(i).getString("asignaturas")+";";
                    nombrePet = nombrePet+jsonArray.getJSONObject(i).getString("nombre")+",";
                    Imagenes imagenes=new Imagenes(jsonArray.getJSONObject(i).getString("idUsu"),jsonArray.getJSONObject(i).getString("imagen"));
                    Imagenes.lisimagenes.add(imagenes);
                    duracionPet = duracionPet+jsonArray.getJSONObject(i).getString("duracion")+",";
                    fechahoraPet = fechahoraPet+jsonArray.getJSONObject(i).getString("fechaClase")+" ";
                    fechahoraPet = fechahoraPet+jsonArray.getJSONObject(i).getString("horaClase")+",";
                    int puntual = jsonArray.getJSONObject(i).getInt("puntual");
                    if (puntual==1){
                        intensivoPet=intensivoPet+"ocasional"+",";
                    }else{
                        intensivoPet=intensivoPet+"intensivo"+",";
                    }
                    String dias=jsonArray.getJSONObject(i).getString("dias");
                    if (!dias.equals("")){
                        diasPet=diasPet+jsonArray.getJSONObject(i).getString("dias")+",";
                    }else{
                        diasPet=diasPet+"nada,";
                    }
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

    private void addLocUsu(String usuario,String locU, String latU, String lngU) {
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
            parametrosJSON.put("usuario", usuario);
            parametrosJSON.put("loc", locU);
            parametrosJSON.put("lat", latU);
            parametrosJSON.put("lng", lngU);
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

    private void pendientes(String profe) {
        String url = URL_BASE + "pendientes.php?profe="+profe;
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
                    usupend=usupend+jsonArray.getJSONObject(i).getString("usu")+",";
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
    private void aceptados(String profe) {
        String url = URL_BASE + "aceptados.php?profe="+profe;
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
                    usuacept=usuacept+jsonArray.getJSONObject(i).getString("usu")+",";
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
    private void addCliente(String usuCli, int numhijos) {
        String url = URL_BASE + "addCliente.php?idCliente="+usuCli+"&numHijos="+numhijos;
        System.out.println(url);

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

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
    private void addAlumno(String usuCli, String nomshijos) {
        String url = URL_BASE + "addAlumno.php?usuario="+usuCli+"&nom="+nomshijos;
        System.out.println(url);

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

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private void addProf(String usuProf, String exp,Float prec, String idiom, String cursos, String asig) {
        String url = URL_BASE + "addProfe.php";
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
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametrosJSON);
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
        String url = URL_BASE + "login_usuarios.php?usuario="+usuInt;
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
                    perfil = jsonArray.getJSONObject(i).getString("per");
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
    private void insertImagen(String usu) {
        String imagen=Imagenes.perfilusuario.getImagen();
        String url = "http://ec2-54-93-62-124.eu-central-1.compute.amazonaws.com/sgarcia216/WEB/insertar_imagenes.php";

        HttpURLConnection urlConnection = null;
        try {
            URL requestUrl = new URL(url);
            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject parametrosJSON = new JSONObject();
            parametrosJSON.put("usuario", usu);
            parametrosJSON.put("imagen", imagen);
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametrosJSON);
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
                    String nomuri = jsonArray.getJSONObject(i).getString("imagen");
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
    public static String obtenerImagenEnString(String filePath) {
        try {
            // Leer los bytes del archivo
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));

            // Decodificar los bytes en forma de cadena Base64
            String imageString = Base64.encodeToString(bytes, Base64.DEFAULT);

            return imageString;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

