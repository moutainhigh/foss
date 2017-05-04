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
 * PROJECT NAME	: bse-baseinfo
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/action/PlatformAction.java
 * 
 * FILE NAME        	: PlatformAction.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
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
/**
 * 
 * 外场管理员可对所属外场月台基础资料进行新增、
 * 
 * 修改、作废、查询操作。 
 * 
 * 系统管理员可对所有外场月台基础资料
 * 
 * 进行新增、修改、作废、查询操作。
 * 
 * 前置条件 1、 外场资料录入完成 2、
 * 
 *  库位资料录入完成 SUC-85 
 *  
 *  修改_查询行政组织业务属性
 * 
 * 后置条件 1、 
 * 
 * 为用户提供外场月台基础资料增删改查操作。
 * 
 *  2、 
 *  
 *  为中转提供最优停靠月台计算 
 *  
 *  3、 
 *  
 *  为库位提供到月台的距离维护 SUC-371
 *  
 * 计算车辆最优停靠月台 SUC-185 
 * 
 * 新增_修改_作废_查询库位信息
 * 
 * 
 * 
 * 
 * 进入月台新增界面。如果当前用户是外场管理员，则字段“外场名称”，
 * 
 * 自动带出为用户所属外场。如果当前用户是系统管理员，
 * 
 * 则“外场名称”可编辑。
 * 
 * 成功保存至系统。返回月台主界面。
 * 
 * 1. 功能按钮区域 新增按钮：点击新增按钮进入新增界面，
 * 
 * 2)
 * 
 * 导入按钮：点击导入按钮弹出界面，
 * 
 * 选择要导入月台数据文件（Excel文件），
 * 
 * 进行Excel数据导入； 3)
 * 
 * 导出按钮：点击导出按钮，
 * 
 * 可以把月台所有数据导出到Excel中
 * 
 * 4) 查询按钮：输入查询条件，
 * 
 * 点击查询按钮，系统返回查询结果，
 * 
 * 刷新查询列表。 5)
 * 
 * 重置按钮：点击重置按钮，
 * 
 * 重置查询条件。 6)
 * 
 * 作废按钮：选中列表中一行或多行记录，
 * 
 * 点击作废按钮，选中的记录被作废；
 * 
 * 或点击各行的作废按钮，作废改行记录；
 * 
 * 需要弹出确认提示框。 7)
 * 
 * 修改按钮：点击各行的修改按钮，
 * 
 * 进入修改界面，。
 * 
 * 
 * 8) 分页按钮：实现分页功能。
 * 
 * 2. 列表显示区域 1)
 * 
 * 列表区域默认不显示，点击查询按钮，
 * 
 * 根据查询条件显示列表数据。
 * 
 * 2) 列表中包含外场编号，外场名称，
 * 
 * 
 * 月台编号,可停靠车型,高度,宽度,升降台,位置。
 * 
 * 3. 字段输入区域 录入查询条件，
 * 
 * 点击查询按钮后在列表区按外场编号，
 * 
 * 月台编号顺序显示满足条件的查询结果。
 * 
 */
/**
 * 
 * 外场管理员只能管理自己所在外场的月台资料,
 * 
 * 在列表页面不展示外场编号和外场名称两列。
 * 
 * 系统管理员只有在新增时可以选择外场名称，
 * 
 * 并自动带出外场编号，修改时不允许修改所属外场。
 * 
 * “外场编号”，“月台编号”组合唯一。
 * 
 * 月台高度默认1.3米,
 * 
 * 宽度默认4.5米。
 * 
 * 
 * 1．功能按钮区域 1) 
 * 
 * 保存按钮：点击保存按钮，
 * 
 * 成功保存界面信息至数据库，
 * 
 * 并返回到查询列表页面;
 * 
 * 失败则停留在原页面并提示用户失败原因。 
 * 
 * 2)
 * 重置按钮：点击重置按钮，
 * 
 * 回到当前界面的初始状态。
 * 
 *  3) 取消按钮：点击取消按钮，
 *  
 *  退出当前界面，返回查询列表页面。
 *  
 *   2. 字段输入区域
 *   
 * 参见数据元素【月台新增或修改信息】
 */
/**
 * 
 * 外场管理员可对所属外场月台基础资料进行新增、
 * 
 * 修改、作废、查询操作。 
 * 
 * 系统管理员可对所有外场月台基础资料
 * 
 * 进行新增、修改、作废、查询操作。
 * 
 * 前置条件 1、 外场资料录入完成 2、
 * 
 *  库位资料录入完成 SUC-85 
 *  
 *  修改_查询行政组织业务属性
 * 
 * 后置条件 1、 
 * 
 * 为用户提供外场月台基础资料增删改查操作。
 * 
 *  2、 
 *  
 *  为中转提供最优停靠月台计算 
 *  
 *  3、 
 *  
 *  为库位提供到月台的距离维护 SUC-371
 *  
 * 计算车辆最优停靠月台 SUC-185 
 * 
 * 新增_修改_作废_查询库位信息
 * 
 * 
 * 
 * 
 * 进入月台新增界面。如果当前用户是外场管理员，则字段“外场名称”，
 * 
 * 自动带出为用户所属外场。如果当前用户是系统管理员，
 * 
 * 则“外场名称”可编辑。
 * 
 * 成功保存至系统。返回月台主界面。
 * 
 * 1. 功能按钮区域 新增按钮：点击新增按钮进入新增界面，
 * 
 * 2)
 * 
 * 导入按钮：点击导入按钮弹出界面，
 * 
 * 选择要导入月台数据文件（Excel文件），
 * 
 * 进行Excel数据导入； 3)
 * 
 * 导出按钮：点击导出按钮，
 * 
 * 可以把月台所有数据导出到Excel中
 * 
 * 4) 查询按钮：输入查询条件，
 * 
 * 点击查询按钮，系统返回查询结果，
 * 
 * 刷新查询列表。 5)
 * 
 * 重置按钮：点击重置按钮，
 * 
 * 重置查询条件。 6)
 * 
 * 作废按钮：选中列表中一行或多行记录，
 * 
 * 点击作废按钮，选中的记录被作废；
 * 
 * 或点击各行的作废按钮，作废改行记录；
 * 
 * 需要弹出确认提示框。 7)
 * 
 * 修改按钮：点击各行的修改按钮，
 * 
 * 进入修改界面，。
 * 
 * 
 * 8) 分页按钮：实现分页功能。
 * 
 * 2. 列表显示区域 1)
 * 
 * 列表区域默认不显示，点击查询按钮，
 * 
 * 根据查询条件显示列表数据。
 * 
 * 2) 列表中包含外场编号，外场名称，
 * 
 * 
 * 月台编号,可停靠车型,高度,宽度,升降台,位置。
 * 
 * 3. 字段输入区域 录入查询条件，
 * 
 * 点击查询按钮后在列表区按外场编号，
 * 
 * 月台编号顺序显示满足条件的查询结果。
 * 
 */
/**
 * 
 * 外场管理员只能管理自己所在外场的月台资料,
 * 
 * 在列表页面不展示外场编号和外场名称两列。
 * 
 * 系统管理员只有在新增时可以选择外场名称，
 * 
 * 并自动带出外场编号，修改时不允许修改所属外场。
 * 
 * “外场编号”，“月台编号”组合唯一。
 * 
 * 月台高度默认1.3米,
 * 
 * 宽度默认4.5米。
 * 
 * 
 * 1．功能按钮区域 1) 
 * 
 * 保存按钮：点击保存按钮，
 * 
 * 成功保存界面信息至数据库，
 * 
 * 并返回到查询列表页面;
 * 
 * 失败则停留在原页面并提示用户失败原因。 
 * 
 * 2)
 * 重置按钮：点击重置按钮，
 * 
 * 回到当前界面的初始状态。
 * 
 *  3) 取消按钮：点击取消按钮，
 *  
 *  退出当前界面，返回查询列表页面。
 *  
 *   2. 字段输入区域
 *   
 * 参见数据元素【月台新增或修改信息】
 */
/**
 * 
 * 外场管理员可对所属外场月台基础资料进行新增、
 * 
 * 修改、作废、查询操作。 
 * 
 * 系统管理员可对所有外场月台基础资料
 * 
 * 进行新增、修改、作废、查询操作。
 * 
 * 前置条件 1、 外场资料录入完成 2、
 * 
 *  库位资料录入完成 SUC-85 
 *  
 *  修改_查询行政组织业务属性
 * 
 * 后置条件 1、 
 * 
 * 为用户提供外场月台基础资料增删改查操作。
 * 
 *  2、 
 *  
 *  为中转提供最优停靠月台计算 
 *  
 *  3、 
 *  
 *  为库位提供到月台的距离维护 SUC-371
 *  
 * 计算车辆最优停靠月台 SUC-185 
 * 
 * 新增_修改_作废_查询库位信息
 * 
 * 
 * 
 * 
 * 进入月台新增界面。如果当前用户是外场管理员，则字段“外场名称”，
 * 
 * 自动带出为用户所属外场。如果当前用户是系统管理员，
 * 
 * 则“外场名称”可编辑。
 * 
 * 成功保存至系统。返回月台主界面。
 * 
 * 1. 功能按钮区域 新增按钮：点击新增按钮进入新增界面，
 * 
 * 2)
 * 
 * 导入按钮：点击导入按钮弹出界面，
 * 
 * 选择要导入月台数据文件（Excel文件），
 * 
 * 进行Excel数据导入； 3)
 * 
 * 导出按钮：点击导出按钮，
 * 
 * 可以把月台所有数据导出到Excel中
 * 
 * 4) 查询按钮：输入查询条件，
 * 
 * 点击查询按钮，系统返回查询结果，
 * 
 * 刷新查询列表。 5)
 * 
 * 重置按钮：点击重置按钮，
 * 
 * 重置查询条件。 6)
 * 
 * 作废按钮：选中列表中一行或多行记录，
 * 
 * 点击作废按钮，选中的记录被作废；
 * 
 * 或点击各行的作废按钮，作废改行记录；
 * 
 * 需要弹出确认提示框。 7)
 * 
 * 修改按钮：点击各行的修改按钮，
 * 
 * 进入修改界面，。
 * 
 * 
 * 8) 分页按钮：实现分页功能。
 * 
 * 2. 列表显示区域 1)
 * 
 * 列表区域默认不显示，点击查询按钮，
 * 
 * 根据查询条件显示列表数据。
 * 
 * 2) 列表中包含外场编号，外场名称，
 * 
 * 
 * 月台编号,可停靠车型,高度,宽度,升降台,位置。
 * 
 * 3. 字段输入区域 录入查询条件，
 * 
 * 点击查询按钮后在列表区按外场编号，
 * 
 * 月台编号顺序显示满足条件的查询结果。
 * 
 */
/**
 * 
 * 外场管理员只能管理自己所在外场的月台资料,
 * 
 * 在列表页面不展示外场编号和外场名称两列。
 * 
 * 系统管理员只有在新增时可以选择外场名称，
 * 
 * 并自动带出外场编号，修改时不允许修改所属外场。
 * 
 * “外场编号”，“月台编号”组合唯一。
 * 
 * 月台高度默认1.3米,
 * 
 * 宽度默认4.5米。
 * 
 * 
 * 1．功能按钮区域 1) 
 * 
 * 保存按钮：点击保存按钮，
 * 
 * 成功保存界面信息至数据库，
 * 
 * 并返回到查询列表页面;
 * 
 * 失败则停留在原页面并提示用户失败原因。 
 * 
 * 2)
 * 重置按钮：点击重置按钮，
 * 
 * 回到当前界面的初始状态。
 * 
 *  3) 取消按钮：点击取消按钮，
 *  
 *  退出当前界面，返回查询列表页面。
 *  
 *   2. 字段输入区域
 *   
 * 参见数据元素【月台新增或修改信息】
 */
/**
 * 
 * 外场管理员可对所属外场月台基础资料进行新增、
 * 
 * 修改、作废、查询操作。 
 * 
 * 系统管理员可对所有外场月台基础资料
 * 
 * 进行新增、修改、作废、查询操作。
 * 
 * 前置条件 1、 外场资料录入完成 2、
 * 
 *  库位资料录入完成 SUC-85 
 *  
 *  修改_查询行政组织业务属性
 * 
 * 后置条件 1、 
 * 
 * 为用户提供外场月台基础资料增删改查操作。
 * 
 *  2、 
 *  
 *  为中转提供最优停靠月台计算 
 *  
 *  3、 
 *  
 *  为库位提供到月台的距离维护 SUC-371
 *  
 * 计算车辆最优停靠月台 SUC-185 
 * 
 * 新增_修改_作废_查询库位信息
 * 
 * 
 * 
 * 
 * 进入月台新增界面。如果当前用户是外场管理员，则字段“外场名称”，
 * 
 * 自动带出为用户所属外场。如果当前用户是系统管理员，
 * 
 * 则“外场名称”可编辑。
 * 
 * 成功保存至系统。返回月台主界面。
 * 
 * 1. 功能按钮区域 新增按钮：点击新增按钮进入新增界面，
 * 
 * 2)
 * 
 * 导入按钮：点击导入按钮弹出界面，
 * 
 * 选择要导入月台数据文件（Excel文件），
 * 
 * 进行Excel数据导入； 3)
 * 
 * 导出按钮：点击导出按钮，
 * 
 * 可以把月台所有数据导出到Excel中
 * 
 * 4) 查询按钮：输入查询条件，
 * 
 * 点击查询按钮，系统返回查询结果，
 * 
 * 刷新查询列表。 5)
 * 
 * 重置按钮：点击重置按钮，
 * 
 * 重置查询条件。 6)
 * 
 * 作废按钮：选中列表中一行或多行记录，
 * 
 * 点击作废按钮，选中的记录被作废；
 * 
 * 或点击各行的作废按钮，作废改行记录；
 * 
 * 需要弹出确认提示框。 7)
 * 
 * 修改按钮：点击各行的修改按钮，
 * 
 * 进入修改界面，。
 * 
 * 
 * 8) 分页按钮：实现分页功能。
 * 
 * 2. 列表显示区域 1)
 * 
 * 列表区域默认不显示，点击查询按钮，
 * 
 * 根据查询条件显示列表数据。
 * 
 * 2) 列表中包含外场编号，外场名称，
 * 
 * 
 * 月台编号,可停靠车型,高度,宽度,升降台,位置。
 * 
 * 3. 字段输入区域 录入查询条件，
 * 
 * 点击查询按钮后在列表区按外场编号，
 * 
 * 月台编号顺序显示满足条件的查询结果。
 * 
 */
/**
 * 
 * 外场管理员只能管理自己所在外场的月台资料,
 * 
 * 在列表页面不展示外场编号和外场名称两列。
 * 
 * 系统管理员只有在新增时可以选择外场名称，
 * 
 * 并自动带出外场编号，修改时不允许修改所属外场。
 * 
 * “外场编号”，“月台编号”组合唯一。
 * 
 * 月台高度默认1.3米,
 * 
 * 宽度默认4.5米。
 * 
 * 
 * 1．功能按钮区域 1) 
 * 
 * 保存按钮：点击保存按钮，
 * 
 * 成功保存界面信息至数据库，
 * 
 * 并返回到查询列表页面;
 * 
 * 失败则停留在原页面并提示用户失败原因。 
 * 
 * 2)
 * 重置按钮：点击重置按钮，
 * 
 * 回到当前界面的初始状态。
 * 
 *  3) 取消按钮：点击取消按钮，
 *  
 *  退出当前界面，返回查询列表页面。
 *  
 *   2. 字段输入区域
 *   
 * 参见数据元素【月台新增或修改信息】
 */
/**
 * 
 * 外场管理员可对所属外场月台基础资料进行新增、
 * 
 * 修改、作废、查询操作。 
 * 
 * 系统管理员可对所有外场月台基础资料
 * 
 * 进行新增、修改、作废、查询操作。
 * 
 * 前置条件 1、 外场资料录入完成 2、
 * 
 *  库位资料录入完成 SUC-85 
 *  
 *  修改_查询行政组织业务属性
 * 
 * 后置条件 1、 
 * 
 * 为用户提供外场月台基础资料增删改查操作。
 * 
 *  2、 
 *  
 *  为中转提供最优停靠月台计算 
 *  
 *  3、 
 *  
 *  为库位提供到月台的距离维护 SUC-371
 *  
 * 计算车辆最优停靠月台 SUC-185 
 * 
 * 新增_修改_作废_查询库位信息
 * 
 * 
 * 
 * 
 * 进入月台新增界面。如果当前用户是外场管理员，则字段“外场名称”，
 * 
 * 自动带出为用户所属外场。如果当前用户是系统管理员，
 * 
 * 则“外场名称”可编辑。
 * 
 * 成功保存至系统。返回月台主界面。
 * 
 * 1. 功能按钮区域 新增按钮：点击新增按钮进入新增界面，
 * 
 * 2)
 * 
 * 导入按钮：点击导入按钮弹出界面，
 * 
 * 选择要导入月台数据文件（Excel文件），
 * 
 * 进行Excel数据导入； 3)
 * 
 * 导出按钮：点击导出按钮，
 * 
 * 可以把月台所有数据导出到Excel中
 * 
 * 4) 查询按钮：输入查询条件，
 * 
 * 点击查询按钮，系统返回查询结果，
 * 
 * 刷新查询列表。 5)
 * 
 * 重置按钮：点击重置按钮，
 * 
 * 重置查询条件。 6)
 * 
 * 作废按钮：选中列表中一行或多行记录，
 * 
 * 点击作废按钮，选中的记录被作废；
 * 
 * 或点击各行的作废按钮，作废改行记录；
 * 
 * 需要弹出确认提示框。 7)
 * 
 * 修改按钮：点击各行的修改按钮，
 * 
 * 进入修改界面，。
 * 
 * 
 * 8) 分页按钮：实现分页功能。
 * 
 * 2. 列表显示区域 1)
 * 
 * 列表区域默认不显示，点击查询按钮，
 * 
 * 根据查询条件显示列表数据。
 * 
 * 2) 列表中包含外场编号，外场名称，
 * 
 * 
 * 月台编号,可停靠车型,高度,宽度,升降台,位置。
 * 
 * 3. 字段输入区域 录入查询条件，
 * 
 * 点击查询按钮后在列表区按外场编号，
 * 
 * 月台编号顺序显示满足条件的查询结果。
 * 
 */
/**
 * 
 * 外场管理员只能管理自己所在外场的月台资料,
 * 
 * 在列表页面不展示外场编号和外场名称两列。
 * 
 * 系统管理员只有在新增时可以选择外场名称，
 * 
 * 并自动带出外场编号，修改时不允许修改所属外场。
 * 
 * “外场编号”，“月台编号”组合唯一。
 * 
 * 月台高度默认1.3米,
 * 
 * 宽度默认4.5米。
 * 
 * 
 * 1．功能按钮区域 1) 
 * 
 * 保存按钮：点击保存按钮，
 * 
 * 成功保存界面信息至数据库，
 * 
 * 并返回到查询列表页面;
 * 
 * 失败则停留在原页面并提示用户失败原因。 
 * 
 * 2)
 * 重置按钮：点击重置按钮，
 * 
 * 回到当前界面的初始状态。
 * 
 *  3) 取消按钮：点击取消按钮，
 *  
 *  退出当前界面，返回查询列表页面。
 *  
 *   2. 字段输入区域
 *   
 * 参见数据元素【月台新增或修改信息】
 */
/**
 * 
 * 外场管理员可对所属外场月台基础资料进行新增、
 * 
 * 修改、作废、查询操作。 
 * 
 * 系统管理员可对所有外场月台基础资料
 * 
 * 进行新增、修改、作废、查询操作。
 * 
 * 前置条件 1、 外场资料录入完成 2、
 * 
 *  库位资料录入完成 SUC-85 
 *  
 *  修改_查询行政组织业务属性
 * 
 * 后置条件 1、 
 * 
 * 为用户提供外场月台基础资料增删改查操作。
 * 
 *  2、 
 *  
 *  为中转提供最优停靠月台计算 
 *  
 *  3、 
 *  
 *  为库位提供到月台的距离维护 SUC-371
 *  
 * 计算车辆最优停靠月台 SUC-185 
 * 
 * 新增_修改_作废_查询库位信息
 * 
 * 
 * 
 * 
 * 进入月台新增界面。如果当前用户是外场管理员，则字段“外场名称”，
 * 
 * 自动带出为用户所属外场。如果当前用户是系统管理员，
 * 
 * 则“外场名称”可编辑。
 * 
 * 成功保存至系统。返回月台主界面。
 * 
 * 1. 功能按钮区域 新增按钮：点击新增按钮进入新增界面，
 * 
 * 2)
 * 
 * 导入按钮：点击导入按钮弹出界面，
 * 
 * 选择要导入月台数据文件（Excel文件），
 * 
 * 进行Excel数据导入； 3)
 * 
 * 导出按钮：点击导出按钮，
 * 
 * 可以把月台所有数据导出到Excel中
 * 
 * 4) 查询按钮：输入查询条件，
 * 
 * 点击查询按钮，系统返回查询结果，
 * 
 * 刷新查询列表。 5)
 * 
 * 重置按钮：点击重置按钮，
 * 
 * 重置查询条件。 6)
 * 
 * 作废按钮：选中列表中一行或多行记录，
 * 
 * 点击作废按钮，选中的记录被作废；
 * 
 * 或点击各行的作废按钮，作废改行记录；
 * 
 * 需要弹出确认提示框。 7)
 * 
 * 修改按钮：点击各行的修改按钮，
 * 
 * 进入修改界面，。
 * 
 * 
 * 8) 分页按钮：实现分页功能。
 * 
 * 2. 列表显示区域 1)
 * 
 * 列表区域默认不显示，点击查询按钮，
 * 
 * 根据查询条件显示列表数据。
 * 
 * 2) 列表中包含外场编号，外场名称，
 * 
 * 
 * 月台编号,可停靠车型,高度,宽度,升降台,位置。
 * 
 * 3. 字段输入区域 录入查询条件，
 * 
 * 点击查询按钮后在列表区按外场编号，
 * 
 * 月台编号顺序显示满足条件的查询结果。
 * 
 */
/**
 * 
 * 外场管理员只能管理自己所在外场的月台资料,
 * 
 * 在列表页面不展示外场编号和外场名称两列。
 * 
 * 系统管理员只有在新增时可以选择外场名称，
 * 
 * 并自动带出外场编号，修改时不允许修改所属外场。
 * 
 * “外场编号”，“月台编号”组合唯一。
 * 
 * 月台高度默认1.3米,
 * 
 * 宽度默认4.5米。
 * 
 * 
 * 1．功能按钮区域 1) 
 * 
 * 保存按钮：点击保存按钮，
 * 
 * 成功保存界面信息至数据库，
 * 
 * 并返回到查询列表页面;
 * 
 * 失败则停留在原页面并提示用户失败原因。 
 * 
 * 2)
 * 重置按钮：点击重置按钮，
 * 
 * 回到当前界面的初始状态。
 * 
 *  3) 取消按钮：点击取消按钮，
 *  
 *  退出当前界面，返回查询列表页面。
 *  
 *   2. 字段输入区域
 *   
 * 参见数据元素【月台新增或修改信息】
 */
package com.deppon.foss.module.base.baseinfo.server.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.deppon.foss.base.util.define.ColumnConstants;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.components.export.excel.ExportResource;
import com.deppon.foss.framework.server.components.export.excel.ExportSetting;
import com.deppon.foss.framework.server.components.export.excel.ExporterExecutor;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.service.IPlatformService;
import com.deppon.foss.module.base.baseinfo.api.server.service.commonselector.ICommonVehicleTypeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.PlatformEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.VehicleTypeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PlatformExcelDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.FileException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.MessageType;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.PlatformVo;


import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.copyUtil.XlsImpUtil;

/**
 * 月台ACTION
 * 
 * @author 078838-foss-zhangbin
 * @date 2012-11-28
 * @version 1.0
 */
public class PlatformAction extends AbstractAction {

    private static final long serialVersionUID = 2883644272419312426L;

    // 前后台传的参数
    private PlatformVo platformVo = new PlatformVo();

    public PlatformVo getPlatformVo() {
	return platformVo;
    }

    public void setPlatformVo(PlatformVo platformVo) {
	this.platformVo = platformVo;
    }

    /**
     * 导入文件
     */
    private File uploadFile;
    /**
     * 导入文件名
     */
    private String uploadFileFileName;
    /**
     * 导出Excel 文件流
     */
    private InputStream inputStream;

    /**
     * 导出Excel 文件名
     */
    private String downloadFileName;

    public void setUploadFile(File uploadFile) {
	this.uploadFile = uploadFile;
    }

    public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}
    public InputStream getInputStream() {
	return inputStream;
    }

    public String getDownloadFileName() {
	return downloadFileName;
    }

    // 月台service
    private IPlatformService platformService;
    private ICommonVehicleTypeService vehicleTypeService;
    public void setPlatformService(IPlatformService platformService) {
	this.platformService = platformService;
    }
    public void setVehicleTypeService(
			ICommonVehicleTypeService vehicleTypeService) {
		this.vehicleTypeService = vehicleTypeService;
	}
    private static final int CELL_COUNT = 12;

    /**
     * .
     * <p>
     * 查询所有的月台根据条件<br/>
     * 方法名：queryPlatformListByCondition
     * </p>
     * 
     * @author 078838-foss-zhangbin
     * @时间 2012-11-28
     * @since JDK1.6
     */
    @JSON
    public String queryPlatformListByCondition() {
	try {
	    UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	     String currentOrgCode = user.getEmployee().getOrgCode();
	    this.setTotalCount(platformService.countPlatformListByCondition(
		    platformVo.getPlatformEntity(), userCode, currentOrgCode));
	    List<PlatformEntity> platformEntityList = platformService
		    .queryPlatformListByCondition(
			    platformVo.getPlatformEntity(), start, limit,
			    userCode, currentOrgCode);
	    platformVo.setPlatformEntityList(platformEntityList);
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 批量作废月台信息<br/>
     * 方法名：deletePlatform
     * </p>
     * 
     * @author 078838-foss-zhangbin
     * @时间 2012-11-28
     * @since JDK1.6
     */
    @JSON
    public String deletePlatform() {
	try {
	    UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    platformService.deletePlatforms(
		    platformVo.getPlatformVirtualCodes(), userCode);
	    return returnSuccess(MessageType.DELETE_PLATFORM_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 新增月台信息<br/>
     * 方法名：addPlatform
     * </p>
     * 
     * @author 078838-foss-zhangbin
     * @时间 2012-11-28
     * @since JDK1.6
     */
    @JSON
    public String addPlatform() {
	try {
	    UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    platformVo.getPlatformEntity().setCreateUser(userCode);
	    platformService.addPlatform(platformVo.getPlatformEntity());
	    return returnSuccess(MessageType.SAVE_PLATFORM_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 修改月台信息<br/>
     * 方法名：updatePlatform
     * </p>
     * 
     * @author 078838-foss-zhangbin
     * @时间 2012-11-28
     * @since JDK1.6
     */
    @JSON
    public String updatePlatform() {
	try {
	    UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    platformVo.getPlatformEntity().setModifyUser(userCode);
	    platformService.updatePlatform(platformVo.getPlatformEntity());
	    return returnSuccess(MessageType.UPDATE_PLATFORM_SUCCESS);
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 查询月台信息<br/>
     * 方法名：searchPlatform
     * </p>
     * 
     * @author 078838-foss-zhangbin
     * @时间 2012-11-28
     * @since JDK1.6
     */
    @JSON
    public String searchPlatform() {
	try {
	    PlatformEntity platformEntity = platformService
		    .queryPlatformByVirtualCode(platformVo.getPlatformEntity()
			    .getVirtualCode());
	    platformVo.setPlatformEntity(platformEntity);
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * .
     * <p>
     * 查询月台信息根据外场CODE<br/>
     * 方法名：searchPlatform
     * </p>
     * 
     * @author 078838-foss-zhangbin
     * @时间 2012-11-28
     * @since JDK1.6
     */
    @JSON
    public String searchPlatformByOrganizationCode() {
	try {
	    List<PlatformEntity> platformEntityList = platformService
		    .queryPlatformListByOrganizationCode(platformVo
			    .getPlatformEntity().getOrganizationCode());
	    platformVo.setPlatformEntityList(platformEntityList);
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	}
    }

    /**
     * 导出排班表信息
     * com.deppon.foss.module.base.baseinfo.server.action.PlatformAction.
     * exportPlatform
     * 
     * @author 078838-foss-zhangbin
     * @date 2012-12-10上午10:42:37
     */
    public String exportPlatform() {
	try {
	    downloadFileName = URLEncoder.encode(
		    ColumnConstants.EXPORT_PLATFORM_NAME, "UTF-8");

	    UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
	    String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
	    String currentOrgCode = user.getEmployee().getOrgCode();

	    //导出表中的月台数据
	    ExportResource exportResource =platformService.exportPlatformList(platformVo.getPlatformEntity(), userCode, currentOrgCode);
	    ExportSetting exportSetting = new ExportSetting();
	    //设置名称
	    exportSetting.setSheetName(ColumnConstants.EXPORT_PLATFORM_NAME);
	    //设置下载最大条数
//	    exportSetting.setSize(ColumnConstants.EXPORT_MAX_NUM);
	    ExporterExecutor objExporterExecutor = new ExporterExecutor();
	    // 导出成文件
	    inputStream =  objExporterExecutor.exportSync(exportResource, exportSetting);
	   
	    return returnSuccess();
	} catch (BusinessException e) {
	    return returnError(e);
	} catch (UnsupportedEncodingException e) {
	    return returnError("UnsupportedEncodingException", "");
	}

    }
    
    /**
     * 导入月台
     * 
     * @author 078838-foss-zhangbin
     * @date 2012-12-10下午4:51:08
     */
    public String importPlatform() {
		Workbook book = null;
		FileInputStream fileInputStream = null;
		try {
			if (uploadFile != null && !StringUtils.isEmpty(uploadFileFileName)) {
				try {
					//转换流
					inputStream =new FileInputStream(uploadFile);
					//解析excel流
					book =XlsImpUtil.create(inputStream);
				} catch (Exception e) {
					return returnError("数据异常:" + e.getMessage(), e);
				}
			} else {
				throw new FileException("请选择导入文件", "请选择导入文件");
			}
			if (book != null) {
				// 默认获取获取工作表1
				Sheet sheet = book.getSheetAt(0);// 默认第一个
				// 读取Excel的所用数据,考虑到数据量不大，暂时不适用集中缓存
				List<PlatformExcelDto> excelDtos = new ArrayList<PlatformExcelDto>();
				// 将Excel表格每行数据读入列表
				makeExcelDtos(excelDtos, sheet);
				if (CollectionUtils.isNotEmpty(excelDtos)) {
					UserEntity user = (UserEntity) UserContext.getCurrentUser();// 获取当前登录用户
					String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
					
					// 执行批量插入
					List<Integer> numList = platformService.importPlatformList(
							excelDtos, userCode);
					System.out.println("===44444444444444==========");
					platformVo.setNumList(numList);
				} else {
					throw new FileException("导入数据为空", "导入数据为空");
				}
			}
			return super.returnSuccess(MessageType.IMPORT_SUCCESS);
		} catch (FileException e) {
			e.printStackTrace();
			return super.returnError(e);
		} finally {
			if (book != null) {
				book = null;
			}
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					return returnError("文件关闭失败");
				}
			}
		}
    }

    /**
     * 将Excel表格每行数据读入列表
     * 
     * @author 078838-foss-zhangbin
     * @date 2012-12-10 上午10:46:31
     */
	private void makeExcelDtos(List<PlatformExcelDto> excelDtos, Sheet sheet) {
		if (sheet != null && excelDtos != null) {
			// 获取物理行数
			int rowCount = sheet.getPhysicalNumberOfRows();
			// 根据行数循环
			Row row = null;
			// EXCEL行记录
			PlatformExcelDto excelDto = null;
			// 根据行数循环
			for (int rowNum = 1; rowNum < rowCount; rowNum++) { 
				// 获取每行数据
				excelDto = new PlatformExcelDto();
				// 取得一行的数据
				row = sheet.getRow(rowNum);
				boolean isContinu = false;// 是否继续
				// 如果有一行空白则不会再录数据(一行数据全为空)
				// 循环取列值
				for (int colNum = 0; colNum < CELL_COUNT; colNum++) {
					
					// 由于读取EXCEL效率低下，故先取所有的值，再到内存中校验，提升性能（前提是数据量不大）
					Object [] rtn  = obtainCellValue(row, colNum, excelDto);
					boolean isSuccess = (Boolean) rtn[0];
					isContinu = isContinu || isSuccess;
				}
				if (!isContinu) {
					break;
				}
				excelDto.setRowNo(rowNum);
				excelDtos.add(excelDto);
			}
		}

    }

    /**
     * 单元格取值
     * 
     * @author 078838-foss-zhangbin
     * @date 2012-12-10 上午9:22:47
     */
    private String obtainStringVal(Cell cell) {
	// 列值
	String cellVal = "";
	// 单元格类型
	switch (cell.getCellType()) {
	case HSSFCell.CELL_TYPE_NUMERIC: // 数值型
	    if (HSSFDateUtil.isCellDateFormatted(cell)) {
		// 如果是date类型则 ，获取该cell的date值
		cellVal = com.deppon.foss.util.DateUtils.convert(
			HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),
			com.deppon.foss.util.DateUtils.DATE_FORMAT);
	    } else {// 纯数字
		cellVal = String.valueOf(cell.getNumericCellValue());
	    }
	    break;
	// 此行表示单元格的内容为string类型
	case HSSFCell.CELL_TYPE_STRING: // 字符串型
	    cellVal = cell.getRichStringCellValue().toString();
	    break;
	case HSSFCell.CELL_TYPE_FORMULA:// 公式型
	    // 读公式计算值
	    cellVal = String.valueOf(cell.getNumericCellValue());
	    if (cellVal.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
		cellVal = cell.getRichStringCellValue().toString();
	    }
	    break;
	case HSSFCell.CELL_TYPE_BOOLEAN:// 布尔
	    cellVal = " " + cell.getBooleanCellValue();
	    break;
	// 此行表示该单元格值为空
	case HSSFCell.CELL_TYPE_BLANK: // 空值
	    //cellVal = "";
	    //break;
	case HSSFCell.CELL_TYPE_ERROR: // 故障
	    cellVal = "";
	    break;
	default:
	    cellVal = cell.getRichStringCellValue().toString();
	}
	return cellVal;
    }

    /**
     * 获取单元格值
     * 
     * @author 078838-foss-zhangbin
     * @date 2012-12-10 下午7:37:43
     */
	private Object[] obtainCellValue(Row row, int colNum,
			PlatformExcelDto excelDto) {
		Object[] rtn = { true, "success", colNum };
		if (row != null) {
			Cell cell = row.getCell(colNum);
			if (cell != null) {
				// 取单元格值
				String cellVal = obtainStringVal(cell);
				if (StringUtil.isNotBlank(cellVal)) {
					Result result = this.sonarSplitOne(excelDto, colNum, cellVal, rtn);
					if(result.isFlg()){
						return result.getRtn();
					} else {
						return this.sonarSplitTwo(excelDto, colNum, cellVal, rtn);
					}
				}
			}
		}
		rtn[0] = false;
		rtn[1] = " 行为空";
		return rtn;
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private Result sonarSplitOne(PlatformExcelDto excelDto, int colNum,
			String cellVal, Object[] rtn) {
		Result result = this.new Result();
		switch (colNum) {
		case NumberConstants.NUMBER_1:// 外场编号
			excelDto.setTransferCode(cellVal);
			result.setRtn(rtn);
			result.setFlg(true);
			return result;
		case NumberConstants.NUMBER_2:// 月台编号
			if(cellVal.length()<NumberConstants.NUMBER_4){
			excelDto.setPlatformCode(cellVal);
			}else{
				rtn[0] = false;
				rtn[1] = "月台编码长度大于4";
			}
			result.setRtn(rtn);
			result.setFlg(true);
			return result;
		case NumberConstants.NUMBER_3:// 是否有升降台
			excelDto.setHasLift(cellVal);
			result.setRtn(rtn);
			result.setFlg(true);
			return result;
		case NumberConstants.NUMBER_4:// 月台高度
			excelDto.setHeight(cellVal);
			result.setRtn(rtn);
			result.setFlg(true);
			return result;
		case NumberConstants.NUMBER_5:// 月台宽度
			excelDto.setWidth(cellVal);
			result.setRtn(rtn);
			result.setFlg(true);
			return result;
		default: 
			result.setFlg(false);
			return result;
		}
	}
	
	/**
	 * sonar优化拆分
	 * 
	 * @author 313353
	 */
	private Object[] sonarSplitTwo(PlatformExcelDto excelDto, int colNum,
			String cellVal, Object[] rtn) {
		switch (colNum) {
		case NumberConstants.NUMBER_6:// 可停靠车型
			VehicleTypeEntity vehicleType = new VehicleTypeEntity();
			vehicleType.setVehicleLengthName(cellVal);
			vehicleType.setActive("Y");
			List<VehicleTypeEntity> vehicleTypeEntities = vehicleTypeService
					.queryVehicleTypesByCondition(vehicleType, 0, 1);
			if (vehicleTypeEntities.size() > 0) {
				vehicleType = vehicleTypeEntities.get(0);
			} else {
				rtn[0] = false;
				rtn[1] = "可停靠车型输入错误";
			}
			excelDto.setVehicleCode(vehicleType.getVehicleLengthCode());
			return rtn;
		case NumberConstants.NUMBER_7:// 月台位置
			excelDto.setPosition(cellVal);
			return rtn;
		case NumberConstants.NUMBER_8:// 月台位置
			excelDto.setPlatformType(cellVal);
			return rtn;
		case NumberConstants.NUMBER_9:// 横坐标
			excelDto.setAbscissa(cellVal);
			return rtn;
		case NumberConstants.NUMBER_10:// 纵坐标
			excelDto.setOrdinate(cellVal);
			return rtn;
		case NumberConstants.NUMBER_11:// 备注
			excelDto.setNotes(cellVal);
			return rtn;
		default: return rtn;
		}
	}
	
	private class Result{
		private Object[] rtn;
		private boolean flg;
		public Object[] getRtn() {
			return rtn;
		}
		public void setRtn(Object[] rtn) {
			this.rtn = rtn;
		}
		public boolean isFlg() {
			return flg;
		}
		public void setFlg(boolean flg) {
			this.flg = flg;
		}
	}
}
