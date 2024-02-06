package com.example.calendarapp.ui.slideshow;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapp.R;
import com.example.calendarapp.databinding.FragmentSlideshowBinding;
import com.example.calendarapp.ui.adapters.ExamListAdapter;
import com.example.calendarapp.ui.models.ExamModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private RecyclerView recyclerViewExams;
    private List<ExamModel> examList;
    private ExamListAdapter adapter;
    private SharedPreferences sharedPreferences;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerViewExams = root.findViewById(R.id.recyclerViewExams);
        recyclerViewExams.setLayoutManager(new LinearLayoutManager(getActivity()));

        examList = new ArrayList<>();

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        loadExamsFromSharedPreferences();

        adapter = new ExamListAdapter(getActivity(), examList);
        recyclerViewExams.setAdapter(adapter);

//        examList.add(new ExamModel("Math Exam", LocalDateTime.of(2024, 3, 15, 9, 30), "Room 101", "Chapter 1-5"));
//        examList.add(new ExamModel("History Exam", LocalDateTime.of(2024, 4, 22, 13, 45), "Auditorium", "World Wars"));
//        examList.add(new ExamModel("Science Exam", LocalDateTime.of(2024, 5, 10, 10, 15), "Laboratory", "Chemistry and Biology"));
//        examList.add(new ExamModel("English Exam", LocalDateTime.of(2024, 6, 5, 11, 0), "Classroom 202", "Literature Analysis"));
//        adapter.notifyDataSetChanged();

        Button addButton = root.findViewById(R.id.examsAddButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.edit_exam_dialog, null);
                showEditDialog(dialogView);
            }
        });

        return root;
    }

    private void loadExamsFromSharedPreferences() {
        Set<String> examSet = sharedPreferences.getStringSet("examSet", new HashSet<>());
        for (String examString : examSet) {
            ExamModel examModel = ExamModel.fromStringExam(examString);
            if (examModel != null) {
                examList.add(examModel);
            }
        }
    }

    private void saveExamsToSharedPreferences() {
        Set<String> examSet = new HashSet<>();
        for (ExamModel examModel : examList) {
            examSet.add(examModel.toString());
        }
        sharedPreferences.edit().putStringSet("examSet", examSet).apply();
    }

    private void showEditDialog(View dialogView) {
        EditText editExamName = dialogView.findViewById(R.id.editExamName);
        Spinner editExamHour = dialogView.findViewById(R.id.editExamHour);
        Spinner editExamMinute = dialogView.findViewById(R.id.editExamMinute);
        Spinner editExamAm = dialogView.findViewById(R.id.editExamAm);
        Spinner editExamDay = dialogView.findViewById(R.id.editExamDay);
        Spinner editExamMonth = dialogView.findViewById(R.id.editExamMonth);
        EditText editExamLocation = dialogView.findViewById(R.id.editExamLocation);
        EditText editExamNote = dialogView.findViewById(R.id.editExamNote);
        Button buttonSave = dialogView.findViewById(R.id.editExamButtonSave);
        Button buttonCancel = dialogView.findViewById(R.id.editExamButtonCancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(dialogView.getContext());
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dueHour = Integer.parseInt(editExamHour.getSelectedItem().toString());
                int dueMinute = Integer.parseInt(editExamMinute.getSelectedItem().toString());
                String dueAmPm = editExamAm.getSelectedItem().toString();
                int dueDay = editExamDay.getSelectedItemPosition() + 1;
                int dueMonth = editExamMonth.getSelectedItemPosition() + 1;

                if (dueAmPm.equals("PM")) {
                    if (dueHour < 12) {
                        dueHour += 12;
                    }
                } else {
                    if (dueHour == 12) {
                        dueHour = 0;
                    }
                }

                LocalDateTime currentDateTime = LocalDateTime.now();
                int currentYear = currentDateTime.getYear();

                LocalDateTime time = LocalDateTime.of(currentYear, dueMonth, dueDay, dueHour, dueMinute);

                String name = editExamName.getText().toString();
                String location = editExamLocation.getText().toString();
                String note = editExamNote.getText().toString();

                ExamModel exam = new ExamModel(name, time, location, note);
                examList.add(exam);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        saveExamsToSharedPreferences();
        binding = null;
    }
}