package curtin.edu.assignment2;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Email;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment2.R;

import java.io.ByteArrayOutputStream;

public class RegisterStudentActivity extends AppCompatActivity
{
    // CONSTANTS
    private static final int REQUEST_CONTACT = 2;
    private static final int REQUEST_READ_CONTACT_PERMISSION = 3;

    private static final int REQUEST_PHOTO = 4;
    private static final int REQUEST_GRIDPHOTO = 5;
    private static final int REQUEST_PICTURE = 6;

    // UI ELEMENTS
    private EditText firstname;
    private EditText lastname;
    private EditText number;
    private EditText email;
    private ImageView image;

    private Button btnPhMore;
    private Button btnEmailMore;
    private Button btnImport;
    private Button btnAddPhoto;
    private Button btnAddStudent;
    private Button btnBack;

    private RecyclerView recycler;
    private RegisterStudentAdapter adapter;
    private LinearLayoutManager lm;

    private StudentList list;

    private int contactId;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_register_student);

        // GET DATABASE
        list = new StudentList();
        list.load(RegisterStudentActivity.this);


        // SET UI ELEMENTS
        firstname = findViewById(R.id.editRegisterStudentFirstName);
        lastname = findViewById(R.id.editRegisterStudentLastname);
        number = findViewById(R.id.editRegisterStudentPhNumber);
        email = findViewById(R.id.editRegisterStudentEmail);
        image = findViewById(R.id.imageRegisterStudentImage);

        btnPhMore = findViewById(R.id.btnRegisterStudentPhMore);
        btnEmailMore = findViewById(R.id.btnRegisterStudentEmailMore);
        btnImport = findViewById(R.id.btnRegisterStudentImport);
        btnAddPhoto = findViewById(R.id.btnRegisterStudentPhoto);
        btnAddStudent = findViewById(R.id.btnRegisterStudentAdd);
        btnBack = findViewById(R.id.btnRegisterStudentBack);
        recycler = findViewById(R.id.recyclerRegisterStudent);

        // OTHER VARIABLES
        Student student = new Student();

        adapter = new RegisterStudentAdapter();
        lm = new LinearLayoutManager(RegisterStudentActivity.this);

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(lm);

        btnAddStudent.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (firstname.getText().toString().trim().length() == 0
                    || lastname.getText().toString().trim().length() == 0
                    || number.getText().toString().trim().length() == 0
                    || email.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(RegisterStudentActivity.this, "Null fields detected, please fill every field"
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

                    int insertPosition = list.add(student);

                    clear();

                    adapter.notifyItemInserted(insertPosition);
                    lm.scrollToPosition(insertPosition);

                    Toast.makeText(RegisterStudentActivity.this, "Student registered", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // IMPORT FROM CONTACTS
        btnImport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(ContextCompat.checkSelfPermission(RegisterStudentActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(RegisterStudentActivity.this, new String[] { Manifest.permission.READ_CONTACTS },
                            REQUEST_READ_CONTACT_PERMISSION);
                }

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setData(ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CONTACT);
            }
        });



        btnAddPhoto.setOnClickListener(new View.OnClickListener()
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

                Button btnLivePhoto = (Button) popupView.findViewById(R.id.popupTakePhoto);
                Button btnLocalPhoto = (Button) popupView.findViewById(R.id.popupLocalPhoto);
                Button btnFindPhoto = (Button) popupView.findViewById(R.id.popupOnlinePhoto);

                btnLivePhoto.setOnClickListener(new View.OnClickListener()
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

                btnFindPhoto.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        startActivityForResult(new Intent(RegisterStudentActivity.this, GridPhotoActivity.class), REQUEST_GRIDPHOTO);

                        popupWindow.dismiss();
                    }
                });

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(UserActivity.getIntent(RegisterStudentActivity.this, 0));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CONTACT && resultCode == RESULT_OK)
        {
            Uri contactUri = data.getData();
            String[] queryIDFields = new String[] { ContactsContract.Contacts._ID
                                                    , ContactsContract.Contacts.DISPLAY_NAME };

            Cursor c = getContentResolver().query(
                    contactUri, queryIDFields, null, null, null);

            try
            {
                if (c.getCount() > 0)
                {
                    c.moveToFirst();
                    this.contactId = c.getInt(0);
                    String contactName = c.getString(1);

                    // SEPARATE NAME INTO FIRST AND LAST NAME
                    String[] names = contactName.split(" ");
                    String fName = names[0];
                    String lName = names[1];

                    firstname.setText(fName);
                    lastname.setText(lName);
                }
            }
            finally
            {
                c.close();
            }

            // GET PHONE NUMBER
            Cursor numberCursor = getApplication().getContentResolver().query(
                    Phone.CONTENT_URI,
                    new String[] { Phone.NUMBER },
                    Phone.CONTACT_ID + " = ? AND " + Phone.TYPE + " = " + Phone.TYPE_MOBILE,
                    new String[] { String.valueOf(contactId) },
                    null, null);

            try
            {
                if(numberCursor.getCount() > 0)
                {
                    numberCursor.moveToNext();
                    String phNumber = numberCursor.getString(0);
                    number.setText(phNumber);
                }
            }
            finally
            {
                numberCursor.close();
            }

            // GET EMAIL
            Cursor emailCursor = getApplicationContext().getContentResolver().query(
                    Email.CONTENT_URI,
                    new String[] { Email.ADDRESS },
                    Email.CONTACT_ID + " = ?",
                    new String[] { String.valueOf(contactId) },
                    null, null);

            try
            {
                if(emailCursor.getCount() > 0)
                {
                    emailCursor.moveToFirst();
                    email.setText(emailCursor.getString(0));
                }
            }
            finally
            {
                emailCursor.close();
            }
        }
        else if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_PHOTO)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            if(image != null)
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(requestCode == REQUEST_READ_CONTACT_PERMISSION)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(RegisterStudentActivity.this, "Contact Reading Permission Granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class RegisterStudentViewHolder extends RecyclerView.ViewHolder
    {
        private Student student;
        private TextView fName;
        private TextView lName;
        private Button edit;
        private Button del;

        public RegisterStudentViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.entry_register_student, parent, false));

            fName = (TextView) itemView.findViewById(R.id.textEntryFirstname);
            lName = (TextView) itemView.findViewById(R.id.textEntryLastname);
            edit = (Button) itemView.findViewById(R.id.btnEntryEdit);
            del = (Button) itemView.findViewById(R.id.btnEntryDel);

            edit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    startActivity(EditStudentActivity.getIntent(RegisterStudentActivity.this, student, 0));
                }
            });

            del.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    list.remove(student);
                    adapter.notifyItemRemoved(getAdapterPosition());
                }
            });
        }

        public void bind(Student student)
        {
            this.student = student;

            fName.setText(student.getFirstname());
            lName.setText(student.getLastname());
        }
    }

    public class RegisterStudentAdapter extends RecyclerView.Adapter<RegisterStudentViewHolder>
    {
        @Override
        public RegisterStudentViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new RegisterStudentViewHolder(LayoutInflater.from(RegisterStudentActivity.this), container);
        }

        @Override
        public void onBindViewHolder(RegisterStudentViewHolder vh, int position)
        {
            Student student = list.get(position);

            vh.bind(student);
        }

        @Override
        public int getItemCount()
        {
            return list.size();
        }
    }

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
            Toast.makeText(RegisterStudentActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }

        return byteImage;
    }

    private void clear()
    {
        firstname.setText("");
        lastname.setText("");
        number.setText("");
        email.setText("");
        image.setImageResource(android.R.color.transparent);
    }
}
