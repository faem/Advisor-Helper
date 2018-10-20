package com.fahimabrar.tracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.karan.churi.PermissionManager.PermissionManager;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawer;
    RecyclerView recyclerView;
    RelativeLayout relativeLayoutNoStudent;
    PermissionManager permissionManager;
    TextView textViewDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        relativeLayoutNoStudent = findViewById(R.id.relativeLayoutNoStudent);
        relativeLayoutNoStudent.setVisibility(View.INVISIBLE);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        TextView textView = (TextView) v.findViewById(R.id.textViewName);
        ImageView imageView = v.findViewById(R.id.imageViewNav);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String image = sharedPref.getString("image","0");
        String name = sharedPref.getString("name","Name");

        Picasso.with(getApplicationContext())
                .load(image)
                .error( R.drawable.error_image)
                .placeholder( R.drawable.progressbar )
                .into(imageView);

        textView.setText(name);

        textViewDescription = findViewById(R.id.textView);

        permissionManager = new PermissionManager() {
            @Override
            public boolean checkAndRequestPermissions(Activity activity) {
                return super.checkAndRequestPermissions(activity);
            }

            @Override
            public List<String> setPermission() {
                return super.setPermission();
            }

            @Override
            public void ifCancelledAndCanRequest(Activity activity) {
                super.ifCancelledAndCanRequest(activity);
            }

            @Override
            public void ifCancelledAndCannotRequest(Activity activity) {
                super.ifCancelledAndCannotRequest(activity);
            }
        };
        sharedPref = getApplicationContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
        String id = sharedPref.getString("id","5");

        permissionManager.checkAndRequestPermissions(this);
        new Viewdata().execute("https://advisor-assistant.000webhostapp.com/all_student.php?id="+id);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startScan();
                Intent intent = new Intent(MainActivity.this,StudentInfoActivity.class);
                intent.putExtra("flag","barcode");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.nav_home){
            drawer.closeDrawers();
            return true;
        }else if(item.getItemId()==R.id.nav_logout){
            Intent intent = new Intent(this,LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("mypref", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("id", "-1");
            editor.apply();
            startActivity(intent);
            drawer.closeDrawers();
            return true;

        }else if(item.getItemId()==R.id.nav_rate_us){
            drawer.closeDrawers();
            return true;
        }else if(item.getItemId()==R.id.nav_about_us){
            Intent intent = new Intent(this,AboutUsActivity.class);
            startActivity(intent);
            drawer.closeDrawers();
            return true;
        }
        return false;
    }



    class Viewdata extends Urltask{


        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(List<DataModel> dataModels) {
            super.onPostExecute(dataModels);
            progressDialog.dismiss();
            RecyclerviewAdapter recyclerviewAdapter = new RecyclerviewAdapter(dataModels,getApplicationContext());
            if(recyclerviewAdapter.getItemCount()==0){
                relativeLayoutNoStudent.setVisibility(View.VISIBLE);
            }
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(recyclerviewAdapter);
        }
    }
}