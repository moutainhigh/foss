package com.deppon.pda.bdm.module.foss.unload.shared.domain;

import java.util.Date;
import java.util.List;
import com.deppon.pda.bdm.module.core.shared.domain.ScanMsgEntity;
/**
 * 
 * TODO(叉车异常扫描明细)
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:Administrator,date:2014-1-3 下午2:11:50,content:TODO </p>
 * @author gaojia
 * @date 2014-1-3 下午2:11:50
 * @since
 * @version
 */
public class ExcPalletBoundScanEntity extends ScanMsgEntity{
	/**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 绑定时间
	 */
	private Date bindingDate;
	/**
	 * 叉车绑定任务号
	 */
	private String forkBindingNo;
	/**
	 * 绑定运单明细
	 */
	private List<ExcBindingScanEntity> bindingScanInfoList;
	public Date getBindingDate() {
		return bindingDate;
	}
	public void setBindingDate(Date bindingDate) {
		this.bindingDate = bindingDate;
	}
	public List<ExcBindingScanEntity> getBindingScanInfoList() {
		return bindingScanInfoList;
	}
	public void setBindingScanInfoList(
			List<ExcBindingScanEntity> bindingScanInfoList) {
		this.bindingScanInfoList = bindingScanInfoList;
	}
	public String getForkBindingNo() {
		return forkBindingNo;
	}
	public void setForkBindingNo(String forkBindingNo) {
		this.forkBindingNo = forkBindingNo;
	} 
	
}
