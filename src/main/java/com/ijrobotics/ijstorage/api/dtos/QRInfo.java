package com.ijrobotics.ijstorage.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class QRInfo implements Serializable {
    private long id;
    private String department;
    private String project;


    public QRInfo() {
        super();
    }
}
