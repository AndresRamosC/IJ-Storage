package com.ijrobotics.ijstorage.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateComponent {
    private String description;
    private Long maxStock;
    private Long minStock;
    private String partNo;

    public UpdateComponent() {
        super();
    }
}
