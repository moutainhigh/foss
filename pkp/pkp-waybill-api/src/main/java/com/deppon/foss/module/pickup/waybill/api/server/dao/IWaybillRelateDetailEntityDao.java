package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.waybill.shared.domain.EcomWaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.EwaybillRelateEntity;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillRelateDetailEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.LastWaybillRfcQueryDto;
import com.deppon.foss.module.pickup.waybill.shared.vo.GenerateUnActiveEWaybillVo;
/**
 * 子母件基础数据数据持久接口层
 * @author Foss-105888-Zhangxingwang
 * @date 2015-8-6 17:35:44
 */
public interface IWaybillRelateDetailEntityDao {
    int deleteWaybillRelateDetailByPrimaryKey(String id);

    int insertWaybillRelateDetail(WaybillRelateDetailEntity record);

    int insertWaybillRelateDetailSelective(WaybillRelateDetailEntity record);

    WaybillRelateDetailEntity selectWaybillRelateDetailByPrimaryKey(String id);

    int updateWaybillRelateDetailByPrimaryKeySelective(WaybillRelateDetailEntity record);

    int updateWaybillRelateDetailByPrimaryKey(WaybillRelateDetailEntity record);

	int insertWaybillRelateDetailBatchSelective(List<WaybillRelateDetailEntity> waybillRelateDtlList);

	List<WaybillRelateDetailEntity> queryNewWaybillRelateDtlEntityByNo(LastWaybillRfcQueryDto queryDto);

	List<WaybillRelateDetailEntity> queryWaybillRelateDetailListByOrderOrWaybillNo(Map<String, Object> params);

	int batchUpdateWaybillRelateDetailEntity(List<WaybillRelateDetailEntity> waybillChargeDtlList);

	List<String> queryWaybillNoListByParentWaybillNo(String waybillNo);
	
	int deleteWaybillRelateDetailByParentWaybillNo(Map<String, Object> maps);

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

	/**
	 * 根据子单号判定该单是否为子母单
	 * @author Foss-105888-Zhangxingwang
	 * @date 2015-8-28 08:55:10
	 * @param record
	 * @return
	 */
	WaybillRelateDetailEntity queryWaybillRelateDetailByWaybillNo(String waybillNo);
	
	/**
	 *根据子单号判定该单是否为母件
	 *@author 283250 -Liuyi
	 * @date 2015-9-24 15:59:10
	 * @param record
	 * @return
	 */
	WaybillRelateDetailEntity queryWaybillParentRelateDetailByWaybillNo(String waybillNo);
	
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
	
	/**
	 * TODO 根据原始订单号查询dop同步数据和pda扫描表重量体积
	 * @param originalNumber
	 * @author foss-206860
	 * @date 2015-9-11 下午2:45:54
	 */
	List<EcomWaybillRelateEntity> queryAllEwaybillRelateByEcomWaybillRelateAndPdascan(Map<String, Object> params);
	
	List<EcomWaybillRelateEntity> queryEcomWaybillRelateListByPdaScanTaskId(Map<String, Object> params);

	/**
	  * @description 
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午3:43:04
	  * @param vo
	  * void
	 * @return 
	   */
	int updateEWaybillRelateJobIDTopByRowNum(GenerateUnActiveEWaybillVo vo);
	
	/**
	  * @description 查询记录任务相同的记录
	  * @author Foss-270293-Zhangfeng
	  * @date 2015-9-13 t下午6:45:44
	  * @param params
	  * @return
	  * List<EwaybillRelateEntity>
	 */
    List<EwaybillRelateEntity> queryEwaybillRelateByTaskId(Map<String, Object> params); 
    
    int updateEwaybillRelateByPrimaryKeyNoSelective(EwaybillRelateEntity ewaybillRelateEntity);
    
    List<EcomWaybillRelateEntity> queryAllEwaybillRelateByEcomWaybillRelateWayBillNo(Map<String, Object> params);
    
    List<EwaybillRelateEntity> selectEwaybillRelateByPrimaryOrderNo(Map<String, Object> params);
    
    int updateEcomWaybillRelateByWaybillByOrignalOrderNo(EcomWaybillRelateEntity ecomWaybillRelateEntity);

	/**
	 * <p>子母件激活线程处理，线程池满数据回滚操作，将jobId 置为N/A,状态置为N待于再执行一次子母件激活后续流程</p> 
	 * @author Foss-151211-yangtaohong 
	 * @date 2016-8-14 下午5:36:53
	 * @param id
	 * @return
	 * @see
	 */
	int updateJobIdForActive(String id);
}