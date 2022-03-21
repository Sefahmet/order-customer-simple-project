package com.example.tesodevchallenge.model.entity;

import com.example.tesodevchallenge.model.Address;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order {

    @Id
    private UUID id;

   /* @NotEmpty(message = "{validation.messages.order.quantity.not-null}")*/
    @Column(name = "quantity")
    private Integer quantity;

    @NotEmpty(message = "{validation.messages.order.price.not-null}")
    @Column(name = "price")
    private float price;

    @NotEmpty(message = "{validation.messages.order.status.not-null}")
    @Column(name = "status")
    private String status;


    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private Date createdAt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_date")
    private Date updatedAt;


    // RDBMS n-1 Customer and Order
    @JsonBackReference
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;


    // RDBMS n-n Product and Order
    @JsonIgnore
    @ManyToMany(mappedBy = "orders",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Product> products;

    //RDBMS n-1 Order and Address

    @JsonBackReference
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;



}
