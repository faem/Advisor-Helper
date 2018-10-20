package com.fahimabrar.tracker;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.DashPathEffect;
import android.graphics.PathEffect;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.vision.barcode.Barcode;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;

public class StudentInfoActivity extends AppCompatActivity {
    ImageView imageView, emailButton, phoneButton;
    TextView idTextView, cgpaTextView, phoneTextView, backlogTextView, nameTextView, emailTextView ;
    LinearLayout llCgpa, llBl;

    String apiUrl;
    Number[] domainLabels;
    Number[] series1Numbers;
    int count = 0;

    String bl_details = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        //titleTextView = findViewById(R.id.titleTextView);
        //categoryTextView = findViewById(R.id.categoryTextView);
        imageView = findViewById(R.id.imageview);
        idTextView = findViewById(R.id.id);
        cgpaTextView = findViewById(R.id.cgpa);
        phoneTextView = findViewById(R.id.phone);
        emailTextView = findViewById(R.id.email);
        nameTextView = findViewById(R.id.name);
        backlogTextView = findViewById(R.id.backlog);
        emailButton = findViewById(R.id.emailButton);
        phoneButton = findViewById(R.id.phoneButton);
        llCgpa = findViewById(R.id.llCgpa);
        llBl = findViewById(R.id.llBl);
        llBl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(StudentInfoActivity.this);
                dialog.setContentView(R.layout.backlog_dialog);
                TextView textView = dialog.findViewById(R.id.textViewBl);
                if(bl_details.equals("")){
                    textView.setText("No Backlog!");
                }else{
                    textView.setText(bl_details);
                }

                Button button = dialog.findViewById(R.id.button2);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        llCgpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count != 0) {
                    final Dialog dialog = new Dialog(StudentInfoActivity.this);
                    dialog.setContentView(R.layout.cgpa_dialog);
                    dialog.setTitle("CGPA");
                    XYPlot plot = (XYPlot) dialog.findViewById(R.id.plot);
                    Button button = dialog.findViewById(R.id.button);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });


                    // create a couple arrays of y-values to plot:


                    // turn the above arrays into XYSeries':
                    // (Y_VALS_ONLY means use the element index as the x value)
                    XYSeries series1 = new SimpleXYSeries(
                            Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "cgpa");
                    // create formatters to use for drawing a series using LineAndPointRenderer
                    // and configure them from xml:
                    LineAndPointFormatter series1Format =
                            new LineAndPointFormatter(StudentInfoActivity.this, R.xml.line_point_formatter_with_labels);

                    // add an "dash" effect to the series2 line:

                    // just for fun, add some smoothing to the lines:
                    // see: http://androidplot.com/smooth-curves-and-androidplot/
                    series1Format.setInterpolationParams(
                            new CatmullRomInterpolator.Params(count, CatmullRomInterpolator.Type.Centripetal));
                    series1Format.setLegendIconEnabled(false);

                    // add a new series' to the xyplot:
                    plot.addSeries(series1, series1Format);
                    //plot.setDomainBoundaries(1,8, BoundaryMode.FIXED);
                    plot.setRangeBoundaries(2.0, 4.0, BoundaryMode.FIXED);
                    plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
                        @Override
                        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                            int i = Math.round(((Number) obj).floatValue());
                            return toAppendTo.append(domainLabels[i]);
                        }

                        @Override
                        public Object parseObject(String source, ParsePosition pos) {
                            return null;
                        }
                    });
                    dialog.show();
                }else{
                    final Dialog dialog = new Dialog(StudentInfoActivity.this);
                    dialog.setContentView(R.layout.empty_dialog);
                    Button button = dialog.findViewById(R.id.button1);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            }
        });

        Intent intent = getIntent();
        String x = intent.getStringExtra("flag");
        String id = intent.getStringExtra("id");
        if(x.equals("barcode")){
            startScan();
        }else{
            apiUrl = "https://advisor-assistant.000webhostapp.com/single_student.php?id="+id;
            MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
            myAsyncTasks.execute();
        }
    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(StudentInfoActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            // implement API in background and store the response in current variable
            String current = "";

            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(apiUrl);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isw.read();
                        System.out.print(current);

                    }
                    // return the data to onPostExecute method
                    return current;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {

            Log.d("data", s.toString());
            // dismiss the progress dialog after receiving data from API
            progressDialog.dismiss();
            try {
                // JSON Parsing of data
                JSONArray jsonArray = new JSONArray(s);

                JSONObject oneObject = jsonArray.getJSONObject(0);
                // Pulling items from the array
                final String name,bl,cgpa,email,sid,tel, image;
                double[] acgpa = new double[8];
                name = oneObject.getString("username");
                bl = oneObject.getString("bl");
                cgpa = oneObject.getString("cgpa");
                email = oneObject.getString("email");
                sid = oneObject.getString("sid");
                tel = oneObject.getString("tel");
                if(!bl.equals("0"))
                    bl_details = oneObject.getString("bl_details");
                count = 0;
                for(int i=0;i<8;i++){
                    int k = i+1;
                    acgpa[i] = oneObject.getDouble("cgpa"+k);
                    Log.e("cgpa"+i,""+acgpa[i]);
                    if(acgpa[i]==0){
                        count = i;
                        break;
                    }
                }
                domainLabels = new Number[count];
                series1Numbers = new Number[count];

                for(int i=0;i<count;i++){
                    domainLabels[i] = i;
                    series1Numbers[i] = acgpa[i];
                }

                image = "https://advisor-assistant.000webhostapp.com/image/"+oneObject.getString("pic");

                phoneButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:"+tel));
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(callIntent);
                    }
                });

               emailButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                                "mailto", email, null));
                        startActivity(intent);
                    }
                });


                nameTextView.setText(name);
                backlogTextView.setText(bl);
                cgpaTextView.setText(cgpa);
                emailTextView.setText(email);
                idTextView.setText(sid);
                phoneTextView.setText(tel);
                Picasso.with(getApplicationContext())
                        .load(image)
                        .error( R.drawable.error_image)
                        .placeholder( R.drawable.progressbar )
                        .into(imageView);


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    private void startScan() {
        /**
         * Build a new MaterialBarcodeScanner
         */
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(StudentInfoActivity.this)
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withText("Scanning...")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        //Barcode barcodeResult = barcode;
                        //textViewDescription.setText(barcode.rawValue);
                        apiUrl = "https://advisor-assistant.000webhostapp.com/single_student.php?id="+Integer.parseInt(barcode.rawValue);
                        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                        myAsyncTasks.execute();
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }
}
