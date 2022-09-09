package curtin.edu.assignment2;

import java.io.Serializable;

public class Student implements Serializable
{
    // CLASSFIELDS
    private int id;
    private String firstname;
    private String lastname;
    private String number;
    private String email;
    private byte[] image;

    public Student()
    {
        id = 0;
        firstname = null;
        lastname = null;
        number = null;
        email = null;
        image = null;
    }

    // ALTERNATE CONSTRUCTOR W/O ID
    public Student(String firstname, String lastname, String number, String email, byte[] image)
    {
        this.id = 0;
        this.firstname = firstname;
        this.lastname = lastname;
        this.number = number;
        this.email = email;
        this.image = image;
    }

    // ALTERNATE CONSTRUCTOR W/ ID
    public Student(int id, String firstname, String lastname, String number, String email, byte[] image)
    {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.number = number;
        this.email = email;
        this.image = image;
    }

    // MUTATORS
    public void setFirstname(String firstname)
    {
        this.firstname = firstname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    // ACCESSORS
    public String getFirstname()
    {
        return this.firstname;
    }

    public String getLastname()
    {
        return this.lastname;
    }

    public String getNumber()
    {
        return this.number;
    }

    public String getEmail()
    {
        return this.email;
    }

    public  byte[] getImage()
    {
        return this.image;
    }

    public int getId()
    {
        return this.id;
    }
}
