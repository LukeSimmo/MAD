package curtin.edu.pracgrader;

public class Mark
{
    private String username;
    private String pracName;
    private double mark;

    public Mark()
    {
        username = null;
        pracName = null;
        mark = 0.0;
    }

    public Mark(String username, String pracName, double mark)
    {
        this.username = username;
        this.pracName = pracName;
        this.mark = mark;
    }

    // ACCESSORS
    public String getUsername()
    {
        return username;
    }

    public String getPracName()
    {
        return pracName;
    }

    public double getMark()
    {
        return mark;
    }

    // MUTATORS
    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPracName(String pracName)
    {
        this.pracName = pracName;
    }

    public void setMark(double mark)
    {
        this.mark = mark;
    }
}
