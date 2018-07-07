package petra.tugas.ppm_project_balloon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class CustomAdaptor extends BaseAdapter {
    Context context;
    private static LayoutInflater inflater = null;
    private ArrayList<String> _listNama = new ArrayList<String>(5);
    private ArrayList<Integer> _listScore = new ArrayList<Integer>(5);

    public CustomAdaptor(MainActivity list, ArrayList<String> _listNama, ArrayList<Integer> _listScore) {
        context=list;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this._listNama = _listNama;
        this._listScore = _listScore;

        System.out.println(_listNama.get(0));
        System.out.println(_listNama.get(1));

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public class Content{
        TextView _forName, _forScore;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        Content content = new Content();
        View Baris;
        Baris = inflater.inflate(R.layout.contentview, null);
        content._forName=(TextView) Baris.findViewById(R.id._forname);
        content._forScore=(TextView) Baris.findViewById(R.id._forscore);

        content._forName.setText(_listNama.get(position));
        content._forScore.setText(_listScore.get(position));
        System.out.println("JALAN");
        return Baris;
    }
}