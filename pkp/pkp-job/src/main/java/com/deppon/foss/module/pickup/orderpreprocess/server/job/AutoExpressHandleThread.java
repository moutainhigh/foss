/**
 * 2016-09-13版本清楚快递自动调度
 */
/*
package com.deppon.foss.module.pickup.orderpreprocess.server.job;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.module.base.dict.api.server.service.IConfigurationParamsService;
import com.deppon.foss.module.base.dict.api.shared.define.ConfigurationParamsConstants;
import com.deppon.foss.module.base.dict.api.shared.domain.ConfigurationParamsEntity;
import com.deppon.foss.module.pickup.order.api.server.dao.IDispatchOrderChangeEntityDao;
import com.deppon.foss.module.pickup.order.api.server.service.IAutoExpressOrderService;
import com.deppon.foss.module.pickup.order.api.shared.domain.DispatchOrderChangeEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressCityDto;
import com.deppon.foss.module.pickup.order.api.shared.dto.ExpressOrderDto;
import com.deppon.foss.module.pickup.order.api.shared.tools.OrderThreadPoolCaller;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

*//**
 * 
 * @ClassName: AutoExpressHandleThread
 * @Description: 快递订单-线程自动加载
 * @author YANGBIN
 * @date 2014-5-4 下午4:36:43
 * 
 *//*
public class AutoExpressHandleThread extends OrderThreadPoolCaller {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AutoExpressHandleThread.class);
	
	// 变更表dao
	private IDispatchOrderChangeEntityDao dispatchOrderChangeEntityDao;
	// 配置service
	private IConfigurationParamsService configurationParamsService;
	
	private IAutoExpressOrderService autoExpressOrderService;
	
	*//**
	 * 
	 * @Title: process
	 * @Description: 自动启动线程服务
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 *//*
	@Override
	public int serviceCaller() {
		LOGGER.info("AutoExpressHandleThread 线程自动处理开启......");
		int isQuery = 0;
		// TODO 判断总开关
		try {	
			//从配置参数中读取自动调度开关值
			String flag = FossConstants.NO;
			String[] codes = new String[1];
			codes[0]=ConfigurationParamsConstants.PKP_AUTO_EXPRESS_SCHEDULE_MANAGE;//快递自动调度总开关
			List<ConfigurationParamsEntity> configurationParamsEntitys = this.configurationParamsService.queryConfigurationParamsBatchByCode(codes);
			if(null	!=	configurationParamsEntitys
					&&	configurationParamsEntitys.size() > 0){
				//开关是否关闭
				flag = configurationParamsEntitys.get(0).getConfValue();
			}
			// 如果为关闭，则直接退出
			if(StringUtils.equals(FossConstants.NO, flag)){//不存在开关或者开关关闭
				return 0;
			}	
			LOGGER.info("【自动调度】总开关是否开启"+flag);
			
			//zxy 20140716 AUTO-165 start 修改:把按城市分别取数据,改成只按容量取一次数据
			// 自动查询 自动受理
			isQuery = handleByAuto(null);
			//zxy 20140716 AUTO-165 end 修改:把按城市分别取数据,改成只按容量取一次数据
		} catch (Exception e) {
			LOGGER.info("AutoExpressHandleThread 快递线程自动处理异常！");
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("AutoExpressHandleThread 快递线程自动处理结束。。。。。。");
		return isQuery;
	}

	*//**
	 * jobid更新
	 * updateRepaymentForJob: <br/>
	 * 
	 * Date:2014-7-9下午7:08:40
	 * @author 157229-zxy
	 * @return
	 * @since JDK 1.6
	 *//*
	private String updateOrderChangeForExpressJob(ExpressCityDto expressCityDto) {
		String jobId = UUIDUtils.getUUID();
		ExpressOrderDto dto = new ExpressOrderDto();
		dto.setProductCodes(ProductEntityConstants.EXPRESS_PRODUCT_CODE_LIST);
		//zxy 20140716 AUTO-165 start 修改:去掉城市和时间的判断，直接读取N条数据
//		dto.setCityCode(expressCityDto.getCityCode());
		dto.setRownum(readChangeCount());
//		dto.setStartTime(expressCityDto.getStartTime());
//		dto.setEndTime(expressCityDto.getEndTime());
		//zxy 20140716 AUTO-165 end 修改:去掉城市和时间的判断，直接读取N条数据
		dto.setJobId(jobId);
		dispatchOrderChangeEntityDao.updateOrderChangeForExpressJob(dto);
		return jobId;
	}
	*//**
	 * 14.7.14 gcl AUTO-156 从配置参数中获得快递每个线程自动读取数量
	 * @return
	 *//*
	private int readChangeCount(){
		int num = 100;
		String[] codes = new String[1];
		codes[0]=ConfigurationParamsConstants.PKP_EXPRESS_AUTO_SCHEDULE_COUNT;
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
	
	*//**
	 * 
	 * @Title: handleByAuto
	 * @Description: 城市有效时间段，自动处理
	 * @param @param expressCityDto 设定文件
	 * @return void 返回类型
	 * @throws
	 *//*
	private int handleByAuto(ExpressCityDto expressCityDto) {
		//zxy 20140711 AUTO-149 start 修改:先加锁再查询
		String jobId = updateOrderChangeForExpressJob(expressCityDto);
		int isQueryExist = 0;
		ExpressOrderDto dto = new ExpressOrderDto();
		// 设置运输性质为包裹  或者3.60特惠件  gcl 10.9
		List<String> productCodes = new ArrayList<String>();
		productCodes.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_PACKAGE);
		productCodes.add(ProductEntityConstants.PRICING_PRODUCT_ROUND_COUPON_PACKAGE);
		productCodes.add(ProductEntityConstants.ECOMMERCE_PROMOTIONAL_ENJOY_PACKAGE);
		productCodes.add(ProductEntityConstants.PRICING_PRODUCT_EXPRESS_AIR_FREIGHT);
		dto.setProductCodes(productCodes);
		dto.setJobId(jobId);
		//zxy 20140711 AUTO-149 end 修改:先加锁再查询
		// 查询变更表中的记录
		List<DispatchOrderChangeEntity> changeList = dispatchOrderChangeEntityDao
				.queryChangeByExpressDto(dto);
		if (changeList != null && changeList.size() != 0) {
			LOGGER.info("快递线程自动处理开始。。。。。。。。。");
			isQueryExist = 1;
			// 更新查询标记位 
			//zxy 20140711 AUTO-149 注销此句
//			dispatchOrderChangeEntityDao.batchUpdateChange(changeList);
			//14.7.12 gcl  AUTO-156 加入线程池
			autoExpressOrderService.aotoDispatchOrderList(changeList);
		} else {
			LOGGER.info("无订单进行线程自动调度处理");
		}
		return isQueryExist;
	}

	public void setAutoExpressOrderService(
			IAutoExpressOrderService autoExpressOrderService) {
		this.autoExpressOrderService = autoExpressOrderService;
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
*/