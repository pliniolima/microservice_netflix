package io.micro.musicratingservice.models;

import java.util.List;

public class UserRating {

    public UserRating() {
    }

    public UserRating(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }

    private List<Rating> ratingList;

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
    }
}
