package com.example.vitinhtt2.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.img_happy) ImageView _img_happy;
    @BindView(R.id.img_normal) ImageView _img_normal;
    @BindView(R.id.img_unhappy) ImageView _img_unhappy;
    @BindView(R.id.img_staff) ImageView _img_staff;

    @BindView(R.id.tv_name_emp) TextView _tv_name_emp;

    //private DatabaseReference mDatabase;
    private int normal_num = 0 ;
    private int happy_num = 0 ;
    private int unhappy_num = 0;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //TextView _tv_name_emp = (TextView)findViewById(R.id.tv_name_emp);

        Toolbar toolbar = findViewById(R.id.toolbar);

        final DatabaseReference ref1 = database.getReference("Users").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        ref1.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                _tv_name_emp.setText("Employer: " + name);
                //Toast.makeText(MainActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Happy set
        _img_happy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Thank you!", Toast.LENGTH_SHORT).show();
                final DatabaseReference ref = database.getReference("Users").
                        child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("happy");

                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            happy_num = Integer.parseInt(dataSnapshot.getValue().toString());
                            happy_num++;
                            ref.setValue(happy_num);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
        //Normal set
        _img_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Thank you!", Toast.LENGTH_SHORT).show();
                final DatabaseReference ref = database.getReference("Users").
                        child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("normal");
                // Attach a listener to read the data at our reference
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            normal_num = Integer.parseInt(dataSnapshot.getValue().toString());
                            normal_num++;
                            ref.setValue(normal_num);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        //Unhappy set
        _img_unhappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Thank you!", Toast.LENGTH_SHORT).show();
                final DatabaseReference ref = database.getReference("Users").
                        child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("unhappy");
                // Attach a listener to read the data at our reference
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            unhappy_num = Integer.parseInt(dataSnapshot.getValue().toString());
                            unhappy_num++;
                            ref.setValue(unhappy_num);
                            //Toast.makeText(MainActivity.this, unhappy_num + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference ref1 = database.getReference("Users").
                child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref1.child("image").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String imageLink = dataSnapshot.getValue().toString();
                //Toast.makeText(MainActivity.this, imageLink, Toast.LENGTH_SHORT).show();
                Picasso.get().load(imageLink).into(_img_staff);
                }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
        return true;
    }


}
