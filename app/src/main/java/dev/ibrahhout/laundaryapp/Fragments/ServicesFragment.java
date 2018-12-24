package dev.ibrahhout.laundaryapp.Fragments;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.ibrahhout.laundaryapp.MainActivity;
import dev.ibrahhout.laundaryapp.Models.ServiceType;
import dev.ibrahhout.laundaryapp.R;
import dev.ibrahhout.laundaryapp.Utils.Constants;
import dev.ibrahhout.laundaryapp.Utils.UtilsClass;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.Submit_Service)
    Button SubmitService;
    @BindView(R.id.normalClothesCheckBox)
    CheckBox normalClothesCheckBox;
    @BindView(R.id.normalClothesSpinner)
    Spinner normalClothesSpinner;
    @BindView(R.id.normalClothesCountEditText)
    EditText normalClothesCountEditText;
    @BindView(R.id.normalClothesLayout)
    LinearLayout normalClothesLayout;
    @BindView(R.id.duvetsCheckBox)
    CheckBox duvetsCheckBox;
    @BindView(R.id.duvetsCountEditText)
    EditText duvetsEditText;
    @BindView(R.id.duvetsLayout)
    LinearLayout duvetsLayout;
    @BindView(R.id.carpetsCheckBox)
    CheckBox carpetsCheckBox;
    @BindView(R.id.carpetsEditText)
    EditText carpetsEditText;
    @BindView(R.id.carpetsLayout)
    LinearLayout carpetsLayout;
    @BindView(R.id.curtainsCheckBox)
    CheckBox curtainsCheckBox;
    @BindView(R.id.curtainsSpinner)
    Spinner curtainsSpinner;
    @BindView(R.id.curtainsCountEditText)
    EditText curtainsCountEditText;
    @BindView(R.id.curtainsLayout)
    LinearLayout curtainsLayout;
    @BindView(R.id.ScrollView)
    android.widget.ScrollView ScrollView;
    @BindView(R.id.fragment_services)
    FrameLayout fragmentServices;


    boolean normalClothesIncluded = false;
    boolean duvetsIncluded = false;
    boolean carpetsIncluded = false;
    boolean curtainsIncluded = false;


    ArrayList<ServiceType> serviceTypes;

    public ServicesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        unbinder = ButterKnife.bind(this, view);

        serviceTypes = new ArrayList<>();

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.orderType, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        curtainsSpinner.setAdapter(spinnerAdapter);
        normalClothesSpinner.setAdapter(spinnerAdapter);

        SubmitService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                serviceTypes.clear();

                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

                if (normalClothesIncluded || carpetsIncluded || curtainsIncluded || duvetsIncluded) {


                    if (normalClothesIncluded) {
                        if (normalClothesCountEditText.getText().toString().isEmpty()) {

                            Snackbar.make(view, "You Have to Enter the number of items", Snackbar.LENGTH_LONG).show();

                            return;
                        }
                    }
                    if (duvetsIncluded) {
                        if (duvetsEditText.getText().toString().isEmpty()) {

                            Snackbar.make(view, "You Have to Enter the number of items", Snackbar.LENGTH_LONG).show();

                            return;
                        }
                    }
                    if (carpetsIncluded) {
                        if (carpetsEditText.getText().toString().isEmpty()) {

                            Snackbar.make(view, "You Have to Enter the number of items", Snackbar.LENGTH_LONG).show();

                            return;
                        }
                    }
                    if (curtainsIncluded) {
                        if (curtainsCountEditText.getText().toString().isEmpty()) {

                            Snackbar.make(view, "You Have to Enter the number of items", Snackbar.LENGTH_LONG).show();

                            return;
                        }
                    }

                    if (normalClothesCheckBox.isChecked()) {

                        serviceTypes.add(new ServiceType(Constants.SERVICE_TYPE_NORMAL_CLOTHES, normalClothesSpinner.getSelectedItemPosition(), Integer.parseInt(normalClothesCountEditText.getText().toString())));

                    }
                    if (duvetsCheckBox.isChecked()) {

                        serviceTypes.add(new ServiceType(Constants.SERVICE_TYPE_DUVETS, Constants.SERVICE_PROCEDURE_NONE, Integer.parseInt(duvetsEditText.getText().toString())));

                    }
                    if (carpetsCheckBox.isChecked()) {

                        serviceTypes.add(new ServiceType(Constants.SERVICE_TYPE_CARPETS, Constants.SERVICE_PROCEDURE_NONE, Integer.parseInt(carpetsEditText.getText().toString())));

                    }

                    if (curtainsCheckBox.isChecked()) {

                        serviceTypes.add(new ServiceType(Constants.SERVICE_TYPE_CURTAINS, curtainsSpinner.getSelectedItemPosition(), Integer.parseInt(curtainsCountEditText.getText().toString())));

                    }


                    ShowPopupWindow(serviceTypes);

                } else {

                    Snackbar.make(view, "Please Make sure You have Chicked At least on Serivce", Snackbar.LENGTH_LONG).show();

                }
            }
        });

        normalClothesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                normalClothesIncluded = isChecked;
                if (isChecked) {
                    normalClothesLayout.setVisibility(View.VISIBLE);

                } else {
                    normalClothesLayout.setVisibility(View.GONE);
                }
            }
        });
        duvetsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                duvetsIncluded = isChecked;
                if (isChecked) {
                    duvetsLayout.setVisibility(View.VISIBLE);
                } else {
                    duvetsLayout.setVisibility(View.GONE);
                }
            }
        });
        carpetsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                carpetsIncluded = isChecked;
                if (isChecked) {
                    carpetsLayout.setVisibility(View.VISIBLE);
                } else {
                    carpetsLayout.setVisibility(View.GONE);
                }
            }
        });
        curtainsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                curtainsIncluded = isChecked;
                if (isChecked) {
                    curtainsLayout.setVisibility(View.VISIBLE);
                } else {
                    curtainsLayout.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onResume() {
        super.onResume();
        serviceTypes.clear();

    }

    public void ShowPopupWindow(final ArrayList<ServiceType> serviceTypes) {


        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.popup_confirm_order_summary, null);

        final PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        popupWindow.setOutsideTouchable(true);//لما تضغط على زر الرجوع يخرج من الواجهه
        popupWindow.setFocusable(true);////لما تضغط على زر الرجوع يخرج من الواجهه
        popupWindow.setWindowLayoutMode(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);


        LinearLayout normalClothesSummaryLayout = popupView.findViewById(R.id.normalClothesSummaryLayout);
        normalClothesSummaryLayout.setVisibility(View.GONE);
        LinearLayout duvetsSummaryLayout = popupView.findViewById(R.id.duvetsSummaryLayout);
        duvetsSummaryLayout.setVisibility(View.GONE);
        TextView normalClothesCountSummary = popupView.findViewById(R.id.normalClothesCountSummary);
        TextView normalClothesProcessSummary = popupView.findViewById(R.id.normalClothesProcessSummary);
        TextView duvetsCountSummary = popupView.findViewById(R.id.duvetsCountSummary);
        TextView carpetsCountSummary = popupView.findViewById(R.id.carpetsCountSummary);
        TextView curtainsCountSummary = popupView.findViewById(R.id.curtainsCountSummary);
        TextView curtainsProcessSummary = popupView.findViewById(R.id.curtainsProcessSummary);
        TextView totalTextView = popupView.findViewById(R.id.totalTextView);


        LinearLayout curtainsSummaryLayout = popupView.findViewById(R.id.curtainsSummaryLayout);
        curtainsSummaryLayout.setVisibility(View.GONE);
        LinearLayout carpetsSummaryLayout = popupView.findViewById(R.id.carpetsSummaryLayout);
        carpetsSummaryLayout.setVisibility(View.GONE);
        ImageButton close = popupView.findViewById(R.id.close);
        Button confirmSending = popupView.findViewById(R.id.confirmSending);

        Log.d(TAG, "ShowPopupWindow: size" + serviceTypes.size());

        int total = 0;

        for (int i = 0; i < serviceTypes.size(); i++) {


            Log.d(TAG, "ShowPopupWindow: " + serviceTypes.get(i).toString() + "\n");

            int serviceType = serviceTypes.get(i).getServiceType();
            switch (serviceType) {
                case Constants.SERVICE_TYPE_NORMAL_CLOTHES:


                    normalClothesSummaryLayout.setVisibility(View.VISIBLE);
                    normalClothesCountSummary.setText(serviceTypes.get(i).getCount() + "");
                    total = total + (Constants.NORMAL_CLOTHES_PRICE * serviceTypes.get(i).getCount());

                    String process = "";
                    if (serviceTypes.get(i).getProcedureType() == Constants.SERVICE_PROCEDURE_TYPE_CLEANING) {
                        process = "Cleaning Only";
                    } else if (serviceTypes.get(i).getProcedureType() == Constants.SERVICE_PROCEDURE_TYPE_CLEANING_AND_IRONING) {
                        process = "Cleaning and Ironing";
                        total = total * 2;

                    }
                    normalClothesProcessSummary.setText(process);
                    break;
                case Constants.SERVICE_TYPE_DUVETS:

                    total = total + (Constants.DUVETS_PRICE * serviceTypes.get(i).getCount());
                    duvetsSummaryLayout.setVisibility(View.VISIBLE);
                    duvetsCountSummary.setText(serviceTypes.get(i).getCount() + "");


                    break;
                case Constants.SERVICE_TYPE_CARPETS:
                    total = total + (Constants.CARPETS_PRICE * serviceTypes.get(i).getCount());

                    carpetsSummaryLayout.setVisibility(View.VISIBLE);
                    carpetsCountSummary.setText(serviceTypes.get(i).getCount() + "");
                    break;
                case Constants.SERVICE_TYPE_CURTAINS:
                    total = total + (Constants.CURTAINS_PRICE * serviceTypes.get(i).getCount());

                    curtainsSummaryLayout.setVisibility(View.VISIBLE);
                    curtainsCountSummary.setText(serviceTypes.get(i).getCount() + "");
                    String process1 = "";
                    if (serviceTypes.get(i).getProcedureType() == Constants.SERVICE_PROCEDURE_TYPE_CLEANING) {
                        process1 = "Cleaning Only";
                    } else if (serviceTypes.get(i).getProcedureType() == Constants.SERVICE_PROCEDURE_TYPE_CLEANING_AND_IRONING) {
                        process1 = "Cleaning and Ironing";
                        total = total * 2;
                    }
                    curtainsProcessSummary.setText(process1);
                    break;

            }


        }
        totalTextView.setText(total + " $");

//        if (normalClothesIncluded) {
//
//
//        } else {
//            normalClothesSummaryLayout.setVisibility(View.GONE);
//        }
//
//        if (duvetsIncluded) {
//            //TODO
//        } else {
//            duvetsSummaryLayout.setVisibility(View.GONE);
//        }
//
//        if (carpetsIncluded) {
//            //TODO
//        } else {
//
//        }
//
//        if (curtainsIncluded) {
//            //TODO
//
//        } else {
//            curtainsSummaryLayout.setVisibility(View.GONE);
//        }


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        final String stringPrice= total+"";
        confirmSending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE)
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String address = dataSnapshot.child(Constants.FIREBASE_USERS_ADDRESS).getValue(String.class);
                                String orderName = dataSnapshot.child(Constants.FIREBASE_USERS_USERNAME).getValue(String.class);
                                long current = System.currentTimeMillis();
                                String orderID = current+"";

                                String orderStatus = "On progress";
                                String orderDueDate = current+259200000+"";
                                HashMap<String,ServiceType> hashMap= new HashMap<>();
                                for (int i = 0; i < serviceTypes.size(); i++) {
                                    hashMap.put(i+"", serviceTypes.get(i));
                                }

                                HashMap<String,Object> uploadDataHashMap = new HashMap<>();

                                uploadDataHashMap.put(Constants.ORDER_NODE_ORDER_ID, orderID);
                                uploadDataHashMap.put(Constants.ORDER_NODE_ORDER_NAME,orderName );
                                uploadDataHashMap.put(Constants.ORDER_NODE_ORDER_STATUS,orderStatus );
                                uploadDataHashMap.put(Constants.ORDER_NODE_ORDER_DATE, orderID);
                                uploadDataHashMap.put(Constants.ORDER_NODE_ORDER_DUE_DATE,orderDueDate);
                                Log.d(TAG, "onDataChange: order date = " +orderID);
                                Log.d(TAG, "onDataChange: order Duedate = " +orderDueDate);
                                uploadDataHashMap.put(Constants.ORDER_NODE_ORDER_TOTAL_PRICE,stringPrice);
                                uploadDataHashMap.put(Constants.ORDER_NODE_ORDER_ADDRESS,address);
                                uploadDataHashMap.put(Constants.ORDER_NODE_ORDER_DETAILS,hashMap);


//                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                FirebaseDatabase.getInstance().getReference().child(Constants.ORDERS_NODE)
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .child(orderID)
                                        .setValue(uploadDataHashMap);


                                ((MainActivity) getActivity()).getViewPagerMainChat().setCurrentItem(1, true);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        }
                );






                Toast.makeText(getContext(), "Order was placed, check history", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();

            }
        });





    }

}
