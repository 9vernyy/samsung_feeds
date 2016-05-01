package kz.alisher.samsungnews.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.fragment.CategoryFragment;
import kz.alisher.samsungnews.fragment.FavouriteFragment;
import kz.alisher.samsungnews.fragment.HomeFragment;
import kz.alisher.samsungnews.utils.GlobalItems;
import kz.alisher.samsungnews.utils.SessionManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private SharedPreferences.Editor editor;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean themeName = pref.getBoolean("chb1",false);
        if (themeName) {
            super.setTheme(R.style.AppThemeTwo);
        } else {
            super.setTheme(R.style.AppTheme);
        }
        setContentView(R.layout.activity_main);
        setTitle("Samsung news");

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, HomeFragment.newInstance()).commit();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        editor = getPreferences(MODE_PRIVATE).edit();
        editor.putInt("news_count", 0);
        editor.apply();
        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()){
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
            initProfile();

        } else {
            navigationView.getMenu().findItem(R.id.favourite).setVisible(false);
            navigationView.getMenu().findItem(R.id.logout).setVisible(false);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.favourite) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, FavouriteFragment.newInstance()).commit();
        } else if (id == R.id.homeF) {
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.categories) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, CategoryFragment.newInstance()).commit();
        } else if (id == R.id.login){
            startActivity(new Intent(this, LoginActivity.class));
        } else if (id == R.id.logout){
            sessionManager.setLogin(false);
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.settings){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, new SetFragment()).commit();
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void initProfile() {
        View header = navigationView.getHeaderView(0);
        TextView nameTxt = (TextView) header.findViewById(R.id.login_name);
        TextView emailTxt = (TextView) header.findViewById(R.id.login_email);
        ImageView imageView = (ImageView) header.findViewById(R.id.login_image);
        nameTxt.setText("admin");
        emailTxt.setText("admin@gmail.com");
        imageView.setImageResource(R.drawable.profile);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.search) {
            startActivity(new Intent(this, SearchActivity.class));
        }
        return super.onOptionsItemSelected(item) || toggle.onOptionsItemSelected(item);
    }

    private void getPushNotification() {
//        final ArrayList<String> testStrings = new ArrayList<>();
//        testStrings.add("First");
//        testStrings.add("Second");

        Timer myTimer = new Timer();
        final Intent intent = new Intent(this, MainActivity.class);
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
//                int old = prefs.getInt("news_count", testStrings.size());
//                if (old == 0){
//                    editor.putInt("news_count", testStrings.size());
//                    editor.apply();
//                    old = testStrings.size();
//                }
//                Log.d("TEST", "------------------------");
//                Log.d("TEST OLD", old+"");
//                Log.d("TEST SIZE", testStrings.size()+"");
//                if (testStrings.size() > old) {
//                    Log.d("TEST TRUE", testStrings.size()+"");
//                    showNotificationMessage(testStrings.get(testStrings.size() - 1), testStrings.get(testStrings.size() - 1), intent);
//                    editor.putInt("news_count", testStrings.size());
//                    editor.apply();
//                }
//                Log.d("TEST FALSE", testStrings.size()+"");
//                testStrings.add("THIRD");
                try {
                    Elements items = new GlobalItems().execute().get();
                    SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                    int old = prefs.getInt("news_count", 0);
                    if (old == 0) {
                        editor.putInt("news_count", items.size());
                        editor.apply();
                        old = items.size();
                    }
                    if (items.size() > old) {
                        Element e = items.get(items.size() - 1);
                        String title = e.select("title").first().text();
                        String description = e.select("description").first().text();
                        showNotificationMessage(title, description, intent);
                        editor.putInt("news_count", items.size());
                        editor.apply();
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, 0L, 60L * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPushNotification();
    }

    public void showNotificationMessage(String title, String message, Intent intent) {
        int icon = R.drawable.star_notfilled;
        int smallIcon = R.drawable.star_filled;
        int mNotificationId = 1;
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Notification notification = mBuilder.setSmallIcon(smallIcon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setStyle(inboxStyle)
                .setContentIntent(resultPendingIntent)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), icon))
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(mNotificationId, notification);
    }
}