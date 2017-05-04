--ɾ��֮ǰ����ͼ
drop MATERIALIZED VIEW stv.MV_STV_CUSTOMER;
--������ͼ
CREATE MATERIALIZED VIEW stv.MV_STV_CUSTOMER
REFRESH FORCE ON DEMAND
START WITH TO_DATE('20-06-2013', 'DD-MM-YYYY') NEXT TRUNC(SYSDATE) +  1 
AS
--�̶��ͻ���Ϣ  select CODE,NAME,create_time,CUSTOMER_TYPE
SELECT CODE, NAME, create_time,CUSTOMER_TYPE, cust_level
  from (SELECT CODE,
               NAME,
               create_time,
               'LC' AS CUSTOMER_TYPE,
               '1' as cust_level,
               row_number() over(partition by CODE order by create_time desc) as rn
          FROM BSE.T_BAS_CUSTOMER c)
 where rn = 1


--������Ϣ
UNION ALL
select CODE, NAME, create_time, CUSTOMER_TYPE, cust_level
  from (SELECT AGENT_COMPANY_CODE AS CODE,
               AGENT_COMPANY_NAME AS NAME,
               create_time,
               --ƫ�ߡ����ˡ�����
               CASE
                 WHEN AGENT_COMPANY_SORT = 'PX' THEN
                  'PA'
                 WHEN AGENT_COMPANY_SORT = 'KY' THEN
                  'AA'
                 ELSE
                  'A'
               END AS CUSTOMER_TYPE,
                            '2' as cust_level,
               row_number() over(partition by p.AGENT_COMPANY_CODE order by p.create_time desc) as rn
        
          FROM BSE.T_BAS_BUSINESS_PARTNER p
          where AGENT_COMPANY_SORT in ('PX','KY'))
 where RN = 1
 
 UNION ALL
--ɢ����Ϣ ɢ����Ϣû��ʱ�佨ģ
--��Ҫȥ���ظ�����
select CODE, NAME, create_time, CUSTOMER_TYPE, cust_level
  from (SELECT CUSTCODE AS CODE,
               CUSTNAME AS NAME,
               create_time,
               'LC' AS CUSTOMER_TYPE,
                '3' as cust_level,
               row_number() over(partition by CUSTCODE order by create_time desc) as rn
          FROM BSE.T_BAS_NONFIXED_CUSTOMER C
        --�����ڹ̶��ͻ���Ϣ
         where not exists
         (select 1 from BSE.T_BAS_CUSTOMER c1 where c1.code = c.CUSTCODE)
         --Ҳ�����ڴ�����Ϣ
         and not exists 
          (select 1 from BSE.T_BAS_BUSINESS_PARTNER c2 where c2.agent_company_code = c.CUSTCODE))
 where rn = 1;
 
 --��������
 create Index idx1_MV_STV_CUSTOMER on stv.MV_STV_CUSTOMER (code);
