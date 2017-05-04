PL/SQL Developer Test script 3.0
13
-- Created on 2013-03-18 by ZHUWEI 
declare 
  -- Local variables here
  ROW_T_BAS_ORG BSE.T_BAS_ORG%ROWTYPE;
  i integer;
begin
  -- Test statements here
  ROW_T_BAS_ORG := pkg_stl_common.FUNC_GET_ORG_INFO('W31000304010513');
  ROW_T_BAS_ORG := pkg_stl_common.FUNC_GET_ORG_INFO('W31000304010513');
  
  DBMS_OUTPUT.put_line(ROW_T_BAS_ORG.Unified_Code || ROW_T_BAS_ORG.Name);
  
end;
0
0
