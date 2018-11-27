package com.smt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.smt.R;
import com.smt.config.SMTApplication;
import com.smt.domain.Attachment;
import com.smt.inter.DownManager;

import java.util.ArrayList;
import java.util.List;

public class AttachmentAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater mInflater;
    private List<Attachment> attachments = new ArrayList<Attachment>();

    public AttachmentAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        if (attachments != null)
            return attachments.size();
        else
            return 0;
    }

    public void setData(List<Attachment> attachments) {
        this.attachments = attachments;
        this.notifyDataSetChanged();
    }

    @Override
    public Object getItem(int id) {
        return attachments.get(id);
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
            convertView = mInflater.inflate(R.layout.list_attachment, parent, false);
            holder.attachmentTitle = convertView.findViewById(R.id.attachment_title);
            holder.attachmentDownload = convertView.findViewById(R.id.download_attachment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Attachment res = attachments.get(position);

        holder.attachmentTitle.setText(res.name+"."+res.suffix);
        holder.attachmentDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String downUrl = res.url;
                String downPath = SMTApplication.getAttachmentDirDir() +res.name+"."+res.suffix;
                DownManager downManager = new DownManager(context);
                downManager.downSatrt(downUrl, downPath, "是否下载文件");
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView attachmentTitle;
        Button attachmentDownload;
    }
}