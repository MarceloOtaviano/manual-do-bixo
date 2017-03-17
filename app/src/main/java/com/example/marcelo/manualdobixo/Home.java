package com.example.marcelo.manualdobixo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ListView list =(ListView) findViewById(R.id.home_lista);
        final List<IndexItem> indexList = new ArrayList<IndexItem>();

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("indice.txt")));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                String[] parts = mLine.split(",");
                IndexItem ii = new IndexItem();
                ii.setTitle(parts[0]);
                parts[1] = parts[1].replaceAll("\\s+","");
                System.out.println(parts[0] + "----" + parts[1]);
                ii.setFileName(parts[1]);
                indexList.add(ii);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }

        CustomAdapter ca = new CustomAdapter(getApplicationContext(),indexList);
        list.setAdapter(ca);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IndexItem ii = new IndexItem();
                ii = indexList.get(i);
                Intent newIntent = new Intent(Home.this, ShowHTML.class);
                newIntent.putExtra("filename",ii.getFileName());
                startActivity(newIntent);
            }
        });
    }


}
