package dev.ibrahhout.laundaryapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.ibrahhout.laundaryapp.Adapters.ServicesHistoryAdapter;
import dev.ibrahhout.laundaryapp.Models.OrderModel;
import dev.ibrahhout.laundaryapp.R;
import dev.ibrahhout.laundaryapp.Utils.Constants;


public class ServicesHistoryFragment extends Fragment {

    private static final String TAG = "test";
    @BindView(R.id.servicesHistoryRecyclerView)
    RecyclerView servicesHistoryRecyclerView;
    Unbinder unbinder;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public ServicesHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services_history, container, false);
        unbinder = ButterKnife.bind(this, view);

        final ArrayList<OrderModel> orders = new ArrayList<>();
        final ServicesHistoryAdapter adapter = new ServicesHistoryAdapter(getContext(), orders);
        servicesHistoryRecyclerView.setAdapter(adapter);
        servicesHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child(Constants.ORDERS_NODE)
                .child(uID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                OrderModel orderModel = dataSnapshot.getValue(OrderModel.class);
                ArrayList<HashMap<String, Long>> detials = (ArrayList) dataSnapshot.child(Constants.ORDER_NODE_ORDER_DETAILS).getValue();

                orderModel.setOrdDetials(detials);

                Log.d(TAG, "onChildAdded: size =" + detials.size() + " and count is : " + ((HashMap<String, Long>) detials.get(0)).get("count"));

//                orderModel.setOrdDetials(detials);
                orders.add(orderModel);


                if (progressBar!=null){

                    progressBar.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                OrderModel orderModel = dataSnapshot.getValue(OrderModel.class);
                ArrayList<HashMap<String, Long>> detials = (ArrayList) dataSnapshot.child(Constants.ORDER_NODE_ORDER_DETAILS).getValue();

                orderModel.setOrdDetials(detials);

                Log.d(TAG, "onChildAdded: size =" + detials.size() + " and count is : " + ((HashMap<String, Long>) detials.get(0)).get("count"));
                int pos = 0;
                for (int i = 0; i < orders.size(); i++) {
                    if (orders.get(i).getOrderID().equals(orderModel.getOrderID())) {
                        orders.remove(i);
                        pos = i;
                    }
                }
//                orderModel.setOrdDetials(detials);
                orders.add(pos, orderModel);
                adapter.notifyDataSetChanged();

                if (progressBar!=null){

                    progressBar.setVisibility(View.GONE);
                }
                if (servicesHistoryRecyclerView != null) {

                    servicesHistoryRecyclerView.smoothScrollToPosition((orders.size() - 1) - pos);
                }


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
