package com.deppon.foss.module.pickup.pricing.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.pricing.api.server.dao.IldpCompanyValueAddDao;
import com.deppon.foss.module.pickup.pricing.api.server.service.IldpCompanyValueAddService;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.PartbussValueAddEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class LdpCompanyValueAddService implements IldpCompanyValueAddService {
	
	//注入快递代理公司增值服务的Dao
	IldpCompanyValueAddDao  ldpCompanyValueAddDao;
	
	
	public IldpCompanyValueAddDao getLdpCompanyValueAddDao() {
		return ldpCompanyValueAddDao;
	}

	public void setLdpCompanyValueAddDao(
			IldpCompanyValueAddDao ldpCompanyValueAddDao) {
		this.ldpCompanyValueAddDao = ldpCompanyValueAddDao;
	}

	/**
	 *  根据条件查询快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:47:22
	 * @param   entity
	 * @param   start
	 * @param   limit
	 * @return  List<PartbussValueAddEntity>
	 * 
	 *
	 */
	public List<PartbussValueAddEntity> queryEntityByParams(PartbussValueAddEntity entity,int start,int limit) {
		    if(null == entity){
		    	throw new BusinessException("传入的对象为空！");
		    }
		    return ldpCompanyValueAddDao.queryEntityByParams(entity, start, limit);
			 
		}
	
	/**
	 * 新增一条快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:48:48
	 * @param   entity
	 * @return  int 1:成功 -1：失败
	 *
	 */
	public int addNewPartbussValueAddEntity(PartbussValueAddEntity entity) {
		
		if(null == entity){
			throw new BusinessException("传入的对象为空！");
		}
		if(StringUtils.isEmpty(entity.getExpressPartbussCode())){
			throw new BusinessException("必填项:快递代理公司为空！");
		}
        if(StringUtils.isEmpty(entity.getProCode())){
        	throw new BusinessException("必填项:省份为空！");
		}
        if(null == entity.getBeginTime()){
        	throw new BusinessException("必填项:生效时间为空！");
		}
        if(null == entity.getEndTime()){
        	throw new BusinessException("必填项:截止时间为空！");
        }
        if(null == entity.getCodRate()){
        	throw new BusinessException("必填项:代收货款手续费率为空！");
        }
        if(null == entity.getMinCodFee()){
        	throw new BusinessException("必填项:代收货款手续费最低一票为空！");
        }
        if(null == entity.getInsuranceFeeRate()){
        	throw new BusinessException("必填项:保价费率为空！");
        }
        if(null == entity.getMinInsuranceFee()){
        	throw new BusinessException("必填项:保价费最低一票为空！");
        }
        if(null == entity.getFreightPayFeeRate()){
        	throw new BusinessException("必填项:到付费率为空！");
        }
        if(null == entity.getMinFreightPayFee()){
        	throw new BusinessException("必填项:到付费最低一票为空！");
        }
        if(entity.getBeginTime().after(entity.getEndTime())){
        	throw new BusinessException("生效时间不能晚于截止时间！");
        }
/*		List<PartbussValueAddEntity> list = null;
		//根据条件查询配载部门、快递代理公司、省份的组合是否已经存在
		boolean flag = false;
		list = this.queryEntityByParams(entity);
		if(CollectionUtils.isNotEmpty(list)){
			flag = true;
		}
		if(flag){
			throw new BusinessException("该方案对应的生效时间和截止时间存在重叠" +
					"【同一方案多条记录不能存在时间重叠的情况】，请修改后重新提交！");
		}else{*/
//        PartbussValueAddEntity addEntity = new PartbussValueAddEntity();
//        addEntity.setExpressPartbussCode(entity.getExpressPartbussCode());
//        List<PartbussValueAddEntity> list = queryEntityByParams(addEntity);
//        if(CollectionUtils.isNotEmpty(list)){
//            throw new BusinessException("一个快递代理公司只能设置一条增值服务信息!");
//        }
        //记录成功与失败
        int count = 0;
			entity.setLastCreateTime(new Date());
			entity.setLastCreatorCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			entity.setLastCreatorName(FossUserContext.getCurrentUser().getEmpName());
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
			entity.setId(UUIDUtils.getUUID());
			entity.setModifyUser(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			entity.setCreateOrgCode(FossUserContext.getCurrentDeptCode());
			entity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
			entity.setCurrencyCode(FossConstants.CURRENCY_CODE_RMB);
			entity.setMinCodFee(entity.getMinCodFee().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
			entity.setMinInsuranceFee(entity.getMinInsuranceFee().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
			entity.setMinFreightPayFee(entity.getMinFreightPayFee().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
			count = ldpCompanyValueAddDao.addNewPartbussValueAddEntity(entity);
//		}
		
		return count > 0 ? 1 : -1;
	}
	
	/**
	 * 根据id去删除快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:50:40
	 * @param   ids
	 * @return  int 1:成功 -1：失败
	 *
	 */
	public int deletePartbussValueAddEntity(String[] ids) {
		return ldpCompanyValueAddDao.deletePartbussValueAddEntity(ids)>0 ? 1 : -1;
	}
	
	/**
	 * 根据id去激活快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:50:40
	 * @param   ids
	 * @return  int 1:成功 -1：失败
	 *
	 */
	public int activeValueAddedServices(String[] ids) {
		List<PartbussValueAddEntity> list = null;
		//根据条件查询配载部门、快递代理公司、省份的组合是否已经存在
		boolean flag = false;
		list = ldpCompanyValueAddDao.queryEntityByIds(ids[0]);
		PartbussValueAddEntity entity = new PartbussValueAddEntity();
		if(CollectionUtils.isNotEmpty(list)){
			flag = true;
			entity = list.get(0);
		}
		if(flag){
			throw new BusinessException("方案【"+entity.getExpressPartbussName()+"-"+entity.getProName()+"】对应的生效时间和截止时间存在重叠" +
					"【同一方案多条记录不能存在时间重叠的情况】，请修改后重新提交！");
		}
		return ldpCompanyValueAddDao.activeValueAddedServices(ids) >0 ? 1 : -1;
	}
	
	/**
	 * 更新快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:53:40
	 * @param   entity
	 * @return  int 1:成功 -1：失败
	 *
	 */
	public int updatePartbussValueAddEntity(PartbussValueAddEntity entity) {
		
		if(null == entity){
			throw new BusinessException("传入的对象为空！");
		}
		if(StringUtils.isEmpty(entity.getExpressPartbussCode())){
			throw new BusinessException("必填项:快递代理公司为空！");
		}
        if(StringUtils.isEmpty(entity.getProCode())){
        	throw new BusinessException("必填项:省份为空！");
		}
        if(null == entity.getBeginTime()){
        	throw new BusinessException("必填项:生效时间为空！");
		}
        if(null == entity.getEndTime()){
        	throw new BusinessException("必填项:截止时间为空！");
        }
        if(null == entity.getCodRate()){
        	throw new BusinessException("必填项:代收货款手续费率为空！");
        }
        if(null == entity.getMinCodFee()){
        	throw new BusinessException("必填项:代收货款手续费最低一票为空！");
        }
        if(null == entity.getInsuranceFeeRate()){
        	throw new BusinessException("必填项:保价费率为空！");
        }
        if(null == entity.getMinInsuranceFee()){
        	throw new BusinessException("必填项:保价费最低一票为空！");
        }
        if(null == entity.getFreightPayFeeRate()){
        	throw new BusinessException("必填项:到付费率为空！");
        }
        if(null == entity.getMinFreightPayFee()){
        	throw new BusinessException("必填项:到付费最低一票为空！");
        }
        if(entity.getBeginTime().after(entity.getEndTime())){
        	throw new BusinessException("生效时间不能晚于截止时间！");
        }
//        PartbussValueAddEntity addEntity = new PartbussValueAddEntity();
//        addEntity.setExpressPartbussCode(entity.getExpressPartbussCode());
//        addEntity.setId(entity.getId());
//        List<PartbussValueAddEntity> list = queryEntityByParams(addEntity);
//        if(CollectionUtils.isNotEmpty(list)){
//            throw new BusinessException("一个快递代理公司只能设置一条增值服务信息!");
//        }
		entity.setModifyUser(FossUserContext.getCurrentUser().getEmpCode());
		entity.setModifyOrgCode(FossUserContext.getCurrentDeptCode());
		entity.setMinCodFee(entity.getMinCodFee().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
		entity.setMinInsuranceFee(entity.getMinInsuranceFee().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
		entity.setMinFreightPayFee(entity.getMinFreightPayFee().multiply(new BigDecimal(NumberConstants.NUMBER_100)));
		if(StringUtils.equals(entity.getActive(),"激活")){
			entity.setActive(FossConstants.ACTIVE);
		}
		if(StringUtils.equals(entity.getActive(),"未激活")){
			entity.setActive(FossConstants.INACTIVE);
		}
		
		return ldpCompanyValueAddDao.updatePartbussValueAddEntity(entity) > 0 ? 1:-1;
	}
	
	/**
	 * 立即激活快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:56:58
	 * @param   id
	 * @return  int 1:成功 -1：失败
	 *
	 */
	public int activeImmediatelyValueAddedServices(PartbussValueAddEntity entity) {
		List<PartbussValueAddEntity> list = null;
		//根据条件查询配载部门、快递代理公司、省份的组合是否已经存在
		boolean flag = false;
		ldpCompanyValueAddDao.activeImmediatelyValueAddedServices(entity);
		list = ldpCompanyValueAddDao.queryEntityByIds(entity.getId());
		if(CollectionUtils.isNotEmpty(list)){
			flag = true;
			entity = list.get(0);
		}
		if(flag){
			throw new BusinessException("方案【"+entity.getExpressPartbussName()+"-"+entity.getProName()+"】对应的生效时间和截止时间存在重叠" +
					"【同一方案多条记录不能存在时间重叠的情况】，请修改后重新提交！");
		}
		return ldpCompanyValueAddDao.activeImmediatelyValueAddedServices(entity)> 0 ? 1:-1;
	}
	
	/**
	 * 立即终止快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:57:04
	 * @param   id
	 * @return  int 1:成功 -1：失败
	 *
	 */
	public int inActiveImmediatelyValueAddedServices(PartbussValueAddEntity entity) {
		return ldpCompanyValueAddDao.inActiveImmediatelyValueAddedServices(entity) > 0 ? 1:-1;
	}
	
	/**
	 *  根据条件查询快递代理公司增值服务信息
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 上午10:47:22
	 * @param   entity
	 * @return  List<PartbussValueAddEntity>
	 * 
	 *
	 */
	public List<PartbussValueAddEntity> queryEntityByParams(PartbussValueAddEntity entity) {
		if(null == entity){
	    	throw new BusinessException("传入的对象为空！");
	    }
		return ldpCompanyValueAddDao.queryEntityByParams(entity);
	}
	
	/**
	 * 统计查询的行数
	 * 
	 * @author  WangPeng
	 * @Date    2013-8-14 下午1:51:08
	 * @param   entity
	 * @return  Long
	 *
	 */
	public Long countQueryRecord(PartbussValueAddEntity entity) {
		if(null == entity){
	    	throw new BusinessException("传入的对象为空！");
	    }
		return ldpCompanyValueAddDao.countQueryRecord(entity);
	}
}
