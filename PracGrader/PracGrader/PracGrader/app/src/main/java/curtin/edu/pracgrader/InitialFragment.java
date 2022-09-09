package curtin.edu.pracgrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class InitialFragment extends Fragment
{
    private UserList userList;

    // UI DECLARATIONS
    private EditText inEtUsername;
    private EditText inDigit1;
    private EditText inDigit2;
    private EditText inDigit3;
    private EditText inDigit4;
    private Button btnCreateAdmin;
    private Button btnVerify;
    private Button btnLogin;


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
        View view = li.inflate(R.layout.fragment_initial, parent, false);

        inEtUsername = (EditText) view.findViewById(R.id.etUsername);
        inDigit1 = (EditText) view.findViewById(R.id.etDigit1);
        inDigit2 = (EditText) view.findViewById(R.id.etDigit2);
        inDigit3 = (EditText) view.findViewById(R.id.etDigit3);
        inDigit4 = (EditText) view.findViewById(R.id.etDigit4);
        btnCreateAdmin = (Button) view.findViewById(R.id.btnCreateAdmin);
        btnVerify = (Button) view.findViewById(R.id.btnVerify);
        btnLogin = (Button) view.findViewById(R.id.loginBtn);

        //setting verify to invisible to hide while not needed
        btnVerify.setVisibility(View.INVISIBLE);
        btnLogin.setVisibility(View.INVISIBLE);

        btnCreateAdmin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(userList.hasUsername(String.valueOf(inEtUsername.getText())))
                {
                    Toast toast = Toast.makeText(getActivity(), "Invalid username", Toast.LENGTH_SHORT);
                    toast.show();
                    inEtUsername.setText("");
                }
                else
                {
                    String firstPin = String.valueOf(inDigit1.getText()) + String.valueOf(inDigit2.getText())
                            + String.valueOf(inDigit3.getText()) + String.valueOf(inDigit4.getText());

                    // CLEAR PIN SO IT CAN BE VERIFIED
                    inDigit1.setText("");
                    inDigit2.setText("");
                    inDigit3.setText("");
                    inDigit4.setText("");

                    Toast toast = Toast.makeText(getActivity(), "Please verify pin", Toast.LENGTH_SHORT);
                    toast.show();

                    btnVerify.setVisibility(View.VISIBLE);

                    btnVerify.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            String secondPin = String.valueOf(inDigit1.getText()) + String.valueOf(inDigit2.getText())
                                    + String.valueOf(inDigit3.getText()) + String.valueOf(inDigit4.getText());

                            if(secondPin.equals(firstPin)) {
                                int selection = userList.add(new User(String.valueOf(inEtUsername.getText()), null, null, secondPin, null, "admin"));

                                Toast toast = Toast.makeText(getActivity(), "admin account created", Toast.LENGTH_SHORT);
                                toast.show();

                                inDigit1.setText("");
                                inDigit2.setText("");
                                inDigit3.setText("");
                                inDigit4.setText("");
                                inEtUsername.setText("");

                                btnCreateAdmin.setVisibility(View.INVISIBLE);
                                btnLogin.setVisibility(View.VISIBLE);

                                btnLogin.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                                        ft.replace(R.id.loginFragment, new LoginFragment());
                                        ft.commit();
                                    }
                                });
                            }
                            else
                            {
                                Toast toast = Toast.makeText(getActivity(), "pins do not match, try again", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    });
                }
            }
        });

        return view;
    }
}
