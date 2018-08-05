package com.tarlic.TeamGen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

// We need to extend FragmentActivity instead of the normal Activity. 
public class TeamGen extends FragmentActivity 
					implements PlayersListDialogFragment.NoticeDialogListener {

	private ListView playersList;
	
	// Dialog identifiers
	private static final int DIALOG_NUMBER_OF_TEAMS_ID = 0;

	private final CharSequence[] number_of_teams = { "2", "4", "8", "12", "16" };

	private final ArrayList<HashMap<String, String>> arrayNumberOfTeams = new ArrayList<HashMap<String, String>>();

	private final ArrayList<HashMap<String, String>> arrayPlayers = new ArrayList<HashMap<String, String>>();
	
	private Integer playerCounter = 0;
	
	private Integer numberOfTeams;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_view);

		/*
		 * Disable the Android OnScreen keyboard when the activity is opened
		 */

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		initLists();

	}

	private void initLists() {

		// Initialize the number of teams list
		ListView numberOfTeamsList = (ListView) findViewById(R.id.listNumberOfTeams);

		HashMap<String, String> temp = new HashMap<String, String>();
		temp.put("field", "Number of Teams");
		temp.put("value", "0");
		arrayNumberOfTeams.add(temp);

		ListAdapter adapter = new SimpleAdapter(this, // Context
				arrayNumberOfTeams, R.layout.row_view, // Specify the row
														// template to use
				new String[] { "field", "value" }, new int[] { R.id.field,
						R.id.value }); // Parallel array of which template
										// objects to bind to those columns.

		numberOfTeamsList.setAdapter(adapter);

		// Attach a click listener for number of teams list
		numberOfTeamsList.setOnItemClickListener(numberOfTeamsListener);
		
		// Initialize the players list 
		playersList = (ListView) findViewById(R.id.listPlayers);
		
		// Attach a click listener for the players list		
		playersList.setOnItemClickListener(new OnItemClickListener() {            
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {              
            	
            	/*
            	 * Every fragment must have an empty constructor, so it can be
            	 * instantiated when restoring its activity's state. It is strongly
            	 * recommended that subclasses do not have other constructors
            	 * with parameters, since these constructors will not be called when
            	 * the fragment is re-instantiated; instead, arguments can be
            	 * supplied by the caller with setArguments(Bundle) and later
            	 * retrieved by the Fragment with getArguments().
            	 */
            	Bundle dataBundle = new Bundle();            	
            	dataBundle.putInt("Id", position);            	
                showPlayersListNoticeDialog(dataBundle);
            }
        });

	}

	/*
	 * Called when it is pushed a Number of Teams in the dialog  
	*/ 
	
	private void updateNumberOfTeamsList(String selection) {

		ListView numberOfTeamsList = (ListView) findViewById(R.id.listNumberOfTeams);

		arrayNumberOfTeams.clear();

		HashMap<String, String> temp = new HashMap<String, String>();
		temp.put("field", "Number of Teams");
		temp.put("value", selection);
		arrayNumberOfTeams.add(temp);

		ListAdapter adapter = new SimpleAdapter(this, // Context
				arrayNumberOfTeams, R.layout.row_view, // Specify the row
														// template to use
				new String[] { "field", "value" }, new int[] { R.id.field,
						R.id.value }); // Parallel array of which template
										// objects to bind to those columns.

		numberOfTeamsList.setAdapter(adapter);

	}

	private OnItemClickListener numberOfTeamsListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			// do something when the option "Number of Teams" is clicked
			// TODO: fix
			showDialog(DIALOG_NUMBER_OF_TEAMS_ID);
		}
	};

	protected Dialog onCreateDialog(int id) {
		AlertDialog alert;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		// do the work to define the dialog
		
		switch (id) {
		case DIALOG_NUMBER_OF_TEAMS_ID:

			builder.setTitle("Pick the number of teams");
			builder.setSingleChoiceItems(number_of_teams, -1,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							Toast.makeText(getApplicationContext(),
									number_of_teams[which] + " teams selected",
									Toast.LENGTH_SHORT).show();

							updateNumberOfTeamsList((String) number_of_teams[which]);
							
							// save the number of teams
							
							numberOfTeams = Integer.parseInt(number_of_teams[which].toString());

							dialog.dismiss();
						}
					});

			alert = builder.create();

			break;		
		default:
			alert = null;
		}
		return alert;
	}

	
	 private void showPlayersListNoticeDialog(Bundle dataBundle) {
	        // Create an instance of the dialog fragment and show it
	        DialogFragment dialog = new PlayersListDialogFragment();
	        dialog.setArguments(dataBundle);
	        dialog.show(getSupportFragmentManager(), "PlayersListDialogFragment");
	    }
	 
	    // The dialog fragment receives a reference to this Activity through the
	    // Fragment.onAttach() callback, which it uses to call the following methods
	    // defined by the PlayersListDialogFragment.NoticeDialogListener interface
	    public void onDialogPositiveClick(DialogFragment dialog) {
	        // User touched the dialog's positive button	        
	    	Bundle dataBundle = dialog.getArguments();
	    	
	    	// Get the id passed previously
	    	Integer id = dataBundle.getInt("Id");
	    		    	
	    	// Remove the selected row			
			arrayPlayers.remove((int)id);

			ListAdapter adapter = new SimpleAdapter(this, // Context
					arrayPlayers, R.layout.row_view, // Specify the row template to
														// use
					new String[] { "field", "value" }, new int[] { R.id.field,
							R.id.value }); // Parallel array of which template
											// objects to bind to those columns.
	    	
			playersList.setAdapter(adapter);
			
 		    Toast.makeText(getApplicationContext(),
						"Player removed",
						Toast.LENGTH_SHORT).show();
	    }

	    public void onDialogNegativeClick(DialogFragment dialog) {
	        // User touched the dialog's negative button
	        //...
 		    Toast.makeText(getApplicationContext(),
						"Nice",
						Toast.LENGTH_SHORT).show();
	    }
	
	public void disableEditTextResults(int idEditTextResults) {
		EditText editTextResults = (EditText) findViewById(idEditTextResults);

		editTextResults.setKeyListener(null);
	}

	/* Process the Add button */
	
	public void buttonAddClick(View view) {
		
		EditText editTextPlayerName = (EditText) findViewById(R.id.EditTextPlayerName);
						
		if (editTextPlayerName.length() == 0)
		{
			Toast.makeText(getApplicationContext(),
					"Type a name",
					Toast.LENGTH_SHORT).show();
			
		} else {
			
			playerCounter++;

			HashMap<String, String> temp = new HashMap<String, String>();
			temp.put("field", "Player " + playerCounter.toString());
			temp.put("value", editTextPlayerName.getText().toString());
			arrayPlayers.add(temp);

			ListAdapter adapter = new SimpleAdapter(this, // Context
					arrayPlayers, R.layout.row_view, // Specify the row template to
														// use
					new String[] { "field", "value" }, new int[] { R.id.field,
							R.id.value }); // Parallel array of which template
											// objects to bind to those columns.

			playersList.setAdapter(adapter);
		}
		
		editTextPlayerName.setText(new String());		
	}
	
	/* Process the Make Team button */
	
	public void buttonMakeTeamsClick(View view) {
		
		ListIterator<HashMap<String, String>> it = arrayNumberOfTeams.listIterator();
		
		HashMap<String, String> temp = it.next();
		
		final Integer numberOfTeams = Integer.valueOf(temp.get("value"));
		
		// Check if it is possible to make teams
		
		if ( arrayPlayers.size() == 0 )
			showMsg("Add some players");
		else if ( numberOfTeams == 0 ) 
			showMsg("Select a number of teams");			
		else if ( arrayPlayers.size() % numberOfTeams != 0 )
		{
			if ( arrayPlayers.size() == 1 )
				showMsg("You cannot make " + numberOfTeams + " teams using one player");
			else
				showMsg("You cannot make " + numberOfTeams + " teams using " +
						arrayPlayers.size() + " players");			
		} else {
		
			// If is possible to make teams
			
			Intent intent = new Intent(this, Teams.class);
        
			intent.putExtra("arrayPlayers", arrayPlayers);
			intent.putExtra("numberOfTeams", numberOfTeams);
		
			startActivity(intent);
		}
        
		
	}
	
	/*
	 * Menu implementation
	*/
	
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.triforce_menu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_more:
			showMore();
			return true;
		case R.id.menu_exit:
			showExit();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showMore() {
		LayoutInflater inflater = this.getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.dialog_more, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.app_name);
		builder.setView(dialogView);
		builder.setCancelable(false).setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	private void showExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(R.string.dialog_exit).setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								TeamGen.this.finish();
							}
						}).setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		AlertDialog alert = builder.create();

		alert.show();
	}

	private void showMsg(String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);

		builder.setMessage(msg).setCancelable(true).setPositiveButton("Ok",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		AlertDialog alert = builder.create();

		alert.show();
	}

}
