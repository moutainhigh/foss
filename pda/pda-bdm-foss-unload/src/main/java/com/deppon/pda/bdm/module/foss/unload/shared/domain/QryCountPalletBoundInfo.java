package com.deppon.pda.bdm.module.foss.unload.shared.domain;


/**
 * 刷新卸车任务
 * @author zhangzhenxian
 * @date may 6,2014 14:33:30 PM
 * @version 1.0
 * @since
 */

public class QryCountPalletBoundInfo {

	/**
	 * 操作人工号
	 */
	private String userCode;

    /**
     * 操作部门编码
     */
	private String deptCode;

    /**
     * 当前时间到前  N 个小时
     */
	private int timeSpan;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public int getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(int timeSpan) {
        this.timeSpan = timeSpan;
    }

	
}