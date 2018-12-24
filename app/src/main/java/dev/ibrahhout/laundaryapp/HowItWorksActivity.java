package dev.ibrahhout.laundaryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ibrahhout.laundaryapp.Adapters.HowItWorksAdapter;
import dev.ibrahhout.laundaryapp.Models.Step;

public class HowItWorksActivity extends AppCompatActivity {

    @BindView(R.id.howItWorksGridView)
    GridView howItWorksGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_it_works);
        ButterKnife.bind(this);


        ArrayList<Step> steps = new ArrayList<>();
        steps.add(new Step("1- Book A Collection Online or with our award winning app we'll bring a bag", R.drawable.ic_shirt, 1));
        steps.add(new Step("2- Let us know where you are, and we will receive your bag and clean it", R.drawable.ic_washing_machine, 2));
        steps.add(new Step("3- We will deliver your pristine garments back to you anytime and anywhere", R.drawable.ic_delivery_truck, 3));
        steps.add(new Step("4- You will pay cash to the delivery man", R.drawable.ic_cash, 4));

        HowItWorksAdapter howItWorksAdapter = new HowItWorksAdapter(this, steps);
        howItWorksGridView.setAdapter(howItWorksAdapter);
    }
}
