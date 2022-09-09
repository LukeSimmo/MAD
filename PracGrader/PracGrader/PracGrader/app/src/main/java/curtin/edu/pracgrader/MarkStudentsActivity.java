package curtin.edu.pracgrader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MarkStudentsActivity extends AppCompatActivity
{
    private UserList userList;
    private String selectedUsername;
    private String curUsrUsername;
    private String selectedPrac;

    private Button back;

    private RecyclerView recycler;
    private MarkStudentsActivity.viewStudentAdapter adapter;
    private LinearLayoutManager lm;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_op_mark_students);

        userList = new UserList();
        userList.load(getApplicationContext());

        curUsrUsername = getIntent().getStringExtra("username");
        selectedPrac = getIntent().getStringExtra("pracName");
        selectedUsername = getIntent().getStringExtra("selectedUser");

        // UI ELEMENTS
        recycler = findViewById(R.id.studentMarkRecycler);
        back = findViewById(R.id.btnMarkStudentBack);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(OptionsActivity.getIntent(getApplicationContext(), curUsrUsername));
            }
        });

        // SET ADAPTER
        adapter = new viewStudentAdapter();
        lm = new LinearLayoutManager(getApplicationContext());

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(lm);
    }

    private class viewStudentsViewHolder extends RecyclerView.ViewHolder
    {
        private User user;

        private String selected;

        private TextView name;
        private TextView username;
        private Button select;

        public viewStudentsViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.mark_student_entry, parent, false));

            name = (TextView) itemView.findViewById(R.id.textMarkStudentName);
            username = (TextView) itemView.findViewById(R.id.textMarkStudentUsername);
            select = (Button) itemView.findViewById(R.id.btnMarkStudentSelect);

            select.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    selected = String.valueOf(username.getText());

                    if(selectedPrac == null)
                    {

                        Bundle bundle = new Bundle();
                        bundle.putString("username", curUsrUsername);
                        bundle.putString("selectedUser", selected);

                        FragmentManager fm = getSupportFragmentManager();
                        funcMarkStudentsFragment frag = (funcMarkStudentsFragment) fm.findFragmentById(R.id.markFragment);
                        if(frag == null)
                        {
                            frag = new funcMarkStudentsFragment();
                            frag.setArguments(bundle);
                            fm.beginTransaction()
                                    .add(R.id.markFragment, frag)
                                    .commit();
                        }
                    }

                }
            });

            if(selectedPrac != null)
            {
                Bundle bundle = new Bundle();

                bundle.putString("username", curUsrUsername);
                bundle.putString("selectedPrac", selectedPrac);
                bundle.putString("selectedUser", selectedUsername);

                FragmentManager fm = getSupportFragmentManager();
                funcMarkMarkStudentFragment frag = (funcMarkMarkStudentFragment) fm.findFragmentById(R.id.markFragment);
                if(frag == null)
                {
                    frag = new funcMarkMarkStudentFragment();
                    frag.setArguments(bundle);
                    fm.beginTransaction()
                            .add(R.id.markFragment, frag)
                            .commit();
                }

            }

        }

        public void bind(User user)
        {
            this.user = user;

            name.setText(user.getName());
            username.setText(user.getUsername());
        }
    }

    public class viewStudentAdapter extends RecyclerView.Adapter<viewStudentsViewHolder>
    {
        @Override
        public viewStudentsViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return  new viewStudentsViewHolder(LayoutInflater.from(getApplicationContext()), container);
        }

        @Override
        public void onBindViewHolder(viewStudentsViewHolder vh, int position)
        {
            if(curUsrUsername.equals("admin"))
            {
                User allUser = userList.getStudent(position);

                vh.bind(allUser);
            }
            else
            {
                User someUser = userList.getStudent(position + 1);

                if(someUser.getAddedBy().equals(curUsrUsername))
                {
                    vh.bind(someUser);
                }
            }
        }

        @Override
        public int getItemCount()
        {
            if(curUsrUsername.equals("admin"))
            {
                return userList.getTypeCount("Student");
            }
            else
            {
                return userList.getStudentAddedCount(curUsrUsername);
            }
        }
    }

    public static Intent getIntent(Context c, String username, String pracName, String selectedUser)
    {
        Intent intent = new Intent(c, MarkStudentsActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("pracName", pracName);
        intent.putExtra("selectedUser", selectedUser);
        return intent;
    }
}
