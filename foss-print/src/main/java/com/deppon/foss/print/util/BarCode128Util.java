package com.deppon.foss.print.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.krysalis.barcode4j.HumanReadablePlacement;
import org.krysalis.barcode4j.impl.code128.Code128Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

public class BarCode128Util {

	
	/**
	 * <a3 fontSize="4" fontName="黑体" hrp="0" barHeight="23" orientation="0" antiAlias="false" 
	 * quietZone="false" dpi="200" type="CODE128"/>
	 * @param code
	 * @param hasNumber
	 * @return
	 * @throws Exception
	 */
	public static InputStream newBarCode128(String code,boolean hasNumber) throws Exception {
		
		Code128Bean bean = new Code128Bean();
		int dpi = 1200;
		//bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
		bean.setModuleWidth(UnitConv.in2mm(1.66f / dpi));
		bean.doQuietZone(false);
		bean.setBarHeight(1.05);
		// set font 
		if(hasNumber){
			bean.setMsgPosition(HumanReadablePlacement.HRP_BOTTOM);
			bean.setFontName("黑体");
			bean.setFontSize(2);
		}
		else {
			bean.setMsgPosition(HumanReadablePlacement.HRP_NONE);
		}
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BitmapCanvasProvider canvas = new BitmapCanvasProvider(output,"image/jpeg", dpi, BufferedImage.TYPE_BYTE_BINARY,false, 0);
		bean.generateBarcode(canvas, code);
		canvas.finish();
		ByteArrayInputStream is = new ByteArrayInputStream(output.toByteArray());
		output.close();
		return is;
	}
	
	public static void main(String[] args) {
		try {
			FileOutputStream fo = new FileOutputStream("d:\\1204.jpg");

			InputStream in = BarCode128Util.newBarCode128("123456789001", false);
			byte[] buffer = new byte[1024];
			int pos = 0;
			while ((pos = in.read(buffer)) != -1) {
				fo.write(buffer, 0, pos);
			}
			fo.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
