CREATE TABLE `PostLike`
(
    `좋아요 ID`      VARCHAR(50) NOT NULL,
    `좋아요한 포스트 ID` VARCHAR(50) NOT NULL,
    `좋아요 한 유저ID`  VARCHAR(50) NOT NULL,
    `좋아요 여부`      BOOLEAN     NULL,
    `좋아요 생성일시`    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `좋아요 업데이트 일시` TIMESTAMP   NULL
);

CREATE TABLE `Comment`
(
    `댓글 ID`          VARCHAR(50) NOT NULL,
    `댓글 작성자 ID`      VARCHAR(50) NOT NULL,
    `댓글이 작성된 포스트 ID` VARCHAR(50) NOT NULL,
    `댓글 내용`          TEXT        NOT NULL,
    `댓글 생성일시`        TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `댓글 수정일시`        TIMESTAMP   NULL
);

CREATE TABLE `Post`
(
    `포스트 ID`        VARCHAR(50)  NOT NULL,
    `포스트 작성자 유저 ID` VARCHAR(50)  NOT NULL,
    `포스트 제목`        VARCHAR(255) NOT NULL,
    `포스트 본문`        TEXT         NOT NULL,
    `포스트 좋아요 수`     INT          NOT NULL DEFAULT 0,
    `포스트 댓글 수`      INT          NOT NULL DEFAULT 0,
    `포스트 조회수`       INT          NOT NULL DEFAULT 0,
    `포스트 생성일시`      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `포스트 수정 일시`     TIMESTAMP    NULL
);

CREATE TABLE `User`
(
    `유저ID`          VARCHAR(50)  NOT NULL,
    `이메일`           VARCHAR(100) NOT NULL,
    `비밀번호`          VARCHAR(255) NOT NULL,
    `닉네임`           VARCHAR(50)  NOT NULL,
    `프로필 이미지 URL`   VARCHAR(255) NULL,
    `회원 생성일시`       TIMESTAMP    NULL,
    `회원 정보 업데이트 일시` TIMESTAMP    NULL,
    `삭제 여부`         BOOLEAN      NULL
);

ALTER TABLE `PostLike`
    ADD CONSTRAINT `PK_POSTLIKE` PRIMARY KEY (
                                              `좋아요 ID`
        );

ALTER TABLE `Comment`
    ADD CONSTRAINT `PK_COMMENT` PRIMARY KEY (
                                             `댓글 ID`
        );

ALTER TABLE `Post`
    ADD CONSTRAINT `PK_POST` PRIMARY KEY (
                                          `포스트 ID`
        );

ALTER TABLE `User`
    ADD CONSTRAINT `PK_USER` PRIMARY KEY (
                                          `유저ID`
        );
