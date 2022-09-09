package curtin.edu.assignment2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignment2.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ViewStudentActivity extends AppCompatActivity
{
    private StudentList list;

    private RecyclerView recycler;
    private ViewStudentAdapter adapter;
    private LinearLayoutManager lm;

    private Button btnBack;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_view_student);

        recycler = findViewById(R.id.recyclerViewStudent);
        btnBack = findViewById(R.id.btnViewStudentBack);

        // SET DATABASE
        list = new StudentList();
        list.load(ViewStudentActivity.this);

        // SET ADAPTER
        adapter = new ViewStudentAdapter();
        lm = new LinearLayoutManager(ViewStudentActivity.this);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(lm);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(UserActivity.getIntent(ViewStudentActivity.this, 0));
            }
        });
    }

    private class ViewStudentViewHolder extends RecyclerView.ViewHolder
    {
        private Student student;

        private TextView firstname;
        private TextView lastname;
        private ImageView image;

        public ViewStudentViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.entry_view_student, parent, false));

            firstname = (TextView) itemView.findViewById(R.id.textViewStudentEntryFirstname);
            lastname = (TextView) itemView.findViewById(R.id.textViewStudentEntryLastname);
            image = (ImageView) itemView.findViewById(R.id.imageViewStudentEntry);
        }

        public void bind(Student student)
        {
            this.student = student;

            firstname.setText(student.getFirstname());
            lastname.setText(student.getLastname());

            // make byte array to bitmap
            try
            {
                Bitmap bitmap = BitmapFactory.decodeByteArray(student.getImage(), 0, student.getImage().length);
                image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 100, false));
            }
            catch (Exception e)
            {
                Toast.makeText(ViewStudentActivity.this, "No Image Found", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class ViewStudentAdapter extends RecyclerView.Adapter<ViewStudentViewHolder>
    {
        @Override
        public ViewStudentViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new ViewStudentViewHolder(LayoutInflater.from(ViewStudentActivity.this), container);
        }

        @Override
        public void onBindViewHolder(ViewStudentViewHolder vh, int position)
        {
            Student student = list.get(position);

            vh.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    startActivity(EditStudentActivity.getIntent(ViewStudentActivity.this, student, 1));
                }
            });

            vh.bind(student);
        }

        @Override
        public int getItemCount()
        {
            return list.size();
        }
    }
}
