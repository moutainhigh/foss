package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.HandoverBillInfoEntity;

public interface IHandoverBillInfoService extends IService{
	/**
	 * 
	 * <p>交单管理新增</p> 
	 * @author 189284 
	 * @date 2015-4-16 上午10:51:45
	 * @param handoverBillInfo
	 * @param empCode
	 * @see
	 */
	void addHandoverBillInfo(HandoverBillInfoEntity handoverBillInfo,
			String empCode);
	/**
	 * 
	 * <p>根据条件,  分页查询 交单管理信息</p> 
	 * @author 189284 
	 * @date 2015-4-17 上午9:45:01
	 * @param handoverBillInfo
	 * @param start
	 * @param limit
	 * @return
	 * @see
	 */
	List<HandoverBillInfoEntity> queryHandoverBillInfoList(
			HandoverBillInfoEntity handoverBillInfo, int start, int limit);
	/**
	 * 
	 * <p>根据条件,查询 交单管理信息   总数</p> 
	 * @author 189284 
	 * @date 2015-4-17 上午9:45:44
	 * @param handoverBillInfo
	 * @return
	 * @see
	 */
	Long queryHandoverBillInfoCount(HandoverBillInfoEntity handoverBillInfo);
	/**
	 * 
	 * <p>修改交单信息</p> 
	 * @author 189284 
	 * @date 2015-4-17 上午9:59:36
	 * @param handoverBillInfo
	 * @param empCode
	 * @see
	 */
	void updateHandoverBillInfo(HandoverBillInfoEntity handoverBillInfo,
			String empCode);
	/**
	 * 
	 * <p>批量作废 交单信息 根据ids</p> 
	 * @author 189284 
	 * @date 2015-4-17 上午10:00:18
	 * @param ids
	 * @see
	 */
	void deleteHandoverBillInfo(List<String> ids);
	/**
	 * 
	 * <p>根据部门编码 查询交单信息</p> 
	 * @author 189284 
	 * 提供给 结算的接口
	 * @date 2015-5-21 上午10:31:25
	 * @param Department 交单部门code
	 * @return
	 * @see
	 */
   HandoverBillInfoEntity queryHandoverBillInfoByDepartment(String department );

}
