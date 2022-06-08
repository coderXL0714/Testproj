package com.example.Testproj.Entity;

import java.io.Serializable;

public class OrganizationEntity implements Serializable {
    private int id;
    private String organizationName;
    private int number;

    public OrganizationEntity(int id, String organizationName, int number) {
        this.id = id;
        this.organizationName = organizationName;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
