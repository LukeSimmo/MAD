package curtin.edu.assignment2;

import java.io.Serializable;

public class Test implements Serializable
{
    // CLASSFIELDS
    private int studentID;
    private String timeOfTest;
    private int totalTime;
    private double mark;

    // DEFAULT CONSTRUCTOR (NO PARAMETERS)
    public Test()
    {
        studentID = 0;
        timeOfTest = "";
        totalTime = 0;
        mark = 0.0;
    }

    // ALTERNATE CONSTRUCTOR
    public Test(int studentId, String timeOfTest, int totalTime, double mark)
    {
        this.studentID = studentId;
        this.timeOfTest = timeOfTest;
        this.totalTime = totalTime;
        this.mark = mark;
    }

    // MUTATORS
    public void setStudentID(int studentID)
    {
        this.studentID = studentID;
    }

    public void setTimeOfTest(String timeOfTest)
    {
        this.timeOfTest = timeOfTest;
    }

    public void setTotalTime(int totalTime)
    {
        this.totalTime = totalTime;
    }

    public void setMark(double mark)
    {
        this.mark = mark;
    }

    // ACCESSORS
    public int getStudentID()
    {
        return this.studentID;
    }

    public String getTimeOfTest()
    {
        return this.timeOfTest;
    }

    public int getTotalTime()
    {
        return this.totalTime;
    }

    public double getMark()
    {
        return this.mark;
    }
}
