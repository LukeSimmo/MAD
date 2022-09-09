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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class funcEditPracticalsFragment extends Fragment
{
    private PracticalList pracList;
    private StudentMarksList marksList;
    private UserList userList;

    private EditText name;
    private EditText description;
    private EditText mark;
    private Button add;
    private Button back;

    private RecyclerView recycler;
    private editPracticalAdapter adapter;
    private LinearLayoutManager lm;

    private String curUsrUsername;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);

        pracList = new PracticalList();
        pracList.load(getActivity());

        marksList = new StudentMarksList();
        marksList.load(getActivity());

        userList = new UserList();
        userList.load(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b)
    {
        View view = li.inflate(R.layout.fragment_op_edit_practicals, parent, false);

        name = view.findViewById(R.id.editPracticalNameEdit);
        description = view.findViewById(R.id.editPracticalDescEdit);
        mark = view.findViewById(R.id.editPracticalMarkEdit);
        add = view.findViewById(R.id.btnPracticalAdd);
        back = view.findViewById(R.id.btnPracticalBack);

        recycler = view.findViewById(R.id.addPracticalRecycler);

        curUsrUsername = getArguments().getString("username");
        Practical prac = new Practical();

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(name.getText().toString().trim().length() == 0
                || description.getText().toString().trim().length() == 0
                || mark.getText().toString().trim().length() == 0)
                {
                    Toast toast = Toast.makeText(getActivity(), "Null fields detected, please fill every field", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(pracList.hasPractical(String.valueOf(name.getText())))
                {
                    Toast toast = Toast.makeText(getActivity(), "Practical name not available", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    prac.setName(String.valueOf(name.getText()));
                    prac.setDesc(String.valueOf(description.getText()));
                    prac.setMark(Double.parseDouble(String.valueOf(mark.getText())));

                    clear();

                    // ADDING PRACTICAL FOR EVERY STUDENT
                    List<User> students = userList.getAllStudent();

                    for(int i = 0; i < students.size(); i++)
                    {
                        Mark mark = new Mark();

                        mark.setUsername(students.get(i).getUsername());
                        mark.setPracName(String.valueOf(name.getText()));

                        marksList.add(mark);
                    }


                    int insertPosition = pracList.add(prac);

                    adapter.notifyItemInserted(insertPosition);
                    lm.scrollToPosition(insertPosition);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(OptionsActivity.getIntent(getActivity(), curUsrUsername));
            }
        });

        // SET ADAPTER
        adapter = new editPracticalAdapter();
        lm = new LinearLayoutManager(getActivity());

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(lm);

        return view;
    }

    public void clear()
    {
        name.setText("");
        description.setText("");
        mark.setText("");
    }

    private class editPracticalViewHolder extends RecyclerView.ViewHolder
    {
        private Practical prac;
        private TextView pracName;
        private Button edit;
        private Button del;

        public editPracticalViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.prac_aed_entry, parent, false));

            pracName = (TextView) itemView.findViewById(R.id.aedPracName);
            edit = (Button) itemView.findViewById(R.id.aedBtnEdit);
            del = (Button) itemView.findViewById(R.id.aedBtnDel);

            edit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    startActivity(EditPracticalActivity.getIntent(getActivity(), String.valueOf(pracName.getText()), curUsrUsername));
                    adapter.notifyItemChanged(getAdapterPosition());
                }
            });

            del.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    pracList.remove(prac);
                    adapter.notifyItemRemoved(getAdapterPosition());
                }
            });

        }

        public void bind(Practical prac)
        {
            this.prac = prac;

            pracName.setText(prac.getName());
        }
    }

    public class editPracticalAdapter extends RecyclerView.Adapter<editPracticalViewHolder>
    {
        @Override
        public editPracticalViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new editPracticalViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(editPracticalViewHolder vh, int position)
        {
            vh.bind(pracList.get(position));
        }

        @Override
        public int getItemCount()
        {
            return pracList.size();
        }
    }
}
