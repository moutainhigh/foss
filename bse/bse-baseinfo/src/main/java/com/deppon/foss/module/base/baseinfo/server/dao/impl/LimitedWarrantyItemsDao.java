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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/LimitedWarrantyItemsDao.java
 * 
 * FILE NAME        	: LimitedWarrantyItemsDao.java
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
 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；


 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；


 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；
 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 * 1.5.3	界面描述-限保物品查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件:物品名称；
2.	查询结果列表：数据元素参见【限保物品查询结果列表】，双击某行记录可打开该条限保物品详情界面；
3.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开限保物品新增界面(图2)；
	修改：图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可打开限保物品修改界面(图2)；
	作废：【限保物品查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的限保物品信息；另外存在“作废”的图标按钮，位于【限保物品查询结果列表】最前面的操作列，点击可作废该行限保物品信息；
	查询：点击此按钮查询符合条件的限保物品信息；
	重置：重新初始化【限保物品查询条件】；
4.	提供的相关用例链接或提示信息：作废限保物品信息成功后，提示操作成功，同时刷新【限保物品查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。


1.5.5	界面描述-限保物品新增、修改界面
该界面分为两个部分：限保物品信息输入区域，功能按钮区；
1.	限保物品信息输入区域：字段包括物品名称、限保金额、描述； 
1.1	物品名称：不可重复，参见业务规则SR-1；
2.	功能按钮区：按钮包括保存、取消、重置；
	保存：点击此按钮保存限保物品信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
3.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。

SR-1	“物品名称”不可重复；

 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 限保物品 DAO
 * 
 * @author 087584-foss-lijun
 * @date 2012-12-18 下午3:57:32
 */
public class LimitedWarrantyItemsDao extends SqlSessionDaoSupport implements
	ILimitedWarrantyItemsDao {

    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX
	    + ".limitedWarrantyItems.";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:1:0
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao#addLimitedWarrantyItems(com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity)
     */
    @Override
    @Transactional
    public LimitedWarrantyItemsEntity addLimitedWarrantyItems(LimitedWarrantyItemsEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}
	
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	if(StringUtils.isBlank(entity.getVirtualCode())){
	    entity.setVirtualCode(entity.getId());
	}
	// CreateUser为传入的用户编码，CreateDate为当前时间
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setModifyUser(entity.getCreateUser());
	entity.setVersionNo(now.getTime());
	
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addLimitedWarrantyItems", entity);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:1:0
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao#deleteLimitedWarrantyItems(java.lang.String)
     */
    @Override
    @Transactional
    public LimitedWarrantyItemsEntity deleteLimitedWarrantyItems(LimitedWarrantyItemsEntity entity) {
	// 请求参数合法性验证
	if(null == entity){
	    return null;
	}
	if (StringUtils.isBlank(entity.getVirtualCode())) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	entity.setModifyDate(now);
	entity.setVersionNo(now.getTime());
	// entity应包含modifyUser,因此不用处理
	entity.setActive(FossConstants.INACTIVE);
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(NAMESPACE + "deleteLimitedWarrantyItems", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:1:0
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao#deleteLimitedWarrantyItemsMore(java.lang.String[], java.lang.String)
     */
    @Override
    @Transactional
    public LimitedWarrantyItemsEntity deleteLimitedWarrantyItemsMore(String[] codes , String deleteUser) {
	// 请求合法性判断：
	if(ArrayUtils.isEmpty(codes)) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	LimitedWarrantyItemsEntity entity = new LimitedWarrantyItemsEntity();

	entity.setModifyDate(now);
	entity.setVersionNo(now.getTime());
	entity.setModifyUser(deleteUser);
	entity.setActive(FossConstants.INACTIVE);
	
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("codes", codes);
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	
	int result = getSqlSession().update(
		NAMESPACE + "deleteLimitedWarrantyItemsMore", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过VIRTUAL_CODE标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:1:0
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao#updateLimitedWarrantyItems(com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity)
     */
    @Override
    @Transactional
    public LimitedWarrantyItemsEntity updateLimitedWarrantyItems(LimitedWarrantyItemsEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getVirtualCode())) {
	    return entity;
	}
	
	// 更新要先删除旧的数据：
	LimitedWarrantyItemsEntity result = this.deleteLimitedWarrantyItems(entity);
	if (result == null) {
	    String msg = "更新时，作废失败";
	    LOGGER.error(msg);
	}

	// 组装插入参数
	entity.setId(UUIDUtils.getUUID());
	// CreateUser为传入的用户编码，CreateDate为当前时间
	Date now = new Date();
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	entity.setVersionNo(now.getTime());
	
	entity.setActive(FossConstants.ACTIVE);
	int resultNum = getSqlSession().insert(NAMESPACE + "addLimitedWarrantyItems", entity);
	return resultNum > NumberConstants.ZERO ? entity : null;
    }



    /**
     * 以下全为查询：
     */
    
    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:1:0
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao#queryLimitedWarrantyItemsByCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public LimitedWarrantyItemsEntity queryLimitedWarrantyItemsByVirtualCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	
	// 构造查询条件：
	LimitedWarrantyItemsEntity entity=new LimitedWarrantyItemsEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setVirtualCode(code);
	
	List<LimitedWarrantyItemsEntity> entitys = this.getSqlSession().selectList(
		NAMESPACE + "queryLimitedWarrantyItemsByVirtualCode", entity);
	if (CollectionUtils.isEmpty(entitys)) {
	    return null;
	} else {
	    return entitys.get(NumberConstants.ZERO);
	}
    }

    
    /**
     * 精确查询
     * 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao#queryLimitedWarrantyItemsBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LimitedWarrantyItemsEntity> queryLimitedWarrantyItemsBatchByVirtualCode(
	    String[] codes) {
	// 请求参数合法性判断
	if (ArrayUtils.isEmpty(codes)){
	    return null;
	}
	
	// 构造查询条件：
	Map<String,Object> map = new HashMap<String , Object>();
	map.put("codes", codes);
	map.put("active", FossConstants.ACTIVE);
	
	return getSqlSession().selectList(
		NAMESPACE + "queryLimitedWarrantyItemsBatchByVirtualCode", map);
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao#queryLimitedWarrantyItemsExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LimitedWarrantyItemsEntity> queryLimitedWarrantyItemsExactByEntity(
	    LimitedWarrantyItemsEntity entity, int start, int limit) {
	LimitedWarrantyItemsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LimitedWarrantyItemsEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession()
		.selectList(NAMESPACE + "queryLimitedWarrantyItemsExactByEntity",
			queryEntity,
			rowBounds);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao#queryLimitedWarrantyItemsExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity)
     */
    @Override
    public long queryLimitedWarrantyItemsExactByEntityCount(LimitedWarrantyItemsEntity entity) {
	LimitedWarrantyItemsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LimitedWarrantyItemsEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(
		NAMESPACE + "queryLimitedWarrantyItemsExactByEntityCount",
		queryEntity);
    }

    /**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:1:0
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.ILimitedWarrantyItemsDao#queryLimitedWarrantyItemsMore(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LimitedWarrantyItemsEntity> queryLimitedWarrantyItemsByEntity(
	    LimitedWarrantyItemsEntity entity, int start, int limit) {
	LimitedWarrantyItemsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LimitedWarrantyItemsEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(NAMESPACE + "queryLimitedWarrantyItemsByEntity", queryEntity,
			rowBounds);
    }

    /**
     * 模糊查询
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午9:1:0
     * @see com.deppon.foss.module.base.baseinfo.server.dao.ILimitedWarrantyItemsDao#queryLimitedWarrantyItemsByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.LimitedWarrantyItemsEntity)
     */
    @Override
    public long queryLimitedWarrantyItemsByEntityCount(LimitedWarrantyItemsEntity entity) {
	LimitedWarrantyItemsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LimitedWarrantyItemsEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(NAMESPACE + "queryLimitedWarrantyItemsByEntityCount", queryEntity);
    }

	
    /**
     * 根据entity精确查询
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午6:22:37
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<LimitedWarrantyItemsEntity> queryLimitedWarrantyItemsForDownload(LimitedWarrantyItemsEntity entity){
	LimitedWarrantyItemsEntity queryEntity;
	if (null == entity) {
	    queryEntity = new LimitedWarrantyItemsEntity();
	}else{
	    queryEntity = entity;
	}
	return (List<LimitedWarrantyItemsEntity>) getSqlSession().selectList(NAMESPACE + "queryLimitedWarrantyItemsForDownload", queryEntity);
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LimitedWarrantyItemsDao.class);

}
