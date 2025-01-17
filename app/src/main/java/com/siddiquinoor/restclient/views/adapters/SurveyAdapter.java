package com.siddiquinoor.restclient.views.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.siddiquinoor.restclient.R;
import com.siddiquinoor.restclient.data_model.DTSurveyTableDataModel;

import java.util.ArrayList;

/**
 * Created by Fahim Ahmed on 11-11-16.
 */
public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.SurveyItemViewHolder> {

    private static final int MINIMUM_PHOTO_LENGTH = 5;
    private ArrayList<DTSurveyTableDataModel> dtSurveyTableDataModels;

    private Context mContext;

    public SurveyAdapter(ArrayList<DTSurveyTableDataModel> dtSurveyTableDataModels, Context context) {
        this.dtSurveyTableDataModels = dtSurveyTableDataModels;
        this.mContext = context;
    }

    @Override
    public SurveyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.item_survey_row, parent, false);
        return new SurveyItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SurveyItemViewHolder holder, final int position) {
        final DTSurveyTableDataModel dtSurveyTableDataModel = dtSurveyTableDataModels.get(position);
        holder.tvQuestion.setText(dtSurveyTableDataModel.getDtqText());

        holder.imgVImage.setVisibility(View.GONE);

        /**
         * if the  answer type is radio button IT will show total value
         */
        if (dtSurveyTableDataModel.getDtaValue().equalsIgnoreCase("Y")) {
            holder.tvAnswer.setText("Yes");
        } else if (dtSurveyTableDataModel.getDtaValue().equalsIgnoreCase("N")) {
            holder.tvAnswer.setText("No");
        } else if (dtSurveyTableDataModel.getDtALabel() == null) {    //if the answer control is not Radio button or check Box  then set the DtaValue
            holder.tvAnswer.setText(dtSurveyTableDataModel.getDtaValue());
        } else {
            if (dtSurveyTableDataModel.getDtPhoto().length() < MINIMUM_PHOTO_LENGTH)
                holder.tvAnswer.setText(dtSurveyTableDataModel.getDtALabel());
            else {

                holder.tvAnswer.setVisibility(View.GONE);
                holder.imgVImage.setVisibility(View.VISIBLE);


                byte[] data = Base64.decode(dtSurveyTableDataModel.getDtPhoto(), Base64.DEFAULT);//encodeToString(byteArray, Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                holder.imgVImage.setImageBitmap(bmp);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dtSurveyTableDataModels.size();
    }

    public static class SurveyItemViewHolder extends RecyclerView.ViewHolder {

        protected TextView tvQuestion, tvAnswer;
        protected ImageView imgVImage;
        protected LinearLayout llContainer;

        public SurveyItemViewHolder(View itemView) {
            super(itemView);
            tvQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
            tvAnswer = (TextView) itemView.findViewById(R.id.tvAnswer);
            imgVImage = (ImageView) itemView.findViewById(R.id.imageView_servyImage);
            llContainer = (LinearLayout) itemView.findViewById(R.id.llContainer);
        }
    }
}
