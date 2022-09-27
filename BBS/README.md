# BBS

---
#### SQL
```
[계정 생성]
SQL> CREATE USER admin IDENTIFIED BY 1234;
```
```
[계정 권한 부여 (접속권한, 객체생성 권한)]
SQL> GRANT CONNECT, RESOURCE TO admin;
```
```
[생성 계정으로 접속]
SQL> CONN admin/1234;
```
```
[member TABLE 생성]
SQL> CREATE TABLE member (
id VARCHAR2(10) NOT NULL, 
pass VARCHAR2(10) NOT NULL, 
name VARCHAR2(30) NOT NULL, 
regidate DATE DEFAULT sysdate NOT NULL, 
PRIMARY KEY (id)
);
```
```
[board TABLE 생성]
SQL> CREATE TABLE board (
num NUMBER PRIMARY KEY, 
title VARCHAR2(200) NOT NULL, 
content VARCHAR2(2000) NOT NULL, 
id VARCHAR2(10) NOT NULL, 
postdate DATE DEFAULT sysdate NOT NULL, 
visitcount NUMBER(6)
);
```
```
[board TABLE의 외래키 설정]
SQL> ALTER TABLE board ADD CONSTRAINT board_mem_fk FOREIGN KEY (id) REFERENCES member (id);
```
```
[생성 TABLE 확인]
SQL>SELECT * FROM tab;
```
```
[일련번호 시퀸스 객체 생성 (시퀀스_순차 증가 순번을 반환하는 DB 객체)]
SQL> CREATE sequence seq_board_num increment BY 1 start WITH 1 minvalue 1 nomaxvalue nocycle nocache;
```
```
[member/board TABLE dummy data]
SQL> INSERT INTO member (id, pass, name) VALUES ('admin', '1234', '관리자');
SQL> INSERT INTO board (num, title, content, id, postdate, visitcount) 
VALUES (seq_board_num.nextval, '관리자가 작성한 제목입니다', '관리자가 작성한 내용입니다\n관리자가 작성한 내용입니다', 'admin', sysdate, 0);
```
```
[commit]
SQL> commit;
```
```
[attachedFile TABLE 생성]
SQL> CREATE TABLE myfile(
idx NUMBER PRIMARY KEY, 
name VARCHAR2(50) NOT NULL, 
title VARCHAR2(200) NOT NULL, 
cate VARCHAR2(30), 
ofile VARCHAR2(100) NOT NULL, 
sfile VARCHAR2(30) NOT NULL, 
postdate DATE DEFAULT sysdate NOT NULL
);
```
```
[mvcboard TABLE 생성]
SQL > CREATE TABLE mvcboard(
idx NUMBER PRIMARY KEY, 
name VARCHAR2(50) NOT NULL, 
title VARCHAR2(200) NOT NULL, 
content VARCHAR2(2000) NOT NULL, 
postdate DATE DEFAULT sysdate NOT NULL, 
ofile VARCHAR2(100), 
sfile VARCHAR2(30), 
downcount NUMBER(5) DEFAULT 0 NOT NULL, 
pass VARCHAR2(50) NOT NULL, 
visitcount NUMBER DEFAULT 0 NOT NULL
);

[mvcboard TABLE dummy data]
SQL > INSERT INTO mvcboard (idx, name, title, content, pass) VALUES (seq_board_num.nextval, '홍길동', '제목 1 홍길동', '내용 홍길동 홍길동 홍길동 홍길동', '1234');
SQL > INSERT INTO mvcboard (idx, name, title, content, pass) VALUES (seq_board_num.nextval, '홍길이', '제목 2 홍길이', '내용 홍길이 홍길이 홍길이 홍길이', '1234');
SQL > INSERT INTO mvcboard (idx, name, title, content, pass) VALUES (seq_board_num.nextval, '홍길준', '제목 3 홍길준', '내용 홍길준 홍길준 홍길준 홍길준', '1234');
SQL > INSERT INTO mvcboard (idx, name, title, content, pass) VALUES (seq_board_num.nextval, '홍길찬', '제목 4 홍길찬', '내용 홍길찬 홍길찬 홍길찬 홍길찬', '1234');
SQL > INSERT INTO mvcboard (idx, name, title, content, pass) VALUES (seq_board_num.nextval, '홍길희', '제목 5 홍길희', '내용 홍길희 홍길희 홍길희 홍길희', '1234');
```

---
#### Connection Pool을 위한 DB 연결
 - Tomcat의 conf 디렉토리의 server.xml과 context.xml 수정 필요

```
 [server.xml]
 // <GlobalNamingResources> 엘리먼트에 추가
 <Resource auth="Container"
              driverClassName="oracle.jdbc.OracleDriver"
              type="javax.sql.DataSource"
              initialSize="0"
              minIdle="5"
              maxTotal="20"
              maxIdle="20"
              maxWaitMillis="5000"
              url="jdbc:oracle:thin:@localhost:1521:xe"
              name="dbcp_myoracle"
              username="admin"
              password="1234" />
```
```
[context.xml]
// <context> 엘리먼트에 추가
<ResourceLink global="dbcp_myoracle" name="dbcp_myoracle" type="javax.sql.DataSource" />
```
