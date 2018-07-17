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
import androide.practica.com.practicaandroid.adapters.NoteAdapter;
import androide.practica.com.practicaandroid.models.Board;
import androide.practica.com.practicaandroid.models.Note;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NoteActivity extends AppCompatActivity implements RealmChangeListener<Board> {

    private ListView listView;
    private FloatingActionButton fab;

    private NoteAdapter adapter;
    private RealmList<Note> notes;
    private Realm realm;

    private int boardId;
    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        realm = Realm.getDefaultInstance();

        if(getIntent().getExtras() != null) {
           boardId = getIntent().getExtras().getInt("id");
        }

        board = realm.where(Board.class).equalTo("id", boardId).findFirst();
        board.addChangeListener(this);
        notes = board.getNotes();

        this.setTitle(board.getTitle());

        fab = findViewById(R.id.fabAddNote);
        listView = findViewById(R.id.listViewNote);
        adapter = new NoteAdapter(this, notes, R.layout.list_view_note_item);

        listView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertForCreatingNote("title", "message");
            }
        });

    }


    // Dialogs
    private void showAlertForCreatingNote(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (title != null) builder.setTitle(title);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_note, null);
        builder.setView(viewInflated);

        final EditText input = viewInflated.findViewById(R.id.editTextNewNote);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String note = input.getText().toString().trim();
                if (note.length() > 0) {
                    createNewNote(note);
                } else {
                    Toast.makeText(getApplicationContext(), "the note should not be empty", Toast.LENGTH_SHORT).show();
                }

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void createNewNote (String note) {
        realm.beginTransaction();
        Note _note = new Note(note);
        realm.copyToRealm(_note);
        board.getNotes().add(_note);
        realm.commitTransaction();


    }

    @Override
    public void onChange(Board board) {
        adapter.notifyDataSetChanged();
    }
}
