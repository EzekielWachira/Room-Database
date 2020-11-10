package com.ezzy.roomdatabase.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ezzy.roomdatabase.R;
import com.ezzy.roomdatabase.database.MainData;
import com.ezzy.roomdatabase.database.RoomDB;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {

    private List<MainData> mainDataList;
    private Activity context;
    private RoomDB database;

    public MainAdapter(List<MainData> mainDataList, Activity context) {
        this.mainDataList = mainDataList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item, parent, false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        MainData mainData = mainDataList.get(position);
        //initialize database
        database = RoomDB.getInstance(context);

        holder.titleTextView.setText(mainData.getTitle());

        //handle edit button
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainData data = mainDataList.get(holder.getAdapterPosition());
                int sID = data.getId();
                String sText = data.getTitle();

                //create edit dialog
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_update);
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setLayout(width, height);
                dialog.show();

                EditText editText = dialog.findViewById(R.id.edit_title);
                Button updateButton = dialog.findViewById(R.id.buttonUpdate);
                //set text on the edit text
                editText.setText(sText);
                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        //get updated text from edittext
                        String updatedText = editText.getText().toString().trim();
                        //update text in the database
                        database.mainDao().update(sID, sText);
                        //notify when datalist changes
                        mainDataList.clear();
                        mainDataList.addAll(database.mainDao().getAll());
                        notifyDataSetChanged();
                    }
                });
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainData data = mainDataList.get(holder.getAdapterPosition());
                database.mainDao().delete(data);

                int position = holder.getAdapterPosition();
                mainDataList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mainDataList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mainDataList.size();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageButton editButton, deleteButton;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.titleTextview);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
