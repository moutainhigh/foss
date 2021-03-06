/*PDA设备信息表*/
CREATE UNIQUE INDEX UN_IDX_SVR_DEVICE1 ON T_SVR_DEVICE(DVCCODE);
CREATE UNIQUE INDEX UN_SVR_DEVICE2 ON T_SVR_DEVICE(SERNBR);

/*PDA设备领用记录表*/
CREATE INDEX IDX_SVR_BORROW1 ON T_SVR_BORROW(DVCCODE);

/*版本升级记录表*/
CREATE UNIQUE INDEX UN_SVR_VERSION ON T_SVR_VERSION(PGMVER);

/*角色权现唯一键*/
alter table T_SVR_PRIVILEGE
add constraint T_SVR_PRIVILEGE_UQID_PCODE unique (PRIVILEGECODE)

