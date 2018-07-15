package androide.practica.com.practicaandroid.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androide.practica.com.practicaandroid.MyAdapter;
import androide.practica.com.practicaandroid.R;

public class MainActivityBoard extends AppCompatActivity {
    private ListView listView;
    //private GridView gridView;
    private List<String> names;
    private FloatingActionButton fab;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fab = findViewById(R.id.fabAddBoard);
        listView = findViewById(R.id.listview);

        names = new ArrayList<>();
        names.add("abc1");
        names.add("abc2");
        names.add("abc3");
        names.add("abc4");
        names.add("abc5");
        names.add("abc6");
        names.add("abc7");
        names.add("abc8");
        names.add("abc9");
        names.add("abc10");
        names.add("abc11");
        names.add("abc12");
        names.add("abc13");
        names.add("abc14");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivityBoard.this, "Clicked: " + names.get(position), Toast.LENGTH_LONG).show();
            }
        });

        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, names);
        listView.setAdapter(myAdapter);
    }

}
