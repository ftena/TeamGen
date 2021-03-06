package com.tarlic.TeamGen;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class PlayerArrayAdapter extends ArrayAdapter<Player> {

	public PlayerArrayAdapter(Context context,
							  List<Player> objects) {
		super(context, R.layout.teams_row_view, objects);
	
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			// ROW INFLATION
			
			LayoutInflater inflater = (LayoutInflater) this.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.teams_row_view, parent, false);
			
		}

		// Get item
		Player player = getItem(position);
		//countryIcon = (ImageView) row.findViewById(R.id.country_icon);
		TextView firstTeam = (TextView) row.findViewById(R.id.firstTeam);
		TextView secondTeam = (TextView) row.findViewById(R.id.secondTeam);

		firstTeam.setText(player.firstTeam);		
		secondTeam.setText(player.secondTeam);

		return row;
	}

}
