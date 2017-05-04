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
  is '每月结存明细';
-- Add comments to the columns 
comment on column t_stl_balance_Detail_INIT.id
  is 'ID';
comment on column t_stl_balance_Detail_INIT.source_bill_no
  is '来源单号';
comment on column t_stl_balance_Detail_INIT.source_bill_type
  is '来源单据类型';
comment on column t_stl_balance_Detail_INIT.check_out_org_code
  is '结账部门编码';
comment on column t_stl_balance_Detail_INIT.stl_type
  is '结算类型';
comment on column t_stl_balance_Detail_INIT.product_code
  is '产品类型';
comment on column t_stl_balance_Detail_INIT.customer_code
  is '客户编码';
comment on column t_stl_balance_Detail_INIT.customer_name
  is '客户名称';
comment on column t_stl_balance_Detail_INIT.check_out_period
  is '结账期间';
comment on column t_stl_balance_Detail_INIT.begin_amount
  is '期初金额';
comment on column t_stl_balance_Detail_INIT.produce_amount
  is '发生金额';
comment on column t_stl_balance_Detail_INIT.writeoff_amount
  is '核销金额';
comment on column t_stl_balance_Detail_INIT.end_amount
  is '期末余额';
comment on column t_stl_balance_Detail_INIT.balance_time
  is '结账时间';
comment on column t_stl_balance_Detail_INIT.create_time
  is '创建时间';
comment on column t_stl_balance_Detail_INIT.modify_time
  is '修改时间';
comment on column t_stl_balance_Detail_INIT.active
  is '是否有效';
comment on column t_stl_balance_Detail_INIT.currency_code
  is '币种';
comment on column t_stl_balance_Detail_INIT.bill_type
  is '单据子类型';
comment on column t_stl_balance_Detail_INIT.business_date
  is '业务日期';
comment on column t_stl_balance_Detail_INIT.payment_type
  is '付款方式';
comment on column t_stl_balance_Detail_INIT.aging_days
  is '账龄天数' ;
