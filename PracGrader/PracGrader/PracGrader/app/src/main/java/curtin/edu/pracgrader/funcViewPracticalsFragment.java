package curtin.edu.pracgrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class funcViewPracticalsFragment extends Fragment
{
    private PracticalList pracList;

    private String curUser;

    private Button back;
    private RecyclerView recycler;

    private funcViewPracticalsFragment.viewPracticalAdapter adapter;
    private LinearLayoutManager lm;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);

        pracList = new PracticalList();
        pracList.load(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater li, ViewGroup parent, Bundle b)
    {
        View view = li.inflate(R.layout.fragment_op_view_practicals, parent, false);

        recycler = view.findViewById(R.id.recyclerPracticalView);
        back = view.findViewById(R.id.btnPrViewBack);

        curUser = getArguments().getString("username");

        // SET ADAPTER
        adapter = new viewPracticalAdapter();
        lm = new LinearLayoutManager(getActivity());

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(lm);

        back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(OptionsActivity.getIntent(getActivity(), curUser));
            }
        });


        return view;
    }

    private class viewPracticalViewHolder extends RecyclerView.ViewHolder
    {
        private Practical prac;

        private TextView name;
        private TextView desc;

        public viewPracticalViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.entry_practical_view, parent, false));

            name = (TextView) itemView.findViewById(R.id.textPrName);
            desc = (TextView) itemView.findViewById(R.id.textPrDesc);
        }

        public void bind(Practical prac)
        {
            this.prac = prac;

            name.setText(prac.getName());
            desc.setText(prac.getDesc());
        }
    }

    public class viewPracticalAdapter extends RecyclerView.Adapter<funcViewPracticalsFragment.viewPracticalViewHolder>
    {

        @Override
        public funcViewPracticalsFragment.viewPracticalViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new funcViewPracticalsFragment.viewPracticalViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(funcViewPracticalsFragment.viewPracticalViewHolder vh, int position)
        {
            Practical prac = pracList.get(position);

            vh.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    startActivity(viewPracticalDetailsActivity.getIntent(getActivity(), prac.getName(), curUser));
                }
            });

            vh.bind(prac);
        }

        @Override
        public int getItemCount()
        {
            return pracList.size();
        }
    }
}
