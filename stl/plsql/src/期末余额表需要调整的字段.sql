

-- 每日明细（用户生成每日汇总）
alter table stv.T_STL_DAILY_BALANCE_DETAIL drop column balance_id;
alter table stv.T_STL_DAILY_BALANCE_DETAIL drop column check_out_com_code;
alter table stv.T_STL_DAILY_BALANCE_DETAIL drop column check_out_com_name;
alter table stv.T_STL_DAILY_BALANCE_DETAIL drop column check_out_org_name;
alter table stv.T_STL_DAILY_BALANCE_DETAIL drop column customer_name;
alter table stv.T_STL_DAILY_BALANCE_DETAIL drop column total_produce_amount;
alter table stv.T_STL_DAILY_BALANCE_DETAIL drop column total_write_off_amount;
alter table stv.T_STL_DAILY_BALANCE_DETAIL modify balance_time timestamp;
alter table stv.T_STL_DAILY_BALANCE_DETAIL modify create_time timestamp;
alter table stv.T_STL_DAILY_BALANCE_DETAIL modify modify_time timestamp;
alter table stv.T_STL_DAILY_BALANCE_DETAIL add business_date timestamp; 
alter table stv.T_STL_DAILY_BALANCE_DETAIL add payment_type varchar2(20);
comment on column stv.T_STL_DAILY_BALANCE_DETAIL.business_date
  is '业务日期';
comment on column stv.T_STL_DAILY_BALANCE_DETAIL.payment_type
  is '付款方式';


--每日明细汇总（用于生成每月明细）
alter table stv.T_STL_DAILY_BALANCE drop column autper_id;
alter table stv.T_STL_DAILY_BALANCE drop column check_out_com_code;
alter table stv.T_STL_DAILY_BALANCE drop column check_out_com_name;
alter table stv.T_STL_DAILY_BALANCE drop column check_out_org_name;
alter table stv.T_STL_DAILY_BALANCE drop column customer_name;
alter table stv.T_STL_DAILY_BALANCE drop column total_produce_amount;
alter table stv.T_STL_DAILY_BALANCE drop column total_write_off_amount;
alter table stv.T_STL_DAILY_BALANCE modify balance_time timestamp;
alter table stv.T_STL_DAILY_BALANCE modify create_time timestamp;
alter table stv.T_STL_DAILY_BALANCE modify modify_time timestamp;
alter table stv.T_STL_DAILY_BALANCE add business_date timestamp; 
alter table stv.T_STL_DAILY_BALANCE add payment_type varchar2(20);
comment on column stv.T_STL_DAILY_BALANCE.business_date
  is '业务日期';
comment on column stv.T_STL_DAILY_BALANCE.payment_type
  is '付款方式';
  
   
--每月明细（用户生成每月汇总）
alter table stv.T_STL_BALANCE_DETAIL drop column balance_id; 
alter table stv.T_STL_BALANCE_DETAIL drop column check_out_com_code;
alter table stv.T_STL_BALANCE_DETAIL drop column check_out_com_name;
alter table stv.T_STL_BALANCE_DETAIL drop column check_out_org_name;
alter table stv.T_STL_BALANCE_DETAIL drop column total_produce_amount;
alter table stv.T_STL_BALANCE_DETAIL drop column total_writeoff_amount; 
alter table stv.T_STL_BALANCE_DETAIL modify balance_time timestamp;
alter table stv.T_STL_BALANCE_DETAIL modify create_time timestamp;
alter table stv.T_STL_BALANCE_DETAIL modify modify_time timestamp;
alter table T_STL_BALANCE_DETAIL modify source_bill_no null;
alter table T_STL_BALANCE_DETAIL modify source_bill_type null;
alter table stv.T_STL_BALANCE_DETAIL add business_date timestamp; 
alter table stv.T_STL_BALANCE_DETAIL add payment_type varchar2(20);
comment on column stv.T_STL_BALANCE_DETAIL.business_date  is '业务日期';
comment on column stv.T_STL_BALANCE_DETAIL.payment_type
  is '付款方式'; 
alter table  stv.T_STL_BALANCE_DETAIL add aging_days number(20); 
comment on column  stv.T_STL_BALANCE_DETAIL.aging_days
  is '账龄天数'; 
  
--每月汇总 
alter table stv.T_STL_BALANCE drop column autper_id; 
alter table stv.T_STL_BALANCE modify balance_time timestamp;
alter table stv.T_STL_BALANCE modify create_time timestamp;
alter table stv.T_STL_BALANCE modify modify_time timestamp;
alter table stv.T_STL_BALANCE add business_date timestamp;
alter table stv.T_STL_BALANCE add payment_type varchar2(20); 
comment on column stv.T_STL_BALANCE.business_date
  is '业务日期';
comment on column stv.T_STL_BALANCE.payment_type
  is '付款方式'; 
alter table stv.T_STL_BALANCE add aging_days number(20); 
comment on column stv.T_STL_BALANCE.aging_days
  is '账龄';
