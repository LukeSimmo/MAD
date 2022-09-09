package curtin.edu.assignment2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.assignment2.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GridPhotoActivity extends AppCompatActivity
{
    Button btnDownload;
    EditText searchKey;
    ProgressBar progressBar;

    Bitmap[] imageArray;

    // RECYCLER
    RecyclerView rv;
    gridAdapter adapter;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_grid_photo);

        btnDownload = findViewById(R.id.btnGridPhotoDownload);
        searchKey = findViewById(R.id.editGridPhotoSearchValues);
        progressBar = findViewById(R.id.progressBarGridPhoto);
        rv = findViewById(R.id.gridPhotoRecycler);

        progressBar.setMax(20);

        adapter = new gridAdapter();
        rv.setLayoutManager(new GridLayoutManager(
                GridPhotoActivity.this,
                3,
                RecyclerView.VERTICAL,
                false));

        btnDownload.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String searchValues = searchKey.getText().toString();

                new URLTask().execute(searchValues);
            }
        });
    }

    private class gridViewHolder extends RecyclerView.ViewHolder
    {
        ImageView image;

        public gridViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.entry_grid, parent, false));

            image = (ImageView) itemView.findViewById(R.id.entryGridImage);
        }

        public void bind(Bitmap bitmap)
        {
            image.setImageBitmap(bitmap);
        }
    }

    public class gridAdapter extends RecyclerView.Adapter<gridViewHolder>
    {
        @Override
        public gridViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new gridViewHolder(LayoutInflater.from(GridPhotoActivity.this), container);
        }

        @Override
        public void onBindViewHolder(gridViewHolder vh, int position)
        {
            Bitmap tempImg = imageArray[position];

            vh.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    byte[] byteImg;

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    tempImg.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byteImg = baos.toByteArray();

                    Intent returnData = new Intent();
                    returnData.putExtra("image", byteImg);
                    setResult(RESULT_OK, returnData);
                    finish();
                }
            });

            vh.bind(tempImg);
        }

        @Override
        public int getItemCount()
        {
            return 20;
        }
    }

    private class URLTask extends AsyncTask<String, Integer, Bitmap[]>
    {
        @Override
        protected Bitmap[] doInBackground(String... params)
        {
            String result = "";
            HttpsURLConnection conn;
            String[] urlArray = new String[20];
            Bitmap[] bitArray = new Bitmap[20];

            try
            {
                // BUILD URL
                String urlString = Uri.parse("https://pixabay.com/api/")
                        .buildUpon()
                        .appendQueryParameter("key", "23319229-94b52a4727158e1dc3fd5f2db")
                        .appendQueryParameter("q", params[0])
                        .build().toString();

                Log.d("Log", urlString);

                URL url = new URL(urlString);
                conn = (HttpsURLConnection) url.openConnection();

                try
                {
                    InputStream input = conn.getInputStream();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    byte[] buffer = new byte[1024];
                    int bytesRead = input.read(buffer);

                    while(bytesRead > 0)
                    {
                        baos.write(buffer, 0, bytesRead);
                        bytesRead = input.read(buffer);
                    }

                    baos.close();

                    result = new String(baos.toByteArray());
                }
                finally
                {
                    conn.disconnect();
                }

                JSONObject jBase = new JSONObject(result);

                JSONArray jHits = jBase.getJSONArray("hits");

                for(int ii = 0; ii < urlArray.length; ii++)
                {
                    JSONObject temp = jHits.getJSONObject(ii);

                    urlArray[ii] = temp.getString("previewURL");
                }

                for(int ii = 0; ii < urlArray.length; ii++)
                {
                    URL imgURL = new URL(urlArray[ii]);
                    HttpsURLConnection imgConn = (HttpsURLConnection) imgURL.openConnection();

                    publishProgress(ii);

                    try
                    {
                        bitArray[ii] = BitmapFactory.decodeStream(imgConn.getInputStream());
                    }
                    finally
                    {
                        imgConn.disconnect();
                    }
                }

                publishProgress(20);

            }
            catch(MalformedURLException e)
            {
                Log.d("Exception", "MalformedURLException " + e.getMessage());
            }
            catch(IOException e)
            {
                Log.d("Exception", "IOException " + e.getMessage());
            }
            catch(JSONException e)
            {
                Log.d("Exception", "JSONException " + e.getMessage());
            }

            return bitArray;
        }

        @Override
        protected void onProgressUpdate(Integer... params)
        {
            progressBar.setProgress(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap[] result)
        {
            if(result != null)
            {
                imageArray = result;
                rv.setAdapter(adapter);
            }
            else
            {
                Log.d("Exception", "Error in Search Images");
            }
        }
    }

    public static byte[] getImage(Intent intent)
    {
        return intent.getByteArrayExtra("image");
    }
}
