-- the first script for migration
CREATE TABLE Article (
 id bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 content varchar(5000),
 postedAt timestamp,
 title varchar(255)
);
CREATE TABLE Event (
 id bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 name varchar(255),
 eventmanager varchar(5000),
 eventdate varchar(255),
 eventtype varchar(255),
 location varchar(255)
);
 
CREATE TABLE Article_authorIds (
 Article_id bigint UNSIGNED not null,
 authorIds bigint UNSIGNED
);
CREATE TABLE Event_authorIds (
 Event_id bigint UNSIGNED not null,
 authorIds bigint UNSIGNED
);
 
CREATE TABLE User (
 id bigint UNSIGNED AUTO_INCREMENT PRIMARY KEY,
 fullname varchar(255),
 isAdmin boolean not null,
 password varchar(255),
 username varchar(255)
);
ALTER TABLE Article_authorIds
add foreign key (Article_id)
references Article(id);
 

ALTER TABLE Event_authorIds
add foreign key (Event_id)
references Event(id);
