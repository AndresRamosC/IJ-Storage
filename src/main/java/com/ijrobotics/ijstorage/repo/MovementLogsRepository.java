package com.ijrobotics.ijstorage.repo;

import com.ijrobotics.ijstorage.domain.MovementLogs;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovementLogsRepository extends CrudRepository<MovementLogs,Long> {
    List<MovementLogs> findTop10ByComponentIdOrderByTimeStampDesc (long componentId);
}
