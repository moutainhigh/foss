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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/BillAdvertisingSloganService.java
 * 
 * FILE NAME        	: BillAdvertisingSloganService.java
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
 * 1.5.3	界面描述-单据广告语查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件包括广告语代码、广告语名称、所属子系统、子系统功能模块；
1.1	所属子系统：广告语所属的子系统，下拉框，读取自数据字典；
1.2	子系统功能模块：读取自数据字典，“所属子系统”下的子系统功能模块，选择“所属子系统”后，该控件自动过滤为所选子系统下的功能模块；
2.1.	查询结果列表：数据元素参见【单据广告语查询结果列表】，“广告语代码”字段添加超链接，点击超链可打开单据广告语详情界面；
3.2.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开单据广告语新增界面(图2)；
	修改：图标按钮，位于【单据广告语查询结果列表】最前面的操作列，点击可打开单据广告语修改界面(图2)；
	作废：【单据广告语查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的单据广告语信息；另外存在“作废”的图标按钮，位于【单据广告语查询结果列表】最前面的操作列，点击可作废该行单据广告语；
	查询：点击此按钮查询符合条件的单据广告语；
	重置：重新初始化【单据广告语查询条件】；
4.3.	提供的相关用例链接或提示信息：作废单据广告语成功后，提示操作成功，同时刷新【单据广告语查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。

1.5.5	界面描述-单据广告语新增、修改界面
该界面分为两个部分：单据广告语信息输入区域、部门广告语列表、功能按钮区；
1.	单据广告语信息输入区域：字段包括所属子系统、子系统功能模块、广告语代码、广告语名称、广告语内容；
1.1	所属子系统：广告语所属的子系统，下拉框，读取自数据字典；
1.2	子系统功能模块：读取自数据字典，“所属子系统”下的子系统功能模块，选择“所属子系统”后，该控件自动过滤为所选子系统下的功能模块；
1.31.1	广告语代码、广告语名称：参见业务规则SR-1；
2.	部门广告语列表：列表形式，字段包括“适用部门”、“广告语内容”，最前面的操作列包含有“修改”、“作废”的图标按钮，点击分别打开部门广告语修改界面(图3)、作废该行部门广告语；
3.	功能按钮区：按钮包括保存、取消、重置、添加部门广告语、作废；
	保存：点击此按钮保存单据广告语信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
	添加部门广告语：点击此按钮，弹出新增部门广告语界面(图3)；
	作废：点击该按钮，作废【部门广告语列表】中被勾选的列；
4.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。


1.5.7	界面描述-部门广告语新增、修改界面
本界面用于为某部门定制单据广告语，分为两个区域，字段输入区域，功能按钮区；
1、	字段输入区域：输入字段包括适用部门、广告语内容：
1.1	适用部门：共用选择框，读取行政组织，参见业务规则SR-2；
1.2	广告语内容：部门对应的广告语内容；
2、	功能按钮区：包括取消、重置、确定；
2.1	取消：点击该按钮退出当前界面；
2.2	重置：点击该按钮重新初始化界面字段；
2.3	确定：点击该按钮将输入的【部门广告语信息】添加至单据广告语新增、修改界面(图2)的【部门广告语列表】中。


SR-1	“广告语代码”不可重复；“广告语名称”不可重复；
SR-2	对于同一条单据广告语，每个部门在【部门广告语列表】中最多只能存在一条记录；

 * 1.5.3	界面描述-单据广告语查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件包括广告语代码、广告语名称、所属子系统、子系统功能模块；
1.1	所属子系统：广告语所属的子系统，下拉框，读取自数据字典；
1.2	子系统功能模块：读取自数据字典，“所属子系统”下的子系统功能模块，选择“所属子系统”后，该控件自动过滤为所选子系统下的功能模块；
2.1.	查询结果列表：数据元素参见【单据广告语查询结果列表】，“广告语代码”字段添加超链接，点击超链可打开单据广告语详情界面；
3.2.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开单据广告语新增界面(图2)；
	修改：图标按钮，位于【单据广告语查询结果列表】最前面的操作列，点击可打开单据广告语修改界面(图2)；
	作废：【单据广告语查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的单据广告语信息；另外存在“作废”的图标按钮，位于【单据广告语查询结果列表】最前面的操作列，点击可作废该行单据广告语；
	查询：点击此按钮查询符合条件的单据广告语；
	重置：重新初始化【单据广告语查询条件】；
4.3.	提供的相关用例链接或提示信息：作废单据广告语成功后，提示操作成功，同时刷新【单据广告语查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。

1.5.5	界面描述-单据广告语新增、修改界面
该界面分为两个部分：单据广告语信息输入区域、部门广告语列表、功能按钮区；
1.	单据广告语信息输入区域：字段包括所属子系统、子系统功能模块、广告语代码、广告语名称、广告语内容；
1.1	所属子系统：广告语所属的子系统，下拉框，读取自数据字典；
1.2	子系统功能模块：读取自数据字典，“所属子系统”下的子系统功能模块，选择“所属子系统”后，该控件自动过滤为所选子系统下的功能模块；
1.31.1	广告语代码、广告语名称：参见业务规则SR-1；
2.	部门广告语列表：列表形式，字段包括“适用部门”、“广告语内容”，最前面的操作列包含有“修改”、“作废”的图标按钮，点击分别打开部门广告语修改界面(图3)、作废该行部门广告语；
3.	功能按钮区：按钮包括保存、取消、重置、添加部门广告语、作废；
	保存：点击此按钮保存单据广告语信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
	添加部门广告语：点击此按钮，弹出新增部门广告语界面(图3)；
	作废：点击该按钮，作废【部门广告语列表】中被勾选的列；
4.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。


1.5.7	界面描述-部门广告语新增、修改界面
本界面用于为某部门定制单据广告语，分为两个区域，字段输入区域，功能按钮区；
1、	字段输入区域：输入字段包括适用部门、广告语内容：
1.1	适用部门：共用选择框，读取行政组织，参见业务规则SR-2；
1.2	广告语内容：部门对应的广告语内容；
2、	功能按钮区：包括取消、重置、确定；
2.1	取消：点击该按钮退出当前界面；
2.2	重置：点击该按钮重新初始化界面字段；
2.3	确定：点击该按钮将输入的【部门广告语信息】添加至单据广告语新增、修改界面(图2)的【部门广告语列表】中。


SR-1	“广告语代码”不可重复；“广告语名称”不可重复；
SR-2	对于同一条单据广告语，每个部门在【部门广告语列表】中最多只能存在一条记录；

 * 1.5.3	界面描述-单据广告语查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件包括广告语代码、广告语名称、所属子系统、子系统功能模块；
1.1	所属子系统：广告语所属的子系统，下拉框，读取自数据字典；
1.2	子系统功能模块：读取自数据字典，“所属子系统”下的子系统功能模块，选择“所属子系统”后，该控件自动过滤为所选子系统下的功能模块；
2.1.	查询结果列表：数据元素参见【单据广告语查询结果列表】，“广告语代码”字段添加超链接，点击超链可打开单据广告语详情界面；
3.2.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开单据广告语新增界面(图2)；
	修改：图标按钮，位于【单据广告语查询结果列表】最前面的操作列，点击可打开单据广告语修改界面(图2)；
	作废：【单据广告语查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的单据广告语信息；另外存在“作废”的图标按钮，位于【单据广告语查询结果列表】最前面的操作列，点击可作废该行单据广告语；
	查询：点击此按钮查询符合条件的单据广告语；
	重置：重新初始化【单据广告语查询条件】；
4.3.	提供的相关用例链接或提示信息：作废单据广告语成功后，提示操作成功，同时刷新【单据广告语查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。

1.5.5	界面描述-单据广告语新增、修改界面
该界面分为两个部分：单据广告语信息输入区域、部门广告语列表、功能按钮区；
1.	单据广告语信息输入区域：字段包括所属子系统、子系统功能模块、广告语代码、广告语名称、广告语内容；
1.1	所属子系统：广告语所属的子系统，下拉框，读取自数据字典；
1.2	子系统功能模块：读取自数据字典，“所属子系统”下的子系统功能模块，选择“所属子系统”后，该控件自动过滤为所选子系统下的功能模块；
1.31.1	广告语代码、广告语名称：参见业务规则SR-1；
2.	部门广告语列表：列表形式，字段包括“适用部门”、“广告语内容”，最前面的操作列包含有“修改”、“作废”的图标按钮，点击分别打开部门广告语修改界面(图3)、作废该行部门广告语；
3.	功能按钮区：按钮包括保存、取消、重置、添加部门广告语、作废；
	保存：点击此按钮保存单据广告语信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
	添加部门广告语：点击此按钮，弹出新增部门广告语界面(图3)；
	作废：点击该按钮，作废【部门广告语列表】中被勾选的列；
4.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。


1.5.7	界面描述-部门广告语新增、修改界面
本界面用于为某部门定制单据广告语，分为两个区域，字段输入区域，功能按钮区；
1、	字段输入区域：输入字段包括适用部门、广告语内容：
1.1	适用部门：共用选择框，读取行政组织，参见业务规则SR-2；
1.2	广告语内容：部门对应的广告语内容；
2、	功能按钮区：包括取消、重置、确定；
2.1	取消：点击该按钮退出当前界面；
2.2	重置：点击该按钮重新初始化界面字段；
2.3	确定：点击该按钮将输入的【部门广告语信息】添加至单据广告语新增、修改界面(图2)的【部门广告语列表】中。


SR-1	“广告语代码”不可重复；“广告语名称”不可重复；
SR-2	对于同一条单据广告语，每个部门在【部门广告语列表】中最多只能存在一条记录；

 * 1.5.3	界面描述-单据广告语查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件包括广告语代码、广告语名称、所属子系统、子系统功能模块；
1.1	所属子系统：广告语所属的子系统，下拉框，读取自数据字典；
1.2	子系统功能模块：读取自数据字典，“所属子系统”下的子系统功能模块，选择“所属子系统”后，该控件自动过滤为所选子系统下的功能模块；
2.1.	查询结果列表：数据元素参见【单据广告语查询结果列表】，“广告语代码”字段添加超链接，点击超链可打开单据广告语详情界面；
3.2.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开单据广告语新增界面(图2)；
	修改：图标按钮，位于【单据广告语查询结果列表】最前面的操作列，点击可打开单据广告语修改界面(图2)；
	作废：【单据广告语查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的单据广告语信息；另外存在“作废”的图标按钮，位于【单据广告语查询结果列表】最前面的操作列，点击可作废该行单据广告语；
	查询：点击此按钮查询符合条件的单据广告语；
	重置：重新初始化【单据广告语查询条件】；
4.3.	提供的相关用例链接或提示信息：作废单据广告语成功后，提示操作成功，同时刷新【单据广告语查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。

1.5.5	界面描述-单据广告语新增、修改界面
该界面分为两个部分：单据广告语信息输入区域、部门广告语列表、功能按钮区；
1.	单据广告语信息输入区域：字段包括所属子系统、子系统功能模块、广告语代码、广告语名称、广告语内容；
1.1	所属子系统：广告语所属的子系统，下拉框，读取自数据字典；
1.2	子系统功能模块：读取自数据字典，“所属子系统”下的子系统功能模块，选择“所属子系统”后，该控件自动过滤为所选子系统下的功能模块；
1.31.1	广告语代码、广告语名称：参见业务规则SR-1；
2.	部门广告语列表：列表形式，字段包括“适用部门”、“广告语内容”，最前面的操作列包含有“修改”、“作废”的图标按钮，点击分别打开部门广告语修改界面(图3)、作废该行部门广告语；
3.	功能按钮区：按钮包括保存、取消、重置、添加部门广告语、作废；
	保存：点击此按钮保存单据广告语信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
	添加部门广告语：点击此按钮，弹出新增部门广告语界面(图3)；
	作废：点击该按钮，作废【部门广告语列表】中被勾选的列；
4.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。


1.5.7	界面描述-部门广告语新增、修改界面
本界面用于为某部门定制单据广告语，分为两个区域，字段输入区域，功能按钮区；
1、	字段输入区域：输入字段包括适用部门、广告语内容：
1.1	适用部门：共用选择框，读取行政组织，参见业务规则SR-2；
1.2	广告语内容：部门对应的广告语内容；
2、	功能按钮区：包括取消、重置、确定；
2.1	取消：点击该按钮退出当前界面；
2.2	重置：点击该按钮重新初始化界面字段；
2.3	确定：点击该按钮将输入的【部门广告语信息】添加至单据广告语新增、修改界面(图2)的【部门广告语列表】中。


SR-1	“广告语代码”不可重复；“广告语名称”不可重复；
SR-2	对于同一条单据广告语，每个部门在【部门广告语列表】中最多只能存在一条记录；

 * 1.5.3	界面描述-单据广告语查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件包括广告语代码、广告语名称、所属子系统、子系统功能模块；
1.1	所属子系统：广告语所属的子系统，下拉框，读取自数据字典；
1.2	子系统功能模块：读取自数据字典，“所属子系统”下的子系统功能模块，选择“所属子系统”后，该控件自动过滤为所选子系统下的功能模块；
2.1.	查询结果列表：数据元素参见【单据广告语查询结果列表】，“广告语代码”字段添加超链接，点击超链可打开单据广告语详情界面；
3.2.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开单据广告语新增界面(图2)；
	修改：图标按钮，位于【单据广告语查询结果列表】最前面的操作列，点击可打开单据广告语修改界面(图2)；
	作废：【单据广告语查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的单据广告语信息；另外存在“作废”的图标按钮，位于【单据广告语查询结果列表】最前面的操作列，点击可作废该行单据广告语；
	查询：点击此按钮查询符合条件的单据广告语；
	重置：重新初始化【单据广告语查询条件】；
4.3.	提供的相关用例链接或提示信息：作废单据广告语成功后，提示操作成功，同时刷新【单据广告语查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。

1.5.5	界面描述-单据广告语新增、修改界面
该界面分为两个部分：单据广告语信息输入区域、部门广告语列表、功能按钮区；
1.	单据广告语信息输入区域：字段包括所属子系统、子系统功能模块、广告语代码、广告语名称、广告语内容；
1.1	所属子系统：广告语所属的子系统，下拉框，读取自数据字典；
1.2	子系统功能模块：读取自数据字典，“所属子系统”下的子系统功能模块，选择“所属子系统”后，该控件自动过滤为所选子系统下的功能模块；
1.31.1	广告语代码、广告语名称：参见业务规则SR-1；
2.	部门广告语列表：列表形式，字段包括“适用部门”、“广告语内容”，最前面的操作列包含有“修改”、“作废”的图标按钮，点击分别打开部门广告语修改界面(图3)、作废该行部门广告语；
3.	功能按钮区：按钮包括保存、取消、重置、添加部门广告语、作废；
	保存：点击此按钮保存单据广告语信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
	添加部门广告语：点击此按钮，弹出新增部门广告语界面(图3)；
	作废：点击该按钮，作废【部门广告语列表】中被勾选的列；
4.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。


1.5.7	界面描述-部门广告语新增、修改界面
本界面用于为某部门定制单据广告语，分为两个区域，字段输入区域，功能按钮区；
1、	字段输入区域：输入字段包括适用部门、广告语内容：
1.1	适用部门：共用选择框，读取行政组织，参见业务规则SR-2；
1.2	广告语内容：部门对应的广告语内容；
2、	功能按钮区：包括取消、重置、确定；
2.1	取消：点击该按钮退出当前界面；
2.2	重置：点击该按钮重新初始化界面字段；
2.3	确定：点击该按钮将输入的【部门广告语信息】添加至单据广告语新增、修改界面(图2)的【部门广告语列表】中。


SR-1	“广告语代码”不可重复；“广告语名称”不可重复；
SR-2	对于同一条单据广告语，每个部门在【部门广告语列表】中最多只能存在一条记录；
 * 1.5.3	界面描述-单据广告语查询界面
该界面分为三个部分：查询条件区域、查询结果列表、功能按钮区；
1.	查询条件区域：查询条件包括广告语代码、广告语名称、所属子系统、子系统功能模块；
1.1	所属子系统：广告语所属的子系统，下拉框，读取自数据字典；
1.2	子系统功能模块：读取自数据字典，“所属子系统”下的子系统功能模块，选择“所属子系统”后，该控件自动过滤为所选子系统下的功能模块；
2.1.	查询结果列表：数据元素参见【单据广告语查询结果列表】，“广告语代码”字段添加超链接，点击超链可打开单据广告语详情界面；
3.2.	功能按钮区：按钮包括新增、修改、作废、查询、重置；
	新增：此按钮位于系统悬浮工具条中，点击可打开单据广告语新增界面(图2)；
	修改：图标按钮，位于【单据广告语查询结果列表】最前面的操作列，点击可打开单据广告语修改界面(图2)；
	作废：【单据广告语查询结果列表】顶部和下部各有一个“作废”按钮，点击可作废选中的单据广告语信息；另外存在“作废”的图标按钮，位于【单据广告语查询结果列表】最前面的操作列，点击可作废该行单据广告语；
	查询：点击此按钮查询符合条件的单据广告语；
	重置：重新初始化【单据广告语查询条件】；
4.3.	提供的相关用例链接或提示信息：作废单据广告语成功后，提示操作成功，同时刷新【单据广告语查询结果列表】，停留在查询页面，可继续执行本用例；作废失败后，提示失败原因。

1.5.5	界面描述-单据广告语新增、修改界面
该界面分为两个部分：单据广告语信息输入区域、部门广告语列表、功能按钮区；
1.	单据广告语信息输入区域：字段包括所属子系统、子系统功能模块、广告语代码、广告语名称、广告语内容；
1.1	所属子系统：广告语所属的子系统，下拉框，读取自数据字典；
1.2	子系统功能模块：读取自数据字典，“所属子系统”下的子系统功能模块，选择“所属子系统”后，该控件自动过滤为所选子系统下的功能模块；
1.31.1	广告语代码、广告语名称：参见业务规则SR-1；
2.	部门广告语列表：列表形式，字段包括“适用部门”、“广告语内容”，最前面的操作列包含有“修改”、“作废”的图标按钮，点击分别打开部门广告语修改界面(图3)、作废该行部门广告语；
3.	功能按钮区：按钮包括保存、取消、重置、添加部门广告语、作废；
	保存：点击此按钮保存单据广告语信息；
	取消：退出当前界面；
	重置：点击此按钮重新初始化输入区域各控件；
	添加部门广告语：点击此按钮，弹出新增部门广告语界面(图3)；
	作废：点击该按钮，作废【部门广告语列表】中被勾选的列；
4.	提供的相关用例链接或提示信息：保存成功后，提示操作成功，停留在当前界面，界面中各控件不可输入；保存失败后，提示失败原因。


1.5.7	界面描述-部门广告语新增、修改界面
本界面用于为某部门定制单据广告语，分为两个区域，字段输入区域，功能按钮区；
1、	字段输入区域：输入字段包括适用部门、广告语内容：
1.1	适用部门：共用选择框，读取行政组织，参见业务规则SR-2；
1.2	广告语内容：部门对应的广告语内容；
2、	功能按钮区：包括取消、重置、确定；
2.1	取消：点击该按钮退出当前界面；
2.2	重置：点击该按钮重新初始化界面字段；
2.3	确定：点击该按钮将输入的【部门广告语信息】添加至单据广告语新增、修改界面(图2)的【部门广告语列表】中。


SR-1	“广告语代码”不可重复；“广告语名称”不可重复；
SR-2	对于同一条单据广告语，每个部门在【部门广告语列表】中最多只能存在一条记录；

*1.5.7	界面描述-部门广告语新增、修改界面
本界面用于为某部门定制单据广告语，分为两个区域，字段输入区域，功能按钮区；
1、	字段输入区域：输入字段包括适用部门、广告语内容：
1.1	适用部门：共用选择框，读取行政组织，参见业务规则SR-2；
1.2	广告语内容：部门对应的广告语内容；
2、	功能按钮区：包括取消、重置、确定；
2.1	取消：点击该按钮退出当前界面；
2.2	重置：点击该按钮重新初始化界面字段；
2.3	确定：点击该按钮将输入的【部门广告语信息】添加至单据广告语新增、修改界面(图2)的【部门广告语列表】中。


SR-1	“广告语代码”不可重复；“广告语名称”不可重复；
SR-2	对于同一条单据广告语，每个部门在【部门广告语列表】中最多只能存在一条记录；

*SR-1	“广告语代码”不可重复；“广告语名称”不可重复；
SR-2	对于同一条单据广告语，每个部门在【部门广告语列表】中最多只能存在一条记录；
SR-2	对于同一条单据广告语，每个部门在【部门广告语列表】中最多只能存在一条记录；

 */
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillAdvertisingSloganDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganForDepService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService;
import com.deppon.foss.module.base.baseinfo.api.server.service.complex.IOrgAdministrativeInfoComplexService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BillAdvertisingSloganException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 单据广告语service接口实现：提供对单据广告语的增删改查的基本操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-13 上午10:08:51
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-13 上午10:08:51
 * @since
 * @version
 */
public class BillAdvertisingSloganService implements
	IBillAdvertisingSloganService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(BillAdvertisingSloganService.class);

    /**
     * 单据广告语Dao接口.
     */
    private IBillAdvertisingSloganDao billAdvertisingSloganDao;
    
    /**
     * 行政组织接口.
     */
    private IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService;
    
    /**
     * 部门单据广告语service接口.
     */
    private IBillAdvertisingSloganForDepService billAdvertisingSloganForDepService;

    /**
     * 设置 单据广告语Dao接口.
     *
     * @param billAdvertisingSloganDao the new 单据广告语Dao接口
     */
    public void setBillAdvertisingSloganDao(
	    IBillAdvertisingSloganDao billAdvertisingSloganDao) {
	this.billAdvertisingSloganDao = billAdvertisingSloganDao;
    }
    
    /**
     * 设置 行政组织接口.
     *
     * @param orgAdministrativeInfoComplexService the orgAdministrativeInfoComplexService to set
     */
    public void setOrgAdministrativeInfoComplexService(
    	IOrgAdministrativeInfoComplexService orgAdministrativeInfoComplexService) {
        this.orgAdministrativeInfoComplexService = orgAdministrativeInfoComplexService;
    }
    
    /**
     * 设置 部门单据广告语service接口.
     *
     * @param billAdvertisingSloganForDepService the billAdvertisingSloganForDepService to set
     */
    public void setBillAdvertisingSloganForDepService(
    	IBillAdvertisingSloganForDepService billAdvertisingSloganForDepService) {
        this.billAdvertisingSloganForDepService = billAdvertisingSloganForDepService;
    }

    /**
     * 新增单据广告语.
     *
     * @param entity 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午10:21:50
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService#addBillAdvertisingSlogan(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity)
     */
    @Transactional
    @Override
    public int addBillAdvertisingSlogan(BillSloganEntity entity) {

	if (null == entity) {
	    throw new BillAdvertisingSloganException("传入的参数不允许为空！");
	} else {
	    BillSloganEntity smsSlogan = queryBillSloganBySmsSloganCode(entity
		    .getSloganCode(),null);
	    if (null != smsSlogan) {
		throw new BillAdvertisingSloganException("单据广告语代码不允许重复！");
	    }
	    smsSlogan = queryBillSloganBySmsSloganName(entity.getSloganName(),null);
	    if (null != smsSlogan) {
		throw new BillAdvertisingSloganException("单据广告语名称不允许重复！");
	    }
	    entity.setId(UUIDUtils.getUUID());
	    entity.setCreateDate(new Date());
	    // 设置广告语类型为：单据广告语
	    entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_BILL);
	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	    entity.setVirtualCode(entity.getId());
	    entity.setActive(FossConstants.ACTIVE);

	    return billAdvertisingSloganDao.addBillAdvertisingSlogan(entity);
	}
    }

    /**
     * 根据code作废单据广告语信息.
     *
     * @param codeStr 
     * @param modifyUser 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午10:21:59
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService#deleteBillAdvertisingSloganByCode(java.lang.String[])
     */
    @Transactional
    @Override
    public int deleteBillAdvertisingSloganByCode(String codeStr,
	    String modifyUser) {

	if (StringUtil.isBlank(codeStr)) {
	    throw new BillAdvertisingSloganException("单据广告语虚拟编码不允许为空！");
	} else {
	    LOGGER.debug("codeStr: " + codeStr + "modifyUser: " + modifyUser);
	    String[] codes = StringUtil.split(codeStr, SymbolConstants.EN_COMMA);

	    return billAdvertisingSloganDao.deleteBillAdvertisingSloganByCode(
		    codes, modifyUser);
	}
    }

    /**
     * 根据传入对象查询符合条件所有单据广告语信息.
     *
     * @param entity 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午10:22:09
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService#updateBillAdvertisingSlogan(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity)
     */
    @Transactional
    @Override
    public int updateBillAdvertisingSlogan(BillSloganEntity entity) {

	if (null == entity) {
	    return FossConstants.FAILURE;
	} else if (StringUtil.isBlank(entity.getVirtualCode())) {
	    throw new BillAdvertisingSloganException("单据广告语虚拟编码不允许为空！");
	} else {
	    BillSloganEntity smsSlogan = queryBillSloganBySmsSloganCode(entity
		    .getSloganCode(),entity.getId());
	    if (null != smsSlogan) {
		throw new BillAdvertisingSloganException("单据广告语代码不允许重复！");
	    }
	    smsSlogan = queryBillSloganBySmsSloganName(entity.getSloganName(),entity.getId());
	    if (null != smsSlogan) {
		throw new BillAdvertisingSloganException("单据广告语名称不允许重复！");
	    }
	    // 设置广告语类型为：单据广告语
	    entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_BILL);
	    return billAdvertisingSloganDao.updateBillAdvertisingSlogan(entity);
	}
    }

    /**
     * 根据传入对象查询符合条件所有单据广告语信息.
     *
     * @param entity 
     * @param limit 
     * @param start 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午10:22:20
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService#queryBillAdvertisingSlogans(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity,
     * int, int)
     */
    @Transactional
    @Override
    public List<BillSloganEntity> queryBillAdvertisingSlogans(
	    BillSloganEntity entity, int limit, int start) {

	entity.setActive(FossConstants.ACTIVE);
	// 设置广告语类型为：单据广告语
	entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_BILL);

	return billAdvertisingSloganDao.queryBillAdvertisingSlogans(entity,
		limit, start);
    }

    /**
     * 统计总记录数.
     *
     * @param entity 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-10-13 上午10:23:48
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity)
     */
    @Transactional
    @Override
    public Long queryRecordCount(BillSloganEntity entity) {

	entity.setActive(FossConstants.ACTIVE);
	// 设置广告语类型为：单据广告语
	entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_BILL);

	return billAdvertisingSloganDao.queryRecordCount(entity);
    }

    /**
     * <p>
     * 根据单据广告语代码查询广告语内容
     * </p>.
     *
     * @param billSloganCode 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-12-4 上午10:10:54
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService#queryBillSloganContent(java.lang.String)
     */
    @Override
    public String queryBillSloganContent(String billSloganCode) {

	if (StringUtil.isBlank(billSloganCode)) {
	    throw new BillAdvertisingSloganException("单据广告语代码不允许为空！");
	} else {
	    BillSloganEntity entity = billAdvertisingSloganDao
		    .queryBillSloganContent(billSloganCode,null);
	    if (null != entity) {
		return entity.getContent();
	    } else {
		return null;
	    }
	}
    }
    
    /**
     * <p>根据单据广告语代码、部门编码查询广告语内容</p>.
     *
     * @param billSloganCode 广告月CODE
     * @param deptCode 部门编码
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-4-25 上午9:11:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService#queryBillSloganContent(java.lang.String, java.lang.String)
     */
    @Override
    public String queryBillSloganContent(String billSloganCode, String deptCode) {
	if (StringUtil.isBlank(billSloganCode)) {
	    throw new BillAdvertisingSloganException("单据广告语代码不允许为空！");
	} else {
	    //根据单据广告语代码查询单据广告语信息
	    BillSloganEntity entity = billAdvertisingSloganDao
		    .queryBillSloganContent(billSloganCode,null);
	    if (null == entity) {
		return null;
	    } else {
		//获取广告语内容
		String message = entity.getContent();
		if(StringUtil.isBlank(deptCode)){
		    return message;
		}else {
		    //根据部门编码查询本部门以及所有的上级部门
		    List<OrgAdministrativeInfoEntity> orgList = orgAdministrativeInfoComplexService.queryOrgAdministrativeInfoEntityAllUpByCode(deptCode);
		    if(CollectionUtils.isEmpty(orgList)){
			return message;
		    }
		    String orgCode = deptCode;
		    BillSloganAppOrgEntity appOrgEntity = null;
		    for(int i = 0; i < orgList.size();i++){
			//根据单据广告语虚拟编码、部门编码查询部门单据广告语 
			appOrgEntity = billAdvertisingSloganForDepService.querySloganAppOrgByCode(orgCode, entity.getVirtualCode(), null);
			if(appOrgEntity != null){
			    //获取 部门广告语内容.
			    message = appOrgEntity.getSloganContent();
			    break;
			}else {
			    //获取上级部门编码
			    orgCode = getParentOrgCode(orgList, orgCode);
			}
		    }
		    return message;
		}
	    }
	}
    }
    
    /**
     * <p>根据传入的部门编码查询上级部门的部门编码</p>.
     *
     * @param list 
     * @param orgCode 
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2012-11-17 上午8:11:55
     * @see
     */
    private String getParentOrgCode(List<OrgAdministrativeInfoEntity> list,String orgCode){
	
	Map<String, OrgAdministrativeInfoEntity> codeMap = new HashMap<String, OrgAdministrativeInfoEntity>();
	Map<String, OrgAdministrativeInfoEntity> unicodeMap = new HashMap<String, OrgAdministrativeInfoEntity>();
	for(OrgAdministrativeInfoEntity entity : list){
	    //组织编码作为key把集合中的组织实体放在map中
	    codeMap.put(entity.getCode(), entity);
	    //组织的标杆编码作为key把集合中的组织实体放在map中
	    unicodeMap.put(entity.getUnifiedCode(), entity);
	}
	LOGGER.debug("orgCode: "+ orgCode);
	//通过部门编码查询该部门的实体
	OrgAdministrativeInfoEntity orgEntity = codeMap.get(orgCode);
	
	if(orgEntity != null){
	    //如果上级标杆编码为空
	    if(StringUtil.isNotBlank(orgEntity.getParentOrgUnifiedCode())){
		
		OrgAdministrativeInfoEntity parentOrg = unicodeMap.get(orgEntity.getParentOrgUnifiedCode());
		if(null != parentOrg){
		  //通过部门的上级部门标杆编码查询上级部门编码
		    return parentOrg.getCode();
		}else {
		    return orgCode;
		}
	    }
	    return orgCode;
	}
	//没有上级，返回本部门的部门编码
	return orgCode;
    }

    
    /**
     * <p>根据单据广告语代码查询单据广告语</p>.
     *
     * @param sloganCode 单据广告语代码
     * @param sloganId 单据广告语ID
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService#queryBillSloganBySmsSloganCode(java.lang.String)
     */
    @Override
    public BillSloganEntity queryBillSloganBySmsSloganCode(String sloganCode,String sloganId) {
	if(StringUtil.isBlank(sloganCode)){
	    throw new BillAdvertisingSloganException("单据广告语代码不允许为空！");
	}else {
	    return billAdvertisingSloganDao.queryBillSloganContent(sloganCode,sloganId);
	}
    }
    
    /**
     * <p>根据单据广告语名称查询单据广告语</p>.
     *
     * @param sloganName 单据广告语名称
     * @param sloganId 单据广告语ID
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganService#queryBillSloganBySmsSloganName(java.lang.String)
     */
    @Override
    public BillSloganEntity queryBillSloganBySmsSloganName(String sloganName,String sloganId) {
	if(StringUtil.isBlank(sloganName)){
	    throw new BillAdvertisingSloganException("单据广告语名称不允许为空！");
	}else {
	    return billAdvertisingSloganDao.queryBillSloganBySmsSloganName(sloganName,sloganId);
	}
    }
    
}
