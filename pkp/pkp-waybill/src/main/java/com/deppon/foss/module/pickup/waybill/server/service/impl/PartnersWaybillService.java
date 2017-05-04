package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IPartnersWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IPartnersWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.domain.PartnersWaybillEntity;

public class PartnersWaybillService implements IPartnersWaybillService {
	
	private Logger logger = LoggerFactory.getLogger(PartnersWaybillService.class);
	
	
	private IPartnersWaybillDao partnersWaybillDao ;
 
	@Override
	public String addPartnersWaybillEntity(PartnersWaybillEntity waybill) {
		logger.info("进入addPartnersWaybillEntity 参数信息："+ReflectionToStringBuilder.toString(PartnersWaybillEntity.class));
		String str = null ;
		try {
			if(waybill != null){
				str = partnersWaybillDao.addPartnersWaybillEntity(waybill);
				logger.info("进入addPartnersWaybillEntity 返回信息："+str);
			}
		} catch (Exception e) {
			logger.error("添加合伙人运单信息异常："+e.getMessage());
			throw new BusinessException("添加合伙人运单信息异常："+e.getMessage());
		}
		return str ;
	}

	@Override
	public int updatePartnersWaybill(PartnersWaybillEntity waybill) {
		logger.info("进入updatePartnersWaybill  参数信息："+ReflectionToStringBuilder.toString(PartnersWaybillEntity.class));
		int count = 0 ;
		try {
			if(waybill != null){
				count = partnersWaybillDao.updatePartnersWaybill(waybill) ;
			}
			logger.info("进入updatePartnersWaybill 更新记录数："+count);
		} catch (Exception e) {
			logger.error("更新合伙人运单信息异常："+e.getMessage());
			throw new BusinessException("更新合伙人运单信息异常："+e.getMessage());
		}
		return count ;
	}

	/**
	 * 更新合伙人运单信息是否可用
	 * @param map 只有两个参数 active：是否可激活,waybillNo:运单号
	 * @return
	 * @author 272311-sangwenhao
	 * @date 2016-1-18
	 */
	@Override
	public int updateActiveByWaybillNo(Map<String, String> map) {
		logger.info("进入updateActiveByWaybillNo");
		int count = 0 ;
		try {
			if(map!=null && map.isEmpty()){
				count = partnersWaybillDao.updateActiveByWaybillNo(map);
			}
			logger.info("进入updateActiveByWaybillNo 更新记录数："+count);
		} catch (Exception e) {
			logger.error("更新合伙人运单信息异常："+e.getMessage());
			throw new BusinessException("更新合伙人运单信息异常："+e.getMessage());
		}
		return count ;
	}

	public IPartnersWaybillDao getPartnersWaybillDao() {
		return partnersWaybillDao;
	}

	public void setPartnersWaybillDao(IPartnersWaybillDao partnersWaybillDao) {
		this.partnersWaybillDao = partnersWaybillDao;
	}

	@Override
	public PartnersWaybillEntity queryPartnerWaybillEntityByWaybillId(String waybillNoId) {
		logger.info("进入queryPartnerWaybillEntity");
		PartnersWaybillEntity partnersWaybillEntity = null ;
		try {
			if(StringUtils.isNotBlank(waybillNoId)){
				partnersWaybillEntity = partnersWaybillDao.queryPartnerWaybillEntityByWaybillId(waybillNoId);
			}
		} catch (Exception e) {
			logger.error("根据运单id查询合伙人运单信息异常："+e.getMessage());
			throw new BusinessException("根据运单id查询合伙人运单信息异常："+e.getMessage());
		}
		return partnersWaybillEntity ;
	}

	@Override
	public int updatePartnerWaybillById(PartnersWaybillEntity waybill) {
		logger.info("进入updatePartnerWaybillById");
		int count = 0 ;
		try {
			if(waybill != null){
				count = partnersWaybillDao.updatePartnerWaybillById(waybill);
			}
			logger.info("进入updatePartnerWaybillById 更新记录数："+count);
		} catch (Exception e) {
			logger.error("更新合伙人运单信息异常："+e.getMessage());
			throw new BusinessException("更新合伙人运单信息异常："+e.getMessage());
		}
		return count ;
	}
	
	@Override
	public PartnersWaybillEntity queryPartnerWaybillEntityByWaybillNo(Map<String, String> map) {
		logger.info("进入queryPartnerWaybillEntityByWaybillNo");
		PartnersWaybillEntity partnersWaybillEntity = null ;
		try {
			if(null != map){
				partnersWaybillEntity = partnersWaybillDao.queryPartnerWaybillEntityByWaybillNo(map);
			}
		} catch (Exception e) {
			logger.error("根据运单编号查询合伙人运单信息异常："+e.getMessage());
			throw new BusinessException("根据运单编号查询合伙人运单信息异常："+e.getMessage());
		}
		return partnersWaybillEntity ;
	}
}
