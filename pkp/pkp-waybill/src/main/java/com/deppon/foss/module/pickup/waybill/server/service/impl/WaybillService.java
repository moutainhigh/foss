package com.deppon.foss.module.pickup.waybill.server.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICustomerCircleRelationNewService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerCircleNewDto;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.waybill.api.server.dao.IWaybillDao;
import com.deppon.foss.module.pickup.waybill.api.server.service.IExpWaybillInfoToPtpService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillInfoToPtpService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillRfcService;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.define.WaybillConstants;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.PtpWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillStockStatusDto;
import com.deppon.foss.module.pickup.waybill.shared.request.CubcQueryTotalAmountRequest;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListQueryVo;
import com.deppon.foss.module.pickup.waybill.shared.vo.DeliverGoodsListVo;
import com.deppon.foss.util.define.FossConstants;
/**
 * IWaybillService 接口实现类
 * @author 272311
 *
 */
@Transactional
@Service
public class WaybillService implements IWaybillService {
	
	private static final int NUMBER_2000 = 2000;

	private Logger logger = LoggerFactory.getLogger(WaybillService.class);
	
	@Resource
	private IWaybillDao waybillDao ;
	
	@Resource
	private IExpWaybillInfoToPtpService expWaybillInfoToPtpService;
	
	@Resource
	private ISaleDepartmentService saleDepartmentService;
	
	@Resource
	private IWaybillInfoToPtpService WaybillInfoToPtpService;
	
	@Resource
	private IWaybillRfcService waybillRfcService;
	
	/**
	 * 异地调货调用综合接口查询客户，合同相关的信息
	 */
	private ICustomerCircleRelationNewService customerCircleRelationNewService;
	
	/**
	 * 系统配置项服务
	 * 提供与系统配置项相关的服务接口
	 */
	@Resource
	private IConfigurationParamsService configurationParamsService;
	
	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}

	public void setWaybillDao(IWaybillDao waybillDao) {
		this.waybillDao = waybillDao;
	}
	
	public void setWaybillInfoToPtpService(
			IWaybillInfoToPtpService waybillInfoToPtpService) {
		WaybillInfoToPtpService = waybillInfoToPtpService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	public void setExpWaybillInfoToPtpService(
			IExpWaybillInfoToPtpService expWaybillInfoToPtpService) {
		this.expWaybillInfoToPtpService = expWaybillInfoToPtpService;
	}
	
	public void setWaybillRfcService(IWaybillRfcService waybillRfcService) {
		this.waybillRfcService = waybillRfcService;
	}

	@Override
	public int queryWaybillDeliverGoodsListCount(
			DeliverGoodsListQueryVo deliverGoodsListQueryVo) {
		logger.info("进入 queryWaybillDeliverGoodsListCount");
		if(deliverGoodsListQueryVo != null){
			return waybillDao.queryWaybillDeliverGoodsListCount(deliverGoodsListQueryVo);
		}else {
			return 0 ;
		}
		
	}

	@Override
	public Map<String, Object> queryWaybillDeliverGoodsList(
			DeliverGoodsListQueryVo deliverGoodsListQueryVo, int start,
			int limit) {
		logger.info("进入 queryWaybillDeliverGoodsList");
		Map<String, Object> map = new HashMap<String, Object>();
		if (deliverGoodsListQueryVo != null) {
			if(StringUtils.isBlank(deliverGoodsListQueryVo.getDeliveryCustomerCode())){
				logger.info("客户编码为空");
				throw new BusinessException("客户编码不能为空");
			}
			if(CollectionUtils.isNotEmpty(deliverGoodsListQueryVo.getWaybillNoList())){
				deliverGoodsListQueryVo.setBeginMonthTime(null);
				deliverGoodsListQueryVo.setEndMonthTime(null);
				deliverGoodsListQueryVo.setStartTime(null);
				deliverGoodsListQueryVo.setEndTime(null);
			}else if(deliverGoodsListQueryVo.getBeginMonthTime()==null&&deliverGoodsListQueryVo.getEndMonthTime()==null
					&&deliverGoodsListQueryVo.getEndTime()==null&&deliverGoodsListQueryVo.getStartTime()==null){
				throw new BusinessException("按日查询和按月查询 选项必填其一");
			}
			
			if(limit> NUMBER_2000) 
				limit = NUMBER_2000 ;
			if(start < 0 ){ 
				start  = 0;
			}else{
				start = start * limit ;
			}
			
			List<DeliverGoodsListVo> resultList = waybillDao.queryWaybillDeliverGoodsList(deliverGoodsListQueryVo, start, limit);
			if(resultList == null || resultList.size()<=0){
				map.put("count", 0);
				map.put("resultList", resultList);
				return map ;
			}
			// 判断货物状态是否为空或全部
			String goodsStatus = deliverGoodsListQueryVo.getGoodsStatus();
			if (StringUtils.isBlank(goodsStatus) || "all".equalsIgnoreCase(goodsStatus)) {
				logger.info("货物状态为空或为all,进入数据库分页查询");
				map.put("count", waybillDao.queryWaybillDeliverGoodsListCount(deliverGoodsListQueryVo));
				map.put("resultList", resultList);
				return map ;
			} else {
				//查询条件中有货物状态，需自行过滤数据和分页
				//设置运单状态
				logger.info("货物状态不为空,开始查询货物状态信息");
				for(DeliverGoodsListVo deliverGoodsListVo : resultList){
					deliverGoodsListVo.setGoodsStatus(waybillDao.queryWaybillStatus(deliverGoodsListVo.getWaybillNo()));
				}
				//筛选状态
				map = queryWaybillByStatus(resultList, goodsStatus, start, limit);
				logger.info("条件查询 货物状态： "+goodsStatus+" 总记录数:"+map.get("count"));
				//返回结果
				return map ;
			}

		} else {
			return null;
		}
	}
	
	/**
	 * 根据状态进行查询
	 * @param list
	 * @param result
	 * @param status 要查询的运单状态
	 * @param start
	 * @param limit
	 */
	private Map<String, Object> queryWaybillByStatus(List<DeliverGoodsListVo> list, String status, int start, int limit){
		logger.info("进入 queryWaybillByStatus") ;
		Map<String, Object> result = new HashMap<String, Object>();
		if(list != null && list.size() > 0 ){
			//
			List<DeliverGoodsListVo> deliverGoodsListVos = new ArrayList<DeliverGoodsListVo>();
			for(DeliverGoodsListVo deliverGoodsListVo : list){
				String goodsStatus = deliverGoodsListVo.getGoodsStatus();
				//判断货物状态
				if(StringUtils.isNotBlank(goodsStatus)){
					/**
					 * 在途运输时，包含状态： STATION_OUT";// 营业部已出发 TFR_IN";// 已到达中转场
					 * TFR_STOCK";// 中转库存 TFR_OUT";// 中转运输 STATION_IN";// 已到达营业部
					 * STATION_STOCK";// 营业部库存 DELIVERING";// 送货中
					 * 
					 */
					if (WaybillConstants.ACCOUNT_IN_TRANSIT.equals(status)) {
						if (WaybillConstants.ACCOUNT_STATION_OUT
								.equals(goodsStatus)
								|| WaybillConstants.ACCOUNT_TFR_IN
										.equals(goodsStatus)
								|| WaybillConstants.ACCOUNT_TFR_STOCK
										.equals(goodsStatus)
								|| WaybillConstants.ACCOUNT_TFR_OUT
										.equals(goodsStatus)
								|| WaybillConstants.ACCOUNT_STATION_IN
										.equals(goodsStatus)
								|| WaybillConstants.ACCOUNT_STATION_STOCK
										.equals(goodsStatus)
								|| WaybillConstants.ACCOUNT_EFFECTIVE
										.equals(goodsStatus)
								|| WaybillConstants.ACCOUNT_DELIVERING
										.equals(goodsStatus)) {
							deliverGoodsListVos.add(deliverGoodsListVo);//

						}
						// 正常签收
					} else if (WaybillConstants.ACCOUNT_NORMAL_SIGN.equals(status)) {
						if (WaybillConstants.ACCOUNT_NORMAL_SIGN.equals(goodsStatus)) {
							deliverGoodsListVos.add(deliverGoodsListVo);//
						}
						// 异常签收
					} else if (WaybillConstants.ACCOUNT_UNNORMAL_SIGN
							.equals(status)) {
						if (WaybillConstants.ACCOUNT_UNNORMAL_SIGN
								.equals(goodsStatus)) {
							deliverGoodsListVos.add(deliverGoodsListVo);//
						}//if end

					}//if end
				}//if end
			}//for end

			int pageCount = (deliverGoodsListVos.size()-1)/limit + 1 ;
			
			// 跨越总页数置为最后一页
			if (start > pageCount) {
				start = pageCount;
			}
			// 准备要显示的数据
			List<DeliverGoodsListVo> pageList = new ArrayList<DeliverGoodsListVo>();
			for (int i = ((start - 1) * limit); i < deliverGoodsListVos.size()
					&& i < ((start) * limit) && start > 0; i++) {
				pageList.add(deliverGoodsListVos.get(i));
			}
			result.put("count", deliverGoodsListVos.size());//返回当前状态下的总数			
			result.put("resultList", pageList);//支持内存分页
			return result ;
		}else {
			return null ;
		}
		
	}
	/**
	* 供结算丢货弃货时调用，将运单号等信息同步至PTP
	* @param waybillNo om.deppon.foss.module.pickup.waybill.shared.domain
	*/
	@Override
	public void syncLostGoodsToPtp(String waybillNo,String status) {
		logger.info("开始异常签收单同步至PTP：" + waybillNo);
		WaybillEntity waybillEntity = waybillDao.queryWaybillByNo(waybillNo);
		if(waybillEntity == null || waybillEntity.getBillTime() == null){
			return;
		}
		//合伙人项目于4.10凌晨上线，4.10前的运单更改不走PTP逻辑
		ConfigurationParamsEntity entityDate = configurationParamsService.queryConfigurationParamsOneByCode(ConfigurationParamsConstants.PKP_PTP_SIGN_INIT_410);
		if (StringUtils.isNotEmpty(entityDate.getConfValue())) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date parterOnlineDate = null;
			try {
				parterOnlineDate = sdf.parse(entityDate.getConfValue());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if(FossConstants.YES.equals(waybillEntity.getIsWholeVehicle()) || !waybillEntity.getBillTime().after(parterOnlineDate)){
				return;
			}
		}
		
		SaleDepartmentEntity startOrgEntity = saleDepartmentService.querySimpleSaleDepartmentByCodeCache(waybillEntity.getReceiveOrgCode());
		SaleDepartmentEntity arriveOrgEntity = saleDepartmentService.querySimpleSaleDepartmentByCodeCache(waybillEntity.getCustomerPickupOrgCode());
		if(FossConstants.YES.equals((startOrgEntity.getIsLeagueSaleDept())) || FossConstants.YES.equals((arriveOrgEntity.getIsLeagueSaleDept()))){
			PtpWaybillDto ptpWaybillDto = new PtpWaybillDto();
			WaybillStockStatusDto stockStatus = waybillRfcService.queryWaybillStockStatus(waybillNo, waybillEntity);
			if(stockStatus != null && stockStatus.getResult() != null){
				ptpWaybillDto.setWaybillStock(stockStatus.getResult());
			}
			ptpWaybillDto.setWaybillNOId(waybillEntity.getId());
			ptpWaybillDto.setWaybillNo(waybillEntity.getWaybillNo());
			ptpWaybillDto.setIsExpress(waybillEntity.getIsExpress());
			ptpWaybillDto.setFeeType(WaybillConstants.FOUR);
			ptpWaybillDto.setIsChanged(status);
			ptpWaybillDto.setDeliveryCustomerCode(waybillEntity.getDeliveryCustomerCode());
			ptpWaybillDto.setDeliveryCustomerName(waybillEntity.getDeliveryCustomerName());
			if(FossConstants.YES.equals((startOrgEntity.getIsLeagueSaleDept()))){
				ptpWaybillDto.setIsReceiveDepPartner(true);
			}else{
				ptpWaybillDto.setIsReceiveDepPartner(false);
			}
			if(FossConstants.YES.equals((arriveOrgEntity.getIsLeagueSaleDept()))){
				ptpWaybillDto.setIsArriveDepPartner(true);
			}else{
				ptpWaybillDto.setIsArriveDepPartner(false);
			}
			
			logger.info("结束异常签收单同步至PTP：" + ptpWaybillDto.toString());
			if(WaybillConstants.YES.equals(waybillEntity.getIsExpress())){//快递开单
				expWaybillInfoToPtpService.asynSendExpWaybillInfoToPtp(ptpWaybillDto);
			}else{//是零担开单
				WaybillInfoToPtpService.asynSendWaybillInfoToPtp(ptpWaybillDto);
			}
		}
		logger.info("结束异常签收单同步至PTP：" + waybillNo);
	}

	/**
	 * 通过运单号判断是否为悟空快递单
	 * @param waybillNo 
	 * @return
	 * @author 272311-sangwenhao
	 */
	@Override
	public String queryIsECSByWayBillNo(String waybillNo) {
		if(StringUtils.isBlank(waybillNo)){
			return null ;
		}
		return waybillDao.queryIsECSByWayBillNo(waybillNo);
	}
	
	/**
	 * 
	 * <p>根据代理网点编码查询代理网点信息</p> 
	 * @author 272311-sangwenhao 
	 * @date 2016-8-13 下午7:18:08
	 * @param agencyBranchCode
	 * @return
	 */
	@Override
	public OuterBranchEntity queryAgencyBranchInfo(String agencyBranchCode) {
		return waybillDao.queryAgencyBranchInfo(agencyBranchCode);
	}

	/**
	 * cubc，根据开单时间和客户编码查询（预付+到付-代收货款）总金额
	 * String
	 * @author 198771-zhangwei
	 * 2017-1-6 下午4:54:23
	 */
	@Override
	public String queryTotalAmount(CubcQueryTotalAmountRequest requestParam) {
		return waybillDao.queryTotalAmount(requestParam);
	}
	@Override
	public CustomerCircleNewDto queryCustomerByCusCode(
			String deliveryCustomerCode, Date time, String active) {
		return customerCircleRelationNewService.getByCustCode(deliveryCustomerCode,time,active);
	}


}
