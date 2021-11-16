package com.example.loacationsaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.loacationsaver.model.LocationModel;
import com.example.loacationsaver.model.db.LocationDBAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    LocationModel model;
    Button add;
    ListView show;
    List<String> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitView();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    boolean flag=model.saveLocation("123","321");
                    if(flag){
                        Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                        Refresh();
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Un-Successful",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if(DeleteLocation(position+1)){
                        Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                        Refresh();
                    }
                    else{
                        Toast.makeText(MainActivity.this,"Unsuccessful",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean DeleteLocation(int SrNo) throws Exception {
        return model.DeleteLocation(SrNo);
    }

    public void InitView(){
        add=(Button) findViewById(R.id.savelocation);
        show=(ListView) findViewById(R.id.show);
        Refresh();
    }

    public LocationModel GetModel(Context context){
        return new LocationModel(LocationDBAdapter.getLocationInstance(context));
    }

    public void Refresh(){
        try {
            model= GetModel(MainActivity.this);
            list=model.getAllSavedLocation();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.listview,list);
            show.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
        }
    }
}