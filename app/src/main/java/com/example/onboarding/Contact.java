package com.example.onboarding;

public class Contact {
    private String name,phone, bio;
    private int photo;

    public Contact(String name, String phone, int photo, String bio) {
        this.name = name;
        this.phone = phone;
        this.photo = photo;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }
}
