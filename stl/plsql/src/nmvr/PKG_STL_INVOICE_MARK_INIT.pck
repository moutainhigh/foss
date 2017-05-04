CREATE OR REPLACE PACKAGE PKG_STL_INVOICE_MARK_INIT IS

	-- ==============================================================
	-- Author  : ZBW
	-- Created : 2013-11-18
	-- Purpose : 结算财务基础单据发票标记字段初始化
	-- ==============================================================

	-----------------------------------------------------------------
	-- 给定表是否存在
	-----------------------------------------------------------------
	FUNCTION FUNC_TABLE_EXIST(P_TABLE_NAME VARCHAR2) RETURN BOOLEAN;

	-----------------------------------------------------------------
	-- 初始化
	-----------------------------------------------------------------
	FUNCTION FUNC_INITIALIZE RETURN BOOLEAN; -- 返回结果 true:成功，false:失败

	-----------------------------------------------------------------
	-- 销毁
	-----------------------------------------------------------------
	FUNCTION FUNC_DESTROY RETURN BOOLEAN;

	-----------------------------------------------------------------
	-- 执行初始化
	-----------------------------------------------------------------
	PROCEDURE PROC_EXECUTE;

	TYPE LOG_RECORD IS RECORD(
		ID           VARCHAR2(50),
		TO_INIT_DATE DATE,
		IS_INIT      CHAR(1),
		INIT_TIME    TIMESTAMP);

	TYPE LOG_CURSOR IS REF CURSOR;

	--记录初始化日志
	V_LOG_TABLE VARCHAR2(50) := 'T_STV_INVOICE_MARK_INIT_LOG';

	--初始化开始时间和结束时间
	V_END_DATE   DATE := TO_DATE('2014-01-01', 'YYYY-MM-DD');
	V_START_DATE DATE := TO_DATE('2010-01-01', 'YYYY-MM-DD');

	--执行时间在4点到6点
	V_EXECUTE_HOUR_BEGIN INT := 2;
	V_EXECUTE_HOUR_END   INT := 6;

END PKG_STL_INVOICE_MARK_INIT;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_INVOICE_MARK_INIT IS

	-- ==============================================================
	-- Author  : ZBW
	-- Created : 2013-11-18
	-- Purpose : 结算财务基础单据发票标记字段初始化
	-- ==============================================================

	-----------------------------------------------------------------
	-- 给定表是否存在
	-----------------------------------------------------------------
	FUNCTION FUNC_TABLE_EXIST(P_TABLE_NAME VARCHAR2) RETURN BOOLEAN IS
		V_RESULT BOOLEAN := FALSE;
		V_ROWS   INT;
	BEGIN
	
		V_ROWS := 0;
	
		--查询是否存在日志表
		SELECT COUNT(1)
			INTO V_ROWS
			FROM USER_TABLES T
		 WHERE T.TABLE_NAME = P_TABLE_NAME;
	
		--如果存在，返回TRUE
		IF V_ROWS = 1 THEN
			V_RESULT := TRUE;
		END IF;
	
		RETURN V_RESULT;
	
	END;

	-----------------------------------------------------------------
	-- 初始化
	-----------------------------------------------------------------
	FUNCTION FUNC_INITIALIZE RETURN BOOLEAN IS
		V_ROWS  INT;
		V_SQL   VARCHAR2(200);
		V_DAY   DATE := V_END_DATE - 1;
		V_EXIST BOOLEAN;
	BEGIN
	
		V_EXIST := FUNC_TABLE_EXIST(V_LOG_TABLE);
	
		--如果不存在，则创建日志表
		IF NOT V_EXIST THEN
		
			V_SQL := 'CREATE TABLE ' || V_LOG_TABLE ||
							 '(ID VARCHAR2(50) PRIMARY KEY,TO_INIT_DATE DATE,IS_INIT CHAR(1),INIT_TIME TIMESTAMP)';
		
			EXECUTE IMMEDIATE V_SQL;
		
			V_SQL  := 'INSERT INTO ' || V_LOG_TABLE ||
								'(ID,TO_INIT_DATE,IS_INIT) VALUES(SYS_GUID(),:TO_INIT_DATE,''N'')';
			V_ROWS := 0;
		
			--初始化天数
			WHILE V_DAY >= V_START_DATE LOOP
			
				EXECUTE IMMEDIATE V_SQL
					USING V_DAY;
			
				V_DAY  := V_DAY - 1;
				V_ROWS := V_ROWS + 1;
			
				--100条提交一次
				IF MOD(V_ROWS, 100) = 0 THEN
					COMMIT;
					V_ROWS := 0;
				END IF;
			END LOOP;
		
			COMMIT;
		
		END IF;
	
		RETURN TRUE;
	
	END; -- 返回结果 true:成功，false:失败

	-----------------------------------------------------------------
	--销毁
	-----------------------------------------------------------------
	FUNCTION FUNC_DESTROY RETURN BOOLEAN IS
		V_SQL   VARCHAR2(200);
		V_EXIST BOOLEAN;
	BEGIN
	
		V_EXIST := FUNC_TABLE_EXIST(V_LOG_TABLE);
	
		--如果存在，则删除表
		IF V_EXIST THEN
		
			V_SQL := 'DROP TABLE ' || V_LOG_TABLE;
			EXECUTE IMMEDIATE V_SQL;
		
		END IF;
	
		--EXECUTE IMMEDIATE 'DROP PACKAGE PKG_STL_INVOICE_MARK_INIT';
	
		RETURN TRUE;
	END;

	-----------------------------------------------------------------
	--执行初始化
	-----------------------------------------------------------------
	PROCEDURE PROC_EXECUTE IS
	
		V_RESULT     BOOLEAN;
		V_SQL        VARCHAR2(200);
		V_CUR_DATA   LOG_CURSOR;
		V_LOG_RECORD LOG_RECORD;
		V_HOUR       INT;
		V_INDEX_DATE DATE;
		V_END_DATE   DATE;
	BEGIN
	
		V_RESULT := FUNC_INITIALIZE();
	
		--预初始化
		IF V_RESULT THEN
		
			--打开游标
			V_SQL := 'SELECT * FROM ' || V_LOG_TABLE ||
							 ' WHERE IS_INIT = ''N'' ORDER BY TO_INIT_DATE DESC';
			OPEN V_CUR_DATA FOR V_SQL;
		
			LOOP
			
				FETCH V_CUR_DATA
					INTO V_LOG_RECORD;
			
				EXIT WHEN V_CUR_DATA%NOTFOUND;
			
				--1小时提交一次
				V_INDEX_DATE := V_LOG_RECORD.TO_INIT_DATE;
				V_END_DATE   := V_LOG_RECORD.TO_INIT_DATE + 1;
				WHILE V_INDEX_DATE < V_END_DATE LOOP
				
					--1)更新应收单
					UPDATE STL.T_STL_BILL_RECEIVABLE T
						 SET T.INVOICE_MARK = 'INVOICE_TYPE_02'
					 WHERE T.ACCOUNT_DATE >= V_INDEX_DATE
								 AND T.ACCOUNT_DATE < V_INDEX_DATE + 1 / 24
								 AND T.INVOICE_MARK IS NULL;
				
					--2)更新应付单
					UPDATE STL.T_STL_BILL_PAYABLE T
						 SET T.INVOICE_MARK = 'INVOICE_TYPE_02'
					 WHERE T.ACCOUNT_DATE >= V_INDEX_DATE
								 AND T.ACCOUNT_DATE < V_INDEX_DATE + 1 / 24
								 AND T.INVOICE_MARK IS NULL;
				
					--3)现金收款单
					UPDATE STL.T_STL_BILL_CASH_COLLECTION T
						 SET T.INVOICE_MARK = 'INVOICE_TYPE_02'
					 WHERE T.ACCOUNT_DATE >= V_INDEX_DATE
								 AND T.ACCOUNT_DATE < V_INDEX_DATE + 1 / 24
								 AND T.INVOICE_MARK IS NULL;
				
					--4)预收单
					UPDATE STL.T_STL_BILL_DEPOSIT_RECEIVED T
						 SET T.INVOICE_MARK = 'INVOICE_TYPE_02'
					 WHERE T.ACCOUNT_DATE >= V_INDEX_DATE
								 AND T.ACCOUNT_DATE < V_INDEX_DATE + 1 / 24
								 AND T.INVOICE_MARK IS NULL;
				
					--5)预付单
					UPDATE STL.T_STL_BILL_ADVANCED_PAYMENT T
						 SET T.INVOICE_MARK = 'INVOICE_TYPE_02'
					 WHERE T.ACCOUNT_DATE >= V_INDEX_DATE
								 AND T.ACCOUNT_DATE < V_INDEX_DATE + 1 / 24
								 AND T.INVOICE_MARK IS NULL;
				
					--6)小票
					UPDATE STL.T_STL_OTHER_REVENUE T
						 SET T.INVOICE_MARK = 'INVOICE_TYPE_02'
					 WHERE T.CREATE_TIME >= V_INDEX_DATE
								 AND T.CREATE_TIME < V_INDEX_DATE + 1 / 24
								 AND T.INVOICE_MARK IS NULL;
				
					COMMIT;
				
					V_INDEX_DATE := V_INDEX_DATE + 1 / 24;
				
				END LOOP;
			
				--更新日志记录
				EXECUTE IMMEDIATE 'UPDATE ' || V_LOG_TABLE ||
													' SET IS_INIT = :1,INIT_TIME = :2 WHERE ID = :3'
					USING 'Y', SYSDATE, V_LOG_RECORD.ID;
			
				COMMIT;
			
				SELECT TO_NUMBER(TO_CHAR(SYSDATE, 'HH24')) INTO V_HOUR FROM DUAL;
			
				--如果不在规定时间内执行，则退出
				IF V_HOUR < V_EXECUTE_HOUR_BEGIN OR V_HOUR >= V_EXECUTE_HOUR_END THEN
					EXIT;
				END IF;
			
			END LOOP;
		
		END IF;
	
	END;

END PKG_STL_INVOICE_MARK_INIT;
/
