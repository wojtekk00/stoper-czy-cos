package com.example.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewCzas;
    private Button buttonStart;
    private Button buttonStop;
    private Button buttonReset;
    private Button buttonZapisz;
    private ListView listViewCzasy;
    private int ileSekund;
    private boolean czyZlicza;
    private ArrayList<String> arrayListCzasy;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ileSekund = 0;
        czyZlicza = false;

        textViewCzas = findViewById(R.id.textViewCzas);
        buttonStart = findViewById(R.id.buttonStart);
        buttonStop = findViewById(R.id.buttonStop);
        buttonReset = findViewById(R.id.buttonReset);
        buttonZapisz = findViewById(R.id.buttonZapisz);
        listViewCzasy = findViewById(R.id.listViewCzasy);

        arrayListCzasy = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListCzasy);
        listViewCzasy.setAdapter(arrayAdapter);

        Handler handler = new Handler();
        handler.post(
                new Runnable() {
                    @Override
                    public void run() {
                        if(czyZlicza){
                            ileSekund++;
                            textViewCzas.setText(zwrocCzas());
                        }
                        handler.postDelayed(this, 1000);
                    }
                }
        );

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                czyZlicza = true;
            }
        });
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                czyZlicza = false;
            }
        });
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ileSekund = 0;
                textViewCzas.setText(zwrocCzas());
                czyZlicza = false;
            }
        });
        buttonZapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrayListCzasy.add(zwrocCzas());
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
    private String zwrocCzas(){
        int sekundy = ileSekund%60;
        int minuty = (ileSekund/60)%60;
        int godziny = ileSekund/3600;
        return String.format("%02d : %02d : %02d", godziny, minuty, sekundy);
    }
}