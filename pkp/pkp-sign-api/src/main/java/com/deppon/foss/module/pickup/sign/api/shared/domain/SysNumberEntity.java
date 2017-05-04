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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/shared/domain/SysNumberEntity.java
 * 
 * FILE NAME        	: SysNumberEntity.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.shared.domain;

/**   
 * <p>流水号<br />
 * </p>
 * @title SysNumberEntity.java
 * @package com.deppon.foss.module.pickup.sign.api.shared.domain 
 * @author ibm-lizhiguo
 * @version 0.1 2012-12-4
 */
public class SysNumberEntity {
	/**
	 * 流水号
	 */
    private Integer seq;
    /**
     * 描述
     */
    private String descrip;
	/**
	 * @return seq : return the property seq.
	 */
	public Integer getSeq() {
		return seq;
	}
	/**
	 * @param seq : set the property seq.
	 */
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	/**
	 * @return descrip : return the property descrip.
	 */
	public String getDescrip() {
		return descrip;
	}
	/**
	 * @param descrip : set the property descrip.
	 */
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	

}