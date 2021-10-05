package vn.edu.usth.coronatracker.utils;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import vn.edu.usth.coronatracker.R;

public class MyMarkerView extends MarkerView {

    private TextView tvContent;
    private Context mContext;

    public MyMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);

        tvContent = (TextView) findViewById(R.id.text_content);
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true));
//        if (e instanceof CandleEntry) {
//
//            CandleEntry ce = (CandleEntry) e;
//            Toast.makeText(mContext, String.valueOf("Values" + ce.getHigh()), Toast.LENGTH_SHORT).show();
//
//            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 7, true));
//        } else {
//            tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 7, true));
//
//        }

        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }
}
