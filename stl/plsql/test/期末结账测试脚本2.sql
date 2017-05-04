--������ĩ����
DECLARE

  --��ʼ����
  V_BEGIN_DATE DATE;
  --��������
  V_END_DATE DATE;
  --ִ�н��
  V_RESULT BOOLEAN;
BEGIN
  --��ʼ������
  V_BEGIN_DATE := TO_DATE('2012-12-1', 'yyyy-mm-dd');
  V_END_DATE   := SYSDATE;
  
  --ɾ��֮ǰ���ɵ�����
  DELETE FROM stv.t_stl_balance;
  COMMIT;
  DELETE FROM stv.t_stl_balance_detail;
  COMMIT;
  DELETE FROM stv.t_stl_balance_batch;
  COMMIT;
  DELETE FROM stv.t_stl_balance_log;
  COMMIT;  
  

  --ִ��Ԥ����ĩ����
  V_RESULT := PKG_STL_BALANCE.FUNC_COUNT_BALANCE_DEPOSIT(V_BEGIN_DATE,
                                                         V_END_DATE);
  --ִ��Ӧ����ĩ����
  V_RESULT := PKG_STL_BALANCE.FUNC_COUNT_BALANCE_RECEIVABLE(V_BEGIN_DATE,
                                                            V_END_DATE);
  --ִ��Ӧ����ĩ����
  V_RESULT := PKG_STL_BALANCE.FUNC_COUNT_BALANCE_PAYABLE(V_BEGIN_DATE,
                                                         V_END_DATE);

END;
