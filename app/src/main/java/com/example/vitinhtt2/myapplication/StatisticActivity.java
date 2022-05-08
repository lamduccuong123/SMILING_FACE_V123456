package com.example.vitinhtt2.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference ref;
    User user;
    TableLayout stk;
    @BindView(R.id.btn_clickToast) Button _btn_clickToast;
    @BindView(R.id.btn_backAdmin) Button _btn_backAdmin;
    int fontTbSize =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        ButterKnife.bind(this);
        user = new User();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");
        fontTbSize = (int)getResources().getDimension(R.dimen.fontTableSize_btn);
        final Intent intentAdmin = new Intent(this, AdminActivity.class);

        _btn_backAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentAdmin);
            }
        });
        final GradientDrawable gd = new GradientDrawable();
        gd.setColor(0xFF00FF00); // Changes this drawbale to use a single color instead of a gradient
        gd.setCornerRadius(5);
        gd.setStroke(1, 0xFF000000);
        final GradientDrawable gdtb = new GradientDrawable();
        //gd.setColor(0xFF00FF00); // Changes this drawbale to use a single color instead of a gradient
        gdtb.setCornerRadius(5);
        gdtb.setStroke(1, 0xFF000000);

        _btn_clickToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stk = (TableLayout) findViewById(R.id.displayLinear);
                TableRow tbrow0 = new TableRow(StatisticActivity.this);

                TextView tv0 = new TextView(StatisticActivity.this);
                tv0.setText(" No. ");
                tv0.setTextSize(fontTbSize);
                tv0.setTextColor(Color.WHITE);
                tv0.setTypeface(tv0.getTypeface(), Typeface.BOLD);
                tv0.setBackground(gd);
                tbrow0.addView(tv0);

                TextView tv1 = new TextView(StatisticActivity.this);
                tv1.setText(" Name         ");
                tv1.setTextColor(Color.WHITE);
                tv1.setTextSize(fontTbSize);
                tv1.setTypeface(tv0.getTypeface(), Typeface.BOLD);
                tv1.setBackground(gd);
                tv1.setGravity(Gravity.CENTER);
                tbrow0.addView(tv1);

                TextView tv2 = new TextView(StatisticActivity.this);
                tv2.setText(" Happy ");
                tv2.setTextColor(Color.WHITE);
                tv2.setTextSize(fontTbSize);
                tv2.setTypeface(tv0.getTypeface(), Typeface.BOLD);
                tv2.setBackground(gd);
                tbrow0.addView(tv2);

                TextView tv3 = new TextView(StatisticActivity.this);
                tv3.setText(" Normal ");
                tv3.setTextColor(Color.WHITE);
                tv3.setTextSize(fontTbSize);
                tv3.setTypeface(tv0.getTypeface(), Typeface.BOLD);
                tv3.setBackground(gd);
                tbrow0.addView(tv3);

                TextView tv4 = new TextView(StatisticActivity.this);
                tv4.setText(" Unhappy ");
                tv4.setTextColor(Color.WHITE);
                tv4.setTextSize(fontTbSize);
                tv4.setTypeface(tv0.getTypeface(), Typeface.BOLD);
                tv4.setBackground(gd);
                tbrow0.addView(tv4);

                stk.addView(tbrow0);

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Add Table row
                        int i = 1;
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            user = ds.getValue(User.class);

                            TableRow tbrow = new TableRow(StatisticActivity.this);
                            TextView t1v = new TextView(StatisticActivity.this);
                            t1v.setText("" + i);
                            t1v.setTextColor(Color.WHITE);
                            t1v.setTextSize(fontTbSize);
                            t1v.setGravity(Gravity.CENTER);
                            t1v.setBackground(gdtb);
                            tbrow.addView(t1v);

                            TextView t2v = new TextView(StatisticActivity.this);
                            t2v.setText(user.getName());
                            t2v.setTextColor(Color.WHITE);
                            t2v.setTextSize(fontTbSize);
                            t2v.setGravity(Gravity.LEFT);
                            t2v.setBackground(gdtb);
                            tbrow.addView(t2v);

                            TextView t3v = new TextView(StatisticActivity.this);
                            t3v.setText(String.valueOf(user.getHappy()));
                            t3v.setTextColor(Color.WHITE);
                            t3v.setTextSize(fontTbSize);
                            t3v.setGravity(Gravity.CENTER);
                            t3v.setBackground(gdtb);
                            tbrow.addView(t3v);

                            TextView t4v = new TextView(StatisticActivity.this);
                            t4v.setText(String.valueOf(user.getNormal()));
                            t4v.setTextColor(Color.WHITE);
                            t4v.setTextSize(fontTbSize);
                            t4v.setGravity(Gravity.CENTER);
                            t4v.setBackground(gdtb);
                            tbrow.addView(t4v);

                            TextView t5v = new TextView(StatisticActivity.this);
                            t5v.setText(String.valueOf(user.getUnhappy()));
                            t5v.setTextColor(Color.WHITE);
                            t5v.setTextSize(fontTbSize);
                            t5v.setGravity(Gravity.CENTER);
                            t5v.setBackground(gdtb);
                            tbrow.addView(t5v);

                            stk.addView(tbrow);
                            i++;
                        }
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }


}
