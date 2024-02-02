package com.example.calendarapp.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapp.ClassModel;
import com.example.calendarapp.R;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ClassListAdapter extends RecyclerView.Adapter<ClassListAdapter.ClassViewHolder> {

    private Context mContext; // Context reference
    private List<ClassModel> mClassList; // List of class models

    // Constructor
    public ClassListAdapter(Context context, List<ClassModel> classList) {
        mContext = context; // Initialize Context reference
        mClassList = classList; // Initialize list of class models
    }

    // ViewHolder class
    public static class ClassViewHolder extends RecyclerView.ViewHolder {
        public TextView courseNameTextView;
        public TextView timeTextView;
        public TextView daysTextView;
        public TextView instructorsTextView;

        public ClassViewHolder(View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            daysTextView = itemView.findViewById(R.id.daysTextView);
            instructorsTextView = itemView.findViewById(R.id.instructorsTextView);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        // Get the data model based on position
        ClassModel classModel = mClassList.get(position);

        // Set item views based on your views and data model
        holder.courseNameTextView.setText(classModel.getCourseName());
        holder.timeTextView.setText(classModel.getStartEndTimeAsString());
        holder.daysTextView.setText(classModel.getDaysAsString());
        holder.instructorsTextView.setText(classModel.getInstructors());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mClassList.size();
    }
}
