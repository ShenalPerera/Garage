CREATE TABLE schedule_service
(
    schedule_id int not null,
    service_id  int not null,
    constraint FKbnbutw51pltwyv9v42f5mkmgw
        foreign key (schedule_id) references schedule (id),
    constraint FKmxx5m4q1bxsgt0w2j3hirp0ai
        foreign key (service_id) references service (id)
);