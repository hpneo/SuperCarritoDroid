package com.supercarritodroid.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;

import com.supercarritodroid.models.Product;
import com.supercarritodroid.rest.RestClient.RequestMethod;

import android.os.AsyncTask;

public class ProductFilterTask extends AsyncTask<String, Void, String> {
	private TaskListener listener;
	private RestClient client;
	private HashMap<String, String> params = new HashMap<String, String>();
	
	public ProductFilterTask(TaskListener listener) {
		this.listener = listener;
	}
	
	public void addParams(String name, String value) {
		this.params.put(name, value);
	}
	
	@Override
	protected String doInBackground(String... urls) {
		try {
			return loadFromNetwork(Product.BASE_URL + urls[0]);
		} catch (Exception e) {
			return null;
		}
	}
	  
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	    
	    try {
	      if (!result.equals(null)) {
	        ArrayList<Product> list = Product.fromJSON(new JSONArray(result));
	        
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
	    
	    Iterator<String> iterator = this.params.keySet().iterator();
	    
	    while(iterator.hasNext()) {
	    	String key = iterator.next();
	    	String value = this.params.get(key);
	    	
	    	client.AddParam(key, value);
	    }
	    
	    client.Execute(RequestMethod.GET);
	      
	    return client.getResponse();
	}
}
