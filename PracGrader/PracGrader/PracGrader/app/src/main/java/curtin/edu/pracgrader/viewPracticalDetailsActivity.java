package curtin.edu.pracgrader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class viewPracticalDetailsActivity extends AppCompatActivity
{
    private PracticalList pracList;

    private EditText name;
    private EditText desc;
    private EditText mark;

    private Button confirm;
    private Button back;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_view_practical_details);

        pracList = new PracticalList();
        pracList.load(getApplicationContext());

        String pracName = getIntent().getStringExtra("username");
        String curUser = getIntent().getStringExtra("curUser");

        Practical prac = pracList.getPractical(pracName);

        name = findViewById(R.id.editTextPrName);
        desc = findViewById(R.id.editTextPrDesc);
        mark = findViewById(R.id.editTextPrMark);
        confirm = findViewById(R.id.btnPrConfirm);
        back = findViewById(R.id.btnPrBack);

        name.setText(prac.getName());
        desc.setText(prac.getDesc());
        mark.setText(String.valueOf(prac.getMark()));

        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(name.getText().toString().trim().length() == 0
                || desc.getText().toString().trim().length() == 0
                || mark.getText().toString().trim().length() == 0)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Null fields detected, please fill every field", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    prac.setName(String.valueOf(name.getText()));
                    prac.setDesc(String.valueOf(desc.getText()));
                    prac.setMark(Double.parseDouble(mark.getText().toString()));

                    pracList.edit(prac);
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

    public static Intent getIntent(Context c, String username, String user)
    {
        Intent intent = new Intent(c, viewPracticalDetailsActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("curUser", user);
        return intent;
    }
}
