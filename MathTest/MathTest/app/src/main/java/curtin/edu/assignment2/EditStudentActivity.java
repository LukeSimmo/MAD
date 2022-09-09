package curtin.edu.assignment2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class EditStudentActivity extends AppCompatActivity
{
    private static final int REQUEST_PHOTO = 2;
    private static final int REQUEST_GRIDPHOTO = 4;

    private EditText firstname;
    private EditText lastname;
    private EditText number;
    private EditText email;
    private ImageView image;

    private Button btnPhoto;
    private Button btnConfirm;
    private Button btnBack;

    private RecyclerView rv;
    private ViewResultsAdapter adapter;

    private StudentList studentList;
    private int fromActivity;
    private int id;

    private TestList testList;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_edit_student);

        // SET DATABASE
        studentList = new StudentList();
        studentList.load(EditStudentActivity.this);

        testList = new TestList();
        testList.load(EditStudentActivity.this);

        firstname = findViewById(R.id.editEditStudentFirstname);
        lastname = findViewById(R.id.editEditStudentLastname);
        number = findViewById(R.id.editEditStudentNumber);
        email = findViewById(R.id.editEditStudentEmail);
        image = findViewById(R.id.imageEditStudent);
        rv = findViewById(R.id.studentResultsRecycler);

        btnPhoto = findViewById(R.id.btnEditStudentAddPhoto);
        btnConfirm = findViewById(R.id.btnEditStudentConfirm);
        btnBack = findViewById(R.id.btnEditStudentBack);

        Student student = (Student) getIntent().getSerializableExtra("student");
        id = student.getId();

        firstname.setText(student.getFirstname());
        lastname.setText(student.getLastname());
        number.setText(student.getNumber());
        email.setText(student.getEmail());

        fromActivity = getIntent().getIntExtra("fromActivity", 0);

        adapter = new ViewResultsAdapter();
        rv.setLayoutManager(new LinearLayoutManager(EditStudentActivity.this));

        rv.setAdapter(adapter);

        // BYTE ARRAY TO IMAGE VIEW
        try
        {
            Bitmap bitmap = BitmapFactory.decodeByteArray(student.getImage(), 0, student.getImage().length);
            image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 100, false));
        }
        catch(Exception e)
        {

        }

        btnPhoto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = li.inflate(R.layout.popup_photo_buttons, null);

                // CREATE WINDOW
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                Button btnTakePhoto = (Button) popupView.findViewById(R.id.popupTakePhoto);
                Button btnBrowsePhoto = (Button) popupView.findViewById(R.id.popupLocalPhoto);
                Button btnOnlinePhoto = (Button) popupView.findViewById(R.id.popupOnlinePhoto);

                btnTakePhoto.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent = new Intent();
                        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_PHOTO);

                        popupWindow.dismiss();
                    }
                });

                // TODO: BROWSE LOCAL PHOTO


                btnOnlinePhoto.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        startActivityForResult(new Intent(EditStudentActivity.this, GridPhotoActivity.class), REQUEST_GRIDPHOTO);

                        popupWindow.dismiss();
                    }
                });
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (firstname.getText().toString().trim().length() == 0
                        || lastname.getText().toString().trim().length() == 0
                        || number.getText().toString().trim().length() == 0
                        || email.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(EditStudentActivity.this, "Null fields detected, please fill every field"
                            , Toast.LENGTH_SHORT).show();
                }
                else
                {
                    student.setFirstname(firstname.getText().toString());
                    student.setLastname(lastname.getText().toString());
                    student.setNumber(number.getText().toString());
                    student.setEmail(email.getText().toString());

                    byte[] byteImg = makeByteArray();
                    student.setImage(byteImg);

                    studentList.edit(student);

                    Toast.makeText(EditStudentActivity.this, "Changes Saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(fromActivity == 0)
                {
                    startActivity(new Intent(EditStudentActivity.this, RegisterStudentActivity.class));
                }
                else if(fromActivity == 1)
                {
                    startActivity(new Intent(EditStudentActivity.this, ViewStudentActivity.class));
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PHOTO)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if (image != null)
            {
                image.setImageBitmap(bitmap);
            }
        }
        else if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_GRIDPHOTO)
        {
            byte[] img = (byte[]) GridPhotoActivity.getImage(data);

            Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
            image.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 100, 100, false));
        }
    }

    private class ViewResultsViewHolder extends RecyclerView.ViewHolder
    {
        TextView firstname;
        TextView lastname;
        TextView mark;
        TextView testTime;
        TextView totalTime;

        public ViewResultsViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.entry_result, parent, false));

            firstname = (TextView) itemView.findViewById(R.id.textEntryResultFirstname);
            lastname = (TextView) itemView.findViewById(R.id.textEntryResultLastname);
            mark = (TextView) itemView.findViewById(R.id.textEntryResultMark);
            testTime = (TextView) itemView.findViewById(R.id.textEntryResultDate);
            totalTime = (TextView) itemView.findViewById(R.id.textEntryResultTotalTime);
        }

        public void bind(String firstnameVal, String lastnameVal, Test test)
        {
            firstname.setText(firstnameVal);
            lastname.setText(lastnameVal);
            mark.setText(String.valueOf(test.getMark()));
            testTime.setText(test.getTimeOfTest());
            totalTime.setText(String.valueOf(test.getTotalTime()) + " Seconds");
        }
    }

    public class ViewResultsAdapter extends RecyclerView.Adapter<ViewResultsViewHolder>
    {
        @Override
        public ViewResultsViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new ViewResultsViewHolder(LayoutInflater.from(EditStudentActivity.this), container);
        }

        @Override
        public void onBindViewHolder(ViewResultsViewHolder vh, int position)
        {
            Test test = testList.getById(id, position);

            Student tempStudent = studentList.getStudentById(id);

            vh.bind(tempStudent.getFirstname(), tempStudent.getLastname(), test);
        }

        @Override
        public int getItemCount()
        {
            return testList.idSize(id);
        }
    }

    public static Intent getIntent(Context c, Student student, int num)
    {
        Intent intent = new Intent(c, EditStudentActivity.class);
        intent.putExtra("student", student);
        intent.putExtra("fromActivity", num);
        return intent;
    }

    // private helper methods
    private byte[] makeByteArray()
    {
        byte[] byteImage = null;

        try
        {
            Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byteImage = baos.toByteArray();
        }
        catch(Exception e)
        {
            Toast.makeText(EditStudentActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }

        return byteImage;
    }
}
