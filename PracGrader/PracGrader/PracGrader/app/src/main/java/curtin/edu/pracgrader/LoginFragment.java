package curtin.edu.pracgrader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment
{
    // DECLARATIONS
    UserList userList;

    // UI DECLARATIONS
    private EditText username;
    private EditText digit1;
    private EditText digit2;
    private EditText digit3;
    private EditText digit4;
    private Button loginBtn;

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
        View view = li.inflate(R.layout.fragment_login, parent, false);

        // ASSIGN UI
        username = (EditText) view.findViewById(R.id.enterUsername);
        digit1 = (EditText) view.findViewById(R.id.loginDigit1);
        digit2 = (EditText) view.findViewById(R.id.loginDigit2);
        digit3 = (EditText) view.findViewById(R.id.loginDigit3);
        digit4 = (EditText) view.findViewById(R.id.loginDigit4);
        loginBtn = (Button) view.findViewById(R.id.btnLogin);

        loginBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String pin = String.valueOf(digit1.getText()) + String.valueOf(digit2.getText())
                        + String.valueOf(digit3.getText()) + String.valueOf(digit4.getText());

                if(userList.hasUsername(String.valueOf(username.getText())))
                {
                    if(userList.getUser(String.valueOf(username.getText())).getPin().equals(pin))
                    {
                        // Intent intent = new Intent(getActivity(), OptionsActivity.class);
                        startActivity(OptionsActivity.getIntent(getActivity(), String.valueOf(username.getText())));
                    }
                    else
                    {
                        Toast toast = Toast.makeText(getActivity(), "Incorrect pin for associated username, try again", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else
                {
                    Toast toast = Toast.makeText(getActivity(), "Username not found, re-enter and try again", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


        return view;
    }
}
