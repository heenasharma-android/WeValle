package com.models;

import java.util.ArrayList;

/**
 * Created by Sumit on 16-Feb-18.
 */

public class HomePageModel   {

    ArrayList<ArrayList<UserModel>> homeUsers;
    ArrayList<EventModel> homeEvents;

    public ArrayList<ArrayList<UserModel>> getHomeUsers() {
        return homeUsers;
    }

    public void setHomeUsers(ArrayList<ArrayList<UserModel>> homeUsers) {
        this.homeUsers = homeUsers;
    }

    public ArrayList<EventModel> getHomeEvents() {
        return homeEvents;
    }

    public void setHomeEvents(ArrayList<EventModel> homeEvents) {
        this.homeEvents = homeEvents;
    }
}
