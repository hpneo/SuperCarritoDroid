package com.supercarritodroid.rest;

import java.util.ArrayList;

import org.json.JSONArray;

import com.supercarritodroid.models.Supermarket;
import com.supercarritodroid.rest.RestClient.RequestMethod;

import android.os.AsyncTask;

public class SupermarketTask extends AsyncTask<String, Void, String> {
	private TaskListener listener;
	private RestClient client;
    
    public SupermarketTask() {
    }
    
    public SupermarketTask(TaskListener listener) {
        this.listener = listener;
    }

    public void setListener(TaskListener listener) {
        this.listener = listener;
    }

	@Override
	protected String doInBackground(String... urls) {
		try {
			return loadFromNetwork(Supermarket.BASE_URL + urls[0]);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		
		try {
			if (!result.equals(null)) {
				ArrayList<Supermarket> list = Supermarket.fromJSON(new JSONArray(result));
				
				this.listener.onTaskCompleted(list);
			}
		} catch (Exception e) {
			this.listener.onTaskError(new Error("Error at fetching data"));
		}
	}
	  
	@Override
	protected void onCancelled() {
		this.listener.onTaskCancelled("Task cancelled");
	}
	
	private String loadFromNetwork(String url) throws Exception {
		client = new RestClient(url);
	    client.Execute(RequestMethod.GET);
	    
	    return client.getResponse();
	}

}
