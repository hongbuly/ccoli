package com.example.school01;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ProductModel.DetailModel> mData = new ArrayList<>();
    private List<String> keys = new ArrayList<>();
    private OnItemClickListener mListener;
    
    void initialSetUp() { mData = new ArrayList<>(); }

    public interface OnItemClickListener {
        void onItemClick(int component, int position);
    }

    void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ProductAdapter() {
        FirebaseDatabase
                .getInstance()
                .getReference()
                .child("products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        mData.clear();

                        for (DataSnapshot item : snapshot.getChildren()) {
                            mData.add(item.getValue(ProductModel.DetailModel.class));
                            keys.add(item.getKey());
                        }
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout first_layout;
        ImageView product_img1;
        TextView product_name1;
        TextView product_cost1;

        LinearLayout second_layout;
        ImageView product_img2;
        TextView product_name2;
        TextView product_cost2;
        
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            first_layout = itemView.findViewById(R.id.first_layout);
            product_img1 = itemView.findViewById(R.id.product_img1);
            product_name1 = itemView.findViewById(R.id.product_name1);
            product_cost1 = itemView.findViewById(R.id.product_cost1);

            second_layout = itemView.findViewById(R.id.second_layout);
            product_img2 = itemView.findViewById(R.id.product_img2);
            product_name2 = itemView.findViewById(R.id.product_name2);
            product_cost2 = itemView.findViewById(R.id.product_cost2);
            
            first_layout.setOnClickListener(v -> {
                Toast.makeText(context, product_name1.getText().toString(), Toast.LENGTH_SHORT).show();
            });
            
            second_layout.setOnClickListener(v -> {
                Toast.makeText(context, product_name2.getText().toString(), Toast.LENGTH_SHORT).show();
            });
        }

        void onBind(ProductModel.DetailModel nData, int position) {
            product_name1.setText(nData.name01);
            product_cost1.setText(nData.cost01);
            product_name2.setText(nData.name02);
            product_cost2.setText(nData.cost02);
        }
    }


    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        context = view.getContext();
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Uri uri01 = Uri.parse(mData.get(position).img01);
        Glide.with(context)
                .load(uri01)
                .into(holder.product_img1);

        Uri uri02 = Uri.parse(mData.get(position).img02);
        Glide.with(context)
                .load(uri02)
                .into(holder.product_img2);

        holder.onBind(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
