package ca.ualberta.cs.lonelytwitter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.google.gson.Gson;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class LonelyTwitterActivity extends Activity {

	private static final String FILENAME = "file.sav";
	private EditText bodyText;
	private ListView oldTweetsList;
	private ArrayAdapter<String> adapter;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		bodyText = (EditText) findViewById(R.id.body);
		Button saveButton = (Button) findViewById(R.id.save);
		oldTweetsList = (ListView) findViewById(R.id.oldTweetsList);

		saveButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				setResult(RESULT_OK);
				String text = bodyText.getText().toString();
				LonelyTweetModel tweet = new LonelyTweetModel(text, new Date(System.currentTimeMillis()));
				saveInFile(tweet);
				String string = tweet.getTimestamp().toString()+" | "+tweet.getText();
				adapter.add(string);
				bodyText.setText("");
				//adapter.notifyDataSetChanged();
				//finish();

			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		ArrayList<String> lst = new ArrayList<String>();
		String[] tweets = loadFromFile();
		lst.addAll(Arrays.asList(tweets));
		adapter = new ArrayAdapter<String>(this, R.layout.list_item, lst);
		oldTweetsList.setAdapter(adapter);
	}

	private String[] loadFromFile() {
		Gson gson = new Gson();
		LonelyTweetModel tweet;
		ArrayList<String> tweets = new ArrayList<String>();
		try {
			FileInputStream fis = openFileInput(FILENAME);
			BufferedReader in = new BufferedReader(new InputStreamReader(fis));
			String line = in.readLine();
			while (line != null) {
				tweet = gson.fromJson(line, LonelyTweetModel.class);
				tweets.add(tweet.getTimestamp().toString()+" | "+tweet.getText());
				line = in.readLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tweets.toArray(new String[tweets.size()]);
	}
	
	private void saveInFile(LonelyTweetModel tweet) {
		Gson gson = new Gson();
		try {
			FileOutputStream fos = openFileOutput(FILENAME,
					Context.MODE_APPEND);
			fos.write((gson.toJson(tweet)+"\n").getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}