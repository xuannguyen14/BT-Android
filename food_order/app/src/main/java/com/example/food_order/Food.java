package com.example.food_order;

public class Food {
    private String nameFoodBig;
    private int imageFoodBig;
    private String details;
    private int imageFoodSmall;

    public String getNameFoodBig() {
        return nameFoodBig;
    }

    public void setNameFoodBig(String nameFoodBig) {
        this.nameFoodBig = nameFoodBig;
    }

    public int getImageFoodBig() {
        return imageFoodBig;
    }

    public void setImageFoodBig(int imageFoodBig) {
        this.imageFoodBig = imageFoodBig;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getImageFoodSmall() {
        return imageFoodSmall;
    }

    public void setImageFoodSmall(int imageFoodSmall) {
        this.imageFoodSmall = imageFoodSmall;
    }

    public Food(String nameFoodBig, int imageFoodBig, String details, int imageFoodSmall) {
        this.nameFoodBig = nameFoodBig;
        this.imageFoodBig = imageFoodBig;
        this.details = details;
        this.imageFoodSmall = imageFoodSmall;
    }
}
