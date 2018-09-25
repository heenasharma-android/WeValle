package com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sumit on 13-Feb-18.
 */

public class EventModel implements Parcelable {

    private String EventId;
    private String EventTitle;
    private String EventDescription;
    private String EventContact;
    private String EventVenue;
    private String EventLat;
    private String EventLong;
    private String EventHeritage;
    private String EventImageName;
    private String EventDate;
    private String EventStatus;
    private String EventImageUrl;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.EventId);
        dest.writeString(this.EventTitle);
        dest.writeString(this.EventDescription);
        dest.writeString(this.EventContact);
        dest.writeString(this.EventVenue);
        dest.writeString(this.EventLat);
        dest.writeString(this.EventLong);
        dest.writeString(this.EventHeritage);
        dest.writeString(this.EventImageName);
        dest.writeString(this.EventDate);
        dest.writeString(this.EventStatus);
        dest.writeString(this.EventImageUrl);
    }

    public EventModel() {
    }

    protected EventModel(Parcel in) {
        this.EventId = in.readString();
        this.EventTitle = in.readString();
        this.EventDescription = in.readString();
        this.EventContact = in.readString();
        this.EventVenue = in.readString();
        this.EventLat = in.readString();
        this.EventLong = in.readString();
        this.EventHeritage = in.readString();
        this.EventImageName = in.readString();
        this.EventDate = in.readString();
        this.EventStatus = in.readString();
        this.EventImageUrl = in.readString();
    }

    public static final Creator<EventModel> CREATOR = new Creator<EventModel>() {
        @Override
        public EventModel createFromParcel(Parcel source) {
            return new EventModel(source);
        }

        @Override
        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public String getEventTitle() {
        return EventTitle;
    }

    public void setEventTitle(String eventTitle) {
        EventTitle = eventTitle;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    public String getEventContact() {
        return EventContact;
    }

    public void setEventContact(String eventContact) {
        EventContact = eventContact;
    }

    public String getEventVenue() {
        return EventVenue;
    }

    public void setEventVenue(String eventVenue) {
        EventVenue = eventVenue;
    }

    public String getEventLat() {
        return EventLat;
    }

    public void setEventLat(String eventLat) {
        EventLat = eventLat;
    }

    public String getEventLong() {
        return EventLong;
    }

    public void setEventLong(String eventLong) {
        EventLong = eventLong;
    }

    public String getEventHeritage() {
        return EventHeritage;
    }

    public void setEventHeritage(String eventHeritage) {
        EventHeritage = eventHeritage;
    }

    public String getEventImageName() {
        return EventImageName;
    }

    public void setEventImageName(String eventImageName) {
        EventImageName = eventImageName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getEventStatus() {
        return EventStatus;
    }

    public void setEventStatus(String eventStatus) {
        EventStatus = eventStatus;
    }

    public String getEventImageUrl() {
        return EventImageUrl;
    }

    public void setEventImageUrl(String eventImageUrl) {
        EventImageUrl = eventImageUrl;
    }
}
