package kz.alisher.samsungnews.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import kz.alisher.samsungnews.R;
import kz.alisher.samsungnews.rssmanager.RssItem;
import kz.alisher.samsungnews.utils.Favourite;

/**
 * Created by Alisher Kozhabay on 4/23/2016.
 */
public class NewsActivity extends AppCompatActivity implements Html.ImageGetter {

    private TextView contentTxt, numberOfCommentsTxt;
    private RssItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initToolbar();
        getExtras();

        contentTxt = (TextView) findViewById(R.id.contentNews);
        numberOfCommentsTxt = (TextView) findViewById(R.id.numberOfComments);
        ImageButton comment = (ImageButton) findViewById(R.id.comment);
        final ImageButton favourite = (ImageButton) findViewById(R.id.favourite);

        Spanned spanned = Html.fromHtml(item.getContent(), this, null);
        contentTxt.setText(spanned);
        numberOfCommentsTxt.setText(item.getNumberOfComments());

        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsActivity.this, "Comment", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NewsActivity.this, CommentActivity.class).putExtra("url", item.getCommentUrl()));
            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.isFavourite()==false){
                    item.setIsFavourite(true);
                    Favourite.faList.add(item);
                    favourite.setImageDrawable(getResources().getDrawable(R.drawable.star_notfilled));
                    Toast.makeText(NewsActivity.this, "Favourite", Toast.LENGTH_SHORT).show();
                }
                else{
                    item.setIsFavourite(false);
                    favourite.setImageDrawable(getResources().getDrawable(R.drawable.star_filled));
                    Favourite.faList.remove(item);
                    Toast.makeText(NewsActivity.this, "deleted from favourite", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getExtras() {
        Intent i = getIntent();
        item = (RssItem) i.getSerializableExtra("item");
        Log.d("ITEM", item.getContent());
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(R.mipmap.ic_launcher);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());

        new LoadImage().execute(source, d);

        return d;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share) {
            Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Here is the share content body";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        } else if (id == android.R.id.home) {
            onBackPressed();
        }

        return true;
    }

    class LoadImage extends AsyncTask<Object, Void, Bitmap> {

        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            Log.d("TAG", "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d("TAG", "onPostExecute drawable " + mDrawable);
            Log.d("TAG", "onPostExecute bitmap " + bitmap);
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                // i don't know yet a better way to refresh TextView
                // mTv.invalidate() doesn't work as expected
                CharSequence t = contentTxt.getText();
                contentTxt.setText(t);
            }
        }
    }
}
