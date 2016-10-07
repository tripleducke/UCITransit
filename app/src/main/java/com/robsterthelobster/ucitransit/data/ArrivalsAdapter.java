package com.robsterthelobster.ucitransit.data;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.robsterthelobster.ucitransit.R;
import com.robsterthelobster.ucitransit.data.models.Arrivals;
import com.robsterthelobster.ucitransit.data.models.Prediction;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by robin on 9/20/2016.
 */

public class ArrivalsAdapter
        extends RealmBasedRecyclerViewAdapter<Arrivals, ArrivalsAdapter.ViewHolder> {

    Realm realm;

    public ArrivalsAdapter(Context context, RealmResults<Arrivals> realmResults, boolean automaticUpdate, boolean animateResults, Realm realm) {
        super(context, realmResults, automaticUpdate, animateResults);
        this.realm = realm;
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate((R.layout.prediction_card), viewGroup, false);
        return new ViewHolder((LinearLayout) view);
    }

    @Override
    public void onBindRealmViewHolder(ArrivalsAdapter.ViewHolder viewHolder, int position) {
        final Arrivals arrivals = realmResults.get(position);
        RealmList<Prediction> predictionRealmList = arrivals.getPredictions();
        String minutes = "NA";
        String secondaryMinutes = "NA";
        int size = predictionRealmList.size();
        if (size > 0) {
            minutes = predictionRealmList.get(0).getMinutes() + " min";
            if (size > 1) {
                secondaryMinutes = predictionRealmList.get(1).getMinutes() + " min";
            }
        }
        viewHolder.cardView.setBackgroundColor(Color.parseColor(arrivals.getColor()));
        viewHolder.arrivalText.setText(minutes);
        viewHolder.routeText.setText(arrivals.getRouteName());
        viewHolder.stopText.setText(String.valueOf(arrivals.getStopName()));
        viewHolder.secondaryArrivalText.setText(secondaryMinutes);
        viewHolder.favoriteCheck.setOnCheckedChangeListener(null);
        viewHolder.favoriteCheck.setChecked(arrivals.isFavorite());
        viewHolder.favoriteCheck.setOnCheckedChangeListener(
                (checkBox, checked) -> {
                    checkBox.setChecked(checked);
                    realm.executeTransaction(r -> {
                        arrivals.setFavorite(checked);
                        r.copyToRealmOrUpdate(arrivals);
                    });
                });
    }

    public class ViewHolder extends RealmViewHolder {

        LinearLayout container;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.prediction_route_name)
        TextView routeText;
        @BindView(R.id.prediction_stop_name)
        TextView stopText;
        @BindView(R.id.prediction_favorite_button)
        CheckBox favoriteCheck;
        @BindView(R.id.prediction_arrival_time)
        TextView arrivalText;
        @BindView(R.id.prediction_arrival_time_alt)
        TextView secondaryArrivalText;

        private int originalHeight = 0;
        private int expandingHeight = 0;
        private boolean isViewExpanded = false;

        ViewHolder(LinearLayout container) {
            super(container);
            this.container = container;
            ButterKnife.bind(this, container);

            cardView.setOnClickListener(view -> {
                if (originalHeight == 0) {
                    originalHeight = view.getHeight();
                    expandingHeight = (int)(originalHeight * .5);
                }

                ValueAnimator valueAnimator;
                if (!isViewExpanded) {
                    secondaryArrivalText.setVisibility(View.VISIBLE);
                    secondaryArrivalText.setEnabled(true);
                    isViewExpanded = true;
                    valueAnimator = ValueAnimator.ofInt(originalHeight,
                            originalHeight + expandingHeight);
                } else {
                    isViewExpanded = false;
                    valueAnimator = ValueAnimator.ofInt(originalHeight + expandingHeight,
                            originalHeight);

                    Animation a = new AlphaAnimation(1.00f, 0.00f); // Fade out
                    a.setDuration(100);
                    a.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {}

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            secondaryArrivalText.setVisibility(View.GONE);
                            secondaryArrivalText.setEnabled(false);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                    secondaryArrivalText.startAnimation(a);
                }
                valueAnimator.setDuration(100);
                valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                valueAnimator.addUpdateListener(animation -> {
                    view.getLayoutParams().height = (Integer) animation.getAnimatedValue();
                    view.requestLayout();
                });
                valueAnimator.start();
            });

            if (!isViewExpanded) {
                secondaryArrivalText.setVisibility(View.GONE);
                secondaryArrivalText.setEnabled(false);
            }
        }
    }
}
