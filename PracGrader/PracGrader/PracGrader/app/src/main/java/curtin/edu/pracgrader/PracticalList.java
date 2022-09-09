package curtin.edu.pracgrader;

import android.content.Context;

import java.util.List;

public class PracticalList
{
    private List<Practical> practicals;
    private practicalDBModel pracDBModel = new practicalDBModel();

    public PracticalList()
    {

    }

    public int size()
    {
        return practicals.size();
    }

    public void load(Context context)
    {
        pracDBModel.load(context);
        practicals = pracDBModel.getAllUser();
    }

    public Practical get(int i)
    {
        return practicals.get(i);
    }

    public int add(Practical newPrac)
    {
        practicals.add(newPrac);
        pracDBModel.addPractical(newPrac);

        return practicals.size() - 1;
    }

    public void edit(Practical newPrac)
    {
        pracDBModel.updatePractical(newPrac);
    }

    public void remove(Practical rmPrac)
    {
        practicals.remove(rmPrac);
        pracDBModel.removePractical(rmPrac);
    }

    public Practical getPractical(String name)
    {
        return pracDBModel.getPrac(name);
    }

    public boolean hasPractical(String name)
    {
        return pracDBModel.hasPractical(name);
    }

    public double getPracticalMark(String name)
    {
        return pracDBModel.getPracMark(name);
    }
}
