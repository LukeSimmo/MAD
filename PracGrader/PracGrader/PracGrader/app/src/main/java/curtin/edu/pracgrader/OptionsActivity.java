package curtin.edu.pracgrader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class OptionsActivity extends AppCompatActivity
{
    private TextView optionsTitle;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_options);

        // DECLARE VARIABLES
        optionsTitle = findViewById(R.id.optionsTitle);

        // GET USERNAME FROM FRAGMENT
        String username = getIntent().getStringExtra("username");

        UserList userList = new UserList();
        userList.load(getApplicationContext());

        User currUser = userList.getUser(username);

        FragmentManager fm = getSupportFragmentManager();

        if(currUser.getType().equals("admin"))
        {
            optionsTitle.setText("Admin Functions");

            Bundle bundleOne = new Bundle();
            bundleOne.putString("username", username);

            AdminOptionsFragment adFrag = (AdminOptionsFragment) fm.findFragmentById(R.id.optionsFragment);
            if(adFrag == null)
            {
                adFrag = new AdminOptionsFragment();
                adFrag.setArguments(bundleOne);
                fm.beginTransaction()
                        .add(R.id.optionsFragment, adFrag)
                        .commit();
            }
        }
        else if(currUser.getType().equals("Instructor"))
        {
            optionsTitle.setText("Instructor Functions");

            Bundle bundleTwo = new Bundle();
            bundleTwo.putString("username", username);

            InstructorOptionsFragment inFrag = (InstructorOptionsFragment) fm.findFragmentById(R.id.optionsFragment);
            if(inFrag == null)
            {
                inFrag = new InstructorOptionsFragment();
                inFrag.setArguments(bundleTwo);
                fm.beginTransaction()
                        .add(R.id.optionsFragment, inFrag)
                        .commit();
            }
        }
        else if(currUser.getType().equals("Student"))
        {
            optionsTitle.setText("Student Functions");

            StudentOptionsFragment stFrag = (StudentOptionsFragment) fm.findFragmentById(R.id.optionsFragment);
            if(stFrag == null)
            {
                stFrag = new StudentOptionsFragment();
                fm.beginTransaction()
                        .add(R.id.optionsFragment, stFrag)
                        .commit();
            }
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(), "user does not match a user type, sign out and try again", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static Intent getIntent(Context c, String username)
    {
        Intent intent = new Intent(c, OptionsActivity.class);
        intent.putExtra("username", username);
        return intent;
    }
}
