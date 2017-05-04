
package com.deppon.foss.module.transfer.platform.server.action;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.transfer.platform.api.server.service.ILoadingProgressService;
import com.deppon.foss.module.transfer.platform.api.shared.dto.LoadingProgressResultDto;
import com.deppon.foss.module.transfer.platform.api.shared.vo.LoadingProgressVo;

/**
* @ClassName: QueryLoadingProgressAction 
* @Description: 月台项目-查询装车进度action类
* @author shiwei shiwei@outlook.com
* @date 2014年4月12日 上午11:06:13 
*
 */
public class LoadingProgressAction extends AbstractAction {
	
	/**
	 * 查询装车进度接口
	 */
	private ILoadingProgressService loadingProgressService;


	/**
	 * 序列UID
	 */
	private static final long serialVersionUID = -439129558156685679L;
	/**
	 * 装车进度vo
	 */
	private LoadingProgressVo loadingProgressVo = new LoadingProgressVo();
	
	/**
	 * 
	 * 查询装车进度
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-19 下午4:52:54
	 */
	@JSON
	public String queryLoadingProgressInfo(){
		try {
			//查询装车进度 参数：查询条件dto,页大小，开始
			List<LoadingProgressResultDto> queryLoadingProgressResultList = 
					loadingProgressService.queryLoadingProgressInfo(
					loadingProgressVo.getLoadingProgressConditionDto(),
					loadingProgressVo.getPageSize(), 
					loadingProgressVo.getInitStart());
			
			Integer count = loadingProgressService
					.queryLoadingProgressInfoCount(loadingProgressVo
							.getLoadingProgressConditionDto(), 
							loadingProgressVo.getPageSize(), 
							loadingProgressVo.getInitStart());

			loadingProgressVo.setLoadingProgressResultList(queryLoadingProgressResultList);
			loadingProgressVo.setCount(count);
			return returnSuccess();
		} catch (BusinessException e) {
			return returnError(e);
		}
	}

	/**
	 * 获取 vo.
	 *
	 * @return the vo
	 */
	public LoadingProgressVo getLoadingProgressVo() {
		return loadingProgressVo;
	}

	/**
	 * 设置 vo.
	 *
	 * @param queryLoadingProgressVo the new vo
	 */
	public void LoadingProgressVo(LoadingProgressVo loadingProgressVo) {
		this.loadingProgressVo = loadingProgressVo;
	}

	/** 
	 * @param loadingProgressService 要设置的 loadingProgressService 
	 */
	public void setLoadingProgressService(
			ILoadingProgressService loadingProgressService) {
		this.loadingProgressService = loadingProgressService;
	}
	
}