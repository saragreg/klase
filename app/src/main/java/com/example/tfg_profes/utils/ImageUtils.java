package com.example.tfg_profes.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ImageUtils {
    public boolean sessionExists(Context context, String file) {
        String ret = readImage(context, file);

        return !ret.isEmpty();
    }
    /*
     * Code extracted and adapted from StackOverflow (User: Iarsaars)
     * https://stackoverflow.com/questions/14376807/read-write-string-from-to-a-file-in-android
     */
    public String readImage(Context context, String file) {
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(file);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString;
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e);
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e);
        }

        return ret.replaceAll("\\s", "");
    }
}

