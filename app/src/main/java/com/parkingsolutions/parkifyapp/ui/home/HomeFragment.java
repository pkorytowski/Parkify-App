package com.parkingsolutions.parkifyapp.ui.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingsolutions.parkifyapp.MainActivity;
import com.parkingsolutions.parkifyapp.R;
import com.parkingsolutions.parkifyapp.data.model.ReservationFull;
import com.parkingsolutions.parkifyapp.data.request.HttpRequest;
import com.parkingsolutions.parkifyapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment  implements HomeRecyclerViewAdapter.ItemClickListener {

    private FragmentHomeBinding binding;
    private HomeRecyclerViewAdapter adapter;
    private HttpRequest httpRequest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.context);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        httpRequest = new HttpRequest();
        String response = httpRequest.get("reservation/full/" + prefs.getString("id", ""));
        ObjectMapper mapper = new ObjectMapper();

        List<ReservationFull> reservationFullList = new ArrayList<>();

        try {
            reservationFullList = mapper.readValue(response, mapper.getTypeFactory().constructCollectionType(List.class, ReservationFull.class));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        RecyclerView recyclerView = root.findViewById(R.id.home_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.context));
        adapter = new HomeRecyclerViewAdapter(MainActivity.context, reservationFullList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(MainActivity.context, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}