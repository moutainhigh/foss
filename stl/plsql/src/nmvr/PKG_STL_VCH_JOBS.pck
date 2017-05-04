CREATE OR REPLACE PACKAGE STV.PKG_STL_VCH_JOBS IS

	-- ==============================================================
	-- Author  : ZHUWEI
	-- Created : 2012-12-07 14:29:42
	-- Purpose : ƾ֤�������
	-- ==============================================================

	-----------------------------------------------------------------
	-- ÿ��ƾ֤����
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_JOBS_DAILY(P_PERIOD VARCHAR2 -- ����yyyymmdd������20121221
																	 ) RETURN BOOLEAN;

	-----------------------------------------------------------------
	-- ÿ��ƾ֤����
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_JOBS_MONTHLY(P_PERIOD VARCHAR2 -- ����yyyymm������201212
																		 ) RETURN BOOLEAN;

	--  ÿ��ƾ֤
	PROCEDURE PROC_VOUCHER_JOBS;

END PKG_STL_VCH_JOBS;
/
CREATE OR REPLACE PACKAGE BODY STV.PKG_STL_VCH_JOBS IS

	-- ==============================================================
	-- Author  : ZHUWEI
	-- Created : 2012-12-07 14:29:42
	-- Purpose : ƾ֤�������
	-- ==============================================================

	-----------------------------------------------------------------
	-- ÿ��ƾ֤����
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_JOBS_DAILY(P_PERIOD VARCHAR2 -- ����yyyymmdd������20121221
																	 ) RETURN BOOLEAN IS
	
		V_RESULT     BOOLEAN;
		V_BEGIN_DATE DATE := TO_DATE(P_PERIOD, 'YYYYMMDD');
		V_END_DATE   DATE := V_BEGIN_DATE + 1;
	
	BEGIN
	
		V_RESULT := PKG_STL_VCH_DAILY.FUNC_VOUCHER_DAILY(P_PERIOD,
																										 V_BEGIN_DATE,
																										 V_END_DATE);
																										 
		-- �����ÿ��ƾ֤����                                                 
		V_RESULT := PKG_STL_VCH_DAILY_DHK.FUNC_VOUCHER_DAILY(P_PERIOD,
																										 V_BEGIN_DATE,
																										 V_END_DATE); 
																										 
		RETURN V_RESULT;
	
	EXCEPTION
		WHEN OTHERS THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				V_BEGIN_DATE,
																				V_END_DATE,
																				'PKG_STL_VCH_JOBS.FUNC_VOUCHER_JOBS_DAILY',
																				'ƾ֤����JOB����ϸ�����쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_JOBS_DAILY;

	-----------------------------------------------------------------
	-- ÿ��ƾ֤����
	-----------------------------------------------------------------
	FUNCTION FUNC_VOUCHER_JOBS_MONTHLY(P_PERIOD VARCHAR2 -- ����yyyymm������201212
																		 ) RETURN BOOLEAN IS
	
		CURSOR CUR_PERIOD IS
			SELECT T.ID, T.PERIOD, T.VOUCHER_BEGIN_TIME, T.VOUCHER_END_TIME
				FROM STV.T_STL_ACCOUNTING_PERIODS T
			 WHERE T.PERIOD = P_PERIOD
						 AND T.ACTIVE = PKG_STL_COMMON.ACTIVE;
	
		V_BEGIN_DATE DATE;
		V_END_DATE   DATE;
		V_RESULT     BOOLEAN := TRUE;
	
	BEGIN
	
		FOR V_CUR_ROW IN CUR_PERIOD LOOP
		
			V_BEGIN_DATE := V_CUR_ROW.VOUCHER_BEGIN_TIME;
			V_END_DATE   := V_CUR_ROW.VOUCHER_END_TIME;
		
			--ͳ�ƻ���
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_MONTHLY(V_BEGIN_DATE,
																														 V_END_DATE);
			END IF;
		
			--01��ͨʼ���±���
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFONO(P_PERIOD,
																														V_BEGIN_DATE,
																														V_END_DATE);
			END IF;
		
			--01����ʼ���±���
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFOSO(P_PERIOD,
																														V_BEGIN_DATE,
																														V_END_DATE);
			END IF;
		
			--02��ͨʼ���±���
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFONT(P_PERIOD,
																														V_BEGIN_DATE,
																														V_END_DATE);
			END IF;
		
			--02����ʼ���±���
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFOST(P_PERIOD,
																														V_BEGIN_DATE,
																														V_END_DATE);
			END IF;
		
			--01�����±���
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFDO(P_PERIOD,
																													 V_BEGIN_DATE,
																													 V_END_DATE);
			END IF;
		
			--02�����±���
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFDT(P_PERIOD,
																													 V_BEGIN_DATE,
																													 V_END_DATE);
			END IF;
		
			--ƫ���±���
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NPLR(P_PERIOD,
																													V_BEGIN_DATE,
																													V_END_DATE);
			END IF;
		
			--�����±���
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NAFR(P_PERIOD,
																													V_BEGIN_DATE,
																													V_END_DATE);
			END IF;
		
			--ʼ��ר�������±���
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NRFI(P_PERIOD,
																													V_BEGIN_DATE,
																													V_END_DATE);
			END IF;
		
			--ʼ��ƫ�������±���
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NPLI(P_PERIOD,
																													V_BEGIN_DATE,
																													V_END_DATE);
			END IF;
		
			--ʼ�����������±���
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_NAFI(P_PERIOD,
																													V_BEGIN_DATE,
																													V_END_DATE);
			END IF;
		
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_RETURN_COD(P_PERIOD,
																																V_BEGIN_DATE,
																																V_END_DATE);
			END IF;
		
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_LDD(P_PERIOD,
																												 V_BEGIN_DATE,
																												 V_END_DATE);
			END IF;
		
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_LDI(P_PERIOD,
																												 V_BEGIN_DATE,
																												 V_END_DATE);
			END IF;
		
			IF V_RESULT THEN
				V_RESULT := PKG_STL_VCH_MONTHLY.FUNC_VOUCHER_LWO(P_PERIOD,
																												 V_BEGIN_DATE,
																												 V_END_DATE);
			END IF;
		
			
			-- �����ÿ��ƾ֤����
			V_RESULT := PKG_STL_VCH_DAILY_DHK.FUNC_VOUCHER_MONTHLY(P_PERIOD,
																										 V_BEGIN_DATE,
																										 V_END_DATE);
		
			IF V_RESULT THEN
				UPDATE STV.T_STL_ACCOUNTING_PERIODS T
					 SET T.ACTIVE = PKG_STL_COMMON.INACTIVE
				 WHERE T.ID = V_CUR_ROW.ID;
				COMMIT;
			END IF;
		
		END LOOP;
	
		RETURN V_RESULT;
	
	EXCEPTION
		WHEN OTHERS THEN
			--���������־��
			PKG_STL_COMMON.PROC_STL_ERROR_LOG(P_PERIOD,
																				V_BEGIN_DATE,
																				V_END_DATE,
																				'PKG_STL_VCH_JOBS.FUNC_VOUCHER_JOBS_MONTHLY',
																				'ƾ֤����JOB�±��������쳣:' || SQLERRM);
		
			--����ʧ�ܱ�־
			RETURN FALSE;
		
	END FUNC_VOUCHER_JOBS_MONTHLY;

	--  ÿ��ƾ֤
	PROCEDURE PROC_VOUCHER_JOBS IS
	
		V_PERIOD   VARCHAR2(50) := TO_CHAR(SYSDATE - 1, 'YYYYMMDD'); --�ձ����ڼ�
		V_END_DATE DATE := TRUNC(SYSDATE);
		V_COUNT    INT;
		V_RESULT   BOOLEAN;
	
	BEGIN
	
		V_RESULT := FUNC_VOUCHER_JOBS_DAILY(V_PERIOD);
	
		--�ձ������ɹ������Ƿ���Ҫ�����±���
		IF V_RESULT THEN
		
			SELECT COUNT(1)
				INTO V_COUNT
				FROM STV.T_STL_ACCOUNTING_PERIODS T
			 WHERE T.VOUCHER_END_TIME = V_END_DATE
						 AND T.ACTIVE = PKG_STL_COMMON.ACTIVE;
		
			IF V_COUNT > 0 THEN
			
				SELECT T.PERIOD
					INTO V_PERIOD
					FROM STV.T_STL_ACCOUNTING_PERIODS T
				 WHERE T.VOUCHER_END_TIME = V_END_DATE
							 AND T.ACTIVE = PKG_STL_COMMON.ACTIVE
							 AND ROWNUM <= 1;
			
				V_RESULT := FUNC_VOUCHER_JOBS_MONTHLY(V_PERIOD);
			
			END IF;
		
		END IF;
	
	END PROC_VOUCHER_JOBS;

END PKG_STL_VCH_JOBS;
/
