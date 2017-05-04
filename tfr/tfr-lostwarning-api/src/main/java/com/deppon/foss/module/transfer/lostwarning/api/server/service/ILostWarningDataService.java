package com.deppon.foss.module.transfer.lostwarning.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.lostwarning.api.server.domain.LostWarningDataEntity;
import com.deppon.foss.module.transfer.lostwarning.api.shared.dto.LostWarningTempDto;
/**
 * 丢失预警数据基础处理
 * 
 * 项目名称：tfr-lostwarning-api
 * 
 * 类名称：IlostwarningService
 * 
 * 创建人：lyz 263072
 * 
 * 创建时间：2015-6-9 下午3:02:52
 * 
 * 版权所有：上海德邦物流有限公司
 */
public interface ILostWarningDataService extends IService{
	
	/**
	 * 查询丢货数据
	 * @Description: 
	 * @date 2015-6-10 下午5:35:37   
	 * @author 263072
	 */
	public List<LostWarningTempDto> searchLostWarnningData(String jobID);
	
	
	/**
	 * 出发库存丢货数据同步到临时表
	 */
	public void synStartDptLostWarningData();
	
	/**
	 * @Description: 同步集中接货丢货数据
	 * @date 2015-6-16 下午6:22:59   
	 * @author 263072
	 */
	public void synJZReceiveLostData();
	
	/**
	 * @Description: 同步传输丢货数据
	 * @date 2015-6-19 下午1:56:20   
	 * @author 263072
	 */
	public void synTransferLostData();
	
	/**
	 * @Description: 同步已到达丢货数据
	 * @date 2015-6-24 上午10:09:34   
	 * @author 263072
	 */
	public void synAlreadyArriveLostData();
	
	/**
	 * @Description: 同步中转库存丢货数据
	 * @date 2015-6-25 上午11:09:02   
	 * @author 263072
	 */
	public void synTransferStoreLostData();
	
	/**
	 * @Description: 同步已交接丢货数据
	 * @date 2015-6-26 下午6:51:13   
	 * @author 263072
	 */
	public void synHandoverLostData();
	
	/**
	 * @Description:同步三次以上异常库存数据 
	 * @date 2015-6-30 上午11:06:22   
	 * @author 263072
	 */
	public void synDifferStockLostData();
	
	/**
	 * @Description: 同步派送丢货数据
	 * @date 2015-7-3 下午3:25:28   
	 * @author 263072 
	 * @param handleTime
	 */
	public void synDeliverLostData();
	
	/**
	 * @Description: 同步空运外发丢货数据
	 * @date 2015-7-6 下午4:35:23   
	 * @author 263072 
	 * @param handleTime
	 */
	public void synAirTransferLostData();
	
	/**
	 * @Description: 同步快递外发丢货数据
	 * @date 2015-7-6 下午7:04:38   
	 * @author 263072 
	 * @param handleTime
	 */
	public void synExpressExternalLostData();
	
	
	/**
	 * @Description: 更改JOBID
	 * @date 2015-7-21 上午8:56:18   
	 * @author 263072 
	 */
	public String upateWarningDataForJob();
	
	
	/**
	 * @Description: 保存上报信息成功日志
	 * @date 2015-7-18 下午6:52:26   
	 * @author 263072 
	 * @param bean
	 * @param lostRepCode 丢货编码
	 * @param infoType 临时表字段：上报信息类型
	 */
	public void saveUploadSuccInfo(LostWarningDataEntity bean, String lostRepCode,String infoType,String uploadMsg);
	
	
	/**
	 * @Description: 保存上报信息失败日志
	 * @date 2015-7-18 下午6:54:46   
	 * @author 263072 
	 * @param bean
	 * @param errorMsg 失败日志信息
	 */
	public void saveUploadFalseInfo(LostWarningDataEntity bean,String errorMsg,String infoType,String uploadMsg);
	

}
