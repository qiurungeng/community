## Spring Boot 项目学习，码农社区

## 脚本
```sql
create table USER
(
	ID INTEGER auto_increment,
	ACCOUNT VARCHAR(50),
	NAME VARCHAR(50),
	TOKEN CHAR(36),
	GMT_CREATE BIGINT,
	GMT_MODIFIED BIGINT,
	constraint USER_PK
		primary key (ID)
);
```
```bash
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
