package curtin.edu.pracgrader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CountryAdapter extends ArrayAdapter
{
    String[] countryNames;
    int[] countryImages;
    Context context;

    public CountryAdapter(Context context, String[] titles, int[] images)
    {
        super(context, R.layout.country_spinner_row);

        this.countryNames = titles;
        this.countryImages = images;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return countryNames.length;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        CountryViewHolder vh = new CountryViewHolder();

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = li.inflate(R.layout.country_spinner_row, parent, false);
        vh.flag = (ImageView) view.findViewById(R.id.flagImg);
        vh.countryName = (TextView) view.findViewById(R.id.flagName);

        vh.flag.setImageResource(countryImages[position]);
        vh.countryName.setText(countryNames[position]);

        return view;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup parent)
    {
        return getView(position, view, parent);
    }

    private class CountryViewHolder
    {
        ImageView flag;
        TextView countryName;
    }
}
