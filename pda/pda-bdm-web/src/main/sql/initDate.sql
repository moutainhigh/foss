/*权限表*/
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'0','0000','-1','系统菜单','','','系统菜单','092016',systimestamp,'1','1',0);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','1000','0000','PDA管理','','treeNodePackage','PDA管理','092016',systimestamp,'1','1',1);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','1001','1000','设备管理','findDevice.action','treeNodeLeaf','设备管理','092016',systimestamp,'1','1',1);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','1002','1000','领用管理','initFindBorrow.action','treeNodeLeaf','领用管理','092016',systimestamp,'1','1',2);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','2000','0000','版本管理','','treeNodePackage','版本管理','092016',systimestamp,'1','1',2);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','2001','2000','PDA程序升级','initFindVersion.action','treeNodeLeaf','PDA程序升级','092016',systimestamp,'1','1',1);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','3000','0000','权限管理','','treeNodePackage','权限管理','092016',systimestamp,'1','1',3);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','3001','3000','组织机构查询','dept.action','treeNodeLeaf','组织机构查询','092016',systimestamp,'1','1',1);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','3002','3000','用户账号查询','user.action','treeNodeLeaf','用户账号查询','092016',systimestamp,'1','1',2);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','3003','3000','角色权限管理','role.action','treeNodeLeaf','角色权限管理','092016',systimestamp,'1','1',3);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','4000','0000','综合管理','','treeNodePackage','综合管理','092016',systimestamp,'1','1',4);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','4001','4000','AB货管理','abProject.action','treeNodeLeaf','AB货管理','092016',systimestamp,'1','1',1);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','4002','4000','危禁品管理','danGerProject.action','treeNodeLeaf','危禁品管理','092016',systimestamp,'1','1',2);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','4003','4000','增值业务管理','addService.action','treeNodeLeaf','增值业务管理','092016',systimestamp,'1','1',3);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','4004','4000','包装类型管理','packageType.action','treeNodeLeaf','包装类型管理','092016',systimestamp,'1','1',4);

insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','4005','4000','数据同步监控','sysdataMonitor.action','treeNodeLeaf','数据同步监控','092042',systimestamp,'1','1',5);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','4006','4000','数据同步查询','asyncDataShow.action','treeNodeLeaf','数据同步查询','092042',systimestamp,'1','1',6);

/*理货员对应权限 1级*/

insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','179','0000','装车','','treeNodePackage','装车','092016',SYSTIMESTAMP,'1','0',11);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','146','0000','卸车','','treeNodePackage','卸车','092016',SYSTIMESTAMP,'1','0',12);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','138','0000','清仓','','treeNodePackage','清仓','092016',SYSTIMESTAMP,'1','0',13);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','219','0000','包装','','treeNodePackage','包装','092016',SYSTIMESTAMP,'1','0',14);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','171','0000','货物登记','','treeNodePackage','货物登记','092016',SYSTIMESTAMP,'1','0',15);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','177','0000','出发到达','','treeNodePackage','出发到达','092016',SYSTIMESTAMP,'1','0',16);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','201','0000','客户自提','','treeNodePackage','客户自提','092016',SYSTIMESTAMP,'1','0',17);


insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','99','0000','数据更新','','treeNodePackage','数据更新','092016',SYSTIMESTAMP,'1','0',18);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','100','0000','程序更新','','treeNodePackage','程序更新','092016',SYSTIMESTAMP,'1','0',19);

/*司机对应权限 1级 */ 
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','178','0000','接货任务','','treeNodePackage','接货任务','092016',SYSTIMESTAMP,'1','0',7);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','135','0000','送货任务','','treeNodePackage','送货任务','092016',SYSTIMESTAMP,'1','0',8);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','215','0000','收派件统计','','treeNodePackage','收派件统计','092016',SYSTIMESTAMP,'1','0',9);

/* 闭掉该功能
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','0','0000','运单查询','','treeNodePackage','运单查询','092016',SYSTIMESTAMP,'0','0',13);

*/
/*该权限毙掉
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','98','0000','我的通知','','treeNodePackage','我的通知','092016',SYSTIMESTAMP,'0','0',18);
*/
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'1','212','0000','综合查询(FOSS)','','treeNodePackage','综合查询(FOSS)','092016',SYSTIMESTAMP,'1','0',10);



/*接货任务下的子功能*/
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','LIMIT_ACCT_01','178','待处理订单','','treeNodeLeaf','接货功能权限','092016',SYSTIMESTAMP,'1','0',1);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','LIMIT_ACCT_02','178','无订单接货','','treeNodeLeaf','接货功能权限','092016',SYSTIMESTAMP,'1','0',2);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','LIMIT_ACCT_03','178','完成接货','','treeNodeLeaf','接货功能权限','092016',SYSTIMESTAMP,'1','0',3);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','LIMIT_ACCT_04','178','已接货订单','','treeNodeLeaf','接货功能权限','092016',SYSTIMESTAMP,'1','0',4);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','LIMIT_ACCT_05','178','已退回订单','','treeNodeLeaf','接货功能权限','092016',SYSTIMESTAMP,'1','0',5);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','LIMIT_ACCT_06','178','重打标签','','treeNodeLeaf','接货功能权限','092016',SYSTIMESTAMP,'1','0',6);


insert into t_bam_role(ID,ROLENAME,ROLEBASNUMBER,ROLEDESC,OPERFLAG,UPDTIME) values(sys_guid(),'后台管理员','PDAADMIN','PDA后台管理员','1',SYSTIMESTAMP);
insert into t_bam_role(ID,ROLENAME,ROLEBASNUMBER,ROLEDESC,OPERFLAG,UPDTIME) values(sys_guid(),'PDA设备管理员','PDADEVICEADMIN','PDA设备管理员','1',SYSTIMESTAMP);
insert into t_bam_role(ID,ROLENAME,ROLEBASNUMBER,ROLEDESC,OPERFLAG,UPDTIME) values(sys_guid(),'版本管理员','PDAVERSIONADMIN','PDA版本升级管理员','1',SYSTIMESTAMP);
insert into t_bam_role(ID,ROLENAME,ROLEBASNUMBER,ROLEDESC,OPERFLAG,UPDTIME) values(sys_guid(),'综合管理','CARGOTYPE','综合管理','1',SYSTIMESTAMP);

insert into t_bam_user(id,empcode,password,username,deptid,updtime,activetime,isactive,version,operflag) values(sys_guid(),'admin','ICy5YqxZB1uWSwcVLSNLcA==','超级管理员','FOSSORG-10981',systimestamp,systimestamp,'0',1,'1');

insert into t_bam_user_role(id,userid,deptid,roleid) values(sys_guid(),(select id from t_bam_user u where u.empcode='admin'),(select u.empcode from t_bam_user u where u.empcode='admin'),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'));

insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='PDA管理'),'PDA管理',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='设备管理'),'设备管理',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='领用管理'),'领用管理',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='版本管理'),'版本管理',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='PDA程序升级'),'PDA程序升级',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='权限管理'),'权限管理',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='组织机构查询'),'组织机构查询',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='用户账号查询'),'用户账号查询',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='角色权限管理'),'角色权限管理',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='综合管理'),'综合管理',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='AB货管理'),'AB货管理',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='危禁品管理'),'危禁品管理',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='增值业务管理'),'增值业务管理',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='包装类型管理'),'包装类型管理',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');

insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='数据同步监控'),'数据同步监控',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='数据同步查询'),'数据同步查询',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');


insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='数据更新'),'数据更新',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='装车'),'装车',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='卸车'),'卸车',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='清仓'),'清仓',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='包装'),'包装',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='货物登记'),'货物登记',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='出发到达'),'出发到达',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='客户自提'),'客户自提',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');

insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='程序更新'),'程序更新',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='接货任务'),'接货任务',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='送货任务'),'送货任务',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='收派件统计'),'收派件统计',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');

insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='综合查询(FOSS)'),'综合查询(FOSS)',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');

/*闭掉该权限
//insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='我的通知'),'我的通知',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
*/
/*闭掉该功能
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='运单查询'),'运单查询',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
*/

/*插入接货功能二级菜单*/
/*
--插入接货二级权限
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','LIMIT_ACCT_01',(select privilegecode from t_svr_privilege p where p.privilegename='接货任务'),'待处理订单','','treeNodePackage','接货功能权限','092016',SYSTIMESTAMP,'1','0',1);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','LIMIT_ACCT_02',(select privilegecode from t_svr_privilege p where p.privilegename='接货任务'),'无订单接货','','treeNodePackage','接货功能权限','092016',SYSTIMESTAMP,'1','0',2);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','LIMIT_ACCT_03',(select privilegecode from t_svr_privilege p where p.privilegename='接货任务'),'完成接货','','treeNodePackage','接货功能权限','092016',SYSTIMESTAMP,'1','0',3);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','LIMIT_ACCT_04',(select privilegecode from t_svr_privilege p where p.privilegename='接货任务'),'已接货订单','','treeNodePackage','接货功能权限','092016',SYSTIMESTAMP,'1','0',4);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','LIMIT_ACCT_05',(select privilegecode from t_svr_privilege p where p.privilegename='接货任务'),'已退回订单','','treeNodePackage','接货功能权限','092016',SYSTIMESTAMP,'1','0',5);
insert into t_svr_privilege(id,plevel,privilegecode,parentprivilegecode,privilegename,privilegeurl,privilegeicon,remark,lastupduserid,lastupddate,status,isbam,sortby) values(sys_guid(),'2','LIMIT_ACCT_06',(select privilegecode from t_svr_privilege p where p.privilegename='接货任务'),'重打标签','','treeNodePackage','接货功能权限','092016',SYSTIMESTAMP,'1','0',6);
--插入角色权限表中属于接货二级权限
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='待处理订单'),'待处理订单',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='无订单接货'),'无订单接货',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='完成接货'),'完成接货',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='已接货订单'),'已接货订单',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='已退回订单'),'已退回订单',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select ROLEBASNUMBER from t_bam_role r where r.rolename='后台管理员'),(select privilegecode from t_svr_privilege p where p.privilegename='重打标签'),'重打标签',(select u.empcode from t_bam_user u where u.empcode='admin'),systimestamp,'1');
*/

/*---------------------------------插入bdm的--------------------------------*/
/*
/*司机*/
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='司机'),(select privilegecode from t_svr_privilege p where p.privilegename='接货任务'),'接货任务',(select deptid from t_svr_user u where u.empcode='125837'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='司机'),(select privilegecode from t_svr_privilege p where p.privilegename='送货任务'),'送货任务',(select deptid from t_svr_user u where u.empcode='125837'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='司机'),(select privilegecode from t_svr_privilege p where p.privilegename='收派件统计'),'收派件统计',(select deptid from t_svr_user u where u.empcode='125837'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='司机'),(select privilegecode from t_svr_privilege p where p.privilegename='我的通知'),'我的通知',(select deptid from t_svr_user u where u.empcode='125837'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='司机'),(select privilegecode from t_svr_privilege p where p.privilegename='综合查询(FOSS)'),'综合查询(FOSS)',(select deptid from t_svr_user u where u.empcode='125837'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='司机'),(select privilegecode from t_svr_privilege p where p.privilegename='数据更新'),'数据更新',(select deptid from t_svr_user u where u.empcode='125837'),systimestamp,'1');
/*理货员*/
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='理货员'),(select privilegecode from t_svr_privilege p where p.privilegename='数据更新'),'数据更新',(select deptid from t_svr_user u where u.empcode='055382'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='理货员'),(select privilegecode from t_svr_privilege p where p.privilegename='装车'),'装车',(select deptid from t_svr_user u where u.empcode='055382'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='理货员'),(select privilegecode from t_svr_privilege p where p.privilegename='卸车'),'卸车',(select deptid from t_svr_user u where u.empcode='055382'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='理货员'),(select privilegecode from t_svr_privilege p where p.privilegename='清仓'),'清仓',(select deptid from t_svr_user u where u.empcode='055382'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='理货员'),(select privilegecode from t_svr_privilege p where p.privilegename='包装'),'包装',(select deptid from t_svr_user u where u.empcode='055382'),systimestamp,'1');

insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='理货员'),(select privilegecode from t_svr_privilege p where p.privilegename='货物登记'),'货物登记',(select deptid from t_svr_user u where u.empcode='055382'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='理货员'),(select privilegecode from t_svr_privilege p where p.privilegename='出发到达'),'出发到达',(select deptid from t_svr_user u where u.empcode='055382'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='理货员'),(select privilegecode from t_svr_privilege p where p.privilegename='客户自提'),'客户自提',(select deptid from t_svr_user u where u.empcode='055382'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='理货员'),(select privilegecode from t_svr_privilege p where p.privilegename='运单查询'),'运单查询',(select deptid from t_svr_user u where u.empcode='055382'),systimestamp,'1');
insert into t_svr_role_privilege(id,roleid,privilegecode,remark,lastupduserid,lastupddate,status) values(sys_guid(),(select rolebasnumber from t_svr_role r where r.rolename='理货员'),(select privilegecode from t_svr_privilege p where p.privilegename='程序更新'),'程序更新',(select deptid from t_svr_user u where u.empcode='055382'),systimestamp,'1');
*/

/*操作类型表*/

insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'ACCT_02','反馈订单已接收','反馈订单已接收',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'ACCT_03','反馈订单已读','反馈订单已读',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'ACCT_04','接收约车任务','接收约车任务',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'ACCT_05','订单退回','订单退回',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'ACCT_06','现场开单','现场开单',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'ACCT_07','标签打印','标签打印',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'ACCT_08','重打标签','重打标签',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'DERY_02','按件正常签收','按件正常签收',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'DERY_03','按票正常签收','按票正常签收',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'DERY_04','异常签收','异常签收',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'DERY_05','派送异常','派送异常',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'DERY_07','客户自提','客户自提',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'LOAD_04','装车扫描','装车扫描',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'LOAD_05','完成装车任务','完成装车任务',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'LOAD_07','未装车备注','未装车备注',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'LOAD_08','撤销装车扫描','撤销装车扫描',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'LOAD_09','录入封签','录入封签',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'LOAD_10','上报封签异常','上报封签异常',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'LOAD_11','增加/删除理货员','增加/删除理货员',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'LOAD_12','撤销装车任务','撤销装车任务',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'UNLD_02','检查标签','检查标签',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'UNLD_05','卸车扫描','卸车扫描',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'UNLD_06','撤销卸车扫描','撤销卸车扫描',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'UNLD_07','补录重量体积','补录重量体积',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'UNLD_08','完成卸车任务','完成卸车任务',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'UNLD_10','增加/删除理货员','增加/删除理货员',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'WRAP_01','入库登记','入库登记',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'WRAP_03','打包扫描','打包扫描',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'WRAP_04','出库登记','出库登记',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'CLEAR_04','清仓扫描','清仓扫描',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'CLEAR_06','完成清仓任务','完成清仓任务',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'CLEAR_07','撤销清仓任务','撤销清仓任务',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'REG_01','贵重物品入库','贵重物品入库',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'REG_02','贵重物品出库','贵重物品出库',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'REG_03','异常货物入库','异常货物入库',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'REG_04','异常货物出库','异常货物出库',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'REG_05','单票入库','单票入库',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'ARR_01','记录车辆到达信息','记录车辆到达信息',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'AID_02','打木架确认','打木架确认',SYSTIMESTAMP,SYSTIMESTAMP,'1');
insert into T_SVR_OPERTYPE(ID,OPERTYPE,OPERNAME,REMARK,CREATEDATE,UPDDATE,OPERFLAG) values(sys_guid(),'SYS_05','扫描数据上传','扫描数据上传',SYSTIMESTAMP,SYSTIMESTAMP,'1');





insert into t_svr_dataversion values(sys_guid(),to_char(sysdate,'yyyymmdd'),sysdate);
create sequence t_svr_privilege_pcode
minvalue 1
maxvalue 999999999
start with 50000
increment by 1
nocache
cycle;