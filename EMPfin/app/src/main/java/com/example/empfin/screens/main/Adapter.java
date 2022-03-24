package com.example.empfin.screens.main;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;


import com.example.empfin.App;
import com.example.empfin.R;
import com.example.empfin.model.Note;
import com.example.empfin.screens.details.NoteDetailsActivity;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.NoteViewHolder> {

    private SortedList<Note> sortedList;

    public Adapter() {

        sortedList = new SortedList<>(Note.class, new SortedList.Callback<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                if (!o2.done && o1.done) {
                    return 1;
                }
                if (o2.done && !o1.done) {
                    return -1;
                }
                return (int) (o2.timestamp - o1.timestamp);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(Note oldItem, Note newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Note item1, Note item2) {
                return item1.uid == item2.uid;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    public void removeItemAt(int index) {
        sortedList.removeItemAt(index);
        
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Note> notes) {
        sortedList.replaceAll(notes);
    }

public Note getNoteAt(int position){
        return sortedList.get(position);
}

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteText;
        TextView noteTitle;
        CheckBox completed;


        Note note;

        boolean silentUpdate;



        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);

            noteTitle = itemView.findViewById(R.id.title);

            noteText = itemView.findViewById(R.id.text);
            completed = itemView.findViewById(R.id.completed);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                   NoteDetailsActivity.start(unwrap(itemView.getContext()) , note);

                }
            });




            completed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    if (!silentUpdate) {
                        note.done = checked;
                        App.getInstance().getNoteDao().update(note);
                    }
                    updateStrokeOut();
                }
            });

        }


        public void bind(Note note) {
            this.note = note;
            noteTitle.setText(note.title);
            noteText.setText(note.text);
            updateStrokeOut();

            silentUpdate = true;
            completed.setChecked(note.done);
            silentUpdate = false;
        }

        private void updateStrokeOut() {
            if (note.done) {
                noteTitle.setPaintFlags(noteTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                noteText.setPaintFlags(noteText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                noteTitle.setPaintFlags(noteTitle.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                noteText.setPaintFlags(noteText.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
    private static Activity unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return (Activity) context;
    }
}