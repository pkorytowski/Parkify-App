package com.parkingsolutions.parkifyapp.ui.home;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.parkingsolutions.parkifyapp.R;
import com.parkingsolutions.parkifyapp.data.model.ReservationFull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ReservationFull> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, List<ReservationFull> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.home_recycler_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReservationFull reservation = mData.get(position);

        holder.reservationStatus.setText(reservation.getReservation().getReservationStatus().toString());
        holder.reservationAddress.setText(getAddress(reservation));
        holder.reservationLane.setText(reservation.getReservation().getLaneName());
        holder.reservationTime.setText(getFormattedDate(reservation.getReservation().getReservationEnd()));
        holder.reservationOther.setText(" ");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getFormattedDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return date.format(formatter);
    }

    private String getAddress(ReservationFull reservation) {
        StringBuilder address = new StringBuilder();

        address.append(reservation.getParking().getName());
        address.append(", ");
        address.append(reservation.getParking().getStreet());
        address.append(", ");
        address.append(reservation.getParking().getPostalcode());
        address.append(" ");
        address.append(reservation.getParking().getCity());

        return address.toString();
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView reservationStatus;
        TextView reservationAddress;
        TextView reservationLane;
        TextView reservationTime;
        TextView reservationOther;

        ViewHolder(View itemView) {
            super(itemView);
            reservationStatus = itemView.findViewById(R.id.ReservationStatus);
            reservationAddress = itemView.findViewById(R.id.ReservationAddress);
            reservationLane = itemView.findViewById(R.id.ReservationLane);
            reservationTime = itemView.findViewById(R.id.ReservationTime);
            reservationOther = itemView.findViewById(R.id.ReservationOther);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    ReservationFull getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
