package com.example.mymap;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymap.Model.ParkViewModel;
import com.example.mymap.adapter.ParkRecycleViewAdapter;
import com.example.mymap.adapter.ViewPagerAdapter;
import com.example.mymap.data.Repository;

import java.util.ArrayList;


public class FragmentDetails extends Fragment {

   private ParkViewModel parkViewModel;
   private ViewPagerAdapter viewPagerAdapter;
   private ViewPager2 viewPager2;
    public FragmentDetails() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FragmentDetails newInstance() {
        FragmentDetails fragment = new FragmentDetails();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      viewPager2=view.findViewById(R.id.viewpager);
      parkViewModel=new ViewModelProvider(requireActivity()).get(ParkViewModel.class);
        TextView parkName = view.findViewById(R.id.name);
        TextView parkDes = view.findViewById(R.id.degination);
        TextView description = view.getRootView().findViewById(R.id.details_description);
        TextView activities = view.getRootView().findViewById(R.id.details_activities);
        TextView entranceFees = view.getRootView().findViewById(R.id.details_entrancefees);
        TextView opHours = view.getRootView().findViewById(R.id.details_operatinghours);
        TextView detailsTopics = view.getRootView().findViewById(R.id.details_topics);
        TextView directions = view.getRootView().findViewById(R.id.details_directions);

        parkViewModel.getSelectedPark().observe(this, park -> {
            parkName.setText(park.getName());
            parkDes.setText(park.getDesignation());
            description.setText(park.getDescription());



            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < park.getActivities().size(); i++) {
                stringBuilder.append(park.getActivities().get(i).getName())
                        .append(" | ");

            }
            activities.setText(stringBuilder);
            if (park.getEntranceFees().size() > 0) {
                entranceFees.setText(String.format("Cost: $%s", park.getEntranceFees().get(0).getCost()));
            }else {
                entranceFees.setText("Info is not available");
            }
            StringBuilder opsString = new StringBuilder();
            opsString.append("Wednesday: ").append(park.getOperatingHours().get(0).getStandardHours().getWednesday()).append("\n")
                    .append("Monday: ").append(park.getOperatingHours().get(0).getStandardHours().getMonday()).append("\n")
                    .append("Thursday: ").append(park.getOperatingHours().get(0).getStandardHours().getThursday()).append("\n")
                    .append("Sunday: ").append(park.getOperatingHours().get(0).getStandardHours().getSunday()).append("\n")
                    .append("Tuesday: ").append(park.getOperatingHours().get(0).getStandardHours().getTuesday()).append("\n")
                    .append("Friday: ").append(park.getOperatingHours().get(0).getStandardHours().getFriday()).append("\n")
                    .append("Saturday: ").append(park.getOperatingHours().get(0).getStandardHours().getSaturday());

            opHours.setText(opsString);

            StringBuilder topicBuilder = new StringBuilder();
            for (int i = 0; i < park.getTopics().size() ; i++) {
                topicBuilder.append(park.getTopics().get(i).getName()).append(" | ");
            }
            detailsTopics.setText(topicBuilder);

            if (!TextUtils.isEmpty(park.getDirectionsInfo())) {
                directions.setText(park.getDirectionsInfo());
            }else {
                directions.setText("Direction is not available");
            }


            viewPagerAdapter = new ViewPagerAdapter(park.getImages());
            viewPager2.setAdapter(viewPagerAdapter);

        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        viewPager2 = view.findViewById(R.id.viewpager);
        viewPagerAdapter = new ViewPagerAdapter(new ArrayList<>());
        viewPager2.setAdapter(viewPagerAdapter);

        return view;}
}