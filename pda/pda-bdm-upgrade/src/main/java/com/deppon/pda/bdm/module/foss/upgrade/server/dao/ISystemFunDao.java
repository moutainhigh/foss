package com.deppon.pda.bdm.module.foss.upgrade.server.dao;


import java.util.List;

import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.BaseDataVerEntity;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.DepartAssemblyDept;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.foss.upgrade.shared.domain.UserEntity;


  
/**
 * 
 * TODO(系统模块DAO接口)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:chengang,date:2013-3-20 下午5:31:33,content:TODO </p>
 * @author chengang
 * @date 2013-3-20 下午5:31:33
 * @since
 * @version
 */

public interface ISystemFunDao {
	/**
	 * 
	 * <p>TODO(查询数据版本信息)</p> 
	 * @author chengang
	 * @date 2012-12-23 上午4:14:12
	 * @return
	 * @see
	 */
	public BaseDataVerEntity getDataVerInfo();
	
	/**
	 * 
	 * <p>TODO(保存版本信息)</p> 
	 * @author chengang
	 * @date 2012-12-23 上午4:13:57
	 * @param basEntity
	 * @see
	 */
	public void saveBaseDataVer(BaseDataVerEntity basEntity);
	
	/**
	 * 
	 * <p>TODO(修改版本信息)</p> 
	 * @author chengang
	 * @date 2012-12-23 上午4:13:43
	 * @param basEntity
	 * @see
	 */
	public void updDataVer(BaseDataVerEntity basEntity);
	
	/**
	 * 
	 * <p>TODO(查询用户信息)</p> 
	 * @author chengang
	 * @date 2012-12-23 上午4:13:27
	 * @param userCode
	 * @return
	 * @see
	 */
	public UserEntity getUserInfo(String userCode);
	
	/**
	 * 
	 * <p>TODO(查询部门信息)</p> 
	 * @author chengang
	 * @date 2012-12-28 下午3:25:15
	 * @param deptCode
	 * @return
	 * @see
	 */
	public DeptEntity getDeptCode(String deptCode);
	/**
	 * 
	 * @param topFleetCode
	 * @return
	 * @description 通过顶级车队编码查询出对应的开单组Code 
	 * @version 1.0
	 * @author wenwuneng 
	 * @update 2013-8-10 下午4:05:36
	 */
	 
	 
	public String getBillGroupCodeByFleetCode(String topFleetCode);
	
	/**
	 * 
	 * @param salesDeptCode
	 * @return
	 * @description 通过快递员所属部门到始发配载表中查询配载部门 
	 * @version 1.0
	 * @author xujun 
	 * @update 2013-10-07 下午11:05:36
	 */
	 
	public DepartAssemblyDept getDepartAssemblyDept(String salesDeptCode);
	/**
	 * 
	 * <p>TODO(根据快递员工号查找对应的出发部门)</p> 
	 * @author Administrator
	 * @date 2013-10-19 上午9:45:35
	 * @param userCode
	 * @return
	 * @see
	 */
	public String getCourierSourceDeptCode(String userCode);
	/**
	 * 
	 * <p>TODO(查找车队所服务的外场)</p> 
	 * @author Administrator
	 * @date 2013-10-27 下午3:15:34
	 * @param topFleetCode
	 * @return
	 * @see
	 */
	public String queryTransCenter(String topFleetCode);
	/**
	 * 通过快递deptCode 从部门区域关系表中查找区域编码
	 */
	
	public List<String> getExpressRegionCodeByDeptCode(String deptCode);
	/**
	 * 通过 令零担deptCode 从部门区域关系表中查找区域编码
	 */
	
	public List<String> getRegionCodeByDeptCode(String deptCode);
	/**
	 根据部门编码查找部门组织信息
	 */
	public DeptEntity getDeptInfoByCode(String deptCode);
	/**
	 * 通过区县编码查找时效区域编码查找--零担
	 */
	public String getRegionByCount(String deptCounty);
	/**
	 * 通过城市编码找时效区域编码查找 --零担
	 */
	public String getRegionByCity(String deptCity);
	/**
	 * 通过省编码找时效区域编码查找--零担
	 */
	public String getRegionByProvince(String deptProvince);

	public String queryResidentDeptCode(String sourceTransCenter);
	/**
	 * 通过区县编码查找时效区域编码查找 ---------快递
	 */
	public String getExpressRegionByCount(String deptCounty);
	/**
	 * 通过城市编码找时效区域编码查找 --快递
	 */

	public String getExpressRegionByCity(String deptCity);
	/**
	 * 通过省编码找时效区域编码查找--快递
	 */
	public String getExpressRegionByProvince(String deptProvince);
	/**
	 * 
	 * <p>TODO(获取下载数据服务器域名与端口号)</p> 
	 * @author Administrator
	 * @date 2014-3-20 下午2:00:48
	 * @return
	 * @see
	 */
	public String queryHttpHost();
	
}
