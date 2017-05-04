-- Add/modify columns 
alter table stv.T_STL_BALANCE_BATCH add create_time timestamp;
alter table stv.T_STL_BALANCE_BATCH add modify_time timestamp;
-- Add comments to the columns 
comment on column stv.T_STL_BALANCE_BATCH.create_time
  is '创建时间';
comment on column stv.T_STL_BALANCE_BATCH.modify_time
  is '修改时间';
  
-- Add/modify columns 
alter table stv.T_STL_BALANCE_BATCH add begin_time timestamp;
alter table stv.T_STL_BALANCE_BATCH add end_time timestamp;
-- Add comments to the columns 
comment on column stv.T_STL_BALANCE_BATCH.begin_time
  is '开始结账时间';
comment on column stv.T_STL_BALANCE_BATCH.end_time
  is '完结结账时间'; 
