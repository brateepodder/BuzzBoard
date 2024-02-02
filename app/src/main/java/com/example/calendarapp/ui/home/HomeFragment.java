package com.example.calendarapp.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendarapp.ClassModel;
import com.example.calendarapp.databinding.FragmentHomeBinding;
import com.example.calendarapp.ui.adapters.ClassListAdapter;
import com.example.calendarapp.R;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewClasses;
    private ClassListAdapter adapter;
    private List<ClassModel> classList;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize recycler view
        recyclerViewClasses = root.findViewById(R.id.recyclerViewClasses);
        recyclerViewClasses.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize class list
        classList = new ArrayList<>();

        // Initialize adapter
        adapter = new ClassListAdapter(getActivity(), classList);
        recyclerViewClasses.setAdapter(adapter);

        // Add sample data (you can replace this with your actual data)
        classList.add(new ClassModel("CS2110", LocalTime.parse("2:00 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("3:15 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.TUESDAY, DayOfWeek.THURSDAY}, "Dr. MaryGold"));
        classList.add(new ClassModel("CS2200", LocalTime.parse("09:30 AM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("10:45 PM", DateTimeFormatter.ofPattern("h:mm a")),new DayOfWeek[]{DayOfWeek.TUESDAY, DayOfWeek.THURSDAY}, "Prof. Johnson"));
        classList.add(new ClassModel("MATH1553", LocalTime.parse("10:30 AM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("12:30 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY}, "Dr. Smith"));
        classList.add(new ClassModel("ARBC1002", LocalTime.parse("5:00 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("6:00 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY}, "Mr. Ahmed"));
        classList.add(new ClassModel("CS2110", LocalTime.parse("2:00 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("3:15 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.TUESDAY, DayOfWeek.THURSDAY}, "Dr. MaryGold"));
        classList.add(new ClassModel("CS1332", LocalTime.parse("09:30 AM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("10:45 PM", DateTimeFormatter.ofPattern("h:mm a")),new DayOfWeek[]{DayOfWeek.TUESDAY, DayOfWeek.THURSDAY}, "Prof. Johnson"));
        classList.add(new ClassModel("MATH1554", LocalTime.parse("10:30 AM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("12:30 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY}, "Dr. Smith"));
        classList.add(new ClassModel("ARBC1001", LocalTime.parse("5:00 PM", DateTimeFormatter.ofPattern("h:mm a")), LocalTime.parse("6:00 PM", DateTimeFormatter.ofPattern("h:mm a")), new DayOfWeek[]{DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY}, "Mr. Ahmed"));
        // Notify adapter about data change
        adapter.notifyDataSetChanged();

//        classList.removeIf(classModel -> classModel.getCourseName().equals("ARBC1002"));
//        classList.removeIf(classModel -> classModel.getCourseName().equals("MATH1554"));
//
//        // Notify adapter about the data change after removing classes
//        adapter.notifyDataSetChanged();


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}