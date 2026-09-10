package com.gym.gym_ff_backend.dto;

public class TrainerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String specialization;
    private String experience;
    private String bio;
    private Boolean active;

    public TrainerResponse(
            Long id,
            String firstName,
            String lastName,
            String email,
            String phone,
            String specialization,
            String experience,
            String bio,
            Boolean active) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
        this.experience = experience;
        this.bio = bio;
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

    public String getSpecialization() {
        return specialization;
    }

    public String getExperience() {
        return experience;
    }

    public String getBio() {
        return bio;
    }

    public Boolean getActive() {
        return active;
    }
}