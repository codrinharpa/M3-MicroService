package com.hazardmanager.users.models;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

/**
 * Created by Bogdan on 04-May-17.
 */
@Document(collection="Locations")
public class Location {

    @NotBlank
    @Size(max=50)
    private double latitude;

    @NotBlank
    @Size(max=50)
    private double longitude;

    @NotBlank
    @Size(max=50)
    private String alias;

    @Size(max=50)
    private String userId;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
