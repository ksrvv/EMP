package com.example.empfin.screens.details;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.empfin.App;
import com.example.empfin.R;
import com.example.empfin.model.Note;

public class NoteDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_NOTE = "NoteDetailsActivity.EXTRA_NOTE";

    private Note note;

    private EditText editText;
    private EditText editText2;

    public static void start(Activity caller, Note note) {
        Intent intent = new Intent(caller, NoteDetailsActivity.class);
        if (note != null) {
            intent.putExtra(EXTRA_NOTE, note);
        }
        caller.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Button button=findViewById(R.id.add);

        setTitle(getString(R.string.note_details_title));

        editText2 = findViewById(R.id.title);
        editText = findViewById(R.id.text);

        if (getIntent().hasExtra(EXTRA_NOTE)) {
            note = getIntent().getParcelableExtra(EXTRA_NOTE);
            editText.setText(note.text);
            editText2.setText(note.title);
        } else {
            note = new Note();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void add(View view) {
        if (editText.getText().length() > 0 && editText2.getText().length() > 0) {
            note.text = editText.getText().toString();
            note.title = editText2.getText().toString();
            note.done = false;
            note.timestamp = System.currentTimeMillis();
            if (getIntent().hasExtra(EXTRA_NOTE)) {
                App.getInstance().getNoteDao().update(note);
            } else {
                App.getInstance().getNoteDao().insert(note);
            }
            finish();
        }
    }

    public void cancel(View view) {
        finish();
    }
}