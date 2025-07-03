create table venue (
    id bigint auto_increment primary key,
    name varchar(255) not null,
    address varchar(255) not null,
    total_capacity int not null
);

create table event (
    id bigint auto_increment primary key,
    name varchar(255) not null,
    venue_id bigint not null,
    total_capacity int not null,
    left_capacity int not null,
    constraint fk_event_venue foreign key (venue_id) references venue(id) On delete cascade
);