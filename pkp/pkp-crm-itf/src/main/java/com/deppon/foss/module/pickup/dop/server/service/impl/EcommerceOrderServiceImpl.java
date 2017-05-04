package com.deppon.foss.module.pickup.dop.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillPendingService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRelateDetailEntityService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.EcomWaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillComponentEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.EcomOrderDetailDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * dop交互信息
 * @author 270293 
 *
 */
@Controller
public class EcommerceOrderServiceImpl {
	private static final Logger LOGGER = LoggerFactory.getLogger(EcommerceOrderServiceImpl.class);
	
	@Resource
	private IWaybillRelateDetailEntityService waybillRelateDetailEntityService;
	
	@Resource
	private IWaybillPendingService waybillPendingService;
	
	public static final String ESB_RESULT_CODE = "ESB-ResultCode";
	//失败
	public static final String ESB_RESULT_FIAL_CODE = "00000";
	//成功
	public static final String ESB_RESULT_SUCCESS_CODE = "10000";
	
	
	@RequestMapping(value="/addEcommerceOrderDetail",method =RequestMethod.POST)
	@ResponseBody
	public WaybillComponentEntity addEcommerceOrderDetail(@RequestBody EcomOrderDetailDto ecomWaybillRelateDto, HttpServletResponse response){
		WaybillComponentEntity waybillComponent = new WaybillComponentEntity();
		try {
			response.setHeader(ESB_RESULT_CODE, String.valueOf(1));
			int count = 0;
			if(ecomWaybillRelateDto == null){
				//消息代码查询失败为“00000”
				waybillComponent.setMessage_code(ESB_RESULT_FIAL_CODE);
				//消息代码说明
				waybillComponent.setMessage_detail("Get information failed,information is null");
				//返回记录的条数
				waybillComponent.setCount(0);
				//处理明细
				waybillComponent.setDetail(null);
				return waybillComponent;
			}
			LOGGER.info("addEcommerceOrderDetail start : "+ ReflectionToStringBuilder.toString(ecomWaybillRelateDto));
			//获取对应的数据详情
			//原始订单
			String orignalOrderNo = ecomWaybillRelateDto.getOriginalNumber();
			//运单号
			String waybillNo = ecomWaybillRelateDto.getWaybillNumber();
			//判定数据是否为空
			if(StringUtils.isEmpty(waybillNo)){
				//消息代码查询失败为“00000”
				waybillComponent.setMessage_code(ESB_RESULT_FIAL_CODE);
				//消息代码说明
				waybillComponent.setMessage_detail("waybillNo is null,Please check waybillInfo");
				//返回记录的条数
				waybillComponent.setCount(0);
				//处理明细
				waybillComponent.setDetail(null);
				return waybillComponent;
			}
			//主要是配合vas系统把普通电子运单改成子母弹的问题
			//如果该单已经是待激活状态，把该运单作废，从新作为子母件
			if(FossConstants.YES.equals(ecomWaybillRelateDto.getIsExisted())){
			WaybillPendingEntity waybillPending = waybillPendingService.queryPendingByNo(waybillNo);
			//WaybillEntity waybill = waybillManagerService.queryWaybillBasicByNo(waybillNo);
			if(waybillPending != null){
				waybillPendingService.updatePendingActive(waybillPending.getId());
			}
			}
			//封装查询参数
			Map<String, Object> params = new HashMap<String, Object>();
			//params.put("active", FossConstants.YES);
			params.put("orignalOrderNo", orignalOrderNo);
			params.put("waybillNo", waybillNo);
			List<EcomWaybillRelateEntity> exisistWaybillRelateDetailList = 
					waybillRelateDetailEntityService.queryAllEwaybillRelateByEcomWaybillRelate(params);
			
			Date nowDate = new Date();
			//根据原始订单号查询出来的数据为空
			EcomWaybillRelateEntity ecomWaybillRelateEntity = null;
			if(CollectionUtils.isEmpty(exisistWaybillRelateDetailList)&&StringUtils.isEmpty(ecomWaybillRelateDto.getModifyType())){
				ecomWaybillRelateEntity = new EcomWaybillRelateEntity();
				ecomWaybillRelateEntity.setOrignalOrderNo(orignalOrderNo);
				ecomWaybillRelateEntity.setWaybillNo(waybillNo);
				//创建时间修改时间
				ecomWaybillRelateEntity.setCreateTime(nowDate);
				ecomWaybillRelateEntity.setModifyTime(nowDate);
				//ID
				ecomWaybillRelateEntity.setId(UUIDUtils.getUUID());
				//是否子母件
				ecomWaybillRelateEntity.setIsPicPackage(ecomWaybillRelateDto.getIsPicPackage());
				//运单类型
				ecomWaybillRelateEntity.setWaybillType(WaybillConstants.EWAYBILL);
				//重量
				if(ecomWaybillRelateDto.getTotalWeight() != null){
					ecomWaybillRelateEntity.setWeight(BigDecimal.valueOf(ecomWaybillRelateDto.getTotalWeight()));
				}
				//体积
				if(ecomWaybillRelateDto.getTotalVolume() != null){
					ecomWaybillRelateEntity.setVolume(BigDecimal.valueOf(ecomWaybillRelateDto.getTotalVolume()));
				}
				//件数
				ecomWaybillRelateEntity.setPiece(Integer.valueOf(1));
				//设置是否有效
				ecomWaybillRelateEntity.setActive(FossConstants.YES);
				
				 //暂时写dop只是表示数据来源于dop？
				ecomWaybillRelateEntity.setCreateOrgCode("DOP");
				ecomWaybillRelateEntity.setCreateUserCode("DOP");
				ecomWaybillRelateEntity.setModifyOrgCode("DOP");
				ecomWaybillRelateEntity.setModifyUserCode("DOP");
				waybillRelateDetailEntityService.insertEcomWaybillRelateSelective(ecomWaybillRelateEntity);
			}else if("delete".equals(ecomWaybillRelateDto.getModifyType())){
				 ecomWaybillRelateEntity =new EcomWaybillRelateEntity();
				 ecomWaybillRelateEntity.setActive(FossConstants.NO);
				 ecomWaybillRelateEntity.setModifyTime(nowDate);
				 ecomWaybillRelateEntity.setWaybillNo(waybillNo);
				 waybillRelateDetailEntityService.updateEcomWaybillRelateByPrimaryKeySelective(ecomWaybillRelateEntity);
				//为了waybill_no作为主键，删掉当前记录
				// waybillRelateDetailEntityService.deleteEcomWaybillRelateByPrimaryKey(waybillNo);
			}else if("modify".equals(ecomWaybillRelateDto.getModifyType())||StringUtils.isEmpty(ecomWaybillRelateDto.getModifyType())){
				ecomWaybillRelateEntity = new EcomWaybillRelateEntity();
				//创建时间修改时间
				ecomWaybillRelateEntity.setModifyTime(nowDate);
				//是否子母件
				ecomWaybillRelateEntity.setIsPicPackage(ecomWaybillRelateDto.getIsPicPackage());
				//运单类型
				ecomWaybillRelateEntity.setWaybillType(WaybillConstants.EWAYBILL);
				ecomWaybillRelateEntity.setWaybillNo(waybillNo);
				//重量
				if(ecomWaybillRelateDto.getTotalWeight() != null){
					ecomWaybillRelateEntity.setWeight(BigDecimal.valueOf(ecomWaybillRelateDto.getTotalWeight()));
				}
				//体积
				if(ecomWaybillRelateDto.getTotalVolume() != null){
					ecomWaybillRelateEntity.setVolume(BigDecimal.valueOf(ecomWaybillRelateDto.getTotalVolume()));
				}
				//设置是否有效
				ecomWaybillRelateEntity.setActive(FossConstants.YES);
				waybillRelateDetailEntityService.updateEcomWaybillRelateByPrimaryKeySelective(ecomWaybillRelateEntity);
			}
			//消息代码成功为“10000”
			waybillComponent.setMessage_code(ESB_RESULT_SUCCESS_CODE);
			//消息代码说明
			waybillComponent.setMessage_detail("Get information succeed");
			//返回记录的条数
			waybillComponent.setCount(count);
			//消息代码查询失败为“00000”
			//waybillComponent.setMessage_code(ESB_RESULT_FIAL_CODE);
			//处理明细
			waybillComponent.setDetail(null);
		} catch (Exception e) {
			response.setHeader(ESB_RESULT_CODE, String.valueOf(0));
			if(e.getMessage().indexOf("ORA-00001")>0){
				waybillComponent.setMessage_code(ESB_RESULT_SUCCESS_CODE);
				//消息代码说明
				waybillComponent.setMessage_detail("Get information succeed");
				//返回记录的条数
				waybillComponent.setCount(1);
			}else{
				waybillComponent.setMessage_code(ESB_RESULT_FIAL_CODE);
				//消息代码说明
				waybillComponent.setMessage_detail("Get information fialed");
				//返回记录的条数
				waybillComponent.setCount(0);
			}
			LOGGER.error(e.getMessage(),e);
			//throw new RuntimeException(e);
			return waybillComponent;
		
		}
		return waybillComponent;
	}
	
	public void setWaybillRelateDetailEntityService(IWaybillRelateDetailEntityService waybillRelateDetailEntityService) {
		this.waybillRelateDetailEntityService = waybillRelateDetailEntityService;
	}
	
	/**
	 * @param waybillPendingService the waybillPendingService to set
	 */
	public void setWaybillPendingService(IWaybillPendingService waybillPendingService) {
		this.waybillPendingService = waybillPendingService;
	}
}