package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DbActivity extends AppCompatActivity {

    EditText et_data;
    Button btn_save2;
    Database database;
    Dao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        et_data = findViewById(R.id.et_data);
        btn_save2 = findViewById(R.id.btn_save2);
        database = Database.getInstance(this);
        dao = database.getDao();

        btn_save2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = et_data.getText() != null
                                ? et_data.getText().toString()
                                : "";
                if (data.length() > 0){
                    Entity entity = new Entity();
                    entity.data = data;
                    dao.insertEntity(entity);
                }
            }
        });
    }
}