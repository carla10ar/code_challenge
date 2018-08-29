package com.qlee.code_challenge.model;

public class LocationContactInfo {

    private String timeZone;

    private Address address;

    private PhoneNumber phone;

    private PhoneNumber fax;

    private GPSCoordinates gps;

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PhoneNumber getPhone() {
        return phone;
    }

    public void setPhone(PhoneNumber phone) {
        this.phone = phone;
    }

    public PhoneNumber getFax() {
        return fax;
    }

    public void setFax(PhoneNumber fax) {
        this.fax = fax;
    }

    public GPSCoordinates getGps() {
        return gps;
    }

    public void setGps(GPSCoordinates gps) {
        this.gps = gps;
    }
}
