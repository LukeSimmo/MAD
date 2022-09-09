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

public class viewInstructorDetailsActivity extends AppCompatActivity
{
    private UserList userList;

    private EditText name;
    private EditText email;
    private EditText usr;
    private EditText pinOne;
    private EditText pinTwo;
    private EditText pinThree;
    private EditText pinFour;
    private Spinner country;
    private Button confirm;
    private Button back;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_view_instructor_details);

        userList = new UserList();
        userList.load(getApplicationContext());

        String username = getIntent().getStringExtra("username");
        String curUser = getIntent().getStringExtra("curUser");

        User user = userList.getUser(username);

        name = findViewById(R.id.editTextInDName);
        email = findViewById(R.id.editTextInDEmail);
        usr = findViewById(R.id.editTextInDUsername);
        pinOne = findViewById(R.id.editInDDigitOne);
        pinTwo = findViewById(R.id.editInDDigitTwo);
        pinThree = findViewById(R.id.editInDDigitThree);
        pinFour = findViewById(R.id.editInDDigitFour);
        country = findViewById(R.id.spinnerInD);
        confirm = findViewById(R.id.btnInDConfirm);
        back = findViewById(R.id.btnInDBack);

        name.setText(user.getName());
        email.setText(user.getEmail());
        usr.setText(user.getUsername());
        pinOne.setText(String.valueOf(user.getPin().charAt(0)));
        pinTwo.setText(String.valueOf(user.getPin().charAt(1)));
        pinThree.setText(String.valueOf(user.getPin().charAt(2)));
        pinFour.setText(String.valueOf(user.getPin().charAt(3)));

        String ogCountry = user.getCountry();

        CountryInitalize newCountry = new CountryInitalize();
        String[] countryNames = newCountry.getNames();
        int[] countryImages = newCountry.getImages();

        CountryAdapter ca = new CountryAdapter(getApplicationContext(), countryNames, countryImages);
        country.setAdapter(ca);

        country.setSelection(newCountry.getNamePosition(user.getCountry()));

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user.setCountry(((TextView) findViewById(R.id.flagName)).getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                user.setCountry(ogCountry);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.getText().toString().trim().length() == 0
                        || email.getText().toString().trim().length() == 0
                        || usr.getText().toString().trim().length() == 0
                        || pinOne.getText().toString().trim().length() == 0
                        || pinTwo.getText().toString().trim().length() == 0
                        || pinThree.getText().toString().trim().length() == 0
                        || pinFour.getText().toString().trim().length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Null fields detected, please fill every field", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    user.setName(String.valueOf(name.getText()));
                    user.setEmail(String.valueOf(email.getText()));
                    user.setUsername(String.valueOf(usr.getText()));

                    String pin = String.valueOf(pinOne.getText())
                            + String.valueOf(pinTwo.getText())
                            + String.valueOf(pinThree.getText())
                            + String.valueOf(pinFour.getText());

                    user.setPin(pin);
                    user.setType("Instructor");
                    user.setAddedBy("admin");


                    userList.edit(user);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(OptionsActivity.getIntent(getApplicationContext(), curUser));
            }
        });
    }

    public static Intent getIntent(Context c, String  username, String user)
    {
        Intent intent = new Intent(c, viewInstructorDetailsActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("curUser", user);
        return intent;
    }
}
