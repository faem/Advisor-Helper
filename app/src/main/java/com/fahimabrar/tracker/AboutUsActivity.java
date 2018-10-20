package com.fahimabrar.tracker;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class AboutUsActivity extends AppCompatActivity {
    ImageView imageViewFahim, imageViewSaki, imageViewRema, imageViewTanu, imageViewSagor, imageViewFbSaki, imageViewLinkedinSaki, imageViewMailSaki, imageViewPhoneSaki, imageViewFbFahim, imageViewLinkedinFahim, imageViewMailFahim, imageViewPhoneFahim, imageViewPhoneRema, imageViewFbRema, imageViewMailRema, imageViewLinkedinRema, imageViewPhoneTanu, imageViewFbTanu, imageViewMailTanu, imageViewLinkedinTanu,imageViewPhoneSagor, imageViewFbSagor, imageViewMailSagor, imageViewLinkedinSagor;
    CustomTabsServiceConnection mCustomTabsServiceConnection;
    CustomTabsClient mClient;
    CustomTabsSession mCustomTabsSession;
    CustomTabsIntent.Builder builder;
    boolean warmedUp = false;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageViewFahim = (ImageView) findViewById(R.id.imageViewFahim);
        imageViewSaki = (ImageView) findViewById(R.id.imageViewSaki);
        imageViewRema = (ImageView) findViewById(R.id.imageViewRema);
        imageViewFbFahim = (ImageView) findViewById(R.id.imageViewFbFahim);
        imageViewFbSaki = (ImageView) findViewById(R.id.imageViewFbSaki);
        imageViewFbRema = (ImageView) findViewById(R.id.imageViewFbRema);
        imageViewMailFahim = (ImageView) findViewById(R.id.imageViewMailFahim);
        imageViewMailSaki = (ImageView) findViewById(R.id.imageViewMailSaki);
        imageViewMailRema = (ImageView) findViewById(R.id.imageViewMailRema);
        imageViewLinkedinFahim = (ImageView) findViewById(R.id.imageViewLinkdinFahim);
        imageViewLinkedinSaki = (ImageView) findViewById(R.id.imageViewLinkdinSaki);
        imageViewLinkedinRema = (ImageView) findViewById(R.id.imageViewLinkdinRema);
        imageViewPhoneFahim = (ImageView) findViewById(R.id.imageViewPhoneFahim);
        imageViewPhoneSaki = (ImageView) findViewById(R.id.imageViewPhoneSaki);
        imageViewPhoneRema = (ImageView) findViewById(R.id.imageViewPhoneRema);
        imageViewPhoneTanu = findViewById(R.id.imageViewPhoneTanu);
        imageViewFbTanu = findViewById(R.id.imageViewFbTanu);
        imageViewTanu = findViewById(R.id.imageViewTanu);
        imageViewMailTanu = findViewById(R.id.imageViewMailTanu);
        imageViewPhoneSagor = findViewById(R.id.imageViewPhoneSagor);
        imageViewFbSagor = findViewById(R.id.imageViewFbSagor);
        imageViewSagor = findViewById(R.id.imageViewSagor);
        imageViewMailSagor = findViewById(R.id.imageViewMailSagor);
        imageViewLinkedinSagor = findViewById(R.id.imageViewLinkdinSagor);


        imageViewFahim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "http://www.facebook.com/fahim.abrar3";
                startChrome(url);
            }
        });

        imageViewSaki.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "http://www.facebook.com/sakikowsar";
                startChrome(url);
            }
        });
        imageViewRema.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "http://www.facebook.com/rocker.rema";
                startChrome(url);
            }
        });
        imageViewTanu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "http://www.facebook.com/tanusree.debi";
                startChrome(url);
            }
        });
        imageViewSagor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "http://www.facebook.com/ikramulhaq.sagor";
                startChrome(url);
            }
        });
        imageViewFbFahim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "http://www.facebook.com/fahim.abrar3";
                startChrome(url);
            }
        });

        imageViewFbSaki.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "http://www.facebook.com/sakikowsar";
                startChrome(url);
            }
        });

        imageViewFbRema.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "http://www.facebook.com/rocker.rema";
                startChrome(url);
            }
        });

        imageViewFbTanu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "http://www.facebook.com/tanusree.deb";
                startChrome(url);
            }
        });

        imageViewFbSagor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "http://www.facebook.com/ikramulhaq.sagor";
                startChrome(url);
            }
        });

        imageViewLinkedinFahim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "https://www.linkedin.com/in/fahim-abrar-855784103/";
                startChrome(url);
            }
        });

        imageViewLinkedinRema.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //url = "https://www.linkedin.com/in/mdSakihossain/";
                //startChrome(url);
            }
        });
        imageViewLinkedinSaki.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //url = "https://www.linkedin.com/in/mdSakihossain/";
                //startChrome(url);
            }
        });
        imageViewLinkedinSagor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                url = "https://www.linkedin.com/in/md-ikramul-hoque-2088a8164/";
                startChrome(url);
            }
        });


        imageViewMailFahim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "fahimabrar02@gmail.com", null));
                startActivity(intent);
            }
        });

        imageViewMailSaki.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "sakikowsar@cuet.ac.bd", null));
                startActivity(intent);
            }
        });
        imageViewMailRema.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "farjanarema13@gmail.com", null));
                startActivity(intent);
            }
        });
        imageViewMailTanu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "tanusreedebi11@gmail.com ", null));
                startActivity(intent);
            }
        });

        imageViewMailSagor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "ikramulhaqsagor@gmail.com", null));
                startActivity(intent);
            }
        });


        imageViewPhoneFahim.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+8801521485508"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                startActivity(callIntent);
            }
        });

        imageViewPhoneSaki.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+8801713109890"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                startActivity(callIntent);
            }
        });

        imageViewPhoneRema.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+8801521483183"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                startActivity(callIntent);
            }
        });

        imageViewPhoneTanu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+8801675714491"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        imageViewPhoneSagor.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+8801516101290"));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });
    }


    void startChrome(final String url){
        builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        builder.setShowTitle(true);

        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                //Pre-warming
                mClient = customTabsClient;
                mClient.warmup(0L);
                mCustomTabsSession = mClient.newSession(null);
                mCustomTabsSession.mayLaunchUrl(Uri.parse(url), null, null);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mClient = null;
            }
        };
        warmedUp = CustomTabsClient.bindCustomTabsService(AboutUsActivity.this, "com.android.chrome", mCustomTabsServiceConnection);

        builder.build().launchUrl(AboutUsActivity.this, Uri.parse(url));
    }
}
