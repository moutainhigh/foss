/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-management-api
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/management/api/shared/vo/WayBillVo.java
 *  
 *  FILE NAME          :WayBillVo.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.management.api.shared.vo;
/**
 * Vo开发规范
 * 1.必须实现java.io.Serializable接口
 * 2.必须生成serialVersionUID
 * 3.类名必须以Vo结尾
 * 4.建议不要直接继承Entity,可以使用Entity作为Vo的一个属性通过getter、setter访问
 *   Vo的生命周期只能到Action层,禁止将Vo传入Service层以下,可以通过Vo.getXXEntity(XXEntity代表某个Entity的名称)获取Entity传入Service层以下
 *   Vo主要负责接受前台给Action传参、封装后台返回结果给前台
 */