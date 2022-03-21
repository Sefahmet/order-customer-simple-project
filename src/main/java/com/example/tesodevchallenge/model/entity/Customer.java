package com.example.tesodevchallenge.model.entity;

import com.example.tesodevchallenge.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "customers")
public class Customer {


    // ad-soyad ve telefon bilgileri

    @Id
    @Column(name = "id")
    private UUID id;
    /*
    @Column(unique = true, name = "uuid", nullable = false)
    private String uuid = UUID.randomUUID().toString().toUpperCase()
*/
    @NotNull(message = "{validation.messages.customer.firstname}")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "{validation.messages.customer.lastname}")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "{validation.messages.customer.email}")
    @Email(message = "validation.messages.customer.invalid-email")
    @Column(name = "email")
    private String email;

    @NotNull(message = "{validation.messages.customer.phoneNumber}")
    @Column(name = "phone_number")
    private String phoneNumber;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    // Cascade Type sample usage as CascadeType.REMOVE
    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Address> addresses;






}
