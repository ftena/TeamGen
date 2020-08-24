package com.tarlic.TeamGen;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Teams extends Activity {
	
	private List<Player> playerList= new ArrayList<>();
	
	/** Called when the activity is first created. */
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set the View layer
		setContentView(R.layout.main_teams_view);		

		ArrayList<HashMap<String, String>> arrayPlayers = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("arrayPlayers");		
		Vector<String> solutionArray = new Vector<>();
		Integer numberOfTeams = (Integer)getIntent().getSerializableExtra("numberOfTeams");
	    int playersOnATeam;
	    StringBuilder firstTeam = new StringBuilder();
	    StringBuilder secondTeam = new StringBuilder();
	    boolean firstTeamMade = false;

		for (HashMap<String, String> temp : arrayPlayers) {
			solutionArray.add(temp.get("value"));
		}
		
		playersOnATeam = solutionArray.size() / numberOfTeams;
				
	    Collections.shuffle(solutionArray);
	    
	    for (int i = 0; i < numberOfTeams; i++)
	    {
	    		
	    		for (int k = 0; k < playersOnATeam; k++) {
	    		
	    			//System.out.print(solutionArray.remove(0));
	    			
	    			if (! firstTeamMade)
	    			
	    				firstTeam.append(solutionArray.remove(0));
	    			
	    			else
	    				
	    				secondTeam.append(solutionArray.remove(0));
	    			
	    			//final String countryName = temp.get("field");
	    			
	    			if (k != playersOnATeam -1)
	    				//System.out.print(", ");
	    				if (! firstTeamMade)
	    					firstTeam.append(", ");
	    				else
	    					secondTeam.append(", ");
	    				
	    		}
	    		
	    		if (i % 2 == 0) {
	    			// mark the flag to start displaying the second team
	    			firstTeamMade = true;
	    		}
	    		else {
	    			Player player = new Player(firstTeam.toString(), secondTeam.toString(), "");
    				
	    			// Add to list
	    			playerList.add(player);
	    			
	    			// clean the strings
	    			
	    			firstTeam = new StringBuilder();
	    			secondTeam = new StringBuilder();
	    			
	    			// the first team will be made again from starting
	    			
	    			firstTeamMade = false;
	    			
	    		}
	    }
		
		// Create a customized ArrayAdapter
		PlayerArrayAdapter adapter = new PlayerArrayAdapter(
				getApplicationContext(), playerList);
		
		// Get reference to ListView holder
		ListView lv = (ListView) this.findViewById(R.id.listTeams);
		
		// Set the ListView adapter
		lv.setAdapter(adapter);
	}
	
}
