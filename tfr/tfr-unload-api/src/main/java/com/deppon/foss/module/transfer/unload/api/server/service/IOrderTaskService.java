package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderSerialNoDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderTaskEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OtHandOverBillDetailEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskAddnewDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskModifyDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderTaskMoreDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialInfoBywaybillNoDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.QuerySerialListByTskNumDto;

public interface IOrderTaskService extends IService{

	/**
	 * 通过交接单号获取交接单信息
	 * @author 272681-foss-chenlei 
	 * @date 2015-12-14 上午10:14:03
	 * @param handOverBillNoList
	 * @return List<OrderTaskBillInfoDto>
	 */
	public List<OtHandOverBillDetailEntity> queryHandOverBillInfoByNo(String handoverNo);
	
	//获取上级组织实体
	OrgAdministrativeInfoEntity querySuperOrgByOrgCode(String orgCode);
   
	/**
	 * 传入交接单编号和运单号，获取流水号
	 * @author 272681-foss-chenlei
	 * @date 2015-12-23 
	 */
	public List<HandOverBillSerialNoDetailEntity> querySerialNoListByHandOverBillNoAndWaybillNo(
			String handOverBillNo, String waybillNo);

	/**
	 * 分配点单任务
	 * @author 272681-foss-chenlei
	 * @date 2015-12-23 
	 */
	public String addOrderTask(OrderTaskAddnewDto addnewDto);

	/**
	 * 新增卸车任务基本信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-24
	 */
	int addOrderTaskBasicInfo(OrderTaskEntity baseEntity);

	/**
	 * 新增点单任务明细
	 * @author 272681-foss-chenlei
	 * @date 2015-12-24
	 */
	int addOrderTaskBillDetailList(
			List<OtHandOverBillDetailEntity> billDetailList);

	/**
	 * 查询点单任务(分页)
	 * @author 272681-foss-chenlei
	 * @date 2015-12-25
	 */
	List<OrderTaskDto> queryOrderTask(OrderTaskDto orderTaskDto, int limit,
			int start);

	/**
	 * 查询点单任务总数
	 * @author 272681-foss-chenlei
	 * @date 2015-12-25 
	 */
	Long getTotCount(OrderTaskDto orderTaskDto);

	/**
	 * 处理点单任务时，加载修改前的基本信息数据
	 * @author 272681-foss-chenlei
	 * @date 2015-12-26
	 */
	public OrderTaskEntity queryOrderTaskBaseInfoByNo(String orderTaskNo);
	
	/**
	 * 处理点单任务时，加载修改前的单据信息数据
	 * @author 272681-foss-chenlei
	 * @date 2015-12-26
	 */
	public List<OtHandOverBillDetailEntity> queryOrderTaskBillDetailListByNo(
			String orderTaskNo);

	/**
	 * 新增点单任务流水
	 * @author 272681-foss-chenlei
	 * @date 2015-12-28
	 */
	int addOrderTaskSerialNoDetailList(
			List<OrderSerialNoDetailEntity> serialNoDetailList);

	/**
	 * 传入交接单编号和运单号，获取流水号信息
	 * @author 272681-foss-chenlei
	 * @date 2015-12-28
	 */
	public List<OrderSerialNoDetailEntity> queryOrderTaskSerialNoListByBillNo(
			String id);

	
	/**
	 * 添加多货时，输入的运单号获取流水号
	 * @author 272681-foss-chenlei
	 * @date 2015-12-30
	 */
	public List<QuerySerialInfoBywaybillNoDto> queryValidateWaybillNoAndSerialNo(String waybillNo);

	/**
	 * 处理点单任务时，提交数据
	 * @author 272681-foss-chenlei
	 * @date 2016-01-04 
	 */
	public int updateOrderTask(OrderTaskModifyDto orderTaskModifyDto);

	/**
	 * 根据点单任务号更新点单任务基本信息
	 * @author 272681-foss-chenlei
	 * @date 2016-01-04 
	 */
	int updateOrderTaskBasicInfo(OrderTaskEntity entity);

	/**
	 * 处理点单任务界面添加多货流水
	 * @author 272681-foss-chenlei
	 * @date 2016-01-07 
	 */
	public String insertMoreSerialNo(
			OrderTaskMoreDto orderTaskMoreDto);

	/**
	 * 处理点单任务时，根据点单任务编号查询所有流水数据
	 * @author 272681-foss-chenlei
	 * @date 2016-01-10
	 */
	public List<QuerySerialListByTskNumDto> queryOrderTaskSerialNo(
			String orderTaskNo);

	public void orderTaskFinish();

	public List<OrderSerialNoDetailEntity> queryOrderTaskMoreSerialNoListByNo(
			String orderTaskNo, String waybillNo);


	

}
