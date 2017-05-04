/**   
* @Title: DailyLoadVolumeService.java 
* @Package com.deppon.foss.module.transfer.platform.server.service.impl 
* @Description: 日承载货量service
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年6月26日 上午11:39:47 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.IDailyLoadVolumeDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IDailyLoadVolumeService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.DailyLoadVolumeEntity;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.DateUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/** 
 * @ClassName: DailyLoadVolumeService 
 * @Description: 日承载货量service
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年6月26日 上午11:39:47 
 *  
 */
public class DailyLoadVolumeService implements IDailyLoadVolumeService {
	
	/**
	 * dao
	 */
	private IDailyLoadVolumeDao dailyLoadVolumeDao;
	
	/** 
	 * @param dailyLoadVolumeDao 要设置的 dailyLoadVolumeDao 
	 */
	public void setDailyLoadVolumeDao(IDailyLoadVolumeDao dailyLoadVolumeDao) {
		this.dailyLoadVolumeDao = dailyLoadVolumeDao;
	}

	/** 
	 * @Title: addDailyLoadVolumeEntity 
	 * @Description: 新增
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年6月26日 上午11:39:47 
	 * @param @param dailyLoadVolumeEntity
	 * @param @return    设定文件 
	 * @throws 
	 */
	@Override
	@Transactional
	public boolean addDailyLoadVolumeEntity(
			DailyLoadVolumeEntity dailyLoadVolumeEntity) {
		//查询该外场是否存在有效记录
		dailyLoadVolumeEntity.setBeNewest(FossConstants.YES);
		//查询
		List<DailyLoadVolumeEntity> existEntityList = dailyLoadVolumeDao.queryDailyLoadVolumeNoPaging(dailyLoadVolumeEntity);
		if(!CollectionUtils.isEmpty(existEntityList)){
			throw new TfrBusinessException(dailyLoadVolumeEntity.getOrgName() + "已经存在日承载量信息，请直接修改原记录。");
		}else{
			//当前日期
			Date nowDate = new Date();
			//当前登陆人信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//新增实体设置属性
			dailyLoadVolumeEntity.setAvailabilityDate(DateUtils.getStartDatetime(nowDate));
			dailyLoadVolumeEntity.setCreateTime(nowDate);
			dailyLoadVolumeEntity.setCreateUserCode(cInfo.getEmpCode());
			dailyLoadVolumeEntity.setCreateUserName(cInfo.getEmpName());
			dailyLoadVolumeEntity.setId(UUIDUtils.getUUID());
			dailyLoadVolumeDao.addDailyLoadVolumeEntity(dailyLoadVolumeEntity);
		}
		return true;
	}

	/** 
	 * @Title: queryDailyLoadVolumeList 
	 * @Description: 查询
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年6月26日 上午11:39:47 
	 * @param @param dailyLoadVolumeEntity
	 * @param @param limit
	 * @param @param start
	 * @param @return    设定文件 
	 * @throws 
	 */
	@Override
	public List<DailyLoadVolumeEntity> queryDailyLoadVolumeList(
			DailyLoadVolumeEntity dailyLoadVolumeEntity, int limit, int start) {
		//分页时只查询最新记录
		dailyLoadVolumeEntity.setBeNewest(FossConstants.YES);
		return dailyLoadVolumeDao.queryDailyLoadVolumeList(dailyLoadVolumeEntity, limit, start);
	}

	/** 
	 * @Title: queryDailyLoadVolumeCount 
	 * @Description: 查询个数
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年6月26日 上午11:39:47 
	 * @param @param dailyLoadVolumeEntity
	 * @param @return    设定文件 
	 * @throws 
	 */
	@Override
	public Long queryDailyLoadVolumeCount(
			DailyLoadVolumeEntity dailyLoadVolumeEntity) {
		return dailyLoadVolumeDao.queryDailyLoadVolumeCount(dailyLoadVolumeEntity);
	}

	/** 
	 * @Title: updateDailyLoadVolumeEntity 
	 * @Description: 更新
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年6月26日 上午11:39:47 
	 * @param @param dailyLoadVolumeEntity
	 * @param @return    设定文件 
	 * @throws 
	 */
	@Override
	@Transactional
	public boolean updateDailyLoadVolumeEntity(
			DailyLoadVolumeEntity dailyLoadVolumeEntity) {
		List<DailyLoadVolumeEntity> oldEntityList = dailyLoadVolumeDao.queryDailyLoadVolumeNoPaging(dailyLoadVolumeEntity);
		if(CollectionUtils.isEmpty(oldEntityList)){
			throw new TfrBusinessException("没有查询到要修改的记录，请查询后重试。");
		}
		DailyLoadVolumeEntity oldEntity = oldEntityList.get(0);
		//看是否修改过三个业务字段，如果未修改，则抛出异常。。。
		if(Double.valueOf(oldEntity.getFullValue()).equals(Double.valueOf(dailyLoadVolumeEntity.getFullValue()))
				&& Double.valueOf(oldEntity.getDangerValue()).equals(Double.valueOf(dailyLoadVolumeEntity.getDangerValue()))
				&& dailyLoadVolumeEntity.getDailyLoadVolume().compareTo(oldEntity.getDailyLoadVolume()) == 0){
			throw new TfrBusinessException("未修改任何信息，请修改后重试。");
		}
		//获取当前用户信息
		CurrentInfo cInfo = FossUserContext.getCurrentInfo();
		//如果修改的为当天的记录，将直接update，如果修改的为当天以前的记录，需要将修改前的记录update掉，然后新增一条修改后的记录
		if(beTheSameDay(oldEntity.getAvailabilityDate(),new Date())){
			//update，设置修改时间，修改人信息
			dailyLoadVolumeEntity.setModifyTime(new Date());
			dailyLoadVolumeEntity.setModifyUserCode(cInfo.getEmpCode());
			dailyLoadVolumeEntity.setModifyUserName(cInfo.getEmpName());
			dailyLoadVolumeDao.updateDailyLoadVolumeEntity(dailyLoadVolumeEntity);
		}else{
			//将上一条记录更新，新增一条新的记录
			oldEntity.setBeNewest(FossConstants.NO);
			oldEntity.setModifyTime(new Date());
			oldEntity.setModifyUserCode(cInfo.getEmpCode());
			oldEntity.setModifyUserName(cInfo.getEmpName());
			oldEntity.setExpiryDate(DateUtils.getStartDatetime(new Date()));
			dailyLoadVolumeDao.updateDailyLoadVolumeEntity(oldEntity);
			//插入一条新的记录，重新设置ID
			dailyLoadVolumeEntity.setAvailabilityDate(DateUtils.getStartDatetime(new Date()));
			dailyLoadVolumeEntity.setCreateTime(new Date());
			dailyLoadVolumeEntity.setCreateUserCode(cInfo.getEmpCode());
			dailyLoadVolumeEntity.setCreateUserName(cInfo.getEmpName());
			dailyLoadVolumeEntity.setId(UUIDUtils.getUUID());
			dailyLoadVolumeEntity.setBeNewest(FossConstants.YES);
			dailyLoadVolumeDao.addDailyLoadVolumeEntity(dailyLoadVolumeEntity);
		}
		return false;
	}

	/** 
	 * @Title: queryDailyLoadVolumeHistoryList 
	 * @Description: 查询历史
	 * @author shiwei-045923-shiwei@outlook.com
	 * @date 2014年6月26日 上午11:39:47 
	 * @param @param dailyLoadVolumeEntity
	 * @param @return    设定文件 
	 * @throws 
	 */
	@Override
	public List<DailyLoadVolumeEntity> queryDailyLoadVolumeNoPaging(
			DailyLoadVolumeEntity dailyLoadVolumeEntity) {
		//不分页查询，即为查询历史记录，故此处将newest设置为N
		dailyLoadVolumeEntity.setBeNewest(FossConstants.NO);
		return dailyLoadVolumeDao.queryDailyLoadVolumeNoPaging(dailyLoadVolumeEntity);
	}
	
	/**
	* @Title: beTheSameDay 
	* @Description: 判断两个日期是否为同一天
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月26日 下午4:09:30 
	* @param @param date1
	* @param @param date2
	* @param @return    设定文件 
	* @return boolean    返回类型 
	* @throws
	 */
	private boolean beTheSameDay(Date date1,Date date2){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String str1 = sdf.format(date1);
		String str2 = sdf.format(date2);
		if(StringUtils.equals(str1, str2)){
			return true;
		}
		return false;
	}

	/** 
	* @Title: queryDailyLoadVolumeByOrgCode 
	* @Description: 根据部门code获取日承载货量
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月29日 下午4:00:56 
	* @param @param orgCode
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public DailyLoadVolumeEntity queryDailyLoadVolumeByOrgCode(String orgCode) {
		//备胎
		DailyLoadVolumeEntity pEntity = new DailyLoadVolumeEntity();
		pEntity.setOrgCode(orgCode);
		pEntity.setFullValue(0);
		pEntity.setDangerValue(0);
		//部门为空则全部返回0
		if(StringUtils.isBlank(orgCode)){
			return pEntity;
		}
		//查询
		pEntity.setBeNewest(FossConstants.YES);
		List<DailyLoadVolumeEntity> entityList = dailyLoadVolumeDao.queryDailyLoadVolumeNoPaging(pEntity);
		//未查询到结果也返回0
		if(CollectionUtils.isEmpty(entityList)){
			return pEntity;
		}
		return entityList.get(0);
	}

	/** 
	* @Title: queryDailyLoadVolumeByOrgCodeAndDate 
	* @Description: 根据转运场code、查询日期获取日承载货量
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月29日 下午4:37:25 
	* @param @param orgCode
	* @param @param queryDate
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public DailyLoadVolumeEntity queryDailyLoadVolumeByOrgCodeAndDate(String orgCode,
			Date queryDate) {
		if(StringUtils.isBlank(orgCode) || queryDate == null){
			return new DailyLoadVolumeEntity();
		}
		List<DailyLoadVolumeEntity> entityList = dailyLoadVolumeDao.queryDailyLoadVolumeByOrgCodeAndDate(orgCode, queryDate);
		if(!CollectionUtils.isEmpty(entityList)){
			return entityList.get(0);
		}
		return new DailyLoadVolumeEntity();
	}

	/** 
	* @Title: queryMonthLoadVolumeByOrgCodeAndDate 
	* @Description: 传入指定的部门code，日期，计算月承载货量，需要查询历史记录
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月29日 下午5:20:35 
	* @param @param orgCode
	* @param @param queryDate
	* @param @return    设定文件 
	* @throws 
	*/
	@Override
	public BigDecimal queryMonthLoadVolumeByOrgCodeAndDate(String orgCode,
			Date queryDate) {
		//定义返回的值
		BigDecimal value = BigDecimal.ZERO;
		//获取传入日期所属月份的第一天
		Calendar cal = Calendar.getInstance();
		cal.setTime(queryDate);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		Date firstDayOfMonth = DateUtils.getStartDatetime(cal.getTime());
		Date endQueryDate = DateUtils.getStartDatetime(queryDate);
		//获取传入的查询日期在当月已经过去的天数
		Long dayCount = DateUtils.getTimeDiff(firstDayOfMonth, endQueryDate) + 1;
		
		//查询，如果返回为空，则说明最后一条有效记录的生效日期在该月之前此时取最新记录，乘以天数，即为月承载货量
		List<DailyLoadVolumeEntity> entityList = dailyLoadVolumeDao.queryMonthLoadVolumeByOrgCodeAndDate(orgCode, firstDayOfMonth, endQueryDate);
		int size = CollectionUtils.isEmpty(entityList) ? 0 : entityList.size();
		if(CollectionUtils.isEmpty(entityList)){
			DailyLoadVolumeEntity queryEntity = new DailyLoadVolumeEntity();
			queryEntity.setOrgCode(orgCode);
			queryEntity.setBeNewest(FossConstants.YES);
			entityList = dailyLoadVolumeDao.queryDailyLoadVolumeNoPaging(queryEntity);
			if(CollectionUtils.isEmpty(entityList)){
				return value;
			}
			BigDecimal newValue = entityList.get(0).getDailyLoadVolume();
			return newValue.multiply(new BigDecimal(dayCount));
		}
		//如果查询结果不为空
		//先将第一条记录的生效日期置为该月第一天
		if(firstDayOfMonth.after(entityList.get(0).getAvailabilityDate())){
			entityList.get(0).setAvailabilityDate(firstDayOfMonth);
		}
		//如果最后一条记录的失效日期大于查询日期，则说明期间的记录全为无效
		if(entityList.get(size - 1).getExpiryDate().after(endQueryDate)){
			//此时将最后一条的失效日期设置为查询日期
			entityList.get(size - 1).setExpiryDate(endQueryDate);
			for(int i = 0;i < size;i++){
				//获取生效日期、截止日期的天数差，乘以该条记录的日承载货量
				BigDecimal dayDif = new BigDecimal(DateUtils.getTimeDiff(entityList.get(i).getAvailabilityDate(), entityList.get(i).getExpiryDate()));
				if(i == size - 1){
					BigDecimal dayDif2 = dayDif.add(BigDecimal.ONE);
					value = value.add(entityList.get(i).getDailyLoadVolume().multiply(dayDif2));
				}else{
					value = value.add(entityList.get(i).getDailyLoadVolume().multiply(dayDif));
				}
			}
			return value;
		}else{
			//如果最后一条记录的时效日期小于等于查询日期，则说明有些天数需要使用最新记录来拼接
			//此时将最后一条的失效日期设置为查询日期
			entityList.get(size - 1).setExpiryDate(endQueryDate);
			for(int i = 0;i < size;i++){
				//获取生效日期、截止日期的天数差，乘以该条记录的日承载货量
				value = value.add(entityList.get(i).getDailyLoadVolume().multiply(new BigDecimal(DateUtils.getTimeDiff(entityList.get(i).getAvailabilityDate(), entityList.get(i).getExpiryDate()))));
			}
			//取最新记录
			DailyLoadVolumeEntity queryEntity = new DailyLoadVolumeEntity();
			queryEntity.setOrgCode(orgCode);
			queryEntity.setBeNewest(FossConstants.YES);
			entityList = dailyLoadVolumeDao.queryDailyLoadVolumeNoPaging(queryEntity);
			if(CollectionUtils.isEmpty(entityList)){
				return value;
			}
			//获取查询日期与最新记录生效日期的相差天数
			Long moreCount = DateUtils.getTimeDiff(entityList.get(0).getAvailabilityDate(), endQueryDate) + 1;
			value = value.add(entityList.get(0).getDailyLoadVolume().multiply(new BigDecimal(moreCount)));
			return value;
		}
	}

}
