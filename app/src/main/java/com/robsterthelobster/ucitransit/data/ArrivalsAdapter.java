package com.robsterthelobster.ucitransit.data;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ftinc.scoop.Scoop;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.robsterthelobster.ucitransit.R;
import com.robsterthelobster.ucitransit.data.models.Arrival;
import com.robsterthelobster.ucitransit.data.models.Arrivals;
import com.robsterthelobster.ucitransit.data.models.Route;
import com.robsterthelobster.ucitransit.data.models.Stop;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * Created by robin on 9/20/2016.
 */

public class ArrivalsAdapter
        extends RealmRecyclerViewAdapter<Arrival, ArrivalsAdapter.ViewHolder> {

    private boolean routeColorOn = false;

    public ArrivalsAdapter(@Nullable OrderedRealmCollection<Arrival> data, boolean autoUpdate, boolean updateOnModification) {
        super(data, autoUpdate, updateOnModification);
        if(Scoop.getInstance().getCurrentFlavor().getName().contains("Route")){
            routeColorOn = true;
        }
    }

    @Override
    public ArrivalsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.prediction_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ArrivalsAdapter.ViewHolder holder, int position) {
        final Arrival arrival = getItem(position);
        if(arrival!=null){
            holder.arrival = arrival;

            Route route = arrival.getRoute();
            System.out.println("route "+route.getShortName());
            Stop stop = arrival.getStop();

            if(routeColorOn) {
                holder.cardView.setBackgroundColor(Color.parseColor(route.getColor()));
            }
            holder.routeText.setText(route.getShortName() + route.getLongName());
            holder.arrivalText.setText(arrival.getArrivalAt());
            holder.stopText.setText(stop.getName());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private float EXPAND_CARD_RATIO = 0.33f;

        public Arrival arrival;

        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.prediction_route_name)
        TextView routeText;
        @BindView(R.id.prediction_stop_name)
        TextView stopText;
        @BindView(R.id.prediction_favorite_button)
        AppCompatCheckBox favoriteCheck;
        @BindView(R.id.prediction_arrival_time)
        TextView arrivalText;
        @BindView(R.id.prediction_arrival_time_alt)
        TextView secondaryArrivalText;

        @Nullable
        AdView adView;

        private int originalHeight = 0;
        private int expandingHeight = 0;
        private boolean isViewExpanded = false;

        ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                int[][] states = new int[][]{
                        new int[]{-android.R.attr.state_checked},
                        new int[]{android.R.attr.state_checked}};
                int[] colors = new int[]{
                        view.getContext().getResources().getColor(android.R.color.primary_text_light),
                        view.getContext().getResources().getColor(R.color.colorAccent)
                };
                favoriteCheck.setSupportButtonTintList(new ColorStateList(states, colors));
            }

            cardView.setOnClickListener(this::expandCard);
            if (!isViewExpanded) {
                secondaryArrivalText.setVisibility(View.GONE);
                secondaryArrivalText.setEnabled(false);
            }
        }

        void expandCard(View view){
            if (originalHeight == 0) {
                originalHeight = view.getHeight();
                expandingHeight = (int)(originalHeight * EXPAND_CARD_RATIO);
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
        }
    }
}
