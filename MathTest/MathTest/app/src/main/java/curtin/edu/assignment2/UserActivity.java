package curtin.edu.assignment2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.assignment2.R;

public class UserActivity extends AppCompatActivity
{
    // CONSTANTS
    private static final int TEACHER_FRAGMENT = 0;
    private static final int STUDENT_FRAGMENT = 1;

    private TextView activityTitle;
    private Button btnHome;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_user_view);

        // DECLARE VARIABLES
        int selection = getIntent().getIntExtra("selected", 2);

        // SET UI ELEMENTS
        activityTitle = findViewById(R.id.labelUserViewTitle);
        btnHome = findViewById(R.id.btnUserViewHome);

        FragmentManager fm = getSupportFragmentManager();

        switch(selection)
        {
            case TEACHER_FRAGMENT:

                activityTitle.setText("Teacher Functions");

                 FragmentTeacherFunctions tFrag = (FragmentTeacherFunctions) fm.findFragmentById(R.id.fragmentUserView);
                 if(tFrag == null)
                 {
                     tFrag = new FragmentTeacherFunctions();
                     fm.beginTransaction()
                             .add(R.id.fragmentUserView, tFrag)
                             .commit();
                 }

                break;
            case STUDENT_FRAGMENT:

                activityTitle.setText("Student Functions");

                FragmentStudentFunctions sFrag = (FragmentStudentFunctions) fm.findFragmentById(R.id.fragmentUserView);
                if(sFrag == null)
                {
                    sFrag = new FragmentStudentFunctions();
                    fm.beginTransaction()
                            .add(R.id.fragmentUserView, sFrag)
                            .commit();
                }

                break;
            default:
                Toast.makeText(UserActivity.this, "Invalid user selected, go back and try again", Toast.LENGTH_SHORT).show();
        }

        btnHome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(UserActivity.this, MainActivity.class));
            }
        });
    }

    public static Intent getIntent(Context c, int selected)
    {
        Intent intent = new Intent(c, UserActivity.class);
        intent.putExtra("selected", selected);
        return intent;
    }
}
