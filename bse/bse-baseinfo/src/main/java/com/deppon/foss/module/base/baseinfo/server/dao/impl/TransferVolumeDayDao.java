package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ITransferVolumeDayDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TransferVolumeDayEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class TransferVolumeDayDao extends SqlSessionDaoSupport implements ITransferVolumeDayDao {
	 private static final String NAMESPACE = "foss.bse.bse-baseinfo.transferVolumeDayEntityMapper.";
	 /**
		 *<P>添加日承载货量信息<P>
		 * @author :130346-lifanghong
		 * @date : 2014-4-16下午3:28:02
		 * @param entity
		 * @return
		 */
	 @Override
	 @JSON
	public int addTransferVolumeDay(
			TransferVolumeDayEntity entity) {
		  entity.setId(UUIDUtils.getUUID());
		  entity.setCreateTime(new Date());
	      entity.setModifyTime(new Date(NumberConstants.ENDTIME));
		  entity.setActive(FossConstants.ACTIVE);
		  BigDecimal thisMonthDay = new BigDecimal(getThisMonthDay(new Date()));
		  entity.setVolumeMonth(entity.getVolumeDay().multiply(thisMonthDay));
		 return this.getSqlSession().insert(NAMESPACE+"addVolumeDay", entity);
	}
	 /**
		 *<P>根据CODE作废信息<P>
		 * @author :130346-lifanghong
		 * @date : 2014-4-16下午3:33:35
		 * @param entity
		 * @return
		 */
	@Override
	@JSON
	public int deleteTransferVolumeDayEntityByCode(
			TransferVolumeDayEntity entity) {
		   entity.setModifyTime(new Date());
		return getSqlSession().update(NAMESPACE + "updateVolumeDayUnActive", entity);
	}
	/**
	 *<P>根据条件分页查询实体<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 * @param satelliteDeptCode
	 * @param salesDeptcode
	 * @return
	 */
	@Override
	@JSON
	public List<TransferVolumeDayEntity> queryTransferVolumeDayList(
			TransferVolumeDayEntity entity, int start, int limit) {
		// 强制设置为只查询“启用”的记录
		entity.setActive(FossConstants.ACTIVE);
		return getSqlSession().selectList(
				NAMESPACE + "queryTransferVolumeDayList", entity,
				new RowBounds(start, limit));
	}
	/**
	 *<P>查询总数<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 * @param entity
	 * @return
	 */
	@Override
	@JSON
	public long queryTransferVolumeDayListCount(TransferVolumeDayEntity entity) {
		TransferVolumeDayEntity queryEntity = entity == null ? new TransferVolumeDayEntity() : entity;
		queryEntity.setActive(FossConstants.ACTIVE);
		return (Long)getSqlSession().selectOne(NAMESPACE + "queryTransferVolumeDayListCount", queryEntity);
	    
	}
	/**
     * 更新 
     * 
     * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
     */
	@Override
	@JSON
	public TransferVolumeDayEntity updateTransferVolumeDay(
			TransferVolumeDayEntity entity) {
		if (entity == null) {
		    return null;
		}
		List<TransferVolumeDayEntity> entitys = queryTransferVolumeDayList(entity,NumberConstants.ZERO,NumberConstants.NUMBER_1); 
		deleteTransferVolumeDayEntityByCode(entity);
		/*if (entity == null) {
		    return null;
		}*/
		//查询上一次的月承载货量
		TransferVolumeDayEntity oldEntity = new TransferVolumeDayEntity();
		for(TransferVolumeDayEntity transferVolumeDayEntity:entitys){
			oldEntity = transferVolumeDayEntity;
		}
		//获取当月剩下的天数
		BigDecimal lastDay;
		BigDecimal volumeMonth;
		
		volumeMonth = oldEntity.getVolumeMonth();
		lastDay = new BigDecimal(getThisMonthLastDays(new Date()));
		//减去按以前日承载货量算的剩下的天数的承载量
//		-(Double.parseDouble(oldEntity.getVolumeDay()))*lastDay
		volumeMonth = volumeMonth.subtract((oldEntity.getVolumeDay()).multiply(lastDay));
		//加上按新的日承载货量算的新的承载量得到最新的月承载量
//		volumeMonth =volumeMonth+(Double.parseDouble(entity.getVolumeDay()))*lastDay;
		volumeMonth =volumeMonth.add((entity.getVolumeDay()).multiply(lastDay));
		
		entity.setVolumeMonth(volumeMonth);
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateTime(new Date());
		entity.setModifyTime(new Date());
		entity.setCreateUserCode(oldEntity.getModifyUserCode());
		entity.setModifyUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setActive(FossConstants.ACTIVE);
		int result = getSqlSession().insert(NAMESPACE + "addVolumeDay", entity);
		return result > 0 ? entity : null;
	    }
	/**
     * 查询外场对应仓库饱和度危险值和预警值
     * 
     * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
     */
	@Override
	public List<TransferVolumeDayEntity> selectTransferFullAndDangerValue(
			TransferVolumeDayEntity entity) {
		
		entity.setActive(FossConstants.ACTIVE);
		
		 List<TransferVolumeDayEntity> results =  (List<TransferVolumeDayEntity>) getSqlSession().selectList(NAMESPACE + "selectTransferFullAndDangerValue", entity);
		 return results;
	}
	
//	/**
//     * 查询外场的月承载货量
//     * 
//     * @author :130346-lifanghong
//	 * @date : 2014-4-16下午3:33:35
//     */
//	@Override
//	public List<TransferVolumeDayEntity> selectTransferMonthValue(
//			TransferVolumeDayEntity entity) {
//		
//		entity.setActive(FossConstants.ACTIVE);
//		
//		 List<TransferVolumeDayEntity> results =  (List<TransferVolumeDayEntity>) getSqlSession().selectList(NAMESPACE + "selectTransferMonthValue", entity);
//		 return results;
//	}
	
	/**
	 * 获取给定日期的月份的天数
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 */
	public static int getThisMonthDay(Date date){
		//获得这个月的天数
				Calendar rightNow = Calendar.getInstance();
					rightNow.setTime(date);
				int days = rightNow.getActualMaximum(Calendar.DAY_OF_MONTH);
				return days;
	} 
	
	/**
	 * 获取当前月份剩下的天数
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 */
	public static long getThisMonthLastDays(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);  
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);  
		// 设置Calendar日期为下一个月一号  
		calendar.set(Calendar.DATE, 1);  
		// 设置Calendar日期减一,为本月末  
		calendar.add(Calendar.DATE, -1);  
		Date lastDay = calendar.getTime();
		
		long mm = lastDay.getTime()-date.getTime();
		return mm/(NumberConstants.NUMBER_24*NumberConstants.NUMBER_60*NumberConstants.NUMBER_60*NumberConstants.NUMBER_1000);
	} 
	
}
