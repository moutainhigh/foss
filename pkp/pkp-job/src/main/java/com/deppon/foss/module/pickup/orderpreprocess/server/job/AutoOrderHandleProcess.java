package com.deppon.foss.module.pickup.orderpreprocess.server.job;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoDispathchOrderService;
import com.deppon.foss.module.pickup.order.api.shared.define.DispatchOrderStatusConstants;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.QueryOrderChangeDto;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

public class AutoOrderHandleProcess extends OrderThreadPoolCaller {
	
	private IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao;
	private IAutoDispathchOrderService autoDispathchOrderService;
	private IConfigurationParamsService configurationParamsService;
	@Override
	public int serviceCaller() {
		//开关是否关闭 开着 走自动调度项目线程
		//从配置参数中读取自动调度开关值
		String flag = FossConstants.NO;//默认开关关闭
		String[] codes = new String[1];
		codes[0]=ConfigurationParamsConstants.PKP_AUTO_SCHEDULE_MANAGE;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(null!=configurationParamsEntitys&&configurationParamsEntitys.size()>0){
			flag = configurationParamsEntitys.get(0).getConfValue();//开关是否关闭
		}
		if(!StringUtils.equals(FossConstants.YES, flag)){//不存在开关或者开关关闭
			return 0;
		}
		//zxy 20140711 AUTO-149 start 修改:先加锁再查询
		// 查询变更表中的记录
		//查询零担变更表中接货订单中的的记录
		String jobId = updateRepaymentForJob();
		//cdl 20141017 在sql语句中增加了查询条件，运输性质为门-门、场对场、新产品的订单使用人工处理
		List<DispatchOrderChangeEntity> changeList = getOrderChangeDto(jobId);
		//zxy 20140711 AUTO-149 end 修改:先加锁再查询
		if (changeList != null && changeList.size() > 0) {
			//zxy 20140711 AUTO-149 注销此句
			//标记处理状态
//			dispatchOrderChangeEntityDao.batchUpdateChange(changeList);
			//加入线程池
			autoDispathchOrderService.aotoDispatchOrderList(changeList);
			return changeList.size();
		}
		return 0;
	}
	
	/**
	 * 封装自动调度查询条件
	 * @return
	 */
	private List<DispatchOrderChangeEntity> getOrderChangeDto(String jobId) {
		QueryOrderChangeDto queryOrderChangeDto = new QueryOrderChangeDto();
		queryOrderChangeDto.setOrder_type(DispatchOrderStatusConstants.TYPE_PICKUP_ORDER);
		//cdl 1020增加门对门、场对场查询条件
		List<String> productCodes = new ArrayList<String>();
		productCodes.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);//不是快递
		productCodes.add(ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE);//不是3.60
		productCodes.add(ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);//不是电商尊享
		productCodes.add(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR);//不是门对门
		productCodes.add(ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD);//不是场对场
		queryOrderChangeDto.setProductCodes(productCodes);
		//zxy 20140711 AUTO-149 start 修改:只查询jobid数据
//		queryOrderChangeDto.setNum(100);
//		queryOrderChangeDto.setJob_id("N/A");
		queryOrderChangeDto.setJob_id(jobId);
		//zxy 20140711 AUTO-149 end 修改:只查询jobid数据
		List<DispatchOrderChangeEntity> changeList = dispatchOrderChangeEntityDao.queryByQueryOrderChangeDto(queryOrderChangeDto);
		return changeList;
	}
	/**
	 * 14.7.14 gcl AUTO-156 从配置参数中获得零担每个线程自动读取数量
	 * @return
	 */
	private int readChangeCount(){
		int num = NumberConstants.NUMBER_100;
		String[] codes = new String[1];
		codes[0]=ConfigurationParamsConstants.PKP_AUTO_SCHEDULE_COUNT;
		List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
		if(null	!=	configurationParamsEntitys
				&&	configurationParamsEntitys.size() > 0){
			String value = configurationParamsEntitys.get(0).getConfValue();
			if(value != null){
				num = Integer.parseInt(value);
			}
		}
		return num;
	}
	/**
	 * 更新job锁
	 * updateRepaymentForJob: <br/>
	 * 
	 * Date:2014-7-9下午7:08:40
	 * @author 157229-zxy
	 * @return
	 * @since JDK 1.6
	 */
	private String updateRepaymentForJob() {
		String jobId = UUIDUtils.getUUID();
		QueryOrderChangeDto queryOrderChangeDto = new QueryOrderChangeDto();
		queryOrderChangeDto.setOrder_type(DispatchOrderStatusConstants.TYPE_PICKUP_ORDER);
		List<String> productCodes = new ArrayList<String>();
		productCodes.addAll(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);//不是快递
		productCodes.add(ProductEntityConstants.PRICING_PRODUCT_DOOR_TO_DOOR);//不是门对门
		productCodes.add(ProductEntityConstants.PRICING_PRODUCT_YARD_TO_YARD);//不是场对场
		queryOrderChangeDto.setProductCodes(productCodes);
		queryOrderChangeDto.setNum(readChangeCount());
		queryOrderChangeDto.setJob_id(jobId);
		dispatchOrderChangeEntityDao.updateOrderChangeForJob(queryOrderChangeDto);
		return jobId;
	}

	public void setAutoDispathchOrderService(
			IAutoDispathchOrderService autoDispathchOrderService) {
		this.autoDispathchOrderService = autoDispathchOrderService;
	}
	
	public void setDispatchOrderChangeEntityDao(
			IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao) {
		this.dispatchOrderChangeEntityDao = dispatchOrderChangeEntityDao;
	}

	public void setConfigurationParamsService(
			IConfigurationParamsService configurationParamsService) {
		this.configurationParamsService = configurationParamsService;
	}
	
}