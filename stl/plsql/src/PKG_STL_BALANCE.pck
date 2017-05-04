CREATE OR REPLACE PACKAGE PKG_STL_BALANCE IS

  -- Author  : 000123-fosss-huangxiaobo
  -- Created : 2012/11/19 11:33:31
  -- Purpose : PKG_BALANCE

  --结账调用入口
  procedure proc_balance_call(p_source_type  in varchar2 default Pkg_Stl_Balance_Constant.BALANCE_SOURCE_TYPE_JOB, --来源类别-默认JOB    
                              p_operate_type in varchar2 default Pkg_Stl_Balance_Constant.BALANCE_OPERATE_TYPE_BALANCE, --操作类别  -- 默认结账               
                              p_end_date     in date default trunc(sysdate) - 1 --截至日期
                              );

  --判断结账程序是否在运行
  function func_is_balance_run return boolean;

  --判断结账程序是否在运行
  function func_is_sub_job_run return boolean;

  ----------------------------------------------
  --结账状态
  --供程序调用
  -------------------------------------------------
  function func_balance_state return int;

  --期末结账入口
  PROCEDURE PROC_BALANCE_main(p_end_date in timestamp);

  --------------------------------------------------
  --期末结账JOB
  --实现多线程结账
  --------------------------------------------------
  PROCEDURE proc_balance_job(p_business_date      in timestamp,
                             p_max_threads        int,
                             p_current_thread_num int);

  --期末反结账
  PROCEDURE PROC_UNBALANCE(P_BEGIN_DATE IN timestamp -- 截至日期
                           );

  ------------------------------------------------------------------
  --结算应收余额
  ------------------------------------------------------------------
  PROCEDURE PROC_COUNT_BALANCE_RECEIVABLE(P_ORG_CODE   IN VARCHAR2, --业务组织
                                          P_BEGIN_DATE IN DATE, -- 起始日期
                                          P_END_DATE   IN DATE -- 结束日期
                                          );

  ------------------------------------------------------------------
  --结算应付余额
  ------------------------------------------------------------------
  PROCEDURE PROC_COUNT_BALANCE_PAYABLE(P_ORG_CODE   IN VARCHAR2, --业务组织
                                       P_BEGIN_DATE IN DATE, -- 起始日期
                                       P_END_DATE   IN DATE -- 结束日期
                                       );

  ------------------------------------------------------------------
  --结算预收余额
  ------------------------------------------------------------------
  PROCEDURE PROC_COUNT_BALANCE_DEPOSIT(P_ORG_CODE   IN VARCHAR2, --业务组织
                                       P_BEGIN_DATE IN DATE, -- 起始日期
                                       P_END_DATE   IN DATE -- 结束日期
                                       );

END PKG_STL_BALANCE;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_BALANCE IS
  --author：0000123-foss-huangxiaobo
  --created：2012-11-19
  --期末余额结账具体实现

  --结账调用入口
  --使用说明:
  --JOB 调用 ： p_source_type ：= ‘JOB’ 即可
  --
  --FOSS程序调用：
  --     结账：
  --       p_source_type ：= ‘’，p_operate_type := ‘BALANCE’ ，p_end_date := 你期望的结账日期
  --     反结账：
  --     p_source_type ：= ‘’， p_operate_type := ‘UNBALANCE’ ，p_end_date := 你期望的结账日期
  procedure proc_balance_call(p_source_type  in varchar2 default Pkg_Stl_Balance_Constant.BALANCE_SOURCE_TYPE_JOB, --来源类别-默认JOB
                              p_operate_type in varchar2 default Pkg_Stl_Balance_Constant.BALANCE_OPERATE_TYPE_BALANCE, --操作类别              
                              p_end_date     in date default trunc(sysdate) - 1 --截至日期
                              ) is
    --结账实例是否在运行
    v_is_balance_run boolean;
    --截至日期
    V_end_date timestamp;
    --job_action
    v_job_action varchar2(200);
    --来源类别
    v_source_type varchar2(30);
    --操作类别
    v_operate_type varchar2(30);    
  
  begin
    --实例是否允许
    v_is_balance_run := func_is_balance_run;
    --截至日期
    V_end_date := p_end_date;
    --来源类别
    v_source_type := p_source_type;
    --操作类别
    v_operate_type := p_operate_type;
  
    --如果来源为JOB
    if v_is_balance_run and
       v_source_type = Pkg_Stl_Balance_Constant.BALANCE_SOURCE_TYPE_JOB then
      --跳过
      return;
    elsif v_is_balance_run then
      --抛出异常
      RAISE_APPLICATION_ERROR('-20002', '系统正在进行结账,不能重复执行!');
      return;
    end if;
  
    --校验时间范围
    if p_end_date >= trunc(sysdate) /*or
       p_end_date < Pkg_Stl_Balance_Constant.BEGIN_BALANCE_DATE*/ then
      --返回异常
      RAISE_APPLICATION_ERROR('-20002', '日期范围不正确！');
    end if;
    
  dbms_output.put_line (v_source_type || ',' || v_operate_type );
  
    --如果来源JOB为自动
    if v_source_type = 'JOB' then
      --调用程序入口
      PROC_BALANCE_main(p_end_date);
    elsif v_operate_type =
          Pkg_Stl_Balance_Constant.BALANCE_OPERATE_TYPE_BALANCE then
      --定义job_action
      v_job_action := 'begin pkg_stl_balance.PROC_BALANCE_main(to_date(''' ||
                      to_char(v_end_date, 'yyyymmdd') ||
                      ''',''yyyymmdd'')); end;';
    
      --创建结账JOB
      DBMS_SCHEDULER.create_job(job_name   => 'JOB_STL_BALANCE_M',
                                job_type   => 'PLSQL_BLOCK',
                                enabled    => true,
                                job_action => v_job_action,
                                COMMENTS   => '期末余额手工结账');
    
    elsif v_operate_type =
          Pkg_Stl_Balance_Constant.BALANCE_OPERATE_TYPE_UNBALANCE then
      --定义job_action
      v_job_action := 'begin pkg_stl_balance.PROC_UNBALANCE(to_date(''' ||
                      to_char(v_end_date, 'yyyymmdd') ||
                      ''',''yyyymmdd'')); end;';
      --创建反结账JOB
      DBMS_SCHEDULER.create_job(job_name   => 'JOB_STL_UNBALANCE_M',
                                job_type   => 'PLSQL_BLOCK',
                                enabled    => true,
                                job_action => v_job_action,
                                COMMENTS   => '期末余额手工反结账');
    end if;
  
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(p_end_date, 'yyyymm'),
                                        p_end_date,
                                        p_end_date,
                                        'pkg_stl_balance.proc_balance_call',
                                        '期末结账与反结账');
      --返回异常
      RAISE_APPLICATION_ERROR('-20002', SQLCODE || ',' || SQLERRM);
  end;

  --判断结账程序是否在运行
  --是： 运行中 不能进行结账操作
  --否：未运行  能进行结账操作
  function func_is_balance_run return boolean is
    --存在行集
    v_row_counts int := 0;
    --子任务是否正在运行
    v_sub_job_is_running boolean := true;
  begin
  
    --子任务是否正在运行
    v_sub_job_is_running := func_is_sub_job_run;
  
    --是否运行中，
    if v_sub_job_is_running then
      return true;
    else
      --判断自动结账的定时任务是否处于运行装 
      select count(1)
        into v_row_counts
        from user_scheduler_running_jobs
       where JOB_NAME in ('JOB_STV_BALANCE_AUTO',
                          'JOB_STL_BALANCE_M',
                          'JOB_STL_UNBALANCE_M');
      --是否运行
      return v_row_counts > 0;
    end if;
  end;

  --判断结账子程序是否在运行
  function func_is_sub_job_run return boolean is
    --默认等于1，减少风险
    v_run_thread int := 1;
  begin
  
    select count(1)
      into v_run_thread
      from user_scheduler_running_jobs
     where job_name like 'JOB_STV_BALANCE_TH%';
    --大于0 ，认为正在运行
    return v_run_thread > 0;
  end;

  --结账状态
  --0表示未运行
  --1表示运行中
  function func_balance_state return int is
    --结账实例是否在运行
    v_is_balance_run boolean;
  begin
    --实例是否允许
    v_is_balance_run := func_is_balance_run;
  
    if v_is_balance_run = true then
      return 1;
    else
      return 0;
    end if;
  end;

  --期末结账入口
  --注意:日期为字符串
  PROCEDURE PROC_BALANCE_main(p_end_date in timestamp) IS
  
    --程序结束日期
    V_proc_end_date date;
  
    --起始日期
    V_BEGIN_DATE DATE;
    --结束日期
    v_end_date Date;
  
    --上次结账日期
    V_LAST_BUSINES_DATE DATE;
  
    --是否正常结束（默认false）
    V_LAST_DAY_IS_SUCCESS BOOLEAN := FALSE;
  
    --是否第一次结账(默认false)
    V_IS_FIRST_BALANCE BOOLEAN := FALSE;
  
    --结账状态记录数
    V_BALANCE_STATUS_ROWS INT;
  
    --最大进程数
    v_job_max_threads int := Pkg_Stl_Balance_Constant.BALANCE_MAX_THREADS;
  
    --循环笔数
    v_index int := 1;
    --定时器名称
    v_job_name varchar2(200);
    --执行结果
    v_result boolean;
    --结账期间
    v_period varchar2(10);
  
  BEGIN
  
    --将参数的结束日期转换成日期格式
    V_proc_end_date := p_end_date;
  
/*    --核实结账记录
    SELECT COUNT(1)
      INTO V_BALANCE_STATUS_ROWS
      FROM STV.T_STL_BALANCE_BATCH
     WHERE ROWNUM = 1
          --只查询有效的期间
       and active = Pkg_Stl_Common.YES;*/
  
    --如果第一结账
    IF /*V_BALANCE_STATUS_ROWS = 0*/ 
      V_proc_end_date<= PKG_STL_BALANCE_CONSTANT.BEGIN_BALANCE_DATE
      THEN
      --标识第一次结账
      V_IS_FIRST_BALANCE := TRUE;
    
    ELSE
      --找到最大结账日期，用于判断上次结账是否存在异常
      SELECT MAX(BUSINESS_DATE)
        INTO V_LAST_BUSINES_DATE
        FROM STV.T_STL_BALANCE_BATCH
      --只查询有效的期间
       where active = Pkg_Stl_Common.YES;
    
      --异常情况，获得上次未完成的结账计划及结账时间      
      SELECT COUNT(1)
        INTO V_BALANCE_STATUS_ROWS
        FROM (SELECT STATUS
                FROM STV.T_STL_BALANCE_BATCH
               WHERE BUSINESS_DATE = V_LAST_BUSINES_DATE
                    --只查询有效的期间
                 and active = Pkg_Stl_Common.YES
               GROUP BY STATUS);
    
      --记录数不等于1，存在异常
      IF V_BALANCE_STATUS_ROWS != 1 THEN
        --上次结账成功为false
        V_LAST_DAY_IS_SUCCESS := FALSE;
        --进行回退 
        proc_unbalance(V_LAST_BUSINES_DATE);
      else
        --上次结账成功为成功
        V_LAST_DAY_IS_SUCCESS := true;
      END IF;
    END IF;
  
    --确认起始日期
    if V_IS_FIRST_BALANCE = true then
      --等于设定的日期
      V_BEGIN_DATE := Pkg_Stl_Balance_Constant.BEGIN_BALANCE_DATE;
      --上次异常
    elsif V_LAST_DAY_IS_SUCCESS = false then
      --本次结账日期 等于上次结账日期 
      V_BEGIN_DATE := V_LAST_BUSINES_DATE;
    else
      --否则认为正常     
      --本次结账日期 等于上次结账日期  + 1
      V_BEGIN_DATE := V_LAST_BUSINES_DATE + 1;
    end if;
  
    --上次结转日期 大于等于今天，不能继续
    IF V_BEGIN_DATE >= TRUNC(SYSDATE) or V_proc_end_date >= TRUNC(SYSDATE) THEN
      --返回异常
      RAISE_APPLICATION_ERROR('-20002', '不能对当天日业务进行结账！！');
    END IF;
  
    --按日期进行循环
    while v_begin_date <= V_proc_end_date loop
      begin
        --结束日期
        v_end_date := v_begin_date + 1;
      
        --生成签收未核销的应收单
        delete from stv.t_stl_pod_rec;
        commit;
      
        --签收未核销的数据 
        --反签收未核销数据
        insert into stv.t_stl_pod_rec
          (waybill_no, pod_date, pod_type, receivable_org_code)
          select pod.waybill_no,
                 pod.pod_date,
                 pod.pod_type,
                 bill.receivable_org_code
            from stl.t_stl_pod pod
           inner join stl.t_stl_bill_receivable bill
              on bill.waybill_no = pod.waybill_no
             and bill.is_red_back = PKG_STL_COMMON.NO
           where POD.POD_TYPE in
                 (PKG_STL_COMMON.POD__POD_TYPE__POD,
                  PKG_STL_COMMON.POD__POD_TYPE__UPD)
             AND POD.POD_DATE >= V_BEGIN_DATE
             AND POD.POD_DATE < v_end_date
             and bill.account_date < POD.POD_DATE
           group by pod.waybill_no,
                    pod.pod_date,
                    pod.pod_type,
                    bill.receivable_org_code;
        --提交
        commit;
      
        --批量初始批次表
        PKG_STL_BALANCE_BATCH.PROC_INIT_BALANCE_BATCH(V_BEGIN_DATE);
      
        --获得当前期间
        v_period := PKG_STL_BALANCE_COMMON.FUNC_GET_PERIOD(v_begin_date);
      
        --标记开始
        V_RESULT := PKG_STL_BALANCE_BATCH.FUNC_BEGIN_BALANCE_BATCH(Pkg_Stl_Balance_Constant.BALANCE_GLOBAL_ORG,
                                                                   V_BEGIN_DATE);
        --发生错误返回
        IF V_RESULT = FALSE THEN
          --返回异常
          RAISE_APPLICATION_ERROR('-20002',
                                  '结算标记不当天开始结账，期初发生异常');
        END IF;
      
        --如果是该月的第一天 或者第一次结账均计算期初
        IF V_IS_FIRST_BALANCE or
           PKG_STL_BALANCE_COMMON.FUNC_IS_MONTH_FIRST_DAY(V_BEGIN_DATE) THEN
        
          --始发应收期初
          V_RESULT := PKG_STL_BALANCE_COMMON.FUNC_DAILY_DETAIL_BEGIN(V_BEGIN_DATE,
                                                                     v_period);
        
          ---标记为已经结账，防止重复生成期初                                                                     
          V_IS_FIRST_BALANCE := false;
          --提交
          commit;
          --发生错误返回
          IF V_RESULT = FALSE THEN
            --返回异常
            RAISE_APPLICATION_ERROR('-20002', '结算期初发生异常');
          END IF;
        END IF;
      
        --所有任务已经结束，则退出则开始新一天的余额核算
        --hash 值从0开始
        v_index := 0;
        while v_index <= v_job_max_threads loop
          v_job_name := 'JOB_STV_BALANCE_TH' || to_char(v_index);
          --初始化定时器任务
          DBMS_SCHEDULER.create_job(job_name   => v_job_name,
                                    job_type   => 'PLSQL_BLOCK',
                                    enabled    => true,
                                    job_action => 'begin stv.pkg_stl_balance.proc_balance_job(to_date(''' ||
                                                  to_char(v_begin_date,
                                                          'yyyymmdd') ||
                                                  ''',''yyyymmdd''),' ||
                                                  v_job_max_threads || ',' ||
                                                  v_index || '); end;',
                                    COMMENTS   => '期末余额结账之' || v_job_name);
          ----等待1秒，  
          dbms_lock.sleep(0.5);
          v_index := v_index + 1;
        end loop;
      
        --如果进程一直运作中，则等待
        while func_is_sub_job_run loop
          ----等待1秒，  
          dbms_lock.sleep(1);
        end loop;
      
        --核算每日明细，生成每日汇总
        V_RESULT := PKG_STL_BALANCE_COMMON.FUNC_DAILY_Detail_END(V_BEGIN_DATE,
                                                                 v_end_date,
                                                                 V_PERIOD);
      
        --发生错误返回
        IF V_RESULT = FALSE THEN
          RAISE_APPLICATION_ERROR('-20002', '结算每日汇总明细发生异常');
        END IF;
      
        --如果是月末核算汇总
        --如果是该月的最后一天，计算期末余额
        IF PKG_STL_BALANCE_COMMON.FUNC_IS_MONTH_LAST_DAY(v_begin_date) THEN
          --计算对每日明细进行汇总，生成每月明细余额
          V_RESULT := PKG_STL_BALANCE_COMMON.FUNC_MONTH_DETAIL_END_AMOUNT(v_period);
        
          --发生错误返回
          IF V_RESULT = FALSE THEN
            RAISE_APPLICATION_ERROR('-20002', '结算每月余额明细发生异常');
          END IF;
        
          --计算月度发生额
          V_RESULT := PKG_STL_BALANCE_COMMON.FUNC_MONTH_CUSTOMER_END_AMOUNT(v_period);
        
          --发生错误返回
          IF V_RESULT = FALSE THEN
            RAISE_APPLICATION_ERROR('-20002', '结算每月余额汇总发生异常');
          END IF;
        
        END IF;
      
        --标记结束 
        V_RESULT := PKG_STL_BALANCE_BATCH.FUNC_END_BALANCE_BATCH(Pkg_Stl_Balance_Constant.BALANCE_GLOBAL_ORG,
                                                                 V_BEGIN_DATE);
        --发生错误返回
        IF V_RESULT = FALSE THEN
          --返回异常
          RAISE_APPLICATION_ERROR('-20002',
                                  '结算标记不当天结账结账发生异常');
        END IF;
      
        --对起始日期进行累加
        V_BEGIN_DATE := V_BEGIN_DATE + 1;
      
      end;
    end loop;
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(V_BEGIN_DATE, 'yyyymm'),
                                        V_BEGIN_DATE,
                                        V_BEGIN_DATE,
                                        'pkg_stl_balance.PROC_BALANCE',
                                        '期末结账发生异常,退出按日期循环结账');
  END;

  --------------------------------------------------
  --期末结账JOB
  --实现多线程结账
  --------------------------------------------------
  procedure proc_balance_job(p_business_date      in timestamp,
                             p_max_threads        int,
                             p_current_thread_num int) is
  
    --开始时间
    v_begin_date timestamp;
    --结束时间
    v_end_date timestamp;
    --当前组织
    v_org_code varchar2(200);
    --执行结果
    V_RESULT boolean;
  
    V_PLSQL VARCHAR2(4000);
  
    --任务游标
    TYPE TYPE_CURSOR IS REF CURSOR;
    V_CURSOR TYPE_CURSOR;
  begin
    --设置起始时间
    V_BEGIN_DATE := p_business_date;
    v_end_date   := V_BEGIN_DATE + 1;
  
    --定义sql
    V_PLSQL := q'<SELECT org_code FROM stv.t_stl_balance_batch T
		                       WHERE T.STATUS = :1 AND T.ACTIVE = :2 AND T.business_date = :3
                           and ora_hash(org_code, :4, 1) = :5
                           >';
  
    OPEN V_CURSOR FOR V_PLSQL
      USING Pkg_Stl_Balance_Constant.BALANCE_BATCH_INITED, Pkg_Stl_Common.YES, p_business_date, p_max_threads, p_current_thread_num;
    LOOP
      FETCH V_CURSOR
        INTO V_ORG_CODE;
      EXIT WHEN V_CURSOR%NOTFOUND;
      BEGIN
      
        --标记开始
        V_RESULT := PKG_STL_BALANCE_BATCH.FUNC_BEGIN_BALANCE_BATCH(V_ORG_CODE,
                                                                   V_BEGIN_DATE);
        --标记成功，继续结账
        IF V_RESULT = false THEN
          --返回异常
          RAISE_APPLICATION_ERROR('-20002',
                                  '标记开始结账，存在事务并发错误，请联系管理员！');
        end if;
      
        --进行应收结账
        PROC_COUNT_BALANCE_RECEIVABLE(V_ORG_CODE, V_BEGIN_DATE, V_END_DATE);
        --进行应付结账
        PROC_COUNT_BALANCE_PAYABLE(V_ORG_CODE, V_BEGIN_DATE, V_END_DATE);
        --进行预收结账
        PROC_COUNT_BALANCE_DEPOSIT(V_ORG_CODE, V_BEGIN_DATE, V_END_DATE);
      
        --标志结束
        V_RESULT := PKG_STL_BALANCE_BATCH.FUNC_END_BALANCE_BATCH(V_ORG_CODE,
                                                                 V_BEGIN_DATE);
        IF V_RESULT = FALSE THEN
          --返回异常 
          RAISE_APPLICATION_ERROR('-20002',
                                  '标记完成结账，存在事务并发错误，请联系管理员！');
        END IF;
        --提交
        COMMIT;
      end;
    end loop;
    --关闭游标
    close V_CURSOR;
    --发生异常时记录，返回false
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(V_BEGIN_DATE, 'yyyymm'),
                                        V_BEGIN_DATE,
                                        V_BEGIN_DATE,
                                        'pkg_stl_balance.proc_balance_job',
                                        V_ORG_CODE || '多线程调度');
  end;

  ---------------------------------------------------------------
  --期末反结账
  --实现将截至日期的余额进行删除
  --注意：只需要删除有效的余额表，因为将来修复数据时可能会插入无效的余额表  
  ------------------------------------------------------------------
  PROCEDURE PROC_UNBALANCE(P_BEGIN_DATE IN timestamp -- 起始日期
                           ) IS
    --结束日期
    v_end_date date;
  BEGIN
  
    --将字符日期转换成日期格式
     v_end_date := trunc(P_BEGIN_DATE);
     
     if v_end_date<=PKG_STL_BALANCE_CONSTANT.BEGIN_BALANCE_DATE then 
     v_end_date := PKG_STL_BALANCE_CONSTANT.BEGIN_BALANCE_DATE;
     end if;   
    
    --删除余额明细数据
    DELETE FROM STV.T_STL_BALANCE_DETAIL
     WHERE BALANCE_TIME >= v_end_date
       and active = Pkg_Stl_Common.YES
       and is_init = pkg_stl_common.NO --删除非初始化的明细数据
       ;
    commit;
  
    --删除余额表数据
    DELETE FROM STV.T_STL_BALANCE
     WHERE BALANCE_TIME >= v_end_date
       and active = Pkg_Stl_Common.YES;
    commit;
  
    --删除每日运单明细
    delete from stv.t_stl_daily_balance_tmp
     where BALANCE_TIME >= v_end_date
          
       and active = Pkg_Stl_Common.YES;
    commit;
  
    --删除每日运单汇总
    delete from stv.t_stl_daily_balance
     where BALANCE_TIME >= v_end_date
       and active = Pkg_Stl_Common.YES;
    commit;
  
    --删除已经结账的批次号
    delete from STV.T_STL_BALANCE_BATCH B
     WHERE B.BUSINESS_DATE >= v_end_date;
    --提交
    COMMIT;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        P_BEGIN_DATE,
                                        P_BEGIN_DATE,
                                        'pkg_stl_balance.PROC_UNBALANCE',
                                        '期末反结账');
  END;

  ------------------------------------------------------------------
  --应收期末结账
  ------------------------------------------------------------------
  PROCEDURE PROC_COUNT_BALANCE_RECEIVABLE(P_ORG_CODE   IN VARCHAR2, --业务组织
                                          P_BEGIN_DATE IN DATE, -- 起始日期
                                          P_END_DATE   IN DATE -- 结束日期
                                          ) IS
    --执行结果
    V_RESULT BOOLEAN;
    --结账期间
    V_PERIOD VARCHAR2(10);
    --结算类型
    V_BALANCE_TYPE VARCHAR2(50);
  
  BEGIN
    --初始结账类型 应收期末结账
    V_BALANCE_TYPE := Pkg_Stl_Balance_Constant.BALANCE_TYPE_RECEIVABLE;
  
    --获得结账期间
    V_PERIOD := PKG_STL_BALANCE_COMMON.FUNC_GET_PERIOD(P_BEGIN_DATE);
  
    --执行应收期末结账
    V_RESULT := PKG_STL_BALANCE_RECEIVABLE.FUNC_COUNT_BALANCE(P_ORG_CODE,
                                                              P_BEGIN_DATE,
                                                              P_END_DATE,
                                                              V_PERIOD,
                                                              V_BALANCE_TYPE);
    --返回结果不成功，抛出异常，回滚
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '应收结账不成功！');
    END IF;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'pkg_stl_balance.func_count_balance_receivable',
                                        '应收期末结账');
  END;

  ------------------------------------------------------------------
  --应付期末结账
  ------------------------------------------------------------------
  PROCEDURE PROC_COUNT_BALANCE_PAYABLE(P_ORG_CODE   IN VARCHAR2, --业务组织
                                       P_BEGIN_DATE IN DATE, -- 起始日期
                                       P_END_DATE   IN DATE -- 结束日期
                                       ) IS
  
    --执行结果
    V_RESULT BOOLEAN;
    --结账期间
    V_PERIOD VARCHAR2(10);
    --结算类型
    V_BALANCE_TYPE VARCHAR2(50);
  
  BEGIN
  
    --初始结账类型 应付期末结账
    V_BALANCE_TYPE := Pkg_Stl_Balance_Constant.BALANCE_TYPE_PAYABLE;
    --获得结账期间
    V_PERIOD := PKG_STL_BALANCE_COMMON.FUNC_GET_PERIOD(P_BEGIN_DATE);
  
    --执行应付期末结账
    V_RESULT := PKG_STL_BALANCE_PAYABLE.FUNC_COUNT_BALANCE(P_ORG_CODE,
                                                           P_BEGIN_DATE,
                                                           P_END_DATE,
                                                           V_PERIOD,
                                                           V_BALANCE_TYPE);
    --返回结果不成功，抛出异常，回滚
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '应付结账不成功！');
    END IF;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'pkg_stl_balance.func_count_balance_payable',
                                        '应付期末结账');
  END;

  ------------------------------------------------------------------
  --预收期末结账
  ------------------------------------------------------------------
  PROCEDURE PROC_COUNT_BALANCE_DEPOSIT(P_ORG_CODE   IN VARCHAR2, --业务组织
                                       P_BEGIN_DATE IN DATE, -- 起始日期
                                       P_END_DATE   IN DATE -- 结束日期
                                       ) IS
  
    --执行结果
    V_RESULT BOOLEAN;
    --结账期间
    V_PERIOD VARCHAR2(10);
    --结算类型
    V_BALANCE_TYPE VARCHAR2(50);
  
  BEGIN
  
    --获得结账期间
    V_PERIOD := PKG_STL_BALANCE_COMMON.FUNC_GET_PERIOD(P_BEGIN_DATE);
  
    --初始结账类型 预收期末结账 --单据
    V_BALANCE_TYPE := Pkg_Stl_Balance_Constant.BALANCE_TYPE_DEPOSIT_BILL;
  
    --执行应收期末结账--单据
    V_RESULT := PKG_STL_BALANCE_DEPOSIT_BILL.FUNC_COUNT_BALANCE(P_ORG_CODE,
                                                                P_BEGIN_DATE,
                                                                P_END_DATE,
                                                                V_PERIOD,
                                                                V_BALANCE_TYPE);
    --返回结果不成功，抛出异常，回滚
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '预收(单据)结账不成功！');
    END IF;
  
    --中转科目
    --初始结账类型 预收期末结账 --中转科目
    V_BALANCE_TYPE := Pkg_Stl_Balance_Constant.BALANCE_TYPE_DEPOSIT_TF;
  
    --执行应收期末结账--单据
    V_RESULT := PKG_STL_BALANCE_DEPOSIT_TF.FUNC_COUNT_BALANCE(P_ORG_CODE,
                                                              P_BEGIN_DATE,
                                                              P_END_DATE,
                                                              V_PERIOD,
                                                              V_BALANCE_TYPE);
  
    --返回结果不成功，抛出异常，回滚
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '预收(中转)结账不成功！');
    END IF;
    --现金中专科目
    V_BALANCE_TYPE := Pkg_Stl_Balance_Constant.BALANCE_TYPE_DEPOSIT_TF;
     --执行应收期末结账--单据
    V_RESULT := PKG_STL_BALANCE_cash_tf.FUNC_COUNT_BALANCE(P_ORG_CODE,
                                                              P_BEGIN_DATE,
                                                              P_END_DATE,
                                                              V_PERIOD,
                                                              V_BALANCE_TYPE);
  
    --返回结果不成功，抛出异常，回滚
    IF V_RESULT = FALSE THEN
      RAISE_APPLICATION_ERROR('-20002', '现金(中转)结账不成功！');
    END IF;
    --捕捉异常
  EXCEPTION
  
    WHEN OTHERS THEN
      ROLLBACK;
      --插入错误LOG表
      PKG_STL_COMMON.PROC_STL_ERROR_LOG(TO_CHAR(P_BEGIN_DATE, 'yyyymm'),
                                        P_BEGIN_DATE,
                                        P_END_DATE,
                                        'pkg_stl_balance.func_count_balance_deposit',
                                        '预收期末结账');
  END;

END PKG_STL_BALANCE;
/
