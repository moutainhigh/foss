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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/server/dao/impl/OrgAdministrativeInfoDao.java
 * 
 * FILE NAME        	: OrgAdministrativeInfoDao.java
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
 * 1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。

1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。

1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。

SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。

 * 1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。

1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。

1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。

SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。

 * 1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。

1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。

1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。

SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。

 * 1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。

1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。

1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。

SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。

 * 1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。

1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。

1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。

SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。

 * 1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。

1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。

1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。

SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。

 * 1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。

1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。

1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。

SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。

 * 1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。

1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。

1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。

SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。

 * 1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。

1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。

1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。

SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。

 * 1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。

1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。

1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。

SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。

 * 1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。

1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。

1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。

SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。

 * 1.5.3	界面描述-主界面
1.	功能按钮区域
1)	查询按钮：输入查询条件，点击查询按钮，系统返回查询结果，刷新查询列表。
2)	查看详细信息：点击树形结构中的组织名称，在树型结构的右边显示这个组织的详情，参见【图三：行政组织业务属性详情界面】。
2.	字段输入区域
1)	参见数据元素【行政组织业务属性查询条件】。
3.	树型结构区域
1)	树型结构展示了行政组织的名称，点击树型结构中的部门名称，在右边显示这个部门的业务属性详情。
2)	点击树形结构的“+”，在组织树中展开该行政组织下一级的所有行政组织。点击树形结构的“-”，则收起该行政组织下一级的所有行政组织。

1.5.5	界面描述-行政组织业务属性修改界面
1.	功能按钮区域
1)	保存按钮：点击保存按钮，若保存成功，提示保存成功，返回上一级界面，若保存失败，提示用户保存失败的原因，停留在当前界面。
2)	右全移按钮：点击右全移按钮，将左框中的数据全部移到右框。
3)	右移按钮：从左框中选择一条数据，点击右移按钮，将左框选中的数据移到右框。
4)	左移按钮：从右框中选择一条数据，点击左移按钮，将右框选中的数据移到左框。
5)	左全移按钮：点击左全移按钮，将右框中的数据全部移到左框。
6)	重置按钮：点击重置按钮，恢复行政组织业务属性的初始状态。
7)	取消按钮：点击取消按钮，退出当前界面，返回主界面。
8)	编辑部门电子地图按钮：点击编辑部门电子地图按钮，进入“编辑部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
9)	编辑自提区域电子地图按钮：点击编辑自提区域电子地图按钮，进入“编辑自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
10)	编辑派送区域电子地图按钮：点击编辑派送区域电子地图按钮，进入“编辑派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
2.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】,【提货信息】,【外场信息】,【调度组信息】，【车队信息】，【车队组信息】，【事业部信息】。

1.5.7	界面描述-行政组织业务属性详情界面
2.	功能按钮区域
1)	修改按钮：点击修改按钮，进入修改界面，参见【图二：行政组织业务属性修改界面】。
2)	查看部门电子地图按钮：点击查看部门电子地图按钮，进入“查看部门电子地图”界面。参见【图四：编辑或查看电子地图界面】
3)	查看自提区域电子地图按钮：点击查看自提区域电子地图按钮，进入“查看自提区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
4)	查看派送区域电子地图按钮：点击查看派送区域电子地图按钮，进入“查看派送区域电子地图”界面。参见【图四：编辑或查看电子地图界面】
3.	字段输入区域
1)	参见数据元素【行政组织业务属性信息】，【提货信息】，【外场信息】，【调度组信息】，【车队信息】，【车队组信息】，【车队信息】，【部门基本信息】。

SR-1	选择省份信息后，城市的选择范围自动变成已选省份所包含的城市。选择城市信息后，区县的选择范围自动变成已选城市所包含的区县。
SR-2	只有选择了“是否营业部”，界面才能修改或者显示营业部信息。
SR-3	只有选择了“可到达”，界面才能修改或者显示提货信息，到达适用产品。
SR-4	只有选择了“是否外场”，界面才能修改或者显示外场信息。
SR-5	只有选择了“是否车队调度组”，界面才能修改或者显示调度组信息。
SR-6	只有选择了“可自提”，界面才能修改或者显示“自提区域描述”。
SR-7	只有选择了“可派送”，界面才能修改或者显示“派送区域描述”。
SR-8	只有选择了“可出发”，界面才能修改或者显示“车队”,“出发适用产品”，“所属集中开单组”。
SR-9	外场简码,外场编码均不可重复。如果录入的已存在，提示用户已存在。
SR-10	只有选择了“是否车队”，界面才能修改或者显示车队信息。
SR-11	只有选择了“是否车队组”，界面才能修改或者显示车队组信息。
SR-12	在一个事业部内，集中接送货车队编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-13	只有选择了“是否驻地部门”，界面才能修改或者显示“驻地营业部所属外场”。
SR-14	只有选择了“是否事业部”，界面才能修改或者显示“事业部信息”。
SR-15	事业部编码不能重复，当输入的编码已存在时，提示用户编码已存在。
SR-16	最大临时欠款额度的不是人工维护，是动态变化的，根据前3个月的最高收入所在等级决定。
SR-17	外场的部门面积要大于货区面积，外场的货区面积要大于货台面积。如果用户填写的值不满足此规则，则提示用户。
SR-18	当有客户在某部门开临欠时，此部门的已用临欠额度做相应的更新。



 */
package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.result.DefaultMapResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.base.util.ComnConst;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.components.dataaccess.ibatis.SqlSessionDaoSupport;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.frameworkimpl.server.util.AutoMapResultHandler;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 组织Dao
 * 
 * @date Mar 13, 2013 10:53:21 AM
 * @version 1.0
 */
public class OrgAdministrativeInfoDao extends SqlSessionDaoSupport implements IOrgAdministrativeInfoDao {
    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrgAdministrativeInfoDao.class);

    /**
     * 
     * mybatis 命名空间
     */
    private static final String NAMESPACE = ComnConst.MYBATIS_NAMESPACE_BASEINFO_PREFIX + ".orgAdministrativeInfo.";

    private static final String BANCHEBUN_NAME = "%班车部%";

    /**
     * 新增
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:39:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao#addOrgAdministrativeInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity)
     */
    @Override
    public OrgAdministrativeInfoEntity addOrgAdministrativeInfo(OrgAdministrativeInfoEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}
	
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	// CreateUser为传入的用户编码，CreateDate为当前时间
	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setModifyUser(entity.getCreateUser());
	entity.setVersionNo(now.getTime());
	
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addOrgAdministrativeInfo", entity);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过CODE 标识来删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:39:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao#deleteOrgAdministrativeInfo(java.lang.String)
     */
    @Override
    public OrgAdministrativeInfoEntity deleteOrgAdministrativeInfo(OrgAdministrativeInfoEntity entity) {
	// 请求参数合法性验证
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getCode())) {
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
	int result = getSqlSession().update(NAMESPACE + "deleteOrgAdministrativeInfo", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过CODE 标识来批量删除
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:39:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao#deleteOrgAdministrativeInfoMore(java.lang.String[], java.lang.String)
     */
    @Override
    @Deprecated
    public OrgAdministrativeInfoEntity deleteOrgAdministrativeInfoMore(String[] codes , String deleteUser) {
	// 请求合法性判断：
	if(ArrayUtils.isEmpty(codes)) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
	OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();

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
		NAMESPACE + "deleteOrgAdministrativeInfoMore", map);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 通过CODE标识更新
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:39:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao#updateOrgAdministrativeInfo(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity)
     */
    @Override
    public OrgAdministrativeInfoEntity updateOrgAdministrativeInfo(OrgAdministrativeInfoEntity entity) {
	// 请求合法性判断：
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getCode())) {
	    return entity;
	}
	
	// 更新要先删除旧的数据：
	OrgAdministrativeInfoEntity result = this.deleteOrgAdministrativeInfo(entity);
	if (result == null) {
	    String msg = "更新时，作废失败";
	    LOGGER.error(msg);
	}
	
	// 组装插入参数
	entity.setId(UUIDUtils.getUUID());
	// CreateUser为传入的用户编码，CreateDate为当前时间
//	Date now = new Date();
//	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
//	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
	entity.setCreateUser(entity.getModifyUser());
	
	// 版本号始终取当前时间
	entity.setVersionNo(System.currentTimeMillis());
	
	entity.setActive(FossConstants.ACTIVE);
	int resultNum = getSqlSession().insert(NAMESPACE + "addOrgAdministrativeInfo", entity);
	return resultNum > NumberConstants.ZERO ? entity : null;
    }



    /**
     * 以下全为查询：
     */
    
    /**
     * 通过 标识编码查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:39:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao#queryOrgAdministrativeInfoByCode(java.lang.String)
     */
	@Override
	public OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}

		// 构造查询条件：
		OrgAdministrativeInfoEntity entity = new OrgAdministrativeInfoEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setCode(code);

		@SuppressWarnings("unchecked")
		List<OrgAdministrativeInfoEntity> list = getSqlSession().selectList(NAMESPACE + "queryOrgAdministrativeInfoByCode", entity);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 根据历史时间和组织编码查询组织信息（查询历史组织信息）
	 * 
	 * 若时间为空，则只根据组织编码查询组织信息
	 * 否则将根据时间，查询在creatTime和modifyTime时间段的部门
	 * 不根据Active='Y'来查询
	 * 
	 * @author 026123-foss-lifengteng
	 * @date 2013-4-17 下午6:02:26
	 */
	@SuppressWarnings("unchecked")
	@Override
	public OrgAdministrativeInfoEntity queryOrgAdministrativeInfoByCode(String code,Date billTime) {
		//先根据部门编码进行查询
		OrgAdministrativeInfoEntity orgInfo = queryOrgAdministrativeInfoByCode(code);
		
		//判断时间是否为空
		if(null == billTime){
			return orgInfo;
		}else{
			//若查询出的对象为空，则根据时间查询历史失效组织
			if(null == orgInfo){
				// 构造查询条件
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("code", code);
				map.put("billTime", billTime);
				
				//查询数据
				List<OrgAdministrativeInfoEntity> orgList = getSqlSession().selectList(NAMESPACE + "queryOrgAdministrativeInfoByCodeAndTime", map);
				//集合非空判断
				if(CollectionUtils.isNotEmpty(orgList)){
					orgInfo = orgList.get(0);
				}
			}
		}
		return orgInfo;
	}
    
    @SuppressWarnings("unchecked")
    @Override
    public String queryOrgNameByCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	
	// 构造查询条件：
	OrgAdministrativeInfoEntity entity=new OrgAdministrativeInfoEntity();
	entity.setActive(FossConstants.ACTIVE);
	entity.setCode(code);
	
	List<String> list = getSqlSession().selectList(NAMESPACE + "queryOrgNameByCode", entity);
	if (CollectionUtils.isEmpty(list)) {
	    return null;
	}
	return list.get(NumberConstants.ZERO);
    }
    
    
    /**
     * 通过 标识编码CODE,是否有效ACTIVE精确查询
     * 
     * 两个参数都可传空，当传空时，不做为查询条件，查询时，取更新时间最近的一条
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:39:55
     * @param code 标识编码（组织编码）
     * @param active FossConstants.YES:FossConstants.NO
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoByCodeActive(
	    List<String> codes, String active) {	
	// 请求合法性判断
	if(CollectionUtils.isEmpty(codes)){
	    return null;
	}
	
	//String[] array=(String[])codes.toArray(new String[0]);
	// 构造查询条件：
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("active", active);
	map.put("codes", codes);

	List<OrgAdministrativeInfoEntity> entitys = this.getSqlSession().selectList(
		NAMESPACE + "queryOrgAdministrativeInfoByCodeActive", map);
	
	return entitys;
    }

    
    /**
     * 精确查询
     * 根据多个标识编码批量查询
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-18 下午4:1:47
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao#queryOrgAdministrativeInfoBatchBy(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoBatchByCode(
	    String[] codes) {
	// 请求参数合法性判断
	List<OrgAdministrativeInfoEntity> result = new ArrayList<OrgAdministrativeInfoEntity>();
	if (ArrayUtils.isEmpty(codes)){
	    return result;
	}
	
	// 构造查询条件：
	Map<String,Object> map = new HashMap<String , Object>();
	map.put("codes", codes);
	map.put("active", FossConstants.ACTIVE);
	
	result = getSqlSession().selectList(NAMESPACE + "queryOrgAdministrativeInfoBatchByCode", map);
	return result;
    }

    /** 
     * 精确查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:11:15
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao#queryOrgAdministrativeInfoExactByEntity(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity, int, int)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoExactByEntity(
	    OrgAdministrativeInfoEntity entity, int start, int limit) {
	OrgAdministrativeInfoEntity queryEntity = entity == null ? new OrgAdministrativeInfoEntity() : entity;

	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession()
		.selectList(NAMESPACE + "queryOrgAdministrativeInfoExactByEntity",
			queryEntity,
			rowBounds);
    }

    /**
     * 精确查询-查询总条数，用于分页
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不���空或者空白，则设置为查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-10-19 上午11:09:53
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao#queryOrgAdministrativeInfoExactByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity)
     */
    @Override
    public long queryOrgAdministrativeInfoExactByEntityCount(OrgAdministrativeInfoEntity entity) {
	OrgAdministrativeInfoEntity queryEntity;
	if (null == entity) {
	    queryEntity = new OrgAdministrativeInfoEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(
		NAMESPACE + "queryOrgAdministrativeInfoExactByEntityCount",
		queryEntity);
    }

    /**
     * 模糊查询
     * 动态的查询条件。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为模糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:39:55
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao#queryOrgAdministrativeInfoMore(java.lang.String[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoByEntity(
	    OrgAdministrativeInfoEntity entity, int start, int limit) {
	OrgAdministrativeInfoEntity queryEntity;
	if (null == entity) {
	    queryEntity = new OrgAdministrativeInfoEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	RowBounds rowBounds = new RowBounds(start, limit);
	return getSqlSession().selectList(NAMESPACE + "queryOrgAdministrativeInfoByEntity", queryEntity,
			rowBounds);
    }

    /**
     * 模糊查询
     * 动态的查询条件-查询总条数。 如果传入的对象为空，传入一个对象，可查出所有的数据，如果传入的对象的属性不为空或者空白，则设置为���糊查询的查询条件
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-2 下午2:39:55
     * @see com.deppon.foss.module.base.baseinfo.server.dao.IOrgAdministrativeInfoDao#queryOrgAdministrativeInfoByEntityCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity)
     */
    @Override
    public long queryOrgAdministrativeInfoByEntityCount(OrgAdministrativeInfoEntity entity) {
	OrgAdministrativeInfoEntity queryEntity;
	if (null == entity) {
	    queryEntity = new OrgAdministrativeInfoEntity();
	}else{
	    queryEntity = entity;
	}
	queryEntity.setActive(FossConstants.ACTIVE);
	return (Long)getSqlSession().selectOne(NAMESPACE + "queryOrgAdministrativeInfoByEntityCount", queryEntity);
    }

	
    /**
     * 根据entity精确查询
     * entity里面根据表结构，要动态（可不传入）传入MODIFY_TIME,员工编号，部门编号,
     * 
     * @author 087584-foss-lijun
     * @date 2012-11-7 下午6:45:33
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoForDownload(OrgAdministrativeInfoEntity entity){
	OrgAdministrativeInfoEntity queryEntity;
	if (null == entity) {
	    queryEntity = new OrgAdministrativeInfoEntity();
	}else{
	    queryEntity = entity;
	}
	return (List<OrgAdministrativeInfoEntity>) getSqlSession().selectList(NAMESPACE + "queryOrgAdministrativeInfoForDownload", queryEntity);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoByDistrictCode(
	    String districtCode, Date billDate) {
	Map  map = new HashMap ();
	map.put("districtCode", districtCode);
	map.put("billDate", billDate); 
	return (List<OrgAdministrativeInfoEntity>) getSqlSession().selectList(NAMESPACE + "queryOrgAdministrativeInfoByDistrictCode", map);
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public List<OrgAdministrativeInfoEntity> queryOrgAdministrativeInfoForCache(
	    String orgCode, Date billDate) {
	Map  map = new HashMap ();
	map.put("code", orgCode);
	map.put("billDate", billDate); 
	return (List<OrgAdministrativeInfoEntity>) getSqlSession().selectList(NAMESPACE + "queryOrgAdministrativeInfoByDistrictCode", map);
    }

    @Override
    public OrgAdministrativeInfoEntity queryLastestOrgAdministrativeInfoByCode(String code) {
	if (StringUtils.isBlank(code)) {
	    return null;
	}
	return (OrgAdministrativeInfoEntity) getSqlSession().selectOne(NAMESPACE + "queryLastestOrgAdministrativeInfoByCode", code);
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public List<OrgAdministrativeInfoEntity> searchShuttleGroup(String transDepartmentCode) {
	Map<String, String> map = new HashMap<String, String>();
	map.put("parentOrgCode", transDepartmentCode);
	map.put("active", FossConstants.ACTIVE);
	map.put("transTeam", FossConstants.ACTIVE);
	map.put("name", BANCHEBUN_NAME);
	return (List<OrgAdministrativeInfoEntity>) getSqlSession().selectList(NAMESPACE + "searchShuttleGroup", map);
    }

	/**
	 * 下载组织信息
	 */ @SuppressWarnings("unchecked")
	public Object queryOrgAdministrativeInfoForDownloadByPage(
			OrgAdministrativeInfoEntity entity, int start, int limited) {
		OrgAdministrativeInfoEntity queryEntity;
		RowBounds rowBounds = new RowBounds(start, limited);
		if (null == entity) {
		    queryEntity = new OrgAdministrativeInfoEntity();
		}else{
		    queryEntity = entity;
		}
		return (List<OrgAdministrativeInfoEntity>) getSqlSession()
				.selectList(NAMESPACE + "queryOrgAdministrativeInfoForDownload", 
						queryEntity, rowBounds);
	  
	}

	/** 
	 * 通过 组织标杆编码查询该组织下的所有全部组织信息
	 * @author foss-qiaolifeng
	 * @date 2013-7-17 下午6:40:42
	 * @param parentOrgUnicode
	 * @param active
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao#queryAllOrgAdministrativeInfoByParentOrgUnicodeCode(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<OrgAdministrativeInfoEntity> queryAllOrgAdministrativeInfoByParentOrgUnicodeCode(
			String parentOrgUnicode, String active) {
		
		//参数map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("active", active);
		map.put("parentOrgUnicode", parentOrgUnicode);
		
		//查询
		return ((List<OrgAdministrativeInfoEntity>) getSqlSession().selectList(NAMESPACE + "queryAllOrgAdministrativeInfoByParentOrgUnicodeCode", map));
	}
	
	/**
     * <p>根据上级组织标杆编码parentOrgUnifiedCode查询上级组织名称</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-7-29 上午11:33:31
     * @param parentOrgUnifiedCode
     * @param lastupdatetime
     * @return
     * @see
     */
	@Override
	public String queryParentOrgNameByParentOrgUnifiedCode(
			String parentOrgUnicode) {
		return (String)this.getSqlSession().selectOne(NAMESPACE + "queryParentOrgName",
				parentOrgUnicode);
	}
	
	 /**
     * <p>根据组织标杆编码orgUnifiedCode查询组织编码</p> 
     * @author 132599-foss-shenweihua
     * @date 2013-12-10 下午8:33:31
     * @param parentOrgUnifiedCode
     * @param lastupdatetime
     * @return
     * @see
     */
	@Override
	public String queryOrgCodeByOrgUnifiedCode(String orgUnicode) {
		return (String)this.getSqlSession().selectOne(NAMESPACE + "queryOrgCode",
				orgUnicode);
	}
	
	/**
     * 根据标杆编码获取部门名称
     * @remark 
     * @author WangQianJin
     * @date 2014-3-12 下午4:04:56
     */
	@Override
	public String queryDeptNameByUnifiedCode(String unifiedCode){
		return (String)this.getSqlSession().selectOne(NAMESPACE + "queryDeptNameByUnifiedCode",unifiedCode);				
	}

	/**
	 * 根据部门编码向下查找其下的所有子部门并且也可以判断当前部门是否存在其中
	 *
	 * auther:wangpeng_078816
	 * date:2014-4-19
	 *
	 */
	@SuppressWarnings("unchecked")
	@Override
	public long queryDeptInfoByCode(List<String> codes,
			String code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codes", codes);
		map.put("code", code);
		return (Long)getSqlSession().selectOne(NAMESPACE + "queryDeptInfoByCode", map);
	}
	
	/**
	 * 根据部门编码查询标杆编码
	 */
	@SuppressWarnings("unchecked")
    @Override
    public String queryUnifiedCodeByCode(String code) {
		if (StringUtils.isBlank(code)) {
		    return null;
		}		
		OrgAdministrativeInfoEntity entity=new OrgAdministrativeInfoEntity();
		entity.setActive(FossConstants.ACTIVE);
		entity.setCode(code);
		List<String> list = getSqlSession().selectList(NAMESPACE + "queryUnifiedCodeByCode", entity);
		if (CollectionUtils.isEmpty(list)) {
		    return null;
		}
		return list.get(0);
    }
	

	/**
     * 配合主数据项目接口新增组织信息
     * @author 187862-dujunhui
     * @date 2015-4-16 上午9:26:46
     */
    @Override
    public OrgAdministrativeInfoEntity addOrgAdministrativeInfoOfUU(OrgAdministrativeInfoEntity entity) {
	// 请求合法性验证：
	if (null == entity) {
	    return entity;
	}
	
	Date now = new Date();
	entity.setId(UUIDUtils.getUUID());
	// CreateUser为传入的用户编码，CreateDate为当前时间
//	entity.setCreateDate(now);
	// ModifyDate为2999年，为一个常量
//	entity.setModifyDate(new Date(NumberConstants.ENDTIME));
//	entity.setModifyUser(entity.getCreateUser());
	entity.setVersionNo(now.getTime());
	
	entity.setActive(FossConstants.ACTIVE);
	int result = getSqlSession().insert(NAMESPACE + "addOrgAdministrativeInfoOfUU", entity);
	return result > NumberConstants.ZERO ? entity : null;
    }

    /**
     * 配合主数据项目接口更新组织信息
     * @author 187862-dujunhui
     * @date 2015-4-16 下午3:19:32
     */
	@Override
	public OrgAdministrativeInfoEntity updateOrgAdministrativeInfoOfUU(
			OrgAdministrativeInfoEntity entity) {
		// 请求合法性判断：
		if (null == entity) {
		    return entity;
		}
		if (StringUtils.isBlank(entity.getCode())) {
		    return entity;
		}
		
		// 更新要先删除旧的数据：
		OrgAdministrativeInfoEntity result = this.deleteOrgAdministrativeInfo(entity);
		if (result == null) {
		    String msg = "更新时，作废失败";
		    LOGGER.error(msg);
		}
		
		// 组装插入参数
		entity.setId(UUIDUtils.getUUID());
		// CreateUser为传入的用户编码，CreateDate为当前时间
//		Date now = new Date();
//		entity.setCreateDate(now);
//		// ModifyDate为2999年，为一个常量
//		entity.setModifyDate(new Date(NumberConstants.ENDTIME));
//		entity.setCreateUser(entity.getModifyUser());
		
		// 版本号始终取当前时间
		entity.setVersionNo(System.currentTimeMillis());
		
		entity.setActive(FossConstants.ACTIVE);
		int resultNum = getSqlSession().insert(NAMESPACE + "addOrgAdministrativeInfo", entity);
		return resultNum > NumberConstants.ZERO ? entity : null;
	}
	
	/**
     * 配合主数据项目接口作废组织信息
     * @author 187862-dujunhui
     * @date 2015-4-16 下午3:51:19
     */
    @Override
    public OrgAdministrativeInfoEntity deleteOrgAdministrativeInfoOfUU(OrgAdministrativeInfoEntity entity) {
	// 请求参数合法性验证
	if (null == entity) {
	    return entity;
	}
	if (StringUtils.isBlank(entity.getCode())) {
	    return null;
	}
	
	// 处理删除时要更新的数据
	Date now = new Date();
//	entity.setModifyDate(now);
	entity.setVersionNo(now.getTime());
	// entity应包含modifyUser,因此不用处理
	entity.setActive(FossConstants.INACTIVE);
	
	Map<String, Object> map=new HashMap<String, Object>();
	map.put("entity", entity);
	// 只删除active为有效的：
	map.put("conditionActive", FossConstants.ACTIVE);
	int result = getSqlSession().update(NAMESPACE + "deleteOrgAdministrativeInfo", map);
	return result > NumberConstants.ZERO ? entity : null;
    }
    /**
     * 
     * <p>配合主数据项目，根据UUMS至FOSS的中间表关联FOSS组织表查询组织信息</p> 
     * @author 187862-dujunhui 
     * @date 2015-4-28 上午10:22:01
     * @param unifiedCode
     * @return 
     * @see com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgAdministrativeInfoDao#queryDeptNameByUnifiedCode(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
	public OrgAdministrativeInfoEntity queryOrgAdministrationInfoByMidTable(OrgAdministrativeInfoEntity entity){
		List<OrgAdministrativeInfoEntity> list=this.getSqlSession().selectList(NAMESPACE + "queryOrgAdministrationInfoByMidTable",entity);	
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

    /**
     * 根据部门名称查询其所在城市名称
     * @author 187862-dujunhui
	 * @date 2015-9-9 下午4:19:54
     * @param orgName
     * @return
     */
	@SuppressWarnings("unchecked")
	@Override
	public String queryCityNameByOrgName(String orgName) {
		List<String> list=this.getSqlSession().selectList(NAMESPACE + "queryCityNameByOrgName", orgName);
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}

	@Override
	public Map<String, String> queryOrgNameMapsByCodes(List<String> orgs) {
		if(CollectionUtils.isEmpty(orgs)){
			return new HashMap<String, String>();
		}
		Map<String, String> retMap = new HashMap<String, String>();
		String[] keyValCol ={"code", "name"};
		DefaultMapResultHandler resultHandle = new AutoMapResultHandler(keyValCol,retMap);
		this.getSqlSession().select(NAMESPACE + "queryOrgNameMapsByCodes", orgs, resultHandle);
		
		return retMap;
	}

	@Override
	public List<OrgAdministrativeInfoEntity> queryCurrentUserChangeDeptsByDeptNameLike(
			String empCode, String deptName, int start, int limit) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("empCode", empCode);
		map.put("deptName", deptName);
		return this.getSqlSession().selectList(NAMESPACE + "queryCurrentUserChangeDeptsByDeptNameLike", map,new RowBounds(start,limit));
	}

	@Override
	public Long queryCurrentUserChangeDeptsCountsByDeptNameLike(String empCode,
			String deptName) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("empCode", empCode);
		map.put("deptName", deptName);
		return (Long) this.getSqlSession().selectOne(NAMESPACE + "queryCurrentUserChangeDeptsCountsByDeptNameLike", map);
	}

	@Override
	public OrgAdministrativeInfoEntity queryOrgAdministrationInfoByName(String name) {
		List<OrgAdministrativeInfoEntity> list=this.getSqlSession().selectList(NAMESPACE + "queryOrgAdministrationInfoByName",name);	
		return CollectionUtils.isEmpty(list)?null:list.get(0);
	}
}
