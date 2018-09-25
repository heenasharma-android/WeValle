package com.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.albaniancircle.R;
import com.models.Profession;

import java.util.ArrayList;
import java.util.List;

public class ProfessionAdapter  extends RecyclerView.Adapter<ProfessionAdapter.MyViewHolder> {

    private List<Profession> mModelList;
    OnItemSelectedListener itemSelectedListener;
    Context context;

    public ProfessionAdapter(List<Profession> modelList, Context context,  OnItemSelectedListener itemSelectedListener) {
        this.mModelList = modelList;
        this.context=context;
        this.itemSelectedListener=itemSelectedListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Profession model = mModelList.get(position);
        holder.textView.setText(model.getProfession());

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.isSelected()) {
                    setChecked(false,holder,model);
                } else {
                    setChecked(true, holder, model);
                }
                itemSelectedListener.onItemSelected(model);
            }
        });
    }


    public void setChecked(boolean value, MyViewHolder holder,Profession model) {
        if (value) {
            model.setSelected(!model.isSelected());
            holder.view.setBackgroundColor(Color.GRAY);
            holder.textView.setTextColor(Color.WHITE);
        } else {
            holder.view.setBackgroundColor(Color.WHITE);
            holder.textView.setTextColor(Color.BLACK);

        }
        model.setSelected(value);
        holder.textView.setChecked(value);
    }

    @Override
    public int getItemCount() {
        return mModelList == null ? 0 : mModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public CheckedTextView textView;

        private MyViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
            textView = (CheckedTextView) itemView.findViewById(R.id.text_view);
        }



    }


    public List<Profession> getSelectedItems() {

        List<Profession> selectedItems = new ArrayList<>();
        for (Profession item : mModelList) {
            if (item.isSelected()) {
                selectedItems.add(item);
            }
        }
        return selectedItems;
    }


    public interface OnItemSelectedListener {

        void onItemSelected(Profession item);
    }






}
