--关联语句
create index STL.IDX3_STL_PAYABLE on STL.T_STL_BILL_PAYABLE (is_init, payable_org_code);

-- Create/Recreate indexes 
create index STL.idx_stl_bill_writeoff on STL.T_STL_BILL_WRITEOFF (org_code, begin_no, is_init);

-- Create/Recreate indexes 
create index idx1_stl_balance_init_tmp on T_STL_BALANCE_DETAIL_INIT_TMP (stl_type, check_out_org_code);

-- Create/Recreate indexes 
create index idx1_stl_balance_detail_init on T_STL_BALANCE_DETAIL_INIT (stl_type, check_out_org_code);

-- Create/Recreate indexes 
create index STL.idx8_stl_bill_DEPOSIT_RECEIVED on STL.T_STL_BILL_DEPOSIT_RECEIVED (generating_org_code, is_init);

-- Create/Recreate indexes 
create index idx2_stl_balance_batch on T_STL_BALANCE_BATCH (org_code, active, business_date);

create index STL.IDX11_STL_BILL_RECEIVABLE on STL.T_STL_BILL_RECEIVABLE (RECEIVABLE_ORG_CODE, IS_INIT);

-- Create/Recreate indexes 
create index STL.idx3_stl_pod on STL.T_STL_POD (pod_type);

--新加 12:00
create index idx_mv_stv_customer on STV.mv_stv_customer(CODE);
create index idx_stl_balance_detail on T_STL_BALANCE_DETAIL (balance_time);
create index IDX3_STL_BALANCE on T_STL_BALANCE (BALANCE_TIME);
-- Create/Recreate indexes 
create index idx3_stl_daily_balance_tmp on T_STL_DAILY_BALANCE_TMP (balance_time);
-- Create/Recreate indexes 
create index idx2_stl_daily_balance on T_STL_DAILY_BALANCE (balance_time);



