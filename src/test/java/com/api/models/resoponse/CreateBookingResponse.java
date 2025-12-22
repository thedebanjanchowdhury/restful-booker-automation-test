package com.api.models.resoponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBookingResponse {
    @JsonProperty("bookingid")
    private int bookingid;

    @JsonProperty("bookingResponse")
    private BookingResponse bookingResponse;

    public CreateBookingResponse() {}

    public int getBookingid() {
        return bookingid;
    }

    public void setBookingid(int bookingid) {
        this.bookingid = bookingid;
    }

    public BookingResponse getBooking() {
        return bookingResponse;
    }

    public void setBooking(BookingResponse bookingResponse) {
        this.bookingResponse = bookingResponse;
    }
}
