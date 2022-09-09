package curtin.edu.assignment2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TestList
{
    private List<Test> tests;
    private TestDBModel model = new TestDBModel();

    public TestList()
    {

    }

    public void load(Context context)
    {
        model.load(context);
        tests = model.getAllTest();
    }

    public int size()
    {
        return tests.size();
    }

    public int idSize(int id)
    {
        int count = 0;

        for(int ii = 0; ii < tests.size(); ii++)
        {
            Test temp = tests.get(ii);

            if(temp.getStudentID() == id)
            {
                count++;
            }
        }

        return count++;
    }

    public Test get(int i)
    {
        return tests.get(i);
    }

    public int add(Test newTest)
    {
        tests.add(newTest);
        model.addTest(newTest);

        return tests.size() - 1;
    }

    public void edit(Test editTest)
    {
        model.updateTest(editTest);
    }

    public void remove(Test rmTest)
    {
        tests.remove(rmTest);
        model.removeTest(rmTest);
    }

    public List<Test> getAllTests()
    {
        return tests;
    }

    public Test getById(int id, int i)
    {
        List<Test> tempList = new ArrayList<>();

        for(int ii = 0; ii < tests.size(); ii++)
        {
            if(tests.get(ii).getStudentID() == id)
            {
                tempList.add(tests.get(ii));
            }
        }

        return tempList.get(i);
    }
}
