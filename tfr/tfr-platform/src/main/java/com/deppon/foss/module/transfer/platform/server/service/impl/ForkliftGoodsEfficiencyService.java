package com.deppon.foss.module.transfer.platform.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.BizTypeConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.dao.IForkliftGoodsEfficiencyDao;
import com.deppon.foss.module.transfer.platform.api.server.service.IForkliftGoodsEfficiencyService;
import com.deppon.foss.module.transfer.platform.api.server.service.IPlatformCommonService;
import com.deppon.foss.module.transfer.platform.api.shared.define.PlatformConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftDriverEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftDriverOrgEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.domain.ForkliftEffEntity;
import com.deppon.foss.module.transfer.platform.api.shared.dto.ForkliftGoodsStayDurationDto;
import com.deppon.foss.util.DateUtils;

/**
 * 电叉分货效率 Service
 * @author 200978
 * 2015-2-5
 */
public class ForkliftGoodsEfficiencyService implements
		IForkliftGoodsEfficiencyService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ForkliftGoodsEfficiencyService.class);

	private IForkliftGoodsEfficiencyDao forkliftGoodsEfficiencyDao;
	private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
	
	//注入查询经营本部service
	private IPlatformCommonService platformCommonService;
	
	public void setOrgAdministrativeInfoComplexService(
			IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
		this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
	}
	public void setForkliftGoodsEfficiencyDao(
			IForkliftGoodsEfficiencyDao forkliftGoodsEfficiencyDao) {
		this.forkliftGoodsEfficiencyDao = forkliftGoodsEfficiencyDao;
	}

	/**
	 * 先查询所有有待叉区的外场，针对每个外场进行统计
	 * 电叉分货效率
	 */
	@Override
	public void calculateForkliftGoodsEfficiency() {
			   
			   LOG.error("电叉分货效率 统计方法开始执行！");
			   Date currentiTime = new Date();
			   Calendar calendar = Calendar.getInstance();
			   calendar.setTime(currentiTime);
			   //获取前一天的数据
			   calendar.add(Calendar.DAY_OF_MONTH, -1);
			   // 将时间转为
			   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			   // 将时间格式化后获取日期
			   currentiTime=calendar.getTime();
			   String strDate = sdf.format(currentiTime).substring(PlatformConstants.SONAR_NUMBER_0, PlatformConstants.SONAR_NUMBER_10);
			   // 在将日期字符转为日期格式
			   try {
				   currentiTime = sdf.parse(strDate);
			   } catch (ParseException e) {
				   LOG.error("转换日期格式异常！");
			   }
			   //查询所有含有待叉区，出去分部的的外场
			   List<ForkliftEffEntity> transferCenterCodeList = this.queryTransferCenterContainForklift();
			   /*		Date tempDate = DateUtils.addDayToDate(new Date(), -1);
			    */		// 返回前一天 00:00:00 设置为统计开始时间
			   SimpleDateFormat sb = new SimpleDateFormat("yyyyMM");
			   String staMonthTemp = sb.format(currentiTime);
			   int staMonth = Integer.parseInt(staMonthTemp);
			   for (ForkliftEffEntity forkliftEffEntity : transferCenterCodeList) {
				   //统计主方法
				   this.calculateForkliftGoodsEfficiencyPerTfr(currentiTime, staMonth, forkliftEffEntity.getTransferCenterCode(), forkliftEffEntity.getTransferCenterName());
			   }
			   LOG.error("电叉分货效率 统计方法执行结束！");
			
		
	}
	
	/**
	 * 查询所有含有待叉区，出去分部的的外场
	 * @Author 200978
	 * 2015-2-5
	 * @return
	 */
	private List<ForkliftEffEntity> queryTransferCenterContainForklift(){
		return this.forkliftGoodsEfficiencyDao.queryTransferCenterContainForklift();
	}

	/**
	 * 执行存储过程PRO_FORKLIFT_GOODS_EFFICIENCY   开始统计
	 * @Author 200978
	 * 2015-2-5
	 * @param staDate
	 * @param staMonth
	 * @param tfrCode
	 * @param tfrName
	 */
	private void calculateForkliftGoodsEfficiencyPerTfr(Date staDate,int staMonth,String tfrCode,String tfrName){
		if(StringUtil.isEmpty(tfrCode)||StringUtil.isEmpty(tfrName)){
			LOG.error("查询失败，calculateForkliftGoodsEfficiencyPerTfr方法转运场编码或者转运场名称为空！");
		}
		//执行统计的存储过程  PRO_FORKLIFT_GOODS_EFFICIENCY
		this.forkliftGoodsEfficiencyDao.calculateForkliftGoodsEfficiencyPerTfr(staDate,staMonth,tfrCode,tfrName);
	}
	
	
	/**
	 * 查询给定部门code所属外场
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param code
	 * @return
	 */
	public Map<String, String> queryParentTfrCtrCode(String code) {
		Map<String, String> result = null;

		// 调用综合接口判断当前部门是否外场或外场子部门
		List<String> bizTypesList = new ArrayList<String>();

		bizTypesList.add(BizTypeConstants.ORG_TRANSFER_CENTER);

		OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoComplexService
				.queryOrgAdministrativeInfoIncludeSelfByCode(code, bizTypesList);
		if (orgEntity != null) {
			result = new HashMap<String, String>();
			result.put("code", orgEntity.getCode());
			result.put("name", orgEntity.getName());
		}

		return result;
	}
	
	/**
	 * 查询给定部门code所对应的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-23
	 * @param code
	 * @return
	 */
	public Map<String, String> queryOperationDeptCode(String code) {
		Map<String, String> result = null;
		//查询经营本部
		ForkliftEffEntity temp = this.queryOperationDeptCodeByCurrentCode(code);
		if(temp !=null){
			result = new HashMap<String, String>();
			result.put("code", temp.getOperationDeptCode());
			result.put("name", temp.getOperationDeptName());
		}
		return result;
		//return platformCommonService.findSupHq(code);
	}
	
	/**
	 * 根据部门编码查询所属的经营本部
	 * @Author: 200978  xiaobingcheng
	 * 2015-1-24
	 * @param code
	 * @return
	 */
	private ForkliftEffEntity queryOperationDeptCodeByCurrentCode(String code){
		if(StringUtil.isEmpty(code)){
			throw new TfrBusinessException("根据部门查询所属经营本部失败！查询参数为空！");
		}
		//return this.forkliftGoodsEfficiencyDao.queryOperationDeptCodeByCurrentCode(code);
		ForkliftEffEntity forkliftEffEntity=new ForkliftEffEntity();
		Map<String,String> hpEntity= platformCommonService.findSupHq(code);
		if(hpEntity!=null){
			
			forkliftEffEntity.setOperationDeptCode( hpEntity.get("HQ_CODE")==null?"":hpEntity.get("HQ_CODE"));
			forkliftEffEntity.setOperationDeptName(hpEntity.get("HQ_NAME")==null?"":hpEntity.get("HQ_NAME"));
		}
		return forkliftEffEntity;
	}
	
	/***
	 * 查询电叉分货效率  转运场
	 * @Author 200978
	 * 2015-2-7
	 * @param forkliftEffEntity
	 * @return
	 */
	@Override
	public List<ForkliftEffEntity> queryForkliftGoodsEfficiencyOfTfr(ForkliftEffEntity forkliftEffEntity){
		if(forkliftEffEntity.getStaDate() == null){
			throw new TfrBusinessException("查询时间不能为空！");
		}
		return this.forkliftGoodsEfficiencyDao.queryForkliftGoodsEfficiencyOfTfr(forkliftEffEntity);
	}
	
	/***
	 * 电叉分货效率   叉车司机所属部门
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	@Override
	public List<ForkliftDriverOrgEffEntity> queryForkliftGoodsEfficiencyOfOrg(ForkliftDriverEffEntity forkliftDriverEffEntity,int start,int limit){
		if(forkliftDriverEffEntity.getStaDate() == null){
			throw new TfrBusinessException("查询时间不能为空！");
		}
		return this.forkliftGoodsEfficiencyDao.queryForkliftGoodsEfficiencyOfOrg(forkliftDriverEffEntity,start,limit);
	}
	
	/***
	 * 电叉分货效率   叉车司机所属部门 总条数
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	@Override
	public Long queryForkliftGoodsEfficiencyOfOrgCount(ForkliftDriverEffEntity forkliftDriverEffEntity){
		if(forkliftDriverEffEntity.getStaDate() == null){
			throw new TfrBusinessException("查询时间不能为空！");
		}
		return this.forkliftGoodsEfficiencyDao.queryForkliftGoodsEfficiencyOfOrgCount(forkliftDriverEffEntity);
	}
	
	/**
	 * 电叉分货效率  叉车司机
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	@Override
	public List<ForkliftDriverEffEntity> queryForkliftGoodsEfficiencyOfDriver(ForkliftDriverEffEntity forkliftDriverEffEntity){
		if(forkliftDriverEffEntity.getStaDate() == null){
			throw new TfrBusinessException("查询时间不能为空！");
		}
		return this.forkliftGoodsEfficiencyDao.queryForkliftGoodsEfficiencyOfDriver(forkliftDriverEffEntity);
	}
	
	/**
	 * 查询待叉区停留时长占比
	 * @Author 200978
	 * 2015-2-7
	 * @return
	 */
	@Override
	public List<ForkliftGoodsStayDurationDto> queryForkliftStayDuration(ForkliftEffEntity forkliftEffEntity){
		if(forkliftEffEntity.getStaDate() == null){
			throw new TfrBusinessException("查询时间不能为空！");
		}
		return this.forkliftGoodsEfficiencyDao.queryForkliftStayDuration(forkliftEffEntity);
	}
	/**
	 * @param platformCommonService the platformCommonService to set
	 */
	public void setPlatformCommonService(
			IPlatformCommonService platformCommonService) {
		this.platformCommonService = platformCommonService;
	}
	
	
}
