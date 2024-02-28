create table  if not exists users(
    id serial not null,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(60) not null unique,
    password varchar(200) not null,
    role varchar(10) check (role in ('CUSTOMER','ADMIN')),
    enabled boolean,
    locked boolean,
    primary key(id)
    );

create table if not exists categories (
        id serial not null,
        name varchar(50) not null,
        status boolean,
        primary key(id)
    );

create table if not exists email_verification_tokens (
        id serial not null,
        user_id serial not null unique,
        created_at timestamp,
        expire_at timestamp,
        verified_at timestamp,
        token varchar(255),
        primary key (id),
        foreign key (user_id) references users(id)
    );

   create table if not exists products (
        id serial not null,
        category_id serial not null,
        name varchar(50) not null,
        description varchar(255) not null,
        price numeric not null,
        image_url varchar(255),
        status boolean not null,
        primary key (id),
        foreign key (category_id) references categories(id)
    );

create table if not exists inventory (
        id serial not null,
        product_id serial not null,
        quantity integer,
        created_on timestamp,
        expired_on timestamp,
        primary key (id),
        foreign key (product_id) references products(id)
    );

create table if not exists orders (
        id serial not null,
        user_id serial not null,
        total_price numeric not null,
        order_timestamp timestamp,
        status varchar(15) check (status in ('PROCESSING','SHIPPED','DELIVERED')),
        primary key (id),
        foreign key (user_id) references users(id)
    );

create table if not exists order_product (
        id serial not null,
        order_id serial,
        product_id serial,
        quantity integer,
        sub_total numeric not null,
        primary key (id),
        foreign key (order_id) references orders(id),
        foreign key (product_id) references products(id)
);
