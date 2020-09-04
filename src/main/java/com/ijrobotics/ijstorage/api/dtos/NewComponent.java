package com.ijrobotics.ijstorage.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class NewComponent implements Serializable {
    private long id;
    private String department;
    private String project;
    private String partNo;
    private String description;
    private long stock;
    private long maxStock;
    private long minStock;

    public NewComponent() {
        super();
    }
}
