package curtin.edu.pracgrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class funcMarkMarkStudentFragment extends Fragment
{
    private UserList userList;
    private PracticalList practicalList;
    private StudentMarksList studentMarksList;

    private TextView name;
    private TextView pracName;
    private EditText mark;
    private Button add;

    private String curUser;
    private String selectedUser;
    private String selectedPrac;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);

        userList = new UserList();
        userList.load(getActivity());

        practicalList = new PracticalList();
        practicalList.load(getActivity());

        studentMarksList = new StudentMarksList();
        studentMarksList.load(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b)
    {
        View view = li.inflate(R.layout.fragment_mark_student_result, parent, false);

        curUser = getArguments().getString("username");
        selectedUser = getArguments().getString("selectedUser");
        selectedPrac = getArguments().getString("selectedPrac");

        name = view.findViewById(R.id.textResultUsername);
        pracName = view.findViewById(R.id.textResultPracname);
        mark = view.findViewById(R.id.editResultMark);
        add = view.findViewById(R.id.btnResultAdd);

        name.setText(selectedUser);
        pracName.setText(selectedPrac);

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Mark newMark = new Mark();

                if(mark.getText().toString().trim().length() == 0)
                {
                    Toast toast = Toast.makeText(getActivity(), "Please fill the 'mark' field", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    if(Double.parseDouble(mark.getText().toString()) > practicalList.getPracticalMark(selectedPrac))
                    {
                        mark.setText(String.valueOf(practicalList.getPracticalMark(selectedPrac)));
                    }

                    newMark.setUsername(selectedUser);
                    newMark.setPracName(selectedPrac);
                    newMark.setMark(Double.parseDouble(mark.getText().toString()));

                    studentMarksList.add(newMark);

                    mark.setText("");

                    Toast toast = Toast.makeText(getActivity(), "Mark added to student", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return view;
    }
}
