package curtin.edu.pracgrader;

import android.content.Context;

import java.util.List;

public class StudentMarksList
{
    private List<Mark> marks;
    private MarkDBModel markDBModel = new MarkDBModel();

    public StudentMarksList()
    {

    }

    public void load(Context context)
    {
        markDBModel.load(context);
        marks = markDBModel.getAllMark();
    }

    public int size()
    {
        return marks.size();
    }

    public Mark get(int i)
    {
        return marks.get(i);
    }

    public int add(Mark newMark)
    {
        marks.add(newMark);
        markDBModel.addMark(newMark);

        return marks.size() - 1;
    }

    public void edit(Mark newMark)
    {
        markDBModel.updateMark(newMark);
    }

    public void remove(Mark rmMark)
    {
        marks.remove(rmMark);
        markDBModel.removeMark(rmMark);
    }

    public Mark getMark(String username)
    {
        return markDBModel.getMark(username);
    }
}
