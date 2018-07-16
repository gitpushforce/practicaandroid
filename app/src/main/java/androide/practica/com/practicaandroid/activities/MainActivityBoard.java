package androide.practica.com.practicaandroid.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androide.practica.com.practicaandroid.R;
import androide.practica.com.practicaandroid.adapters.BoardAdapter;
import androide.practica.com.practicaandroid.models.Board;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivityBoard extends AppCompatActivity {
    private Realm realm;
    private FloatingActionButton fab;
    private ListView listView;
    private BoardAdapter adapter;
    private RealmResults<Board> boards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        //Db Realm
        realm = Realm.getDefaultInstance();

        // SELECT ALL sql
        boards = realm.where(Board.class).findAll();

        adapter = new BoardAdapter(this, boards, R.layout.list_view_board_item);
        listView = findViewById(R.id.listViewBoard);
        listView.setAdapter(adapter);
        fab = findViewById(R.id.fabAddBoard);

       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showAlertForCreatingBoard("tadd new board", "enter a name for your new board");
           }
       });
    }

    // Dialogs
    private void showAlertForCreatingBoard(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_board, null);
        builder.setView(viewInflated);

        final EditText input = viewInflated.findViewById(R.id.editTextNewBoard);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String boardName = input.getText().toString().trim();
                if (boardName.length() > 0) {
                    createNewBoard(boardName);
                } else {
                    Toast.makeText(getApplicationContext(), "the name is required to create a new board", Toast.LENGTH_SHORT).show();
                }

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    // CRUD Actions
    private void createNewBoard(String boardName) {
        realm.beginTransaction();
        Board board = new Board(boardName);
        realm.copyToRealm(board);

        realm.commitTransaction();

    }


}
