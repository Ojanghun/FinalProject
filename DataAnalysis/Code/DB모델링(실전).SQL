팀명: 문제issue
팀원: 오장훈 손종희 모성현 이주은 최유정 이영동
주제: 스마트 자격증 학습 플랫폼 개발 

ERD 설계 완료!

# DB 모델링 주소 
  URL : https://aquerytool.com/aquerymain/index/?rurl=e98aa9ac-62ec-44a4-bed4-ccf970f5cf27&
  Password : kgpo75


# 챌린지 포인트(7)

-문제풀이 자동화 트리거(trigger)


# 트리거 작성을 위한 시스템 환경 변수 설정
SET GLOBAL log_bin_trust_function_creators=1; # 1:ON(허용)
SELECT @@log_bin_trust_function_creators;

# 시스템 환경변수 파일에 기록하기.
# 리눅스 : /etc/my.cnf
# 윈도우 : C:\ProgramData\MySQL\MySQL Server 8.0\my.ini


-- 문제풀이 트리거 생성 --

DELIMITER $$
CREATE TRIGGER trg_check_ans2 
BEFORE INSERT ON pbs_log 
FOR EACH ROW 
BEGIN 
   DECLARE correct_ans INT DEFAULT 0;
   SET correct_ans = (SELECT pb_ans FROM pb_info WHERE pb_idx = NEW.pb_idx);
      IF correct_ans = NEW.user_ans THEN
       SET NEW.pbs_check = 1;
   END IF;
  
END $$
DELIMITER ;

# 사용자 문제풀이 제출 답안 체크 

insert into pbs_log (user_id, pb_idx, user_ans, pbs_check, pbs_at) 
values ('user_id 1', 2, 1, 0, now());
insert into pbs_log (user_id, pb_idx, user_ans, pbs_check, pbs_at) 
values ('user_id 1', 3, 2, 0, now());
insert into pbs_log (user_id, pb_idx, user_ans, pbs_check, pbs_at) 
values ('user_id 1', 4, 1, 0, now());

----------------------------------------
-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- li_info table create sql
-- 테이블 생성 sql - li_info
create table li_info
(
    `li_idx`   int            not null    auto_increment comment '자격증 인덱스', 
    `li_name`  varchar(50)    not null    comment '자격증 이름', 
    `li_type`  tinyint(1)     not null    comment '자격증 타입(필기: 0, 실기: 1)', 
    `li_time`  int            not null    comment '자격증 시험 시간(분단위)', 
     primary key (li_idx)
);

-- 테이블 comment 설정 sql - li_info
alter table li_info comment '자격증 정보가 담긴 테이블 (li: license). 여러 자격증 정보가 담긴 테이블';


-- plan_info table create sql
-- 테이블 생성 sql - plan_info
create table plan_info
(
    `plan_idx`   int           not null    auto_increment comment '플랜 인덱스', 
    `li_idx`     int           not null    comment '자격증 인덱스', 
    `plan_type`  tinyint(1)    not null    comment '플랜 타입(0:선택형, 1:필수형)', 
     primary key (plan_idx)
);

-- 테이블 comment 설정 sql - plan_info
alter table plan_info comment '플랜 정보가 담긴 테이블';

-- foreign key 설정 sql - plan_info(li_idx) -> li_info(li_idx)
alter table plan_info
    add constraint fk_plan_info_li_idx_li_info_li_idx foreign key (li_idx)
        references li_info (li_idx) on delete restrict on update restrict;

-- foreign key 삭제 sql - plan_info(li_idx)
-- alter table plan_info
-- drop foreign key fk_plan_info_li_idx_li_info_li_idx;


-- user_info table create sql
-- 테이블 생성 sql - user_info
create table user_info
(
    `user_id`       varchar(50)     not null    comment '회원 아이디', 
    `user_pw`       varchar(255)    not null    comment '회원 비밀번호', 
    `user_name`     varchar(50)     not null    comment '회원 이름(카카오톡 아이디).', 
    `user_profile`  varchar(255)    null        comment '회원 프로필(카카오톡 프로필)', 
    `user_phone`    varchar(50)     not null    comment '회원 전화번호', 
    `user_point`    int             not null    comment '회원 포인트', 
    `joined_at`     datetime        not null    comment '회원가입 날짜', 
     primary key (user_id)
);

-- 테이블 comment 설정 sql - user_info
alter table user_info comment '회원 정보가 담긴 테이블';


-- ex_info table create sql
-- 테이블 생성 sql - ex_info
create table ex_info
(
    `ex_idx`    int     not null    auto_increment comment '시험 인덱스', 
    `li_idx`    int     not null    comment '자격증 인덱스', 
    `app_std`   date    not null    comment '원서 접수 시작일(app: apply)', 
    `ap_ed`     date    not null    comment '원서 접수 마감일', 
    `ex_std`    date    not null    comment '시험 시작일', 
    `ex_ed`     date    not null    comment '시험 종료 일', 
    `ex_round`  int     not null    comment '시험 회차(보통 1, 2, 3)', 
    `ex_rd`     date    not null    comment '합격자 발표일(rd: result date)', 
     primary key (ex_idx)
);

-- 테이블 comment 설정 sql - ex_info
alter table ex_info comment '시험 정보가 담긴 테이블. 시험 회차 정보가 담기 테이블';

-- foreign key 설정 sql - ex_info(li_idx) -> li_info(li_idx)
alter table ex_info
    add constraint fk_ex_info_li_idx_li_info_li_idx foreign key (li_idx)
        references li_info (li_idx) on delete restrict on update restrict;

-- foreign key 삭제 sql - ex_info(li_idx)
-- alter table ex_info
-- drop foreign key fk_ex_info_li_idx_li_info_li_idx;


-- topic_info table create sql
-- 테이블 생성 sql - topic_info
create table topic_info
(
    `topic_idx`   int             not null    auto_increment comment '문제 주제 인덱스', 
    `li_idx`      int             not null    comment '자격증 인덱스', 
    `topic_num`   int             not null    comment '자격증 내 주제 번호', 
    `topic_name`  varchar(255)    not null    comment '주제 이름', 
     primary key (topic_idx)
);

-- 테이블 comment 설정 sql - topic_info
alter table topic_info comment '주제 정보가 담긴 테이블. 자격증 주제 정보가 담긴 테이블';

-- foreign key 설정 sql - topic_info(li_idx) -> li_info(li_idx)
alter table topic_info
    add constraint fk_topic_info_li_idx_li_info_li_idx foreign key (li_idx)
        references li_info (li_idx) on delete restrict on update restrict;

-- foreign key 삭제 sql - topic_info(li_idx)
-- alter table topic_info
-- drop foreign key fk_topic_info_li_idx_li_info_li_idx;


-- pay_info table create sql
-- 테이블 생성 sql - pay_info
create table pay_info
(
    `pay_idx`   int            not null    auto_increment comment '결재 인덱스', 
    `user_id`   varchar(50)    not null    comment '회원 아이디', 
    `plan_idx`  int            not null    comment '플랜 인덱스', 
    `plan_std`  timestamp      not null    comment '플랜 시작일(start date)', 
    `plan_ed`   timestamp      not null    comment '플랜 마감일(end date)', 
     primary key (pay_idx)
);

-- 테이블 comment 설정 sql - pay_info
alter table pay_info comment '결재 정보가 담긴 테이블';

-- foreign key 설정 sql - pay_info(plan_idx) -> plan_info(plan_idx)
alter table pay_info
    add constraint fk_pay_info_plan_idx_plan_info_plan_idx foreign key (plan_idx)
        references plan_info (plan_idx) on delete restrict on update restrict;

-- foreign key 삭제 sql - pay_info(plan_idx)
-- alter table pay_info
-- drop foreign key fk_pay_info_plan_idx_plan_info_plan_idx;

-- foreign key 설정 sql - pay_info(user_id) -> user_info(user_id)
alter table pay_info
    add constraint fk_pay_info_user_id_user_info_user_id foreign key (user_id)
        references user_info (user_id) on delete restrict on update restrict;

-- foreign key 삭제 sql - pay_info(user_id)
-- alter table pay_info
-- drop foreign key fk_pay_info_user_id_user_info_user_id;


-- pb_info table create sql
-- 테이블 생성 sql - pb_info
create table pb_info
(
    `pb_idx`     int              not null    auto_increment comment '문제 인덱스', 
    `li_idx`     int              not null    comment '자격증 인덱스', 
    `ex_idx`     int              not null    comment '시험 인덱스', 
    `pb_num`     int              not null    comment '한 회차 내의 문제 번호', 
    `pb_ques`    varchar(1000)    not null    comment '문제 질문', 
    `pb_detail`  varchar(1000)    null        comment '문제 세부 설명 사진 경로(.gif).', 
    `pb_choi1`   varchar(255)     not null    comment '문제 1번 선지', 
    `pb_choi2`   varchar(255)     not null    comment '문제 2번 선지', 
    `pb_choi3`   varchar(255)     not null    comment '문제 3번 선지', 
    `pb_choi4`   varchar(255)     not null    comment '문제 4번 선지', 
    `pb_ans`     int              not null    comment '문제 정답 번호', 
    `pb_solu`    varchar(1000)    not null    comment '문제 해설', 
    `pb_cr`      varchar(50)      not null    comment '문제 정답률', 
    `topic_idx`  int              not null    comment '문제 주제 인덱스', 
     primary key (pb_idx)
);

-- 테이블 comment 설정 sql - pb_info
alter table pb_info comment '문제 정보가 담긴 테이블. 시험 문제 정보가 담긴 테이블';

-- foreign key 설정 sql - pb_info(li_idx) -> li_info(li_idx)
alter table pb_info
    add constraint fk_pb_info_li_idx_li_info_li_idx foreign key (li_idx)
        references li_info (li_idx) on delete restrict on update restrict;

-- foreign key 삭제 sql - pb_info(li_idx)
-- alter table pb_info
-- drop foreign key fk_pb_info_li_idx_li_info_li_idx;

-- foreign key 설정 sql - pb_info(topic_idx) -> topic_info(topic_idx)
alter table pb_info
    add constraint fk_pb_info_topic_idx_topic_info_topic_idx foreign key (topic_idx)
        references topic_info (topic_idx) on delete restrict on update restrict;

-- foreign key 삭제 sql - pb_info(topic_idx)
-- alter table pb_info
-- drop foreign key fk_pb_info_topic_idx_topic_info_topic_idx;

-- foreign key 설정 sql - pb_info(ex_idx) -> ex_info(ex_idx)
alter table pb_info
    add constraint fk_pb_info_ex_idx_ex_info_ex_idx foreign key (ex_idx)
        references ex_info (ex_idx) on delete restrict on update restrict;

-- foreign key 삭제 sql - pb_info(ex_idx)
-- alter table pb_info
-- drop foreign key fk_pb_info_ex_idx_ex_info_ex_idx;


-- tmp table create sql
-- 테이블 생성 sql - tmp
create table tmp
(
    `tmp`  int    not null    comment '임시', 
     primary key (tmp)
);

-- 테이블 comment 설정 sql - tmp
alter table tmp comment '임시 테이블';


-- tmp1 table create sql
-- 테이블 생성 sql - tmp1
create table tmp1
(
    `tmp1`  int    not null    comment '임시', 
     primary key (tmp1)
);


-- refund_info table create sql
-- 테이블 생성 sql - refund_info
create table refund_info
(
    `rf_idx`     int             not null    auto_increment comment '환불 인덱스', 
    `pay_idx`    int             not null    comment '결재 인덱스', 
    `user_id`    varchar(50)     not null    comment '회원 아이디', 
    `rf_vpath`   varchar(255)    not null    comment '합격 인증 영상 경로(video path)', 
    `rf_name`    varchar(50)     not null    comment '환불 예금주', 
    `rf_bank`    varchar(50)     not null    comment '환불 은행', 
    `rf_accnum`  varchar(50)     not null    comment '환불 계좌번호(account number)', 
    `rf_at`      timestamp       not null    comment '환불 날짜', 
     primary key (rf_idx)
);

-- 테이블 comment 설정 sql - refund_info
alter table refund_info comment '환급 신청 정보가 담긴 테이블';

-- foreign key 설정 sql - refund_info(pay_idx) -> pay_info(pay_idx)
alter table refund_info
    add constraint fk_refund_info_pay_idx_pay_info_pay_idx foreign key (pay_idx)
        references pay_info (pay_idx) on delete restrict on update restrict;

-- foreign key 삭제 sql - refund_info(pay_idx)
-- alter table refund_info
-- drop foreign key fk_refund_info_pay_idx_pay_info_pay_idx;

-- foreign key 설정 sql - refund_info(user_id) -> user_info(user_id)
alter table refund_info
    add constraint fk_refund_info_user_id_user_info_user_id foreign key (user_id)
        references user_info (user_id) on delete restrict on update restrict;

-- foreign key 삭제 sql - refund_info(user_id)
-- alter table refund_info
-- drop foreign key fk_refund_info_user_id_user_info_user_id;


-- atd_log table create sql
-- 테이블 생성 sql - atd_log
create table atd_log
(
    `atd_idx`  int            not null    auto_increment comment '출석 인덱스', 
    `user_id`  varchar(50)    not null    comment '회원 아이디', 
    `atd_at`   timestamp      not null    comment '출석 날짜', 
     primary key (atd_idx)
);

-- 테이블 comment 설정 sql - atd_log
alter table atd_log comment '출석 정보가 담긴 테이블(atd = attendance). 출석 정보가 담긴 테이블';

-- foreign key 설정 sql - atd_log(user_id) -> user_info(user_id)
alter table atd_log
    add constraint fk_atd_log_user_id_user_info_user_id foreign key (user_id)
        references user_info (user_id) on delete restrict on update restrict;

-- foreign key 삭제 sql - atd_log(user_id)
-- alter table atd_log
-- drop foreign key fk_atd_log_user_id_user_info_user_id;


-- pbs_log table create sql
-- 테이블 생성 sql - pbs_log
create table pbs_log
(
    `pbs_idx`    int            not null    auto_increment comment '문제 풀이 로그 인덱스', 
    `user_id`    varchar(50)    not null    comment '회원 아이디', 
    `pb_idx`     int            not null    comment '문제 인덱스', 
    `user_ans`   int            null        comment '사용자 응답', 
    `pbs_check`  tinyint(1)     not null    comment '문제 정답 여부(0: 틀림, 1: 맞음)', 
    `pbs_at`     timestamp      not null    comment '문제 푼 날짜', 
     primary key (pbs_idx)
);

-- 테이블 comment 설정 sql - pbs_log
alter table pbs_log comment '사용자가 문제를 풀었을 때 문제 푼 정보가 저장되는 테이블(pbs = problem solve). 문제를 풀었던 정보가 담기 테이블';

-- foreign key 설정 sql - pbs_log(pb_idx) -> pb_info(pb_idx)
alter table pbs_log
    add constraint fk_pbs_log_pb_idx_pb_info_pb_idx foreign key (pb_idx)
        references pb_info (pb_idx) on delete restrict on update restrict;

-- foreign key 삭제 sql - pbs_log(pb_idx)
-- alter table pbs_log
-- drop foreign key fk_pbs_log_pb_idx_pb_info_pb_idx;

-- foreign key 설정 sql - pbs_log(user_id) -> user_info(user_id)
alter table pbs_log
    add constraint fk_pbs_log_user_id_user_info_user_id foreign key (user_id)
        references user_info (user_id) on delete restrict on update restrict;

-- foreign key 삭제 sql - pbs_log(user_id)
-- alter table pbs_log
-- drop foreign key fk_pbs_log_user_id_user_info_user_id;


