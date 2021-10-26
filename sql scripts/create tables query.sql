drop schema if exists `course_project`;
create schema if not exists `course_project`;

create table if not exists `course_project`.`users`(
	id bigint not null auto_increment,
    nickname varchar(50) not null unique,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    email varchar(254) not null unique,
    `password` varchar(1000) not null,
    `role` enum('user', 'moderator', 'admin') not null default 'user',
    registration_time timestamp not null default current_timestamp,
    constraint account_pk primary key (id),
    constraint email_chk check(email like '%@%')
);
create table if not exists `course_project`.`banned_users`(
	user_id bigint not null,
    reason varchar(200),
    constraint banned_user_pk primary key (user_id),
    constraint banned_user_fk foreign key (user_id) references `course_project`.`users` (id)
);
create table if not exists `course_project`.`categories`(
	id smallint not null auto_increment,
    title varchar(30) not null unique,
    constraint category_pk primary key (id)
);
create table if not exists `course_project`.`plugins`(
	id bigint not null auto_increment,
    title varchar(200) not null unique,
	plugin_description text, -- 2^16 + 2 bytes (mediumtext is 2^24+3 bytes)
    category_id smallint null,
    icon mediumblob null, -- 16777215 bytes = 16.78 Mb
    constraint plugin_pk primary key (id),
    constraint plugins_categories_fk foreign key (category_id) references `course_project`.`categories` (id) on delete set null on update cascade
);
create table if not exists `course_project`.`plugin_authors`(
	user_id bigint not null,
    plugin_id bigint not null,
    constraint plugin_author_pk primary key (user_id, plugin_id),
    constraint author_users_fk foreign key (user_id) references `course_project`.`users` (id) on delete cascade on update cascade,
    constraint plugins_fk foreign key (plugin_id) references `course_project`.`plugins` (id) on delete cascade on update cascade
);
create table if not exists `course_project`.`plugin_versions`(
    plugin_id bigint not null,
    version_title varchar(20) not null,
    upload_state enum('Processing', 'Accepted', 'Denied') not null default 'Processing',
    `file` longblob not null, -- 4294967295 bytes = 4294.97 Mb = 4.295 Gb
    upload_time timestamp not null default current_timestamp,
    constraint plugin_version_pk primary key (plugin_id, version_title),
    constraint plugin_version_fk foreign key (plugin_id) references `course_project`.`plugins` (id) on delete cascade on update cascade
);
create table if not exists `course_project`.`plugin_deny_reasons`(
	plugin_id bigint not null,
    plugin_version varchar(20) not null,
    reason varchar(200),
    constraint deny_reason_pk primary key (plugin_id, plugin_version),
    constraint deny_reason_fk foreign key (plugin_id, plugin_version) references `course_project`.`plugin_versions` (plugin_id, version_title) on delete cascade on update cascade
);
create table if not exists `course_project`.`purchased_plugins`(
	user_id bigint not null,
    plugin_id bigint not null,
    constraint purchased_plugin_pk primary key (user_id, plugin_id),
    constraint purchase_user_fk foreign key (user_id) references `course_project`.`users` (id),
    constraint purchase_plugin_fk foreign key (plugin_id) references `course_project`.`plugins` (id)
);
create table if not exists `course_project`.`game_versions`(
	id smallint not null auto_increment,
    version_title varchar(20) not null unique,
    constraint game_version_pk primary key (id)
);
insert into `course_project`.`game_versions` (version_title) values
	("1.7.x"), ("1.8.x"), ("1.9.x"), ("1.10.x"), ("1.11.x"), ("1.12.x"), ("1.13.x"), ("1.14.x"), ("1.15.x"), ("1.16.x"), ("1.17.x"), ("1.18.x");
create table if not exists `course_project`.`supported_game_versions`(
	plugin_id bigint not null,
    plugin_version_title varchar(20) not null,
    game_version_id smallint not null,
    constraint supported_version_pk primary key (plugin_id, plugin_version_title, game_version_id),
    constraint supported_plugin_version_fk foreign key (plugin_id, plugin_version_title) references `course_project`.`plugin_versions` (plugin_id, version_title) on delete cascade on update cascade,
    constraint supported_game_version_fk foreign key (game_version_id) references `course_project`.`game_versions` (id)
);
create table if not exists `course_project`.`plugin_ratings`(
	plugin_id bigint not null,
    user_id bigint not null,
    rating tinyint(1) not null,
    constraint rating_pk primary key (plugin_id, user_id),
    constraint rating_plugin_fk foreign key (plugin_id) references `course_project`.`plugins` (id) on delete cascade on update cascade,
    constraint rating_user_fk foreign key (user_id) references `course_project`.`users` (id) on delete cascade on update cascade
);
create table if not exists `course_project`.`tags`(
	id bigint not null auto_increment,
    title varchar(30) not null unique,
    constraint tag_pk primary key (id)
);
create table if not exists `course_project`.`plugin_tags`(
	plugin_id bigint not null,
    tag_id bigint not null,
    constraint plugin_tag_pk primary key (plugin_id, tag_id),
    constraint tag_tag_fk foreign key (tag_id) references `course_project`.`tags` (id),
    constraint tag_plugin_fk foreign key (plugin_id) references `course_project`.`plugins` (id)
);
create table if not exists `course_project`.`comments`(
	id bigint not null auto_increment,
    user_id bigint not null,
    content varchar(300) not null,
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