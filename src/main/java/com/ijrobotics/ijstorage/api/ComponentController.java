package com.ijrobotics.ijstorage.api;


import com.ijrobotics.ijstorage.api.dtos.ComponentInfo;
import com.ijrobotics.ijstorage.api.dtos.ComponentSimpleInfo;
import com.ijrobotics.ijstorage.api.dtos.NewComponent;
import com.ijrobotics.ijstorage.api.dtos.UpdateComponent;
import com.ijrobotics.ijstorage.api.exceptions.CustomExceptionHandler;
import com.ijrobotics.ijstorage.domain.Component;
import com.ijrobotics.ijstorage.domain.MovementLogs;
import com.ijrobotics.ijstorage.domain.enums.MovementType;
import com.ijrobotics.ijstorage.repo.ComponentRepository;
import com.ijrobotics.ijstorage.repo.MovementLogsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("components")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ComponentController {
    private final ComponentRepository componentRepository;
    private final MovementLogsRepository movementLogsRepository;
    protected Logger logger = Logger.getLogger(ComponentController.class.getName());

    public ComponentController(ComponentRepository componentRepository, MovementLogsRepository movementLogsRepository) {
        super();
        this.componentRepository = componentRepository;
        this.movementLogsRepository = movementLogsRepository;
    }

    @PostMapping
    public ResponseEntity<Component> createComponent(@RequestBody NewComponent component) {
        Optional<Component> componentOptional = componentRepository.findByExternalId(component.getId());
        if (componentOptional.isPresent()) {
            throw new CustomExceptionHandler(HttpStatus.NOT_FOUND,
                    "The component with id: " + component.getId() + " does already exist");
        } else {
            componentOptional =
                    componentRepository.findByExternalIdAndDepartmentAndProjectOrderByDepartmentAscProjectAsc(
                            component.getId(), component.getDepartment().toLowerCase(), component.getProject().toLowerCase());
            if (componentOptional.isPresent()) {
                throw new CustomExceptionHandler(HttpStatus.NOT_FOUND,
                        "The component with id: " + component.getId() +
                                " , department: " + component.getDepartment().toLowerCase() +
                                " , project: " + component.getProject().toLowerCase() + " does already exist");
            } else {
                Component newComponent = new Component(component.getId(), component.getDescription(),
                        component.getPartNo(), component.getDepartment().toLowerCase(), component.getProject().toLowerCase(),
                        component.getStock(), component.getMaxStock(), component.getMinStock());
                return new ResponseEntity<>(componentRepository.save(newComponent), HttpStatus.CREATED);
            }
        }
    }

    @GetMapping("/{id}/{department}/{project}")
    public ResponseEntity<ComponentInfo> getComponentByQR(@PathVariable("id") long id,
                                                          @PathVariable("department") String department,
                                                          @PathVariable("project") String project) {
        Optional<Component> componentOptional =
                componentRepository.findByExternalIdAndDepartmentAndProjectOrderByDepartmentAscProjectAsc(
                        id, department.toLowerCase(), project.toLowerCase());

        if (componentOptional.isPresent()) {
            return new ResponseEntity<>(toComponentInfo(componentOptional.get()), HttpStatus.OK);
        } else {
            throw new CustomExceptionHandler(HttpStatus.NOT_FOUND,
                    "The component with  id: " + id +
                            " , department: " + department.toLowerCase() +
                            " , project: " + project.toLowerCase() + " does not exist");
        }
    }

    @GetMapping
    public ResponseEntity<List<ComponentSimpleInfo>> getAllComponents() {
        return new ResponseEntity<>(
                StreamSupport.stream(componentRepository.findAllByOrderByDepartmentAscProjectAsc().spliterator(), false)
                        .collect(Collectors.toList()).stream().map(this::toComponentSimpleInfo).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @PutMapping("/{id}/{department}/{project}")
    public ResponseEntity<Component> updateComponent(@PathVariable("id") long id,
                                                     @PathVariable("department") String department,
                                                     @PathVariable("project") String project, @RequestBody UpdateComponent component) {
        Optional<Component> componentOptional =
                componentRepository.findByExternalIdAndDepartmentAndProjectOrderByDepartmentAscProjectAsc(
                        id, department.toLowerCase(), project.toLowerCase());
        if (componentOptional.isPresent()) {

            if (component.getDescription() != null) {
                componentOptional.get().setDescription(component.getDescription());
            }
            if (component.getPartNo() != null) {
                componentOptional.get().setPartNo(component.getPartNo());
            }
            if (component.getMaxStock() != null) {
                componentOptional.get().setMaxStock(component.getMaxStock());
            }
            if (component.getMinStock() != null) {
                componentOptional.get().setMinStock(component.getMinStock());
            }
            return new ResponseEntity<>(componentRepository.save(componentOptional.get()), HttpStatus.CREATED);
        } else {
            throw new CustomExceptionHandler(HttpStatus.NOT_FOUND,
                    "The component with id: " + id +
                            " , department: " + department.toLowerCase() +
                            " , project: " + project.toLowerCase() + " does not exist");
        }
    }

    @PutMapping("/{id}/{department}/{project}/{newStock}")
    public ResponseEntity<Component> updateComponentStock(@PathVariable("id") long id,
                                                          @PathVariable("department") String department
            , @PathVariable("project") String project, @PathVariable("newStock") long newStock) {
        Optional<Component> componentOptional =
                componentRepository.findByExternalIdAndDepartmentAndProjectOrderByDepartmentAscProjectAsc(
                        id, department.toLowerCase(), project.toLowerCase());
        if (componentOptional.isPresent()) {
            if (newStock == componentOptional.get().getStock()) {
                movementLogsRepository.save(
                        new MovementLogs(
                                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                                componentOptional.get().getStock(),
                                newStock,
                                componentOptional.get().getExternalId(),
                                MovementType.MAINTAIN));
            }else{
                if (newStock < componentOptional.get().getStock()) {
                    movementLogsRepository.save(
                            new MovementLogs(
                                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                                    componentOptional.get().getStock(),
                                    newStock,
                                    componentOptional.get().getExternalId(),
                                    MovementType.SUBTRACT));
                }  else {
                    movementLogsRepository.save(
                            new MovementLogs(
                                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                                    componentOptional.get().getStock(),
                                    newStock,
                                    componentOptional.get().getExternalId(),
                                    MovementType.ADD));
                }
            }


            componentOptional.get().setStock(newStock);
            return new ResponseEntity<>(componentRepository.save(componentOptional.get()), HttpStatus.CREATED);
        } else {
            throw new CustomExceptionHandler(HttpStatus.NOT_FOUND,
                    "The component with id: " + id +
                            " , department: " + department.toLowerCase() +
                            " , project: " + project.toLowerCase() + " does not exist");
        }
    }


    @ExceptionHandler(CustomExceptionHandler.class)
    public ResponseEntity handleException(CustomExceptionHandler e) {
        // log exception
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }

    private ComponentInfo toComponentInfo(Component component) {
        List<MovementLogs> movementLogs = movementLogsRepository.findTop10ByComponentIdOrderByTimeStampDesc(component.getExternalId());
        return new ComponentInfo(
                component.getExternalId(),
                component.getDescription(),
                component.getDepartment(),
                component.getProject(),
                component.getPartNo(),
                component.getStock(),
                component.getMaxStock(),
                component.getMinStock(),
                movementLogs
        );
    }

    private ComponentSimpleInfo toComponentSimpleInfo(Component component) {
        return new ComponentSimpleInfo(
                component.getExternalId(),
                component.getDepartment(),
                component.getProject(),
                component.getPartNo(),
                component.getStock()
        );
    }
}
