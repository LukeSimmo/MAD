package curtin.edu.pracgrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class funcViewInstructorFragment extends Fragment
{
    private UserList userList;

    private String curUser;

    private Button back;
    private RecyclerView recycler;

    private funcViewInstructorFragment.viewInstructorAdapter adapter;
    private LinearLayoutManager lm;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);

        userList = new UserList();
        userList.load(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b)
    {
        View view = li.inflate(R.layout.fragment_op_view_instructors, parent, false);

        recycler = view.findViewById(R.id.recyclerInstructorView);
        back = view.findViewById(R.id.btnInViewBack);

        curUser = getArguments().getString("username");

        // SET ADAPTER
        adapter = new viewInstructorAdapter();
        lm = new LinearLayoutManager(getActivity());

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(lm);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(OptionsActivity.getIntent(getActivity(), curUser));
            }
        });


        return view;
    }

    private class viewInstructorViewHolder extends RecyclerView.ViewHolder
    {
        private User user;

        private TextView name;
        private TextView username;

        public viewInstructorViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.entry_instructor_view, parent, false));

            name = (TextView) itemView.findViewById(R.id.textEntryInName);
            username = (TextView) itemView.findViewById(R.id.textEntryInUsername);
        }

        public void bind(User user)
        {
            this.user = user;

            name.setText(user.getName());
            username.setText(user.getName());
        }
    }

    public class viewInstructorAdapter extends RecyclerView.Adapter<viewInstructorViewHolder>
    {

        @Override
        public viewInstructorViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new viewInstructorViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(viewInstructorViewHolder vh, int position)
        {
            User user = userList.getInstructor(position);

            vh.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    startActivity(viewInstructorDetailsActivity.getIntent(getActivity(), user.getUsername() , curUser));
                }
            });

            vh.bind(user);
        }

        @Override
        public int getItemCount()
        {
            return userList.getTypeCount("Instructor");
        }
    }
}
