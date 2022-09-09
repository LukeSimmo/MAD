package curtin.edu.pracgrader;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditPracticalActivity extends AppCompatActivity
{
    private PracticalList pracList;
    private Practical prac;

    private EditText pracName;
    private EditText desc;
    private EditText mark;
    private Button confirm;
    private Button back;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_edit_practical);

        pracList = new PracticalList();
        pracList.load(getApplicationContext());

        String name = getIntent().getStringExtra("name");
        String user = getIntent().getStringExtra("user");

        prac = pracList.getPractical(name);

        // ASSIGN UI ELEMENTS
        pracName = findViewById(R.id.editPracticalNameEdit);
        desc = findViewById(R.id.editPracticalDescEdit);
        mark = findViewById(R.id.editPracticalMarkEdit);
        confirm = findViewById(R.id.btnPracticalEditConfirm);
        back = findViewById(R.id.btnPracticalEditBack);

        String ogName = prac.getName();

        // SETTING EDIT TEXT TO CURRENT VALUES
        pracName.setText(prac.getName());
        desc.setText(prac.getDesc());
        mark.setText(String.valueOf(prac.getMark()));

        confirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(pracName.getText().toString().trim().length() == 0
                    || desc.getText().toString().trim().length() == 0
                    || mark.getText().toString().trim().length() == 0)
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Null fields detected, please fill every field", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    prac.setName(String.valueOf(pracName.getText()));
                    prac.setDesc(String.valueOf(desc.getText()));
                    prac.setMark(Double.parseDouble(String.valueOf(mark.getText())));

                    pracList.edit(prac);
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(OptionsActivity.getIntent(getApplicationContext(), user));
            }
        });
    }

    public static Intent getIntent(Context c, String name, String user)
    {
        Intent intent = new Intent(c, EditPracticalActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("user", user);
        return intent;
    }
}
