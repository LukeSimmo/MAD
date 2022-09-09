package curtin.edu.assignment2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.savedstate.SavedStateRegistryOwner;

import com.example.assignment2.R;

public class SendEmailActivity extends AppCompatActivity
{
    private StudentList studentList;

    private String message;

    private Button back;
    private RecyclerView rv;
    private ViewStudentsAdapter adapter;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_send_email);

        // GET DATABASE
        studentList = new StudentList();
        studentList.load(SendEmailActivity.this);

        back = findViewById(R.id.btnSendEmailBack);
        rv = findViewById(R.id.recyclerSendEmail);

        adapter = new ViewStudentsAdapter();
        rv.setLayoutManager(new LinearLayoutManager(SendEmailActivity.this));
        rv.setAdapter(adapter);

        Student student = (Student) getIntent().getSerializableExtra("student");
        Test test = (Test) getIntent().getSerializableExtra("test");

        message = "Student: " + student.getFirstname() + " " + student.getLastname() + "\n";
        message += "Mark: " + test.getMark() + "\nTime and Date of Test: " + test.getTimeOfTest()
                + "\nTotal time: " + test.getTotalTime() + " seconds";


        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(UserActivity.getIntent(SendEmailActivity.this, 1));
            }
        });
    }

    private class ViewStudentsViewHolder extends RecyclerView.ViewHolder
    {
        TextView textFirstname;
        TextView textLastname;
        ImageView image;

        public ViewStudentsViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.entry_view_student, parent, false));

            textFirstname = itemView.findViewById(R.id.textViewStudentEntryFirstname);
            textLastname = itemView.findViewById(R.id.textViewStudentEntryLastname);
            image = itemView.findViewById(R.id.imageViewStudentEntry);
        }

        public void bind(Student currStudent)
        {
            textFirstname.setText(currStudent.getFirstname());
            textLastname.setText(currStudent.getLastname());

            try
            {
                Bitmap bitmap = BitmapFactory.decodeByteArray(currStudent.getImage(), 0, currStudent.getImage().length);
                image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 100, false));
            }
            catch (Exception e)
            {
                Toast.makeText(SendEmailActivity.this, "No Image Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class ViewStudentsAdapter extends RecyclerView.Adapter<ViewStudentsViewHolder>
    {
        @Override
        public ViewStudentsViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new ViewStudentsViewHolder(LayoutInflater.from(SendEmailActivity.this), container);
        }

        @Override
        public void onBindViewHolder(ViewStudentsViewHolder vh, int position)
        {
            Student student = studentList.get(position);

            vh.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", student.getEmail(), null));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Results");
                    intent.putExtra(Intent.EXTRA_TEXT, message);
                    startActivity(Intent.createChooser(intent, "Choose email client: "));
                }
            });

            vh.bind(student);
        }

        @Override
        public int getItemCount()
        {
            return studentList.size();
        }
    }

    public static Intent getIntent(Context c, Student student, Test test)
    {
        Intent intent = new Intent(c, SendEmailActivity.class);
        intent.putExtra("student", student);
        intent.putExtra("test", test);
        return intent;
    }
}
