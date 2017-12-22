package javedhussain.com.cuietinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class AttActivity extends AppCompatActivity {


    OkHttpClient client = new OkHttpClient();
    String htm;
    String det;
    TextView tot;
    TextView pres;
    TextView perct;
    TextView sub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_att);
        tot = findViewById(R.id.tothrs);
        pres = findViewById(R.id.preshrs);
        perct = findViewById(R.id.perct);
        sub = findViewById(R.id.subdet);

        Intent i = getIntent();
        htm = i.getExtras().getString("source");
        det = i.getExtras().getString("detsrc");
        Document doc = Jsoup.parse(htm);
        Element elements = doc.select("div.content1").first();
        Elements tab = doc.select("td");
        String tothrs = tab.get(6).text().toString();
        String preshrs = tab.get(3).text().toString();
        String prct = tab.get(9).text().toString();

        StringBuilder ftot = new StringBuilder(30);
        ftot.append("Total Working Hours : ");
        ftot.append(tothrs);
        tot.setText(ftot);

        StringBuilder fpres = new StringBuilder(30);
        fpres.append("Hours Present : ");
        fpres.append(preshrs);
        pres.setText(fpres);

        StringBuilder fperc = new StringBuilder(30);
        fperc.append("Percentage : ");
        fperc.append(prct);
        perct.setText(fperc);

        Document ddoc = Jsoup.parse(det);
        Elements delements = ddoc.getElementsByClass("tb1");

        StringBuilder superdet = new StringBuilder(500);
        int t;
        int p;
        int pr;
        List<String> stats = new ArrayList<>();

        for (Element row : delements.select("tr")) {
            Elements tds = row.select("td");
            if (tds.size() > 0) {
                superdet.append(tds.get(0).text() + " " + tds.get(1).text() + "\n"
                        + "Present Hours : " + tds.get(2).text() + "\n"
                        + "Total Hours : " + tds.get(3).text() + "\n"
                );

                p = Integer.parseInt(tds.get(2).text());
                t = Integer.parseInt(tds.get(3).text());
                if (t != 0) {
                    pr = 100 * p / t;
                } else {
                    pr = 0;
                }
                superdet.append("Percentage : " + pr + "% " + "\n" + "\n");
                stats.add(superdet.toString());
                superdet.setLength(0);


            }
        }
        StringBuilder findet = new StringBuilder(500);
        for (int j = 0; j < (stats.size()) - 1; j++) {
            findet.append(stats.get(j));
        }
        sub.setText(findet);


    }


}