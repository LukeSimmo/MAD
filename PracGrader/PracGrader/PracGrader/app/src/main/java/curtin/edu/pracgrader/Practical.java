package curtin.edu.pracgrader;

public class Practical
{
    private String pracName;
    private String desc;
    private double mark;

    public Practical()
    {
        pracName = null;
        desc = null;
        mark = 0.0;
    }

    // ALTERNATE CONSTRUCTOR 
    public Practical(String pracName, String desc, double mark)
    {
        this.pracName = pracName;
        this.desc = desc;
        this.mark = mark;
    }

    // ACCESSORS
    public String getName()
    {
        return pracName;
    }

    public String getDesc()
    {
        return desc;
    }

    public double getMark()
    {
        return mark;
    }

    // MUTATORS
    public void setName(String title)
    {
        this.pracName = title;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public void setMark(double mark)
    {
        this.mark = mark;
    }
}