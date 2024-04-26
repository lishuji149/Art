package com.example.artconnect.service.user;

import com.example.artconnect.service.collection.CollectionDTO;
import lombok.Data;

import java.util.List;
@Data
public class ProfileInfo {
    private String username;
    private String avatarUrl;
    private String email;

    private List<CollectionDTO> collections;


    public ProfileInfo(String avatarUrl,String username, String email, List<CollectionDTO> collections) {
        this.avatarUrl=avatarUrl;
        this.username = username;
        this.email = email;
        this.collections = collections;
    }

    public ProfileInfo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
