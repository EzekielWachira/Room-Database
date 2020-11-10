package com.ezzy.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ezzy.roomdatabase.database.MainData;
import com.ezzy.roomdatabase.database.RoomDB;
import com.ezzy.roomdatabase.utils.MainAdapter;
import com.ezzy.roomdatabase.utils.VerticaltemDecorator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText titleEditText;
    private Button addButton, resetButton;
    private RecyclerView dataRecyclerView;

    List<MainData> mainDataList =  new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;
    VerticaltemDecorator decorator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEditText = findViewById(R.id.titleEditText);
        addButton = findViewById(R.id.addButton);
        resetButton = findViewById(R.id.resetButton);
        dataRecyclerView = findViewById(R.id.recyclerview);

        init();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sText = titleEditText.getText().toString();
                if (!sText.equals("")){
                    MainData mainData = new MainData();
                    mainData.setTitle(sText);
                    database.mainDao().insert(mainData);
                    titleEditText.setText("");
                    mainDataList.clear();
                    mainDataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.mainDao().reset(mainDataList);
                mainDataList.clear();
                mainDataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void init() {
        database = RoomDB.getInstance(this);
        mainDataList = database.mainDao().getAll();


        linearLayoutManager = new LinearLayoutManager(this);
        dataRecyclerView.setLayoutManager(linearLayoutManager);
        dataRecyclerView.addItemDecoration(new VerticaltemDecorator(10));
        adapter = new MainAdapter(mainDataList, this);
        dataRecyclerView.setAdapter(adapter);

    }
}