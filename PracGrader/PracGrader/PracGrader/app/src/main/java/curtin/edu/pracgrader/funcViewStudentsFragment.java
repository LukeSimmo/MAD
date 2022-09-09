package curtin.edu.pracgrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class funcViewStudentsFragment extends Fragment
{
    private UserList userList;
    private StudentMarksList studentMarksList;
    private PracticalList practicalList;

    private String curUser;

    private RecyclerView recycler;
    private Button back;

    private funcViewStudentsFragment.viewStudentAdapter adapter;
    private LinearLayoutManager lm;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);

        userList = new UserList();
        userList.load(getActivity());

        studentMarksList = new StudentMarksList();
        studentMarksList.load(getActivity());

        practicalList = new PracticalList();
        practicalList.load(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b)
    {
        View view = li.inflate(R.layout.fragment_op_view_students, parent, false);

        recycler = view.findViewById(R.id.recyclerStView);
        back = view.findViewById(R.id.btnViewStBack);

        curUser = getArguments().getString("username");

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(OptionsActivity.getIntent(getActivity(), curUser));
            }
        });

        // SET ADAPTER
        adapter = new viewStudentAdapter();
        lm = new LinearLayoutManager(getActivity());

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(lm);

        return view;
    }

    private class viewStudentViewHolder extends RecyclerView.ViewHolder
    {
        private User user;
        private Mark markObj;

        private TextView name;
        private TextView mark;
        private ImageView flag;

        public viewStudentViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.entry_student_view, parent, false));

            name = (TextView) itemView.findViewById(R.id.textViewStName);
            mark = (TextView) itemView.findViewById(R.id.textViewStMark);
            flag = (ImageView) itemView.findViewById(R.id.imgStFlag);
        }

        public void bind(User user, Mark markObj)
        {
            this.user = user;
            this.markObj = markObj;

            Practical tempPrac = practicalList.getPractical(markObj.getPracName());

            CountryInitalize country = new CountryInitalize();

            name.setText(user.getName());
            mark.setText(String.format("%.2f" ,markObj.getMark() / tempPrac.getMark() * 100));
            flag.setImageResource(country.getImage(user.getCountry()));


        }
    }

    public class viewStudentAdapter extends RecyclerView.Adapter<viewStudentViewHolder>
    {

        @Override
        public viewStudentViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new viewStudentViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(viewStudentViewHolder vh, int position)
        {
            User user = userList.getStudent(position);

            if(curUser.equals("admin"))
            {
                User allUsr = userList.getStudent(position);
                Mark temp = studentMarksList.getMark(allUsr.getUsername());

                if(allUsr.getType().equals("Student"))
                {
                    vh.bind(allUsr, temp);
                }
            }
            else
            {
                User someUsr = userList.getStudent(position + 1);
                Mark temp = studentMarksList.getMark(someUsr.getUsername());

                if(someUsr.getAddedBy().equals(curUser))
                {
                    vh.bind(someUsr, temp);
                }
            }
        }

        @Override
        public int getItemCount()
        {
            if(curUser.equals("admin"))
            {
                return userList.getTypeCount("Student");
            }
            else
            {
                return userList.getStudentAddedCount(curUser);
            }
        }
    }
}
