package com.example.workshop2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityMostrarProductos extends AppCompatActivity {

    Intent i;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://192.168.1.5:4200";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_productos);
        handleMostarProductos();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);



        findViewById(R.id.btnAgregarProducto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAgregarProducto();
            }
        });
    }
    private void handleMostarProductos(){


        Toast.makeText(ActivityMostrarProductos.this, "Mostrando datos", Toast.LENGTH_LONG).show();
    }
    private void handleAgregarProducto(){
        View view = getLayoutInflater().inflate(R.layout.avtivity_agregar_producto, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view).show();

        Button addProductBtn = view.findViewById(R.id.btnAgregarProductoAlert);
        final EditText nameEdit = view.findViewById(R.id.nameEditProduct);
        final EditText categoryEdit = view.findViewById(R.id.categoryEdit);
        final EditText imageEdit = view.findViewById(R.id.imageEdit);
        final EditText priceEdit = view.findViewById(R.id.priceEdit);
        final EditText countInStockEdit = view.findViewById(R.id.countInStockEdit);
        final EditText brandEdit = view.findViewById(R.id.brandEdit);
        final EditText descriptionEdit = view.findViewById(R.id.descriptionEdit);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", nameEdit.getText().toString());
                map.put("category", categoryEdit.getText().toString());
                map.put("image", imageEdit.getText().toString());
                map.put("price", priceEdit.getText().toString());
                map.put("countInStock", countInStockEdit.getText().toString());
                map.put("brand", brandEdit.getText().toString());
                map.put("description", descriptionEdit.getText().toString());

                Call<Void> call = retrofitInterface.executeAddProduct(map);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 201){
                            Toast.makeText(ActivityMostrarProductos.this, "Producto Agregado Correctamente ", Toast.LENGTH_LONG).show();
                        }else if(response.code()==400){
                            Toast.makeText(ActivityMostrarProductos.this, "Datos incompletos", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ActivityMostrarProductos.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
