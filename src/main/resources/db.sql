use mybatis ;
-- 字典表
create table if not EXISTS dict_c1(
  id int primary key auto_increment ,
  name varchar(50) not null ,
  comm varchar(200)
) ;
create table if not EXISTS  dict_c2(
  id int primary key auto_increment ,
  name varchar(50) not null ,
  comm varchar(200) ,
  c1 int
) ;
-- 创建文章表
create table if not EXISTS articles (
  id int primary key  auto_increment ,
  title varchar(200) not null,
  publishtime varchar(20) ,
  content varchar(10000)  not null ,
  tag varchar(20) ,
  peoples int ,
  c1 int ,
  c2 int ,
  other1 varchar(200) ,
  other2 varchar(200) ,
  other3 varchar(200)
)