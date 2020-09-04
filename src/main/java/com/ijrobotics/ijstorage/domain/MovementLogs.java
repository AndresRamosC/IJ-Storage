package com.ijrobotics.ijstorage.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ijrobotics.ijstorage.domain.enums.MovementType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "MOVEMENTS_LOGS")
@Getter
@Setter
@NoArgsConstructor
public class MovementLogs {
    @Id
    @Column(name = "MOVEMENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long movementId;

    @Column(name = "TIME_STAMP")
    private String timeStamp;

    @Column(name = "OLD_STOCK")
    private long oldStock;

    @Column(name = "NEW_STOCK")
    private long newStock;

    @Column(name = "COMPONENT_ID")
    private long componentId;

    @Column(name = "MOVEMENT_LOG_TYPE")
    private MovementType movement_log_type;


    public MovementLogs(String timeStamp, long oldStock, long newStock, long componentId, MovementType movement_log_type) {
        this.timeStamp = timeStamp;
        this.oldStock = oldStock;
        this.newStock = newStock;
        this.componentId = componentId;
        this.movement_log_type = movement_log_type;
    }
}
