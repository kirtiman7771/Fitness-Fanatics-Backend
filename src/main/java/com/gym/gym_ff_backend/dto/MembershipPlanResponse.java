package com.gym.gym_ff_backend.dto;

import java.math.BigDecimal;

public class MembershipPlanResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private Integer durationInDays;
    private String description;
    private Boolean active;

    public MembershipPlanResponse() {
    }

    public MembershipPlanResponse(
            Long id,
            String name,
            BigDecimal price,
            Integer durationInDays,
            String description,
            Boolean active) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.durationInDays = durationInDays;
        this.description = description;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getDurationInDays() {
        return durationInDays;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getActive() {
        return active;
    }
}