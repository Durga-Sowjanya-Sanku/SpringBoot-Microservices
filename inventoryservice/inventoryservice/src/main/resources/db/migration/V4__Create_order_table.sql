create table `order` (
    id bigint auto_increment primary key,
    total decimal(10,2) not null,
    quantity int not null,
    placed_at timestamp default current_timestamp,
    customer_id bigint ,
    event_id bigint,
    constraint fk_order_customer foreign key(customer_id) references customer(id) On delete set null,
    constraint fk_order_event foreign key(event_id) references event(id) On delete set null
);