package com.ijrobotics.ijstorage.api.dtos;

import com.ijrobotics.ijstorage.domain.MovementLogs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ComponentSimpleInfo {
    private long id;
    private String department;
    private String project;
    private String partNo;
    private long stock;

    public ComponentSimpleInfo() {
        super();
    }
}



