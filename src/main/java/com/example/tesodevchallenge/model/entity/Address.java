package com.example.tesodevchallenge.model.entity;

import com.example.tesodevchallenge.model.entity.Customer;
import com.example.tesodevchallenge.model.entity.Order;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "address")
public class Address implements Serializable {

    @Id
    @Column(name = "id")
    private UUID id;

    @NotBlank(message = "{validation.messages.address.addressLine.not-null}")
    @Column(name = "address_line")
    private String addressLine;

    @NotBlank(message = "{validation.messages.address.city.not-null}")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "{validation.messages.address.country.not-null}")
    @Column(name = "country")
    private String country;

    @Column(name = "city_code")
    private Integer cityCode;


    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "address", cascade = CascadeType.ALL)
    private List<Order> orders;


    @JsonBackReference
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Address(String addressLine, String city, String country) {
        this.addressLine = getAddressLine();
        this.city = city;
        this.country = country;
    }

    public String dbFormat() {
        return addressLine + "/" + city + "/" + country;
    }



}

