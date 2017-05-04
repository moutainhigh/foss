CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_DHK IS

  -- ==============================================================
  -- Author  : guxinhua
  -- Created : 2013-12-04 11:00:00
  -- Purpose : 代汇款报表凭证
  -- ==============================================================

  -----------------------------------------------------------------
  -- 凭证代汇款报表日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 凭证代汇款报表日汇总
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_SUMMARY(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                      P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                      P_END_DATE   DATE -- 结束时间 2012-12-22
                                      ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 凭证代汇款月汇总
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_MONTHLY_SUMMARY(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子201212-03
                                        P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                        P_END_DATE   DATE -- 结束时间 2012-12-22
                                        ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 代汇款每日凭证入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_DAILY(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                              P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                              P_END_DATE   DATE -- 结束时间 2012-12-22
                              ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

  -----------------------------------------------------------------
  -- 代汇款每月凭证入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_MONTHLY(P_PERIOD     VARCHAR2,
                                P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                P_END_DATE   DATE -- 结束时间 2012-12-22
                                ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_DHK;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_DHK IS

  -----------------------------------------------------------------
  -- 凭证代汇款报表日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN
  
    --现金收款单
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
             cc.account_date, --代汇款日期
             P_PERIOD, -- 代汇款所属期间
             cc.collection_org_code, -- 代汇款部门编码
             cc.collection_org_name, -- 代汇款部门名称
             decode(cc.product_code,
                    'PACKAGE',
                    nvl(cc.express_orig_org_code, cc.generating_org_code),
                    cc.generating_org_code), -- 被代汇款部门编码（快递点部编码）
             decode(cc.product_code,
                    'PACKAGE',
                    nvl(cc.express_orig_org_name, cc.generating_org_name),
                    cc.generating_org_name), -- 被代汇款部门名称（快递点部名称）              
             cc.cash_collection_no, -- 单据编号
             '', -- 来源单号
             'XS', -- 单据类型
             cc.amount, -- 代汇金额
             cc.payment_type, -- 付款方式
             sysdate
        from stl.t_stl_bill_cash_collection cc
       where ((cc.product_code in ('FLF', 'FSF', 'LRF', 'SRF', 'PLF') --零担产品类型
             and nvl(cc.GENERATING_ORG_CODE,0) <> nvl(cc.collection_org_code,0)) --收入部门不等于收款部门
             or cc.product_code = 'PACKAGE') --快递产品类型 
         and cc.account_date >= P_BEGIN_DATE
         and cc.account_date < P_END_DATE;
  
    --还款单
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
             bw.account_date, --代汇款日期
             P_PERIOD, -- 代汇款所属期间
             rpm.collection_org_code, -- 代汇款部门编码
             rpm.collection_org_name, -- 代汇款部门名称
             case
               when br.product_code in ('FLF', 'FSF', 'LRF', 'SRF', 'PLF') then --零担产品类型
                br.receivable_org_code
               when br.product_code = 'PACKAGE' and
                    br.bill_type in ('OR', 'RR') then --还款单对应始发应收单和小票应收单
                nvl(br.express_orig_org_code, br.receivable_org_code)
               when br.product_code = 'PACKAGE' and
                    br.bill_type in ('DR', 'CR') then --还款单对应到达应收单，代收货款应收单
                nvl(br.express_dest_org_code, rpm.collection_org_code)
             end, -- 被代汇款部门编码（快递点部编码）
             case
               when br.product_code in ('FLF', 'FSF', 'LRF', 'SRF', 'PLF') then --零担产品类型
                br.receivable_org_name
               when br.product_code = 'PACKAGE' and
                    br.bill_type in ('OR', 'RR') then --还款单对应始发应收单和小票应收单
                nvl(br.express_orig_org_name, br.receivable_org_name)
               when br.product_code = 'PACKAGE' and
                    br.bill_type in ('DR', 'CR') then --还款单对应到达应收单，代收货款应收单
                nvl(br.express_dest_org_name, rpm.collection_org_name)
             end, -- 被代汇款部门名称（快递点部名称）       
             '', -- 单据编号
             br.receivable_no, -- 来源单号
             'HK', -- 单据类型
             bw.amount, -- 代汇金额
             rpm.payment_type, -- 付款方式
             sysdate
        from stl.t_stl_bill_receivable br,
             stl.t_stl_bill_writeoff   bw,
             stl.t_stl_bill_repayment  rpm
       where bw.begin_no = rpm.repayment_no
         and bw.end_no = br.receivable_no
         and bw.writeoff_type = 'RR'
         and br.IS_RED_BACK = 'N'
         and rpm.is_red_back = br.IS_RED_BACK
         and ((br.product_code in ('FLF', 'FSF', 'LRF', 'SRF', 'PLF') --零担产品类型
             and nvl(br.receivable_org_code,0) <> nvl(rpm.collection_org_code,0)--收入部门不等于收款部门
             and br.bill_type in('OR','DR')) 
             or (br.product_code = 'PACKAGE' --快递产品类型 
             and br.bill_type in ('OR', 'RR', 'DR', 'CR'))) --始发应收,小票应收, --到达应收、代收货款应收
         and bw.account_date >= P_BEGIN_DATE
         and bw.account_date < P_END_DATE;
  
    RETURN TRUE;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_DAILY_DHK.FUNC_PROCESS_DAILY_DETAIL',
                                        '凭证代汇款报表日明细' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

  -----------------------------------------------------------------
  -- 凭证代汇款报表日汇总
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_SUMMARY(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121212
                                      P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                      P_END_DATE   DATE -- 结束时间 2012-12-22
                                      ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
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
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_DAILY_DHK.FUNC_PROCESS_DAILY_SUMMARY',
                                        '凭证代汇款报表日汇总' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

  -----------------------------------------------------------------
  -- 凭证代汇款报表月汇总
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_MONTHLY_SUMMARY(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子201212-03
                                        P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                        P_END_DATE   DATE -- 结束时间 2012-12-22
                                        ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
    V_START_PERIOD VARCHAR2(50); --开始期间
    V_END_PERIOD   VARCHAR2(50); --结束期间
  BEGIN
    --开始期间和结束期间
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
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_DAILY_DHK.FUNC_PROCESS_MONTHLY_SUMMARY',
                                        '凭证代汇款报表月汇总' || '异常');
    
      --返回失败标志
      RETURN FALSE;
  END;

  -----------------------------------------------------------------
  -- 代汇款每日凭证入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_DAILY(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                              P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                              P_END_DATE   DATE -- 结束时间 2012-12-22
                              ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
    V_RESULT BOOLEAN := TRUE;
  
  BEGIN
  
    --代汇款
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
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_DAILY_DHK.FUNC_VOUCHER_DAILY',
                                        '代汇款凭证生成日处理程序异常:' || SQLERRM);
    
      --返回失败标志
      RETURN FALSE;
    
  END FUNC_VOUCHER_DAILY;

  -----------------------------------------------------------------
  -- 代汇款每月凭证入口
  -----------------------------------------------------------------
  FUNCTION FUNC_VOUCHER_MONTHLY(P_PERIOD     VARCHAR2,
                                P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                P_END_DATE   DATE -- 结束时间 2012-12-22
                                ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败  
   IS
    V_RESULT BOOLEAN := TRUE;
  
  BEGIN
    -- 删除需要重新统计的月汇总报表
    EXECUTE IMMEDIATE 'DELETE FROM STV.T_STL_MVR_DHK T WHERE T.PERIOD = :P_PERIOD'
      USING P_PERIOD;
  
    V_RESULT := stv.pkg_stl_vch_daily_dhk.FUNC_PROCESS_MONTHLY_SUMMARY(P_PERIOD,
                                                                       P_BEGIN_DATE,
                                                                       P_END_DATE);
  
    COMMIT;
  
    RETURN V_RESULT;
  
  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'PKG_STL_VCH_DAILY_DHK.FUNC_VOUCHER_MONTHLY',
                                        '代汇款凭证生成报表程序异常:' || SQLERRM);
    
      --返回失败标志
      RETURN FALSE;
    
  END FUNC_VOUCHER_MONTHLY;

END PKG_STL_VCH_DAILY_DHK;
/
