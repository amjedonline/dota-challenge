
DROP TABLE IF EXISTS items;
CREATE TABLE items(
	match_id varchar(10),
	timestamp varchar(20),
	hero varchar(100),
	item varchar(50)
);

DROP TABLE IF EXISTS kills;
CREATE TABLE kills(
	match_id varchar(10),
	timestamp varchar(20),
	hero varchar(100),
	killed_hero varchar(100)
);

DROP TABLE IF EXISTS spells;
CREATE TABLE spells(
	match_id varchar(10),
	timestamp varchar(20),
	hero varchar(100),
	spell varchar(100),
	casted_on_hero varchar(100),
	level int
);

DROP TABLE IF EXISTS damages;
CREATE TABLE damages(
	match_id varchar(10),
	timestamp varchar(20),
	hero varchar(100),
	damaged_hero varchar(100),
	damaged_with varchar(100),
	damage_score int,
	score_after_damage int
);
