package curtin.edu.assignment2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment2.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

public class TestActivity extends AppCompatActivity
{
    // CONSTANTS
    private static final String API_KEY = "01189998819991197253";

    private TextView textQuestion;
    private TextView labelQuestion;
    private TextView labelAnswer;
    private EditText editAnswer;
    private TextView labelScore;
    private TextView score;
    private ProgressBar timerBar;
    private TextView labelTotalTime;
    private TextView textTotalTime;

    private Button startTest;
    private Button endTest;
    private Button submit;

    private String[] options;

    private TestList testList;

    String answer;

    RecyclerView rv;
    TestAdapter adapter;

    int i = 0;
    int totalTime = 0;
    String startTimeDate = "";

    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_test);

        testList = new TestList();
        testList.load(TestActivity.this);

        textQuestion = findViewById(R.id.textTestQuestion);
        labelQuestion = findViewById(R.id.labelTestQuestion);
        labelAnswer = findViewById(R.id.labelTestAnswer);
        editAnswer = findViewById(R.id.editTestAnswer);
        labelScore = findViewById(R.id.labelTestScore);
        labelTotalTime = findViewById(R.id.labelTimeElapsed);
        textTotalTime = findViewById(R.id.textTotalTime);
        score = findViewById(R.id.textScore);
        timerBar = findViewById(R.id.progressBarTest);
        rv = findViewById(R.id.testRecyler);

        startTest = findViewById(R.id.btnTestStartTest);
        endTest = findViewById(R.id.btnTestEnd);
        submit = findViewById(R.id.btnTestSubmit);

        // SET VISIBILITIES
        labelQuestion.setVisibility(View.INVISIBLE);
        endTest.setVisibility(View.INVISIBLE);
        labelAnswer.setVisibility(View.INVISIBLE);
        editAnswer.setVisibility(View.INVISIBLE);
        submit.setVisibility(View.INVISIBLE);
        score.setVisibility(View.INVISIBLE);
        labelScore.setVisibility(View.INVISIBLE);
        labelTotalTime.setVisibility(View.INVISIBLE);
        textTotalTime.setVisibility(View.INVISIBLE);

        adapter = new TestAdapter();
        rv.setLayoutManager(new GridLayoutManager(
                TestActivity.this,
                2,
                RecyclerView.VERTICAL,
                false));

        score.setText("0");

        startTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                labelQuestion.setVisibility(View.VISIBLE);
                endTest.setVisibility(View.VISIBLE);
                score.setVisibility(View.VISIBLE);
                labelScore.setVisibility(View.VISIBLE);
                labelTotalTime.setVisibility(View.VISIBLE);
                textTotalTime.setVisibility(View.VISIBLE);

                startTimeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                startTest.setText("Next Question");
                new TestTask().execute();
            }
        });

        endTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Student student = (Student) getIntent().getSerializableExtra("student");
                int id = student.getId();

                testList.add(new Test(id, startTimeDate, totalTime,Double.valueOf(score.getText().toString())));

                startActivity(new Intent(TestActivity.this, SelectStudentActivity.class));
            }
        });
    }

    private class TestTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... params)
        {
            String result = "";
            HttpsURLConnection conn = null;

            try
            {
                String urlString = Uri.parse("https://192.168.134.1:8000/random/question/")
                        .buildUpon()
                        .appendQueryParameter("method", "thedata.getit")
                        .appendQueryParameter("api_key", API_KEY)
                        .appendQueryParameter("format", "json")
                        .build().toString();

                Log.d("URL", urlString);

                URL url = new URL(urlString);
                conn = (HttpsURLConnection) url.openConnection();
                DownloadUtils.addCertificate(TestActivity.this, conn);

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

            }
            catch(MalformedURLException e)
            {
                Log.d("Exception", "MalformedURLException " + e.getMessage());
            }
            catch(IOException e)
            {
                Log.d("Exception", "IOException " + e.getMessage());
            }
            catch(GeneralSecurityException e)
            {
                Log.d("Exception", "GeneralSecurityException " + e.getMessage());
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Void... params)
        {

        }

        @Override
        protected void onPostExecute(String result)
        {
            String question = null;
            String option = null;
            long seconds;

            try
            {

                JSONObject jBase = new JSONObject(result);
                JSONArray jOptions = jBase.getJSONArray("options");

                answer = jBase.getString("result");
                question = jBase.getString("question");
                seconds = jBase.getLong("timetosolve");

                startTimer(seconds);

                if(jOptions.length() != 0)
                {
                    rv.setVisibility(View.VISIBLE);
                    submit.setVisibility(View.INVISIBLE);

                    String[] temp = new String[jOptions.length()];

                    for(int ii = 0; ii < jOptions.length(); ii++)
                    {
                        temp[ii] = jOptions.getString(ii);
                    }

                    options = temp;

                    // set recyler
                    editAnswer.setVisibility(View.INVISIBLE);
                    labelAnswer.setVisibility(View.INVISIBLE);

                    rv.setAdapter(adapter);
                }
                else
                {
                    rv.setVisibility(View.INVISIBLE);
                    submit.setVisibility(View.VISIBLE);

                    // use manual answer
                    editAnswer.setVisibility(View.VISIBLE);
                    labelAnswer.setVisibility(View.VISIBLE);
                }


                /*
                for(int ii = 0; ii < jOptions.length(); ii++)
                {
                    output += jOptions.getInt(ii) + "   ";
                }*/
            }
            catch(org.json.JSONException e)
            {
                Log.d("Exception", "JSONException " + e.getMessage());
            }


            textQuestion.setText(question);

            submit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Log.d("Number", String.valueOf(editAnswer.getText()) + " " + answer);

                    if(String.valueOf(editAnswer.getText()).equals(answer))
                    {
                        score.setText(String.valueOf(Double.valueOf(score.getText().toString()) + 10));
                        Toast.makeText(TestActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        score.setText(String.valueOf(Double.valueOf(score.getText().toString()) - 5));
                        Toast.makeText(TestActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    }

                    editAnswer.setText("");

                    timer.cancel();

                    new TestTask().execute();
                }
            });
        }
    }

    private class TestViewHolder extends RecyclerView.ViewHolder
    {
        Button option;

        public TestViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.entry_test, parent, false));

            option = (Button) itemView.findViewById(R.id.btnTestSelection);
        }

        public void bind(String value)
        {
            option.setText(value);

            option.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(value.equals(answer))
                    {
                        score.setText(String.valueOf(Double.valueOf(score.getText().toString()) + 10));
                        Toast.makeText(TestActivity.this, "Correct", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        score.setText(String.valueOf(Double.valueOf(score.getText().toString()) - 5));
                        Toast.makeText(TestActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    }
                    timer.cancel();

                    new TestTask().execute();
                }
            });
        }
    }

    public class TestAdapter extends RecyclerView.Adapter<TestViewHolder>
    {
        @Override
        public TestViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new TestViewHolder(LayoutInflater.from(TestActivity.this), container);
        }

        @Override
        public void onBindViewHolder(TestViewHolder vh, int position)
        {
            String temp = options[position];

            vh.bind(temp);
        }

        @Override
        public int getItemCount()
        {
            return options.length;
        }
    }

    private void startTimer(long seconds)
    {
        long questionMs = seconds * 1000;
        i = 0;

        timer = new CountDownTimer(questionMs, 1000)
        {
            @Override
            public void onTick(long l)
            {
                i++;
                totalTime++;
                timerBar.setProgress((int)(i*100/(questionMs/1000)));
                textTotalTime.setText(String.valueOf(totalTime));
            }

            @Override
            public void onFinish()
            {
                i++;
                totalTime++;
                textTotalTime.setText(String.valueOf(totalTime));
                timer.cancel();
                new TestTask().execute();
            }
        };

        timer.start();
    }

    public static Intent getIntent(Context c, Student student)
    {
        Intent intent = new Intent(c, TestActivity.class);
        intent.putExtra("student", student);
        return intent;
    }
}
