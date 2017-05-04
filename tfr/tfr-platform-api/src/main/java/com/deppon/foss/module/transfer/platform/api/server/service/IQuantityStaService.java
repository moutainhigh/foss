/**   
* @Title: IQuantityStaCommonService.java 
* @Package com.deppon.foss.module.transfer.platform.api.server.service 
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年8月26日 下午3:09:13 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantitySta4ChartDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaDto;
import com.deppon.foss.module.transfer.platform.api.shared.dto.QuantityStaQcDto;

/** 
 * @ClassName: IQuantityStaCommonService 
 * @Description: 货量统计后台公用service
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年8月26日 下午3:09:13 
 *  
 */
public interface IQuantityStaService extends IService {
	
	/**
	* @Title: queryTransferCenterCode 
	* @Description: 获取登录部门对应的外场
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年8月29日 上午10:11:52 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	String[] queryTransferCenter();
	
	/**
	* @Title: queryStaStartAndEndTime 
	* @Description: 获取某外场的货量统计开始时间、结束时间，用于查询时显示
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年8月31日 下午2:35:32 
	* @param @param transferCenterCode
	* @param @return    设定文件 
	* @return Date[]    返回类型 
	* @throws
	 */
	String queryStaStartAndEndTime(String transferCenterCode);
	
	
	/**
	 * @desc 查询出发货量
	 * @return
	 * @date 2014年8月31日下午5:44:11
	 * @author Ouyang
	 */
	List<QuantityStaDto> queryDepart(QuantityStaQcDto dto);
	
	QuantityStaDto queryDepartTotal(QuantityStaQcDto dto);
	
	List<QuantitySta4ChartDto> queryDepart1Day4Chart(QuantityStaQcDto dto);
	
	List<QuantitySta4ChartDto> queryDepartFewDays1Hh4Chart(QuantityStaQcDto dto);
	
	
	/**
	 * @desc 查询到达货量
	 * @return
	 * @date 2014年8月31日下午5:44:11
	 * @author Ouyang
	 */
	List<QuantityStaDto> queryArrive(QuantityStaQcDto dto);
	
	QuantityStaDto queryArriveTotal(QuantityStaQcDto dto);

	List<QuantitySta4ChartDto> queryArrive1Day4Chart(QuantityStaQcDto dto);
	
	
	List<QuantitySta4ChartDto> queryArriveFewDays1Hh4Chart(QuantityStaQcDto dto);
	
	/**
	* @Title : exportTfrCtrPersonnelBudgets 
	* @Description : 导出出发货量
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年9月4日 上午11:17:35 
	* @param @param qcDto
	* @param @return    设定文件 
	* @return ExportResource    返回类型 
	* @throws
	 */
	ExportResource expertQuantity(QuantityStaQcDto dto);
	
	/**
	* @Title: queryWarnWeightAndVolume 
	* @Description: 获取货量统计的警戒重量、警戒体积
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年9月7日 下午4:18:09 
	* @param @return    设定文件 
	* @return BigDecimal[]    返回类型 
	* @throws
	 */
	BigDecimal[] queryWarnWeightAndVolume(String transferCenterCode);

	
	/**
	 * @desc 查询第2天出发派送预测货量，提供给查询派送货量接口
	 * @param transferCenterCode
	 *            外场编码，当此参数为空时，即查询所有外场的数据
	 * @param staDate
	 *            统计日期；如今天是2014-09-16，则接口是查询2014-09-17的出发派送预测货量，
	 *            此参数传入2014-09-16，时分秒毫秒为0
	 * @return 
	 *         外场出发派送预测货量集合；QuantityStaDto实体中有orgCode外场编码，forecastWeightTotal预测重量
	 *         ，forecastVolumeTotal预测体积，forecastQtyTotal预测票数；
	 *         当某外场没有对应数据时，对应的QuantityStaDto实体为空
	 * @date 2014年9月16日 下午3:05:46
	 * @author Ouyang
	 */
	List<QuantityStaDto> query2ndDepartDeliverFcst(String transferCenterCode,
			Date staDate);
	
	/**
	 * @desc 查询某天总预测货量，提供给仓库饱和度接口
	 * @param transferCenterCode
	 *            外场编码(当此参数为空时，即查询所有外场的数据)
	 * @param staDate
	 *            统计日期；staDate只要年月日，时分秒毫秒为0
	 * @return 
	 *         外场出发派送预测货量集合；QuantityStaDto实体中有orgCode外场编码，forecastWeightTotal预测重量
	 *         ，forecastVolumeTotal预测体积，forecastQtyTotal预测票数；
	 *         当某外场没有对应数据时，对应的QuantityStaDto实体为空
	 * @date 2014年9月16日 下午3:43:27
	 * @author Ouyang
	 */
	List<QuantityStaDto> queryTotalFcst(String transferCenterCode, Date staDate);
	
}
