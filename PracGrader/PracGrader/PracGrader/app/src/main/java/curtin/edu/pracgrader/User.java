package curtin.edu.pracgrader;

public class User {
    private String username;
    private String name;
    private String email;
    private String pin;
    private String country;
    private String type;
    private String addedBy;

    // DEFAULT CONSTRUCTOR
    // useful for creating user object but adding items later
    public User()
    {
        username = null;
        name = null;
        email = null;
        pin = null;
        country = null;
        type = null;
        addedBy = null;
    }

    // ALTERNATE CONSTRUCTOR FOR ADDING ADMIN
    // NOTE - no user can create admin therefore its 'addedBy' classfield is null
    public User(String username, String name, String email, String pin, String country, String type) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.pin = pin;
        this.country = country;
        this.type = type;
    }

    // ALTERNATE CONSTRUCTOR FOR ADMIN ADDING INSTRUCTORS OR STUDENTS
    public User(String username, String name, String email, String pin, String country, String type, String addedBy) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.pin = pin;
        this.country = country;
        this.type = type;
        this.addedBy = addedBy;
    }

    // ACCESSORS
    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPin() {
        return pin;
    }

    public String getCountry() {
        return country;
    }

    public String getType() {
        return type;
    }

    public String getAddedBy()
    {
        return addedBy;
    }

    // MUTATORS
    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPin(String pin)
    {
        this.pin = pin;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setAddedBy(String addedBy)
    {
        this.addedBy = addedBy;
    }
}