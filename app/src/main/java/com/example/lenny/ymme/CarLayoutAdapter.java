package com.example.lenny.ymme;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lenny.ymme.models.Engines;
import com.example.lenny.ymme.models.Makes;
import com.example.lenny.ymme.models.Models;
import com.example.lenny.ymme.models.YMMEElement;
import com.example.lenny.ymme.models.Years;
import com.example.lenny.ymme.util.Config;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;




public class CarLayoutAdapter extends RecyclerView.Adapter<CarLayoutAdapter.ViewHolder> {
    final String YR = Config.Dtypes.Years.toString();
    final String MK = Config.Dtypes.Makes.toString();
    final String MO = Config.Dtypes.Models.toString();
    final String EG = Config.Dtypes.Engines.toString();
    Context context;
    List<String> dataList = new ArrayList<>();
    String dtype;
    String fieldTag;

    public CarLayoutAdapter(Context ctx, List<String> data, String datatype) {
        dataList = data;
        context = ctx;
        dtype = datatype;
    }


    public CarLayoutAdapter(Context ctx, YMMEElement data, String datatype) {
        if (data instanceof Years) {
            dataList = ((Years) data).getData();
            fieldTag = String.format("%s,%s", YR, "*");
        } else if (data instanceof Makes) {
            dataList = ((Makes) data).getData();
            fieldTag = String.format("%s,%s", MK, ((Makes) data).getYear());
        } else if (data instanceof Models) {
            dataList = ((Models) data).getData();
            fieldTag = String.format("%s,%s,%s", MO, ((Models) data).getYear(), ((Models) data).getMake());
        } else {
            dataList = ((Engines) data).getData();
            fieldTag = String.format("%s,%s,%s,%s", EG, ((Engines) data).getYear(), ((Engines) data).getMake(), ((Engines) data).getMake());
        }
        context = ctx;
        dtype = datatype;
    }


    @Override
    public CarLayoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.card_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(CarLayoutAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        String contact = dataList.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.nameTextView;
        textView.setTag((R.string.YMM_ID_KEY), fieldTag);
        textView.setText(contact);
//        Button button = viewHolder.messageButton;
//        button.setText("Message");
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return dataList.size();
    }


    // Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final String YR = Config.Dtypes.Years.toString();
        final String MK = Config.Dtypes.Makes.toString();
        final String MO = Config.Dtypes.Models.toString();
        final String EG = Config.Dtypes.Engines.toString();
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);


            nameTextView = (TextView) itemView.findViewById(R.id.txtdata);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] params = nameTextView.getTag(R.string.YMM_ID_KEY).toString().split(",");
                    EventBus.getDefault().post(new FragmentSwapEvent(params, nameTextView.getText().toString()));
                }
            });

        }
    }
}