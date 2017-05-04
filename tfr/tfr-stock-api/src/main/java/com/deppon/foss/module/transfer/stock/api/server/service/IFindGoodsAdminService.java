package com.deppon.foss.module.transfer.stock.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.GoodsAreaEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminDetailEntity;
import com.deppon.foss.module.transfer.stock.api.shared.domain.FindGoodsAdminEntity;
import com.deppon.foss.module.transfer.stock.api.shared.vo.FindGoodsAdminVo;


/**
 * 
* @ClassName: IFindGoodsAdminService
* @Description: 找货管理接口
* @author 189284--ZX
* @date 2015-7-10 下午4:41:53
*
 */
public interface IFindGoodsAdminService {
	/**
	 * 
	* @Title: qureyFindGoodsAdmin
	* @Description: 查询 找货管理信息 分页
	* @author 189284--ZX
	* @param @param findGoodsAdminEntity
	* @param @param start
	* @param @param limit
	* @param @return    设定文件
	* @return List<FindGoodsAdminEntity>    返回类型
	* @throws
	 */
	List<FindGoodsAdminEntity> qureyFindGoodsAdmin( FindGoodsAdminEntity findGoodsAdminEntity, int start, int limit);
	/**
	 * 
	* @Title: qureyFindGoodsAdminCount
	* @Description: 根据条件查询 找货管理 总数
	* @author 189284--ZX
	* @param @param findGoodsAdminEntitys
	* @param @return    设定文件
	* @return Long    返回类型
	* @throws
	 */
	Long qureyFindGoodsAdminCount(FindGoodsAdminEntity findGoodsAdminEntitys);
	/**
	 * 
	* @Title: querySuperiorOrgByOrgCode
	* @Description: 根据当前部门code获取上级外场  或者营业部
	* @author 189284--ZX
	* @param @param currentDeptCode
	* @param @return    设定文件
	* @return OrgAdministrativeInfoEntity    返回类型
	* @throws
	 */
	public OrgAdministrativeInfoEntity querySuperiorOrgByOrgCode(
			String currentDeptCode);
	/**
	 * 
	* @Title: commitFindGoodsAdmin
	* @Description: 根据 任务编号 提交找货管理信息
	* @author 189284--ZX
	* @param @param taskNo    设定文件
	* @return void    返回类型
	* @throws
	 */
	String commitFindGoodsAdmin(String taskNo,String user);
	/**
	 * 
	* @Title: FindGoodsAdminInsert
	* @Description:新增 找货管理信息 
	* @author 189284--ZX
	* @param @param findGoodsAdminEntity
	* @param @param findGoodsAdminDetailEntitys    设定文件
	* @return void    返回类型
	* @throws
	 */
	int FindGoodsAdminInsert(FindGoodsAdminEntity findGoodsAdminEntity);
	 /**
	  * 
	 * @Title: findGoodsAdminDetailInsert
	 * @Description:新增 找货管理信息 明细
	 * @author 189284--ZX
	 * @param @param findGoodsAdminDetailEntitys
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	  */
	int findGoodsAdminDetailInsert(
			List<FindGoodsAdminDetailEntity> findGoodsAdminDetailEntitys);
	/**
	 * 
	* @Title: qureyGoodsAreaByCode
	* @Description: 提供给pda 查询 当前部门下的库区（如果为营业部 返回null）
	* @author 189284--ZX
	* @param @param organizationCode
	* @param @return    设定文件
	* @return List<GoodsAreaEntity>    返回类型
	* @throws
	 */
	List<GoodsAreaEntity> qureyGoodsAreaByCode(String organizationCode);
	/***
	  * 
	 * @Title: scanFGoodsfgoodsDetail
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @author 189284--ZX
	 * @param waybillNo运单号
	 * @param serialNo流水
	 * @param user修改人
	 * @param orgCode部门
	 * @param @return    设定文件
	 * @return int    返回类型
	 * @throws
	  */
	String scanFGoodsfgoodsDetail(String waybillNo, String serialNo, String user,String orgCode);
	 /**
	  * 
	 * @Title: qureyFGoodsDetailByTaskNo
	 * @Description: 根据任务号 查询明细
	 * @author 189284--ZX
	 * @param @param taskNo
	 * @param @return    设定文件
	 * @return List<FindGoodsAdminDetailEntity>    返回类型
	 * @throws
	  */
	List<FindGoodsAdminDetailEntity> qureyFGoodsDetailBytaskNo(String taskNo);
	/**
	 * 
	* @Title: createFindGoodsTask
	* @Description: Pda 创建 找货任务（拉去明细 同时 保存到本地）
	* @author 189284--ZX
	* @param  findGoodsAdminEntity.goodsAreaCode货区
	* @param  findGoodsAdminEntity.orgCode部门
	* @param  findGoodsAdminEntity.findGoodsManCode找货人
	* @param  findGoodsAdminEntity.taskCreateDate开始上报时间
	* @param  findGoodsAdminEntity.taskEndDate结束上报时间
	* @param @return    设定文件
	* @return FindGoodsAdminVo 返回类型
	* findGoodsAdminDetailEntitys 明细 list
	* @throws
	 */
	FindGoodsAdminVo  createFindGoodsTask(
			FindGoodsAdminEntity findGoodsAdminEntity);
	/**
	 * 
	 * @Title: queryFindGoodsAdminDeatil
	 * @Description: pda 创建找货任务的时间 查明细
	 * @author 189284--ZX
	 * @param findGoodsAdminEntity.goodsAreaCode 货区Code'
	 * @param findGoodsAdminEntity.orgCode 货区所属外场Code
	 * @param findGoodsAdminEntity.taskCreateDate 上报开始时间
	 * @param findGoodsAdminEntity.taskEndDate 上报结束时间
	 * @return List<FindGoodsAdminDetailEntity> 返回类型
	 */
	List<FindGoodsAdminDetailEntity> queryFindGoodsAdminDeatil(
			FindGoodsAdminEntity findGoodsAdminEntity);

	
}
