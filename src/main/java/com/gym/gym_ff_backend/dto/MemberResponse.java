package com.gym.gym_ff_backend.dto;

import java.time.LocalDate;

public class MemberResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;
    private String gender;
    private String address;
    private LocalDate joinDate;
    private Boolean active;

    public MemberResponse() {
    }

    public MemberResponse(
            Long id,
            String firstName,
            String lastName,
            String email,
            String phone,
            LocalDate dateOfBirth,
            String gender,
            String address,
            LocalDate joinDate,
            Boolean active) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.joinDate = joinDate;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public Boolean getActive() {
        return active;
    }
}