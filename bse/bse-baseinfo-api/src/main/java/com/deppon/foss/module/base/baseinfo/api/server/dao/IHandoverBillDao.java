package com.deppon.foss.module.base.baseinfo.api.server.dao;

import java.util.List;
import java.util.Set;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.HandoverBillInfoEntity;

/**
 * 
 *  交单管理 dao 接口
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:189284,date:2015-4-9 上午11:43:00,content:TODO </p>
 * @author 189284 
 * @date 2015-4-9 上午11:43:00
 * @since
 * @version
 */
public interface IHandoverBillDao {
	/**
	 * 
	 * <p>新增 交单管理 基础信息</p> 
	 * @author 189284 
	 * @date 2015-4-9 上午11:53:45
	 * @param handoverBillInfoEntity (时间建模)
	 * @param user（操作人）
	 * @return
	 * @see
	 */
	int addHandoverBill(HandoverBillInfoEntity handoverBillInfoEntity,String user);
   /**
    * 
    * <p>根据条件查询 交单管理 信息（有效）</p> 
    * @author 189284 
    * @date 2015-4-9 下午12:10:48
    * @param handoverBillInfoEntity
    * @param start
    * @param limit
    * @return
    * @see
    */
	List<HandoverBillInfoEntity> qurey(
			HandoverBillInfoEntity handoverBillInfoEntity,Set<String> orgids, int start, int limit);
	/**
	 * 
	 * <p>根据条件查询 交单管理 信息（有效） 总数</p> 
	 * @author 189284 
	 * @date 2015-4-9 下午12:11:43
	 * @param handoverBillInfoEntity
	 * @return
	 * @see
	 */
    long quryCount(HandoverBillInfoEntity handoverBillInfoEntity,Set<String> orgids);
    /**
     * 
     * <p>根据IDs  批量  作废 交单管理 信息</p> 
     * @author 189284 
     * @date 2015-4-9 下午12:22:45
     * @param ids
     * @return
     * @see
     */
	int deleteHandoverBillInfo(List<String> ids);
	/**
	 * 
	 * <p>根据id修改 交单管理新增</p> 
	 * @author 189284 
	 * @date 2015-4-20 下午7:18:15
	 * @param handoverBillInfo
	 * @param empCode
	 * @see
	 */
	void updateHandoverBillInfo(HandoverBillInfoEntity handoverBillInfo,
			String empCode);
	/**
	 * 
	 * <p>根据部门编码 查询交单信息</p> 
	 * @author 189284 
	 * @date 2015-5-21 上午10:31:25
	 * @param Department 交单部门code
	 * @return
	 * @see
	 */
	HandoverBillInfoEntity queryHandoverBillInfoByDepartment(String department);

}
