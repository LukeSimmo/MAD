package curtin.edu.pracgrader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class optionsDisplayActivity extends AppCompatActivity
{
    private TextView pageTitle;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_display_options);

        pageTitle = findViewById(R.id.displayOptionsTitle);

        String username = getIntent().getStringExtra("username");
        int option = getIntent().getIntExtra("fragOption", 0);

        fm = getSupportFragmentManager();

        switch(option)
        {
            case 1:
                pageTitle.setText("Add / Edit / Delete Instructors");

                Bundle bundleOne = new Bundle();
                bundleOne.putString("username", username);

                funcEditInstructorFragment editInFrag = (funcEditInstructorFragment) fm.findFragmentById(R.id.optionsDisplayFragment);
                if(editInFrag == null)
                {
                    editInFrag = new funcEditInstructorFragment();
                    editInFrag.setArguments(bundleOne);
                    fm.beginTransaction()
                            .add(R.id.optionsDisplayFragment, editInFrag)
                            .commit();
                }
                break;
            case 2:
                pageTitle.setText("Add / Edit / Delete Students");

                Bundle bundleTwo = new Bundle();
                bundleTwo.putString("username", username);

                funcEditStudentFragment editStFrag = (funcEditStudentFragment) fm.findFragmentById(R.id.optionsDisplayFragment);
                if(editStFrag == null)
                {
                    editStFrag = new funcEditStudentFragment();
                    editStFrag.setArguments(bundleTwo);
                    fm.beginTransaction()
                            .add(R.id.optionsDisplayFragment, editStFrag)
                            .commit();
                }
                break;
            case 3:
                pageTitle.setText("Add / Edit / Delete Practicals");

                Bundle bundleThree = new Bundle();
                bundleThree.putString("username", username);

                funcEditPracticalsFragment editPrFrag = (funcEditPracticalsFragment) fm.findFragmentById(R.id.optionsDisplayFragment);
                if(editPrFrag == null)
                {
                    editPrFrag = new funcEditPracticalsFragment();
                    editPrFrag.setArguments(bundleThree);
                    fm.beginTransaction()
                            .add(R.id.optionsDisplayFragment, editPrFrag)
                            .commit();
                }
                break;
            case 4:
                startActivity(MarkStudentsActivity.getIntent(getApplicationContext(), username, null, null));
                break;
            case 5:
                pageTitle.setText("View Instructors");

                Bundle bundleFour = new Bundle();
                bundleFour.putString("username", username);

                funcViewInstructorFragment viewInFrag = (funcViewInstructorFragment) fm.findFragmentById(R.id.optionsDisplayFragment);
                if(viewInFrag == null)
                {
                    viewInFrag = new funcViewInstructorFragment();
                    viewInFrag.setArguments(bundleFour);
                    fm.beginTransaction()
                            .add(R.id.optionsDisplayFragment, viewInFrag)
                            .commit();
                }
                break;
            case 6:
                pageTitle.setText("View Students");

                Bundle bundleFive = new Bundle();
                bundleFive.putString("username", username);

                funcViewStudentsFragment viewStFrag = (funcViewStudentsFragment) fm.findFragmentById(R.id.optionsDisplayFragment);
                if(viewStFrag == null)
                {
                    viewStFrag = new funcViewStudentsFragment();
                    viewStFrag.setArguments(bundleFive);
                    fm.beginTransaction()
                            .add(R.id.optionsDisplayFragment, viewStFrag)
                            .commit();
                }

                break;
            case 7:
                pageTitle.setText("View Practicals");

                Bundle bundleSix = new Bundle();
                bundleSix.putString("username", username);

                funcViewPracticalsFragment viewPrFrag = (funcViewPracticalsFragment) fm.findFragmentById(R.id.optionsDisplayFragment);
                if(viewPrFrag == null)
                {
                    viewPrFrag = new funcViewPracticalsFragment();
                    viewPrFrag.setArguments(bundleSix);
                    fm.beginTransaction()
                            .add(R.id.optionsDisplayFragment, viewPrFrag)
                            .commit();
                }
                break;
            default:

        }
    }

    public static Intent getIntent(Context c, String username, int option)
    {
        Intent intent = new Intent(c, optionsDisplayActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("fragOption", option);
        return intent;
    }
}
