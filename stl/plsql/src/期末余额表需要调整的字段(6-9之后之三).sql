-- Add/modify columns 
alter table stv.T_STL_BALANCE_BATCH add create_time timestamp;
alter table stv.T_STL_BALANCE_BATCH add modify_time timestamp;
-- Add comments to the columns 
comment on column stv.T_STL_BALANCE_BATCH.create_time
  is '����ʱ��';
comment on column stv.T_STL_BALANCE_BATCH.modify_time
  is '�޸�ʱ��';
  
-- Add/modify columns 
alter table stv.T_STL_BALANCE_BATCH add begin_time timestamp;
alter table stv.T_STL_BALANCE_BATCH add end_time timestamp;
-- Add comments to the columns 
comment on column stv.T_STL_BALANCE_BATCH.begin_time
  is '��ʼ����ʱ��';
comment on column stv.T_STL_BALANCE_BATCH.end_time
  is '������ʱ��'; 
