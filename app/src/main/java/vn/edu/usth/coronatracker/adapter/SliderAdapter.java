package vn.edu.usth.coronatracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

import vn.edu.usth.coronatracker.R;
import vn.edu.usth.coronatracker.model.SymptomModel;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    private List<SymptomModel>symptomLists;
    private Context mContext;

    public SliderAdapter(Context mContext, List<SymptomModel>symptomLists){
        this.symptomLists = symptomLists;
        this.mContext = mContext;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        SliderAdapter.Holder viewHolder = new SliderAdapter.Holder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position){

        SymptomModel symptomModel = symptomLists.get(position);
        viewHolder.imageView.setImageResource(symptomModel.getImage());
        viewHolder.sliderText.setText(symptomModel .getSymptomText());
    }

    @Override
    public int getCount(){
        return symptomLists.size();
    }



    public class Holder extends SliderViewAdapter.ViewHolder{
        private ImageView imageView;
        private TextView sliderText;
        public Holder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            sliderText = (TextView) itemView.findViewById(R.id.slider_text);
        }
    }
}
