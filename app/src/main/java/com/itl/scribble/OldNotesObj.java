package com.itl.scribble;

import java.io.Serializable;

public class OldNotesObj implements Serializable {
    int id;
    int user_profile_id;
    String written_data;
    String created_on;


    public OldNotesObj(int id, int user_profile_id, String written_data, String created_on) {
        this.id = id;
        this.user_profile_id = user_profile_id;
        this.written_data = written_data;
        this.created_on = created_on;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_profile_id() {
        return user_profile_id;
    }

    public void setUser_profile_id(int user_profile_id) {
        this.user_profile_id = user_profile_id;
    }

    public String getWritten_data() {
        return written_data;
    }

    public void setWritten_data(String written_data) {
        this.written_data = written_data;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }
}
