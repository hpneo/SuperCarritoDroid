package com.supercarritodroid.adapters;

import java.util.ArrayList;

import com.loopj.android.image.SmartImageView;
import com.supercarritodroid.R;
import com.supercarritodroid.models.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProductListAdapter extends ArrayAdapter<Product> {
	private ArrayList<Product> collection;
	private int layoutResourceId;
	
	public ProductListAdapter(Context context, int layoutResourceId, ArrayList<Product> baseCollection) {
		super(context, layoutResourceId, baseCollection);
		this.layoutResourceId = layoutResourceId;
		this.collection = baseCollection;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		Product product = collection.get(position);
		
		if (view == null) {
			LayoutInflater view_inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = view_inflater.inflate(layoutResourceId, null);
		}
		
		view = populateView(product, view);
		
		return view;
	}
	
	public static View populateView(Product product, View view) {
		SmartImageView productImage = (SmartImageView) view.findViewById(R.id.item_product_image);
		TextView productName = (TextView) view.findViewById(R.id.item_product_name);
		TextView productPrice = (TextView) view.findViewById(R.id.item_product_price);
		
		if (product != null) {
			productName.setText(product.getNombre().toUpperCase());
			productPrice.setText("S/. " + product.getPrecio());
			productImage.setImageUrl(product.getRutaImagen());
		}
		
		return view;
	}
}
