PL/SQL Developer Test script 3.0
13
-- Created on 2013-02-21 by ZHUWEI 
DECLARE
	-- Local variables here
	V_RESULT BOOLEAN;
BEGIN
--  EXECUTE IMMEDIATE 'create table test ( a varchar2(10))';
  -- Test statements here
  V_RESULT := PKG_STL_VCH_DAILY.FUNC_VOUCHER_DAILY_POD('20130202',
                                                       TO_DATE('20130201',
                                                               'yyyymmdd'),
                                                       TO_DATE('20130202',
                                                               'yyyymmdd'));
END;
0
0
