delimiter //
create procedure publish_plugin 
	(in pl_title varchar(200), in pl_description text, in pl_category smallint, in pl_icon mediumblob, in pl_version varchar(20), in pl_file longblob, in pl_authors json)
begin
	declare current_pl_id bigint;
	declare indx bigint;
    declare author_id bigint;
    
	insert into `course_project`.`plugins` (title, description, category_id, icon) values(pl_title, pl_description, pl_category, pl_icon);
    select id from `course_project`.`plugins` where title = pl_title into current_pl_id;
    insert into `course_project`.`plugin_versions` (plugin_id, version_title, `file`) values(current_pl_id, pl_version, pl_file);
    set indx = 0;
	repeat
		set author_id = json_extract(pl_authors, concat('$[', indx, ']'));
		insert into `course_project`.`plugin_authors` values(author_id, current_pl_id);
		set indx = indx  + 1;
		until indx = json_length(pl_authors)
	end repeat;
end//

create procedure delete_plugin (in pl_title varchar(200))
begin
	delete from `course_project`.`plugins` where title = pl_title;
end//

create procedure delete_plugin_by_id (in pl_id bigint)
begin
	delete from `course_project`.`plugins` where id = pl_id;
end//

create procedure update_plugin (in pl_id bigint, in pl_title varchar(200), in pl_description text, in pl_category int, in pl_icon mediumblob, in pl_authors json)
begin
	declare indx bigint;
    declare author_id bigint;
    
	update `course_project`.`plugins`
    set title = pl_title,
		description = pl_description,
		category_id = pl_category,
        description = pl_description,
        category_id = pl_category,
        icon = pl_icon
	where id = pl_id;
    delete from `course_project`.`plugin_authors` where plugin_id = pl_id;
    set indx = 0;
	repeat
		set author_id = json_extract(pl_authors, concat('$[', indx, ']'));
		insert into `course_project`.`plugin_authors` values(author_id, pl_id);
		set indx = indx  + 1;
		until indx = json_length(pl_authors)
	end repeat;
end//

create function count_plugins ()
returns bigint
begin
	declare amount bigint;
	select count(1) from `course_project`.`plugins` into amount;
	return amount;
end//
delimiter ;