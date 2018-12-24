package dev.ibrahhout.laundaryapp.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.ibrahhout.laundaryapp.Models.FeedBackModel;
import dev.ibrahhout.laundaryapp.R;
import dev.ibrahhout.laundaryapp.Utils.Constants;
import dev.ibrahhout.laundaryapp.Utils.UtilsClass;

public class FeedBackFragment extends Fragment {


    @BindView(R.id.feedbackEditText)
    EditText feedbackEditText;
    @BindView(R.id.sendFeedbackButton)
    Button sendFeedbackButton;
    Unbinder unbinder;


    public FeedBackFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_feed_back, container, false);
        unbinder = ButterKnife.bind(this, view);

        sendFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UtilsClass.getInstance().hideKeyboard(getContext());
                final ProgressDialog dialog = new ProgressDialog(getContext());
                dialog.setTitle("Loading");
                dialog.setMessage("Sending your feedback, please wait.");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                if (feedbackEditText.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Please make sure you wrote a feedback", Snackbar.LENGTH_LONG).show();
                    dialog.hide();
                } else if (feedbackEditText.getText().toString().length() < 3) {
                    Snackbar.make(view, "Feedback should be longer, please try again.", Snackbar.LENGTH_LONG).show();
                    dialog.hide();
                } else {

                    FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE)
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(Constants.FIREBASE_USERS_USERNAME)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    FeedBackModel model = new FeedBackModel();
                                    model.setName(dataSnapshot.getValue().toString());
                                    model.setFeedBack(feedbackEditText.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child(Constants.FEEDBACK_NODE)
                                            .push().setValue(model)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(getContext(), "Thank you, feedback was sent.", Toast.LENGTH_SHORT).show();
                                                    feedbackEditText.setText("");
                                                    dialog.hide();
                                                }
                                            });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

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
}
