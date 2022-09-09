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

public class funcEditInstructorFragment extends Fragment
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
    private editInstructorAdapter adapter;
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
        View view = li.inflate(R.layout.fragment_op_edit_instructors, parent, false);

        // VARIABLE DECLARATIONS
        curUsrUsername = getArguments().getString("username"); // variable for returning to admin menu
        User user = new User();


        // ASSIGN UI ELEMENTS TO VARIABLE
        name = view.findViewById(R.id.editInName);
        email = view.findViewById(R.id.editInEmail);
        username = view.findViewById(R.id.editInUsername);
        digitOne = view.findViewById(R.id.editInPinOne);
        digitTwo = view.findViewById(R.id.editInPinTwo);
        digitThree = view.findViewById(R.id.editInPinThree);
        digitFour = view.findViewById(R.id.editInPinFour);
        countrySpinner = view.findViewById(R.id.spinnerInCountry);
        addBtn = view.findViewById(R.id.btnInAdd);
        backBtn = view.findViewById(R.id.btnEditIn);

        recycler = view.findViewById(R.id.recyclerInEdit);


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
                    user.setType("Instructor");
                    user.setAddedBy(curUsrUsername);

                    clear();

                    int insertPosition = userList.add(user);

                    adapter.notifyItemInserted(insertPosition);
                    lm.scrollToPosition(insertPosition);
                }
            }
        });

        // BACK TO ADMIN FUNCTIONS MENU
        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(OptionsActivity.getIntent(getActivity(), curUsrUsername));
            }
        });

        // SET ADAPTER
        adapter = new editInstructorAdapter();
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

    private class editInstructorViewHolder extends RecyclerView.ViewHolder
    {
        private User user;
        private TextView textInUsername;
        private Button btnInEdit;
        private Button btnInDelete;

        public editInstructorViewHolder(LayoutInflater li, ViewGroup parent)
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
                    startActivity(EditEntryActivity.getIntent(getActivity(), String.valueOf(textInUsername.getText()), curUsrUsername));
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

    public class editInstructorAdapter extends RecyclerView.Adapter<editInstructorViewHolder>
    {
        @Override
        public editInstructorViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new editInstructorViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(editInstructorViewHolder vh, int position)
        {
            User usr = userList.getInstructor(position);

            if(usr.getType().equals("Instructor"))
            {
                vh.bind(usr);
            }
        }

        @Override
        public int getItemCount()
        {
            return userList.getTypeCount("Instructor");
        }
    }


}
