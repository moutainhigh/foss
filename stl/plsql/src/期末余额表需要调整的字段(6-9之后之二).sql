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
  is '每月结存明细';
-- Add comments to the columns 
comment on column t_stl_balance_Detail_INIT_tmp.id
  is 'ID';
comment on column t_stl_balance_Detail_INIT_tmp.source_bill_no
  is '来源单号';
comment on column t_stl_balance_Detail_INIT_tmp.source_bill_type
  is '来源单据类型';
comment on column t_stl_balance_Detail_INIT_tmp.check_out_org_code
  is '结账部门编码';
comment on column t_stl_balance_Detail_INIT_tmp.stl_type
  is '结算类型';
comment on column t_stl_balance_Detail_INIT_tmp.product_code
  is '产品类型';
comment on column t_stl_balance_Detail_INIT_tmp.customer_code
  is '客户编码';
comment on column t_stl_balance_Detail_INIT_tmp.customer_name
  is '客户名称';
comment on column t_stl_balance_Detail_INIT_tmp.check_out_period
  is '结账期间';
comment on column t_stl_balance_Detail_INIT_tmp.begin_amount
  is '期初金额';
comment on column t_stl_balance_Detail_INIT_tmp.produce_amount
  is '发生金额';
comment on column t_stl_balance_Detail_INIT_tmp.writeoff_amount
  is '核销金额';
comment on column t_stl_balance_Detail_INIT_tmp.end_amount
  is '期末余额';
comment on column t_stl_balance_Detail_INIT_tmp.balance_time
  is '结账时间';
comment on column t_stl_balance_Detail_INIT_tmp.create_time
  is '创建时间';
comment on column t_stl_balance_Detail_INIT_tmp.modify_time
  is '修改时间';
comment on column t_stl_balance_Detail_INIT_tmp.active
  is '是否有效';
comment on column t_stl_balance_Detail_INIT_tmp.currency_code
  is '币种';
comment on column t_stl_balance_Detail_INIT_tmp.bill_type
  is '单据子类型';
comment on column t_stl_balance_Detail_INIT_tmp.business_date
  is '业务日期';
comment on column t_stl_balance_Detail_INIT_tmp.payment_type
  is '付款方式';
comment on column t_stl_balance_Detail_INIT_tmp.aging_days
  is '账龄天数' ;
