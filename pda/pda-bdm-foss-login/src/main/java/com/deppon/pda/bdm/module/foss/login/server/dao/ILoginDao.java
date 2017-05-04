package com.deppon.pda.bdm.module.foss.login.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.pda.bdm.module.core.shared.domain.DeptEntity;
import com.deppon.pda.bdm.module.core.shared.domain.PdaLoginInfo;
import com.deppon.pda.bdm.module.foss.login.shared.domain.DepartAssemblyDeptEntity;
import com.deppon.pda.bdm.module.foss.login.shared.domain.DestDeptInfoEntity;
import com.deppon.pda.bdm.module.foss.login.shared.domain.PrivilegeEntity;
import com.deppon.pda.bdm.module.foss.login.shared.domain.UserInfoEntity;

/**
 * 
  * @ClassName ILoginDao 
  * @Description TODO 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public interface ILoginDao {
	/**
	 * 
	 * <p>TODO(检验用户是否已登录)</p> 
	 * @author Administrator
	 * @date 2012-11-28 下午5:08:04
	 * @param pdaLoginInfo
	 * @return
	 * @see
	 */
	public boolean checkUserLogin(PdaLoginInfo pdaLoginInfo);
	/**
	 * 
	 * <p>TODO(检验该用户是否在该PDA登录)</p> 
	 * @author Administrator
	 * @date 2012-11-28 下午5:25:08
	 * @param pdaLoginInfo
	 * @return
	 * @see
	 */
	public boolean checkUserPdaLogin(PdaLoginInfo pdaLoginInfo);
	/**
	 * 
	 * <p>TODO(保存用户登录西信息)</p> 
	 * @author Administrator
	 * @date 2012-11-28 下午5:52:49
	 * @param pdaLoginInfo
	 * @see
	 */
	public void saveLoginInfo(PdaLoginInfo pdaLoginInfo);

	/**
	 * 
	 * <p>TODO(登陆)</p> 
	 * @author Administrator
	 * @date 2012-11-28 下午5:52:49
	 * @param pdaLoginInfo
	 * @see
	 */
	public int login(PdaLoginInfo pdaLoginInfo);
	public List<PrivilegeEntity> getUserPrivilege(String userCode);
	
	/**
	 * 
	 * <p>TODO(根据部门ID获得该部门下所有员工信息)</p> 
	 * @author chengang
	 * @date 2013-5-21 下午2:49:13
	 * @param deptId
	 * @return
	 * @see
	 */
	public List<UserInfoEntity> getUserInfoByDeptId(String deptId);
	/**
	 * 
	 * <p>TODO(获取当前部门长途目的站)</p> 
	 * @author Administrator
	 * @date 2013-6-4 上午4:20:22
	 * @param map
	 * @return
	 * @see
	 */
	public List<DestDeptInfoEntity> getLongDestDepts(Map<String, String> map);
	/**
	 * 
	 * <p>TODO(获取当前部门短途目的站)</p> 
	 * @author Administrator
	 * @date 2013-6-4 上午7:00:04
	 * @param map
	 * @return
	 * @see
	 */
	public List<DestDeptInfoEntity> getShortDestDepts(Map<String, String> map);
	/**
	 * 
	 * <p>TODO(获取营业部装车目的站)</p> 
	 * @author Administrator
	 * @date 2013-6-4 上午7:27:57
	 * @param map
	 * @return
	 * @see
	 */
	public List<DestDeptInfoEntity> getSalesShortDestDepts(
			Map<String, String> map);
	
	/**
	 * 
	 * <p>TODO(通过车队code获取服务的外场code)</p> 
	 * @author Administrator
	 * @date 2013-8-19 上午7:27:57
	 * @param platoonCode  车队Code
	 * @return
	 * @see
	 */
	public String getOutStorageCode(
			String platoonCode);
	
	/**
	 * 
	* @Description: TODO 通过快递员营业部获取快递配载外场部门
	* @param deptId
	* @return 
	* @return DepartAssemblyDeptEntity
	* @author zhangzhenxian
	* @date 2013-9-18 下午12:33:44
	 */
	public DepartAssemblyDeptEntity getDepartAssemblyDept(String deptId);
	/**
	 * 
	 * <p>TODO(根据外场编码查找对应的始发驻地营业部编码)</p> 
	 * @author 高佳
	 * @date 2014-3-3 上午10:33:49
	 * @param transferCode
	 * @return
	 * @see
	 */
	public DeptEntity querySourceStaionSalesDept(String transferCode);
	/**
	 * 
	 * <p>TODO(查找部门级别的时效区域)</p> 
	 * @author 高佳
	 * @date 2014-3-3 上午10:47:30
	 * @param sourceStaionSalesDept
	 * @return
	 * @see
	 */
	public String queryEffRegionOrg(String sourceStaionSalesDept);
	/**
	 * 
	 * <p>TODO(按区查找时效区域)</p> 
	 * @author 高佳
	 * @date 2014-3-3 上午11:13:58
	 * @param deptCounty
	 * @return
	 * @see
	 */
	public String queryRegionByCounty(String deptCounty);
	/**
	 * 
	 * <p>TODO(按市查找时效区域)</p> 
	 * @author 高佳
	 * @date 2014-3-3 上午11:13:58
	 * @param deptCounty
	 * @return
	 * @see
	 */
	public String queryRegionByCity(String deptCity);
	/**
	 * 
	 * <p>TODO(按省查找时效区域)</p> 
	 * @author 高佳
	 * @date 2014-3-3 上午11:13:58
	 * @param deptCounty
	 * @return
	 * @see
	 */
	public String queryRegionByProvince(String deptProvince);
	
	
	
	/**
	 * 快递
	 * <p>TODO(查找部门级别的时效区域)</p> 
	 * @author 张贞献
	 * @date 2014-8-27 上午14:11:00
	 * @param sourceStaionSalesDept
	 * @return
	 * @see
	 */
	public String queryExpEffRegionOrg(String sourceStaionSalesDept);
	/**
	 * 
	 * <p>TODO(按区查找时效区域)</p> 
	 * @author 张贞献
	 * @date 2014-8-27 上午14:11:00
	 * @param deptCounty
	 * @return
	 * @see
	 */
	public String queryExpRegionByCounty(String deptCounty);
	/**
	 * 
	 * <p>TODO(按市查找时效区域)</p> 
	 * @author 张贞献
	 * @date 2014-8-27 上午14:11:00
	 * @param deptCounty
	 * @return
	 * @see
	 */
	public String queryExpRegionByCity(String deptCity);
	/**
	 * 
	 * <p>TODO(按省查找时效区域)</p> 
	 * @author 张贞献
	 * @date 2014-8-27 上午14:11:00
	 * @param deptCounty
	 * @return
	 * @see
	 */
	public String queryExpRegionByProvince(String deptProvince);
	
	
	/**
	 * 
	 * <p>TODO(根据编码查部门)</p> 
	 * @author 张贞献
	 * @date 2014-8-27 上午14:11:00
	 * @param deptCounty
	 * @return
	 * @see
	 */
	public DeptEntity queryDeptByCode(String deptCode);
	
	/**   
	 * @Title: queryExpressBranch  
	 * @Description: 根据工号查询对应营业映射的点部编码
	 * @param @param userCode
	 * @param @return    设定文件   
	 * @return String    返回类型  
	 * @throws  
	 */
	public String queryExpressBranch(String userCode);
	/**
	 * 
	 * <p>TODO(每次更新PDA SIM卡序列号)</p> 
	 * @author 298403
	 * @date 2016-03-21 下午5:08:04
	 * @param pdaLoginInfo
	 * @return
	 * @see
	 */
	public void updatePdaSimCardCode(PdaLoginInfo pdaLoginInfo);
}
