package com.deppon.foss.module.transfer.platform.api.server.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.platform.api.shared.domain.StockSaturationEntity;


/**
* @description 库存饱和度Service
* @version 1.0
* @author 14022-foss-songjie
* @update 2014年3月22日 下午2:39:41
*/
public interface IStockSaturationService extends IService {
	
	/**
	* @Title: queryOutfieldInfo 
	* @Description: 查询外场信息，查询不到则视为统计部门
	* @author shiwei shiwei@outlook.com
	* @date 2014年3月3日 上午9:49:35 
	* @param @return    设定文件 
	* @return String[]    返回类型 
	* @throws
	 */
	String[] queryOutfieldInfo();
	
	
	/**
	* @description 根据code查询对应的部门名称
	* @param orgCode
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月3日 下午2:53:53
	*/
	String[] queryOutfieldInfo(String orgCode);
	
	/**
	* @description 定时任务执行的service
	* @param queryDate
	* @param threadNo
	* @param threadCount
	* @throws Exception
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年3月22日 下午2:39:21
	*/
	void doStockSaturationJob(Date queryDate,int threadNo,int threadCount) throws Exception;
	
	
	/**
	* @description 仓库饱和度查询
	* @param pojo
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 下午7:58:03
	*/
	List<StockSaturationEntity> queryStockSaturationList(StockSaturationEntity pojo,int start,int limit);
	
	
	/**
	* @description 仓库饱和度导出
	* @param pojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月4日 上午10:15:10
	*/
	InputStream exportStockSaturationList(StockSaturationEntity pojo);
	
	/**
	* @description 日饱和度
	* @param map 外场code 和日期必须有
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 下午7:58:03
	*/
	List<StockSaturationEntity> queryStockSaturationDayList(StockSaturationEntity pojo);
	
	
	
	/**
	* @description 日饱和度导出
	* @param pojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月4日 下午1:55:15
	*/
	InputStream exportStockSaturationDayList(StockSaturationEntity pojo);
	
	/**
	* @description 月仓库饱和度
	* @param map 外场code 和日期必须有
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月2日 下午7:58:03
	*/
	List<StockSaturationEntity> queryStockSaturationMonthList(StockSaturationEntity pojo);
	
	
	/**
	* @description 月仓库饱和度导出
	* @param pojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月4日 下午2:09:52
	*/
	InputStream exportStockSaturationMonthList(StockSaturationEntity pojo);
	
	/**
	* @description 仓库饱和度查询的总记录数
	* @param pojo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月1日 上午10:07:15
	*/
	long queryStockSaturationListCount(StockSaturationEntity pojo);
	
	
	/**
	* @description 判断当前时间是否大于12点
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月4日 下午4:06:35
	*/
	boolean decideTimeTwelve();
	
	
	/**
	* @description 千克转吨
	* @param kilo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月10日 上午8:54:33
	*/
	BigDecimal kiloToTon(BigDecimal kilo);
	
	/**
	 * 每隔一个小时，后台计算一次当日及未来一日仓库饱和度，其中的操作货量取自货量统计中的预测货量。
	 * 若两者（当日及未来一日仓库饱和度）之一超过危险值，则发送
	 * 领导你好，XX外场XX日仓库饱和度达到xx%，已经超过了危险值，请做好预防措施，防止发生爆仓！
	 * 当日、未来一日及此前五日的仓库饱和度中有三次超过警戒值，则向外场负责人发送预警短信。
	 * 若两者之一超过危险值，则发送
	 * 短信模板：
	 * 领导你好，XX外场（实际外场名称）近一周仓库饱和度已有三天超过警戒值，分别是：XX日xx%,XX日xx%,XX日xx%
	* @description 每小时查询一次
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年4月11日 上午9:09:10
	*/
	void smsStockSaturationServiceJob();
	
	/**
	 *com.deppon.foss.module.transfer.platform.api.server.service  
	 *@desc 统计当月预警天数与当月危险预警天数
	 *@param orgCode、startTime、endTime
	 *@return List<StockSaturationEntity>
	 *@author 105795
	 *@date 2015年3月12日下午3:36:36 
	 */
	List<StockSaturationEntity> calculateWarningAndDangerMothDays(List<String> orgCodeList,String queryDate);
	
}
