package com.deppon.foss.module.pickup.pricing.api.server.util;

import com.deppon.foss.framework.shared.util.string.StringUtil;

/**
 * 处理省份名称
 * @author xmm
 * 2014-02-19
 */
public class ProvinceUtil {
	private ProvinceUtil(){}
	public static String getSimplenameByName(String name){
		if(StringUtil.isEmpty(name)){
			return null;
		}
		String simplename = null;
		int size = name.length();
		if(name.endsWith("省")||name.endsWith("市")){//以“省”或者“市”结尾
			simplename= name.substring(0, size-1);
		}else if(name.startsWith("香港")){
			simplename="香港";
		}else if(name.startsWith("新疆")){
			simplename="新疆";
		}else if(name.startsWith("内蒙古")){
			simplename="内蒙古";
		}else if(name.startsWith("西藏")){
			simplename="西藏";
		}else if(name.startsWith("澳门")){
			simplename="澳门";
		}else if(name.startsWith("广西")){
			simplename="广西";
		}else if(name.startsWith("宁夏")){
			simplename="宁夏";
		}else {
			simplename = name;
		}
		return simplename;
	}
}
//江西省	江西
//台湾	台湾
//天津	天津
//北京	北京
//上海	上海
//香港特别行政区	香港
//重庆	重庆
//海南省	海南
//河南省	河南
//四川省	四川
//新疆维吾尔自治区	新疆
//内蒙古自治区	内蒙古
//西藏自治区	西藏
//安徽省	安徽
//贵州省	贵州
//辽宁省	辽宁
//澳门特别行政区	澳门
//吉林省	吉林
//甘肃省	甘肃
//广西壮族自治区	广西
//河北省	河北
//山东省	山东
//湖南省	湖南
//山西省	山西
//江苏省	江苏
//广东省	广东
//黑龙江省	黑龙江
//湖北省	湖北
//福建省	福建
//青海省	青海
//浙江省	浙江
//陕西省	陕西
//云南省	云南
//宁夏回族自治区	宁夏
