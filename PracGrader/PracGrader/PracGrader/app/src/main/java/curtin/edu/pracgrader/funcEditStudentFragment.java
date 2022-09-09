package curtin.edu.pracgrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class funcEditStudentFragment extends Fragment
{
    private UserList userList;

    private EditText name;
    private EditText email;
    private EditText username;
    private EditText digitOne;
    private EditText digitTwo;
    private EditText digitThree;
    private EditText digitFour;
    private Spinner countrySpinner;
    private Button addBtn;
    private Button backBtn;

    private RecyclerView recycler;
    private funcEditStudentFragment.editStudentAdapter adapter;
    private LinearLayoutManager lm;

    private String curUsrUsername;

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
        View view = li.inflate(R.layout.fragment_op_edit_students, parent, false);

        curUsrUsername = getArguments().getString("username");
        User user = new User();

        name = view.findViewById(R.id.editStName);
        email = view.findViewById(R.id.editStEmail);
        username = view.findViewById(R.id.editStUsername);
        digitOne = view.findViewById(R.id.editStPinOne);
        digitTwo = view.findViewById(R.id.editStPinTwo);
        digitThree = view.findViewById(R.id.editStPinThree);
        digitFour = view.findViewById(R.id.editStPinFour);
        countrySpinner = view.findViewById(R.id.spinnerStCountry);
        addBtn = view.findViewById(R.id.btnStAdd);
        backBtn = view.findViewById(R.id.btnEditSt);

        recycler = view.findViewById(R.id.recyclerStEdit);

        // SPINNER DECLARATIONS
        CountryInitalize newCountry = new CountryInitalize();
        String[] countryNames = newCountry.getNames();
        int[] countryImages = newCountry.getImages();

        CountryAdapter ca = new CountryAdapter(getActivity(), countryNames, countryImages);
        countrySpinner.setAdapter(ca);

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                user.setCountry(((TextView) view.findViewById(R.id.flagName)).getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                user.setCountry("Andorra");
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(name.getText().toString().trim().length() == 0
                        || email.getText().toString().trim().length() == 0
                        || username.getText().toString().trim().length() == 0
                        || digitOne.getText().toString().trim().length() == 0
                        || digitTwo.getText().toString().trim().length() == 0
                        || digitThree.getText().toString().trim().length() == 0
                        || digitFour.getText().toString().trim().length() == 0)
                {
                    Toast toast = Toast.makeText(getActivity(), "Null fields detected, please fill every field", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(userList.hasUsername(String.valueOf(username.getText())))
                {
                    Toast toast = Toast.makeText(getActivity(), "Username not available", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    user.setName(String.valueOf(name.getText()));
                    user.setEmail(String.valueOf(email.getText()));
                    user.setUsername(String.valueOf(username.getText()));

                    String pin = String.valueOf(digitOne.getText())
                            + String.valueOf(digitTwo.getText())
                            + String.valueOf(digitThree.getText())
                            + String.valueOf(digitFour.getText());

                    user.setPin(pin);
                    user.setType("Student");
                    user.setAddedBy(curUsrUsername);

                    clear();

                    int insertPosition = userList.add(user);

                    adapter.notifyItemInserted(insertPosition);
                    lm.scrollToPosition(insertPosition);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(OptionsActivity.getIntent(getActivity(), curUsrUsername));
            }
        });

        // SET ADAPTER
        adapter = new editStudentAdapter();
        lm = new LinearLayoutManager(getActivity());

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(lm);

        return view;
    }

    public void clear()
    {
        name.setText("");
        email.setText("");
        username.setText("");
        digitOne.setText("");
        digitTwo.setText("");
        digitThree.setText("");
        digitFour.setText("");
    }

    private class editStudentViewHolder extends RecyclerView.ViewHolder
    {
        private User user;
        private TextView textInUsername;
        private Button btnInEdit;
        private Button btnInDelete;

        public editStudentViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.aed_entry, parent, false));

            textInUsername = (TextView) itemView.findViewById(R.id.aedUsername);
            btnInEdit = (Button) itemView.findViewById(R.id.btnREdit);
            btnInDelete = (Button) itemView.findViewById(R.id.btnRDel);

            btnInEdit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    startActivity((EditEntryActivity.getIntent(getActivity(), String.valueOf(textInUsername.getText()), curUsrUsername)));
                    adapter.notifyItemChanged(getAdapterPosition());
                }
            });

            btnInDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    userList.remove(user);
                    adapter.notifyItemRemoved(getAdapterPosition());
                }
            });
        }

        public void bind(User user)
        {
            this.user = user;

            textInUsername.setText(user.getUsername());
        }
    }

    public class editStudentAdapter extends RecyclerView.Adapter<editStudentViewHolder>
    {
        @Override
        public editStudentViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new editStudentViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(editStudentViewHolder vh, int position)
        {
            if(curUsrUsername.equals("admin"))
            {
                User allUsr = userList.getStudent(position);

                if(allUsr.getType().equals("Student"))
                {
                    vh.bind(allUsr);
                }
            }
            else
            {
                User someUsr = userList.getStudent(position + 1);

                if(someUsr.getAddedBy().equals(curUsrUsername))
                {
                    vh.bind(someUsr);
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
}
