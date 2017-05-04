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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/service/impl/BillAdvertisingSloganForDepService.java
 * 
 * FILE NAME        	: BillAdvertisingSloganForDepService.java
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

 */
package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.base.util.define.SymbolConstants;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IBillAdvertisingSloganForDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganForDepService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganAppOrgEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.BillAdvertisingSloganForDepException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 部门单据广告语service接口实现：对部门单据广告语提供增删改查操作
 * <p style="display:none">
 * modifyRecord
 * </p>
 * <p style="display:none">
 * version:V1.0,author:094463-foss-xieyantao,date:2012-10-18 上午9:49:29
 * </p>
 * 
 * @author 094463-foss-xieyantao
 * @date 2012-10-18 上午9:49:29
 * @since
 * @version
 */
public class BillAdvertisingSloganForDepService implements
	IBillAdvertisingSloganForDepService {

    /**
     * 日志.
     */
    private static final Logger LOGGER = LoggerFactory
	    .getLogger(BillAdvertisingSloganForDepService.class);

    /**
     * 部门单据广告语Dao接口.
     */
    private IBillAdvertisingSloganForDeptDao billAdvertisingSloganForDeptDao;
    
    /**
     * 组织信息 Service接口.
     */
    private IOrgAdministrativeInfoService orgAdministrativeInfoService;
    
    /**
     * 设置 部门单据广告语Dao接口.
     *
     * @param billAdvertisingSloganForDeptDao the new 部门单据广告语Dao接口
     */
    public void setBillAdvertisingSloganForDeptDao(
	    IBillAdvertisingSloganForDeptDao billAdvertisingSloganForDeptDao) {
	this.billAdvertisingSloganForDeptDao = billAdvertisingSloganForDeptDao;
    }
    
    /**
     * 设置 组织信息 Service接口.
     *
     * @param orgAdministrativeInfoService the orgAdministrativeInfoService to set
     */
    public void setOrgAdministrativeInfoService(
    	IOrgAdministrativeInfoService orgAdministrativeInfoService) {
        this.orgAdministrativeInfoService = orgAdministrativeInfoService;
    }

    /**
     * 新增部门单据广告语.
     *
     * @param entity 
     * @return 
     * @throws BillAdvertisingSloganForDepException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-18 上午9:49:29
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganForDepService#addBillAdvertisingSloganForDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity)
     */
    @Override
    public int addBillAdvertisingSloganForDept(BillSloganAppOrgEntity entity)
	    throws BillAdvertisingSloganForDepException {

	if (null == entity) {
	    throw new BillAdvertisingSloganForDepException("传入的参数不允许为空！");
	} else {
	    BillSloganAppOrgEntity sloganAppOrg = querySloganAppOrgByCode(entity.getOrgCode(),
		    entity.getSloganCode(),null);
	    if (null != sloganAppOrg) {
		throw new BillAdvertisingSloganForDepException("一个部门只能存在一条单据广告语！");
	    }
	    entity.setId(UUIDUtils.getUUID());
	    entity.setCreateDate(new Date());
	    entity.setActive(FossConstants.ACTIVE);
	    entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	    // 设置广告语类型为：单据广告语
	    entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_BILL);
	    LOGGER.debug("sloganSort: " + entity.getSloganSort());

	    return billAdvertisingSloganForDeptDao
		    .addBillAdvertisingSloganForDept(entity);
	}
    }

    /**
     * 根据id作废部门单据广告语信息.
     *
     * @param idstr 
     * @param modifyUser 
     * @return 
     * @throws BillAdvertisingSloganForDepException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-18 上午9:49:29
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganForDepService#deleteBillAdvertisingSloganForDeptByCode(java.lang.String,
     * java.lang.String)
     */
    @Override
    public int deleteBillAdvertisingSloganForDeptByCode(String idstr,
	    String modifyUser) throws BillAdvertisingSloganForDepException {

	if (StringUtil.isBlank(idstr)) {
	    throw new BillAdvertisingSloganForDepException("部门单据广告语ID不允许为空！");
	} else {
	    String[] ids = StringUtil.split(idstr, SymbolConstants.EN_COMMA);
	    LOGGER.debug("idstr: " + idstr + "modifyUser :" + modifyUser);

	    return billAdvertisingSloganForDeptDao
		    .deleteBillAdvertisingSloganForDeptByCode(ids, modifyUser);
	}
    }

    /**
     * 修改单据广告语信息.
     *
     * @param entity 
     * @return 
     * @throws BillAdvertisingSloganForDepException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-18 上午9:49:29
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganForDepService#updateBillAdvertisingSloganForDept(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity)
     */
    @Override
    public int updateBillAdvertisingSloganForDept(BillSloganAppOrgEntity entity)
	    throws BillAdvertisingSloganForDepException {

	if (null == entity) {
	    throw new BillAdvertisingSloganForDepException("传入的实体不允许为空！");
	} else if (StringUtil.isBlank(entity.getId())) {
	    throw new BillAdvertisingSloganForDepException("部门单据广告语ID不允许为空！");
	} else {
	    LOGGER.debug("ID: "+entity.getId());
	    
	    BillSloganAppOrgEntity sloganAppOrg = querySloganAppOrgByCode(entity.getOrgCode(),
		    entity.getSloganCode(),entity.getId());
	    if (null != sloganAppOrg) {
		throw new BillAdvertisingSloganForDepException("一个部门只能存在一条单据广告语！");
	    }
	    // 设置广告语类型为：单据广告语
	    entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_BILL);
	    return billAdvertisingSloganForDeptDao
		    .updateBillAdvertisingSloganForDept(entity);
	}
    }

    /**
     * 根据传入对象查询符合条件所有部门单据广告语信息.
     *
     * @param entity 
     * @param limit 
     * @param start 
     * @return 
     * @throws BillAdvertisingSloganForDepException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-18 上午9:49:29
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganForDepService#queryBillAdvertisingSloganForDepts(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity,
     * int, int)
     */
    @Override
    public List<BillSloganAppOrgEntity> queryBillAdvertisingSloganForDepts(
	    BillSloganAppOrgEntity entity, int limit, int start)
	    throws BillAdvertisingSloganForDepException {
	entity.setActive(FossConstants.ACTIVE);
	// 设置广告语类型为：单据广告语
	entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_BILL);
	return convertInfoList(billAdvertisingSloganForDeptDao
		.queryBillAdvertisingSloganForDepts(entity, limit, start));
    }

    /**
     * 统计总记录数.
     *
     * @param entity 
     * @return 
     * @throws BillAdvertisingSloganForDepException 
     * @author 094463-foss-xieyantao
     * @date 2012-10-18 上午9:49:29
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganForDepService#getCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.BillSloganEntity)
     */
    @Override
    public Long queryRecordCount(BillSloganAppOrgEntity entity)
	    throws BillAdvertisingSloganForDepException {
	entity.setActive(FossConstants.ACTIVE);
	// 设置广告语类型为：单据广告语
	entity.setSloganSort(DictionaryValueConstants.BSE_SLOGAN_TYPE_BILL);
	return billAdvertisingSloganForDeptDao.queryRecordCount(entity);
    }
    
    /**
     * <p>根据单据广告语虚拟编码、部门编码查询部门单据广告语</p>.
     *
     * @param orgCode 部门编码
     * @param sloganVirtualCode 单据广告语虚拟编码
     * @param appOrgId 部门单据广告语ID
     * @return 
     * @author 094463-foss-xieyantao
     * @date 2013-1-10 下午2:55:12
     * @see com.deppon.foss.module.base.baseinfo.api.server.service.IBillAdvertisingSloganForDepService#querySloganAppOrgByCode(java.lang.String, java.lang.String)
     */
    @Override
    public BillSloganAppOrgEntity querySloganAppOrgByCode(String orgCode,
	    String sloganVirtualCode,String appOrgId) {
	if(StringUtil.isBlank(orgCode)||StringUtil.isBlank(sloganVirtualCode)){
	    throw new BillAdvertisingSloganForDepException("传入的参数不允许为空！");
	}else {
	    return convertAppOrg(billAdvertisingSloganForDeptDao.querySloganAppOrgByCode(orgCode, sloganVirtualCode,appOrgId));
	}
    }
    
    /**
     * <p>
     * 填充部门名称
     * </p>
     * .
     * 
     * @param entity
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-12 上午9:12:38
     * @see
     */
    private BillSloganAppOrgEntity convertAppOrg(
	    BillSloganAppOrgEntity entity) {
	if (null == entity) {
	    return null;
	} else {
	    // 通过部门编码获得该部门的实体
	    OrgAdministrativeInfoEntity org = orgAdministrativeInfoService
		    .queryOrgAdministrativeInfoByCode(entity.getOrgCode());

	    if (null != org) {
		// 设置管理公司名称
		entity.setOrgName(org.getName());
	    }
	    
	    return entity;
	}

    }

    /**
     * <p>
     * 填充部门名称
     * </p>
     * .
     * 
     * @param list
     * @return
     * @author 094463-foss-xieyantao
     * @date 2012-11-12 上午9:13:00
     * @see
     */
    private List<BillSloganAppOrgEntity> convertInfoList(
	    List<BillSloganAppOrgEntity> list) {
	List<BillSloganAppOrgEntity> entities = new ArrayList<BillSloganAppOrgEntity>();

	if (CollectionUtils.isNotEmpty(list)) {
	    for (BillSloganAppOrgEntity entity : list) {
		entity = convertAppOrg(entity);
		entities.add(entity);
	    }
	    return entities;
	} else {
	    return null;
	}
    }

}
