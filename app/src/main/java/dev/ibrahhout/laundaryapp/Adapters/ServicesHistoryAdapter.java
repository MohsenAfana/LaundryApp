package dev.ibrahhout.laundaryapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ibrahhout.laundaryapp.Models.OrderModel;
import dev.ibrahhout.laundaryapp.R;
import dev.ibrahhout.laundaryapp.Utils.Constants;

/**
 * This file is created By: ( Ibrahim A. Elhout ) on 07/21/2018 at 5:46 PM
 * Project : LaundaryApp1
 * Contacts:
 * Email: Ibrahimhout.dev@gmail.com
 * Phone: 00972598825662
 **/
public class ServicesHistoryAdapter extends RecyclerView.Adapter<ServicesHistoryAdapter.ServicesViewHolder> {


    private static final String TAG = "test";
    private Context context;
    private ArrayList<OrderModel> orders;

    public ServicesHistoryAdapter(Context context, ArrayList<OrderModel> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ServicesViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_services_history, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder, int position) {
        final OrderModel order = orders.get((orders.size() - 1) - position);


        holder.orderIdTextView.setText(order.getOrderID() + "");

        Log.d(TAG, "onBindViewHolder: new date is : " + order.getOrderDate() + "\n due date is : " + order.getOrderDueDate());


        long date = Long.parseLong(order.getOrderDate());
//        Log.d(TAG, "onBindViewHolder: Date in ms = "+date);
        String dateS = DateFormat.format("dd/MM/yyyy", new Date(date)).toString();
        holder.orderDateTextView.setText(dateS);


        String dueDates = "";
        if (order.getOrderDueDate().equals("0")) {
            dueDates = Constants.STATUS_CANCELED;
        } else if (order.getOrderDueDate().equals("1")) {
            dueDates = Constants.STATUS_COMPLETED;
        } else {

            long dueDate = Long.parseLong(order.getOrderDueDate());
            dueDates = DateFormat.format("dd/MM/yyyy", new Date(dueDate)).toString();

        }
//        Log.d(TAG, "onBindViewHolder: dueDate in ms = "+dueDate);
        holder.orderDueDateTextView.setText(dueDates);


        holder.DuvetsLO.setVisibility(View.GONE);
        holder.carpetsLO.setVisibility(View.GONE);
        holder.curtainsLO.setVisibility(View.GONE);
        holder.normalClothesLO.setVisibility(View.GONE);

        String status = order.getOrderStatus();


        if (status.equals(Constants.STATUS_ON_PROGRESS)) {
            holder.progressStatusIV.setImageResource(R.drawable.ic_progress);
            holder.cancelButton.setVisibility(View.VISIBLE);
        } else if (status.equals(Constants.STATUS_CANCELED)) {
            holder.progressStatusIV.setImageResource(R.drawable.ic_cancel);
            holder.cancelButton.setVisibility(View.INVISIBLE);
            holder.orderDueDateTextView.setText(Constants.STATUS_CANCELED);


        } else if (status.equals(Constants.STATUS_COMPLETED)) {
            holder.progressStatusIV.setImageResource(R.drawable.ic_done);
            holder.cancelButton.setVisibility(View.INVISIBLE);
            holder.orderDueDateTextView.setText(Constants.STATUS_COMPLETED);
        }
        holder.totalTextView.setText(order.getOrderTotalPrice() + "");
        ArrayList<HashMap<String, Long>> servicesDetials = order.getOrdDetials();

//        Log.d(TAG, "onBindViewHolder: "+order.getOrdDetials().size());

//        HashMap<String,ServiceType> services = order.getOrdDetials();
//        Log.d(TAG, "onBindViewHolder: size = "+ services.keySet().size());
//        ArrayList<HashMap<String, ServiceType>> types = order.getOrdDetials();
//
//        for (int i = 0; i < types.size(); i++) {
//            HashMap<String,ServiceType> hashh = types.get(i);
//            String key0  = (String) ((ArrayList) hashh.keySet()).get(0);
//            Log.d(TAG, "onBindViewHolder: key is :"+key0 + "  round "+i )  ;
//
//        }
//

        for (int i = 0; i < servicesDetials.size(); i++) {
            HashMap<String, Long> type = servicesDetials.get(i);

            Long typee = type.get(Constants.SERVICE_TYPE);
            int typeInt = typee.intValue();

            HashMap<String, String> procedures = new HashMap<>();
            procedures.put("0", "Dry Cleaning");
            procedures.put("1", "Dry Cleaning with Ironing");

            switch (typeInt) {
                case Constants.SERVICE_TYPE_NORMAL_CLOTHES:

                    holder.normalClothesLO.setVisibility(View.VISIBLE);

                    holder.normalClothesProcess.setText(procedures.get(type.get(Constants.PROCEDURE_TYPE) + ""));

                    holder.normalClothesCount.setText(type.get(Constants.COUNT) + "");

                    break;
                case Constants.SERVICE_TYPE_DUVETS:
                    holder.DuvetsLO.setVisibility(View.VISIBLE);
                    holder.duvetsCount.setText(type.get(Constants.COUNT) + "");


                    break;
                case Constants.SERVICE_TYPE_CARPETS:
                    holder.carpetsLO.setVisibility(View.VISIBLE);
                    holder.carpetsCount.setText(type.get(Constants.COUNT) + "");


                    break;
                case Constants.SERVICE_TYPE_CURTAINS:
                    holder.curtainsLO.setVisibility(View.VISIBLE);
                    holder.curtainsProcess.setText(procedures.get(type.get(Constants.PROCEDURE_TYPE) + ""));
                    holder.curtiansCount.setText(type.get(Constants.COUNT) + "");

                    break;

                default:
                    break;
            }


        }

        holder.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child(Constants.ORDERS_NODE)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                        child(order.getOrderID()).child(Constants.ORDER_NODE_ORDER_STATUS)
                        .setValue(Constants.STATUS_CANCELED);
                FirebaseDatabase.getInstance().getReference().child(Constants.ORDERS_NODE)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).
                        child(order.getOrderID()).child(Constants.ORDER_NODE_ORDER_DUE_DATE)
                        .setValue("0");
            }
        });

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ServicesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderIdTextView)
        TextView orderIdTextView;
        @BindView(R.id.progressStatusIV)
        ImageView progressStatusIV;
        @BindView(R.id.orderDateTextView)
        TextView orderDateTextView;
        @BindView(R.id.orderDueDateTextView)
        TextView orderDueDateTextView;
        @BindView(R.id.normalClothesProcess)
        TextView normalClothesProcess;
        @BindView(R.id.normalClothesCount)
        TextView normalClothesCount;
        @BindView(R.id.normalClothesLO)
        LinearLayout normalClothesLO;
        @BindView(R.id.duvetsCount)
        TextView duvetsCount;
        @BindView(R.id.DuvetsLO)
        LinearLayout DuvetsLO;
        @BindView(R.id.curtiansCount)
        TextView curtiansCount;
        @BindView(R.id.curtainsLO)
        LinearLayout curtainsLO;
        @BindView(R.id.curtainsProcess)
        TextView curtainsProcess;
        @BindView(R.id.carpetsCount)
        TextView carpetsCount;
        @BindView(R.id.carpetsLO)
        LinearLayout carpetsLO;
        @BindView(R.id.totalTextView)
        TextView totalTextView;
        @BindView(R.id.cancelButton)
        Button cancelButton;

        ServicesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}
