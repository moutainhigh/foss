package com.deppon.foss.module.base.baseinfo.api.shared.vo;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.CommonAirPartAndDeptEntity;

/**
 * 前后台交互公共选择器
 */
public class CommonAirAgentAndOutfieldVo {
		/**
		 * 交互实体
		 */
		private CommonAirPartAndDeptEntity entity;
		/**
		 * 交互实体类型
		 */
		private List<CommonAirPartAndDeptEntity> entityList;

		public CommonAirPartAndDeptEntity getEntity() {
			return entity;
		}

		public void setEntity(CommonAirPartAndDeptEntity entity) {
			this.entity = entity;
		}

		public List<CommonAirPartAndDeptEntity> getEntityList() {
			return entityList;
		}

		public void setEntityList(List<CommonAirPartAndDeptEntity> entityList) {
			this.entityList = entityList;
		}
		
		

}
