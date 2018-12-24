package dev.ibrahhout.laundaryapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dev.ibrahhout.laundaryapp.Models.Step;
import dev.ibrahhout.laundaryapp.R;


public class HowItWorksAdapter extends BaseAdapter {

    Context context;
    ArrayList<Step> steps;

    public HowItWorksAdapter(Context context, ArrayList<Step> steps) {
        this.context = context;
        this.steps = steps;
    }

    @Override
    public int getCount() {
        return steps.size();
    }

    @Override
    public Object getItem(int position) {
        return steps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell_layout_grid_how_it_works, parent, false);

        ImageView imageView = view.findViewById(R.id.stepImageView);
        TextView textView = view.findViewById(R.id.stepTextView);
        LinearLayout layoutCell = view.findViewById(R.id.layoutCell);

        imageView.setImageResource(steps.get(position).getStepImage());
        textView.setText(steps.get(position).getStepText());

        switch (steps.get(position).getStepNum()) {
            case 1:
                layoutCell.setBackgroundColor(Color.rgb(219, 232, 255));
                break;
            case 2:
                layoutCell.setBackgroundColor(Color.rgb(219, 255, 255));

                break;
            case 3:
                layoutCell.setBackgroundColor(Color.rgb(253, 255, 219));

                break;
            case 4:
                layoutCell.setBackgroundColor(Color.rgb(255, 216, 221));

                break;


        }

        return view;
    }
}
