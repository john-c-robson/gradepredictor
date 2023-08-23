package com.example.mobile_dev_assignment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class M_RecyclerViewAdapter extends RecyclerView.Adapter<M_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<ModuleClass> moduleClasses;
    SelectListener listener;

    public M_RecyclerViewAdapter(Context context, ArrayList<ModuleClass> moduleClasses, SelectListener listener){
        this.context = context;
        this.moduleClasses = moduleClasses;
        this.listener = listener;
    }


    @NonNull
    @Override
    public M_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rec_view_row, parent, false);

        return new M_RecyclerViewAdapter.MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull M_RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.modName.setText(moduleClasses.get(position).getModName());
        holder.modCode.setText(moduleClasses.get(position).getModCode());
        holder.modMark.setText(String.valueOf(moduleClasses.get(position).getModMark()));
        holder.modDate.setText(moduleClasses.get(position).getModDate());
        holder.editButton.setTag(moduleClasses.get(position).getId());
        holder.deleteButton.setTag(moduleClasses.get(position).getId());
     }

    @Override
    public int getItemCount() {
        return moduleClasses.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        Button editButton;
        Button deleteButton;
        TextView modCode;
        TextView modName;
        TextView modMark;
        TextView modDate;
        SelectListener listener; //As recycler class is out of bounds of selectActivity, interface is
                                    //used to pass the elements moduleID for directing intents back
                                    //in selectEntryActivity

        public MyViewHolder(@NonNull View itemView, SelectListener listener) {
            super(itemView);

            editButton = itemView.findViewById(R.id.edit_Button);
            deleteButton = itemView.findViewById(R.id.delete_Button);
            modCode = itemView.findViewById(R.id.code_textView);
            modName = itemView.findViewById(R.id.name_textView);
            modMark = itemView.findViewById(R.id.mark_textView);
            modDate = itemView.findViewById(R.id.date_textView);
            this.listener = listener;

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            switch (view.getId())
            {
                case R.id.edit_Button:

                    Log.i("edit button","edit button pressed");
                    Log.i("edit button","my ID is: " + editButton.getTag());
                    int newValue = Integer.parseInt(view.getTag().toString());
                    //This line heads back to SelectEntryActivity onItemClicked()
                    listener.onItemClicked(R.id.edit_Button,newValue);

                    break;
                case R.id.delete_Button:

                    Log.i("delete button","delete button pressed");
                    Log.i("delete button","my ID is: " + deleteButton.getTag());
                    int newValue2 = Integer.parseInt(view.getTag().toString());
                    listener.onItemClicked(R.id.delete_Button,newValue2);

                    break;
                default:
                    break;
            }

        }

    }
}
