SELECT * FROM SCHEDULE;
SELECT * FROM SCHEDULE_CATEGORY;
SELECT * FROM STUDENT;
SELECT * FROM PROFESSOR;


INSERT INTO schedule_category (category_id, category_name, category_color)
VALUES (category_seq.nextval, '학교', '#FF5733');

INSERT INTO schedule_category (category_id, category_name, category_color)
VALUES (category_seq.nextval, '과제', '#FF5733');
