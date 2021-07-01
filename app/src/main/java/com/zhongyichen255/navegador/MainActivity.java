package com.zhongyichen255.navegador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int READ_CODE = 1000;

    private ListView listaRutas;
    private ArchivoAdapter adapter;
    // Los caminos que hay en el fichero
    // 文件夹里的路径
    private List<String> rutas;
    // permiso de leer el almacenamiento externo
    // 是否允许读取外置储存
    private int permisoLeer;

    private String archivoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pedirPermiso();

        // Si se permite hacer la lectura del almacenamiento externo, se ejecuta el codigo siguiente
        // 如果允许读取外置储存话，会运行以下代码
        if(permisoLeer == PackageManager.PERMISSION_GRANTED){
            datos();

            asignar();

            crear();
        }
    }

    // Revisa si tiene el permiso
    // 查看有没有权限
    private void pedirPermiso(){
        // Revisa el permiso de la lectura del almacenamiento externo
        // 查看有没有读取外置储存权限
        permisoLeer = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permisoLeer != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String []{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_CODE);
        }
    }

    // Leer el almacenamiento externo y guarda las rutas de los archivos
    // 读取外置储存,同时保存文件路径
    private void datos(){
        // Leer este fichero
        // 读取这个文件夹
        String ruta = Environment.getExternalStorageDirectory().getPath();

        File archivo = new File(ruta);
        File [] archivos = archivo.listFiles();

        rutas = new ArrayList<>();
        for(File fichero : archivos){
            rutas.add(fichero.getPath());
        }

        Log.i("ruta", "datos: "+ruta);
    }

    // Definir los elementos
    // 定义组件
    private void asignar(){
        listaRutas = findViewById(R.id.lista_ruta);
    }

    // Definir el adaptor y asignar al Listview
    // 创造Listview的适配器
    private void crear(){
        //ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,rutas);

        adapter = new ArchivoAdapter(rutas, this, R.layout.ruta_archivo);

        listaRutas.setAdapter(adapter);

        listaRutas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                actualizarRutas(adapter.getItem(i).toString());
                adapter.notifyDataSetChanged();
            }
        });

    }

    // Actualizar los datos del adaptador de Listview
    // 刷新Listview适配器的数据
    private void actualizarRutas(String rutaActual){
        File archivo = new File(rutaActual);

        if(archivo.isDirectory()){
            File [] archivos = archivo.listFiles();

            rutas = new ArrayList<>();
            String rutaRaiz = Environment.getExternalStorageDirectory().getPath();
            if(!rutaRaiz.equals(rutaActual)){
                rutas.add(archivo.getParent());
            }

            for(File fichero : archivos){
                rutas.add(fichero.getPath());
            }

            adapter.setRutas(rutas);
        }else if(archivo.isFile()){
            Toast.makeText(this,"Direccion: "+archivo.getPath(),Toast.LENGTH_LONG).show();
        }else{
            Log.i("lectura de archivo", "Archivo desconocido");
        }


    }

    // Resultado de la peticion del permiso
    // 申请权限结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @org.jetbrains.annotations.NotNull String[] permissions, @NonNull @org.jetbrains.annotations.NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case READ_CODE:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.i("permiso_leer", "aceptado");
                }else{
                    Log.i("permiso_leer", "denegado");
                }
                break;
            default:
                break;
        }
    }
}