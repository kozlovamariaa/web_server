package com.springapp.dto;

/* Data transfer object for user
without password
 */

public class UserDTO {
    private String name;
    private String lastname;
    private String email;
    private int id;

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }
}
