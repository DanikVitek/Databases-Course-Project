create table if not exists `course_project`.`users`(
	id bigint not null auto_increment,
    nickname varchar(50) not null unique,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(254) not null unique,
    `password` varchar(1000) not null,
    is_admin bool not null default false,
    registration_time timestamp not null default current_timestamp,
    constraint account_pk primary key (id),
    constraint email_chk check(email like '%@%')
);
create table if not exists `course_project`.`categories`(
	id int not null auto_increment,
    title varchar(30) not null unique,
    constraint category_pk primary key (id)
);
create table if not exists `course_project`.`plugins`(
	id bigint not null auto_increment,
    title varchar(200) not null unique,
	plugin_description text,
    category_id int null,
    publication_time timestamp not null default current_timestamp,
    upload_state enum('Processing', 'Accepted', 'Denied'),
    `file` longblob not null,
    constraint plugin_pk primary key (id),
    constraint plugins_categories_fk foreign key (category_id) references `course_project`.`categories` (id) on delete set null on update cascade
);
create table if not exists `course_project`.`plugins_authors`(
	user_id bigint not null,
    plugin_id bigint not null,
    constraint plugin_author_pk primary key (user_id, plugin_id),
    constraint author_users_fk foreign key (user_id) references `course_project`.`users` (id) on delete cascade on update cascade,
    constraint plugins_fk foreign key (plugin_id) references `course_project`.`plugins` (id) on delete cascade on update cascade
);
create table if not exists `course_project`.`comments`(
	id bigint not null auto_increment,
    user_id bigint not null,
    content text not null,
    publication_time timestamp not null default current_timestamp,
    constraint comment_pk primary key (id),
    constraint comment_users_fk foreign key (user_id) references `course_project`.`users` (id) on delete cascade on update cascade
);
create table if not exists `course_project`.`comment_responses`(
	parent_id bigint not null,
    response_id bigint not null,
    constraint comment_response_pk primary key (parent_id, response_id),
    constraint parent_comment_fk foreign key (parent_id) references `course_project`.`comments` (id) on delete cascade on update cascade,
    constraint response_comment_fk foreign key (response_id) references `course_project`.`comments` (id) on delete cascade on update cascade
);