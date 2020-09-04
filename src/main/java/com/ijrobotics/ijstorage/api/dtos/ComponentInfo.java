package com.ijrobotics.ijstorage.api.dtos;

import com.ijrobotics.ijstorage.domain.MovementLogs;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ComponentInfo {
    private long id;
    private String description;
    private String department;
    private String project;
    private String partNo;
    private long stock;
    private long maxStock;
    private long minStock;
    private List<MovementLogs> movementLogs;
}
