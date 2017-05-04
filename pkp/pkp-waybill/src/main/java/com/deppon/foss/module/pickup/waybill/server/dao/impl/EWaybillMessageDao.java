package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IEWaybillMessageDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.EWaybillMessageEntity;

/**
 * 
 * @author 297064
 *
 */
/**
 *@项目：FOSS转接组
 *@功能：电子面单下单失败中文异常信息
 *@author:297064-foss-yanggang
 *@date:2015-11-18
 */
public class EWaybillMessageDao extends iBatis3DaoImpl implements IEWaybillMessageDao {

	private static final String NAMESPACE="foss.pkp.EWaybillMessageEntityMapper.";
	
	/**
	 * 国际化资源接口
	 */
	IMessageBundle messageBundle; 
	
	public EWaybillMessageEntity getEWaybillMessageByFailCode(
			String failCode) {
		if(!StringUtils.isBlank(failCode)){
			EWaybillMessageEntity entity = new EWaybillMessageEntity();
			entity.setFailCode(failCode);
			return (EWaybillMessageEntity)this.getSqlSession().selectOne(NAMESPACE+"getEWaybillMessageByFailCode",entity);
		}else{
			return null;
		}
	}
	
	/**
	 * 获取运单校验转译信息
	 */
	public String getWVMessageByFailCode(String failCode){
		List<EWaybillMessageEntity> list = null;
		String failReason = null;
		try{
			if(!StringUtils.isBlank(failCode)){
				EWaybillMessageEntity entity = new EWaybillMessageEntity();
				entity.setFailCode(failCode);
				list = (List<EWaybillMessageEntity>)this.getSqlSession().selectList(NAMESPACE+"getEWaybillMessageByFailCode",entity);
			}
			if(list != null && list.size() > 0){
				EWaybillMessageEntity messageEntity = list.get(0);
				failReason = messageEntity.getMessage();
			}else{
				failReason = messageBundle.getMessage(failCode,""); 
			}
			if(StringUtils.isEmpty(failReason)){
				failReason = failCode;
			}
			logger.info("零担电子运单国际化,异常信息为:"+failReason);
		}catch(Exception e){
			logger.info("零担电子运单国际化失败,异常信息为:"+ExceptionUtils.getFullStackTrace(e));
		}
		return failReason;
	}


	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}
	
	
}
