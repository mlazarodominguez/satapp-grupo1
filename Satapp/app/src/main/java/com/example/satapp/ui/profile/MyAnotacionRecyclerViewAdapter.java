package com.example.satapp.ui.profile;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.satapp.AnotacionDetailActivity;
import com.example.satapp.R;
import com.example.satapp.viewmodel.AnotacionViewModel;

import java.util.ArrayList;
import java.util.List;

public class MyAnotacionRecyclerViewAdapter extends RecyclerView.Adapter<MyAnotacionRecyclerViewAdapter.ViewHolder> {

    private List<String> mValues;
    AnotacionViewModel anotacionViewModel;
    Context context;

    public MyAnotacionRecyclerViewAdapter(Context ctx, List<String> anotaciones, AnotacionViewModel anotacionViewModel) {
        context = ctx;
        mValues = anotaciones;
        this.anotacionViewModel = anotacionViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_anotacion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnotacionDetailActivity.class);
                intent.putExtra("idAnotacion", "");
                context.startActivity(intent);
            }
        });
    }

    public void setData(List<String> list){
        if(this.mValues != null) {
            this.mValues.clear();
        } else {
            this.mValues =  new ArrayList<>();
        }
        this.mValues.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public String mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
        }
    }
}
