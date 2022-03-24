package com.example.empfin.screens.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.empfin.App;
import com.example.empfin.R;
import com.example.empfin.model.Note;
import com.example.empfin.screens.details.NoteDetailsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import android.view.Menu;
import android.view.MenuItem;
public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">"+getString(R.string.app_name)+"<\font>"));

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        final Adapter adapter = new Adapter();
        recyclerView.setAdapter(adapter);
//FloatingActionButton fab = findViewById(R.id.fab);
        //ImageButton imageButton = findViewById(R.id.imageButton);
       // imageButton.setOnClickListener(new View.OnClickListener() {
         //   @Override
          //  public void onClick(View view) {
           //     NoteDetailsActivity.start(MainActivity.this, null);
           // }
     //   });



        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.getNoteLiveData().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                adapter.setItems(notes);

            }

        });

        //delete on swipe
new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Note note = adapter.getNoteAt(viewHolder.getAdapterPosition());
        adapter.removeItemAt(viewHolder.getAdapterPosition());

        App.getInstance().getNoteDao().delete(note);
        Toast.makeText(MainActivity.this, "Удалено!", Toast.LENGTH_SHORT).show();
    }
}).attachToRecyclerView(recyclerView);
        //end

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        NoteDetailsActivity.start(MainActivity.this, null);
        return super.onOptionsItemSelected(item);
    }

}