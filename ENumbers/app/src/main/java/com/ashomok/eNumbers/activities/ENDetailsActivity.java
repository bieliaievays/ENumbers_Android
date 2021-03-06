package com.ashomok.eNumbers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashomok.eNumbers.activities.rate_app.RateAppAsker;
import com.ashomok.eNumbers.ad.AdContainer;
import com.ashomok.eNumbers.ad.AdMobContainerImpl;
import com.ashomok.eNumbers.data_load.EN;
import com.ashomok.eNumbers.R;

/**
 * Created by Iuliia on 14.09.2015.
 */
public class ENDetailsActivity extends AppCompatActivity {

    private EN en;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.details_layout);

            ViewGroup parent = findViewById(R.id.details_layout_parent);
            AdContainer adContainer = new AdMobContainerImpl(this);
            adContainer.initAd(parent);

            RateAppAsker.init(this);

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                en = (EN)getIntent().getSerializableExtra(EN.TAG);

            }

            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

            TextView mTextView_ecode = findViewById(R.id.eCodeFull);
            mTextView_ecode.setText(en.getCode());

            String name = en.getName();
            if (name.length() > 0) {
                TextView mTextView_ename = findViewById(R.id.eNameFull);
                mTextView_ename.setText(name);
            } else {
                findViewById(R.id.details_layout_ename_layout).setVisibility(View.GONE);
            }

            String purpose = en.getPurpose();
            if (purpose.length() > 0) {
                TextView mTextView_epurpose = findViewById(R.id.ePurposeFull);
                mTextView_epurpose.setText(purpose);
            } else {
                findViewById(R.id.details_layout_epurpose_layout).setVisibility(View.GONE);
            }

            String status = en.getStatus();
            if (status.length() > 0) {
                TextView mTextView_status = findViewById(R.id.eStatusFull);
                mTextView_status.setText(status);
            } else {
                findViewById(R.id.details_layout_estatus_layout).setVisibility(View.GONE);
            }

            String additional_info = en.getAdditionalInfo();
            if (additional_info.length() > 0) {
                TextView mTextView_additional_info = findViewById(R.id.additional_infoFull);
                mTextView_additional_info.setText(additional_info);
            } else {
                findViewById(R.id.details_layout_eadditional_info_layout).setVisibility(View.GONE);
            }

            String approved_in = en.getApprovedIn();
            if (approved_in.length() > 0) {
                TextView mTextView_approved_in = findViewById(R.id.eapproved_inFull);
                mTextView_approved_in.setText(approved_in);
            } else {
                findViewById(R.id.details_layout_eapproved_in_layout).setVisibility(View.GONE);
            }

            String banned_in = en.getBannedIn();
            if (banned_in.length() > 0) {
                TextView mTextView_banned_in = findViewById(R.id.ebanned_inFull);
                mTextView_banned_in.setText(banned_in);
            } else {
                findViewById(R.id.details_layout_ebanned_in_layout).setVisibility(View.GONE);
            }

            String typical_products = en.getTypicalProducts();
            if (typical_products.length() > 0) {
                TextView mTextView_typical_products = findViewById(R.id.etypical_productsFull);
                mTextView_typical_products.setText(typical_products);
            } else {
                findViewById(R.id.details_layout_etypical_products_layout).setVisibility(View.GONE);
            }

            Boolean is_bad_for_children = !en.getBadForChildren().equals("0");
            if (is_bad_for_children) {
                //visible by default
            } else {
                findViewById(R.id.forbidden_for_children_flag_layout).setVisibility(View.GONE);
            }

            ENumbFlag flag = findViewById(R.id.enumbFlag_details_layout);
            String dangerLevel = en.getDangerLevel();
            TextView mTextView_danger_level = findViewById(R.id.eDangerLevelFull);
            switch (dangerLevel) {
                case "safe":
                    flag.setmIsGreen(true);
                    mTextView_danger_level.setText(R.string.safe);
                    break;
                case "medium":
                    mTextView_danger_level.setText(R.string.use_with_caution);
                    flag.setmIsYellow(true);
                    break;
                case "hight":
                    mTextView_danger_level.setText(R.string.unsafe);
                    flag.setmIsRed(true);
                    break;
                case "unknown":
                    mTextView_danger_level.setText(R.string.unknown);
                    flag.setmIsGrey(true);
                    break;
                default:
                    mTextView_danger_level.setText(R.string.unknown);
                    flag.setmIsGrey(true);
                    break;
            }

        } catch (Exception e) {
            Log.e(this.getClass().getCanonicalName(), e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
