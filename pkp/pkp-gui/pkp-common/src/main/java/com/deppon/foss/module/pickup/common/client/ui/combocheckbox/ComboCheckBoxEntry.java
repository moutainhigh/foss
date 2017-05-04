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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/common/client/ui/combocheckbox/ComboCheckBoxEntry.java
 * 
 * FILE NAME        	: ComboCheckBoxEntry.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * Copyright by Deppon and the original author or authors.
 * 
 * This document only allow internal use ,Any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Deppon from
 *
 *      http://www.deppon.com
 *
 */
package com.deppon.foss.module.pickup.common.client.ui.combocheckbox;

/**
 * JComboBox 实现多选ComboCheckBoxEntry
 * @author shixw
 *
 */
public class ComboCheckBoxEntry {
	/**
	* 是否选中
	*/
    boolean checked;
    /**
     * status
     */
    boolean state;
    /**
     * code to show
     */
    String uniqueCode;
    /**
     * value
     */
    String value;
 
    /**
	 * 构造方法
	 */
    public ComboCheckBoxEntry() {
        this.checked = false;
        this.state = true;
        this.uniqueCode = "-1";
        this.value = "Empty Entry";
    }
 
    /**
	 * 构造方法
	 */
    public ComboCheckBoxEntry(String uniqueCode, String value) {
        this.checked = false;
        this.state = true;
        this.uniqueCode = uniqueCode;
        this.value = value;
    }
 
    /**
	 * 构造方法
	 */
    public ComboCheckBoxEntry(boolean checked, String uniqueCode, String value) {
        this.checked = checked;
        this.state = true;
        this.uniqueCode = uniqueCode;
        this.value = value;
    }
 
    /**
	 * 构造方法
	 */
    public ComboCheckBoxEntry(boolean checked, boolean state, String uniqueCode, String value) {
        this.checked = checked;
        this.state = state;
        this.uniqueCode = uniqueCode;
        this.value = value;
    }
 
    /**
     * getChecked 
     * @return
     */
    public boolean getChecked() {
        return checked;
    }
 
    /**
     * setChecked
     * @param checked
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
 
    /**
     * getUniqueCode
     * @return
     */
    public String getUniqueCode() {
        return uniqueCode;
    }
 
    /**
     * getValue
     * @return
     */
    public String getValue() {
        return value;
    }
 
    /**
     * getState
     * @return
     */
    public boolean getState() {
        return state;
    }
}