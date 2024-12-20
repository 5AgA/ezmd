-- 시퀀스
CREATE SEQUENCE PROFESSOR_SEQ START WITH 30000000 INCREMENT BY 1;
CREATE SEQUENCE SCHEDULE_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE CATEGORY_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE INTERVIEW_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE PROFESSOR_REVIEW_SEQ START WITH 1 INCREMENT BY 1;

-- 테이블 정의
CREATE TABLE student (
    student_id NUMBER NOT NULL,
    name VARCHAR2(50) NOT NULL,
    email VARCHAR2(254) NOT NULL,
    password VARCHAR2(100) NOT NULL,
    phone VARCHAR2(15) NOT NULL,
    dept VARCHAR2(50) NOT NULL,
    grade NUMBER NOT NULL,
    deleted CHAR(1) DEFAULT 'N' NOT NULL,
    CONSTRAINT student_pk PRIMARY KEY (student_id),
    CHECK (deleted IN ('Y', 'N'))
);

CREATE TABLE professor (
    professor_id NUMBER NOT NULL,
    name VARCHAR2(50) NOT NULL,
    email VARCHAR2(254) NOT NULL,
    password VARCHAR2(100) NOT NULL,
    phone VARCHAR2(15) NOT NULL,
    dept VARCHAR2(50) NOT NULL,
    professor_office VARCHAR2(100) NOT NULL,
    deleted CHAR(1) DEFAULT 'N' NOT NULL,
    CONSTRAINT professor_pk PRIMARY KEY (professor_id),
    CHECK (deleted IN ('Y', 'N'))
);

CREATE TABLE schedule_category (
    category_id NUMBER NOT NULL,
    category_name VARCHAR2(50) NOT NULL,
    category_color VARCHAR2(20) NOT NULL,
    user_id NUMBER NOT NULL,
    CONSTRAINT category_pk PRIMARY KEY (category_id)
);

CREATE TABLE schedule (
    schedule_id NUMBER NOT NULL,
    schedule_title VARCHAR2(100) NOT NULL,
    schedule_start TIMESTAMP NOT NULL,
    schedule_end TIMESTAMP NOT NULL,
    schedule_repeat NUMBER NULL,
    schedule_place VARCHAR2(100) NULL,
    schedule_memo VARCHAR2(500) NULL,
    category_id NUMBER NOT NULL,
    user_id NUMBER NOT NULL,
    CONSTRAINT schedule_pk PRIMARY KEY (schedule_id)
);

CREATE TABLE interview (
    interview_id NUMBER NOT NULL,
    requested_date TIMESTAMP NOT NULL,
    interview_category VARCHAR2(50) NOT NULL,
    interview_note VARCHAR2(2000) NULL,
    interview_status VARCHAR2(10) DEFAULT 'pending' NOT NULL,
    interview_notice VARCHAR2(1000) NULL,
    is_completed CHAR(1) DEFAULT 'N' NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NULL,
    student_id NUMBER NOT NULL,
    professor_id NUMBER NOT NULL,
    CONSTRAINT interview_pk PRIMARY KEY (interview_id),
    CHECK (interview_status IN ('pending', 'approved', 'rejected')),
    CHECK (is_completed IN ('Y', 'N'))
);

CREATE TABLE interview_result (
    interview_id NUMBER NOT NULL,
    interview_topic VARCHAR2(100) NOT NULL,
    interview_summary VARCHAR2(2000) NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NULL,
    CONSTRAINT interview_result_pk PRIMARY KEY (interview_id)
);

CREATE TABLE professor_review (
    review_id NUMBER NOT NULL,
    review_of_interview VARCHAR2(2000) NOT NULL,
    review_rating NUMBER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NULL,
    interview_id NUMBER NOT NULL,
    CONSTRAINT professor_review_pk PRIMARY KEY (review_id)
);

-- 외래 키 제약 조건 추가
ALTER TABLE schedule
ADD CONSTRAINT fk_schedule_category
FOREIGN KEY (category_id) REFERENCES schedule_category (category_id) ON DELETE SET NULL;

ALTER TABLE interview
ADD CONSTRAINT fk_interview_student
FOREIGN KEY (student_id) REFERENCES student (student_id) ON DELETE SET NULL;

ALTER TABLE interview
ADD CONSTRAINT fk_interview_professor
FOREIGN KEY (professor_id) REFERENCES professor (professor_id) ON DELETE SET NULL;

ALTER TABLE interview_result
ADD CONSTRAINT fk_interview
FOREIGN KEY (interview_id) REFERENCES interview (interview_id) ON DELETE SET NULL;

ALTER TABLE professor_review
ADD CONSTRAINT fk_interview_review
FOREIGN KEY (interview_id) REFERENCES interview (interview_id) ON DELETE SET NULL;

-- 트리거
CREATE OR REPLACE TRIGGER PROFESSOR_ID_TRIGGER
BEFORE INSERT ON professor
FOR EACH ROW
BEGIN
   :NEW.professor_id := PROFESSOR_SEQ.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER SCHEDULE_ID_TRIGGER
BEFORE INSERT ON schedule
FOR EACH ROW
BEGIN
   :NEW.schedule_id := SCHEDULE_SEQ.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER CATEGORY_ID_TRIGGER
BEFORE INSERT ON schedule_category
FOR EACH ROW
BEGIN
   :NEW.category_id := CATEGORY_SEQ.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER INTERVIEW_ID_TRIGGER
BEFORE INSERT ON interview
FOR EACH ROW
BEGIN
   :NEW.interview_id := INTERVIEW_SEQ.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER PROFESSOR_REVIEW_ID_TRIGGER
BEFORE INSERT ON professor_review
FOR EACH ROW
BEGIN
   :NEW.review_id := PROFESSOR_REVIEW_SEQ.NEXTVAL;
END;
/