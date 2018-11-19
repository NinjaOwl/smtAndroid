package com.smt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smt.R;
import com.smt.domain.Resources;

import java.util.ArrayList;
import java.util.List;

public class ResourcesAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<Resources> resources = new ArrayList<Resources>();

    public ResourcesAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        if (resources != null)
            return resources.size();
        else
            return 0;
    }

    public void setData(List<Resources> resources) {
        this.resources = resources;
        this.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int id) {
        return resources.get(id);
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
            convertView = mInflater.inflate(R.layout.list_resources, parent, false);
            holder.title = convertView.findViewById(R.id.title);
            holder.createTime = convertView.findViewById(R.id.create_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Resources res = resources.get(position);

        holder.title.setText(res.title);
        holder.createTime.setText(res.createTime);

        return convertView;
    }

    class ViewHolder {
        TextView title;
        TextView createTime;
    }
}