create table if not exists generator
(
    id 					bigint auto_increment comment 'id' primary key,
    name        varchar(128)                       null comment '名称',
    description text                               null comment '描述',
    basePackage varchar(128)                       null comment '基础包',
    version     varchar(128)                       null comment '版本',
    author      varchar(128)                       null comment '作者',
    tags        varchar(1024)                      null comment '标签列表（json 数组）',
    picture     varchar(256)                       null comment '图片',
    fileConfig  text                               null comment '文件配置（json字符串）',
    modelConfig text                               null comment '模型配置（json字符串）',
    distPath    text                               null comment '代码生成器产物路径',
    status      int      default 0                 not null comment '状态',
    userId      bigint                             not null comment '创建用户 id',
    createTime  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete    tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
    ) COMMENT '代码生成器' COLLATE = utf8mb4_unicode_ci;

INSERT INTO generator (id, name, description, basePackage, version, author, tags, picture, fileConfig, modelConfig, distPath, status, userId) VALUES (1, 'ACM 模板项目', 'ACM 模板项目生成器', 'com.ziio', '1.0', 'ziio', '["Java"]', 'https://pic.yupi.icu/1/_r0_c1851-bf115939332e.jpg', '{}', '{}', null, 0, 1);
INSERT INTO generator (id, name, description, basePackage, version, author, tags, picture, fileConfig, modelConfig, distPath, status, userId) VALUES (2, 'Spring Boot 初始化模板', 'Spring Boot 初始化模板项目生成器', 'com.ziio', '1.0', 'ziio', '["Java"]', 'https://pic.yupi.icu/1/_r0_c0726-7e30f8db802a.jpg', '{}', '{}', null, 0, 1);
