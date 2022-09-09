package curtin.edu.assignment2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.assignment2.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SelectStudentActivity extends AppCompatActivity
{
    private StudentList list;

    private RecyclerView recycler;
    private SelectStudentAdapter adapter;
    private LinearLayoutManager lm;

    private Button btnBack;

    private int function;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_select_student);

        recycler = findViewById(R.id.recyclerSelectStudent);
        btnBack = findViewById(R.id.btnSelectStudentBack);

        function = getIntent().getIntExtra("function", 0);

        // SET DATABASE
        list = new StudentList();
        list.load(SelectStudentActivity.this);

        // SET ADAPTER
        adapter = new SelectStudentAdapter();
        lm = new LinearLayoutManager(SelectStudentActivity.this);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(lm);

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(UserActivity.getIntent(SelectStudentActivity.this, 1));
            }
        });
    }

    private class SelectStudentViewHolder extends RecyclerView.ViewHolder
    {
        private Student student;

        private TextView firstname;
        private TextView lastname;
        private ImageView image;

        public SelectStudentViewHolder(LayoutInflater li, ViewGroup parent)
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

            try
            {
                Bitmap bitmap = BitmapFactory.decodeByteArray(student.getImage(), 0, student.getImage().length);
                image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 100, false));
            }
            catch (Exception e)
            {

            }
        }
    }

    public class SelectStudentAdapter extends RecyclerView.Adapter<SelectStudentViewHolder>
    {
        @Override
        public SelectStudentViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new SelectStudentViewHolder(LayoutInflater.from(SelectStudentActivity.this), container);
        }

        @Override
        public void onBindViewHolder(SelectStudentViewHolder vh, int position)
        {
            Student student = list.get(position);

            vh.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    startActivity(TestActivity.getIntent(SelectStudentActivity.this, student));
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
