package com.example.mobile_dev_assignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OverviewAdapter extends BaseAdapter {
    Context context;
    ArrayList <ModuleClass> moduleClasses;

    public OverviewAdapter(Context context, ArrayList<ModuleClass> moduleClasses) {
        this.context = context;
        this.moduleClasses = moduleClasses;
    }

    @Override
    public int getCount() {
        return moduleClasses.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.listview_item, viewGroup, false);

        TextView txtName,txtCode,txtMark,txtDate;

        txtName = view.findViewById(R.id.name_tv);
        txtCode = view.findViewById(R.id.code_tv);
        txtMark = view.findViewById(R.id.mark_tv);
        txtDate = view.findViewById(R.id.date_tv);

        txtName.setText(moduleClasses.get(i).getModName());
        txtCode.setText(moduleClasses.get(i).getModCode());
        txtMark.setText(String.valueOf(moduleClasses.get(i).getModMark()));
        txtDate.setText(moduleClasses.get(i).getModDate());

        return view;
    }
}
