package com.example.calendarapp.ui.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapp.R;
import com.example.calendarapp.ui.models.ExamModel;

import java.util.List;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ExamViewHolder> {
    private Context mContext;
    private List<ExamModel> mExamList;

    public ExamListAdapter(Context context, List<ExamModel> examList) {
        mContext = context;
        mExamList = examList;
    }

    public static class ExamViewHolder extends RecyclerView.ViewHolder {
        public TextView examNameTextView;
        public TextView examDateTextView;
        public TextView examLocationTimeView;
        public TextView examNoteTimeView;

        public ExamViewHolder(View itemView) {
            super(itemView);
            examNameTextView = itemView.findViewById(R.id.examNameTextView);
            examDateTextView = itemView.findViewById(R.id.examDateTextView);
            examLocationTimeView = itemView.findViewById(R.id.exam)
        }
    }
}
