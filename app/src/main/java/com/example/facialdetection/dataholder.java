package com.example.facialdetection;

public class dataholder {
    String Name,Age,Gender;
    String Img;

    public dataholder(){

    }

    public dataholder(String name, String age, String gender,String image) {
        Name = name;
        Age = age;
        Gender = gender;
        Img = image;
    }

    public String getImage() {
        return Img;
    }

    public void setImage(String image) {
        Img = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
