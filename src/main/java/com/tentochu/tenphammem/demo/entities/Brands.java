package com.tentochu.tenphammem.demo.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.OneToMany;

import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brands implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brandName")
    private String brandName;

    @OneToMany(mappedBy = "brands", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Products> products;

}
