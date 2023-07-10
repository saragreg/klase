package com.example.tfg_profes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class AdaptadorProfesLista extends BaseAdapter {

    private Context contexto;
    private LayoutInflater inflater;
    private ArrayList<String> nombres;
    private ArrayList<String> precios;
    private ArrayList<String> puntuaciones;
    private ArrayList<String> locs;
    public AdaptadorProfesLista(Context pcontext, ArrayList<String> pnombres, ArrayList<String> pprecio, ArrayList<String> ppuntuaciones,ArrayList<String> plocs)
    {
        contexto = pcontext;
        nombres = pnombres;
        precios=pprecio;
        puntuaciones=ppuntuaciones;
        locs=plocs;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return nombres.size();
    }

    @Override
    public Object getItem(int i) {
        return nombres.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.adaptador_profes,null);
        TextView nombre= (TextView) view.findViewById(R.id.nombre);
        TextView precio=(TextView) view.findViewById(R.id.precio);
        TextView loc = (TextView) view.findViewById(R.id.localProf);
        RatingBar barra= (RatingBar) view.findViewById(R.id.ratingBar);
        ImageView fotoper= view.findViewById(R.id.fotoPerLis);
        String p=Imagenes.obtenerImagen2(nombres.get(i));
        if (!p.equals("imagen")) {
            if (p.length()<100){
                Uri imageUri = Uri.parse(p);
                fotoper.setImageURI(imageUri);
            }else {
                String image64 = p;
                byte[] b = Base64.decode(image64, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0,
                        b.length);
                Bitmap rescaledImage = adjustImageSize(bitmap);
                fotoper.setImageBitmap(rescaledImage);
            }
        }
        barra.setIsIndicator(true);
        nombre.setText(nombres.get(i));
        precio.setText(precios.get(i)+"€");
        loc.setText(locs.get(i));
        barra.setRating(Float.parseFloat(puntuaciones.get(i)));
        return view;
    }
    private Bitmap adjustImageSize(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int length = bitmap.getHeight();

        int newSize = 800;
        float scaleWidth = ((float) newSize/width);
        float scaleLength = ((float) newSize/length);

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleLength);

        return Bitmap.createBitmap(bitmap, 0,0, width, length, matrix, true);
    }
}

