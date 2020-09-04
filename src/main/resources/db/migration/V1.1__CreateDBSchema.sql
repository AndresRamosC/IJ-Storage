create table components (
    internal_id bigint AUTO_INCREMENT,
    department varchar(255),
    description varchar(255),
    external_id bigint,
    max_stock bigint,
    min_stock bigint,
    part_number varchar(255),
    project varchar(255),
    stock bigint,
    primary key (internal_id));

create table movements_logs (
    movement_id bigint AUTO_INCREMENT,
    component_id bigint,
    movement_log_type integer,
    new_stock bigint,
    old_stock bigint,
    time_stamp varchar(255),
    primary key (movement_id));
