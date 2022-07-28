package com.parkingsolutions.parkifyapp.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.parkingsolutions.parkifyapp.R;
import com.parkingsolutions.parkifyapp.common.ReservationStatus;
import com.parkingsolutions.parkifyapp.data.model.ReservationFull;
import com.parkingsolutions.parkifyapp.data.request.ReservationRequest;

import org.json.JSONException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ReservationFull> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ReservationRequest reservationRequest;
    HomeFragment par;

    // data is passed into the constructor
    RecyclerViewAdapter(Context context, List<ReservationFull> data, HomeFragment par) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        reservationRequest = new ReservationRequest();
        this.par = par;
    }

    // inflates the row layout from xml when needed
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = mInflater.inflate(R.layout.home_recycler_row, parent, false);
                return new ViewHolderRes(view);
            case 1:
                view = mInflater.inflate(R.layout.home_recycler_occupied_row, parent, false);
                return new ViewHolderOcc(view);
            default :
                view = mInflater.inflate(R.layout.home_recycler_row, parent, false);
                break;
        }
        return new ViewHolderRes(view);
    }

    // binds the data to the TextView in each row
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ReservationFull reservation = mData.get(position);
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolderRes viewHolder = (ViewHolderRes)holder;
                viewHolder.reservationStatus.setText(reservation.getReservation().getReservationStatus().toString());
                viewHolder.reservationAddress.setText(getAddress(reservation));
                viewHolder.reservationLane.setText(reservation.getReservation().getLaneName());
                viewHolder.reservationTime.setText(getFormattedDate(reservation.getReservation().getReservationEnd()));
                viewHolder.reservationOther.setText(" ");
                viewHolder.setReservationId(reservation.getReservation().getId());
                break;
            case 1:
                ViewHolderOcc viewHolder_1 = (ViewHolderOcc) holder;
                viewHolder_1.reservationStatus.setText(reservation.getReservation().getReservationStatus().toString());
                viewHolder_1.reservationAddress.setText(getAddress(reservation));
                viewHolder_1.reservationLane.setText(reservation.getReservation().getLaneName());
                viewHolder_1.reservationTime.setText(getFormattedDate(reservation.getReservation().getReservationEnd()));
                viewHolder_1.reservationOther.setText(" ");
        }

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

    @Override
    public int getItemViewType(int position) {
        ReservationFull reservation = mData.get(position);

        switch (reservation.getReservation().getReservationStatus()) {
            case RESERVED: return 0;
            case OCCUPIED: return 1;
            case ENDED: return 2;
            default: return 3;
        }
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolderRes extends RecyclerView.ViewHolder implements View.OnClickListener {
        private String reservationId;
        TextView reservationStatus;
        TextView reservationAddress;
        TextView reservationLane;
        TextView reservationTime;
        TextView reservationOther;
        Button occupyButton;
        Button extendButton;
        AlertDialog.Builder builder;


        ViewHolderRes(View itemView) {
            super(itemView);
            reservationStatus = itemView.findViewById(R.id.ReservationStatus);
            reservationAddress = itemView.findViewById(R.id.ReservationAddress);
            reservationLane = itemView.findViewById(R.id.ReservationLane);
            reservationTime = itemView.findViewById(R.id.ReservationTime);
            reservationOther = itemView.findViewById(R.id.ReservationOther);
            itemView.setOnClickListener(this);
            occupyButton = itemView.findViewById(R.id.Occupy);
            builder = new AlertDialog.Builder(mInflater.getContext());
            occupyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder.setMessage(R.string.occupy_reservation_msg)
                            .setCancelable(false)
                            .setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean result = reservationRequest.changeReservationStatus(reservationId, ReservationStatus.OCCUPIED);
                                    if (!result) {
                                        Toast.makeText(mInflater.getContext(), R.string.cannot_change_reservation_status, Toast.LENGTH_SHORT).show();
                                    }
                                    par.refreshRecycler();
                                    dialogInterface.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            extendButton = itemView.findViewById(R.id.Extend);
            builder = new AlertDialog.Builder(mInflater.getContext());
            extendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder.setMessage(R.string.extend_reservation_msg)
                            .setCancelable(false)
                            .setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean result = reservationRequest.extendReservationBy15Minutes(reservationId);
                                    if (!result) {
                                        Toast.makeText(mInflater.getContext(), R.string.cannot_extend_reservation, Toast.LENGTH_SHORT).show();

                                    }
                                    par.refreshRecycler();
                                    dialogInterface.cancel();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();


                }
            });



        }

        public void setReservationId(String id) {
            this.reservationId = id;
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }



    }

    public class ViewHolderOcc extends RecyclerView.ViewHolder implements View.OnClickListener {
        private int reservationId;
        TextView reservationStatus;
        TextView reservationAddress;
        TextView reservationLane;
        TextView reservationTime;
        TextView reservationOther;

        ViewHolderOcc(View itemView) {
            super(itemView);
            reservationStatus = itemView.findViewById(R.id.ReservationStatusOccupied);
            reservationAddress = itemView.findViewById(R.id.ReservationAddressOccupied);
            reservationLane = itemView.findViewById(R.id.ReservationLaneOccupied);
            reservationTime = itemView.findViewById(R.id.ReservationTimeOccupied);
            reservationOther = itemView.findViewById(R.id.ReservationOtherOccupied);
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
