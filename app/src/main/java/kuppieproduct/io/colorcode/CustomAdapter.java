package kuppieproduct.io.colorcode;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Item> {

    private List<Item> items;

    CustomAdapter(Context context, int resource, List<Item> items) {
        super(context, resource, items);

        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Item getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_color_list, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.rootView = convertView.findViewById(R.id.rootView);
            viewHolder.colorCode = (TextView) convertView.findViewById(R.id.colorCodeText);

            convertView.setTag(viewHolder);
        }

        Item item = items.get(position);

        viewHolder.rootView.setBackgroundColor(item.pxl);
        viewHolder.colorCode.setText(item.colorCode);

        return convertView;
    }

    static class ViewHolder {
        View rootView;
        TextView colorCode;
    }
}
