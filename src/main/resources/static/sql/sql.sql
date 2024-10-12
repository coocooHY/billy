CREATE TABLE member (
    member_id VARCHAR2(50) ,
    password VARCHAR2(256) NOT NULL,
    name VARCHAR2(20) NOT NULL,
    email VARCHAR2(50) NOT NULL unique,
    point NUMBER DEFAULT 0,
    social NUMBER(1) DEFAULT 0, -- 소셜 로그인 여부 (0: 일반, 1: 소셜)
    del NUMBER(1) DEFAULT 0, -- 삭제 여부 (0: 사용, 1: 삭제)
    constraint pk_member PRIMARY KEY(member_id)
);

CREATE TABLE member_role (
    member_id VARCHAR2(50),
    role_id NUMBER,
    PRIMARY KEY (member_id, role_id),
    CONSTRAINT fk_member_role_member FOREIGN KEY (member_id) REFERENCES member(member_id),
    CONSTRAINT fk_member_role_role FOREIGN KEY (role_id) REFERENCES role(role_id)
);

CREATE TABLE role (
    role_id NUMBER PRIMARY KEY,
    role_name VARCHAR2(50) UNIQUE NOT NULL
);

-- Role 테이블에 권한 추가
INSERT INTO role (role_id, role_name) VALUES (3, 'ROLE_ADMIN');
INSERT INTO role (role_id, role_name) VALUES (2, 'ROLE_MANAGER');
INSERT INTO role (role_id, role_name) VALUES (1, 'ROLE_USER');

-- Member 테이블에 사용자 추가
INSERT INTO member (member_id, password, name, email) VALUES ('java', '$2a$10$Sd3oeSdhSqpQFw9Cko0.p.Pt7jmW.54ybTZVBPsHfnGngWGZzyKxu', '홍길동', 'magic@dream.com');

-- MemberRole 테이블에 사용자 권한 추가
INSERT INTO member_role (member_id, role_id) VALUES ('java', 1);  -- ROLE_USER
INSERT INTO member_role (member_id, role_id) VALUES ('java', 2);  -- ROLE_MANAGER
INSERT INTO member_role (member_id, role_id) VALUES ('java', 3);  -- ROLE_ADMIN
commit;