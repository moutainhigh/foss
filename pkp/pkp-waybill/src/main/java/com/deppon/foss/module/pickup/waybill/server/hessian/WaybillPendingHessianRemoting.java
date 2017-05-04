/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-waybill
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/waybill/server/hessian/WaybillPendingHessianRemoting.java
 * 
 * FILE NAME        	: WaybillPendingHessianRemoting.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.waybill.server.hessian;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.deploy.hessian.annotation.Remote;
import com.deppon.foss.framework.server.web.message.IMessageBundle;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IFreightRouteService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISalesBillingGroupService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.FreightRouteEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.FreightRouteLineDto;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.api.server.service.IIntelligenceTimeRecordService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPicturePushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureSendSmsEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPushMessageEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.ClientEWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillSalesDepartDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillOtherChargeDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto;
import com.deppon.foss.module.pickup.waybill.shared.exception.WaybillValidateException;
import com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillPendingHessianRemoting;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 
 * 运单预处理接口
 * 
 * @author 026113-foss-linwensheng
 * @date 2012-10-22 下午7:32:34
 */
@Remote()
public class WaybillPendingHessianRemoting implements IWaybillPendingHessianRemoting {

	private static final Logger LOGGER = LoggerFactory.getLogger(WaybillPendingHessianRemoting.class);
	@Resource
    IWaybillPendingService waybillPendingService;
    
    @Resource
    private IFreightRouteService freightRouteService ;
    @Resource
	private ISalesBillingGroupService salesBillingGroupService;
    @Resource
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    @Resource
    private IConfigurationParamsService configurationParamsService;
    /**
	 * 业务互斥锁服务  提供业务互斥锁服务接口
	 */
	private IBusinessLockService businessLockService;
    
	public IBusinessLockService getBusinessLockService() {
		return businessLockService;
	}

	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/**
	 * 国际化信息
	 * 
	 * @autor 278328
	 * @date 2015-05-17 上午8:59:57
	 */
    @Resource
	private IMessageBundle messageBundle; 
    
    /**
	 * @项目：智能开单项目
	 * @功能：定义时间统计service
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-27上午09:00
	 */
    @Resource
	IIntelligenceTimeRecordService intelligenceTimeRecordService;
	
	
    public IOrgAdministrativeInfoService getOrgAdministrativeInfoService() {
		return orgAdministrativeInfoService;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	private Log logger = LogFactory.getLog(WaybillPendingHessianRemoting.class);
    
    public ISalesBillingGroupService getSalesBillingGroupService() {
		return salesBillingGroupService;
	}

	public void setSalesBillingGroupService(
			ISalesBillingGroupService salesBillingGroupService) {
		this.salesBillingGroupService = salesBillingGroupService;
	}

	/** 
	 * 运单暂存
	 * @author 026123-foss-lifengteng
	 * @date 2012-11-3 下午5:40:41
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillPendingHessianRemoting#addWaybill(com.deppon.foss.module.pickup.waybill.shared.dto.WaybillPendingDto)
	 */
	@Override
	public void saveWaybill(WaybillPendingDto waybillDto) {
		//获得当前用户信息
		CurrentInfo currentInfo = FossUserContext.getCurrentInfo();
		waybillDto.setCurrentInfo(currentInfo);
		String oldPendingType = waybillDto.getWaybillPending().getPendingTypeByDb();
		String guiTitleName = waybillDto.getGuiTitleName();
		if(guiTitleName != null && "图片开单".equals(guiTitleName)){
			try {
				waybillPendingService.addPendingWaybill(waybillDto);
			} catch (Exception e) {
				String waybillNo = waybillDto.getWaybillPending().getWaybillNo();
				WaybillPictureEntity entity = new WaybillPictureEntity();
				entity.setWaybillNo(waybillNo);
				entity.setActive(FossConstants.ACTIVE);
				
				String errorMsg  = e.getMessage();
				if(e instanceof BusinessException){
					BusinessException bise =	((BusinessException)e);
					String errorCode = bise.getErrorCode();
					try{
						errorMsg = messageBundle.getMessage(errorCode, bise.getErrorArguments());
					}catch(Exception bundle){
						LOGGER.info(bundle.getMessage());
					}
				}
				
				if(errorMsg == null || "".equals(errorMsg)){
					errorMsg = e.getMessage();
				}
				
				//WaybillPictureEntity oldPictureEntity =  queryWaybillPictureByEntity(entity);
				WaybillPictureEntity newEntity = new WaybillPictureEntity();
				newEntity.setWaybillNo(waybillNo);
				newEntity.setActive(FossConstants.ACTIVE);
				if(WaybillConstants.WAYBILL_PICTURE_TYPE_DISTRIBUTED.equals(oldPendingType)){
					//获取备注信息  防止暂存图片修复信息 没有及时更新备注信息
					entity.setPendgingType(WaybillConstants.WAYBILL_PICTURE_TYPE_PENDING);
				}else{
					//获取备注信息  防止暂存图片修复信息 没有及时更新备注信息
					entity.setPendgingType(oldPendingType);
				}
				
				StringBuffer sb = new StringBuffer();
				/*if(oldPictureEntity != null){
					String remark = oldPictureEntity.getRemark();
					if(remark != null){
						sb.append(remark);
						sb.append("/n");
					}
				}*/
				sb.append(errorMsg).append("  ").append(new Date());
				entity.setRemark(sb.toString());
				//更新图片开单表备注信息
				waybillPendingService.updatePictureWaybillByWaybillno(entity);
				
			}
			
		}else{
			waybillPendingService.addPendingWaybill(waybillDto);
		}
		/**
		 * @项目：智能开单项目
		 * @功能：保存IntelligenceBillTimeGather类
		 * @author:218371-foss-zhaoyanjun
		 * @date:2016-05-19下午18:08
		 */
		if(waybillDto.getIbtg()!=null){
			addIntelligenceBillTime(waybillDto);
		}
	}
	
	/**
	 * @项目：智能开单项目
	 * @功能：提交耗时时间任务
	 * @author:218371-foss-zhaoyanjun
	 * @date:2016-05-19下午18:19
	 */
	private void addIntelligenceBillTime(WaybillPendingDto waybillDto) {
		// TODO Auto-generated method stub
		try {
			intelligenceTimeRecordService.insertTimeRecord(waybillDto.getIbtg());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
     * <p>
     * (查询运单待处理表)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	@Override
	public List<WaybillPendingEntity> queryPending(
			WaybillPendingDto waybillPendingDto) {
		 
		return waybillPendingService.queryPendingNoExpress(waybillPendingDto);
	}
	
	public List<WaybillPendingEntity> queryPendingExpress(
			WaybillPendingDto waybillPendingDto) {
		 
		List<WaybillPendingEntity> pends= waybillPendingService.queryPending(waybillPendingDto);
		
		if(CollectionUtils.isNotEmpty(pends)){
			
			for(WaybillPendingEntity pend:pends){
				if(pend.getCustomerPickupOrgCode() !=null && StringUtils.isNotEmpty(pend.getCustomerPickupOrgCode())){
					String targetOrgName=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pend.getCustomerPickupOrgCode()).getName();
					pend.setTargetOrgName(targetOrgName);
				}
				if(pend.getCreateOrgCode() !=null && StringUtils.isNotEmpty(pend.getCreateOrgCode())){
					String startName=orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(pend.getCreateOrgCode()).getCityName();
					pend.setStartName(startName);
				}
				//出发部门
				String rcode =pend.getReceiveOrgCode();
				//收货部门
				String ccode =pend.getCustomerPickupOrgCode();
				// 产品
				String pcode = pend.getProductCode();
				//开单时间
				Date bdate = pend.getBillTime();
				//查找走货路径				
				List<FreightRouteLineDto> lines=null;
				try{
					lines=freightRouteService.queryEnhanceFreightRouteBySourceTarget(rcode,ccode,pcode,bdate);	
				}catch(Exception e){
					System.out.println("获取走货路径失败！");
				}
				if(CollectionUtils.isNotEmpty(lines)){
					//出发外场
				    String  oneTargetCode =null;
				    //最终外场
				    String lastSourceCode=null;
					if(lines.size()>=2){
						for(int i=0;i<=lines.size();i++){
						  if(i==0){
							  oneTargetCode=  lines.get(i).getTargetCode();
						  }
//						  if(i==1){
						  if(i==lines.size()-1){
//							  twoTargetCode=  lines.get(i).getTargetCode();
							  lastSourceCode= lines.get(i).getSourceCode();
							  FreightRouteEntity freightRoute = new FreightRouteEntity();
							  freightRoute.setOrginalOrganizationCode(oneTargetCode);
//							  freightRoute.setDestinationOrganizationCode(twoTargetCode);
							  freightRoute.setDestinationOrganizationCode(lastSourceCode);
							  freightRoute.setTransType(pcode);
							  freightRoute.setActive(FossConstants.YES);
							  //查询时效
							  List<FreightRouteEntity> fys= freightRouteService.querySimpleFreightRouteListByCondition(freightRoute,0,NumberConstants.NUMBER_100);
							  
							  if(CollectionUtils.isNotEmpty(fys)){
								  //时效大于13个小时
								  FreightRouteEntity route = fys.get(0);
								  Long aging=route.getAging();
								  if(aging!=null && aging / NumberConstants.NUMBER_1000 > NumberConstants.NUMBER_13){
									  pend.setIsBig13(FossConstants.YES);
								  }else{
									  pend.setIsBig13(FossConstants.NO);
								  }
							  }
						  }
						 
						}
				 }
			 }
				
			}
		}
		return pends ;
	}
	
	
	public IWaybillPendingService getWaybillPendingService() {
		return waybillPendingService;
	}

	public void setWaybillPendingService(
			IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}
	
	public IConfigurationParamsService getConfigurationParamsService() {
		return configurationParamsService;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	/**
     * <p>
     * 更改运单状态PENDING
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	@Override
	public int updatePendingActive(String id) {
		return waybillPendingService.updatePendingActive(id);
	}
	
	/**
     * <p>
     * (按id查询运单待处理表)
     * </p>
     * @author 105089-FOSS-yangtong
     * @date 2012-10-16 下午04:22:42
     * @return
     * @see
     */
	@Override
	public WaybillPendingEntity queryPendingById(String id) {
		return waybillPendingService.queryPendingById(id);
	}

	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在Pending
	 * </p>
	 * 
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
	@Override
	public boolean isPendingExsits(String mixNo) {
		return waybillPendingService.isPendingExsits(mixNo);
	}
	
	
	/**
	 * 
	 * <p>
	 * 通过运单号/订单号判断运单是否存在Pending
	 * </p>
	 * 
	 * @author foss-yangtong
	 * @date 2012-10-30 下午7:44:25
	 * @return
	 * @see 
	 */
	public boolean isPendingExsitsAndOrderNo(String mixNo, String orderNo) {
		return waybillPendingService.isPendingExsitsAndOrderNo(mixNo, orderNo);
	}
	
	
	/**
	 * 根据运单号查询待处理运单基本信息
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 上午8:39:53
	 */
	@Override
	public WaybillPendingEntity queryWaybillPendingByNo(String waybillNo){
		return waybillPendingService.queryPendingByNo(waybillNo);
	}

	/**
	 * 根据运单号查询待处理运单dto
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-6 下午3:31:57
	 */
	@Override
	public WaybillPendingDto queryWaybillPendingDtoByNo(String waybillNo) {
		return waybillPendingService.queryPendingWaybillByNo(waybillNo);
	}

	/**
	 * 根据运单号查询费用明细中的其它费用 
	 * @author 026123-foss-lifengteng
	 * @date 2012-12-15 下午3:03:45
	 */
	@Override
	public List<WaybillOtherChargeDto> queryOtherChargeByNo(String waybillNo) {
		return waybillPendingService.queryOtherChargeByNo(waybillNo);
	}
	
	@Override
	public WaybillPictureEntity getPictureWaybill(WaybillPictureEntity wpe) {
		//登陆用户所在部门的开单组
		/*List<SalesBillingGroupEntity> list1 = salesBillingGroupService.queryBillingGroupListBySaleDepCode(dept.getCode());
		//判断登陆用户所在部门是否是开单组
		if(list1 != null && list1.size() != 0){
			List<SalesBillingGroupEntity> list = null;
			List<SalesBillingGroupEntity> list2 = new ArrayList<SalesBillingGroupEntity>();
			for(int i = 0 ; i < list1.size() ; i++){
				list = salesBillingGroupService.queryBillingGroupListByBillingGroupCode(list1.get(i).getBillingGroupCode());
				if(list != null && list.size() != 0){
					for(int j = 0 ; j < list.size() ; j++){
						list2.add(list.get(i));
					}
				}
			}
			if(list2 != null && list2.size() != 0){
				Map<String ,Object> clause = new HashMap<String,Object>();
				clause.put("operator", user.getUserName());
				clause.put("dept", list2);
				return waybillPendingService.getPictureWaybill(clause);
			}
		}*/
		/*List<SalesBillingGroupEntity> list = salesBillingGroupService.queryBillingGroupListByBillingGroupCode(dept.getCode());
		if(list != null && list.size() != 0){
			Map<String ,Object> clause = new HashMap<String,Object>();
			clause.put("operator", user.getUserName());
			clause.put("dept", list);
			return waybillPendingService.getPictureWaybill(clause);
		}*/
		
		/**
		 * 在此处为分单逻辑加锁，最大限度防止一单图片运单分到两个开单员
		 * 352676
		 * 2017年4月21日21:00:09
		 */
		//查询配置锁
		ConfigurationParamsEntity entity = configurationParamsService
				.queryConfigurationParamsByOrgCode(
						DictionaryConstants.SYSTEM_CONFIG_PARM__PKP,
						ConfigurationParamsConstants.PKP_FOSS_PIC_WAYBILL_PENDING_LOCK,
						wpe.getBelongOrgCode());
		String lock=entity.getConfValue();
		if(StringUtil.equals("1", lock)){
			//如果处于加锁状态，则加锁保证异步
			WaybillPictureEntity waybillPictureEntity=getPictureWaybillInLock(wpe);
			return waybillPictureEntity;
		}else if(StringUtil.equals("0", lock)){
			//如果处于未加锁状态，按照原来逻辑走
			return waybillPendingService.getPictureWaybill(wpe);
		}else{
			//如果处于其他状态，则为了不影响开单时效，按照原来逻辑走
			return waybillPendingService.getPictureWaybill(wpe);
			//提示配置异常
			//throw new WaybillValidateException("【图片开单分单锁开关】设置参数有误！请联系管理员。");
		}
		
	}

	@Override
	public int getPictureWaybillCount(WaybillPictureEntity entity) {
		return waybillPendingService.getPictureWaybillCount(entity);
	}

	@Override
	public int updateWaybillPicture(WaybillPictureEntity wpe) {
		logger.info("进入 updateWaybillPicture 方法") ;
		return waybillPendingService.updateWaybillPicture(wpe);
	}
	@Override
	public int saveWaybillPushMessage(WaybillPushMessageEntity e) {
		return waybillPendingService.saveWaybillPushMessage(e);
	}
	@Override
	public int updatePictureWaybillByWaybillno(
			WaybillPictureEntity waybillPictureEntity) {
		return waybillPendingService.updatePictureWaybillByWaybillno(waybillPictureEntity);
	}

	@Override
	public WaybillPictureEntity queryWaybillPictureByEntity(
			WaybillPictureEntity entity) {
		return waybillPendingService.queryWaybillPictureByEntity(entity);
	}
	
	public List<WaybillPictureEntity> queryWaybillPicturesByEntity(
			WaybillPictureEntity entity) {
		return waybillPendingService.queryWaybillPicturesByEntity(entity);
	}

@Override
	public void addWaybillPicturePushMessage(
			WaybillPicturePushMessageEntity entity) {
		waybillPendingService.insertWaybillPicturePushMessage(entity);
	}	/**
	 * <p>根据条件进行电子面单数据的查询</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-12-31 11:15:50
	 * @param pendingDto
	 * @return
	 */
	@Override
	public List<EWaybillSalesDepartDto> queryEWaybillSalesDepart(ClientEWaybillConditionDto ewaybillConditionDto, String type) {
		return waybillPendingService.queryEWaybillSalesDepart(ewaybillConditionDto, type);
	}
	
	/**
	 * 查询暂存运单信息表数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-1-22 15:45:34
	 * @param maps
	 * @return
	 */
	@Override
	public WaybillPendingEntity queryBasicWaybillPendingData(WaybillPendingDto waybillPendingDto) {
		return waybillPendingService.queryBasicWaybillPendingData(waybillPendingDto);
	}	@Override
	public void addWaybillPictureSendMessage(WaybillPictureSendSmsEntity entity) {
		waybillPendingService.insertWaybillPictureSendMessage(entity);
	}
	@Override
	public void deleteByWaybillNo(String waybillNo) {
		waybillPendingService.deleteByWaybillNo(waybillNo);
	}
	@Override
	public WaybillPendingEntity queryWaybillByWaybillNo(String waybillNo){
		return waybillPendingService.queryWaybillByWaybillNo(waybillNo);
	}
	@Override
	public void deleteByWaybillNos(List<String> waybillNos) {
		waybillPendingService.deleteByWaybillNos(waybillNos);
	}

	@Override
	public Date discontinueWaybillPicture(WaybillPictureEntity entity) {
		logger.info("进入 discontinueWaybillPicture ");
		//
		Date  date = new Date();
		logger.debug("服务端产生的日期date = "+date);
		entity.setModifyTime(date);
		entity.setEndBillTime(date);
		if(FossUserContext.getCurrentUser()!=null){
			entity.setModifyUserCode(FossUserContext.getCurrentUser().getUserName());
		}
		updateWaybillPicture(entity);
		
		WaybillEntity waybillEntity = new WaybillEntity();
		waybillEntity.setWaybillNo(entity.getWaybillNo());
		waybillEntity.setActive(FossConstants.INACTIVE);
		waybillEntity.setPendingType(WaybillConstants.WAYBILL_PICTURE_TYPE_DISCONTINUE);
		
		//更新T_SRV_WAYBILL 表，设置active 为N,pendingType:DISCONTINUE
		waybillPendingService.updateWaybillActiveByWaybillNo(waybillEntity);
		//更新t_srv_waybill_pending表，设置active为N
		waybillPendingService.updatePendingActiveByNo(entity.getWaybillNo());
		
		return date;
	}

	public IIntelligenceTimeRecordService getIntelligenceTimeRecordService() {
		return intelligenceTimeRecordService;
	}

	public void setIntelligenceTimeRecordService(
			IIntelligenceTimeRecordService intelligenceTimeRecordService) {
		this.intelligenceTimeRecordService = intelligenceTimeRecordService;
	}
	/**
	 * 图片开单查询本地补录运单数
	 * @author Foss-352676-YUANHB 
	 * @date 2017-3-9 下午9:53:09
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillPendingHessianRemoting#getPictureWaybillLocalCount(com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPictureEntity)
	 */
	@Override
	public int getPictureWaybillLocalCount(WaybillPictureEntity entity) {
		
		return waybillPendingService.getPictureWaybillLocalCount(entity);
	}
	/**
	 * 图片开单查询全国待补录运单数
	 * @author Foss-352676-YUANHB 
	 * @date 2017-3-9 下午9:53:01
	 * @return 
	 * @see com.deppon.foss.module.pickup.waybill.shared.hessian.IWaybillPendingHessianRemoting#getPictureWaybillAllCount()
	 */
	@Override
	public int getPictureWaybillAllCount(WaybillPictureEntity entity) {
		
		return waybillPendingService.getPictureWaybillAllCount(entity);
	}

	/**
	 * 查询是否有可补录的订单
	 * by 352676
	 */
	@Override
	public WaybillPictureEntity getPictureWaybillIsExit(WaybillPictureEntity wpe) {
		return waybillPendingService.getPictureWaybillIsExit(wpe);
	}

	/*public IMessageBundle getMessageBundle() {
		return messageBundle;
	}

	public void setMessageBundle(IMessageBundle messageBundle) {
		this.messageBundle = messageBundle;
	}*/
	
	/**
	 * 图片开单分单逻辑加锁处理
	 * @author Foss-352676-YUANHB
	 * @date 2017年4月21日21:53:59
	 * @param wpe
	 * @return
	 */
	public  WaybillPictureEntity getPictureWaybillInLock(WaybillPictureEntity wpe){
		WaybillPictureEntity waybillPictureEntity=waybillPendingService.getPictureWaybillInLock(wpe);
		return waybillPictureEntity;
	}
}