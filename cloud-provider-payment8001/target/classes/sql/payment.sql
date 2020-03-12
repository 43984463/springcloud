create table if not exists `payment` (
  `id` bigint not null  auto_increment comment 'ID',
  `serial` VARCHAR(100),
  primary key (`id`)
)engine=innodb default charset=utf8mb4 comment='payment';