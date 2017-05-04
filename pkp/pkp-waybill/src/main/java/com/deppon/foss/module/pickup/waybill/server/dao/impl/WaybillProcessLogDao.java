package com.deppon.foss.module.pickup.waybill.server.dao.impl;

import java.util.Date;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillProcessLogDao;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillProcessLogEntity;
import com.deppon.foss.util.UUIDUtils;

/**
 * WaybillProcessDao
 *
 * @author 325220-foss-liuhui
 * @date 2016年5月14日
 */
public class WaybillProcessLogDao extends iBatis3DaoImpl implements IWaybillProcessLogDao {

	/**
	 * Logger日志
	 */
	protected static final Logger LOGGER = LoggerFactory.getLogger(WaybillProcessLogDao.class);
	
	private static final String NAMESPACE_PROCESS_LOG_ENTITY = "foss.pkp.waybillProcessLogMapper.";
	
	private static final int failReasonSize = 4000; 
	
	/**
	 * 保存日志,FailResion保留
	 */
	@Override
	public int addWaybillProcessLogEntity(WaybillProcessLogEntity waybillProcessLogEntity) {
		int result = 0;
		String orderNo = null;
		String waybillNo = null;
		try{
			if(waybillProcessLogEntity!=null){
				orderNo = waybillProcessLogEntity.getOrderNo();
				waybillNo = waybillProcessLogEntity.getWaybillNo();
				String failResion = waybillProcessLogEntity.getFailResion();
				//当大于4000个字符时候，取0到3999
				if(StringUtils.isNotEmpty(failResion)&&failResion.length()  > failReasonSize){
					failResion = failResion.substring(0,failReasonSize-1);
					waybillProcessLogEntity.setFailResion(failResion);
				}
				if(StringUtils.isEmpty(waybillProcessLogEntity.getId())){
					waybillProcessLogEntity.setId(UUIDUtils.getUUID());
				}
				result = getSqlSession().insert(NAMESPACE_PROCESS_LOG_ENTITY + "insertSelective", waybillProcessLogEntity);
			}
		}catch(Exception e){
			LOGGER.info("零担电子运单异常日志,方法名:addWaybillProcessLogEntity(),订单号"+orderNo+",运单号:"+waybillNo+",异常信息："+ ExceptionUtils.getFullStackTrace(e));
		}
		LOGGER.info("零担电子运单日志,方法名:addWaybillProcessLogEntity(),订单号"+orderNo+",运单号:"+waybillNo);
		return result;
	}
	
	/**
	 * 添加日志
	 * @param content
	 * @param orderNo
	 * @param waybillNo
	 * @param currentDate
	 * @param logType
	 * @param operateResult
	 */
	@Override
	public int saveLog(String content,String orderNo,String waybillNo,Date currentDate,String logType,String operateResult,String failReason){
		int result = 0;
		try{
			/**
			 * 记录同步OMS的回调日志
			 */
			WaybillProcessLogEntity log = new WaybillProcessLogEntity();
			log.setId(UUIDUtils.getUUID());
			log.setOrderNo(orderNo);
			log.setWaybillNo(waybillNo);
			log.setCreateTime(currentDate);
			log.setModifyTime(currentDate);
			log.setLogType(logType);
			log.setOperateResult(operateResult);
			log.setContent(content);
			//当大于4000个字符时候，取0到3999
			if(StringUtils.isNotEmpty(failReason)&&failReason.length()  > failReasonSize){
				failReason = failReason.substring(0,failReasonSize-1);
			}
			log.setFailResion(failReason);
			result = this.getSqlSession().insert(NAMESPACE_PROCESS_LOG_ENTITY+"insertSelective", log);
		}catch(Exception e){
			LOGGER.info("零担电子运单异常日志,方法名:saveLog(),订单号"+orderNo+",运单号:"+waybillNo+",异常信息："+ ExceptionUtils.getFullStackTrace(e));
		}
		LOGGER.info("零担电子运单日志,方法名:saveLog(),订单号："+orderNo+",运单号:"+waybillNo+",内容,"+ content);
		return result;
	}
}
