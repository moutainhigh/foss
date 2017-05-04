CREATE OR REPLACE PACKAGE PKG_STL_INVOICE_MARK_INIT IS

	-- ==============================================================
	-- Author  : ZBW
	-- Created : 2013-11-18
	-- Purpose : �������������ݷ�Ʊ����ֶγ�ʼ��
	-- ==============================================================

	-----------------------------------------------------------------
	-- �������Ƿ����
	-----------------------------------------------------------------
	FUNCTION FUNC_TABLE_EXIST(P_TABLE_NAME VARCHAR2) RETURN BOOLEAN;

	-----------------------------------------------------------------
	-- ��ʼ��
	-----------------------------------------------------------------
	FUNCTION FUNC_INITIALIZE RETURN BOOLEAN; -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	-- ����
	-----------------------------------------------------------------
	FUNCTION FUNC_DESTROY RETURN BOOLEAN;

	-----------------------------------------------------------------
	-- ִ�г�ʼ��
	-----------------------------------------------------------------
	PROCEDURE PROC_EXECUTE;

	TYPE LOG_RECORD IS RECORD(
		ID           VARCHAR2(50),
		TO_INIT_DATE DATE,
		IS_INIT      CHAR(1),
		INIT_TIME    TIMESTAMP);

	TYPE LOG_CURSOR IS REF CURSOR;

	--��¼��ʼ����־
	V_LOG_TABLE VARCHAR2(50) := 'T_STV_INVOICE_MARK_INIT_LOG';

	--��ʼ����ʼʱ��ͽ���ʱ��
	V_END_DATE   DATE := TO_DATE('2014-01-01', 'YYYY-MM-DD');
	V_START_DATE DATE := TO_DATE('2010-01-01', 'YYYY-MM-DD');

	--ִ��ʱ����4�㵽6��
	V_EXECUTE_HOUR_BEGIN INT := 2;
	V_EXECUTE_HOUR_END   INT := 6;

END PKG_STL_INVOICE_MARK_INIT;
/
CREATE OR REPLACE PACKAGE BODY PKG_STL_INVOICE_MARK_INIT IS

	-- ==============================================================
	-- Author  : ZBW
	-- Created : 2013-11-18
	-- Purpose : �������������ݷ�Ʊ����ֶγ�ʼ��
	-- ==============================================================

	-----------------------------------------------------------------
	-- �������Ƿ����
	-----------------------------------------------------------------
	FUNCTION FUNC_TABLE_EXIST(P_TABLE_NAME VARCHAR2) RETURN BOOLEAN IS
		V_RESULT BOOLEAN := FALSE;
		V_ROWS   INT;
	BEGIN
	
		V_ROWS := 0;
	
		--��ѯ�Ƿ������־��
		SELECT COUNT(1)
			INTO V_ROWS
			FROM USER_TABLES T
		 WHERE T.TABLE_NAME = P_TABLE_NAME;
	
		--������ڣ�����TRUE
		IF V_ROWS = 1 THEN
			V_RESULT := TRUE;
		END IF;
	
		RETURN V_RESULT;
	
	END;

	-----------------------------------------------------------------
	-- ��ʼ��
	-----------------------------------------------------------------
	FUNCTION FUNC_INITIALIZE RETURN BOOLEAN IS
		V_ROWS  INT;
		V_SQL   VARCHAR2(200);
		V_DAY   DATE := V_END_DATE - 1;
		V_EXIST BOOLEAN;
	BEGIN
	
		V_EXIST := FUNC_TABLE_EXIST(V_LOG_TABLE);
	
		--��������ڣ��򴴽���־��
		IF NOT V_EXIST THEN
		
			V_SQL := 'CREATE TABLE ' || V_LOG_TABLE ||
							 '(ID VARCHAR2(50) PRIMARY KEY,TO_INIT_DATE DATE,IS_INIT CHAR(1),INIT_TIME TIMESTAMP)';
		
			EXECUTE IMMEDIATE V_SQL;
		
			V_SQL  := 'INSERT INTO ' || V_LOG_TABLE ||
								'(ID,TO_INIT_DATE,IS_INIT) VALUES(SYS_GUID(),:TO_INIT_DATE,''N'')';
			V_ROWS := 0;
		
			--��ʼ������
			WHILE V_DAY >= V_START_DATE LOOP
			
				EXECUTE IMMEDIATE V_SQL
					USING V_DAY;
			
				V_DAY  := V_DAY - 1;
				V_ROWS := V_ROWS + 1;
			
				--100���ύһ��
				IF MOD(V_ROWS, 100) = 0 THEN
					COMMIT;
					V_ROWS := 0;
				END IF;
			END LOOP;
		
			COMMIT;
		
		END IF;
	
		RETURN TRUE;
	
	END; -- ���ؽ�� true:�ɹ���false:ʧ��

	-----------------------------------------------------------------
	--����
	-----------------------------------------------------------------
	FUNCTION FUNC_DESTROY RETURN BOOLEAN IS
		V_SQL   VARCHAR2(200);
		V_EXIST BOOLEAN;
	BEGIN
	
		V_EXIST := FUNC_TABLE_EXIST(V_LOG_TABLE);
	
		--������ڣ���ɾ����
		IF V_EXIST THEN
		
			V_SQL := 'DROP TABLE ' || V_LOG_TABLE;
			EXECUTE IMMEDIATE V_SQL;
		
		END IF;
	
		--EXECUTE IMMEDIATE 'DROP PACKAGE PKG_STL_INVOICE_MARK_INIT';
	
		RETURN TRUE;
	END;

	-----------------------------------------------------------------
	--ִ�г�ʼ��
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
	
		--Ԥ��ʼ��
		IF V_RESULT THEN
		
			--���α�
			V_SQL := 'SELECT * FROM ' || V_LOG_TABLE ||
							 ' WHERE IS_INIT = ''N'' ORDER BY TO_INIT_DATE DESC';
			OPEN V_CUR_DATA FOR V_SQL;
		
			LOOP
			
				FETCH V_CUR_DATA
					INTO V_LOG_RECORD;
			
				EXIT WHEN V_CUR_DATA%NOTFOUND;
			
				--1Сʱ�ύһ��
				V_INDEX_DATE := V_LOG_RECORD.TO_INIT_DATE;
				V_END_DATE   := V_LOG_RECORD.TO_INIT_DATE + 1;
				WHILE V_INDEX_DATE < V_END_DATE LOOP
				
					--1)����Ӧ�յ�
					UPDATE STL.T_STL_BILL_RECEIVABLE T
						 SET T.INVOICE_MARK = 'INVOICE_TYPE_02'
					 WHERE T.ACCOUNT_DATE >= V_INDEX_DATE
								 AND T.ACCOUNT_DATE < V_INDEX_DATE + 1 / 24
								 AND T.INVOICE_MARK IS NULL;
				
					--2)����Ӧ����
					UPDATE STL.T_STL_BILL_PAYABLE T
						 SET T.INVOICE_MARK = 'INVOICE_TYPE_02'
					 WHERE T.ACCOUNT_DATE >= V_INDEX_DATE
								 AND T.ACCOUNT_DATE < V_INDEX_DATE + 1 / 24
								 AND T.INVOICE_MARK IS NULL;
				
					--3)�ֽ��տ
					UPDATE STL.T_STL_BILL_CASH_COLLECTION T
						 SET T.INVOICE_MARK = 'INVOICE_TYPE_02'
					 WHERE T.ACCOUNT_DATE >= V_INDEX_DATE
								 AND T.ACCOUNT_DATE < V_INDEX_DATE + 1 / 24
								 AND T.INVOICE_MARK IS NULL;
				
					--4)Ԥ�յ�
					UPDATE STL.T_STL_BILL_DEPOSIT_RECEIVED T
						 SET T.INVOICE_MARK = 'INVOICE_TYPE_02'
					 WHERE T.ACCOUNT_DATE >= V_INDEX_DATE
								 AND T.ACCOUNT_DATE < V_INDEX_DATE + 1 / 24
								 AND T.INVOICE_MARK IS NULL;
				
					--5)Ԥ����
					UPDATE STL.T_STL_BILL_ADVANCED_PAYMENT T
						 SET T.INVOICE_MARK = 'INVOICE_TYPE_02'
					 WHERE T.ACCOUNT_DATE >= V_INDEX_DATE
								 AND T.ACCOUNT_DATE < V_INDEX_DATE + 1 / 24
								 AND T.INVOICE_MARK IS NULL;
				
					--6)СƱ
					UPDATE STL.T_STL_OTHER_REVENUE T
						 SET T.INVOICE_MARK = 'INVOICE_TYPE_02'
					 WHERE T.CREATE_TIME >= V_INDEX_DATE
								 AND T.CREATE_TIME < V_INDEX_DATE + 1 / 24
								 AND T.INVOICE_MARK IS NULL;
				
					COMMIT;
				
					V_INDEX_DATE := V_INDEX_DATE + 1 / 24;
				
				END LOOP;
			
				--������־��¼
				EXECUTE IMMEDIATE 'UPDATE ' || V_LOG_TABLE ||
													' SET IS_INIT = :1,INIT_TIME = :2 WHERE ID = :3'
					USING 'Y', SYSDATE, V_LOG_RECORD.ID;
			
				COMMIT;
			
				SELECT TO_NUMBER(TO_CHAR(SYSDATE, 'HH24')) INTO V_HOUR FROM DUAL;
			
				--������ڹ涨ʱ����ִ�У����˳�
				IF V_HOUR < V_EXECUTE_HOUR_BEGIN OR V_HOUR >= V_EXECUTE_HOUR_END THEN
					EXIT;
				END IF;
			
			END LOOP;
		
		END IF;
	
	END;

END PKG_STL_INVOICE_MARK_INIT;
/
