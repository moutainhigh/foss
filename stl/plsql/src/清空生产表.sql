
-- Created on 2013/2/26 by ADMINISTRATOR 
declare
  v_sql varchar2(200);
BEGIN

  --����Ѿ����ڵ�������� 
  --ÿ�������ܣ����ܵ��ͻ���
  v_sql := 'truncate table stv.t_stl_balance';
  execute IMMEDIATE v_sql;
  commit;

  --û�������ܣ����ܵ����ţ�
  v_sql := 'truncate table stv.t_stl_balance_Detail';
  execute IMMEDIATE v_sql;
  commit;

  --��ʼ������ڳ�����
  v_sql := 'truncate table stv.t_stl_balance_Detail_INIT';
  execute IMMEDIATE v_sql;
  commit;

  --��ʼ������ڳ����ܹ��� 
  v_sql := 'truncate table stv.t_stl_balance_Detail_INIT_Tmp';
  execute IMMEDIATE v_sql;

  --ֻ�������ã�����ÿ����ϸ����
  v_sql := 'truncate table stv.T_STL_DAILY_BALANCE';
  execute IMMEDIATE v_sql;
  commit;

  --ֻ�������ã�����ÿ����ϸ�������
  v_sql := 'truncate table stv.T_STL_DAILY_BALANCE_tmp';
  execute IMMEDIATE v_sql;
  commit;

  --���κ� 
  v_sql := 'truncate table stv.t_stl_balance_batch';
  execute IMMEDIATE v_sql;
  commit;

  v_sql := 'truncate table stv.T_STL_ERROR_LOG';
  execute IMMEDIATE v_sql;
  commit;

  --���ý���Ľű�
    stv.pkg_stl_balance.proc_balance_call('M','BALANCE');

end;
