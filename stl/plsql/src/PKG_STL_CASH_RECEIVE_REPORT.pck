CREATE OR REPLACE PACKAGE PKG_STL_CASH_RECEIVE_REPORT IS

  -- Author  : 088933-foss-zhangjiheng

  -- Created : 2012-12-10 19:27:16

  -- Purpose : 生成现金收入报表

  ------------------------------------------------------------------

  --生成现金收入报表（用于每天定时任务跑全公司所有网点的收入报表）

  ------------------------------------------------------------------

  PROCEDURE PROC_STL_CASH_RECEIVE_RPT(P_BEGIN_DATE IN TIMESTAMP, --开始日期

                                      P_END_DATE IN TIMESTAMP --结束日期

                                      );

  ------------------------------------------------------------------

  --生成现金收入报表（根据单个网点和日期生成收入报表）

  ------------------------------------------------------------------

  PROCEDURE PROC_STL_CASH_RECEIVE_RPT_ORG(P_BEGIN_DATE IN TIMESTAMP, --开始日期

                                          P_END_DATE IN TIMESTAMP, --结束日期

                                          P_ORG_CODE IN VARCHAR2, --网点编码

                                          P_CREATE_USER_CODE IN VARCHAR2, --创建人工号

                                          P_CREATE_USER_NAME IN VARCHAR2, --创建人名称

                                          P_CURRENT_TIME IN TIMESTAMP --创建日期

                                          );

END PKG_STL_CASH_RECEIVE_REPORT;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_CASH_RECEIVE_REPORT IS

  -- Author  : 088933-foss-zhangjiheng
  -- Created : 2012-12-10 19:27:16
  -- Purpose : 生成缴款报表

  ------------------------------------------------------------------
  --生成缴款报表（用于每天定时任务跑全公司所有网点的收入报表）
  ------------------------------------------------------------------
  PROCEDURE PROC_STL_CASH_RECEIVE_RPT(P_BEGIN_DATE IN TIMESTAMP, --开始日期
                                      P_END_DATE   IN TIMESTAMP --结束日期
                                      ) IS
    V_USER_CODE    VARCHAR2(50); --创建人工号
    V_USER_NAME    VARCHAR2(50); --创建人名称
    V_CURRENT_TIME DATE; --创建日期
    --定义游标获得所有有效的组织
    CURSOR ORG_CURSOR IS
      SELECT CODE
        FROM BSE.T_BAS_SALES_DEPARTMENT SALEORGS
      --有效的组织
       WHERE SALEORGS.ACTIVE = STV.PKG_STL_COMMON.ACTIVE;
    --定义游标行数
    ORG_CURSOR_ROW ORG_CURSOR%ROWTYPE;
  BEGIN
    V_USER_CODE    := 'SYSTEM';
    V_USER_NAME    := '系统';
    V_CURRENT_TIME := SYSDATE;
    FOR ORG_CURSOR_ROW IN ORG_CURSOR LOOP
      --掉用处理业务存储过程

      PROC_STL_CASH_RECEIVE_RPT_ORG(P_BEGIN_DATE,
                                    P_END_DATE,
                                    ORG_CURSOR_ROW.CODE,
                                    V_USER_CODE,
                                    V_USER_NAME,
                                    V_CURRENT_TIME);

    END LOOP;
  END;
  ------------------------------------------------------------------
  --生成现金收入报表（根据单个网点和日期生成收入报表）
  ------------------------------------------------------------------
  PROCEDURE PROC_STL_CASH_RECEIVE_RPT_ORG(P_BEGIN_DATE       IN TIMESTAMP, --开始日期
                                          P_END_DATE         IN TIMESTAMP, --结束日期
                                          P_ORG_CODE         IN VARCHAR2, --网点编码
                                          P_CREATE_USER_CODE IN VARCHAR2, --创建人工号
                                          P_CREATE_USER_NAME IN VARCHAR2, --创建人名称
                                          P_CURRENT_TIME     IN TIMESTAMP --创建日期
                                          ) IS
    V_REPORT_NO               VARCHAR2(50); --现金收入报表编号
    V_REPAYMENT_DATE          TIMESTAMP; --还款单记账开始日期
    V_CASH_COLLECTION_DATE    TIMESTAMP; --现金收款单记账开始日期
    V_DEPOSIT_RECEIVABLE_DATE TIMESTAMP; --预收单记账开始日期
    V_COUNT_NUM               INT; --统计现金缴款报表明细记录总条数

  BEGIN
    V_REPORT_NO := SYS_GUID();

    /*如果开始日期、结束日期、网点编码不为空且结束日期大于等于开始日期，才执行生成报表业务，否则插入日志信息*/

    IF P_BEGIN_DATE IS NOT NULL AND P_END_DATE IS NOT NULL AND
       P_ORG_CODE IS NOT NULL AND P_END_DATE > P_BEGIN_DATE THEN

        /*查询历史统计记录获取还款单、现金收款单、预收单的记账开始日期*/
        /*传入的开始日期只有在第一次统计时有用，当有历史记录时，开始日期取历史记录里面的最后统计日期 */
        BEGIN
          SELECT REPAYMENT_DATE,
                 CASH_COLLECTION_DATE,
                 DEPOSIT_RECEIVABLE_DATE
            INTO V_REPAYMENT_DATE,
                 V_CASH_COLLECTION_DATE,
                 V_DEPOSIT_RECEIVABLE_DATE
            FROM STL.T_STL_CASH_COLLECTION_COUNT_H CCCH
           WHERE CCCH.ORG_CODE = P_ORG_CODE;
        EXCEPTION
          WHEN NO_DATA_FOUND THEN
            V_REPAYMENT_DATE          := P_BEGIN_DATE;
            V_CASH_COLLECTION_DATE    := P_BEGIN_DATE;
            V_DEPOSIT_RECEIVABLE_DATE := P_BEGIN_DATE;
          WHEN TOO_MANY_ROWS THEN
            --插入错误LOG表
            STV.PKG_STL_COMMON.PROC_STL_ERROR_LOG(NULL, --账期
                                              P_BEGIN_DATE, --开始时间
                                              P_END_DATE, --截止时间
                                              '查询历史统计记录中一个网点存在相同的记录', --操作内容
                                              'PROC_STL_CASH_RECEIVE_RPT_ORG', --备注
                                              'STL_USER_CODE', --创建人
                                              SQLCODE, --错误码
                                              SQLERRM, --错误消息
                                              SYSDATE);
          WHEN OTHERS THEN
            --插入错误LOG表
            stv.PKG_STL_COMMON.PROC_STL_ERROR_LOG(NULL, --账期
                                              P_BEGIN_DATE, --开始时间
                                              P_END_DATE, --截止时间
                                              '生成缴款报表明细', --操作内容
                                              'PROC_STL_CASH_RECEIVE_RPT_ORG', --备注
                                              'STL_USER_CODE', --创建人
                                              SQLCODE, --错误码
                                              SQLERRM, --错误消息
                                              SYSDATE);

        END;

        BEGIN
          --生成现金收入明细报表
          INSERT INTO STL.T_STL_CASH_COLLECTION_RPT_D
            (ID, --ID
             REPORT_NO, --报表编号
             SOURCE_BILL_NO, --运单号
             WAYBILL_NO,
             SOURCE_BILL_TYPE, --来源单据类型
             COLLECTION_ORG_CODE, --收款部门编码
             COLLECTION_ORG_NAME, --收款部门名称
             CUSTOMER_CODE, --客户编码
             CUSTOMER_NAME, --客户名称
             /*应缴款金额*/
             AMOUNT,
             /*未缴款金额*/
             OVERDUE_AMOUNT,
             /*已缴款金额*/
             PAID_AMOUNT,
             /*营业款金额*/
             CLERKS_AMOUNT,
             /*非营业款金额*/
             UNCLERKS_AMOUNT,
             /*预收款金额*/
             PRECOLLECTED_AMOUNT,
             CREATE_TIME,
             ACCOUNT_DATE,
             BUSINESS_DATE,
             PAYMENT_TYPE,
             CUSTOMER_TYPE,
             MODIFY_TIME)

            SELECT SYS_GUID(),
                   V_REPORT_NO,
                   SOURCE_BILL_NO,
                   WAYBILL_NO,
                   SOURCE_BILL_TYPE,
                   COLLECTION_ORG_CODE,
                   COLLECTION_ORG_NAME,
                   CUSTOMER_CODE,
                   CUSTOMER_NAME,
                   AMOUNT,
                   OVERDUE_AMOUNT,
                   PAID_AMOUNT,
                   CLERKS_AMOUNT,
                   UNCLERKS_AMOUNT,
                   PRECOLLECTED_AMOUNT,
                   P_CURRENT_TIME,
                   ACCOUNT_DATE,
                   BUSINESS_DATE,
                   PAYMENT_TYPE,
                   CUSTOMER_TYPE,
                   SYSDATE
              FROM (
                    --查询现金收款单
                    SELECT MAX(TBC.CASH_COLLECTION_NO) AS SOURCE_BILL_NO,
                            MAX(TBC.WAYBILL_NO) AS WAYBILL_NO,
                            MAX(STV.PKG_STL_COMMON.CASH_RPT_D_SOURCE_BILL_TYPE_XS) AS SOURCE_BILL_TYPE,
                            TBC.CUSTOMER_CODE AS CUSTOMER_CODE,
                            MAX(TBC.CUSTOMER_NAME) AS CUSTOMER_NAME,
                            MAX(TBC.COLLECTION_ORG_CODE) AS COLLECTION_ORG_CODE,
                            MAX(TBC.COLLECTION_ORG_NAME) AS COLLECTION_ORG_NAME,
                            SUM(TBC.AMOUNT) AS AMOUNT,
                            SUM(0) AS PAID_AMOUNT,
                            SUM(TBC.AMOUNT) AS OVERDUE_AMOUNT,
                            SUM(TBC.AMOUNT) AS CLERKS_AMOUNT,
                            SUM(0) AS UNCLERKS_AMOUNT,
                            SUM(0) AS PRECOLLECTED_AMOUNT,
                            MAX(TBC.ACCOUNT_DATE) AS ACCOUNT_DATE,
                            MAX(TBC.BUSINESS_DATE) AS BUSINESS_DATE,
                            MAX(TBC.PAYMENT_TYPE) AS PAYMENT_TYPE,
                            MAX(TBC.CUSTOMER_TYPE) AS CUSTOMER_TYPE
                      FROM STL.T_STL_BILL_CASH_COLLECTION TBC
                     WHERE TBC.ACCOUNT_DATE > V_CASH_COLLECTION_DATE
                       AND TBC.ACCOUNT_DATE < P_END_DATE
                       AND TBC.COLLECTION_ORG_CODE = P_ORG_CODE
                       AND TBC.PAYMENT_TYPE IN
                           (stv.PKG_STL_COMMON.PAYMENT_TYPE_CASH,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_CARD,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_ONLINE)
                     GROUP BY TBC.SOURCE_BILL_NO, TBC.CUSTOMER_CODE
                    UNION ALL
                    --查询预收单
                    SELECT MAX(TSD.DEPOSIT_RECEIVED_NO) AS SOURCE_BILL_NO,
                           MAX(NULL) AS WAYBILL_NO,
                           MAX(STV.PKG_STL_COMMON.CASH_RPT_D_SOURCE_BILL_TYPE_US) AS SOURCE_BILL_TYPE,
                           MAX(TSD.CUSTOMER_CODE) AS CUSTOMER_CODE,
                           MAX(TSD.CUSTOMER_NAME) AS CUSTOMER_NAME,
                           MAX(TSD.COLLECTION_ORG_CODE) AS COLLECTION_ORG_CODE,
                           MAX(TSD.COLLECTION_ORG_NAME) AS COLLECTION_ORG_NAME,
                           SUM(TSD.AMOUNT) AS AMOUNT,
                           SUM(0) AS PAID_AMOUNT,
                           SUM(TSD.AMOUNT) AS OVERDUE_AMOUNT,
                           SUM(0) AS CLERKS_AMOUNT,
                           SUM(0) AS UNCLERKS_AMOUNT,
                           SUM(TSD.AMOUNT) AS PRECOLLECTED_AMOUNT,
                           MAX(TSD.ACCOUNT_DATE) AS ACCOUNT_DATE,
                           MAX(TSD.BUSINESS_DATE) AS BUSINESS_DATE,
                           MAX(TSD.PAYMENT_TYPE) AS PAYMENT_TYPE,
                           MAX(TSD.CUSTOMER_TYPE) AS CUSTOMER_TYPE
                      FROM STL.T_STL_BILL_DEPOSIT_RECEIVED TSD
                     WHERE TSD.ACCOUNT_DATE > V_DEPOSIT_RECEIVABLE_DATE
                       AND TSD.ACCOUNT_DATE < P_END_DATE
                       AND TSD.COLLECTION_ORG_CODE = P_ORG_CODE
                       AND TSD.PAYMENT_TYPE IN
                           (STV.PKG_STL_COMMON.PAYMENT_TYPE_CASH,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_CARD,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_ONLINE)
                     GROUP BY TSD.DEPOSIT_RECEIVED_NO
                    UNION ALL
                    --查询还款单
                    SELECT MAX(TBR.REPAYMENT_NO) AS SOURCE_BILL_NO,
                           MAX(NULL) AS WAYBILL_NO,
                           MAX(STV.PKG_STL_COMMON.CASH_RPT_D_SOURCE_BILL_TYPE_HK) AS SOURCE_BILL_TYPE,
                           MAX(TBR.CUSTOMER_CODE) AS CUSTOMER_CODE,
                           MAX(TBR.CUSTOMER_NAME) AS CUSTOMER_NAME,
                           MAX(TBR.COLLECTION_ORG_CODE) AS COLLECTION_ORG_CODE,
                           MAX(TBR.COLLECTION_ORG_NAME) AS COLLECTION_ORG_NAME,
                           SUM(TBR.AMOUNT) AS AMOUNT,
                           SUM(0) AS PAID_AMOUNT,
                           SUM(TBR.AMOUNT) AS OVERDUE_AMOUNT,
                           SUM(TBR.AMOUNT) CLERKS_AMOUNT,
                           SUM(0) UNCLERKS_AMOUNT,
                           SUM(0) AS PRECOLLECTED_AMOUNT,
                           MAX(TBR.ACCOUNT_DATE) AS ACCOUNT_DATE,
                           MAX(TBR.BUSINESS_DATE) AS BUSINESS_DATE,
                           MAX(TBR.PAYMENT_TYPE) AS PAYMENT_TYPE,
                           MAX(TBR.CUSTOMER_TYPE) AS CUSTOMER_TYPE
                      FROM STL.T_STL_BILL_REPAYMENT TBR
                     WHERE TBR.ACCOUNT_DATE > V_REPAYMENT_DATE
                       AND TBR.ACCOUNT_DATE < P_END_DATE
                       AND TBR.COLLECTION_ORG_CODE = P_ORG_CODE
                       AND TBR.PAYMENT_TYPE IN
                           (STV.PKG_STL_COMMON.PAYMENT_TYPE_CASH,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_CARD,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_TELE_TRANSFER,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_NOTE,
                            STV.PKG_STL_COMMON.PAYMENT_TYPE_ONLINE)
                     GROUP BY TBR.REPAYMENT_NO);
        EXCEPTION
          WHEN OTHERS THEN
            ROLLBACK;
            --插入错误LOG表
            STV.PKG_STL_COMMON.PROC_STL_ERROR_LOG(NULL, --账期
                                              P_BEGIN_DATE, --开始时间
                                              P_END_DATE, --截止时间
                                              '生成缴款报表明细', --操作内容
                                              'PROC_STL_CASH_RECEIVE_RPT_ORG', --备注
                                              'STL_USER_CODE', --创建人
                                              SQLCODE, --错误码
                                              SQLERRM, --错误消息
                                              SYSDATE);
        END;

        /***获取现金收款单、还款单、预收单最大记账日期 如果为空记录日志，不抛出异常****/
        BEGIN
          SELECT MAX(US_ACCOUNT_DATE) AS V_DEPOSIT_RECEIVABLE_DATE,
                 MAX(XS_ACCOUNT_DATE) AS V_CASH_COLLECTION_DATE,
                 MAX(HK_ACCOUNT_DATE) AS V_REPAYMENT_DATE

            INTO V_DEPOSIT_RECEIVABLE_DATE,
                 V_CASH_COLLECTION_DATE,
                 V_REPAYMENT_DATE

            FROM (SELECT CASE SOURCE_BILL_TYPE
                           WHEN 'US' THEN
                            TEMP.ACCOUNT_DATE
                         END AS US_ACCOUNT_DATE,
                         CASE SOURCE_BILL_TYPE
                           WHEN 'XS' THEN
                            TEMP.ACCOUNT_DATE
                         END AS XS_ACCOUNT_DATE,
                         CASE SOURCE_BILL_TYPE
                           WHEN 'HK' THEN
                            TEMP.ACCOUNT_DATE
                         END AS HK_ACCOUNT_DATE

                    FROM (
                          /***查询某一时间内现金收款单、预收单、还款单的最大账期***/
                          SELECT MAX(ACCOUNT_DATE) ACCOUNT_DATE,
                                  MAX(SOURCE_BILL_TYPE) SOURCE_BILL_TYPE
                            FROM STL.T_STL_CASH_COLLECTION_RPT_D TD
                           WHERE COLLECTION_ORG_CODE = P_ORG_CODE
                             AND REPORT_NO = V_REPORT_NO
                           GROUP BY SOURCE_BILL_TYPE) TEMP);
        END;

        --首先判断该网点在历史记录表中是否存在，不存在执行insert，存在则执行UPDATE；
        BEGIN
          MERGE INTO STL.T_STL_CASH_COLLECTION_COUNT_H CCCH
          USING (SELECT COUNT(*) COU, MAX(W.ORG_CODE) CODE
                   FROM STL.T_STL_CASH_COLLECTION_COUNT_H W
                  WHERE W.ORG_CODE = P_ORG_CODE) T
          ON (T.CODE = CCCH.ORG_CODE AND T.COU > 0)
          WHEN MATCHED THEN
            UPDATE
               SET
                   CCCH.REPAYMENT_DATE          = DECODE(V_REPAYMENT_DATE,NULL,CCCH.REPAYMENT_DATE,V_REPAYMENT_DATE),
                   CCCH.DEPOSIT_RECEIVABLE_DATE = DECODE(V_DEPOSIT_RECEIVABLE_DATE,NULL,CCCH.DEPOSIT_RECEIVABLE_DATE,V_DEPOSIT_RECEIVABLE_DATE),
                   CCCH.CASH_COLLECTION_DATE    = DECODE(V_CASH_COLLECTION_DATE,NULL,CCCH.CASH_COLLECTION_DATE,V_CASH_COLLECTION_DATE),

                   CCCH.MODIFY_DATE             = SYSDATE
             WHERE CCCH.ORG_CODE = P_ORG_CODE

          WHEN NOT MATCHED THEN
            INSERT
              (ID,
               ORG_CODE,
               REPAYMENT_DATE,
               CASH_COLLECTION_DATE,
               DEPOSIT_RECEIVABLE_DATE,
               MODIFY_DATE)
            VALUES
              (SYS_GUID(),
               P_ORG_CODE,
               DECODE(V_REPAYMENT_DATE,NULL,P_BEGIN_DATE,V_REPAYMENT_DATE),
               DECODE(V_CASH_COLLECTION_DATE,NULL,P_BEGIN_DATE,V_CASH_COLLECTION_DATE),
               DECODE(V_DEPOSIT_RECEIVABLE_DATE,NULL,P_BEGIN_DATE,V_DEPOSIT_RECEIVABLE_DATE),
               SYSDATE);
        EXCEPTION
          WHEN OTHERS THEN
            --插入错误LOG表
            STV.PKG_STL_COMMON.PROC_STL_ERROR_LOG(NULL, --账期
                                              P_BEGIN_DATE, --开始时间
                                              P_END_DATE, --截止时间
                                              '修改或插入网点缴款报表历史统计记录信息', --操作内容
                                              'PROC_STL_CASH_RECEIVE_RPT_ORG', --备注
                                              'STL_USER_CODE', --创建人
                                              SQLCODE, --错误码
                                              SQLERRM, --错误消息
                                              SYSDATE);
        END;




       SELECT COUNT(*)
         INTO V_COUNT_NUM
         FROM STL. T_STL_CASH_COLLECTION_RPT_D TCRD
        WHERE TCRD.REPORT_NO = V_REPORT_NO
          AND TCRD.ACCOUNT_DATE > P_BEGIN_DATE
          AND TCRD.ACCOUNT_DATE < P_END_DATE
          AND TCRD.COLLECTION_ORG_CODE = P_ORG_CODE;

         --生成缴款报表明细大于零，才生成缴款报表
        IF V_COUNT_NUM>0 THEN
          BEGIN
            INSERT INTO STL.T_STL_CASH_COLLECTION_RPT
              (ID,
               REPORT_NO,
               ORG_CODE,
               ORG_NAME,
               BUSINESS_DATE,
               DUES_AMOUNT,
               PAID_AMOUNT,
               OVERDUE_AMOUNT,
               CLERKS_AMOUNT,
               UNCLERKS_AMOUNT,
               PRECOLLECTED_AMOUNT,
               CREATE_USER_CODE,
               CREATE_USER_NAME,
               CREATE_TIME,
               MODIFY_TIME,
               VERSION_NO,
               CURRENCY_CODE)
              SELECT SYS_GUID(),
                     V_REPORT_NO,
                     P_ORG_CODE,
                     P_ORG_CODE,
                     P_CURRENT_TIME, --业务日期
                                                --NVL 替换当金额为空时，返回0
                     NVL(SUM(NVL(DECODE(TCRD.PAYMENT_TYPE,STV.PKG_STL_COMMON.PAYMENT_TYPE_CASH,TCRD.AMOUNT,0),0)),0) AS DUES_AMOUNT,  --现金总金额
                     NVL(SUM(0),0)AS PAID_AMOUNT,   --已交款
                     NVL(SUM(NVL(DECODE(TCRD.PAYMENT_TYPE,STV.PKG_STL_COMMON.PAYMENT_TYPE_CASH,TCRD.AMOUNT,0) ,0)),0) AS OVERDUE_AMOUNT,  --现金未缴款金额
                     NVL(SUM(NVL(CLERKS_AMOUNT,0)),0) AS CLERKS_AMOUNT,
                     NVL(SUM(NVL(UNCLERKS_AMOUNT,0)),0) AS UNCLERKS_AMOUNT,
                     NVL(SUM(NVL(PRECOLLECTED_AMOUNT,0)),0) AS PRECOLLECTED_AMOUNT,

                     P_CREATE_USER_CODE,
                     P_CREATE_USER_NAME,
                     P_CURRENT_TIME,
                     SYSDATE,
                     '1',
                     STV.PKG_STL_COMMON.CURRENCY_CODE_RMB

                FROM STL. T_STL_CASH_COLLECTION_RPT_D TCRD
               WHERE TCRD.REPORT_NO = V_REPORT_NO
                 AND TCRD.COLLECTION_ORG_CODE = P_ORG_CODE;
          EXCEPTION
            WHEN OTHERS THEN
              ROLLBACK;
              --插入错误LOG表
              STV.PKG_STL_COMMON.PROC_STL_ERROR_LOG(NULL, --账期
                                                P_BEGIN_DATE, --开始时间
                                                P_END_DATE, --截止时间
                                                '生成缴款报表', --操作内容
                                                'PROC_STL_CASH_RECEIVE_RPT_ORG', --备注
                                                'STL_USER_CODE', --创建人
                                                SQLCODE, --错误码
                                                SQLERRM, --错误消息
                                                SYSDATE);
          END;
       END IF;

    ELSE

      --插入错误LOG表
      STV.PKG_STL_COMMON.PROC_STL_ERROR_LOG(NULL, --账期
                                        P_BEGIN_DATE, --开始时间
                                        P_END_DATE, --截止时间
                                        '生成现金收入报表：输入的日期或网点信息为空，参数不合法', --操作内容
                                        'PROC_STL_CASH_RECEIVE_RPT_ORG', --备注
                                        'STL_USER_CODE', --创建人
                                        NULL, --错误码
                                        '网点编码：' || P_ORG_CODE ||
                                        '输入的日期或网点信息为空，参数不合法', --错误消息
                                        SYSDATE);
    END IF;

    COMMIT;
  END;
END PKG_STL_CASH_RECEIVE_REPORT;
/
