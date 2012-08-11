package com.lays.fote.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.actionbarsherlock.app.SherlockListActivity;
import com.lays.fote.R;
import com.lays.fote.holders.FoteViewHolder;
import com.lays.fote.models.Fote;
import com.lays.fote.utilities.FoteCalendar;

public class FoteListAdapter extends ArrayAdapter<Fote> {

	/** XML layout inflater */
	private static LayoutInflater inflater;

	/** List of our mArticles objects */
	private ArrayList<Fote> mFotes;

	public FoteListAdapter(SherlockListActivity activity, ArrayList<Fote> fotes) {
		super(activity, R.layout.list_row_fote, fotes);
		inflater = activity.getLayoutInflater();
		mFotes = fotes;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;

		if (row == null) {
			row = inflater.inflate(R.layout.list_row_fote, parent, false);
		}

		FoteViewHolder holder = (FoteViewHolder) row.getTag();

		if (holder == null) {
			holder = new FoteViewHolder(row);
			row.setTag(holder);
		}

		Fote fote = mFotes.get(position);
		FoteCalendar date = new FoteCalendar(fote.getDate());
		holder.getDate().setText(date.getShortenedDateAmerican());

		DecimalFormat df = new DecimalFormat();
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		holder.getAmount().setText("$" + df.format(fote.getAmount()));
		holder.getComment().setText(fote.getComment());

		return row;
	}
}
