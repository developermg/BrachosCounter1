package com.example.shaina.brachoscounter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Shaina on 5/25/2016.
 */
public class BrachosBreakdownAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int mRowLayoutResourceId, mRowTextViewResourceId;
    private ArrayList<String> mBrachosDescription;
    private ArrayList<Integer> mBrachosAmount;

    /*public BrachosBreakdownAdapter(Context context, ArrayList<String> brachosDescription,
                                   ArrayList<Integer> brachosAmount, int rowResourceId,
                                   int rowTextViewResourceId) { */
    public BrachosBreakdownAdapter(Context context, int rowResourceId, int rowTextViewResourceId) {

        // superclass will handle the String array portion of this, like getCount(), etc.
        super(context, rowResourceId);

        // store a reference to the Context
        mContext = context;

        //store a reference to arraylists
        mBrachosDescription = getBrachosDescriptions();
        mBrachosAmount = getBrachosNumbers();

        // We will need this later to inflate each new row
        mLayoutInflater = LayoutInflater.from(context);

        // We will need this later to pass to the LayoutInflater to inflate this res. as the new row
        mRowLayoutResourceId = rowResourceId;

        // We will need these later to know what are the IDs of the resources inside the Row XML
        mRowTextViewResourceId = rowTextViewResourceId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // set the current row to either the converted View or, if null, get a new View
        View currentRow = convertView != null ? convertView : getNewView(parent);

        // regardless of whether or not we have created a new row or converted one from before
        final ViewHolder currentViewHolder = (ViewHolder) currentRow.getTag();

        // set the text of the current TextView to whatever the text is in the ArrayAdapter
        currentViewHolder.mCurrentTextView.setText(getItem(position));

        // set the current row as the View to return when getView is called
        return currentRow;
    }

    @NonNull
    private View getNewView(ViewGroup parent) {
        // inflate a new instance of the XML layout passed in to the constructor
        View newRow = mLayoutInflater.inflate(mRowLayoutResourceId, parent, false);

        // create a new instance of the custom ViewHolder class above
        ViewHolder newViewHolder = new ViewHolder();

        // set the TextView member field in this ViewHolder object to refer to
        // the newly-inflated current row's TextView
        newViewHolder.mCurrentTextView = (TextView) newRow.findViewById(mRowTextViewResourceId);

        // store a reference to this ViewHolder object in the newly-inflated row's Tag attribute
        // Now, we won't need to do a findViewById when processing a converted object in getView
        // because we just stored a reference to each object in the ViewHolder
        newRow.setTag(newViewHolder);

        // Return the newly-inflated row that also contains a ViewHolder reference in its tag
        return newRow;
    }

    static class ViewHolder {
        TextView mCurrentTextView;
    }

}
