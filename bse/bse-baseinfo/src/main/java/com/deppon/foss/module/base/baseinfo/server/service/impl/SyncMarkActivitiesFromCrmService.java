package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.esb.inteface.domain.crm.DevelopDeptInfos;
import com.deppon.esb.inteface.domain.crm.DiscountInfo;
import com.deppon.esb.inteface.domain.crm.GoodsLine;
import com.deppon.esb.inteface.domain.crm.MarketingActivityRequest;
import com.deppon.esb.inteface.domain.crm.MarketingActivityResponse;
import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkActivitiesDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkActivitiesService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkLineDeptService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkMultipleService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IMarkOptionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISyncMarkActivitiesFromCrmService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkActivitiesEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkLineDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkMultipleEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.MarkOptionsEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MarkActivitiesException;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
import com.deppon.foss.ws.syncdata.CommonException;

/**
 * 对市场活动信息进行操作的接口
 * 
 * @author 078816
 * @date 2014-04-14
 * 
 */
public class SyncMarkActivitiesFromCrmService implements
		ISyncMarkActivitiesFromCrmService {

	/**
	 * 日志.
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SyncMarkActivitiesFromCrmService.class);

	/**
	 * 定义常量
	 */
	private static final String USER_CODE = "CRM";

	// 活动的Service层接口
	private IMarkActivitiesService markActivitiesService;

	// 活动的开展部门Service层接口
	private IMarkActivitiesDeptService markActivitiesDeptService;

	// 活动线路部门Service层接口
	private IMarkLineDeptService markLineDeptService;

	// 优惠活动折扣信息Service接口
	private IMarkOptionsService markOptionsService;

	// 活动相关枚举信息Service接口
	private IMarkMultipleService markMultipleService;

	//组织信息Service接口
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	/**
	 * @param markLineDeptService the markLineDeptService to set
	 */
	public void setMarkLineDeptService(IMarkLineDeptService markLineDeptService) {
		this.markLineDeptService = markLineDeptService;
	}

	/**
	 * @param markOptionsService the markOptionsService to set
	 */
	public void setMarkOptionsService(IMarkOptionsService markOptionsService) {
		this.markOptionsService = markOptionsService;
	}

	/**
	 * @param markMultipleService the markMultipleService to set
	 */
	public void setMarkMultipleService(IMarkMultipleService markMultipleService) {
		this.markMultipleService = markMultipleService;
	}

	/**
	 * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置活动的Service层接口
	 * 
	 * auther:wangpeng_078816 date:2014-4-9
	 * 
	 */
	public void setMarkActivitiesService(
			IMarkActivitiesService markActivitiesService) {
		this.markActivitiesService = markActivitiesService;
	}

	/**
	 * 设置活动的Service层接口
	 * 
	 * auther:wangpeng_078816 date:2014-4-9
	 * 
	 */
	public void setMarkActivitiesDeptService(
			IMarkActivitiesDeptService markActivitiesDeptService) {
		this.markActivitiesDeptService = markActivitiesDeptService;
	}

	/**
	 * 接收CRM同步过来市场活动信息
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-15
	 *
	 */
	@Override
	@Transactional
	public MarketingActivityResponse syncMarkActivitiesInfo(
			MarketingActivityRequest requestInfo) throws CommonException {
		// 返回响应
		MarketingActivityResponse response = new MarketingActivityResponse();
		// 市场活动对象
		MarkActivitiesEntity markActivityEntity = new MarkActivitiesEntity();
		// 活动的crmId
		BigDecimal markActivityCrmId = requestInfo.getFid();
		try {

			if (null == markActivityCrmId) {
				throw new MarkActivitiesException("传入的活动CRMID为空");
			}
			// 活动是否过期的标记
			boolean markActivtyflag = false;
			markActivtyflag = markActivitiesService
					.queryIsExistsMarkActivitiesByCrmId(markActivityCrmId);
			markActivityEntity.setId(UUIDUtils.getUUID());
			markActivityEntity.setIsExhibitionGoods(requestInfo.getIsExhibitionGoods());
			markActivityEntity.setActiveCrmId(markActivityCrmId);
			//活动开始时间
			markActivityEntity.setActiveStartTime(requestInfo
					.getActivityBeginTime());
			//活动结束时间
			markActivityEntity.setActiveEndTime(requestInfo
					.getActivityEndTime());
			//活动类别 零担or快递
			markActivityEntity.setActivityCategory(requestInfo
					.getActivityCategory());
			//活动类型 全国or区域活动
			markActivityEntity.setActivityType(requestInfo.getActivityType());
			markActivityEntity.setName(requestInfo.getActivityName());
			markActivityEntity.setCode(requestInfo.getActivityCode());
			if(StringUtils.isNotEmpty(requestInfo
					.getBillMaxMoney())){
				markActivityEntity.setMaxCargoAmount(new BigDecimal(requestInfo
						.getBillMaxMoney()));
			}
			if(StringUtils.isNotEmpty(requestInfo
					.getBillMinMoney())){
				markActivityEntity.setMinCargoAmount(new BigDecimal(requestInfo
						.getBillMinMoney()));
			}
			if(StringUtils.isNotEmpty(requestInfo
					.getGoodsMaxWeight())){
				markActivityEntity.setMaxCargoWeight(new BigDecimal(requestInfo
						.getGoodsMaxWeight()));
			}
			if(StringUtils.isNotEmpty(requestInfo
					.getGoodsMinWeight())){
				markActivityEntity.setMinCargoWeight(new BigDecimal(requestInfo
						.getGoodsMinWeight()));
			}
			if(StringUtils.isNotEmpty(requestInfo
					.getGoodsMaxVolume())){
				markActivityEntity.setMaxCargoVolume(new BigDecimal(requestInfo
						.getGoodsMaxVolume()));
			}
			if(StringUtils.isNotEmpty(requestInfo
					.getGoodsMinVolume())){
				markActivityEntity.setMinCargoVolume(new BigDecimal(requestInfo
						.getGoodsMinVolume()));
			}
			markActivityEntity.setCreateUser(USER_CODE);
			markActivityEntity.setModifyUser(USER_CODE);
			markActivityEntity.setCreateDate(new Date());
			markActivityEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
			String isActive = requestInfo.getFstatus();
			if (markActivtyflag) {
				if(StringUtils.equals(isActive, FossConstants.ACTIVE)||StringUtils.equals(isActive, FossConstants.INACTIVE)){
					markActivityEntity.setActive(isActive);
//				}else if(StringUtils.equals(isActive, FossConstants.INACTIVE)){
//					markActivityEntity.setActive(isActive);
				}else{
					markActivityEntity.setActive(FossConstants.ACTIVE);
				}
				// 活动对象存在，执行修改操作
				 markActivitiesService
						.updateMarkActivities(markActivityEntity);
				 
				//wp_078816_20140627修改 当活动失效后，作废和该活动相关的其它信息
				if(StringUtils.equals(isActive, FossConstants.INACTIVE)){
					
					//根据活动CRMID作废活动开展部门
					MarkActivitiesDeptEntity deptEntity = new MarkActivitiesDeptEntity();
					deptEntity.setActive(FossConstants.INACTIVE);
					deptEntity.setActiviteCrmId(markActivityCrmId);
					deptEntity.setModifyUser(USER_CODE);
					markActivitiesDeptService.updateMarkActivitiesDept(deptEntity);
					
					//根据活动CRMID作废活动线路部门
					MarkLineDeptEntity lineDeptEntity = new MarkLineDeptEntity();
					lineDeptEntity.setActive(FossConstants.INACTIVE);
					lineDeptEntity.setActiviteCrmId(markActivityCrmId);
					lineDeptEntity.setModifyUser(USER_CODE);
					markLineDeptService.updateMarkActivitiesLineDept(lineDeptEntity);
					
					//根据活动CRMID作废优惠活动折扣信息
					MarkOptionsEntity  opEntity = new MarkOptionsEntity();
					opEntity.setActive(FossConstants.INACTIVE);
					opEntity.setActiveCrmId(markActivityCrmId);
					opEntity.setModifyUser(USER_CODE);
					markOptionsService.updateMarkActivitiesOptions(opEntity);
					
					//根据活动CRMID作废活动相关枚举信息
					MarkMultipleEntity mulEntity = new MarkMultipleEntity();
					mulEntity.setModifyUser(USER_CODE);
					mulEntity.setActiviteCrmId(markActivityCrmId);
					markMultipleService.deleteMarkActivitiesMultiple(mulEntity);
					
					response.setErrorMsg(null);
					response.setFid(markActivityCrmId);
					response.setResult(Boolean.TRUE);
					LOGGER.info("FOSS receive mark activity message successfully!: 作废市场活动及其相关信息成功！");
					return response;
				}
			} else {
				if(StringUtils.equals(isActive, FossConstants.ACTIVE)){
					markActivityEntity.setActive(isActive);
					// 活动对象不存在，执行新增操作
					markActivitiesService.addMarkActivities(markActivityEntity);
				}
			}
			// 活动信息操作成功后，获取fossId
			String activiteId = markActivityEntity.getId();
			List<DevelopDeptInfos> deptList = requestInfo.getDevelopDeptInfos();
			// 判断开展部门列表是否为空，不会空则进行相应的操作
			if (!CollectionUtils.isEmpty(deptList)) {
				//在活动信息生效的情况下不管活动部门是否做过修改都根据活动CRMID作废活动开展部门 然后开始新增   同CRM系统逻辑一致
				// 活动部门对象
				MarkActivitiesDeptEntity activeDeptEntity = new MarkActivitiesDeptEntity();
				activeDeptEntity.setActive(FossConstants.INACTIVE);
				activeDeptEntity.setActiviteCrmId(markActivityCrmId);
				activeDeptEntity.setModifyUser(USER_CODE);
				markActivitiesDeptService.updateMarkActivitiesDept(activeDeptEntity);
				BigDecimal activityDeptCrmId = new BigDecimal(0);
				boolean flag = false;
				// 需要新增操作的的列表
				List<MarkActivitiesDeptEntity> insertList = new ArrayList<MarkActivitiesDeptEntity>();
				// 转化封装市场活动开展部门信息
				for (DevelopDeptInfos entity : deptList) {
					MarkActivitiesDeptEntity deptEntity = new MarkActivitiesDeptEntity();
					activityDeptCrmId = entity.getFid();
					flag = markActivitiesDeptService
							.queryIsExistsMarkActivitiesDeptByCrmId(activityDeptCrmId);
					String deptId = UUIDUtils.getUUID();
					deptEntity.setId(deptId);
					//CRM暂时没有修改操作，所有数据都默认为有效的
					deptEntity.setActive(FossConstants.ACTIVE);
					deptEntity.setActiviteCrmId(markActivityCrmId);
					deptEntity.setActiviteId(activiteId);
					deptEntity.setCrmId(activityDeptCrmId);
					//将crm传递过来的开展部门标杆编码转换为组织编码
					OrgAdministrativeInfoEntity orgEntity = orgAdministrativeInfoService
							.queryOrgAdministrativeInfoByUnifiedCodeNoCache(entity.getSponsorDepts());
					deptEntity.setOrgCode(orgEntity.getCode());
					deptEntity.setCreateUser(USER_CODE);
					deptEntity.setModifyUser(USER_CODE);
					deptEntity.setCreateDate(new Date());
					deptEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
					
					if (!flag) {
						insertList.add(deptEntity);
					} 
				}
				// 判断插入列表是否为空，不为空进行插入操作
				if (!CollectionUtils.isEmpty(insertList)) {
					markActivitiesDeptService.addMarkActivitiesDept(insertList);
				}
				
			}

			// 获取市场活动线路信息
			List<GoodsLine> lineList = requestInfo.getGoodsLine();
			// 判断线路信息列表是否为空，不为空，则进行相应的操作
			if (!CollectionUtils.isEmpty(lineList)) {
				BigDecimal activityLineCrmId = new BigDecimal(0);
				boolean flag = false;
				// 需要新增操作的的列表   如后续需在生效的情况下修改 可跟活动开展部门保持一致
				List<MarkLineDeptEntity> insertList = new ArrayList<MarkLineDeptEntity>();
				
				// 遍历转化封装crm传递过来的活动线路信息
				for (GoodsLine lineEntity : lineList) {
					// 活动线路信息对象
					MarkLineDeptEntity lineDeptEntity = new MarkLineDeptEntity();
					activityLineCrmId = lineEntity.getFid();
					flag = markLineDeptService
							.queryIsExistsMarkActivitiesLineDeptByCrmId(activityLineCrmId);
					lineDeptEntity.setId(UUIDUtils.getUUID());
					//CRM暂时没有修改操作，所有数据都默认为有效的
					lineDeptEntity.setActive(FossConstants.ACTIVE);
					lineDeptEntity.setActiviteCrmId(markActivityCrmId);
					lineDeptEntity.setActiviteId(activiteId);
					lineDeptEntity.setCrmId(activityLineCrmId);
					//将CRM传递过来的线路对应的出发外场和到达外场的标杆编码进行转换
					String leaveOutFieldUnifiedCode = lineEntity
							.getStartingOutfield();
					if (!StringUtils.isEmpty(leaveOutFieldUnifiedCode)) {

						OrgAdministrativeInfoEntity leaveDept = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByUnifiedCodeNoCache(leaveOutFieldUnifiedCode);
						lineDeptEntity.setOrigOrgCode(leaveDept.getCode());
						lineDeptEntity.setOrigOrgName(leaveDept.getName());
					}
					String arriveOutFieldUnfiedCode = lineEntity
							.getArrivalOutfield();
					if (!StringUtils.isEmpty(arriveOutFieldUnfiedCode)) {
						OrgAdministrativeInfoEntity arriveDept = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByUnifiedCodeNoCache(arriveOutFieldUnfiedCode);
						lineDeptEntity.setDestOrgCode(arriveDept.getCode());
						lineDeptEntity.setDestOrgName(arriveDept.getName());
					}
					//出发区域
					String leaveAreaCode = lineEntity.getStartingArea();
					if(StringUtils.isNotEmpty(leaveAreaCode)){
						OrgAdministrativeInfoEntity leaveAreaEntity = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByUnifiedCodeNoCache(leaveAreaCode);
						lineDeptEntity.setLeaveAreaCode(leaveAreaEntity.getCode());
					}
					//到达区域
					String arriveAreaCode = lineEntity.getArrivalArea();
					if(StringUtils.isNotEmpty(arriveAreaCode)){
						OrgAdministrativeInfoEntity arriveAreaEntity = orgAdministrativeInfoService
								.queryOrgAdministrativeInfoByUnifiedCodeNoCache(arriveAreaCode);
						lineDeptEntity.setArriveAreaCode(arriveAreaEntity.getCode());
					}
					lineDeptEntity.setCreateUser(USER_CODE);
					lineDeptEntity.setModifyUser(USER_CODE);
					lineDeptEntity.setCreateDate(new Date());
					lineDeptEntity.setModifyDate(new Date(
							NumberConstants.ENDTIME));

					if (!flag) {
						insertList.add(lineDeptEntity);
					} 
				}
				// 判断插入列表是否为空，不为空进行插入操作
				if (!CollectionUtils.isEmpty(insertList)) {
					markLineDeptService.addMarkActivitiesLineDept(insertList);
				}
				
			}

			// 获取市场活动折扣信息
			List<DiscountInfo> optionsList = requestInfo.getDiscountInfos();
			// 判断折扣信息列表是否为空，不为空，则进行相应的操作
			if (!CollectionUtils.isEmpty(optionsList)) {
				BigDecimal activityOptionsCrmId = new BigDecimal(0);
				boolean flag = false;
				// 需要新增操作的的列表
				List<MarkOptionsEntity> insertList = new ArrayList<MarkOptionsEntity>();
				
				// 遍历转化封装crm传递过来的折扣信息
				for (DiscountInfo entity : optionsList) {
					// 折扣信息对象
					MarkOptionsEntity optionsEntity = new MarkOptionsEntity();
					activityOptionsCrmId = entity.getFid();
					flag = markOptionsService
							.queryIsExistsMarkActivitiesOptionsByCrmId(activityOptionsCrmId);
					optionsEntity.setId(UUIDUtils.getUUID());
					//CRM暂时没有修改操作，所有数据都默认为有效的
					optionsEntity.setActive(FossConstants.ACTIVE);
					optionsEntity.setActiveCrmId(markActivityCrmId);
					optionsEntity.setActiveId(activiteId);
					optionsEntity.setCrmId(activityOptionsCrmId);
					// TODO此处crm过来是标杆编码
					optionsEntity.setValue(new BigDecimal(entity
							.getPriceDiscount()));
					optionsEntity.setName(entity.getPreferentialProduct());
					// 续重最低费率
					optionsEntity.setContinueHeavyLowestRate(entity.getConHeavyLowestRate());
					optionsEntity.setCreateUser(USER_CODE);
					optionsEntity.setModifyUser(USER_CODE);
					optionsEntity.setCreateDate(new Date());
					optionsEntity.setModifyDate(new Date(
							NumberConstants.ENDTIME));

					if (!flag) {
						insertList.add(optionsEntity);
					} 
				}
				// 判断插入列表是否为空，不为空进行插入操作
				if (!CollectionUtils.isEmpty(insertList)) {
					markOptionsService.addMarkActivitiesOptions(insertList);
				}
				
			}

			// 获取第一行业列表
			List<String> firstTradesList = requestInfo.getFirstTrades();
			// 获取第二行业列表
			List<String> secondeTradesList = requestInfo.getSecondTrades();
			// 获取开单品名列表
			List<String> goodNameList = requestInfo.getGoodsName();
			// 获取订单来源列表
			List<String> orderResList = requestInfo.getOrderResource();
			// 获取产品类型列表
			List<String> productList = requestInfo.getProductType();

			// 存放活动crmId和fossId以及枚举类型
			List<Object> paramList = new ArrayList<Object>();
			// 活动的crmId
			paramList.add(markActivityCrmId);
			// 活动的fossId
			paramList.add(activiteId);

			if (!CollectionUtils.isEmpty(firstTradesList)) {
				// 第一行业
				paramList.add(ComnConst.FIRST_TRADES_MARK_ACTIVITY);
				this.processingEnumeratedList(firstTradesList, paramList);
				paramList.remove(paramList.size()-1);
			} 
			if (!CollectionUtils.isEmpty(secondeTradesList)) {
				// 第二行业
				paramList.add(ComnConst.SECOND_TRADES_MARK_ACTIVITY);
				this.processingEnumeratedList(secondeTradesList, paramList);
				paramList.remove(paramList.size()-1);
			} 
			if (!CollectionUtils.isEmpty(goodNameList)) {
				// 开单品名
				paramList.add(ComnConst.GOODS_NAME_MARK_ACTIVITY);
				this.processingEnumeratedList(goodNameList, paramList);
				paramList.remove(paramList.size()-1);
			}
			if (!CollectionUtils.isEmpty(orderResList)) {
				// 订单来源
				paramList.add(ComnConst.ORDER_RESOURCE_MARK_ACTIVITY);
				this.processingEnumeratedList(orderResList, paramList);
				paramList.remove(paramList.size()-1);
			} 
			if (!CollectionUtils.isEmpty(productList)) {
				// 产品类型
				paramList.add(ComnConst.PRODUCT_TYPE_MARK_ACTIVITY);
				this.processingEnumeratedList(productList, paramList);
				paramList.remove(paramList.size()-1);
			}
			response.setErrorMsg(null);
			response.setFid(markActivityCrmId);
			response.setResult(Boolean.TRUE);
			LOGGER.info("FOSS receive mark activity message successfully!: FOSS成功接收市场活动信息,同步成功");
		} catch (Exception e) {
			response.setErrorMsg(e.getMessage());
			response.setResult(Boolean.FALSE);
			response.setFid(markActivityCrmId);
			LOGGER.info("FOSS receive mark activity message failure!:FOSS接收市场活动信息失败，同步失败");
			LOGGER.error(e.getMessage(), e);
		}
		return response;
	}

	/**
	 * 处理枚举数据 paramList 存放活动Id以及枚举类型 
	 * auther:wangpeng_078816 
	 * date:2014-4-15
	 * 
	 */
	private void processingEnumeratedList(List<String> list,
			List<Object> paramList) {

		// 活动的crmId
		BigDecimal activityCrmId = (BigDecimal) paramList.get(0);
		// 活动的fossId
		String activityId = (String) paramList.get(1);
		// 枚举类型
		String enumType = (String) paramList.get(2);
		//用于新增的枚举list
		List<MarkMultipleEntity> addMultipleList = new ArrayList<MarkMultipleEntity>();
		
		for (String value : list) {
			// 枚举对象
			MarkMultipleEntity entity = new MarkMultipleEntity();
			entity.setId(UUIDUtils.getUUID());
			entity.setActiviteCrmId(activityCrmId);
			entity.setActiviteId(activityId);
			entity.setEnumType(enumType);
			entity.setValueCode(value);
			entity.setActive(FossConstants.ACTIVE);
			entity.setCreateUser(USER_CODE);
			entity.setModifyUser(USER_CODE);
			entity.setCreateDate(new Date());
			entity.setModifyDate(new Date(NumberConstants.ENDTIME));
			List<MarkMultipleEntity> listEntity = markMultipleService.queryIsExistsMarkActivitiesMultiplieByCrmId(activityCrmId, enumType);
			if(CollectionUtils.isEmpty(listEntity)){
				addMultipleList.add(entity);
			}
		}
		// 判断新增操作的里是否为空，不为空进行相应的操作
		if(CollectionUtils.isNotEmpty(addMultipleList)){
			markMultipleService.addMarkActivitiesMultiple(addMultipleList);
		}
		

	}
	
}
