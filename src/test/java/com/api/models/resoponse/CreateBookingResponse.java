package com.api.models.resoponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBookingResponse {
    @JsonProperty("bookingid")
    private int bookingid;

    @JsonProperty("booking")
    private Booking booking;

    public CreateBookingResponse() {}

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
