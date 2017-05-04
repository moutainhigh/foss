package com.deppon.foss.module.transfer.platform.api.shared.vo;

import java.io.Serializable;
import java.util.List;

import com.deppon.foss.module.transfer.platform.api.shared.domain.LoaderEfficiencyEntity;

/**
 * 装卸车效率vo
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:163580,date:2014-4-29 上午10:19:55,content: </p>
 * @author 163580
 * @date 2014-4-29 上午10:19:55
 * @since
 * @version
 */
public class LoaderEfficiencyVo implements Serializable {
	private static final long serialVersionUID = -7319323813663278068L;

	/**装卸车效率Entity**/
	private LoaderEfficiencyEntity loaderEfficiency;
	
	/**装卸车效率List<LoaderEfficiencyEntity>**/
	private List<LoaderEfficiencyEntity> loaderEfficiencys;
	
	/**当前部门顶级外场**/
	private String superOrgCode;



	public LoaderEfficiencyEntity getLoaderEfficiency() {
		return loaderEfficiency;
	}

	public void setLoaderEfficiency(LoaderEfficiencyEntity loaderEfficiency) {
		this.loaderEfficiency = loaderEfficiency;
	}

	public List<LoaderEfficiencyEntity> getLoaderEfficiencys() {
		return loaderEfficiencys;
	}

	public void setLoaderEfficiencys(List<LoaderEfficiencyEntity> loaderEfficiencys) {
		this.loaderEfficiencys = loaderEfficiencys;
	}

	public String getSuperOrgCode() {
		return superOrgCode;
	}

	public void setSuperOrgCode(String superOrgCode) {
		this.superOrgCode = superOrgCode;
	}
}
