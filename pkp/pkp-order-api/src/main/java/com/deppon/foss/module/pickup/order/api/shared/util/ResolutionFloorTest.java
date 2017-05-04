/**
 * @remark 
 * @author YANGBIN
 * @date 2014-4-1 下午4:41:47
 */
package com.deppon.foss.module.pickup.order.api.shared.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
/**
 * @ClassName: ResolutionFloorTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author YANGBIN
 * @date 2014-4-1 下午4:41:47
 * 
 */
public class ResolutionFloorTest {
	/**
	 * @Title: main
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param args 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void main(String[] args) {
		// String str = "上海市青浦区明珠路号C区楼层1210室";
		// Integer floorNum = NumberResolutionFloor.getFloorNum(str);
		// System.out.println(floorNum);
		String pattern = "yyyy-MM-dd HH:mm:ss";
//		String patternDay = "yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar calendar = Calendar.getInstance();
//		calendar.add(Calendar.DAY_OF_MONTH, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date startTime = calendar.getTime();
		String startTimeStr = sdf.format(startTime);
//
		Calendar calendar2 = Calendar.getInstance();
//		calendar2.add(Calendar.DAY_OF_MONTH, 1);
		calendar2.set(Calendar.HOUR_OF_DAY, SettlementReportNumber.TWENTY_THREE);
		calendar2.set(Calendar.MINUTE, SettlementReportNumber.FIFTY_NINE);
		calendar2.set(Calendar.SECOND, SettlementReportNumber.FIFTY_NINE);
		Date endTime = calendar2.getTime();
		String endTimeStr = sdf.format(endTime);
//		
//		c.setTime(new Date());
//		c.set(java.util.Calendar.HOUR_OF_DAY, 0);
//		c.set(java.util.Calendar.MINUTE, 0);
//		c.set(java.util.Calendar.SECOND, 0);
		
		// System.out.println(calendar2.getTimeZone());
		// System.out.println(calendar2.getTimeInMillis());
		//
		 System.out.println(startTimeStr);
		 System.out.println(endTimeStr);
//		for (int i = 0; i < 4; i++) {
//			System.out.println(createNumber());
//			// int row = (int) (Math.random()*10000);
//			// System.out.println(row);
//		}
		 
		 
	}

	private static String createNumber() {
		StringBuffer sff = new StringBuffer();
		sff.append("W");
		sff.append(random(SettlementReportNumber.SIX));
		return sff.toString();
	}

	public static String random(int n) {
        if (n < 1 || n > SettlementReportNumber.TEN) {
            throw new IllegalArgumentException("cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1) {
            return String.valueOf(ran.nextInt(SettlementReportNumber.TEN));
        }
        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            while(true) {
                int k = ran.nextInt(SettlementReportNumber.TEN);
                if( (bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char)(k + '0');
                    break;
                }
            }
        }
        return new String(chs);
    }
}
