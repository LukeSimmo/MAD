package curtin.edu.pracgrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class AdminOptionsFragment extends Fragment
{
    private Button editInstructors;
    private Button editStudents;
    private Button editPracticals;
    private Button markStudents;
    private Button viewInstructors;
    private Button viewStudents;
    private Button viewPracticals;
    private Button searchStudents;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b)
    {
        View view = li.inflate(R.layout.fragment_ad_options, parent, false);

        String username = getArguments().getString("username");

        // BTN DECLARATIONS
        editInstructors = view.findViewById(R.id.ad_btnAedInstructors);
        editStudents = view.findViewById(R.id.ad_btnAedStudents);
        editPracticals = view.findViewById(R.id.ad_btnAedPracticals);
        markStudents = view.findViewById(R.id.ad_btnMarkStudents);
        viewInstructors = view.findViewById(R.id.ad_btnViewInstructors);
        viewStudents = view.findViewById(R.id.ad_btnViewStudents);
        viewPracticals = view.findViewById(R.id.ad_btnViewPracticals);
        searchStudents = view.findViewById(R.id.ad_btnSearchStudents);

        editInstructors.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(optionsDisplayActivity.getIntent(getActivity(), username, 1));
            }
        });

        editStudents.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(optionsDisplayActivity.getIntent(getActivity(), username, 2));
            }
        });

        editPracticals.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(optionsDisplayActivity.getIntent(getActivity(), username, 3));
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

        viewInstructors.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(optionsDisplayActivity.getIntent(getActivity(), username, 5));
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

        viewPracticals.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(optionsDisplayActivity.getIntent(getActivity(), username, 7));
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
