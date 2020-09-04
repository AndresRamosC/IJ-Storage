package com.ijrobotics.ijstorage.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "COMPONENTS")
@Getter
@Setter
@NoArgsConstructor
public class Component {
    @Id
    @Column(name = "INTERNAL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long internalId;

    @Column(name = "EXTERNAL_ID")
    private long externalId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PART_NUMBER")
    private String partNo;

    @Column(name = "DEPARTMENT")
    private String department;

    @Column(name = "PROJECT")
    private String project;

    @Column(name = "STOCK")
    private long stock;

    @Column(name = "MAX_STOCK")
    private long maxStock;

    @Column(name = "MIN_STOCK")
    private long minStock;

    public Component(long id, String description, String partNo, String department, String project, long stock, long maxStock, long minStock) {
        this.externalId = id;
        this.description = description;
        this.partNo = partNo;
        this.department = department;
        this.project = project;
        this.stock = stock;
        this.maxStock = maxStock;
        this.minStock = minStock;
    }
}
