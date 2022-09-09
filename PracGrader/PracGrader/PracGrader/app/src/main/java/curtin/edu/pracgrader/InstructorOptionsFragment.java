package curtin.edu.pracgrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class InstructorOptionsFragment extends Fragment
{
    private Button editStudents;
    private Button markStudents;
    private Button viewStudents;
    private Button searchStudents;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b)
    {
        View view = li.inflate(R.layout.fragment_in_options, parent, false);

        String username = getArguments().getString("username");

        editStudents = view.findViewById(R.id.btnInEditStudents);
        markStudents = view.findViewById(R.id.btnInMarkStudents);
        viewStudents = view.findViewById(R.id.btnInViewStudent);
        searchStudents = view.findViewById(R.id.btnInSearchStudent);

        editStudents.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(optionsDisplayActivity.getIntent(getActivity(), username, 2));
            }
        });

        markStudents.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(optionsDisplayActivity.getIntent(getActivity(), username, 4));
            }
        });

        viewStudents.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(optionsDisplayActivity.getIntent(getActivity(), username, 6));
            }
        });

        searchStudents.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(optionsDisplayActivity.getIntent(getActivity(), username, 8));
            }
        });

        return view;
    }
}
