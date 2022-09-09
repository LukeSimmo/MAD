package curtin.edu.assignment2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.assignment2.R;

public class FragmentTeacherFunctions extends Fragment
{
    // UI ITEMS
    private Button btnRegisterStudent;
    private Button btnViewStudent;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b)
    {
        View view = li.inflate(R.layout.fragment_teacher_functions, parent, false);

        // VARAIBLE DECLARATIONS


        // SET UI ELEMENTS
        btnRegisterStudent = view.findViewById(R.id.btnTeacherFunctrionsAddStudent);
        btnViewStudent = view.findViewById(R.id.btnTeacherFunctionsViewStudents);

        btnRegisterStudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getActivity(), RegisterStudentActivity.class));
            }
        });

        btnViewStudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(getActivity(), ViewStudentActivity.class));
            }
        });

        return view;
    }
}
