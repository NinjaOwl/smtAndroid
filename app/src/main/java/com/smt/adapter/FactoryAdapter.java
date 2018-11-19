package com.smt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smt.R;
import com.smt.domain.Factory;

import java.util.ArrayList;
import java.util.List;

public class FactoryAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<Factory> factories = new ArrayList<Factory>();

    public FactoryAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        if (factories != null)
            return factories.size();
        else
            return 0;
    }

    public void setData(List<Factory> factories) {
        this.factories = factories;
        this.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int id) {
        return factories.get(id);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_factory, parent, false);
            holder.name = convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Factory factory = factories.get(position);

        holder.name.setText(factory.name);

        return convertView;
    }

    class ViewHolder {
        TextView name;
    }
}