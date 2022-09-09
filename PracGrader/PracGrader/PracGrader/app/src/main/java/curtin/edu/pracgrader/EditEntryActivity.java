package curtin.edu.pracgrader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class EditEntryActivity extends AppCompatActivity
{
    private UserList userList;
    private User user;

    private EditText name;
    private EditText email;
    private EditText username;
    private EditText digitOne;
    private EditText digitTwo;
    private EditText digitThree;
    private EditText digitFour;
    private Spinner countrySpinner;
    private Button btnConfirm;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_edit_user);

        userList = new UserList();
        userList.load(getApplicationContext());

        String editUsr = getIntent().getStringExtra("username");
        String addingUser = getIntent().getStringExtra("addingUser");

        user = userList.getUser(editUsr);

        // ASSIGNING UI ELEMENTS
        name = findViewById(R.id.editEName);
        email = findViewById(R.id.editEEmail);
        username = findViewById(R.id.editEUsername);
        digitOne = findViewById(R.id.editEDigitOne);
        digitTwo = findViewById(R.id.editEDigitTwo);
        digitThree = findViewById(R.id.editEDigitThree);
        digitFour = findViewById(R.id.editEDigitFour);
        countrySpinner = findViewById(R.id.editCountrySpinner);

        btnConfirm = findViewById(R.id.btnEditConfirm);
        btnBack = findViewById(R.id.btnEditBack);

        // VARIABLE DECLARATIONS
        String ogCountry = user.getCountry();

        // SETTING EDIT TEXT'S TO CURRENT VALUES
        name.setText(user.getName());
        email.setText(user.getEmail());
        username.setText(user.getUsername());
        digitOne.setText(String.valueOf(user.getPin().charAt(0)));
        digitTwo.setText(String.valueOf(user.getPin().charAt(1)));
        digitThree.setText(String.valueOf(user.getPin().charAt(2)));
        digitFour.setText(String.valueOf(user.getPin().charAt(3)));

        // SPINNER SETUP
        CountryInitalize newCountry = new CountryInitalize();
        String[] countryNames = newCountry.getNames();
        int[] countryImages = newCountry.getImages();

        CountryAdapter ca = new CountryAdapter(getApplicationContext(), countryNames, countryImages);
        countrySpinner.setAdapter(ca);

        countrySpinner.setSelection(newCountry.getNamePosition(user.getCountry()));

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                user.setCountry(((TextView) findViewById(R.id.flagName)).getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                user.setCountry(ogCountry);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener()
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
                    Toast toast = Toast.makeText(getApplicationContext(), "Null fields detected, please fill every field", Toast.LENGTH_SHORT);
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
                    user.setAddedBy(addingUser);


                    userList.edit(user);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(OptionsActivity.getIntent(getApplicationContext(), addingUser));
            }
        });
    }

    public static Intent getIntent(Context c, String username, String addingUser)
    {
        Intent intent = new Intent(c, EditEntryActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("addingUser", addingUser);
        return intent;
    }
}


