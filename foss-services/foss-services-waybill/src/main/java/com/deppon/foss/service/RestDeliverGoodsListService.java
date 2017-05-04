package com.deppon.foss.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.reponse.DeliverGoodsListReponse;
import com.deppon.foss.module.pickup.waybill.shared.request.DeliverGoodsListQueryRequest;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListVo;
import com.deppon.foss.module.transfer.departure.api.server.service.ITrackingService;
import com.deppon.foss.module.transfer.scheduling.api.shared.dto.GeneralQueryDto;
import com.deppon.foss.util.define.ESBHeaderConstant;
import com.deppon.foss.util.define.FossConstants;

/**
 * 发货清单服务接口
 * @author 272311
 * 2015.08.19
 */
@Controller
@RequestMapping("/waybill/deliverGoodsList")
public class RestDeliverGoodsListService {
	
	private Log logger = LogFactory.getLog(RestDeliverGoodsListService.class);
	//接送货 服务接口
	@Autowired
	private IWaybillService waybillService ;
	
	//中转服务接口
	@Autowired
	private IWaybillSignResultService waybillSignResultService ;
	
	//结算服务接口
	@Autowired
	private ITrackingService trackingService ;
	
	//综合的接口
	@Autowired(required=false)
	private IOrgAdministrativeInfoService orgAdministrativeInfoService ; 

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		// 对于需要转换为Date类型的属性，使用DateEditor进行处理
		binder.registerCustomEditor(Date.class, new DateEditor());
	}
	
	@ResponseBody
	@RequestMapping(value="/getDeliverGoodsList")
	public DeliverGoodsListReponse getDeliverGoodsList(@RequestBody DeliverGoodsListQueryRequest deliverGoodsListQueryRequest ,HttpServletResponse response){
		logger.warn("==============================调用getDeliverGoodsList接口 begin=====================================");
		long begin = System.currentTimeMillis();
		logger.info("请求参数："+deliverGoodsListQueryRequest.toString());
		DeliverGoodsListReponse deliverGoodsListReponse = new DeliverGoodsListReponse() ;
		try {
			Map<String,Object> map = waybillService.queryWaybillDeliverGoodsList(deliverGoodsListQueryRequest, deliverGoodsListQueryRequest.getStart(), deliverGoodsListQueryRequest.getLimit());
			int count = (Integer) map.get("count");
			logger.info("返回结果 查询到的记录总数："+count);
			if(count > 0){
				List<DeliverGoodsListVo> deliverGoodsListVos = (List<DeliverGoodsListVo>) map.get("resultList");
				deliverGoodsListReponse.setDeliverGoodsListVoList(deliverGoodsListVos);
				deliverGoodsListReponse.setTotalCount(count);
				for(DeliverGoodsListVo vo : deliverGoodsListVos){
					//结算 提供 ‘签收人’和‘签收时间’
					WaybillSignResultEntity entity = new WaybillSignResultEntity(vo.getWaybillNo(), FossConstants.ACTIVE);
					WaybillSignResultEntity waybillSignResultEntity = waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
					if(waybillSignResultEntity != null){
						vo.setDeliverymanName(waybillSignResultEntity.getDeliverymanName());//签收人
						vo.setSignTime(waybillSignResultEntity.getSignTime());//签收时间
					}
					
					//中转 提供 ‘货物状态’和‘货物当前所在城市’
					//DEFECT-16407 311396 wwb 修改为如果签收表里有数据且不是部分签收就不再查询中转 2016年10月15日12:17:21
					if(StringUtils.isEmpty(vo.getGoodsStatus()) || SignConstants.SIGN_STATUS_PARTIAL.equals(vo.getGoodsStatus())){
						List<GeneralQueryDto> generalQueryDtos = trackingService.queryWaybillStatusByWaybillNoForPkp(vo.getWaybillNo());
						if(CollectionUtils.isNotEmpty(generalQueryDtos)){
							GeneralQueryDto generalQueryDto = generalQueryDtos.get(0);
							vo.setGoodsStatus(generalQueryDto.getAction());//
							vo.setGoodsCurrentCity(orgAdministrativeInfoService.queryCityNameByOrgName(generalQueryDto.getNowPosition()));//从综合再次调用
						}
					}
					
					
				}//for end
				//
				deliverGoodsListReponse.setDeliverGoodsListVoList(deliverGoodsListVos);
			}
			
			response.setHeader(ESBHeaderConstant.RESULTCODE, "1");
			logger.info("耗时："+(System.currentTimeMillis()-begin)+";总条数："+count);
			logger.warn("==============================调用getDeliverGoodsList接口 end=====================================");
			return deliverGoodsListReponse ;
		} catch (Exception e) {
			logger.warn("调用getDeliverGoodsList接口 异常结束");
			logger.error(e.getMessage(), e);
			deliverGoodsListReponse.setSuccess(FossConstants.NO);
			deliverGoodsListReponse.setErrorMsg(e.getMessage());
			response.setHeader(ESBHeaderConstant.RESULTCODE, "0");
			return deliverGoodsListReponse;
		}
		
		
		
	}

}
