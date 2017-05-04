package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;
import com.deppon.esb.pojo.domain.crm2foss.UpdateEOrderRequest;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.EwaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillPendingEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.ResultDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillExpressPdaDto;



/**
 * @author 136334-foss-bailei
 * @param <DispatchOrderEntity>
 *
 */
public interface IEWaybillService extends IService {
		
	/**
	 * 生成待激活电子运单JOB
	 * @author:foss-136334-BaiLei
	 * @date 2014-10-1 16:47:58
	 * @throws Exception
	 */
	public void batchGenerateUnActiveEWaybillJobs();

	/**
	 * 逾期后台自动作废数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-1 10:49:14
	 */
	void invalidEWaybillOrverDays();

	void activeEWaybillByPda(WaybillExpressPdaDto waybillExpressPdaDto, String billOrgCode);

	/**
	 * 判定是否电子运单数据
	 * @param eWaybillNo
	 * @return
	 */
	boolean queryIsEWaybill(String eWaybillNo);

	/**
	 * 批量激活电子运单数据，根据运单号激活批量激活
	 * @param eWaybillConditionDto
	 * @date 2014-9-14 18:42:46
	 * @return
	 */
	public void batchGenerateActiveEWaybillByPda(EWaybillConditionDto eWaybillConditionDto);

	/**
	 * PDA那边可以退回电子运单
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014年9月15日 10:55:31
	 */
	public ResultDto returnEWaybillByPda(EWaybillConditionDto eWaybillConditionDto);

	/**
	 * 作废单个逾期电子运单数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-8-1 10:49:14
	 */
	void invalidSingleEWaybillOrverDay(WaybillPendingEntity waybillPendingEntity);

	/**
	 * 获取待激活运单有星标记,对外提供用于判断该标签是否支持自动分拣
	 * @author:foss-136334-BaiLei
	 * @date 2014-09-16
	 */
	public String getExpressIsStarFlag(WaybillPendingEntity entity);
	
	/**
	 * 自动生成电子运单号
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-3 09:55:15
	 * @return
	 */
	String generateEWaybillNO();
	
	/**
	 * <p>单个处理待激活的待补录数据,此方法不建议直接使用，只有在处理异常数据时候进行时候为佳</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-3 09:55:15
	 * @return
	 */
	void singleGenerateUnActiveEWaybill(String orderNo);
	
	/**
	 * <p>生成待激活电子运单通过多线程方式</p>
	 * @author 045925
	 * @date 2015-01-26 09:55:15
	 * @return
	 */
	public int batchHandleEWaybillThread(String jobId);
	
	String singleUpdateEcommerceOrder(UpdateEOrderRequest updateEOrderRequest);
	
	void singleDeleteEcommerceOrder(UpdateEOrderRequest updateEOrderRequest);
	
	void afterActiveFail(WaybillDto waybillDto, List<WaybillPendingEntity> waybillPendingEntityList);
	
	void addWaybillExpress(WaybillDto waybillDto, WaybillPendingEntity waybillPendingEntity);
	
	public void batchGenerateActiveWaybillRelateJobs(String jobId);
	//public void batchGenerateActiveWaybillRelateJobs();

	void singleHandleActiveWaybillRelateJobs(EwaybillRelateEntity ewaybillRelateEntity);

	/**
	 * <p>单个处理待激活的待补录数据,此方法不建议直接使用，只有在处理异常数据时候进行时候为佳</p>
	 * @author Foss-105888-Zhangxingwang
	 * @date 2014-10-3 09:55:15
	 * @return
	 */
	void singleGenerateActiveEWaybill(String orderNo);

	void singleGenerateAntiveEwaybillByWaybillRelate(List<WaybillRelateDetailEntity> waybillRelateDetailList,
			List<WaybillPendingEntity> waybillPendingEntityList, EwaybillRelateEntity ewaybillRelateEntity);
	
	void singleGenerateAntiveEwaybillByEwaybillEntity(EwaybillRelateEntity ewaybillRelateEntity);
}
