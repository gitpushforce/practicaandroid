package androide.practica.com.practicaandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import androide.practica.com.practicaandroid.R;
import androide.practica.com.practicaandroid.models.Note;

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> list;
    private int layout;

    public NoteAdapter(Context context, List<Note> list, int layout) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ViewHolder();
            vh.description = convertView.findViewById(R.id.textViewNoteDescription);
            vh.createdAt = convertView.findViewById(R.id.textViewNoteCreatedAt);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        Note note = list.get(position);
        vh.description.setText(note.getDescription());

        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String date = df.format(note.getCreatedAt());
        vh.createdAt.setText(date);

        return convertView;
    }

    private class ViewHolder {
        TextView description;
        TextView createdAt;
    }
}
