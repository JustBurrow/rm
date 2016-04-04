-- MySQL Script generated by MySQL Workbench
-- Tue Apr  5 01:22:45 2016
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema urs
-- -----------------------------------------------------
-- Unreliable Resource-manage System
DROP SCHEMA IF EXISTS `urs` ;

-- -----------------------------------------------------
-- Schema urs
--
-- Unreliable Resource-manage System
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `urs` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;
USE `urs` ;

-- -----------------------------------------------------
-- Table `urs`.`op_operators`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `urs`.`op_operators` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `password` TINYTEXT NOT NULL,
  `non_expired` TINYINT(1) NOT NULL DEFAULT 0,
  `non_locked` TINYINT(1) NOT NULL DEFAULT 0,
  `credentials_non_expired` TINYINT(1) NOT NULL DEFAULT 0,
  `enabled` TINYINT(1) NOT NULL DEFAULT 0,
  `create_utc` BIGINT NOT NULL,
  `update_utc` BIGINT NOT NULL,
  `modify_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UQ_OPERATORS_EMAIL` (`email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `urs`.`op_client_platform`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `urs`.`op_client_platform` (
  `id` INT(9) UNSIGNED NOT NULL AUTO_INCREMENT,
  `owner` INT UNSIGNED NOT NULL COMMENT '프로덕트 관리자 ID.',
  `code` VARCHAR(45) NOT NULL,
  `label` VARCHAR(45) NOT NULL,
  `description` TEXT NOT NULL,
  `create_utc` BIGINT NOT NULL,
  `update_utc` BIGINT NOT NULL,
  `modify_ts` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `UQ_CLIENT_PLATFORM_CODE` (`owner` ASC, `code` ASC),
  CONSTRAINT `FK_CLIENT_PLATFORM_PK_OPERATOR`
    FOREIGN KEY (`owner`)
    REFERENCES `urs`.`op_operators` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
