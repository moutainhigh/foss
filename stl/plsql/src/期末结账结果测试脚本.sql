--统计每月期末余额
select stl_type,
       sum(bal.begin_amount) as begin_amount,
       sum(bal.produce_amount) as produce_amount,
       sum(bal.writeoff_amount) as writeoff_amount,
       sum(bal.end_amount) as end_amount
  from stv.t_stl_balance bal
 where bal.check_out_period = '201303'
 group by bal.stl_type;

--统计每月明细期末汇总 
select stl_type,
       sum(bal.begin_amount) as begin_amount,
       sum(bal.produce_amount) as produce_amount,
       sum(bal.writeoff_amount) as writeoff_amount,
       sum(bal.end_amount) as end_amount
  from stv.t_stl_balance_detail bal
 where bal.check_out_period = '201303'
 group by bal.stl_type;

--统计每日余额
select stl_type,
       sum(bal.begin_amount) as begin_amount,
       sum(bal.produce_amount) as produce_amount,
       sum(bal.writeoff_amount) as writeoff_amount,
       sum(bal.end_amount) as end_amount
  from stv.t_stl_daily_balance bal
 where bal.check_out_period = '201303'
 group by bal.stl_type;

--统计每日明细余额
select stl_type,
       sum(bal.begin_amount) as begin_amount,
       sum(bal.produce_amount) as produce_amount,
       sum(bal.writeoff_amount) as writeoff_amount,
       sum(bal.end_amount) as end_amount
  from stv.t_stl_daily_balance_detail bal
 where bal.check_out_period = '201303'
 group by bal.stl_type;
 
--统计应收发生（已经确认收入）
select sum(amount) as amount from stl.t_stl_bill_receivable bill
where bill.conreven_date > bill.account_date
and bill.conreven_date >= to_date('2013-3-1','yyyy-mm-dd')
and bill.conreven_date < to_date('2013-4-1','yyyy-mm-dd');

--统计应收核销

--统计应收发生（未确认收入）
select sum(amount) as amount from stl.t_stl_bill_receivable bill
where (bill.conreven_date < bill.account_date or bill.conreven_date is null)
and bill.account_date >= to_date('2013-3-1','yyyy-mm-dd')
and bill.account_date < to_date('2013-4-1','yyyy-mm-dd');

--统计应付发生
select sum(amount) as amount from stl.t_stl_bill_payable bill
where  bill.account_date >= to_date('2013-3-1','yyyy-mm-dd')
and bill.account_date < to_date('2013-4-1','yyyy-mm-dd');

--统计预收发生
select sum(amount) as amount from stl.t_stl_bill_deposit_received bill
where  bill.account_date >= to_date('2013-3-1','yyyy-mm-dd')
and bill.account_date < to_date('2013-4-1','yyyy-mm-dd');

