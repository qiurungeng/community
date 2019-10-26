create table comment
(
	id bigint auto_increment,
	parent_id bigint not null,
	type int not null,
	like_count bigint default 0,
	gmt_create bigint,
	gmt_modified int,
	commentator int not null,
	constraint comment_pk
		primary key (id)
);
