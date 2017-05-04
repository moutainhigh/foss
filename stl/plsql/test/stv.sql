-----------------------------------------------
-- Export file for user STV                  --
-- Created by zhuwei on 2012-12-29, 09:41:37 --
-----------------------------------------------

spool stv.log

prompt
prompt Creating package PKG_STL_BALANCE_TEST
prompt =====================================
prompt
@@PKG_STL_BALANCE_TEST.PCK

prompt
prompt Creating procedure PROC_TEST_INSERT_BAD_ACCOUNT
prompt ===============================================
prompt
@@PROC_TEST_INSERT_BAD_ACCOUNT.PRC
prompt
prompt Creating procedure PROC_TEST_INSERT_DEPOSIT_RECED
prompt =================================================
prompt
@@PROC_TEST_INSERT_DEPOSIT_RECED.PRC
prompt
prompt Creating procedure PROC_TEST_INSERT_OTHER_REVENUE
prompt =================================================
prompt
@@PROC_TEST_INSERT_OTHER_REVENUE.PRC
prompt
prompt Creating procedure PROC_TEST_INSERT_PAYABLE
prompt ===========================================
prompt
@@PROC_TEST_INSERT_PAYABLE.PRC
prompt
prompt Creating procedure PROC_TEST_INSERT_PAYMENT
prompt ===========================================
prompt
@@PROC_TEST_INSERT_PAYMENT.PRC
prompt
prompt Creating procedure PROC_TEST_INSERT_REPAYMENT
prompt =============================================
prompt
@@PROC_TEST_INSERT_REPAYMENT.PRC
prompt
prompt Creating procedure PROC_TEST_INSERT_WRITEOFF
prompt ============================================
prompt
@@PROC_TEST_INSERT_WRITEOFF.PRC
prompt
prompt Creating procedure RROC_TEST_INSERT_RECEIVABLE
prompt ==============================================
prompt
@@RROC_TEST_INSERT_RECEIVABLE.PRC

spool off
