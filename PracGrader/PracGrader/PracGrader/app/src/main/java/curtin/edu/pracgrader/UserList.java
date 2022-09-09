package curtin.edu.pracgrader;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserList
{
    private List<User> users;
    private List<User> instructors;
    private List<User> students;
    private UserDBModel userDBModel = new UserDBModel();
    private boolean admin;

    public UserList()
    {
        admin = false;
    }

    // LOAD ALL
    public void load(Context context)
    {
        userDBModel.load(context);
        users = userDBModel.getAllUser();
        instructors = userDBModel.getAllInstructor();
        students = userDBModel.getAllStudent();
    }

    public int size()
    {
        return users.size();
    }

    public User get(int i)
    {
        return users.get(i);
    }

    public User getInstructor(int i)
    {
        return instructors.get(i);
    }

    public User getStudent(int i)
    {
        return students.get(i);
    }

    public int add(User newUser)
    {
        users.add(newUser);
        userDBModel.addUser(newUser);

        if(newUser.getType().equals("Instructor"))
        {
            instructors.add(newUser);
            return getTypeCount("Instructor") - 1; // return insertion point
        }
        else if(newUser.getType().equals("Student"))
        {
            students.add(newUser);
            return getTypeCount("Student") - 1;
        }

        return users.size() - 1;
    }

    public void edit(User newUser)
    {
        userDBModel.updateUser(newUser);
    }

    public void remove(User rmUser)
    {
        users.remove(rmUser);
        userDBModel.removeUser(rmUser);

        if(rmUser.getType().equals("Instructor"))
        {
            instructors.remove(rmUser);
        }
        else if(rmUser.getType().equals("Student"))
        {
            students.remove(rmUser);
        }
    }

    public boolean hasUsername(String username)
    {
        return userDBModel.hasUsername(username);
    }

    public User getUser(String username)
    {
        return userDBModel.getUser(username);
    }

    public boolean hasAdmin()
    {
        return userDBModel.hasAdmin();
    }

    // RETURNS NUMBER OF USERS WITH GIVEN TYPE
    public int getTypeCount(String type)
    {
        if(type.equals("Instructor"))
        {
            return instructors.size();
        }
        else if(type.equals("Student"))
        {
            return students.size();
        }
        else
        {
            return users.size();
        }
    }

    public int getStudentAddedCount(String addedBy)
    {
        Iterator itr = students.iterator();
        int count = 0;

        while(itr.hasNext())
        {
            User user = (User) itr.next();
            if(user.getAddedBy().equals(addedBy))
            {
                count++;
            }
        }

        return count;
    }

    public List<User> getAllStudent()
    {
        return students;
    }
}
