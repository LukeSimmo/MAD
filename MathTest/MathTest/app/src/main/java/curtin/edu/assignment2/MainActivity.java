package curtin.edu.assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.assignment2.R;

public class MainActivity extends AppCompatActivity
{
    private Button btnTeacher;
    private Button btnStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Math Test"); // set title for app

        btnTeacher = findViewById(R.id.btnMainTeacher);
        btnStudent = findViewById(R.id.btnMainStudent);

        btnTeacher.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(UserActivity.getIntent(MainActivity.this, 0));
            }
        });

        btnStudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(UserActivity.getIntent(MainActivity.this, 1));
            }
        });
    }
}