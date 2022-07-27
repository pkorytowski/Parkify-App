package com.parkingsolutions.parkifyapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.parkingsolutions.parkifyapp.DrawerActivity;
import com.parkingsolutions.parkifyapp.data.model.ReservationFull;
import com.parkingsolutions.parkifyapp.data.request.ReservationRequest;
import com.parkingsolutions.parkifyapp.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment implements RecyclerViewAdapter.ItemClickListener{

    private FragmentHomeBinding binding;
    private RecyclerViewAdapter recyclerViewAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final SwipeRefreshLayout layout = binding.homeSwipeRefresh;
        layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshRecycler();
                layout.setRefreshing(false);
            }
        });
        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        final RecyclerView recyclerView = binding.recycler;
        recyclerView.setLayoutManager(new LinearLayoutManager(DrawerActivity.context));
        List<ReservationFull> reservations = getReservations();
        System.out.println(reservations.size());
        recyclerViewAdapter = new RecyclerViewAdapter(DrawerActivity.context, reservations, this);
        recyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void refreshRecycler() {
        final RecyclerView recyclerView = binding.recycler;
        List<ReservationFull> reservations = getReservations();
        recyclerViewAdapter = new RecyclerViewAdapter(DrawerActivity.context, reservations, this);
        recyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


    public List<ReservationFull> getReservations() {
        ReservationRequest reservationRequest = new ReservationRequest();
        return reservationRequest.getFullReservations();
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(DrawerActivity.context, "You clicked " + recyclerViewAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

}