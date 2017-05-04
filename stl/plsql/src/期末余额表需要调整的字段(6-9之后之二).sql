-- Create table
create table t_stl_balance_Detail_INIT_tmp
(
  id                 VARCHAR2(50) not null,
  source_bill_no     VARCHAR2(50),
  source_bill_type   VARCHAR2(20),
  check_out_org_code VARCHAR2(50),
  stl_type           VARCHAR2(20),
  product_code       VARCHAR2(20) not null,
  customer_code      VARCHAR2(50),
  customer_name      VARCHAR2(200),
  check_out_period   VARCHAR2(50) not null,
  begin_amount       NUMBER(18),
  produce_amount     NUMBER(18),
  writeoff_amount    NUMBER(18),
  end_amount         NUMBER(18),
  balance_time       TIMESTAMP(6) not null,
  create_time        TIMESTAMP(6),
  modify_time        TIMESTAMP(6),
  active             CHAR(1) default 'Y' not null,
  currency_code      VARCHAR2(20),
  bill_type          VARCHAR2(20),
  business_date      TIMESTAMP(6),
  payment_type       VARCHAR2(20),
  aging_days         NUMBER(20)
);
-- Add comments to the table 
comment on table t_stl_balance_Detail_INIT_tmp
  is 'ÿ�½����ϸ';
-- Add comments to the columns 
comment on column t_stl_balance_Detail_INIT_tmp.id
  is 'ID';
comment on column t_stl_balance_Detail_INIT_tmp.source_bill_no
  is '��Դ����';
comment on column t_stl_balance_Detail_INIT_tmp.source_bill_type
  is '��Դ��������';
comment on column t_stl_balance_Detail_INIT_tmp.check_out_org_code
  is '���˲��ű���';
comment on column t_stl_balance_Detail_INIT_tmp.stl_type
  is '��������';
comment on column t_stl_balance_Detail_INIT_tmp.product_code
  is '��Ʒ����';
comment on column t_stl_balance_Detail_INIT_tmp.customer_code
  is '�ͻ�����';
comment on column t_stl_balance_Detail_INIT_tmp.customer_name
  is '�ͻ�����';
comment on column t_stl_balance_Detail_INIT_tmp.check_out_period
  is '�����ڼ�';
comment on column t_stl_balance_Detail_INIT_tmp.begin_amount
  is '�ڳ����';
comment on column t_stl_balance_Detail_INIT_tmp.produce_amount
  is '�������';
comment on column t_stl_balance_Detail_INIT_tmp.writeoff_amount
  is '�������';
comment on column t_stl_balance_Detail_INIT_tmp.end_amount
  is '��ĩ���';
comment on column t_stl_balance_Detail_INIT_tmp.balance_time
  is '����ʱ��';
comment on column t_stl_balance_Detail_INIT_tmp.create_time
  is '����ʱ��';
comment on column t_stl_balance_Detail_INIT_tmp.modify_time
  is '�޸�ʱ��';
comment on column t_stl_balance_Detail_INIT_tmp.active
  is '�Ƿ���Ч';
comment on column t_stl_balance_Detail_INIT_tmp.currency_code
  is '����';
comment on column t_stl_balance_Detail_INIT_tmp.bill_type
  is '����������';
comment on column t_stl_balance_Detail_INIT_tmp.business_date
  is 'ҵ������';
comment on column t_stl_balance_Detail_INIT_tmp.payment_type
  is '���ʽ';
comment on column t_stl_balance_Detail_INIT_tmp.aging_days
  is '��������' ;
