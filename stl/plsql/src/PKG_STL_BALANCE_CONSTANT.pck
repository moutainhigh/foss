CREATE OR REPLACE PACKAGE PKG_STL_BALANCE_CONSTANT IS

  -- Author  : 000123-foss-huangxiaobo
  -- Created : 2012/11/20 9:37:48
  -- Purpose : ��ĩ���˳���

  --=============================��ĩ��������====================================
  --��������
  BALANCE_TYPE_RECEIVABLE   VARCHAR(20) := 'R'; -- Ӧ����ĩ����
  BALANCE_TYPE_PAYABLE      VARCHAR(20) := 'P'; -- Ӧ����ĩ����
  BALANCE_TYPE_DEPOSIT_BILL VARCHAR(20) := 'DB'; -- Ԥ����ĩ����--Ԥ�տͻ�
  BALANCE_TYPE_DEPOSIT_TF   VARCHAR(20) := 'DT'; -- Ԥ����ĩ����--��ת��Ŀ

  ---==========================�������κ�==================================
  BALANCE_BATCH_INITED VARCHAR2(20) := 'INITED'; --��ʼ��û�����κβ���
  BALANCE_BATCH_BEGIN  VARCHAR2(20) := 'BEGIN'; --���ڽ���
  BALANCE_BATCH_END    VARCHAR2(20) := 'END'; --���˽���

  --��С�������ڣ�2014-01-01��
  BEGIN_BALANCE_DATE date := to_date('20140101', 'yyyymmdd');

  --����߳�����
  BALANCE_MAX_THREADS int := 25;

  --������Դ���
  --JOB
  BALANCE_SOURCE_TYPE_JOB varchar2(20) := 'JOB';
  --�ֹ�
  BALANCE_SOURCE_TYPE_MANUAL varchar2(20) := 'M';

  --�������
  BALANCE_OPERATE_TYPE_BALANCE   VARCHAR2(20) := 'BALANCE';
  BALANCE_OPERATE_TYPE_UNBALANCE VARCHAR2(20) := 'UNBALANCE';

  --ȫ����֯
  --�û�����ȫ�ֽ���״̬
  BALANCE_GLOBAL_ORG varchar2(50) := '0____BALANCE_GLOBAL_ORG___0';

  --����
  POD__POD_TYPE__BILL VARCHAR2(20) := 'BILL'; -- ����״̬
  
  --�쳣�������
  RECEIVABLE_SOURCE_TYPE_E varchar2(20) := 'E';

end PKG_STL_BALANCE_CONSTANT;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE_CONSTANT IS
END PKG_STL_BALANCE_CONSTANT;
/
