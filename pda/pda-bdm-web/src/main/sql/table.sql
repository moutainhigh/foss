-- Create table
-- 设备修改日志记录表
create table T_SVR_UPDLOG_DATADETAIL
(
  ID       VARCHAR2(50) not null,
  DVCCODE  VARCHAR2(20) not null,
  EMPCODE  VARCHAR2(50),
  USERNAME VARCHAR2(200),
  UPDTYPE  CHAR(1) not null,
  UPDNAME  VARCHAR2(200),
  UPDDATE  TIMESTAMP(6) not null,
  REMARK   VARCHAR2(2000)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16
    next 8
    minextents 1
    maxextents unlimited
  );


/*操作类型表  */
create table T_SVR_OPERTYPE
(
  ID         VARCHAR2(36) not null,
  OPERTYPE   VARCHAR2(20) not null,
  OPERNAME   VARCHAR2(500) not null,
  REMARK     VARCHAR2(1000),
  CREATEDATE TIMESTAMP(6) not null,
  UPDDATE    TIMESTAMP(6) not null,
  OPERFLAG   CHAR(1)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Create/Recreate primary, unique and foreign key constraints 
alter table T_SVR_OPERTYPE
  add constraint PK_SVR_OPERTYPE primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
/*BAM 包装类型管理  */
-- Create table
create table T_SVR_PACKAGETYPE
(
  ID         VARCHAR2(36) not null,
  PRICE      NUMBER not null,
  P_TYPE     VARCHAR2(100),
  REMARK     VARCHAR2(1000),
  STATE      CHAR(1),
  CREATEDATE TIMESTAMP(6) not null,
  UPDDATE    TIMESTAMP(6) not null,
  VERSION    NUMBER not null,
  CONSTRAINT T_SVR_PACKAGETYPE PRIMARY KEY ("ID")
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column T_SVR_PACKAGETYPE.ID
  is 'id';
comment on column T_SVR_PACKAGETYPE.PRICE
  is '价格';
comment on column T_SVR_PACKAGETYPE.P_TYPE
  is '包装类型';
comment on column T_SVR_PACKAGETYPE.REMARK
  is '备注';
comment on column T_SVR_PACKAGETYPE.STATE
  is '状态（0：启用,1:作废)';
comment on column T_SVR_PACKAGETYPE.CREATEDATE
  is '创建时间';
comment on column T_SVR_PACKAGETYPE.UPDDATE
  is '修改时间';
comment on column T_SVR_PACKAGETYPE.VERSION
  is '版本号';

/*BAM 增值业务管理  */
-- Create table
create table T_SVR_ADDSERVICE
(
  ID         VARCHAR2(36) not null,
  A_NAME     VARCHAR2(100) not null,
  PRICE      NUMBER not null,
  A_TYPE     VARCHAR2(100),
  REMARK     VARCHAR2(1000),
  STATE      CHAR(1),
  CREATEDATE TIMESTAMP(6) not null,
  UPDDATE    TIMESTAMP(6) not null,
  VERSION    NUMBER not null,
  CONSTRAINT T_SVR_ADDSERVICE PRIMARY KEY ("ID")
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column T_SVR_ADDSERVICE.ID
  is 'id';
comment on column T_SVR_ADDSERVICE.A_NAME
  is '名称';
comment on column T_SVR_ADDSERVICE.PRICE
  is '价格';
comment on column T_SVR_ADDSERVICE.A_TYPE
  is '类型';
comment on column T_SVR_ADDSERVICE.REMARK
  is '备注';
comment on column T_SVR_ADDSERVICE.STATE
  is '状态（0：启用,1:作废)';
comment on column T_SVR_ADDSERVICE.CREATEDATE
  is '创建时间';
comment on column T_SVR_ADDSERVICE.UPDDATE
  is '修改时间';
comment on column T_SVR_ADDSERVICE.VERSION
  is '版本号';

  
 /*BAM AB货表T_SVR_ABPROJECT */
create table T_SVR_ABPROJECT
(
  ID         VARCHAR2(36) not null,
  AB_KEY     VARCHAR2(90),
  AB_VALUE   VARCHAR2(1000),
  STATE      CHAR(1),
  CREATEDATE TIMESTAMP(6) not null,
  UPDDATE    TIMESTAMP(6) not null,
  VERSION    NUMBER not null,
  CONSTRAINT T_SVR_ABPROJECT PRIMARY KEY ("ID")
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column T_SVR_ABPROJECT.ID
  is 'id';
comment on column T_SVR_ABPROJECT.AB_KEY
  is 'Key值';
comment on column T_SVR_ABPROJECT.AB_VALUE
  is 'Value值';
comment on column T_SVR_ABPROJECT.STATE
  is '状态';
comment on column T_SVR_ABPROJECT.CREATEDATE
  is '创建时间';
comment on column T_SVR_ABPROJECT.UPDDATE
  is '最后修改时间';
comment on column T_SVR_ABPROJECT.VERSION
  is '版本号';

  
/*BAM 危禁品表 T_SVR_DANPROJECT */
-- Create table
create table T_SVR_DANPROJECT
(
  ID         VARCHAR2(36) not null,
  DAN_TYPE   VARCHAR2(100),
  DAN_NAME   VARCHAR2(100),
  DAN_LEVEL  VARCHAR2(100),
  REMARK     VARCHAR2(1000),
  STATE      CHAR(1),
  CREATEDATE TIMESTAMP(6) not null,
  UPDDATE    TIMESTAMP(6) not null,
  VERSION    NUMBER not null,
  CONSTRAINT T_SVR_DANPROJECT PRIMARY KEY ("ID")
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64
    next 8
    minextents 1
    maxextents unlimited
  );
-- Add comments to the columns 
comment on column T_SVR_DANPROJECT.ID
  is 'id';
comment on column T_SVR_DANPROJECT.DAN_TYPE
  is '违禁品类型';
comment on column T_SVR_DANPROJECT.DAN_NAME
  is '违禁品名称';
comment on column T_SVR_DANPROJECT.DAN_LEVEL
  is '违禁品等级';
comment on column T_SVR_DANPROJECT.REMARK
  is '备注';
comment on column T_SVR_DANPROJECT.STATE
  is '状态（0：启用,1:作废)';
comment on column T_SVR_DANPROJECT.CREATEDATE
  is '创建时间';
comment on column T_SVR_DANPROJECT.UPDDATE
  is '修改时间';
comment on column T_SVR_DANPROJECT.VERSION
  is '版本号';



/*BAM用户表  T_BAM_USER */
create table T_BAM_USER
(
  id         VARCHAR2(50) not null,
  empcode    VARCHAR2(50),
  password   VARCHAR2(200),
  username   VARCHAR2(200),
  deptid     VARCHAR2(50),
  updtime    DATE,
  activetime DATE,
  isactive   CHAR(1),
  version    NUMBER,
  operflag   CHAR(1),
  
  CONSTRAINT PK_BAM_USER PRIMARY KEY ("ID")
);

/*角色表  T_BAM_ROLE*/
CREATE TABLE T_BAM_ROLE
(
  id            VARCHAR2(50) not null,
  rolename      VARCHAR2(200) not null,
  rolebasnumber VARCHAR2(50) not null,
  roledesc      VARCHAR2(1000) not null,
  operflag      VARCHAR2(2) not null,
  updtime       TIMESTAMP(6) not null,
	
CONSTRAINT PK_BAM_ROLE PRIMARY KEY ("ID")
);

/*用户角色表 T_BAM_USER_ROLE*/
CREATE TABLE T_BAM_USER_ROLE
(
	ID					VARCHAR2(50) NOT NULL,
	USERID				VARCHAR2(50) NOT NULL,
	DEPTID				VARCHAR2(50) NOT NULL,
	ROLEID				VARCHAR2(50) NOT NULL,
  
	CONSTRAINT PK_BAM_USER_ROLE PRIMARY KEY ("ID")
);

/*权限表*/
create table T_SVR_PRIVILEGE
(
  id                  VARCHAR2(36) not null,
  plevel              VARCHAR2(10),
  privilegecode       VARCHAR2(32) not null,
  parentprivilegecode VARCHAR2(32),
  privilegename       VARCHAR2(32) not null,
  privilegeurl        VARCHAR2(200),
  privilegeicon		  VARCHAR2(200),
  remark              VARCHAR2(200),
  lastupduserid		  VARCHAR2(50) not null,
  lastupddate         TIMESTAMP(6) not null,
  status              VARCHAR2(20) default '0' not null,
  isbam					VARCHAR2(10),
  sortby			  NUMBER,
  
  CONSTRAINT PK_SVR_PRIVILEGE PRIMARY KEY ("ID")
);

/*角色权限表*/
create table T_SVR_ROLE_PRIVILEGE
(
  id				VARCHAR2(36) not null,
  roleid			VARCHAR2(50) not null,
  privilegecode		VARCHAR2(50) not null,
  remark			VARCHAR2(200),
  lastupduserid		VARCHAR2(50) not null,
  lastupddate		TIMESTAMP(6) not null,
  status			VARCHAR2(20) default '0' not null,
  
  CONSTRAINT PK_SVR_ROLE_PRIVILEGE PRIMARY KEY ("ID")
);

/*设备  T_PDA_VERSION*/
CREATE TABLE T_SVR_DEVICE
(
	ID                  VARCHAR2(36) NOT NULL,
	DVCCODE             VARCHAR2(20) NOT NULL,
	SERNBR              VARCHAR2(25),
	REMARK              VARCHAR2(300),
	SIMCARDCODE         VARCHAR2(30),
	MODEL               VARCHAR2(90) NOT NULL, 
	COMPANYCODE         VARCHAR2(44) NOT NULL, 
	PURDATE             TIMESTAMP, 
	LASTUPDATERCODE     VARCHAR2(20) NOT NULL, 
	LASTUPDATERNM       VARCHAR2(64) NOT NULL, 
	LASTUPDDATE         TIMESTAMP NOT NULL, 
	STATUS              VARCHAR2(20) DEFAULT '0' NOT NULL, 
	HASPEN              VARCHAR2(20) DEFAULT '0' NOT NULL, 
	HASPACKAGE          VARCHAR2(20) DEFAULT '0' NOT NULL, 
	CURRENT_DEVICE_VERSION VARCHAR2(20),
	NEWEST_VERSION		VARCHAR2(20),
	CURRENT_VESION_TO_LONG NUMBER,
	NEWEST_VESION_TO_LONG NUMBER,
	
	CONSTRAINT PK_SVR_DEVICE PRIMARY KEY ("ID")
);

/*PDA设备领用记录表  T_SVR_BORROW*/
CREATE TABLE T_SVR_BORROW
(
  ID                  VARCHAR2(36) NOT NULL,
  DVCCODE             VARCHAR2(20) NOT NULL,
  BORWDATE            TIMESTAMP NOT NULL,
  BORWUSRCODE         VARCHAR2(20) NOT NULL,
  BORWUSRNM           VARCHAR2(64) NOT NULL,
  BORWCOMCODE         VARCHAR2(44) NOT NULL,
  PLANRTNDATE         TIMESTAMP NOT NULL,
  REALRTNDATE         TIMESTAMP,
  STATUS              VARCHAR2(20) DEFAULT '0' NOT NULL,
  REMARK              VARCHAR2(300),
  BORWOUTUSRCODE      VARCHAR2(20) NOT NULL,
  BORWOUTUSRNM        VARCHAR2(64) NOT NULL,
  MODIFYDATE          TIMESTAMP NOT NULL,
  PACKAGENUM          NUMBER(6) NOT NULL,
  PENNUM              NUMBER(6) NOT NULL,
  WRISTBANDNUM        NUMBER(6) NOT NULL,
  BATTERYNUM          NUMBER(6) NOT NULL,
  INTACTPACKAGE       VARCHAR2(20) DEFAULT '1',
  INTACTPEN           VARCHAR2(20) DEFAULT '1',
  INTACTWRISTBAND     VARCHAR2(20) DEFAULT '1',
  INTACTBATTERY       VARCHAR2(20) DEFAULT '1',
  
  CONSTRAINT PK_SVR_BORROW PRIMARY KEY ("ID")
)；

/*版本升级记录表  T_PDA_VERSION*/
CREATE TABLE T_SVR_VERSION
(
	ID                  VARCHAR2(36) NOT NULL,
	PGMVER              VARCHAR2(10) NOT NULL,
	PDAMODEL            VARCHAR2(32) NOT NULL,
	UPDATEDEPT          VARCHAR2(4000),
	UPDATEDEPTNAME      VARCHAR2(4000),
	ACTIVETIME          TIMESTAMP NOT NULL,
	UPDDATE             TIMESTAMP NOT NULL,
	USERCODE            VARCHAR2(20) NOT NULL,
	USERNM              VARCHAR2(64) NOT NULL,
	FORCFLAG            VARCHAR2(20) DEFAULT '1' NOT NULL,
	REMARK              VARCHAR2(300),
	
	CONSTRAINT PK_SVR_VERSION PRIMARY KEY ("ID")
);

/*PDA设备表*/
CREATE TABLE T_SVR_DEVICE
(
  ID                  VARCHAR2(36) NOT NULL,
  DVCCODE             VARCHAR2(20) NOT NULL,
  SERNBR              VARCHAR2(25),
  REMARK              VARCHAR2(300),
  SIMCARDCODE         VARCHAR2(30),
  MODEL               VARCHAR2(90) NOT NULL, 
  COMPANYCODE         VARCHAR2(44) NOT NULL, 
  PURDATE             TIMESTAMP, 
  LASTUPDATERCODE     VARCHAR2(20) NOT NULL, 
  LASTUPDATERNM       VARCHAR2(64) NOT NULL, 
  LASTUPDDATE         TIMESTAMP NOT NULL, 
  STATUS              VARCHAR2(20) DEFAULT '0' NOT NULL, 
  HASPEN              VARCHAR2(20) DEFAULT '0' NOT NULL, 
  HASPACKAGE          VARCHAR2(20) DEFAULT '0' NOT NULL, 
  
  CONSTRAINT PK_SVR_DEVICE PRIMARY KEY ("ID")
);

/*PDA领用表*/
CREATE TABLE T_SVR_BORROW
(
  ID                  VARCHAR2(36) NOT NULL,
  DVCCODE             VARCHAR2(20) NOT NULL,
  BORWDATE            TIMESTAMP NOT NULL,
  BORWUSRCODE         VARCHAR2(20) NOT NULL,
  BORWUSRNM           VARCHAR2(64) NOT NULL,
  BORWCOMCODE         VARCHAR2(44) NOT NULL,
  PLANRTNDATE         TIMESTAMP NOT NULL,
  REALRTNDATE         TIMESTAMP,
  STATUS              VARCHAR2(20) DEFAULT '0' NOT NULL,
  REMARK              VARCHAR2(300),
  BORWOUTUSRCODE      VARCHAR2(20) NOT NULL,
  BORWOUTUSRNM        VARCHAR2(64) NOT NULL,
  MODIFYDATE          TIMESTAMP NOT NULL,
  PACKAGENUM          NUMBER(6) NOT NULL,
  PENNUM              NUMBER(6) NOT NULL,
  WRISTBANDNUM        NUMBER(6) NOT NULL,
  BATTERYNUM          NUMBER(6) NOT NULL,
  INTACTPACKAGE       VARCHAR2(20) DEFAULT '1',
  INTACTPEN           VARCHAR2(20) DEFAULT '1',
  INTACTWRISTBAND     VARCHAR2(20) DEFAULT '1',
  INTACTBATTERY       VARCHAR2(20) DEFAULT '1',
  
  CONSTRAINT PK_SVR_BORROW PRIMARY KEY ("ID")
);

/*版本表*/
CREATE TABLE T_SVR_VERSION
(
  ID                  VARCHAR2(36) NOT NULL,
  PGMVER              VARCHAR2(10) NOT NULL,
  PDAMODEL            VARCHAR2(32) NOT NULL,
  UPDATEDEPT          VARCHAR2(4000),
  UPDATEDEPTNAME      VARCHAR2(4000),
  ACTIVETIME          TIMESTAMP NOT NULL,
  UPDDATE             TIMESTAMP NOT NULL,
  USERCODE            VARCHAR2(20) NOT NULL,
  USERNM              VARCHAR2(64) NOT NULL,
  FORCFLAG            VARCHAR2(20) DEFAULT '1' NOT NULL,
  REMARK              VARCHAR2(300),
  
  CONSTRAINT PK_SVR_VERSION PRIMARY KEY ("ID")
);

/*数据版本表*/
CREATE TABLE T_SVR_DATAVERSION
(
  ID          VARCHAR2(36) NOT NULL,
  DATAVER        VARCHAR(20) NOT NULL,
  UPDTIME        TIMESTAMP NOT NULL,
  
  CONSTRAINT PK_SVR_DATAVERSION PRIMARY KEY ("ID")
);

/*BAM 危禁品表 T_SVR_DANPROJECT */
create table T_SVR_DANPROJECT
(
  ID         VARCHAR2(36) not null,
  DAN_TYPE   VARCHAR2(100),
  DAN_NAME   VARCHAR2(100),
  DAN_LEVEL  VARCHAR2(100),
  REMARK     VARCHAR2(1000),
  STATE      CHAR(1),
  CREATEDATE TIMESTAMP(6) not null,
  UPDDATE    TIMESTAMP(6) not null,
  VERSION	 NUMBER not null,
  CONSTRAINT T_SVR_DANPROJECT PRIMARY KEY ("ID")
)

/*BAM AB货物表 T_SVR_ABPROJECT */
create table T_SVR_ABPROJECT
(
  ID         VARCHAR2(36) not null,
  AB_KEY     VARCHAR2(90),
  AB_VALUE   VARCHAR2(1000),
  STATE      CHAR(1),
  CREATEDATE TIMESTAMP(6) not null,
  UPDDATE    TIMESTAMP(6) not null,
  VERSION	 NUMBER not null,
  CONSTRAINT T_SVR_ABPROJECT PRIMARY KEY ("ID")
);
/**BAM 增值服务表T_SVR_ADDSERVICE*/
create table T_SVR_ADDSERVICE
(
  ID         VARCHAR2(36) not null,
  A_NAME     VARCHAR2(100) not null,
  PRICE      NUMBER not null,
  A_TYPE     VARCHAR2(100),
  REMARK     VARCHAR2(1000),
  STATE      CHAR(1),
  CREATEDATE TIMESTAMP(6) not null,
  UPDDATE    TIMESTAMP(6) not null,
  VERSION    NUMBER not null,
  CONSTRAINT T_SVR_ADDSERVICE PRIMARY KEY ("ID")
);

/**BAM 包装表*/
create table T_SVR_PACKAGETYPE
(
  ID         VARCHAR2(36) not null,
  PRICE      NUMBER not null,
  P_TYPE     VARCHAR2(100),
  REMARK     VARCHAR2(1000),
  STATE      CHAR(1),
  CREATEDATE TIMESTAMP(6) not null,
  UPDDATE    TIMESTAMP(6) not null,
  VERSION    NUMBER not null,
  CONSTRAINT T_SVR_PACKAGETYPE PRIMARY KEY ("ID")
);
