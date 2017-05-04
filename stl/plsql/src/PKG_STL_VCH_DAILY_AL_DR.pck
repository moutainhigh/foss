CREATE OR REPLACE PACKAGE PKG_STL_VCH_DAILY_AL_DR IS

  -- ==============================================================
  -- Author  : ZBW
  -- Created : 2013-11-06
  -- Purpose : 每日凭证预收空运/落地配代理
  -- ==============================================================

  -----------------------------------------------------------------
  -- 预收空运/落地配代理处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

END PKG_STL_VCH_DAILY_AL_DR;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_VCH_DAILY_AL_DR IS

  -----------------------------------------------------------------
  -- 预收空运/落地配代理处理日明细
  -----------------------------------------------------------------
  FUNCTION FUNC_PROCESS_DAILY_DETAIL(P_PERIOD     VARCHAR2, -- 账期yyyymmdd，例子20121221
                                     P_BEGIN_DATE DATE, -- 开始时间 2012-12-21
                                     P_END_DATE   DATE -- 结束时间 2012-12-22
                                     ) RETURN BOOLEAN -- 返回结果 true:成功，false:失败
   IS
  BEGIN



    RETURN TRUE;

  EXCEPTION
    WHEN OTHERS THEN
      --插入错误日志表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'FUNC_PROCESS_DAILY_DETAIL',
                                        '预收空运/落地配代理处理日明细' || '异常');

      --返回失败标志
      RETURN FALSE;
  END;

END PKG_STL_VCH_DAILY_AL_DR;
/
