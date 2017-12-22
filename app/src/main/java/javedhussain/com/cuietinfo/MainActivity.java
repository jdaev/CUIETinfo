package javedhussain.com.cuietinfo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button LogIn;
    EditText reg;
    EditText pass;
    String regno;
    String password;
    String det;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        reg = findViewById(R.id.regno);
        pass = findViewById(R.id.password);
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        String rereg = settings.getString("reg", null);
        String repass = settings.getString("pass", null);
        reg.setText(rereg);
        pass.setText(repass);
        if (savedInstanceState != null) {
            String editTextString = savedInstanceState.getString("reg");
            reg.setText(editTextString);
        }

        if (savedInstanceState != null) {
            String editTextString = savedInstanceState.getString("pass");
            pass.setText(editTextString);
        }
        LogIn = findViewById(R.id.login);
        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkconn()) {
                    attemptLogin();

                } else {
                    Context context = getApplicationContext();

                    CharSequence text = "No network,turn it on and try again!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


                }
            }
        });


    }

    public boolean checkconn() {
        boolean sts;

        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        sts = activeNetwork != null && activeNetwork.isConnected();
        return sts;
    }

    private void attemptLogin() {

        // Store values at the time of the login attempt.
        regno = reg.getText().toString();
        password = pass.getText().toString();

        boolean cancel = false;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            cancel = true;
        }

        if (cancel) {
        } else {
            // perform the user login attempt
            View lbutton = findViewById(R.id.login);
            lbutton.setVisibility(View.GONE);
            View regtxt = findViewById(R.id.regno);
            regtxt.setVisibility(View.GONE);
            View pass = findViewById(R.id.password);
            pass.setVisibility(View.GONE);
            View load = findViewById(R.id.loader);
            load.setVisibility(View.VISIBLE);


            UserLoginTask log = new UserLoginTask();
            log.execute(regno, password);


        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("reg", reg.getText().toString());
        outState.putString("pass", pass.getText().toString());

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        reg.setText(savedInstanceState.getString("reg"));
        pass.setText(savedInstanceState.getString("pass"));
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onResume() {

        super.onResume();
        View lbutton = findViewById(R.id.login);
        lbutton.setVisibility(View.VISIBLE);
        View regtxt = findViewById(R.id.regno);
        regtxt.setVisibility(View.VISIBLE);
        View passw = findViewById(R.id.password);
        passw.setVisibility(View.VISIBLE);
        View load = findViewById(R.id.loader);
        load.setVisibility(View.GONE);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Intent mintent = new Intent(MainActivity.this,
                    AboutActivity.class);
            startActivity(mintent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.colnot) {
            String url = "http://cuiet.info/index.php?id=33";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        } else if (id == R.id.unot) {
            String url = "http://www.universityofcalicut.info/index2.php?option=com_content&task=view&id=744&pop=1&page=0";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.ures) {
            String url = "http://www.cupbresults.uoc.ac.in/CuPbhavan/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        } else if (id == R.id.syl) {
            Intent sintent = new Intent(MainActivity.this,
                    SyllabusActivity.class);
            startActivity(sintent);


        } else if (id == R.id.qp) {
            Intent qintent = new Intent(MainActivity.this,
                    QuesActivity.class);
            startActivity(qintent);


        } else if (id == R.id.tt) {
            String url = "http://www.universityofcalicut.info/index2.php?option=com_content&task=view&id=745&pop=1&page=0";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("reg", regno);
        editor.putString("pass", password);

        // Commit the edits!
        editor.commit();
    }

    public class UserLoginTask extends AsyncTask<String, Void, String> {
        String htm;


        @Override
        protected String doInBackground(String... strings) {
            String reg = strings[0];
            String pass = strings[1];


            CookieJar cookieJar = new CookieJar() {
                private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            };
            OkHttpClient client = new OkHttpClient.Builder()
                    .cookieJar(cookieJar)
                    .build();


            RequestBody formBody = new FormBody.Builder()
                    .add("username", reg)
                    .add("password", pass)
                    .add("login", "Login")
                    .build();
            Request request = new Request.Builder()
                    .url("http://cuiet.info/module/index.php")
                    .post(formBody)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    Context context = getApplicationContext();

                    CharSequence text = "Cannot reach server, try again later";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();


                    throw new IOException("Unexpected code " + response);
                }
                Request arequest = new Request.Builder()
                        .url("http://cuiet.info/module/students/index.php?opt=2")
                        .get()
                        .build();
                Response aresponse = client.newCall(arequest).execute();

                htm = (aresponse.body().string());

                Request drequest = new Request.Builder()
                        .url("http://cuiet.info/module/students/index.php?opt=3")
                        .get()
                        .build();
                Response dresponse = client.newCall(drequest).execute();

                det = (dresponse.body().string());


                return htm;


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int b = s.indexOf("Please login");
            if (b > 0) {
                View load = findViewById(R.id.loader);
                load.setVisibility(View.GONE);

                View lbutton = findViewById(R.id.login);
                lbutton.setVisibility(View.VISIBLE);
                View regtxt = findViewById(R.id.regno);
                regtxt.setVisibility(View.VISIBLE);
                View pass = findViewById(R.id.password);
                pass.setVisibility(View.VISIBLE);
                Context context = getApplicationContext();
                CharSequence text = "Try Again!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


            } else {

                Intent Lintent = new Intent(MainActivity.this,
                        AttActivity.class);
                Lintent.putExtra("source", s);
                Lintent.putExtra("detsrc", det);
                startActivity(Lintent);

            }


        }


    }


}
