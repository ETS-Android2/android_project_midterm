package vn.edu.usth.coronatracker.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;


public class ClaimsXAxisValueFormatter extends ValueFormatter {
    List<String> datesList;

    public ClaimsXAxisValueFormatter(List<String> arrayOfDates) {
        this.datesList = arrayOfDates;
    }

    @Override
    public String getFormattedValue(float value) {
        return String.valueOf(value);
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        int position = Math.round(value);
        if (position >= 0 && position <= datesList.size() - 1) {
            return datesList.get(position);
        } else {
            return "";
        }

    }
}
