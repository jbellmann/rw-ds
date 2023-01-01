create table store (
    id bigserial primary key,
    project_id varchar(255) not null,
    store_id varchar(255) not null,
    requested_at timestamp not null,
    slug varchar(255) not null,
    state varchar(255) not null,
    uri varchar(255) not null
);