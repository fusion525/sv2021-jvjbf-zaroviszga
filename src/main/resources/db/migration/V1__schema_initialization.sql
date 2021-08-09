CREATE TABLE `players` (
    `player_id` BIGINT NOT NULL AUTO_INCREMENT,
    `player_name` VARCHAR(255),
    `player_birthdate` DATE,
    `player_position` VARCHAR(255),
    PRIMARY KEY (`player_id`)
);

CREATE TABLE `teams` (
    `team_id` BIGINT NOT NULL AUTO_INCREMENT,
    `team_name` VARCHAR(255),
    PRIMARY KEY (`team_id`)
);