package com.architsoni.faceless;

import java.util.ArrayList;

public class Chat {
    private String uname;
    private String rname;
    private ArrayList<Message> msges;

    public Chat(String uname, String rname, ArrayList<Message> msges) {
        this.uname = uname;
        this.rname = rname;
        this.msges = msges;
    }

    public Chat(){
    }
    public ArrayList<Message> getMsges() {
        return msges;
    }

    public void setMsges(ArrayList<Message> msges) {
        this.msges = msges;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
