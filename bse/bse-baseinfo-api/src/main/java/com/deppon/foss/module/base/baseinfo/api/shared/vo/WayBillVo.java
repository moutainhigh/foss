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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/vo/WayBillVo.java
 * 
 * FILE NAME        	: WayBillVo.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.vo;
/**
 * Vo开发规范
 * 1.必须实现java.io.Serializable接口
 * 2.必须生成serialVersionUID
 * 3.类名必须以Vo结尾
 * 4.建议不要直接继承Entity,可以使用Entity作为Vo的一个属性通过getter、setter访问
 *   Vo的生命周期只能到Action层,禁止将Vo传入Service层以下,可以通过Vo.getXXEntity(XXEntity代表某个Entity的名称)获取Entity传入Service层以下
 *   Vo主要负责接受前台给Action传参、封装后台返回结果给前台
 */
