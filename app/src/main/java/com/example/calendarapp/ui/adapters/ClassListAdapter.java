package com.example.calendarapp.ui.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapp.ClassModel;
import com.example.calendarapp.R;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
        public View classBubbleView; // Add a reference to the background view


        public ClassViewHolder(View itemView) {
            super(itemView);
            courseNameTextView = itemView.findViewById(R.id.courseNameTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            daysTextView = itemView.findViewById(R.id.daysTextView);
            instructorsTextView = itemView.findViewById(R.id.instructorsTextView);
            classBubbleView = itemView.findViewById(R.id.bubble); // Initialize the reference
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
        Collections.sort(mClassList);

        // Set item views based on your views and data model
        holder.courseNameTextView.setText(classModel.getCourseName());
        holder.timeTextView.setText(classModel.getStartEndTimeAsString());
        holder.daysTextView.setText(classModel.getDaysAsString());
        holder.instructorsTextView.setText(classModel.getInstructors());

        ArrayList<Integer> colorResources = new ArrayList<>(Arrays.asList(
                R.color.bubble_1,
                R.color.bubble_2,
                R.color.bubble_3,
                R.color.bubble_4,
                R.color.bubble_5,
                R.color.bubble_6,
                R.color.bubble_7,
                R.color.bubble_8,
                R.color.bubble_9,
                R.color.bubble_10,
                R.color.yellow
        ));

        Random random = new Random();
        int randomNumber = random.nextInt(11);

        int selectedColorResource = colorResources.get(randomNumber);

// Get the actual color integer value from the color resource
        int selectedColor = ContextCompat.getColor(mContext, selectedColorResource);

        Drawable backgroundDrawable = ContextCompat.getDrawable(mContext, R.drawable.class_bubble);
        backgroundDrawable.setTint(selectedColor);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mClassList.size();
    }
}
