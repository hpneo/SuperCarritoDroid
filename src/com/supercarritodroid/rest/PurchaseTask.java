package com.supercarritodroid.rest;

import java.util.HashMap;
import java.util.Iterator;

import com.supercarritodroid.models.Purchase;
import com.supercarritodroid.rest.RestClient.RequestMethod;

import android.os.AsyncTask;
import android.util.Log;

public class PurchaseTask extends AsyncTask<String, Void, String> {
	private TaskListener listener;
	private RestClient client;
	private HashMap<String, String> params = new HashMap<String, String>();
	
	public PurchaseTask(TaskListener listener) {
		this.listener = listener;
	}
	
	public void addParams(String name, String value) {
		this.params.put(name, value);
	}
	
	@Override
	protected String doInBackground(String... urls) {
		try {
			return loadFromNetwork(Purchase.BASE_URL + urls[0]);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		
		try {
		    Log.i("SUPER_RESULT", result);
			if (!result.equals(null)) {
				this.listener.onTaskCompleted(result);
			}
		} catch (Exception e) {
			this.listener.onTaskError(new Error("Error at fetching data : " + result));
		}
	}
    
	@Override
	protected void onCancelled() {
		this.listener.onTaskCancelled("Task cancelled");
	}
	  
	private String loadFromNetwork(String url) throws Exception {
	    client = new RestClient(url);
	    
	    Iterator<String> iterator = this.params.keySet().iterator();
	    
	    while(iterator.hasNext()) {
	    	String key = iterator.next();
	    	String value = this.params.get(key);
	    	
	    	client.AddParam(key, value);
	    }
	    
	    client.Execute(RequestMethod.GET);
	    
	    // Log.i("SUPER_RESPONSE", client.getResponse());
	    
	    return client.getResponse();
	}
}
