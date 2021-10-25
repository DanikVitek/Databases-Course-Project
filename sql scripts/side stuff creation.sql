set @total_users = 0;
create trigger total_users_increment after insert on `course_project`.`users`
for each row set @total_users = @total_users + 1;
create trigger total_users_decrement after insert on `course_project`.`users`
for each row set @total_users = @total_users - 1;

set @total_plugins = 0;
create trigger total_plugins_increment after insert on `course_project`.`plugins`
for each row set @total_plugins= @total_plugins + 1;
create trigger total_plugins_decrement after insert on `course_project`.`plugins`
for each row set @total_plugins = @total_plugins - 1;

delimiter //
create procedure publish_plugin (in pl_title varchar(200), in pl_description text, in pl_category int, in pl_authors json)
begin
	insert into `course_project`.`plugins` (title, plugin_description, category_id) values(pl_title, pl_description, pl_category);
    set @current_pl_id = -1;
    select id from `course_project`.`plugins` where title = pl_title into @current_pl_id;
    set @indx = 0;
	repeat
		set @author_id_ = json_extract(pl_authors, concat('$[', @indx, ']'));
		insert into `course_project`.`plugins_authors` values(@author_id_, @current_pl_id);
		set @indx = @indx  + 1;
		until @indx = json_length(pl_authors)
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

create procedure update_plugin (in pl_id bigint, in pl_title varchar(200), in pl_description text, in pl_category int, in pl_authors json)
begin
	update `course_project`.`plugins`
    set title = pl_title,
		plugin_description = pl_description,
		category_id = pl_category,
        plugin_description = pl_description,
        category_id = pl_category
	where id = pl_id;
    delete from `course_project`.`plugins_authors` where plugin_id = pl_id;
    set @indx = 0;
	repeat
		set @author_id_ = json_extract(pl_authors, concat('$[', @indx, ']'));
		insert into `course_project`.`plugins_authors` values(@author_id_, pl_id);
		set @indx = @indx  + 1;
		until @indx = json_length(pl_authors)
	end repeat;
end//
delimiter ;