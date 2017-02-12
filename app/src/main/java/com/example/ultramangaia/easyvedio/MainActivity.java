package com.example.ultramangaia.easyvedio;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private ArrayList<String> fileArray = new ArrayList<>();
    private ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //创建控件
        Button search = (Button) findViewById(R.id.search);
        Button clear = (Button) findViewById(R.id.clear);
        listView = (ListView) findViewById(R.id.listview);

        //按钮的点击监听器
        search.setOnClickListener(this);
        clear.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String path = fileArray.get(position);
                Intent intent = new Intent(MainActivity.this,PlayActivity.class);
                intent.putExtra("path",path);
                startActivity(intent);
            }
        });

        //列表显示
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,fileArray);
        listView.setAdapter(adapter);

        //动态申请权限,并初始化路径
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

    }


    private void searchVedioPath(File []  files){
       for(File file : files) {
           if(file.isDirectory()){
               searchVedioPath(file.listFiles());
           }
           else{
               String path = file.getPath();
               if(path.endsWith("flv")||path.endsWith("mp4")){
                   fileArray.add(path);
                   Log.d(TAG, "searchVedioPath: " + path);
               }
           }
       }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String [] permissions, int [] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }
                else{
                    Toast.makeText(this,"拒绝权限将无法使用程序",Toast.LENGTH_LONG).show();
                }
                break;
            default:

        }
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.search:
                fileArray.clear();
                searchVedioPath(new File("/sdcard").listFiles());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,fileArray);
                listView.setAdapter(adapter);
                break;
            case R.id.clear:
               fileArray.clear();
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,fileArray);
                listView.setAdapter(adapter1);
                break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
