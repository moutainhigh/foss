/**   
* @Title: DailyLoadVolumeAction.java 
* @Package com.deppon.foss.module.transfer.platform.server.action 
* @Description: 日承载货量action
* @author shiwei-045923-shiwei@outlook.com
* @date 2014年6月26日 下午4:49:53 
* @version V1.0   
*/
package com.deppon.foss.module.transfer.platform.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.common.api.server.service.IBusinessLockService;
import com.deppon.foss.module.base.common.api.shared.define.MutexElementType;
import com.deppon.foss.module.base.common.api.shared.dto.MutexElement;
import com.deppon.foss.module.transfer.common.api.shared.exception.TfrBusinessException;
import com.deppon.foss.module.transfer.platform.api.server.service.IDailyLoadVolumeService;
import com.deppon.foss.module.transfer.platform.api.shared.define.DailyLoadVolumeConstants;
import com.deppon.foss.module.transfer.platform.api.shared.domain.DailyLoadVolumeEntity;
import com.deppon.foss.module.transfer.platform.api.shared.vo.DailyLoadVolumeVo;

/** 
 * @ClassName: DailyLoadVolumeAction 
 * @Description: 日承载货量action
 * @author shiwei-045923-shiwei@outlook.com
 * @date 2014年6月26日 下午4:49:53 
 *  
 */
public class DailyLoadVolumeAction extends AbstractAction {

	/** 
	* @Fields serialVersionUID
	*/ 
	private static final long serialVersionUID = -4937643250380376959L;
	
	/**
	 * service，业务操作
	 */
	private IDailyLoadVolumeService dailyLoadVolumeService;
	
	/**
	 * vo，前后传参
	 */
	private DailyLoadVolumeVo dailyLoadVolumeVo = new DailyLoadVolumeVo();
	
	/**
	 * 业务锁
	 */
	private IBusinessLockService businessLockService;

	/** 
	 * @param businessLockService 要设置的 businessLockService 
	 */
	public void setBusinessLockService(IBusinessLockService businessLockService) {
		this.businessLockService = businessLockService;
	}

	/** 
	 * @param dailyLoadVolumeService 要设置的 dailyLoadVolumeService 
	 */
	public void setDailyLoadVolumeService(
			IDailyLoadVolumeService dailyLoadVolumeService) {
		this.dailyLoadVolumeService = dailyLoadVolumeService;
	}

	/** 
	 * @return dailyLoadVolumeVo 
	 */
	public DailyLoadVolumeVo getDailyLoadVolumeVo() {
		return dailyLoadVolumeVo;
	}

	/** 
	 * @param dailyLoadVolumeVo 要设置的 dailyLoadVolumeVo 
	 */
	public void setDailyLoadVolumeVo(DailyLoadVolumeVo dailyLoadVolumeVo) {
		this.dailyLoadVolumeVo = dailyLoadVolumeVo;
	}
	
	/**
	* @Title: queryDailyLoadVolumeList 
	* @Description: 分页查询
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月26日 下午5:01:32 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String queryDailyLoadVolumeList(){
		DailyLoadVolumeEntity dailyLoadVolumeEntity = dailyLoadVolumeVo.getDailyLoadVolumeEntity();
		//获取查询结果
		List<DailyLoadVolumeEntity> dailyLoadVolumeEntityList = dailyLoadVolumeService.queryDailyLoadVolumeList(dailyLoadVolumeEntity, this.getLimit(), this.getStart());
		Long count = dailyLoadVolumeService.queryDailyLoadVolumeCount(dailyLoadVolumeEntity);
		this.setTotalCount(count);
		dailyLoadVolumeVo.setDailyLoadVolumeEntityList(dailyLoadVolumeEntityList);
		return SUCCESS;
	}
	
	/**
	* @Title: queryDailyLoadVolumeNoPaging 
	* @Description: 查询历史记录
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月26日 下午5:08:32 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public String queryDailyLoadVolumeNoPaging(){
		DailyLoadVolumeEntity dailyLoadVolumeEntity = dailyLoadVolumeVo.getDailyLoadVolumeEntity();
		//获取查询结果
		List<DailyLoadVolumeEntity> dailyLoadVolumeEntityList = dailyLoadVolumeService.queryDailyLoadVolumeNoPaging(dailyLoadVolumeEntity);
		dailyLoadVolumeVo.setDailyLoadVolumeEntityList(dailyLoadVolumeEntityList);
		return SUCCESS;
	}
	
	/**
	* @Title: addDailyLoadVolumeEntity 
	* @Description: 新增
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月26日 下午5:10:58 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@JSON
	public String addDailyLoadVolumeEntity(){
		DailyLoadVolumeEntity dailyLoadVolumeEntity = dailyLoadVolumeVo.getDailyLoadVolumeEntity();
		//获取转运场code
		String orgCode = dailyLoadVolumeEntity.getOrgCode();
		// 出发部门，到达部门，发车日期
		String lockStr = orgCode + DailyLoadVolumeConstants.ADD;
		MutexElement mutex = new MutexElement(lockStr, "新增日承载货量", MutexElementType.TFR_DAILYLOADVOLUME_ADD);
		// 锁定
		boolean flag = businessLockService.lock(mutex, 0);
		if(flag){
			try{
				dailyLoadVolumeService.addDailyLoadVolumeEntity(dailyLoadVolumeEntity);
			}catch(BusinessException e){
				return returnError(e);
			}finally{
				businessLockService.unlock(mutex);
			}
		}else{
			throw new TfrBusinessException("该转运场正在新增日承载货量信息，请稍后重试。");
		}
		return SUCCESS;
	}
	
	/**
	* @Title: updateDailyLoadVolumeEntity 
	* @Description: 更新日承载货量信息 
	* @author shiwei-045923-shiwei@outlook.com
	* @date 2014年6月26日 下午6:14:33 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@JSON
	public String updateDailyLoadVolumeEntity(){
		DailyLoadVolumeEntity dailyLoadVolumeEntity = dailyLoadVolumeVo.getDailyLoadVolumeEntity();
		//获取转运场code
		String orgCode = dailyLoadVolumeEntity.getOrgCode();
		// 出发部门，到达部门，发车日期
		String lockStr = orgCode + DailyLoadVolumeConstants.UPDATE;
		MutexElement mutex = new MutexElement(lockStr, "新增日承载货量", MutexElementType.TFR_DAILYLOADVOLUME_UPDATE);
		// 锁定
		boolean flag = businessLockService.lock(mutex, 0);
		if(flag){
			try{
				dailyLoadVolumeService.updateDailyLoadVolumeEntity(dailyLoadVolumeEntity);
			}catch(BusinessException e){
				return returnError(e);
			}finally{
				businessLockService.unlock(mutex);
			}
		}else{
			throw new TfrBusinessException("该转运场正在新增日承载货量信息，请稍后重试。");
		}
		return SUCCESS;
		
	}
	
	

}
