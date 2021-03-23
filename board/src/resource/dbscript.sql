--board: 번호, 상위번호, 제목, 작성자, 등록일, 수정일, 조회수, 내용 
--				no  refno  subject writer regdte uptdte readcnt content 
--		boardfile : 번호, 파일명, 경로명, 내용, 생성일 
--					no  fname   pathname content credte 
SELECT * FROM BOARD;
ALTER TABLE board RENAME TO board01;
CREATE TABLE board(
	NO NUMBER PRIMARY KEY,
	refno NUMBER,
	subject varchar2(200),
	content varchar2(2000),
	writer varchar2(100),
	readcnt NUMBER,
	regdte DATE,
	uptdte DATE
);
SELECT * FROM board;
INSERT INTO board values(board_seq.nextval,0,'첫번째 글','내용','홍길동',0,sysdate,sysdate);
CREATE SEQUENCE board_seq
	START WITH 1 
	MINVALUE 1 
	INCREMENT BY 1
	MAXVALUE 9999999;

CREATE TABLE boardfile(
	NO NUMBER,
	fname varchar2(100),
	pathname varchar2(100),
	content varchar2(1000),
	credte date
);
