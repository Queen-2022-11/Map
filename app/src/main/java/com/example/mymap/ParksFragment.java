package com.example.mymap;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymap.Model.Park;
import com.example.mymap.Model.ParkViewModel;
import com.example.mymap.adapter.OnparkClickListener;
import com.example.mymap.adapter.ParkRecycleViewAdapter;
import com.example.mymap.data.Repository;

import java.util.ArrayList;
import java.util.List;


public class ParksFragment extends Fragment implements OnparkClickListener {
    private RecyclerView recyclerView;
    private ParkRecycleViewAdapter parkRecycleViewAdapter;
    private List<Park> parkList;
    private ParkViewModel parkViewModel;


    public ParksFragment() {
        // Required empty public constructor
    }


    public static ParksFragment newInstance() {
        ParksFragment fragment = new ParksFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parkList = new ArrayList<>();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);
        if (parkViewModel.getParks().getValue() != null) {
            parkList = parkViewModel.getParks().getValue();
            parkRecycleViewAdapter = new ParkRecycleViewAdapter(parkList, this);
            recyclerView.setAdapter(parkRecycleViewAdapter);
        }    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_parks, container, false);
        recyclerView=view.findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onParkClick(Park park) {
        Log.d("Park","Touch"+ park.getName());
        assert getFragmentManager() != null;
        getFragmentManager().beginTransaction().replace(R.id.Fragment_id,FragmentDetails.newInstance()).commit();
    }
}