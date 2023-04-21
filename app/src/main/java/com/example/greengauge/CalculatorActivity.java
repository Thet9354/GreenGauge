package com.example.greengauge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalculatorActivity extends AppCompatActivity {

    private androidx.cardview.widget.CardView cv_publicTransport, cv_carpool, cv_electrical, cv_walk, cv_maintain, cv_unnecessary;
    private LinearLayout ll_recc;
    private LinearLayout ll_publicTransport, ll_publicTransportExplanation, ll_carpool, ll_carpool_explanation, ll_electrical, ll_electrical_explanation, ll_walk, ll_walk_explanation, ll_maintain, ll_maintain_explanation, ll_unnecessary, ll_unnecessary_explanation;
    private ImageView btn_publicTransport_arrowDown, btn_carpool_arrowDown, btn_electrical_arrowDOwn, btn_walk_arrow, btn_maintain_down, btn_unnecessary_down;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = this.getWindow();

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        setContentView(R.layout.activity_calculator);

        initWidget();

        pageDirectories();

        FloatingActionButton prevBtn = findViewById(R.id.prev_btn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final Button saveBtn = findViewById(R.id.save_btn);

        final int[] selRadioId = {((RadioGroup)findViewById(R.id.radio_group)).getCheckedRadioButtonId()};
        final double[] miles = {-1};
        final double[] mpg = {-1};
        final double[] lbCO2 = {-1};
        final double[] monthsCO2 = {-1};

        Button calcBtn = findViewById(R.id.calc_btn);
        calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miles[0] = -1;
                mpg[0] = -1;

                try {
                    miles[0] = Double.parseDouble(((EditText)findViewById(R.id.miles_in)).getText().toString());} catch (NumberFormatException e) {
                    miles[0] = -1;}
                try {
                    mpg[0] = Double.parseDouble(((EditText)findViewById(R.id.mpg_in)).getText().toString());} catch (NumberFormatException e) {
                    mpg[0] = -1;}
                selRadioId[0] = ((RadioGroup)findViewById(R.id.radio_group)).getCheckedRadioButtonId();
                lbCO2[0] = (int)(1000 * CalculatorActivity.this.calcCO2(miles[0], mpg[0], selRadioId[0])) / (double)1000;
                monthsCO2[0] = (int)(1000 * CalculatorActivity.this.calcMonths(lbCO2[0])) / (double)1000;

                TextView calcText = findViewById(R.id.calc_text);
                if (miles[0] != -1 && mpg[0] != -1) {
                    if (saveBtn.getVisibility() == View.GONE) {
                        saveBtn.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.riseout));
                    }
                    saveBtn.setVisibility(View.VISIBLE);
                    calcText.setTextColor(Color.parseColor("#8a000000"));
                    calcText.setText("You have contributed about " + lbCO2[0] + " pounds of CO2 with this entry. It would take a single mature tree about " + monthsCO2[0] + " months to absorb this amount of CO2 from the atmosphere.");
                }
                else {
                    if (saveBtn.getVisibility() == View.VISIBLE) {
                        saveBtn.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sinkin));
                    }
                    saveBtn.setVisibility(View.GONE);
                    calcText.setText("Please input entry values to receive a calculation.");
                    calcText.setTextColor(Color.parseColor("#B00020"));
                }
            }


        });

        final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                miles[0] = -1;
                mpg[0] = -1;

                try {
                    miles[0] = Double.parseDouble(((EditText)findViewById(R.id.miles_in)).getText().toString());} catch (NumberFormatException e) {
                    miles[0] = -1;}
                try {
                    mpg[0] = Double.parseDouble(((EditText)findViewById(R.id.mpg_in)).getText().toString());} catch (NumberFormatException e) {
                    mpg[0] = -1;}
                selRadioId[0] = ((RadioGroup)findViewById(R.id.radio_group)).getCheckedRadioButtonId();
                lbCO2[0] = (int)(1000 * CalculatorActivity.this.calcCO2(miles[0], mpg[0], selRadioId[0])) / (double)1000;
                monthsCO2[0] = (int)(1000 * CalculatorActivity.this.calcMonths(lbCO2[0])) / (double)1000;

                TextView calcText = findViewById(R.id.calc_text);
                if (miles[0] != -1 && mpg[0] != -1) {
                    if (saveBtn.getVisibility() == View.GONE) {
                        saveBtn.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.riseout));
                    }
                    saveBtn.setVisibility(View.VISIBLE);
                    calcText.setTextColor(Color.parseColor("#8a000000"));
                    calcText.setText("You have contributed about " + lbCO2[0] + " pounds of CO2 with this entry. It would take a single mature tree about " + monthsCO2[0] + " months to absorb this amount of CO2 from the atmosphere.");
                    EntryClass obj = new EntryClass();
                    obj.date = formatter.format(Calendar.getInstance().getTime());
                    obj.lbCO2= lbCO2[0];
                    obj.miles= miles[0];
                    obj.mpg = mpg[0];
                    if (selRadioId[0] == (findViewById(R.id.gas_rbtn)).getId()) {
                        obj.radio = "Gasoline";
                    }
                    else if (selRadioId[0] == (findViewById(R.id.diesel_rbtn)).getId()) {
                        obj.radio = "Diesel";
                    }
                    else {
                        obj.radio = "Autogas";
                    }

                    Context context = view.getRootView().getContext();
                    boolean createSuccessful = new TableControllerEntries(context).create(obj);

                    if (createSuccessful)
                        Toast.makeText(context, "Entry saved.", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(context, "Entry error!", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (saveBtn.getVisibility() == View.VISIBLE) {
                        saveBtn.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sinkin));
                    }
                    saveBtn.setVisibility(View.GONE);
                    calcText.setText("Please input entry values to receive a calculation.");
                    calcText.setTextColor(Color.parseColor("#B00020"));
                }
            }
        });

        Button viewDbBtn = findViewById(R.id.view_db_btn);

        viewDbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CalculatorActivity.this, DatabaseActivity.class));
            }
        });
    }

    private void pageDirectories() {

        cv_publicTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                publicTransportExpand();
            }
        });
        
        cv_carpool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                carPoolExpand();
            }
        });
        
        cv_electrical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                electricalExpand();
            }
        });
        
        cv_walk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                walkExpand();
            }
        });

        cv_maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maintainExpand();
            }
        });

        cv_unnecessary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                unnecessaryExpand();
            }
        });
    }

    private void unnecessaryExpand() {

        int v = (ll_unnecessary_explanation.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;
        System.out.println(v);

        TransitionManager.beginDelayedTransition(ll_unnecessary_explanation, new AutoTransition());
        ll_unnecessary_explanation.setVisibility(v);

        if (ll_unnecessary_explanation.getVisibility() == View.VISIBLE)
            btn_unnecessary_down.setImageResource(R.drawable.arrow_up);
        else
            btn_unnecessary_down.setImageResource(R.drawable.arrow_down_ic);
    }

    private void maintainExpand() {

        int v = (ll_maintain_explanation.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(ll_maintain_explanation, new AutoTransition());
        ll_maintain_explanation.setVisibility(v);

        if (ll_unnecessary_explanation.getVisibility() == View.VISIBLE)
            btn_unnecessary_down.setImageResource(R.drawable.arrow_up);
        else
            btn_unnecessary_down.setImageResource(R.drawable.arrow_down_ic);
    }

    private void walkExpand() {

        int v = (ll_walk_explanation.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(ll_walk_explanation, new AutoTransition());
        ll_walk_explanation.setVisibility(v);

        if (ll_walk_explanation.getVisibility() == View.VISIBLE)
            btn_walk_arrow.setImageResource(R.drawable.arrow_up);
        else
            btn_walk_arrow.setImageResource(R.drawable.arrow_down_ic);
    }

    private void electricalExpand() {

        int v = (ll_electrical_explanation.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(ll_electrical_explanation, new AutoTransition());
        ll_electrical_explanation.setVisibility(v);

        if (ll_electrical_explanation.getVisibility() == View.VISIBLE)
            btn_electrical_arrowDOwn.setImageResource(R.drawable.arrow_up);
        else
            btn_unnecessary_down.setImageResource(R.drawable.arrow_down_ic);
    }

    private void carPoolExpand() {

        int v = (ll_carpool_explanation.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(ll_carpool_explanation, new AutoTransition());
        ll_carpool_explanation.setVisibility(v);

        if (ll_carpool_explanation.getVisibility() == View.VISIBLE)
            btn_unnecessary_down.setImageResource(R.drawable.arrow_up);
        else
            btn_unnecessary_down.setImageResource(R.drawable.arrow_down_ic);
    }

    private void publicTransportExpand() {

        int v = (ll_publicTransportExplanation.getVisibility() == View.GONE)? View.VISIBLE: View.GONE;

        TransitionManager.beginDelayedTransition(ll_electrical_explanation, new AutoTransition());
        ll_publicTransportExplanation.setVisibility(v);

        if (ll_publicTransportExplanation.getVisibility() == View.VISIBLE)
            btn_publicTransport_arrowDown.setImageResource(R.drawable.arrow_up);
        else
            btn_publicTransport_arrowDown.setImageResource(R.drawable.arrow_down_ic);
    }

    private void initWidget() {

        //CardView
        cv_publicTransport = findViewById(R.id.cv_publicTransport);
        cv_carpool = findViewById(R.id.cv_carpool);
        cv_electrical = findViewById(R.id.cv_electrical);
        cv_walk = findViewById(R.id.cv_walk);
        cv_maintain = findViewById(R.id.cv_maintain);
        cv_unnecessary = findViewById(R.id.cv_unnecessary);

        //LinearLayout
        ll_publicTransport = findViewById(R.id.ll_publicTransport);
        ll_publicTransportExplanation = findViewById(R.id.ll_publicTransportExplanation);
        ll_carpool = findViewById(R.id.ll_carpool);
        ll_carpool_explanation = findViewById(R.id.ll_carpool_explanation);
        ll_electrical = findViewById(R.id.ll_electrical);
        ll_electrical_explanation = findViewById(R.id.ll_electrical_explanation);
        ll_walk = findViewById(R.id.ll_walk);
        ll_walk_explanation = findViewById(R.id.ll_walk_explanation);
        ll_maintain = findViewById(R.id.ll_maintain);
        ll_maintain_explanation = findViewById(R.id.ll_maintain_explanation);
        ll_unnecessary = findViewById(R.id.ll_unnecessary);
        ll_unnecessary_explanation = findViewById(R.id.ll_unnecessary_explanation);
        ll_recc = findViewById(R.id.ll_recc);

        //ImageView
        btn_publicTransport_arrowDown = findViewById(R.id.btn_publicTransport_arrowDown);
        btn_carpool_arrowDown = findViewById(R.id.btn_carpool_arrowDown);
        btn_electrical_arrowDOwn = findViewById(R.id.btn_electrical_arrowDOwn);
        btn_walk_arrow = findViewById(R.id.btn_walk_arrow);
        btn_maintain_down = findViewById(R.id.btn_maintain_down);
        btn_unnecessary_down = findViewById(R.id.btn_unnecessary_down);

    }

    private double calcCO2(double miles, double mpg, int selRadioId) {
        if (selRadioId == (findViewById(R.id.gas_rbtn)).getId()) {
            return miles/mpg * 18.95;
        }
        else if (selRadioId == (findViewById(R.id.diesel_rbtn)).getId()) {
            return miles/mpg * 22.38;
        }
        else {
            return miles/mpg * 12.72;
        }
    }

    private double calcMonths(double lbCO2) {
        return lbCO2 / 48 * 12;
    }
}
