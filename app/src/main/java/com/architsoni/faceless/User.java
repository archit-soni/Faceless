package com.architsoni.faceless;

import java.util.ArrayList;

public class User {
    private String fullName;
    private String gender;
    private String location;
    private String firstKiss;
    private String fantasy;
    private String foreplay;
    private String id;
    private int age;
    private String bio;
    private String uni;
    private String hobby;
    private ArrayList<String> likedBy;
    private ArrayList<String> liked;
    private ArrayList<String> dislikedBy;
    private ArrayList<String> disliked;
    private String drink;
    private String food;
    private String reln;
    private String watch;
    private String listen;
    private String username;
    private String password;
    public String toString(){
        return this.getFullName()+this.getBio()+this.getDrink()+this.getUni()+this.getUsername();
    }

    public User(String fullName, String password){
        this.setUsername(fullName);
        this.setPassword(password);
        this.setLocation("Location");
        this.setAge(22);
        this.setGender("Gender");
        this.setUni("University");
        this.setWatch("Watch");
        this.setFood("Food");
        this.setDrink("Drink");
        this.setHobby("Hobby");
        this.setReln("Relation");
        this.setListen("Listen");
        this.setBio("Bio");
        this.setFullName("Full Name");
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getReln() {
        return reln;
    }

    public void setReln(String reln) {
        this.reln = reln;
    }

    public String getWatch() {
        return watch;
    }

    public void setWatch(String watch) {
        this.watch = watch;
    }

    public String getListen() {
        return listen;
    }

    public void setListen(String listen) {
        this.listen = listen;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getDisliked() {
        return disliked;
    }

    public void setDisliked(ArrayList<String> disliked) {
        this.disliked = disliked;
    }

    public ArrayList<String> getDislikedBy() {
        return dislikedBy;
    }

    public void setDislikedBy(ArrayList<String> dislikedBy) {
        this.dislikedBy = dislikedBy;
    }

    public ArrayList<String> getLiked() {
        return liked;
    }

    public void setLiked(ArrayList<String> liked) {
        this.liked = liked;
    }

    public ArrayList<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(ArrayList<String> likedBy) {
        this.likedBy = likedBy;
    }

    public String getGender() {
        return gender;
    }

    public User(){}

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFirstKiss() {
        return firstKiss;
    }

    public void setFirstKiss(String firstKiss) {
        this.firstKiss = firstKiss;
    }

    public String getFantasy() {
        return fantasy;
    }

    public void setFantasy(String fantasy) {
        this.fantasy = fantasy;
    }

    public String getForeplay() {
        return foreplay;
    }

    public void setForeplay(String foreplay) {
        this.foreplay = foreplay;
    }
}
