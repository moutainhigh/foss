
-- Created on 2013/2/26 by ADMINISTRATOR 
declare
  v_sql varchar2(200);
BEGIN

  --清空已经存在的余额数据 
  --每月余额汇总（汇总到客户）
  v_sql := 'truncate table stv.t_stl_balance';
  execute IMMEDIATE v_sql;
  commit;

  --没有余额汇总（汇总到单号）
  v_sql := 'truncate table stv.t_stl_balance_Detail';
  execute IMMEDIATE v_sql;
  commit;

  --初始化余额期初汇总
  v_sql := 'truncate table stv.t_stl_balance_Detail_INIT';
  execute IMMEDIATE v_sql;
  commit;

  --初始化余额期初汇总过程 
  v_sql := 'truncate table stv.t_stl_balance_Detail_INIT_Tmp';
  execute IMMEDIATE v_sql;

  --只做计算用，保存每日明细汇总
  v_sql := 'truncate table stv.T_STL_DAILY_BALANCE';
  execute IMMEDIATE v_sql;
  commit;

  --只做计算用，保存每日明细计算过程
  v_sql := 'truncate table stv.T_STL_DAILY_BALANCE_tmp';
  execute IMMEDIATE v_sql;
  commit;

  --批次号 
  v_sql := 'truncate table stv.t_stl_balance_batch';
  execute IMMEDIATE v_sql;
  commit;

  v_sql := 'truncate table stv.T_STL_ERROR_LOG';
  execute IMMEDIATE v_sql;
  commit;

  --调用结算的脚本
    stv.pkg_stl_balance.proc_balance_call('M','BALANCE');

end;
