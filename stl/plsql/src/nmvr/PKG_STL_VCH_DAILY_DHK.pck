CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_DHK IS

  -- ==============================================================
  -- Author  : guxinhua
  -- Created : 2013-12-04 11:00:00
  -- Purpose : ������ƾ֤
  -- ==============================================================

  -----------------------------------------------------------------
  -- ƾ֤����������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ƾ֤�������ջ���
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_SUMMARY(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                      P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                      P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                      ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- ƾ֤������»���
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_MONTHLY_SUMMARY(P_PERIOD     VARCHAR2, -- ����yyyymmdd������201212-03
                                        P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                        P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                        ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- �����ÿ��ƾ֤���
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_DAILY(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                              P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                              P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                              ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

  -----------------------------------------------------------------
  -- �����ÿ��ƾ֤���
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_MONTHLY(P_PERIOD     VARCHAR2,
                                P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                ) RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

END PKG_STL_VCH_DAILY_DHK;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_DHK IS

  -----------------------------------------------------------------
  -- ƾ֤����������ϸ
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                                     P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                     P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                     ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
  BEGIN
  
    --�ֽ��տ
    insert into stv.t_stl_dvd_dhk
      (ID,
       REMIT_DATE,
       PERIOD,
       REMIT_ORG_CODE,
       REMIT_ORG_NAME,
       BYREMIT_ORG_CODE,
       BYREMIT_ORG_NAME,
       BILL_NO,
       SOURCE_BILL_NO,
       BILL_TYPE,
       AMOUNT,
       PAYMENT_TYPE,
       VOUCHER_CREATE_TIME)
      select SYS_GUID(),
             cc.account_date, --���������
             P_PERIOD, -- ����������ڼ�
             cc.collection_org_code, -- �����ű���
             cc.collection_org_name, -- ����������
             decode(cc.product_code,
                    'PACKAGE',
                    nvl(cc.express_orig_org_code, cc.generating_org_code),
                    cc.generating_org_code), -- �������ű��루��ݵ㲿���룩
             decode(cc.product_code,
                    'PACKAGE',
                    nvl(cc.express_orig_org_name, cc.generating_org_name),
                    cc.generating_org_name), -- �����������ƣ���ݵ㲿���ƣ�              
             cc.cash_collection_no, -- ���ݱ��
             '', -- ��Դ����
             'XS', -- ��������
             cc.amount, -- ������
             cc.payment_type, -- ���ʽ
             sysdate
        from stl.t_stl_bill_cash_collection cc
       where ((cc.product_code in ('FLF', 'FSF', 'LRF', 'SRF', 'PLF') --�㵣��Ʒ����
             and nvl(cc.GENERATING_ORG_CODE,0) <> nvl(cc.collection_org_code,0)) --���벿�Ų������տ��
             or cc.product_code = 'PACKAGE') --��ݲ�Ʒ���� 
         and cc.account_date >= P_BEGIN_DATE
         and cc.account_date < P_END_DATE;
  
    --���
    insert into stv.t_stl_dvd_dhk
      (ID,
       REMIT_DATE,
       PERIOD,
       REMIT_ORG_CODE,
       REMIT_ORG_NAME,
       BYREMIT_ORG_CODE,
       BYREMIT_ORG_NAME,
       BILL_NO,
       SOURCE_BILL_NO,
       BILL_TYPE,
       AMOUNT,
       PAYMENT_TYPE,
       VOUCHER_CREATE_TIME)
      select SYS_GUID(),
             bw.account_date, --���������
             P_PERIOD, -- ����������ڼ�
             rpm.collection_org_code, -- �����ű���
             rpm.collection_org_name, -- ����������
             case
               when br.product_code in ('FLF', 'FSF', 'LRF', 'SRF', 'PLF') then --�㵣��Ʒ����
                br.receivable_org_code
               when br.product_code = 'PACKAGE' and
                    br.bill_type in ('OR', 'RR') then --�����Ӧʼ��Ӧ�յ���СƱӦ�յ�
                nvl(br.express_orig_org_code, br.receivable_org_code)
               when br.product_code = 'PACKAGE' and
                    br.bill_type in ('DR', 'CR') then --�����Ӧ����Ӧ�յ������ջ���Ӧ�յ�
                nvl(br.express_dest_org_code, rpm.collection_org_code)
             end, -- �������ű��루��ݵ㲿���룩
             case
               when br.product_code in ('FLF', 'FSF', 'LRF', 'SRF', 'PLF') then --�㵣��Ʒ����
                br.receivable_org_name
               when br.product_code = 'PACKAGE' and
                    br.bill_type in ('OR', 'RR') then --�����Ӧʼ��Ӧ�յ���СƱӦ�յ�
                nvl(br.express_orig_org_name, br.receivable_org_name)
               when br.product_code = 'PACKAGE' and
                    br.bill_type in ('DR', 'CR') then --�����Ӧ����Ӧ�յ������ջ���Ӧ�յ�
                nvl(br.express_dest_org_name, rpm.collection_org_name)
             end, -- �����������ƣ���ݵ㲿���ƣ�       
             '', -- ���ݱ��
             br.receivable_no, -- ��Դ����
             'HK', -- ��������
             bw.amount, -- ������
             rpm.payment_type, -- ���ʽ
             sysdate
        from stl.t_stl_bill_receivable br,
             stl.t_stl_bill_writeoff   bw,
             stl.t_stl_bill_repayment  rpm
       where bw.begin_no = rpm.repayment_no
         and bw.end_no = br.receivable_no
         and bw.writeoff_type = 'RR'
         and br.IS_RED_BACK = 'N'
         and rpm.is_red_back = br.IS_RED_BACK
         and ((br.product_code in ('FLF', 'FSF', 'LRF', 'SRF', 'PLF') --�㵣��Ʒ����
             and nvl(br.receivable_org_code,0) <> nvl(rpm.collection_org_code,0)--���벿�Ų������տ��
             and br.bill_type in('OR','DR')) 
             or (br.product_code = 'PACKAGE' --��ݲ�Ʒ���� 
             and br.bill_type in ('OR', 'RR', 'DR', 'CR'))) --ʼ��Ӧ��,СƱӦ��, --����Ӧ�ա����ջ���Ӧ��
         and bw.account_date >= P_BEGIN_DATE
         and bw.account_date < P_END_DATE;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_DAILY_DHK.FUNC_PROCESS_DAILY_DETAIL',
                                        'ƾ֤����������ϸ' || '�쳣');
    
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

  -----------------------------------------------------------------
  -- ƾ֤�������ջ���
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_SUMMARY(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121212
                                      P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                      P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                      ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
  BEGIN
  
    insert into stv.t_stl_dvs_dhk
      (ID,
       PERIOD,
       REMIT_ORG_CODE,
       REMIT_ORG_NAME,
       BYREMIT_ORG_CODE,
       BYREMIT_ORG_NAME,
       AMOUNT,
       COLLECTION_TYPE,
       VOUCHER_CREATE_TIME)
      select SYS_GUID(),
             P_PERIOD,
             dvd.remit_org_code,
             max(dvd.remit_org_name),
             dvd.byremit_org_code,
             max(dvd.byremit_org_name),
             sum(dvd.amount),
             decode(dvd.payment_type, 'CH', 'CH', 'NCH'),
             sysdate
        from stv.t_stl_dvd_dhk dvd
       where dvd.period = P_PERIOD
         and dvd.remit_date >= P_BEGIN_DATE
         and dvd.remit_date < P_END_DATE
       group by dvd.remit_org_code,
                dvd.byremit_org_code,
                decode(dvd.payment_type, 'CH', 'CH', 'NCH')
      having sum(dvd.amount) <> 0;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_DAILY_DHK.FUNC_PROCESS_DAILY_SUMMARY',
                                        'ƾ֤�������ջ���' || '�쳣');
    
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

  -----------------------------------------------------------------
  -- ƾ֤�������»���
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_MONTHLY_SUMMARY(P_PERIOD     VARCHAR2, -- ����yyyymmdd������201212-03
                                        P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                        P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                        ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
    V_START_PERIOD VARCHAR2(50); --��ʼ�ڼ�
    V_END_PERIOD   VARCHAR2(50); --�����ڼ�
  BEGIN
    --��ʼ�ڼ�ͽ����ڼ�
    V_START_PERIOD := TO_CHAR(P_BEGIN_DATE, 'YYYYMMDD');
    V_END_PERIOD   := TO_CHAR(P_END_DATE, 'YYYYMMDD');
  
    insert into stv.t_stl_mvr_dhk
      (ID,
       PERIOD,
       REMIT_ORG_CODE,
       REMIT_ORG_NAME,
       BYREMIT_ORG_CODE,
       BYREMIT_ORG_NAME,
       AMOUNT,
       COLLECTION_TYPE,
       voucher_begin_time,
       voucher_end_time)
      select SYS_GUID(),
             P_PERIOD,
             dvs.remit_org_code,
             max(dvs.remit_org_name),
             dvs.byremit_org_code,
             max(dvs.byremit_org_name),
             sum(dvs.amount),
             dvs.collection_type,
             P_BEGIN_DATE,
             P_END_DATE
        from stv.t_stl_dvs_dhk dvs
       where dvs.period >= V_START_PERIOD
         and dvs.period < V_END_PERIOD
       group by dvs.remit_org_code,
                dvs.byremit_org_code,
                dvs.collection_type
      having sum(dvs.amount) <> 0;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_DAILY_DHK.FUNC_PROCESS_MONTHLY_SUMMARY',
                                        'ƾ֤�������»���' || '�쳣');
    
      --����ʧ�ܱ�־
      RETURN FALSE;
  END;

  -----------------------------------------------------------------
  -- �����ÿ��ƾ֤���
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_DAILY(P_PERIOD     VARCHAR2, -- ����yyyymmdd������20121221
                              P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                              P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                              ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��
   IS
    V_RESULT BOOLEAN := TRUE;
  
  BEGIN
  
    --�����
    IF V_RESULT THEN
      EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_DVD_DHK T WHERE T.PERIOD = :P_PERIOD'
        USING P_PERIOD;
    
      V_RESULT := PKG_STL_VCH_DAILY_DHK.FUNC_PROCESS_DAILY_DETAIL(P_PERIOD,
                                                                  P_BEGIN_DATE,
                                                                  P_END_DATE);
    
      COMMIT;
    END IF;
    IF V_RESULT THEN
    
      EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_DVS_DHK T WHERE T.PERIOD = :P_PERIOD'
        USING P_PERIOD;
      V_RESULT := stv.pkg_stl_vch_daily_dhk.FUNC_PROCESS_DAILY_SUMMARY(P_PERIOD,
                                                                       P_BEGIN_DATE,
                                                                       P_END_DATE);
    
      COMMIT;
    END IF;
  
    RETURN V_RESULT;
  
  EXCEPTION
    WHEN OTHERS THEN
      DBMS_OUTPUT.PUT_LINE('EXCEPTION');
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_DAILY_DHK.FUNC_VOUCHER_DAILY',
                                        '�����ƾ֤�����մ�������쳣:' || SQLERRM);
    
      --����ʧ�ܱ�־
      RETURN FALSE;
    
  END FUNC_VOUCHER_DAILY;

  -----------------------------------------------------------------
  -- �����ÿ��ƾ֤���
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_MONTHLY(P_PERIOD     VARCHAR2,
                                P_BEGIN_DATE DATE, -- ��ʼʱ�� 2012-12-21
                                P_END_DATE   DATE -- ����ʱ�� 2012-12-22
                                ) RETURN BOOLEAN -- ���ؽ�� true:�ɹ���false:ʧ��  
   IS
    V_RESULT BOOLEAN := TRUE;
  
  BEGIN
    -- ɾ����Ҫ����ͳ�Ƶ��»��ܱ���
    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_DHK T WHERE T.PERIOD = :P_PERIOD'
      USING P_PERIOD;
  
    V_RESULT := stv.pkg_stl_vch_daily_dhk.FUNC_PROCESS_MONTHLY_SUMMARY(P_PERIOD,
                                                                       P_BEGIN_DATE,
                                                                       P_END_DATE);
  
    COMMIT;
  
    RETURN V_RESULT;
  
  EXCEPTION
    WHEN OTHERS THEN
      --���������־��
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_DAILY_DHK.FUNC_VOUCHER_MONTHLY',
                                        '�����ƾ֤���ɱ�������쳣:' || SQLERRM);
    
      --����ʧ�ܱ�־
      RETURN FALSE;
    
  END FUNC_VOUCHER_MONTHLY;

END PKG_STL_VCH_DAILY_DHK;
/
