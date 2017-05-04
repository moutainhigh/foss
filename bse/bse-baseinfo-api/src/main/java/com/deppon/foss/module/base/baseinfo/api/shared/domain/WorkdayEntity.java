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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/shared/domain/WorkdayEntity.java
 * 
 * FILE NAME        	: WorkdayEntity.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.shared.domain;

import com.deppon.foss.framework.entity.BaseEntity;

/**
 * 工作日
 * 
 * @author 087584-foss-lijun
 * @date 2012-10-20 下午2:35:52
 */
public class WorkdayEntity  extends BaseEntity {
	
	/**
	 * 序列ID
	 */
    private static final long serialVersionUID = -3967231351676414921L;

    /**
    *虚拟编码
    */	
    private String virtualCode;
    
    /**
     * 工作月份,唯一标识,例：201203
     */
    private String workMonth;
    
    /**
     * 扩展信息EXTEND_INFO
     */
    private String extendInfo;
    
    /**
     * 备注信息REMARK_INFO 
     */
    private String remarkInfo;

    /**
    *一号
    */	
    private String day1;

    /**
    *二号
    */	
    private String day2;

    /**
    *三号
    */	
    private String day3;

    /**
    *四号
    */	
    private String day4;

    /**
    *五号
    */	
    private String day5;

    /**
    *六号
    */	
    private String day6;

    /**
    *七号
    */	
    private String day7;

    /**
    *八号
    */	
    private String day8;

    /**
    *九号
    */	
    private String day9;

    /**
    *十号
    */	
    private String day10;

    /**
    *十一号
    */	
    private String day11;

    /**
    *十二号
    */	
    private String day12;

    /**
    *十三号
    */	
    private String day13;

    /**
    *十四号
    */	
    private String day14;

    /**
    *十五号
    */	
    private String day15;

    /**
    *十六号
    */	
    private String day16;

    /**
    *十七号
    */	
    private String day17;

    /**
    *十八号
    */	
    private String day18;

    /**
    *十九号
    */	
    private String day19;

    /**
    *二十号
    */	
    private String day20;

    /**
    *二十一号
    */	
    private String day21;

    /**
    *二十二号
    */	
    private String day22;

    /**
    *二十三号
    */	
    private String day23;

    /**
    *二十四号
    */	
    private String day24;

    /**
    *二十五号
    */	
    private String day25;

    /**
    *二十六号
    */	
    private String day26;

    /**
    *二十七号
    */	
    private String day27;

    /**
    *二十八号
    */	
    private String day28;

    /**
    *二十九号
    */	
    private String day29;

    /**
    *三十号
    */	
    private String day30;

    /**
    *三十一号
    */	
    private String day31;

    /**
    *是否启用
    */	
    private String active;

	/**
	 * @return virtualCode
	 */
	public String getVirtualCode() {
		return virtualCode;
	}

	/**
	 * @param  virtualCode  
	 */
	public void setVirtualCode(String virtualCode) {
		this.virtualCode = virtualCode;
	}

	/**
	 * @return workMonth
	 */
	public String getWorkMonth() {
		return workMonth;
	}

	/**
	 * @param  workMonth  
	 */
	public void setWorkMonth(String workMonth) {
		this.workMonth = workMonth;
	}

	/**
	 * @return extendInfo
	 */
	public String getExtendInfo() {
		return extendInfo;
	}

	/**
	 * @param  extendInfo  
	 */
	public void setExtendInfo(String extendInfo) {
		this.extendInfo = extendInfo;
	}

	/**
	 * @return remarkInfo
	 */
	public String getRemarkInfo() {
		return remarkInfo;
	}

	/**
	 * @param  remarkInfo  
	 */
	public void setRemarkInfo(String remarkInfo) {
		this.remarkInfo = remarkInfo;
	}

	/**
	 * @return day1
	 */
	public String getDay1() {
		return day1;
	}

	/**
	 * @param  day1  
	 */
	public void setDay1(String day1) {
		this.day1 = day1;
	}

	/**
	 * @return day2
	 */
	public String getDay2() {
		return day2;
	}

	/**
	 * @param  day2  
	 */
	public void setDay2(String day2) {
		this.day2 = day2;
	}

	/**
	 * @return day3
	 */
	public String getDay3() {
		return day3;
	}

	/**
	 * @param  day3  
	 */
	public void setDay3(String day3) {
		this.day3 = day3;
	}

	/**
	 * @return day4
	 */
	public String getDay4() {
		return day4;
	}

	/**
	 * @param  day4  
	 */
	public void setDay4(String day4) {
		this.day4 = day4;
	}

	/**
	 * @return day5
	 */
	public String getDay5() {
		return day5;
	}

	/**
	 * @param  day5  
	 */
	public void setDay5(String day5) {
		this.day5 = day5;
	}

	/**
	 * @return day6
	 */
	public String getDay6() {
		return day6;
	}

	/**
	 * @param  day6  
	 */
	public void setDay6(String day6) {
		this.day6 = day6;
	}

	/**
	 * @return day7
	 */
	public String getDay7() {
		return day7;
	}

	/**
	 * @param  day7  
	 */
	public void setDay7(String day7) {
		this.day7 = day7;
	}

	/**
	 * @return day8
	 */
	public String getDay8() {
		return day8;
	}

	/**
	 * @param  day8  
	 */
	public void setDay8(String day8) {
		this.day8 = day8;
	}

	/**
	 * @return day9
	 */
	public String getDay9() {
		return day9;
	}

	/**
	 * @param  day9  
	 */
	public void setDay9(String day9) {
		this.day9 = day9;
	}

	/**
	 * @return day10
	 */
	public String getDay10() {
		return day10;
	}

	/**
	 * @param  day10  
	 */
	public void setDay10(String day10) {
		this.day10 = day10;
	}

	/**
	 * @return day11
	 */
	public String getDay11() {
		return day11;
	}

	/**
	 * @param  day11  
	 */
	public void setDay11(String day11) {
		this.day11 = day11;
	}

	/**
	 * @return day12
	 */
	public String getDay12() {
		return day12;
	}

	/**
	 * @param  day12  
	 */
	public void setDay12(String day12) {
		this.day12 = day12;
	}

	/**
	 * @return day13
	 */
	public String getDay13() {
		return day13;
	}

	/**
	 * @param  day13  
	 */
	public void setDay13(String day13) {
		this.day13 = day13;
	}

	/**
	 * @return day14
	 */
	public String getDay14() {
		return day14;
	}

	/**
	 * @param  day14  
	 */
	public void setDay14(String day14) {
		this.day14 = day14;
	}

	/**
	 * @return day15
	 */
	public String getDay15() {
		return day15;
	}

	/**
	 * @param  day15  
	 */
	public void setDay15(String day15) {
		this.day15 = day15;
	}

	/**
	 * @return day16
	 */
	public String getDay16() {
		return day16;
	}

	/**
	 * @param  day16  
	 */
	public void setDay16(String day16) {
		this.day16 = day16;
	}

	/**
	 * @return day17
	 */
	public String getDay17() {
		return day17;
	}

	/**
	 * @param  day17  
	 */
	public void setDay17(String day17) {
		this.day17 = day17;
	}

	/**
	 * @return day18
	 */
	public String getDay18() {
		return day18;
	}

	/**
	 * @param  day18  
	 */
	public void setDay18(String day18) {
		this.day18 = day18;
	}

	/**
	 * @return day19
	 */
	public String getDay19() {
		return day19;
	}

	/**
	 * @param  day19  
	 */
	public void setDay19(String day19) {
		this.day19 = day19;
	}

	/**
	 * @return day20
	 */
	public String getDay20() {
		return day20;
	}

	/**
	 * @param  day20  
	 */
	public void setDay20(String day20) {
		this.day20 = day20;
	}

	/**
	 * @return day21
	 */
	public String getDay21() {
		return day21;
	}

	/**
	 * @param  day21  
	 */
	public void setDay21(String day21) {
		this.day21 = day21;
	}

	/**
	 * @return day22
	 */
	public String getDay22() {
		return day22;
	}

	/**
	 * @param  day22  
	 */
	public void setDay22(String day22) {
		this.day22 = day22;
	}

	/**
	 * @return day23
	 */
	public String getDay23() {
		return day23;
	}

	/**
	 * @param  day23  
	 */
	public void setDay23(String day23) {
		this.day23 = day23;
	}

	/**
	 * @return day24
	 */
	public String getDay24() {
		return day24;
	}

	/**
	 * @param  day24  
	 */
	public void setDay24(String day24) {
		this.day24 = day24;
	}

	/**
	 * @return day25
	 */
	public String getDay25() {
		return day25;
	}

	/**
	 * @param  day25  
	 */
	public void setDay25(String day25) {
		this.day25 = day25;
	}

	/**
	 * @return day26
	 */
	public String getDay26() {
		return day26;
	}

	/**
	 * @param  day26  
	 */
	public void setDay26(String day26) {
		this.day26 = day26;
	}

	/**
	 * @return day27
	 */
	public String getDay27() {
		return day27;
	}

	/**
	 * @param  day27  
	 */
	public void setDay27(String day27) {
		this.day27 = day27;
	}

	/**
	 * @return day28
	 */
	public String getDay28() {
		return day28;
	}

	/**
	 * @param  day28  
	 */
	public void setDay28(String day28) {
		this.day28 = day28;
	}

	/**
	 * @return day29
	 */
	public String getDay29() {
		return day29;
	}

	/**
	 * @param  day29  
	 */
	public void setDay29(String day29) {
		this.day29 = day29;
	}

	/**
	 * @return day30
	 */
	public String getDay30() {
		return day30;
	}

	/**
	 * @param  day30  
	 */
	public void setDay30(String day30) {
		this.day30 = day30;
	}

	/**
	 * @return day31
	 */
	public String getDay31() {
		return day31;
	}

	/**
	 * @param  day31  
	 */
	public void setDay31(String day31) {
		this.day31 = day31;
	}

	/**
	 * @return active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param  active  
	 */
	public void setActive(String active) {
		this.active = active;
	}
}
