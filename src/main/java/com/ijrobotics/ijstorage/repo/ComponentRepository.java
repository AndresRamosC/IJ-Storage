package com.ijrobotics.ijstorage.repo;

import com.ijrobotics.ijstorage.domain.Component;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ComponentRepository extends CrudRepository<Component,Long> {
    Optional<Component> findByExternalIdAndDepartmentAndProjectOrderByDepartmentAscProjectAsc(Long aLong,String department,String project);
    Optional<Component> findByExternalId(Long aLong);
    Iterable<Component> findAllByOrderByDepartmentAscProjectAsc();
}
