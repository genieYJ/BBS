# BBS

---
#### SQL
```
[계정 생성]
SQL> CREATE USER admin IDENTIFIED BY 1234;

[계정 권한 부여 (접속권한, 객체생성 권한)]
SQL> GRANT CONNECT, RESOURCE TO admin;

[생성 계정으로 접속]
SQL> CONN admin/1234;

[member TABLE 생성]
SQL> CREATE TABLE member (
id VARCHAR2(10) NOT NULL, 
pass VARCHAR2(10) NOT NULL, 
name VARCHAR2(30) NOT NULL, 
regidate DATE DEFAULT sysdate NOT NULL, 
PRIMARY KEY (id)
);

[board TABLE 생성]
SQL> CREATE TABLE board (
num NUMBER PRIMARY KEY, 
title VARCHAR2(200) NOT NULL, 
content VARCHAR2(2000) NOT NULL, 
id VARCHAR2(10) NOT NULL, 
postdate DATE DEFAULT sysdate NOT NULL, 
visitcount NUMBER(6)
);

[board TABLE의 외래키 설정]
SQL> ALTER TABLE board ADD CONSTRAINT board_mem_fk FOREIGN KEY (id) REFERENCES member (id);

[생성 TABLE 확인]
SQL>SELECT * FROM tab;

[일련번호 시퀸스 객체 생성 (시퀀스_순차 증가 순번을 반환하는 DB 객체)]
SQL> CREATE sequence seq_board_num increment BY 1 start WITH 1 minvalue 1 nomaxvalue nocycle nocache;

[member/board TABLE dummy data]
SQL> INSERT INTO member (id, pass, name) VALUES ('admin', '1234', '관리자');
SQL> INSERT INTO board (num, title, content, id, postdate, visitcount) 
VALUES (seq_board_num.nextval, '관리자가 작성한 제목입니다', '관리자가 작성한 내용입니다\n관리자가 작성한 내용입니다', 'admin', sysdate, 0);

[commit]
SQL> commit;
```