drop table if exists z_module;

drop table if exists z_privilege;

drop table if exists z_role;

drop table if exists z_user;

/*==============================================================*/
/* Table: z_module                                              */
/*==============================================================*/
create table z_module
(
   module_id_           varchar(36) not null comment '模块ID',
   name_                varchar(64) not null comment '模块名',
   actions_             varchar(256) not null comment '操作项，多个以逗号隔开',
   sort_                char(10) comment '排序',
   is_enabled_          int not null comment '是否可用',
   create_time_         datetime not null comment '创建时间',
   primary key (module_id_)
);

alter table z_module comment '模块';

/*==============================================================*/
/* Table: z_privilege                                           */
/*==============================================================*/
create table z_privilege
(
   privilege_id_        varchar(36) not null comment '权限ID',
   contract_id_         varchar(36) comment '用户ID',
   module_id_           varchar(36) not null comment '模块ID',
   role_id_             varchar(36) comment '角色ID',
   actions_             int not null comment '手机号',
   type_                datetime not null comment '权限类型，用户（user），角色（role）',
   create_time_         datetime not null comment '创建时间',
   primary key (privilege_id_)
);

alter table z_privilege comment '权限';

/*==============================================================*/
/* Table: z_role                                                */
/*==============================================================*/
create table z_role
(
   role_id_             varchar(36) not null comment '角色ID',
   name_                varchar(64) not null comment '角色名称',
   sort_                int comment '排序',
   is_enabled_          int not null comment '是否可用',
   create_time_         datetime not null comment '创建时间',
   primary key (role_id_)
);

alter table z_role comment '角色';

/*==============================================================*/
/* Table: z_user                                                */
/*==============================================================*/
create table z_user
(
   contract_id_         varchar(36) not null comment '用户ID',
   username_            varchar(64) not null comment '用户名',
   password_            varchar(128) not null comment '密码',
   sex_                 int comment '性别',
   phone_               int comment '手机号',
   last_login_time_     datetime comment '最后登录时间',
   create_time_         datetime not null comment '创建时间',
   primary key (contract_id_)
);

alter table z_user comment '用户';

alter table z_privilege add constraint fk_privilege_moduleid foreign key (module_id_)
      references z_module (module_id_) on delete cascade on update restrict;

alter table z_privilege add constraint fk_privilege_roleid foreign key (role_id_)
      references z_role (role_id_) on delete cascade on update restrict;

alter table z_privilege add constraint fk_privilege_userid foreign key (contract_id_)
      references z_user (user_id_) on delete cascade on update restrict;
