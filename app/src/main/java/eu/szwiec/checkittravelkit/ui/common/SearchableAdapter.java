/*
 * Originally downloaded and forked from https://gist.github.com/fjfish/3024308
 * More solutions at https://stackoverflow.com/questions/51913375/smarter-results-from-autocompletetextview-in-android?noredirect=1#comment90790211_51913375
 */

package eu.szwiec.checkittravelkit.ui.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.simmetrics.metrics.StringMetrics;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.VisibleForTesting;
import eu.szwiec.checkittravelkit.R;

public class SearchableAdapter extends BaseAdapter implements Filterable {

    private List<String> originalList;
    private List<String> filteredList;
    private LayoutInflater inflater;
    private ItemFilter filter = new ItemFilter();

    public SearchableAdapter(Context context, List<String> data) {
        this.filteredList = data;
        this.originalList = data;
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        if (filteredList != null) {
            return filteredList.size();
        } else {
            return 0;
        }
    }

    public Object getItem(int position) {
        return filteredList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // A ViewHolder keeps references to children views to avoid unnecessary calls
        // to findViewById() on each row.
        ViewHolder holder;

        // When convertView is not null, we can reuse it directly, there is no need
        // to reinflate it. We only inflate a new View when the convertView supplied
        // by ListView is null.
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);

            // Creates a ViewHolder and store references to the two children views
            // we want to bind data to.
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.text);

            // Bind the data efficiently with the holder.

            convertView.setTag(holder);
        } else {
            // Get the ViewHolder back to get fast access to the TextView
            // and the ImageView.
            holder = (ViewHolder) convertView.getTag();
        }

        // If weren't re-ordering this you could rely on what you set last time
        holder.text.setText(filteredList.get(position));

        return convertView;
    }

    static class ViewHolder {
        TextView text;
    }

    public Filter getFilter() {
        return filter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            List<String> newList = new ArrayList<>();

            for (int i = 0; i < originalList.size(); i++) {
                String text = originalList.get(i);
                if (isMatching(text, constraint.toString())) {
                    newList.add(text);
                }
            }

            results.values = newList;
            results.count = newList.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (ArrayList<String>) results.values;
            notifyDataSetChanged();
        }

    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    boolean isMatching(String text, String constraint) {
        String text_ = text.toLowerCase();
        String constraint_ = constraint.toLowerCase();

        if (text_.startsWith(constraint_)) return true;
        if (firstLetters(text_).contains(constraint_)) return true;
        if (StringMetrics.levenshtein().compare(text_, constraint_) > 0.5f) return true;

        return false;
    }

    private String firstLetters(String input) {
        String firstLetters = "";
        for (String word : input.split(" ")) {
            firstLetters += word.charAt(0);
        }
        return firstLetters;
    }
}