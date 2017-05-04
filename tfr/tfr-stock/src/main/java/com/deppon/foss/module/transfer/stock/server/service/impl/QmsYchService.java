package com.deppon.foss.module.transfer.stock.server.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.transfer.scheduling.api.shared.util.ConstantsNumberSonar;
import com.deppon.foss.module.transfer.stock.api.server.dao.IQmsYchDao;
import com.deppon.foss.module.transfer.stock.api.server.service.IQmsYchService;
import com.deppon.foss.module.transfer.stock.api.shared.domain.InnerPickupCurrDeptEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.IsLoseGroupEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.QmsYchExceptionReportEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillArridept;
import com.deppon.foss.module.transfer.stock.api.shared.domain.WaybillArrideptList;
import com.deppon.foss.util.define.FossConstants;



/**
 * 
* @description 异常货service
* @version 1.0
* @author 218381-foss-lijie
* @update 2015年9月12日 下午2:45:05
 */
public class QmsYchService implements IQmsYchService {
	
	private static final Logger LOGGER = LogManager.getLogger(QmsYchService.class);
	
	private IQmsYchDao qmsYchDao;
	
	public void setQmsYchDao(IQmsYchDao qmsYchDao) {
		this.qmsYchDao = qmsYchDao;
	}



	/**
	* @description FOSS系统库存中开单=90天的运单信息
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IQmsYchService#queryBillTimeBigNinetyDay()
	* @author 218381-foss-lijie
	* @update 2015年6月1日 上午9:43:45
	* @version V1.0
	*/
	@Override
	public List<QmsYchEntity> queryBillTimeBigNinetyDay() {
		List<QmsYchEntity>  qmsYchEntityList = null;
		List<QmsYchEntity>  qmsYchEntityToYchList = new ArrayList<QmsYchEntity>();
		try {
			//让日期为之前90天的日期
			Date date = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.set(Calendar.DATE, cal.get(Calendar.DATE) - ConstantsNumberSonar.SONAR_NUMBER_90);
			
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.SECOND,0);
			cal.set(Calendar.MINUTE,0);
			Date beginDate = cal.getTime();
			
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date);
			cal2.set(Calendar.DATE, cal2.get(Calendar.DATE) - ConstantsNumberSonar.SONAR_NUMBER_90);
			cal2.set(Calendar.HOUR_OF_DAY, ConstantsNumberSonar.SONAR_NUMBER_23);
			cal2.set(Calendar.SECOND,ConstantsNumberSonar.SONAR_NUMBER_59);
			cal2.set(Calendar.MINUTE,ConstantsNumberSonar.SONAR_NUMBER_59);
			Date endDate = cal2.getTime();
			
			qmsYchEntityList = qmsYchDao.queryBillTimeBigNinetyDay(beginDate, endDate);
			//判断运单的提货网点   1:营业部,直接推送给Ych系统   
			//2:驻地营业部,查找驻地营业部的外场,用外场和货物当前所在部门比较,如果是,推送,否则不推送
			for(QmsYchEntity entity : qmsYchEntityList){
				//库存部门code
				String orgCode = entity.getOrgCode();
				//目的部门code
				String targetOrgCode = entity.getTargetOrgCode();
				
				if(FossConstants.YES.equals(entity.getIsStation())){
					targetOrgCode = entity.getTransferCenter();
				}
				if(orgCode.equals(targetOrgCode)){
					qmsYchEntityToYchList.add(entity);
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("数据查询异常", e);
			throw new BusinessException("数据查询异常",e);
		}
		return qmsYchEntityToYchList;
	}



	
	/**
	* @description 查询是否在零担丢货小组或者快递丢货小组
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IQmsYchService#isLoseGroup(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年6月1日 上午11:16:13
	* @version V1.0
	*/
	@Override
	public QmsYchExceptionReportEntity isLoseGroup(String waybillNo) { 
		IsLoseGroupEntity isLoseGroupEntity = new IsLoseGroupEntity();
		//返回给异常货查询的实体
		QmsYchExceptionReportEntity entity = new QmsYchExceptionReportEntity();
		try {
			isLoseGroupEntity = qmsYchDao.isLoseGroup(waybillNo);
			if(isLoseGroupEntity != null){
				entity.setIsInLoseGroup("Y");
				entity.setIsSuccess(1);
			}else{
				entity.setIsInLoseGroup("N");
				entity.setIsSuccess(1);
			}
			
		} catch (Exception e) {
			entity.setIsSuccess(0);
			entity.setReason(waybillNo+"运单号查询失败");
		}
		return entity;
	}



	
	/**
	* @description 内部带货同步所处部门
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IQmsYchService#innerPickupCurrDept(java.lang.String)
	* @author 218381-foss-lijie
	* @update 2015年6月1日 下午3:46:01
	* @version V1.0
	*/
	@Override
	public List<InnerPickupCurrDeptEntity> innerPickupCurrDept() {
		List<InnerPickupCurrDeptEntity> list = null;
		try {
			List<InnerPickupCurrDeptEntity> entityList = qmsYchDao.innerPickupCurrDept();
			list= new ArrayList<InnerPickupCurrDeptEntity>();
			if(entityList != null && entityList.size() > 0){
				for(InnerPickupCurrDeptEntity entity : entityList){
					entity.setIsSuccess(1);
					list.add(entity);
				}
			}
			
		} catch (Exception e) {
			return list;
		}
		
		return list;
	}



	
	/**
	* @description 异常货同步未处理的单号到达部门
	* (non-Javadoc)
	* @see com.deppon.foss.module.transfer.stock.api.server.service.IQmsYchService#waybillArridept(java.util.List)
	* @author 218381-foss-lijie
	* @update 2015年11月9日 上午9:37:24
	* @version V1.0
	*/
	@Override
	public WaybillArrideptList queryWaybillArridept(List<String> list) {
		WaybillArrideptList waybillList = new WaybillArrideptList();
		List<WaybillArridept> waybillArrideptList = null;
		try {
			waybillArrideptList = qmsYchDao.queryWaybillArridept(list);
			waybillList.setWaybillArrideptList(waybillArrideptList);
			waybillList.setIsSuccess("Y");
		} catch (Exception e) {
			waybillList.setIsSuccess("N");
			waybillList.setMessage("查询数据失败");
		}
		
		
		return waybillList;
	}
	
	
}
