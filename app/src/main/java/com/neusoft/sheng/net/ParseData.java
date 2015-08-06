package com.neusoft.sheng.net;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseData
{

	public ArrayList<String> parseSchool(String data)
	{
		ArrayList<String> res_data = new ArrayList<String>();

		try
		{
			JSONObject jsonobj = new JSONObject(data);
			JSONArray jsondata = jsonobj.getJSONArray("result");
			for (int i = 0; i < jsondata.length(); i++)
			{
				JSONObject datajsonobj = jsondata.getJSONObject(i);

				res_data.add(datajsonobj.getString("school_name"));

			}
		} catch (JSONException e)
		{

			e.printStackTrace();
		}
		return res_data;
	}
	
	
	public String parseReg(String data) {
		
		try {
			JSONObject jsonObject = new JSONObject(data);
			
			String state = jsonObject.getString("state");
            
			return state;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return "";
		
	}
	
	public ArrayList<String> parseBuild(String data) {
		ArrayList<String>buildes=new ArrayList<String>();
		try {
			JSONObject JSONObjectsrc = new JSONObject(data);
			
			JSONArray builde_json = JSONObjectsrc.getJSONArray("location");
			
			
			for (int i = 0; i < builde_json.length(); i++)
			{
				JSONObject datajsonobj = builde_json.getJSONObject(i);

				buildes.add(datajsonobj.getString("location"));

			}
            
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return buildes;
		
	}
	
	public ArrayList<String> parseFloor(String data,int position) {
		ArrayList<String>floors=new ArrayList<String>();
		try {
			JSONObject JSONObjectsrc = new JSONObject(data);
			
			JSONArray floor_json = JSONObjectsrc.getJSONArray("number");
			
			
			 floor_json = JSONObjectsrc.getJSONArray("number");
			 
			 
			
			
				JSONObject datajsonobj = floor_json.getJSONObject(position);
				
				JSONArray da=datajsonobj.getJSONArray("number");
				for (int j = 0; j < da.length(); j++)
				{
					JSONObject da2=da.getJSONObject(j);
					floors.add(da2.getString("number"));
				}
				
			
			
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return floors;
		
	}
	

	
	

}
