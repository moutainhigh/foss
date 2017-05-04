package com.deppon.foss.module.transfer.unload.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.transfer.unload.api.server.dao.IOrderDifferReportDao;
import com.deppon.foss.module.transfer.unload.api.shared.define.UnloadConstants;
import com.deppon.foss.module.transfer.unload.api.shared.domain.OrderDifferReportEntity;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderDifferReportDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportDetailDto;
import com.deppon.foss.module.transfer.unload.api.shared.dto.OrderReportSerialNoDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;


/**
 * @title: OrderDifferReportDaoImpl 
 * @description: 点单任务差异报告Dao 数据接口实现.
 * @author: DPAP-CodeGenerator
 * @Date: 2015-12-28 19:17:03
 */
public class OrderDifferReportDao extends iBatis3DaoImpl implements IOrderDifferReportDao {
	/**
	 * 命名空间 foss.load.OrderDifferReport
	 */
	String NAME_SPACE="foss.unload.OrderDifferReport";
	/**
	 * 
	 * <p>差异报告查询界面 根据条件查询</p> 
	 * @author 189284 
	 * @date 2015-12-31 下午4:35:55
	 * @param orderDifferReportEntity
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOrderDifferReportDao#qureyOrderDifferReport1(com.deppon.foss.module.transfer.load.api.shared.domain.OrderDifferReportEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDifferReportDto> qureyOrderDifferReport(OrderDifferReportEntity orderDifferReportEntity){
		orderDifferReportEntity.setOrderOrgCode(FossUserContext.getCurrentDeptCode());
		return getSqlSession().selectList(NAME_SPACE+".qureyOrderDifferReport",orderDifferReportEntity);
	}
	/**
	 * 
	 * <p>差异报告查询界面 根据条件查询 总数</p> 
	 * @author 189284 
	 * @date 2015-12-31 下午5:12:57
	 * @param orderDifferReportEntity
	 * @return
	 * @see
	 */
	@Override
	public Long qureyOrderDifferReportCount(OrderDifferReportEntity orderDifferReportEntity){
		orderDifferReportEntity.setOrderOrgCode(FossUserContext.getCurrentDeptCode());
		return (Long) getSqlSession().selectOne(NAME_SPACE+".qureyOrderDifferReportCount",orderDifferReportEntity);
	}
	
	/**
	* 
	* @MethodName: insert 
	* @description: insert方法
	* @author: DPAP-CodeGenerator 
	* @date: 2015-12-28 19:17:03
	* @param entity void
	*/
	public void insertOrderDifferReport(OrderDifferReportDto entity) {
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateTime(new Date());
		//entity.setCreateUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setCreateUserCode("FOSS-JOB");
		entity.setReportState(UnloadConstants.ORDER_DIFFER_REPORT_STATE_ING);
		getSqlSession().insert(NAME_SPACE+".insertOrderDifferReport", entity);
	}
	/**
	 * 
	 * <p><!-- 根据单点任务编号 生成对应的点单差异报告 运单明细 --></p> 
	 * @author 189284 
	 * @date 2016-1-22 下午3:24:04
	 * @see
	 */
	@Override
	public void insertOrderReportDetailByNo(OrderDifferReportDto entity){
		entity.setCreateTime(new Date());
		entity.setCreateUserCode("FOSS-JOB");
		getSqlSession().insert(NAME_SPACE+".insertOrderReportDetailByNo",  entity);
	}
	/**
	* 
	* @MethodName: updateByPrimaryKey 
	* @description: 根据主键更新
	* @author: DPAP-CodeGenerator 
	* @date: 2015-12-28 19:17:03
	* @param entity void
	*/
	public void updateOrderDifferReportById(OrderDifferReportEntity entity) {
		getSqlSession().update(NAME_SPACE+".updateOrderDifferReportById", entity);
	}
	/**
	 * 
	 * <p>差异报告查询界面 根据条件查询 分页</p> 
	 * @author 189284 
	 * @date 2015-12-31 下午5:27:00
	 * @param orderDifferReportEntity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOrderDifferReportDao#qureyOrderDifferReport(com.deppon.foss.module.transfer.load.api.shared.domain.OrderDifferReportEntity, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDifferReportDto> qureyOrderDifferReport( OrderDifferReportEntity orderDifferReportEntity, int limit, int start) {
		RowBounds rowBounds = new RowBounds(start, limit);
		orderDifferReportEntity.setOrderOrgCode(FossUserContext.getCurrentDeptCode());
		return getSqlSession().selectList(NAME_SPACE+".qureyOrderDifferReport",orderDifferReportEntity,rowBounds);
	}
	/**
	 * 
	 * <p>根据报告编号  查询 点单差异明细（运单）</p> 
	 * @author 189284 
	 * @date 2016-1-5 下午4:35:15
	 * @param reportNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOrderDifferReportDao#queryOrderReportDetailByNo(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderReportDetailDto> queryOrderReportDetailByNo(String reportNo) {
		return getSqlSession().selectList(NAME_SPACE+".queryOrderReportDetailByNo",reportNo);
	}
	/**
	 * 
	 * <p>根据交接单号和运单号查询 差异流水明细</p> 
	 * @author 189284 
	 * @date 2016-1-5 下午4:59:49
	 * @param waybillNo
	 * @param handoverNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOrderDifferReportDao#querySerialNoListByHandOverBillNoAndWaybillNo(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderReportSerialNoDto> querySerialNoListByHandOverBillNoAndWaybillNo(
			String waybillNo, String handoverNo ,String id) {
		OrderReportSerialNoDto orederReportSerialno=new OrderReportSerialNoDto();
		orederReportSerialno.setHandoverNo(handoverNo);
		orederReportSerialno.setWaybillNo(waybillNo);
		orederReportSerialno.setDetailId(id);
		return getSqlSession().selectList(NAME_SPACE+".querySerialNoListByHandOverBillNoAndWaybillNo",orederReportSerialno);
	}
	/**
	 * 
	 * <p>根据 明细ID 差异流水明细</p> 
	 * @author 189284 
	 * @date 2016-1-14 上午11:22:15
	 * @param detailId
	 * @return
	 * @see
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderReportSerialNoDto> querySerialNoListByDetailId(String detailId) {
		OrderReportSerialNoDto orederReportSerialno=new OrderReportSerialNoDto();
		orederReportSerialno.setDetailId(detailId);
		return getSqlSession().selectList(NAME_SPACE+".querySerialNoListByDetailId",detailId);
	}
	/**
	 * 
	 * <p> 根据运单处理点单差异报告</p> 
	 * @author 189284 
	 * @date 2016-1-11 下午3:59:31
	 * @param orderDifferReportVo 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOrderDifferReportDao#handleOrderDifferReportByWaybillNo(com.deppon.foss.module.transfer.load.api.shared.vo.OrderDifferReportVo)
	 */
	@Override
	public void handleOrderDifferReportByWaybillNo(OrderReportSerialNoDto orderReportSerialNoDto) {
		orderReportSerialNoDto.setModifyTime(new Date());
		orderReportSerialNoDto.setHandleTime(new Date());
		orderReportSerialNoDto.setModifyUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		orderReportSerialNoDto.setIsHandle(FossConstants.YES);
		getSqlSession().update(NAME_SPACE+".handleOrderDifferReportByWaybillNo", orderReportSerialNoDto);
	}
	/**
	 * 
	 * <p> 根据运单处理点单差异报告</p> 
	 * @author 189284 
	 * @date 2016-1-11 下午3:59:31
	 * @param orderDifferReportVo 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOrderDifferReportDao#handleOrderDifferReportByWaybillNo(com.deppon.foss.module.transfer.load.api.shared.vo.OrderDifferReportVo)
	 */
	@Override
	public void handleOrderDifferReportBySerialNo(OrderReportSerialNoDto orderReportSerialNoDto) {
		orderReportSerialNoDto.setModifyTime(new Date());
		orderReportSerialNoDto.setHandleTime(new Date());
		orderReportSerialNoDto.setModifyUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		orderReportSerialNoDto.setIsHandle(FossConstants.YES);
		getSqlSession().update(NAME_SPACE+".handleOrderDifferReportBySerialNo", orderReportSerialNoDto);
	}
	/**
	 * 
	 * <p>根据 id和报告编号查询 未处理的运单明细数 （除当前id外）</p> 
	 * @author 189284 
	 * @date 2016-1-12 下午4:01:51
	 * @param id
	 * @param reportNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOrderDifferReportDao#qureyIsHandleWayBillById(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Long qureyIsHandleWayBillById(String id, String reportNo) {
		Map map=new HashMap();
		map.put("id",id );
		map.put("reportNo", reportNo);
		return (Long) getSqlSession().selectOne(NAME_SPACE+".qureyIsHandleWayBillById", map);
	}
	/**
	 * 
	 * <p>根据 id 查询 未处理的流水明细数（除当前流水id 外）</p> 
	 * @author 189284 
	 * @date 2016-1-12 下午4:01:56
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOrderDifferReportDao#qureySerialNoByIdAndSerialNo(java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Long qureyIsHandleSerialNoById(String id,String detailId ) {
		Map map=new HashMap();
		map.put("id", id);
		map.put("detailId", detailId);
		return  (Long) getSqlSession().selectOne(NAME_SPACE+".qureyIsHandleSerialNoById", map);
	}
	/**
	 * 
	 * <p>根据报告编号 修改报告状态  和处理完成时间</p> 
	 * @author 189284 
	 * @date 2016-1-12 下午5:58:43
	 * @param orderDifferReportDto
	 * @see
	 */
	@Override
	public void updateOrderDifferReportStateById(OrderDifferReportDto orderDifferReportDto) {
		orderDifferReportDto.setModifyTime(new Date());
		orderDifferReportDto.setReportEndTime(new Date());
		orderDifferReportDto.setModifyUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		orderDifferReportDto.setReportState(UnloadConstants.ORDER_DIFFER_REPORT_STATE_END);
		getSqlSession().update(NAME_SPACE+".updateOrderDifferReportStateById", orderDifferReportDto);
	}
	/**
	 * 
	 * <p>根据 运单id 跟新 运单是否处理状态</p> 
	 * @author 189284 
	 * @date 2016-1-14 下午4:50:20
	 * @param id
	 * @see
	 */
	@Override
	public void updateOrderReportDetailIsHandleById(String id){
		OrderReportDetailDto orderReportDetailDto=new OrderReportDetailDto();
		orderReportDetailDto.setModifyTime(new Date());
		orderReportDetailDto.setId(id);
		orderReportDetailDto.setModifyUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		orderReportDetailDto.setIsHandle(FossConstants.YES);
		getSqlSession().update(NAME_SPACE+".updateOrderReportDetailIsHandleById", orderReportDetailDto);
	}
	/**
	 * 
	 * <p>根据时间 查询应该生成差异报告的 点单任务</p> 
	 * @author 189284 
	 * @date 2016-1-19 下午2:29:51
	 * @param bizJobStartTime
	 * @param bizJobEndTime
	 * @param threadNo
	 * @param threadCount
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOrderDifferReportDao#queryOrderReportByBatch(java.util.Date, java.util.Date, int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderDifferReportDto> queryOrderReportByBatch(int threadNo,int threadCount) {
		Map<String, Object> queryOrderReportMap = new HashMap<String, Object>();
		queryOrderReportMap.put("orderTaskState", UnloadConstants.ORDER_DIFFER_REPORT_STATE_END);
		System.out.print(queryOrderReportMap.toString());
		return getSqlSession().selectList(NAME_SPACE+".queryOrderReportByBatch", queryOrderReportMap);
	}
	/**
	 * 
	 * <p>根据点单任务编号差异类型  查询 对应需要生产差异报告的 流水明细 </p> 
	 * @author 189284 
	 * @date 2016-1-20 下午6:30:57
	 * @param orderTaskNo 点单任务编号
	 * @param orderReportType 点单差异类型（NORMAL正常,LOSE少货,MORE多货）
	 * @return
	 * @see
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<OrderReportSerialNoDto> qureyOrderTaskSerialnoByNoAndType(String orderTaskNo,String orderReportType ){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderTaskNo", orderTaskNo);
		map.put("orderReportType", orderReportType);
		return getSqlSession().selectList(NAME_SPACE+".qureyOrderTaskSerialnoByNoAndType", map);
	}
	/**
	 * 
	 * <p>新增 差异报告 流水</p> 
	 * @author 189284 
	 * @date 2016-1-22 下午5:46:26
	 * @param orderReportSerialNoDto
	 * @see
	 */
	@Override
	public void insertOrederReportSerialno(OrderReportSerialNoDto orderReportSerialNoDto){
		getSqlSession().insert(NAME_SPACE+".insertOrederReportSerialno", orderReportSerialNoDto);
	}
	/**
	 * 
	 * <p>根据报告编号 查询差异报告</p> 
	 * @author 189284 
	 * @date 2016-1-26 下午6:06:35
	 * @param reportNo
	 * @return 
	 * @see com.deppon.foss.module.transfer.load.api.server.dao.IOrderDifferReportDao#queryOrderReportByReportNo(java.lang.String)
	 */
	@Override
	public OrderDifferReportDto queryOrderReportByReportNo(String reportNo){
		return (OrderDifferReportDto) getSqlSession().selectOne(NAME_SPACE+".queryOrderReportByReportNo", reportNo);
	}
}