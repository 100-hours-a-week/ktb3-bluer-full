CREATE TABLE IF NOT EXISTS `post_like`
(
    `like_id`    VARCHAR(50) NOT NULL,
    `post_id`    VARCHAR(50) NOT NULL,
    `user_id`    VARCHAR(50) NOT NULL,
    `is_active`  BOOLEAN     NULL,
    `created_at` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP   NULL,
    CONSTRAINT `pk_post_like` PRIMARY KEY (`like_id`)
);

CREATE TABLE IF NOT EXISTS `comment`
(
    `comment_id` VARCHAR(50) NOT NULL,
    `author_id`  VARCHAR(50) NOT NULL,
    `post_id`    VARCHAR(50) NOT NULL,
    `content`    TEXT        NOT NULL,
    `created_at` TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP   NULL,
    CONSTRAINT `pk_comment` PRIMARY KEY (`comment_id`)
);

CREATE TABLE IF NOT EXISTS `post`
(
    `post_id`    VARCHAR(50)  NOT NULL,
    `author_id`  VARCHAR(50)  NOT NULL,
    `title`      VARCHAR(255) NOT NULL,
    `content`    TEXT         NOT NULL,
    `like_count` INT          NOT NULL DEFAULT 0,
    `comment`    INT          NOT NULL DEFAULT 0,
    `view_count` INT          NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP    NULL,
    CONSTRAINT `pk_post` PRIMARY KEY (`post_id`)
);

CREATE TABLE IF NOT EXISTS `user`
(
    `user_id`           VARCHAR(50)  NOT NULL,
    `email`             VARCHAR(100) NOT NULL,
    `password`          VARCHAR(255) NOT NULL,
    `nickname`          VARCHAR(50)  NOT NULL,
    `profile_image_url` VARCHAR(255) NULL,
    `created_at`        TIMESTAMP    NULL,
    `updated_at`        TIMESTAMP    NULL,
    `is_deleted`        BOOLEAN      NULL,
    CONSTRAINT `pk_user` PRIMARY KEY (`user_id`)
);
