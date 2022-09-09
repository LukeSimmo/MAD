package curtin.edu.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.assignment2.R;

public class FragmentStudentFunctions extends Fragment
{
    // UI ITEMS
    private Button btnStartTest;
    private Button btnViewMarks;


    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b)
    {
        View view = li.inflate(R.layout.fragment_student_functions, parent, false);

        // VARIABLE DECLARATIONS

        // SET UI ELEMENTS
        btnStartTest = view.findViewById(R.id.btnStudentFunctionsStartTest);
        btnViewMarks = view.findViewById(R.id.btnStudentFunctionsViewMarks);

        btnStartTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getActivity(), SelectStudentActivity.class));
            }
        });

        btnViewMarks.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getActivity(), ViewResults.class));
            }
        });

        return view;
    }
}
