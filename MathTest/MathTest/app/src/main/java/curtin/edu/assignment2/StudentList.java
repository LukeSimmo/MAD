package curtin.edu.assignment2;

import android.content.Context;

import java.util.List;

public class StudentList
{
    private List<Student> students;
    private StudentDBModel model = new StudentDBModel();

    public StudentList()
    {

    }

    public void load(Context context)
    {
        model.load(context);
        students = model.getAllStudent();
    }

    public int size()
    {
        return students.size();
    }

    public Student get(int i)
    {
        return students.get(i);
    }

    public int add(Student newStudent)
    {
        if(students.isEmpty())
        {
            newStudent.setId(1);
        }
        else
        {
            newStudent.setId(findNextId());
        }

        students.add(newStudent);
        model.addStudent(newStudent);

        return students.size() - 1; // insertion point
    }

    public void edit(Student editStudent)
    {
        model.updateStudent(editStudent);
    }

    public void remove(Student rmStudent)
    {
        students.remove(rmStudent);
        model.removeStudent(rmStudent);
    }

    public Student getStudentById(int id)
    {
        return model.getStudentById(id);
    }

    public int findNextId()
    {
        return (model.getAllStudent().remove(students.size() - 1)).getId() + 1;
        // -1 to obtain size starting from 0, +1 to create next id number
        // could leave out the maths but it is clearer with it in
    }
}
