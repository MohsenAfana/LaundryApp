package dev.ibrahhout.laundaryapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev.ibrahhout.laundaryapp.R;
import dev.ibrahhout.laundaryapp.Utils.Constants;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,String user,String adress) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public  void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).keepSynced(true);
        FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
           String user = dataSnapshot.child(Constants.FIREBASE_USERS_USERNAME).getValue(String.class);


               String addess= dataSnapshot.child(Constants.FIREBASE_USERS_ADDRESS).getValue(String.class);
                for (int appWidgetId : appWidgetIds) {


                    updateAppWidget(context, appWidgetManager, appWidgetId, user, addess);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

