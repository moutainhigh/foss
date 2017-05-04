/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-common
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/vo/ValueCopy.java
 * 
 * FILE NAME        	: ValueCopy.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.common.client.vo;

import com.deppon.foss.module.base.dict.api.shared.domain.DataDictionaryValueEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.GoodsTypeEntity;
import com.deppon.foss.module.pickup.pricing.api.shared.domain.ProductEntity;

/**
 * 
 * VO类值拷贝
 * @author 025000-FOSS-helong
 * @date 2012-12-24 下午08:54:20
 */
public class ValueCopy {

	/**
	 * 
	 * 将数据字典entity类值复制到VO类
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-23 上午10:15:42
	 */
	public static void valueCopy(DataDictionaryValueEntity entity,
			DataDictionaryValueVo vo) {
		vo.setId(entity.getId());
		vo.setValueName(entity.getValueName());
		vo.setValueCode(entity.getValueCode());
	}
	
	/**
	 * 
	 * 将货物类型entity类值复制到VO类
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-23 上午10:15:42
	 */
	public static void valueCopy(GoodsTypeEntity entity,
			DataDictionaryValueVo vo) {
		vo.setId(entity.getId());
		vo.setValueName(entity.getName());
		vo.setValueCode(entity.getCode());
	}

	/**
	 * 
	 * 将产品entity类值复制到VO类
	 * 
	 * @author 025000-FOSS-helong
	 * @date 2012-10-23 上午10:15:42
	 */
	public static void entityValueCopy(ProductEntity entity, ProductEntityVo vo) {
		vo.setId(entity.getId());
		vo.setName(entity.getName());
		vo.setCode(entity.getCode());
		vo.setDestNetType(entity.getDestNetType());
	}

}