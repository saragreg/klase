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
import android.widget.TextView;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {
    private Context contexto;
    private LayoutInflater inflater;
    private ArrayList<Imagenes> lisContactos;

    public UserListAdapter(Context pcontext, ArrayList<Imagenes> pliscontactos){
        contexto = pcontext;
        lisContactos = pliscontactos;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return lisContactos.size();
    }

    @Override
    public Object getItem(int i) {
        return lisContactos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.adapter_user_list,null);
        TextView nombre= (TextView) view.findViewById(R.id.nombre);
        ImageView imagen=(ImageView) view.findViewById(R.id.imagen);
        nombre.setText(lisContactos.get(i).getUser());
        if (!lisContactos.get(i).getImagen().equals("imagen")) {
            if (lisContactos.get(i).getImagen().length()<100){
                Uri imageUri = Uri.parse(lisContactos.get(i).getImagen());
                imagen.setImageURI(imageUri);
            }else {
                String image64 = lisContactos.get(i).getImagen();
                byte[] b = Base64.decode(image64, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0,
                        b.length);
                Bitmap rescaledImage = adjustImageSize(bitmap);
                imagen.setImageBitmap(rescaledImage);
            }
        }
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

