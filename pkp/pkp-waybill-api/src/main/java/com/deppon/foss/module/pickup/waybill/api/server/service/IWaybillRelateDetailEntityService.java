package com.deppon.foss.module.pickup.waybill.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.domain.EcomWaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EwaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.TwoInOneWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillSystemDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
/**
 * 子母件服务接口层
 * @author Foss-105888-Zhangxingwang
 * @date 2015-8-6 16:17:58
 */
public interface IWaybillRelateDetailEntityService extends IService {
    int deleteWaybillRelateDetailByPrimaryKey(String id);

    int insertWaybillRelateDetail(WaybillRelateDetailEntity record);

    int insertWaybillRelateDetailSelective(WaybillRelateDetailEntity record);

    WaybillRelateDetailEntity selectWaybillRelateDetailByPrimaryKey(String id);

    int updateWaybillRelateDetailByPrimaryKeySelective(WaybillRelateDetailEntity record);

    int updateWaybillRelateDetailByPrimaryKey(WaybillRelateDetailEntity record);

	int appendWaybillRelateDetailEntityBatchAfterChange(List<WaybillRelateDetailEntity> waybillChargeDtlList, WaybillSystemDto systemDto);

	List<WaybillRelateDetailEntity> queryNewWaybillRelateDtlEntityByNo(LastWaybillRfcQueryDto queryDto);

	int addWaybillRelateDetailEntityBatch(List<WaybillRelateDetailEntity> waybillRelateDtlList);
	
	int addUnActiveWaybillRelateDetailEntityBatch(List<String> waybillNoList, String orderNo);

	int batchUpdateWaybillRelateDetailEntity(List<WaybillRelateDetailEntity> waybillChargeDtlList);
	
	/**
	 * 根据母单号查询对应所有的子单号
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-8 16:05:01
	 * @param waybillNo
	 * @return
	 */
	List<String> queryWaybillNoListByParentWaybillNo(String waybillNo);

	/**
	 * 根据单号、订单号进行数据查询
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-8 16:04:13
	 */
	List<WaybillRelateDetailEntity> queryWaybillRelateDetailListByOrderOrWaybillNo(Map<String, Object> params);

	/**
	 * 根据设定的条件删除对应的数据
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-10 17:08:04
	 * @param maps
	 * @return
	 */
	int deleteWaybillRelateDetailByParentWaybillNo(Map<String, Object> maps);
	
	/**
	 * 根据单号、订单号进行数据查询
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-8 16:04:13
	 */
	List<WaybillRelateDetailEntity> queryCommonLevelRelateDetailListByOrderOrWaybillNo(Map<String, Object> params);
	
	/**
	 * 根据Pda盲扫记录查询出同一接货任务中的子母单记录
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-12 20:56:39
	 * @param params
	 * @return
	 */
	List<WaybillRelateDetailEntity> queryRelateDetailListByPdaScanTaskId(Map<String, Object> params);

	int updateWaybillRelateDetailByWaybillNoSelective(WaybillRelateDetailEntity record);
	
	int deleteEwaybillRelateByPrimaryKey(String id);

    int insertEwaybillRelate(EwaybillRelateEntity record);

    int insertEwaybillRelateSelective(EwaybillRelateEntity record);

    EwaybillRelateEntity selectEwaybillRelateByPrimaryKey(String id);

    int updateEwaybillRelateByPrimaryKeySelective(EwaybillRelateEntity record);

    int updateEwaybillRelateByPrimaryKey(EwaybillRelateEntity record);
    
    List<EwaybillRelateEntity> queryAllEwaybillRelateByCommon(Map<String, Object> params);
    
    int updateEwaybillRelateForJob(GenerateUnActiveEWaybillVo vo);
    
    int deleteEcomWaybillRelateByPrimaryKey(String id);

    int insertEcomWaybillRelate(EcomWaybillRelateEntity record);

    int insertEcomWaybillRelateSelective(EcomWaybillRelateEntity record);

    EcomWaybillRelateEntity selectEcomWaybillRelateByPrimaryKey(String id);

    int updateEcomWaybillRelateByPrimaryKeySelective(EcomWaybillRelateEntity record);

    int updateEcomWaybillRelateByPrimaryKey(EcomWaybillRelateEntity record);
    
    /**
     * 根据运单号、订单号判定是否子母件
     * @author Foss-105888-Zhangxingwang
     * @date 2015-8-31 10:40:35
     * @param params
     * @return
     */
    TwoInOneWaybillDto queryWaybillRelateByWaybillOrOrderNo(Map<String, Object> params);

    /**
     * 根据原始订单号，运单号查询是否存在数据
     * @author Foss-105888-Zhangxingwang
     * @date 2015-8-31 19:28:44
     * @param ecomWaybillRelateList
     * @return
     */
	List<EcomWaybillRelateEntity> queryAllEwaybillRelateByEcomWaybillRelate(Map<String, Object> params);

	int updateEcomWaybillRelateByWaybillNoSelective(EcomWaybillRelateEntity record);
	
	/**
	 * 根据运单号集合来查询各个运单号详情 
	 * @param waybillNumList 运单集合
	 * @param start 起始页
	 * @param limit 每页显示记录数
	 * @param flag 是否启用分页，true:启用，则start,limit不能为空；false:不启用，则start,limit可为0
	 * @return List<WaybillRelateDetailEntity>子母运单信息集合
	 * @see queryWaybillRelateDetailsByWaybillNos
	 * @author 272311-sangwenhao
	 * 2015.09.06
	 */
	List<WaybillRelateDetailEntity> queryWaybillRelateDetailByWaybillNos(List<String> waybillNoList,int start,int limit,boolean flag) ;
	
	/**
	 * 根据运单号集合来查询各个运单号所对应的所有的子母单信息详情 
	 * @param waybillNumList 运单号集合
	 * @param start 起始页
	 * @param limit 每页显示记录数
	 * @param flag 是否启用分页，true:启用，则start,limit不能为空；false:不启用，则start,limit可为0
	 * @return List<WaybillRelateDetailEntity> 子母运单信息集合
	 * @see queryWaybillRelateDetailsByWaybillNos
	 * @author 272311-sangwenhao
	 * 2015.09.06
	 */
	List<WaybillRelateDetailEntity> queryWaybillRelateDetailsByWaybillNos(List<String> waybillNoList,int start,int limit,boolean flag) ;
	
	List<EcomWaybillRelateEntity> queryEcomWaybillRelateListByPdaScanTaskId(Map<String, Object> map);
	
	List<EcomWaybillRelateEntity> queryAllEwaybillRelateByEcomWaybillRelateAndPdascan(Map<String, Object> params);

	/**
	  * @description 执行子母件线程
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午3:41:56
	  * @param vo
	  * @return
	  * int
	   */
	int updateJobIDTopByRowNum(GenerateUnActiveEWaybillVo vo);
	
	/**
	  * @description 查询所有相同任务记录
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午6:50:52
	  * @param params
	  * @return
	  * List<EcomWaybillRelateEntity>
	 */
	
	List<EwaybillRelateEntity> queryEwaybillRelateByTaskId(Map<String, Object> params);
	
	int updateEwaybillRelateByPrimaryKeyNoSelective(EwaybillRelateEntity eWaybillRelateEntity);
	
	List<EcomWaybillRelateEntity> queryAllEwaybillRelateByEcomWaybillRelateWayBillNo(Map<String, Object> params);
	
	List<EwaybillRelateEntity> selectEwaybillRelateByPrimaryOrderNo(Map<String, Object> params);
	
	 int updateEcomWaybillRelateByWaybillByOrignalOrderNo(EcomWaybillRelateEntity ecomWaybillRelateEntity);

		/**
		 * <p>子母件激活线程处理，线程池满数据回滚操作，将jobId 置为N/A,状态置为N待于再执行一次子母件激活后续流程</p> 
		 * @author Foss-151211-yangtaohong 
		 * @date 2016-8-21 下午5:01:52
		 * @param id
		 * @return
		 * @see
		 */
		int updateJobIdForActive(String id);
 }
