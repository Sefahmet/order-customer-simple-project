package com.example.tesodevchallenge.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "products")
public class Product {

    @Id
    private UUID id;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull(message = "Price Could not Empty!")
    @Column(name = "name")
    private String  name;

    @NotNull(message = "Stock Could not Empty")
    @Column(name = "stock")
    private Integer stock;


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    @JsonIgnore
    @JoinTable(name = "orders_products",
            joinColumns = { @JoinColumn(name = "products_id") },
            inverseJoinColumns = { @JoinColumn(name = "orders_id") })
    private List<Order> orders;
}
