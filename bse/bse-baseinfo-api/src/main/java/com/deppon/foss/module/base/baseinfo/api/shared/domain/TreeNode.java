/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/TreeNode.java
 * 
 * FILE NAME        	: TreeNode.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import java.util.ArrayList;
import java.util.List;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 树节点的实体对象
 * 
 * @author 087584-foss-lijun
 * @date 2012-08-27 下午2:35:52
 */
@SuppressWarnings("rawtypes")
public class TreeNode<T extends BaseEntity,K extends TreeNode> {

	/**
	 * 树节点ID
	 */
	private String id;
	
	/**
	 * 树节点文本显示
	 */
	private String text;
	
	/**
	 * 是否叶子节点
	 */
	private Boolean leaf;
	
	/**
	 * 父节点ID
	 */
	private String parentId;
	
	/**
	 * 显示是否选择
	 */
	private Boolean checked;

	/**
	 * 节点对象数据
	 */
	private T entity;
	
	/**
	 * 孩子节点
	 */
	private List<K> children = new ArrayList<K>();

	/**
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param  id  
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param  text  
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return leaf
	 */
	public Boolean getLeaf() {
		return leaf;
	}

	/**
	 * @param  leaf  
	 */
	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	/**
	 * @return parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param  parentId  
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return checked
	 */
	public Boolean getChecked() {
		return checked;
	}

	/**
	 * @param  checked  
	 */
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	/**
	 * @return entity
	 */
	public T getEntity() {
		return entity;
	}

	/**
	 * @param  entity  
	 */
	public void setEntity(T entity) {
		this.entity = entity;
	}

	/**
	 * @return children
	 */
	public List<K> getChildren() {
		return children;
	}

	/**
	 * @param  children  
	 */
	public void setChildren(List<K> children) {
		this.children = children;
	}

}