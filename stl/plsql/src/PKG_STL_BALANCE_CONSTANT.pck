CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_CONSTANT IS

  -- Author  : 000123-foss-huangxiaobo
  -- Created : 2012/11/20 9:37:48
  -- Purpose : 期末结账常量

  --=============================期末结账类型====================================
  --结账类型
  BALANCE_TYPE_RECEIVABLE   VARCHAR(20) := 'R'; -- 应收期末结账
  BALANCE_TYPE_PAYABLE      VARCHAR(20) := 'P'; -- 应付期末结账
  BALANCE_TYPE_DEPOSIT_BILL VARCHAR(20) := 'DB'; -- 预收期末结账--预收客户
  BALANCE_TYPE_DEPOSIT_TF   VARCHAR(20) := 'DT'; -- 预收期末结账--中转科目

  ---==========================结账批次号==================================
  BALANCE_BATCH_INITED VARCHAR2(20) := 'INITED'; --初始化没有做任何操作
  BALANCE_BATCH_BEGIN  VARCHAR2(20) := 'BEGIN'; --正在结账
  BALANCE_BATCH_END    VARCHAR2(20) := 'END'; --结账结束

  --最小记账日期（2014-01-01）
  BEGIN_BALANCE_DATE date := to_date('20140101', 'yyyymmdd');

  --最大线程数量
  BALANCE_MAX_THREADS int := 25;

  --操作来源类别
  --JOB
  BALANCE_SOURCE_TYPE_JOB varchar2(20) := 'JOB';
  --手工
  BALANCE_SOURCE_TYPE_MANUAL varchar2(20) := 'M';

  --操作类别
  BALANCE_OPERATE_TYPE_BALANCE   VARCHAR2(20) := 'BALANCE';
  BALANCE_OPERATE_TYPE_UNBALANCE VARCHAR2(20) := 'UNBALANCE';

  --全局组织
  --用户控制全局进度状态
  BALANCE_GLOBAL_ORG varchar2(50) := '0____BALANCE_GLOBAL_ORG___0';

  --开单
  POD__POD_TYPE__BILL VARCHAR2(20) := 'BILL'; -- 开单状态
  
  --异常单据类别
  RECEIVABLE_SOURCE_TYPE_E varchar2(20) := 'E';

end PKG_STL_BALANCE_CONSTANT;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_CONSTANT IS
END PKG_STL_BALANCE_CONSTANT;
/
