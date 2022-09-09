package curtin.edu.pracgrader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class funcMarkStudentsFragment extends Fragment
{

    private PracticalList pracList;
    private String curUsrUsername;
    private String pracName;
    private String selectedUser;

    private RecyclerView recycler;
    private funcMarkStudentsFragment.viewPracAdapter adapter;
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
        View view = li.inflate(R.layout.fragment_mark_students, parent, false);

        curUsrUsername = getArguments().getString("username");
        selectedUser = getArguments().getString("selectedUser");

        // ASSIGN UI ELEMENTS
        recycler = view.findViewById(R.id.fragMarkRecycler);

        // SET ADAPTER
        adapter = new viewPracAdapter();
        lm = new LinearLayoutManager(getActivity());

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(lm);

        return view;
    }

    private class viewPracViewHolder extends RecyclerView.ViewHolder
    {
        private Practical prac;

        private TextView name;
        private Button select;

        public viewPracViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.mark_student_prac_entry, parent, false));

            name = (TextView) itemView.findViewById(R.id.textMarkPracName);
            select = (Button) itemView.findViewById(R.id.btnMarkPracSelect);

            select.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    pracName = String.valueOf(name.getText());
                    startActivity(MarkStudentsActivity.getIntent(getActivity(), curUsrUsername, pracName, selectedUser));
                }
            });
        }

        public void bind(Practical prac)
        {
            this.prac = prac;

            name.setText(prac.getName());
        }
    }

    public class viewPracAdapter extends RecyclerView.Adapter<viewPracViewHolder>
    {

        @Override
        public viewPracViewHolder onCreateViewHolder(ViewGroup container, int viewType)
        {
            return new viewPracViewHolder(LayoutInflater.from(getActivity()), container);
        }

        @Override
        public void onBindViewHolder(viewPracViewHolder vh, int position)
        {
            Practical prac = pracList.get(position);

            vh.bind(prac);
        }

        @Override
        public int getItemCount()
        {
            return pracList.size();
        }
    }
}
