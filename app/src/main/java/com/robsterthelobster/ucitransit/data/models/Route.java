package com.robsterthelobster.ucitransit.data.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by robin on 10/16/2017.
 */

public class Route extends RealmObject {


    @SerializedName("description")
    public String description;
    @SerializedName("short_name")
    public String shortName;
    @SerializedName("route_id")
    @PrimaryKey
    public String routeId;
    @SerializedName("url")
    public String url;
    @SerializedName("segments")
    transient String segments = "";
    @SerializedName("is_active")
    public Boolean isActive;
    @SerializedName("agency_id")
    public Integer agencyId;
    @SerializedName("text_color")
    public String textColor;
    @SerializedName("long_name")
    public String longName;
    @SerializedName("stops")
    public RealmList<String> stops = null;
    @SerializedName("is_hidden")
    public Boolean isHidden;
    @SerializedName("type")
    public String type;
    @SerializedName("color")
    public String color;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public RealmList<String>  getStops() {
        return stops;
    }

    public void setStops(RealmList<String> stops) {
        this.stops = stops;
    }

    public Boolean getHidden() {
        return isHidden;
    }

    public void setHidden(Boolean hidden) {
        isHidden = hidden;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
