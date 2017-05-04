package com.deppon.foss.module.login.shared.vo;

import com.deppon.foss.framework.entity.BaseEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TreeNode;

/**
 * 
 * 
*******************************************
* <b style="font-family:微软雅黑"><small>Description:功能树节点的实体对象</small></b>   </br>
* <b style="font-family:微软雅黑"><small>HISTORY</small></b></br>
* <b style="font-family:微软雅黑"><small> ID      DATE    PERSON     REASON</small></b><br>
********************************************
* <div style="font-family:微软雅黑,font-size:70%"> 
* 1 	2012-8-30 	钟庭杰 	新增
* </div>  
********************************************
 */
@SuppressWarnings("rawtypes")
public class ResourceTreeNode<T extends BaseEntity> extends TreeNode<T,ResourceTreeNode>{

	//连接
	private String uri;
	
	//是否可展开
	private Boolean expandable;
	
	//是否展开
	private Boolean expend;

	//显示图标
	private String iconCls;
	
	//菜单的CSS
	private String cls;
	
	//菜单的显示顺序
	private String displayOrder;
	
	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
	public Boolean getExpandable() {
		return expandable;
	}

	public void setExpandable(Boolean expandable) {
		this.expandable = expandable;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Boolean getExpend() {
		return expend;
	}

	public void setExpend(Boolean expend) {
		this.expend = expend;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
}
