--测试期末结账
DECLARE

  --起始日期
  V_BEGIN_DATE DATE;
  --结束日期
  V_END_DATE DATE;
  --执行结果
  V_RESULT BOOLEAN;
BEGIN
  --初始化参数
  V_BEGIN_DATE := TO_DATE('2012-12-1', 'yyyy-mm-dd');
  V_END_DATE   := SYSDATE;
  
  --删除之前生成的数据
  DELETE FROM stv.t_stl_balance;
  COMMIT;
  DELETE FROM stv.t_stl_balance_detail;
  COMMIT;
  DELETE FROM stv.t_stl_balance_batch;
  COMMIT;
  DELETE FROM stv.t_stl_balance_log;
  COMMIT;  
  

  --执行预收期末结账
  V_RESULT := PKG_STL_BALANCE.FUNC_COUNT_BALANCE_DEPOSIT(V_BEGIN_DATE,
                                                         V_END_DATE);
  --执行应收期末结账
  V_RESULT := PKG_STL_BALANCE.FUNC_COUNT_BALANCE_RECEIVABLE(V_BEGIN_DATE,
                                                            V_END_DATE);
  --执行应付期末结账
  V_RESULT := PKG_STL_BALANCE.FUNC_COUNT_BALANCE_PAYABLE(V_BEGIN_DATE,
                                                         V_END_DATE);

END;
