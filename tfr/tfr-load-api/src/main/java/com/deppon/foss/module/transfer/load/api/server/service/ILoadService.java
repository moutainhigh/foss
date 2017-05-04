package com.deppon.foss.module.transfer.load.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillSerialNoDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.LoadSerialNoEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.QueryHandOverBillDto;

/** 
 * @className : ILoadService
 * @author : ShiWei shiwei@outlook.com
 * @description : 装车通用service
 * @date : 2013-4-2 下午4:07:56
 * 
 */
public interface ILoadService extends IService {
	
	/**
	 * 根据传入的部门code，获取该部门所属的营业部（派送部）、总调、外场
	 * 装、卸车部门转换均使用该方法
	 * @author 045923-foss-shiwei
	 * @date 2013-4-2 下午4:14:11
	 */
	OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(String orgCode);
	
	/**
	 * 根据传入的部门code，获取该部门所属的营业部(派送部)，总调、外场的code，如果获取到的部门为驻地部门，则返回驻地部门所属外场的code
	 * @author 045923-foss-shiwei
	 * @date 2013-4-16 上午10:13:35
	 */
	String querySuperiorOrgCodeIncludeTransferCenterByOrgCode(String orgCode);
	
	/**
	 * 校验车牌号是否可用，主要是检验是否解封签
	 * @author 045923-foss-shiwei
	 * @date 2013-4-17 上午10:39:10
	 */
	void validateVehicleNoCanBeUsed(String vehicleNo);
	
	/**
	 * 新增交接单时校验车牌号，做如下校验：对于指定的车牌号，存在有出发部门不是本部门、且车辆未出发的记录，则本部门不能使用该车牌号。
	 * @author 045923-foss-shiwei
	 * @date 2013-5-15 上午10:04:22
	 */
	void queryUndepartRecByVehicleNo(String origOrgCode,String vehicleNo,String state);
	
	/**
	 * 生成交接单编号
	 * @author 045923-foss-shiwei
	 * @date 2013-5-23 下午5:40:49
	 */
	String generateHandOverBillNo();
	
	/**
	 * 更新走货路径
	 * @author 045923-foss-shiwei
	 * @date 2013-5-30 上午10:54:10
	 */
	void callTransportPathService(HandOverBillEntity nowHandOverBillEntity,
			List<HandOverBillDetailEntity> allWaybillList,
			List<HandOverBillSerialNoDetailEntity> deletedSerialNoList,
			Map<String,Map<String,HandOverBillSerialNoDetailEntity>> allSerialNoMap);
	
	/**
	 * 漂移交接单待办
	 * @author 045923-foss-shiwei
	 * @date 2013-6-5 下午11:26:54
	 */
	int driftToDoAfterHandOvered(QueryHandOverBillDto baseDto,List<HandOverBillSerialNoDetailEntity> serialNoList);
	
	/**
	 * 
	 * 提供方法给清仓，查询给定时间前的装车扫描状态
	 * @author alfred
	 * @date 2013-9-5 下午4:10:33
	 * @param waybillNo
	 * @param serialNo
	 * @param loadStartTime
	 * @param origOrgCode
	 * @param scanState
	 * @return
	 * @see
	 */
	public List<LoadSerialNoEntity> queryLoadScanState(String waybillNo,String serialNo,Date loadStartTime,String origOrgCode);
	
	/**
	 * 同组织查询所属外场、空运总调、营业部（传入车队返回该顶级车队服务外场）
	 * @author 105869
	 * @date 2015年1月20日 16:37:24
	 * @Prama code
	 * @return OrgAdministrativeInfoEntity
	 * 
	 * */
	OrgAdministrativeInfoEntity querySuperOrgByOrgCode(String orgCode);

	/**
	 * @author nly
	 * @date 2015年5月16日 下午5:49:39
	 * @function 查询落地配装车信息
	 * @param waybillNo
	 * @param serialNo
	 * @param deptCode
	 * @return
	 */
	List<LoadSerialNoEntity> queryLdpLoadScanInfo(String waybillNo,	String serialNo, String deptCode);
	/**
	 * 如果是派送中的运单就不允许发更改
	 * @param waybillNo
	 * @return
	 */
	boolean ifCouldBeChangedeWaybillNo(String waybillNo);
    /**
	 * * 同组织查询所属外场（传入车队返回该顶级车队服务外场）
	 * @author 189284
	 * @date 2015年10月12日 16:37:24
	 * @Prama code
	 * @return OrgAdministrativeInfoEntity
	 * @see com.deppon.foss.module.transfer.load.api.server.service.ILoadService#querySuperOrgByOrgCode(java.lang.String)
	 */
	OrgAdministrativeInfoEntity queryTCOrgByOrgCode(String orgCode);
}
