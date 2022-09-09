package curtin.edu.assignment2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;

import java.util.List;

public class ViewResults extends AppCompatActivity
{
    private Button back;

    private RecyclerView rv;
    private ResultAdapter adapter;

    private TestList testList;
    private StudentList studentList;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_view_results);

        rv = findViewById(R.id.recyclerResults);
        back = findViewById(R.id.btnViewResultsBack);

        testList = new TestList();
        testList.load(ViewResults.this);

        studentList = new StudentList();
        studentList.load(ViewResults.this);

        adapter = new ResultAdapter();
        rv.setLayoutManager(new LinearLayoutManager(ViewResults.this));

        rv.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(UserActivity.getIntent(ViewResults.this, 1));
            }
        });
    }

    private class ResultsViewHolder extends RecyclerView.ViewHolder
    {
        TextView firstname;
        TextView lastname;
        TextView mark;
        TextView timeOfTest;
        TextView totalTime;

        public ResultsViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.entry_result, parent, false));

            firstname = (TextView) itemView.findViewById(R.id.textEntryResultFirstname);
            lastname = (TextView) itemView.findViewById(R.id.textEntryResultLastname);
            mark = (TextView) itemView.findViewById(R.id.textEntryResultMark);
            timeOfTest = (TextView) itemView.findViewById(R.id.textEntryResultDate);
            totalTime = (TextView) itemView.findViewById(R.id.textEntryResultTotalTime);
        }

        public void bind(String firstnameVal, String lastnameVal, Test test)
        {
            firstname.setText(firstnameVal);
            lastname.setText(lastnameVal);
            mark.setText(String.valueOf(test.getMark()));
            timeOfTest.setText(test.getTimeOfTest());
            totalTime.setText(String.valueOf(test.getTotalTime()) + " Seconds");
        }
    }

    public class ResultAdapter extends RecyclerView.Adapter<ResultsViewHolder>
    {
        @Override
        public ResultsViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new ResultsViewHolder(LayoutInflater.from(ViewResults.this), container);
        }

        @Override
        public void onBindViewHolder(ResultsViewHolder vh, int position)
        {
            Test test = testList.get(position);
            Student student = studentList.getStudentById(test.getStudentID());

            vh.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    startActivity(SendEmailActivity.getIntent(ViewResults.this, student, test));
                }
            });

            vh.bind(student.getFirstname(), student.getLastname(), test);
        }

        @Override
        public int getItemCount()
        {
            return testList.size();
        }
    }

    public static Intent getIntent(Context c, Student student)
    {
        Intent intent = new Intent(c, ViewResults.class);
        intent.putExtra("student", student);
        return intent;
    }
}
