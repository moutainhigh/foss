package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSONObject;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillInfoService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.pay.api.server.service.IRentCarReportService;
import com.deppon.foss.module.transfer.load.api.server.service.IOutsideVehicleChargeService;
import com.deppon.foss.module.transfer.load.api.shared.vo.OutVehicleAssembleBillAndFeeVo;
import com.deppon.foss.module.settlement.pay.api.shared.dto.RentCarReportDto;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 343451 on 2017/3/1.
 * 增加REST接口给CUBC-TRADE 根据运单号查询运单信息实现类
 */
public class WaybillInfoService implements IWaybillInfoService {

    private Logger logger = LoggerFactory.getLogger(WaybillInfoService.class);
    /**
     * 注入运单管理服务
     */
    private IWaybillManagerService waybillManagerService;
    
    /**
	 * 注入配载单信息，在付款时如果为外请车，需要将明细传递过去
	 * 需求DN201704100011
	 * @author 378375
	 * @date 2017年4月7日 14:43:55
	 */
	private IOutsideVehicleChargeService outsideVehicleChargeService;
	
	/**
	 * 临时租车service
	 * 需求DN201704100011
	 * @author 378375
	 * @date 2017年4月7日 16:57:17
	 */
	private IRentCarReportService rentCarReportService;


    @Override
    public String queryWaybillInfoByNo(String requestJson) {
    	logger.info("CUBC-TRADE的请求参数为："+requestJson);
    	//运单实体
    	WaybillEntity waybillEntity = new WaybillEntity();
		//请求的实体
		waybillEntity = JSONObject.parseObject(requestJson, WaybillEntity.class);
		String str = "";
		if(null == requestJson || null == waybillEntity.getWaybillNo()){
			logger.info("CUBC-TRADE 根据运单号查询运单信息接口--请求运单号为空");
			str = "CUBC-TRADE 根据运单号查询运单信息接口--请求运单号为空";
			return str;
		}
		//查询运单
    	if(StringUtils.equals(waybillEntity.getQueryMark(), SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__WAYBILL)){
    			try {
    				if (null != waybillEntity.getWaybillNo()){
    					waybillEntity = waybillManagerService.queryWaybillBasicByNo(waybillEntity.getWaybillNo());
    					str = JSONObject.toJSONString(waybillEntity);
    				}
    			} catch (Exception e) {
    				logger.info("CUBC-TRADE 根据运单号查询运单信息接口--请求类转换异常",e);
    				str = "CUBC-TRADE 根据运单号查询运单信息接口--请求类转换异常,异常信息为：" + e;
    				return str;
    			}
    		
    		//查询外请车 汽运配载单
    	}else if(StringUtils.equals(waybillEntity.getQueryMark(), SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__ROAD_FREIGHT_STOWAGE)){
    		//配 载单及调整金额信息
        	OutVehicleAssembleBillAndFeeVo respOutFee = new OutVehicleAssembleBillAndFeeVo();
    		try {
				List<String> list = new ArrayList<String>();
				list.add(waybillEntity.getWaybillNo());
				List<OutVehicleAssembleBillAndFeeVo> outList = outsideVehicleChargeService.queryOutVehicleAssembleBillAndFeeVoList(list);
				if(CollectionUtils.isNotEmpty(outList)){
					respOutFee = outList.get(0);
					str = JSONObject.toJSONString(respOutFee);
				}
			} catch (Exception e1) {
				logger.info("调用中转接口获取配载单信息异常："+e1);
				str = "调用中转接口获取配载单信息异常："+e1;
				return str;
			}
    		//查询租车信息
    	}else if(StringUtils.equals(waybillEntity.getQueryMark(), SettlementDictionaryConstants.BILL_PAYABLE__SOURCE_BILL_TYPE__RENTCAR)){
    		//租车信息实体
        	RentCarReportDto dto = new RentCarReportDto();
        	//存放查询编号的集合
        	List<String> billNos = new ArrayList<String>();
        	//租车编号
        	billNos.add(waybillEntity.getWaybillNo());
        	dto.setBillNos(billNos);
        	//查询类型
        	dto.setQueryType("RCB");
    		List<RentCarReportDto> rentList = rentCarReportService.queryRentCarReportForCUBC(dto);
    		//查询结果非空判断
    		if(CollectionUtils.isNotEmpty(rentList)){
    			RentCarReportDto respDto = rentList.get(0);
    			str = JSONObject.toJSONString(respDto);
    		}
    	}else {
    		str = "CUBC-TRADE 查询类型有误，没有<"+waybillEntity.getQueryMark()+">这种类型，请核实！";
		}
    	logger.info("响应给CUBC-TRADE的数据为："+str);
		return str;
    }


    public void setWaybillManagerService(IWaybillManagerService waybillManagerService) {
        this.waybillManagerService = waybillManagerService;
    }


	public void setOutsideVehicleChargeService(
			IOutsideVehicleChargeService outsideVehicleChargeService) {
		this.outsideVehicleChargeService = outsideVehicleChargeService;
	}


	public void setRentCarReportService(IRentCarReportService rentCarReportService) {
		this.rentCarReportService = rentCarReportService;
	}
	
}
