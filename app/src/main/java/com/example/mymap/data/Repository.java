package com.example.mymap.data;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mymap.Model.Activities;
import com.example.mymap.Model.EntranceFees;
import com.example.mymap.Model.Images;
import com.example.mymap.Model.OperatingHours;
import com.example.mymap.Model.Park;
import com.example.mymap.Model.StandardHours;
import com.example.mymap.Model.Topics;
import com.example.mymap.controller.Appcontroller;
import com.example.mymap.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static List<Park> parkList=new ArrayList<>();
    public static void getParks(final Asyncresponse callback){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Util.ParkUrl,null, response -> {
            try {
                JSONArray jsonArray=response.getJSONArray("data");
                for (int i = 0; i <jsonArray.length() ; i++) {
                    Park park = new Park();
                    JSONObject jsonObject= jsonArray.getJSONObject(i);
                    park.setId(jsonObject.getString("id"));
                    park.setFullName(jsonObject.getString("fullName"));
                    park.setLatitude(jsonObject.getString("latitude"));
                    park.setLongitude(jsonObject.getString("longitude"));
                    park.setParkCode(jsonObject.getString("parkCode"));
                    park.setStates(jsonObject.getString("states"));
                    JSONArray ImageList= jsonObject.getJSONArray("images");
                    List<Images> list= new ArrayList<>();
                    for (int j = 0; j <ImageList.length() ; j++) {
                        Images images = new Images();
                        images.setCredit(ImageList.getJSONObject(j).getString("credit"));
                        images.setTitle(ImageList.getJSONObject(j).getString("title"));
                        images.setUrl(ImageList.getJSONObject(j).getString("url"));
                        list.add(images);
                    }
                        park.setImages(list);
                    park.setWeatherInfo(jsonObject.getString("weatherInfo"));
                    park.setDesignation(jsonObject.getString("designation"));
                    park.setName(jsonObject.getString("name"));
                    //Setup Activities
                    JSONArray activityArray = jsonObject.getJSONArray("activities");
                    List<Activities> activitiesList = new ArrayList<>();
                    for (int j = 0; j < activityArray.length() ; j++) {
                        Activities activities = new Activities();
                        activities.setId(activityArray.getJSONObject(j).getString("id"));
                        activities.setName(activityArray.getJSONObject(j).getString("name"));

                        activitiesList.add(activities);
                    }
                    park.setActivities(activitiesList);

                    //Topics
                    JSONArray topicsArray = jsonObject.getJSONArray("topics");
                    List<Topics> topicsList = new ArrayList<>();
                    for (int j = 0; j < topicsArray.length() ; j++) {
                        Topics topics = new Topics();
                        topics.setId(topicsArray.getJSONObject(j).getString("id"));
                        topics.setName(topicsArray.getJSONObject(j).getString("name"));
                        topicsList.add(topics);

                    }
                    park.setTopics(topicsList);

                    //Operating Hours
                    JSONArray opHours = jsonObject.getJSONArray("operatingHours");
                    List<OperatingHours> operatingHours = new ArrayList<>();
                    for (int j = 0; j < opHours.length() ; j++) {
                        OperatingHours op = new OperatingHours();
                        op.setDescription(opHours.getJSONObject(j).getString("description"));
                        StandardHours standardHours = new StandardHours();
                        JSONObject hours = opHours.getJSONObject(j).getJSONObject("standardHours");

                        standardHours.setWednesday(hours.getString("wednesday"));
                        standardHours.setMonday(hours.getString("monday"));
                        standardHours.setThursday(hours.getString("thursday"));
                        standardHours.setSunday(hours.getString("sunday"));
                        standardHours.setTuesday(hours.getString("tuesday"));
                        standardHours.setFriday(hours.getString("friday"));
                        standardHours.setSaturday(hours.getString("saturday"));
                        op.setStandardHours(standardHours);

                        operatingHours.add(op);

                    }

                    park.setOperatingHours(operatingHours);

                    park.setDirectionsInfo(jsonObject.getString("directionsInfo"));

                    park.setDescription(jsonObject.getString("description"));


                    //Entrance fees
                    JSONArray entranceFeesArray = jsonObject.getJSONArray("entranceFees");
                    List<EntranceFees> entranceFeesList = new ArrayList<>();
                    for (int j = 0; j < entranceFeesArray.length() ; j++) {
                        EntranceFees entranceFees = new EntranceFees();
                        entranceFees.setCost(entranceFeesArray.getJSONObject(j).getString("cost"));
                        entranceFees.setDescription(entranceFeesArray.getJSONObject(j).getString("description"));
                        entranceFees.setTitle(entranceFeesArray.getJSONObject(j).getString("title"));
                        entranceFeesList.add(entranceFees);

                    }
                    park.setEntranceFees(entranceFeesList);
                    park.setWeatherInfo(jsonObject.getString("weatherInfo"));
                    parkList.add(park);
                }
                if(null!=callback){
                    callback.processPark(parkList);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        },error -> {
           error.printStackTrace();
        });
        Appcontroller.getInstance().addrequestqueue(jsonObjectRequest);
    }
}
