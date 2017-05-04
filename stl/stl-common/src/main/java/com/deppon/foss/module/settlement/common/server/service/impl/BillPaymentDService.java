package com.deppon.foss.module.settlement.common.server.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.module.settlement.common.api.server.dao.IBillPaymentDEntityDao;
import com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentDService;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentDEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;


/**
 * 付款单明细Service
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-3-17 下午3:07:02
 * @since
 * @version
 */
public class BillPaymentDService implements IBillPaymentDService{
	
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LogManager
			.getLogger(BillPaymentDService.class);
	/**
	 * 付款单dao
	 */
	private IBillPaymentDEntityDao billPaymentDEntityDao;

	/**
	 * 新增付款单明细
	 *  
	 * @author 099995-foss-wujiangtao
	 * @date 2013-3-17 下午3:07:20
	 * @param entity
	 */
	@Override
	public void addBillPaymentD(BillPaymentDEntity entity) {
		LOGGER.info(" addBillPaymentD start !");
	   if(entity==null
			   //付款单的记账日期
			   ||entity.getPaymentAccountDate()==null  
			   
			   //来源单号
			   ||entity.getSourceBillNo()==null 
			   
			   //来源单据类型
			   ||entity.getSourceBillType()==null 
			   
			   //来源单据的记账日期
			   ||entity.getSourceAccountDate()==null 
			   ){
		   throw new SettlementException("新增付款单明细参数不能为空！");
	   }
	   
	   //保存返回结果
	   int i=this.billPaymentDEntityDao.add(entity);
	   
	   //不等于1，说明未执行成功
	   if(i!=1){
		   throw new SettlementException("新增付款单明细失败！");
	   }
	   LOGGER.info(" addBillPaymentD end !");
	}


	/** 
	 * 根据付款单号查询付款单明细
	 * @author foss-qiaolifeng
	 * @date 2013-5-13 上午9:39:22
	 * @param paymentNo
	 * @return
	 * @see com.deppon.foss.module.settlement.common.api.server.service.IBillPaymentDService#queryPaymentDEntityListByPaymentNo(java.lang.String)
	 */
	@Override
	public List<BillPaymentDEntity> queryPaymentDEntityListByPaymentNo(
			String paymentNo) {
		// 根据付款单号查询付款单明细
		return this.billPaymentDEntityDao.queryPaymentDEntityListByPaymentNo(paymentNo);
	}
	
	/**
	 * 根据运单号、付款单号查运单明细
	 * @author foss-231434-bieyexiong
	 * @date 2016-3-21 下午15:35:26
	 */
	@Override
	public BillPaymentDEntity queryPaymentDEntityByWaybillNo(String waybillNo,
			String paymentNo) {
		//根据付款单号查询付款单明细
		List<BillPaymentDEntity> entitys = this.queryPaymentDEntityListByPaymentNo(paymentNo);
		if(!CollectionUtils.isEmpty(entitys)){
			for(BillPaymentDEntity entity : entitys){
				//如果和传入运单号一致，返回()
				if(StringUtils.equals(waybillNo,entity.getWaybillNo())){
					return entity;
				}
			}
		}
		return null;
	}
	
	/**
	 * @param billPaymentDEntityDao the billPaymentDEntityDao to set
	 */
	public void setBillPaymentDEntityDao(
			IBillPaymentDEntityDao billPaymentDEntityDao) {
		this.billPaymentDEntityDao = billPaymentDEntityDao;
	}
}
