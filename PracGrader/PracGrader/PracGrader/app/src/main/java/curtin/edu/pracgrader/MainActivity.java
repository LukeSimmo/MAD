package curtin.edu.pracgrader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserList userList = new UserList();
        userList.load(getApplicationContext());

        if(!userList.hasAdmin())
        {
            FragmentManager fm = getSupportFragmentManager();
            InitialFragment frag = (InitialFragment) fm.findFragmentById(R.id.loginFragment);
            if (frag == null) {
                frag = new InitialFragment();
                fm.beginTransaction()
                        .add(R.id.loginFragment, frag)
                        .commit();
            }
        }
        else
        {
            FragmentManager fm = getSupportFragmentManager();
            LoginFragment frag = (LoginFragment) fm.findFragmentById(R.id.loginFragment);
            if(frag == null)
            {
                frag = new LoginFragment();
                fm.beginTransaction()
                        .add(R.id.loginFragment, frag)
                        .commit();
            }
        }
    }
}