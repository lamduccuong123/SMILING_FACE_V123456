package com.example.vitinhtt2.myapplication;

public class User {
    public String name;
    public String image;
    public String role;
    public String dep;
    public String mobile;
    public int normal;
    public int happy;
    public int unhappy;
    public String email;


    public User() {
    }

    public User(String name, String role, String dep, String mobile, String image, String email ) {
        this.name = name;
        this.role = role;
        this.dep = dep;
        this.mobile = mobile;
        this.image = image;
        this.email = email;
    }
    public User(String name, String image, String role, String dep, String mobile, int normal, int happy, int unhappy) {
        this.name = name;
        this.image = image;
        this.role = role;
        this.dep = dep;
        this.mobile = mobile;
        this.normal = normal;
        this.happy = happy;
        this.unhappy = unhappy;
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getNormal() {
        return normal;
    }

    public void setNormal(int normal) {
        this.normal = normal;
    }

    public int getHappy() {
        return happy;
    }

    public void setHappy(int happy) {
        this.happy = happy;
    }

    public int getUnhappy() {
        return unhappy;
    }

    public void setUnhappy(int unhappy) {
        this.unhappy = unhappy;
    }
}
