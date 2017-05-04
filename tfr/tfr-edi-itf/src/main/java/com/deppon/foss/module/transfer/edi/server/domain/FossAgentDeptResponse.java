/**
 * @author 332153
 *
 */
package com.deppon.foss.module.transfer.edi.server.domain;

import java.io.Serializable;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.AirlinesAgentEntityTransEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OuterBranchEntity;


/**
 * 
 * 项目名称：ats 
 * 类名称：FossAgentDeptResponse
 * 类描述：opp请求foss获取后Foss返回代理信息实体
 * 创建人：332153 
 * 创建时间：2016-5-13 下午5:24:56 修改备注：
 * 
 * @version
 * 
 */
public class FossAgentDeptResponse implements Serializable {

	/**
	 * @author 332153
	 *
	 */
	private static final long serialVersionUID = -1529588922762142380L;
	/**
	 * 成功标志
	 */
	private boolean isSuccess;
	/**
	 * 处理标志
	 */
	private String failMsg;
	/**
	 * 用来存储交互“航空公司代理人”的数据库对应实体
	 */
	private AirlinesAgentEntityTransEntity agentEntity;
	/**
	 *  用来存储交互“空运代理网点、偏线代理网点”的数据库对应实体
	 */
	private OuterBranchEntity outerBranchEntity;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}


	public OuterBranchEntity getOuterBranchEntity() {
		return outerBranchEntity;
	}

	public void setOuterBranchEntity(OuterBranchEntity outerBranchEntity) {
		this.outerBranchEntity = outerBranchEntity;
	}

	public AirlinesAgentEntityTransEntity getAgentEntity() {
		return agentEntity;
	}

	public void setAgentEntity(AirlinesAgentEntityTransEntity agentEntity) {
		this.agentEntity = agentEntity;
	}

}
