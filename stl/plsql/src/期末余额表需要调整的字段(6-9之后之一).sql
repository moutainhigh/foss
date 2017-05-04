alter table t_stl_daily_balance_detail rename to t_stl_daily_balance_tmp;

-- Create table
create table t_stl_balance_Detail_INIT
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
comment on table t_stl_balance_Detail_INIT
  is 'ÿ�½����ϸ';
-- Add comments to the columns 
comment on column t_stl_balance_Detail_INIT.id
  is 'ID';
comment on column t_stl_balance_Detail_INIT.source_bill_no
  is '��Դ����';
comment on column t_stl_balance_Detail_INIT.source_bill_type
  is '��Դ��������';
comment on column t_stl_balance_Detail_INIT.check_out_org_code
  is '���˲��ű���';
comment on column t_stl_balance_Detail_INIT.stl_type
  is '��������';
comment on column t_stl_balance_Detail_INIT.product_code
  is '��Ʒ����';
comment on column t_stl_balance_Detail_INIT.customer_code
  is '�ͻ�����';
comment on column t_stl_balance_Detail_INIT.customer_name
  is '�ͻ�����';
comment on column t_stl_balance_Detail_INIT.check_out_period
  is '�����ڼ�';
comment on column t_stl_balance_Detail_INIT.begin_amount
  is '�ڳ����';
comment on column t_stl_balance_Detail_INIT.produce_amount
  is '�������';
comment on column t_stl_balance_Detail_INIT.writeoff_amount
  is '�������';
comment on column t_stl_balance_Detail_INIT.end_amount
  is '��ĩ���';
comment on column t_stl_balance_Detail_INIT.balance_time
  is '����ʱ��';
comment on column t_stl_balance_Detail_INIT.create_time
  is '����ʱ��';
comment on column t_stl_balance_Detail_INIT.modify_time
  is '�޸�ʱ��';
comment on column t_stl_balance_Detail_INIT.active
  is '�Ƿ���Ч';
comment on column t_stl_balance_Detail_INIT.currency_code
  is '����';
comment on column t_stl_balance_Detail_INIT.bill_type
  is '����������';
comment on column t_stl_balance_Detail_INIT.business_date
  is 'ҵ������';
comment on column t_stl_balance_Detail_INIT.payment_type
  is '���ʽ';
comment on column t_stl_balance_Detail_INIT.aging_days
  is '��������' ;
