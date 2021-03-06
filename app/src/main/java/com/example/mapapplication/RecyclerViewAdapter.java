package com.example.mapapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import static com.example.mapapplication.MapsActivity.extra_message;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static ArrayList <String> data = new ArrayList<>();

    RecyclerViewAdapter(ArrayList<String> dataSet) {

        data.addAll(0,dataSet);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        ViewHolder(View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    int position =getAdapterPosition();
                    Context context = v.getContext();
                    Intent intent = new Intent(context, WeatherInfoActivity.class);
                    String message = data.get(position);
                    intent.putExtra(extra_message, message);
                    context.startActivity(intent);

                }
            });
            textView = v.findViewById(R.id.textView);
        }

        TextView getTextView() {
            return textView;
        }
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.temp, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTextView().setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static void deleteItem(int position) {
        data.remove(position);
    }
}
