package com.deppon.foss.print.labelprint.test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.ImageIcon;

public class TestZebraPrintImage {

	public static void main(String[] args) {

		try {
			StringBuffer sb = new StringBuffer();

			String imgpath = "D:\\deppon\\FOSS 打印开发方案\\斑马驱动\\国航.bmp";
			File file = new File(imgpath);

			sb.append("N\n");
			//sb.append("GK\"AALOGO\"\n");
			//sb.append("GK\"AALOGO\"\n");
			//sb.append("GM\"AALOGO\"" + file.length() + "\n");
			//sb.append(bytesToHexString(getBytesFromFile(file))+"\n");
			//sb.append("GG\"AALOGO\"\n");
			ImageIcon img = new ImageIcon(imgpath);
			int width = img.getIconWidth();
			int height = img.getIconHeight();
			
			//JPEGImageDecoder _coder = JPEGCodec.createJPEGDecoder(new FileInputStream(file));
			BufferedImage _BufferedImage = ImageIO.read(file);
			
			//ByteArrayOutputStream out = new  ByteArrayOutputStream();
			/*
			for (int i = 0; i < 50; i++) {
				for (int j = 0; j < 50; j++) {
					out.write(_BufferedImage.getRGB(i, j));
				}
			}*/
			//ImageIO.write(_BufferedImage, "jpg", out);
			byte[] _byte = ((DataBufferByte) _BufferedImage.getData().getDataBuffer()).getData(); 
			sb.append("GW30,30,"+_byte.length+","+_BufferedImage.getHeight()+","+new String(_byte)+"\n");
			sb.append("P1\n");
			//out.close();

			//System.out.print(sb.toString());
			
			PrintService service = null;
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
			PrintService[] pss = PrintServiceLookup.lookupPrintServices(flavor,
					pras);
			for (int i = 0; i < pss.length; i++) {
				String name = pss[i].getName();
				if (name.equals("ZDesigner 888-TT")) {
					service = pss[i];
					break;
				}
			}
			if (service != null) {

				DocPrintJob job = service.createPrintJob();
				java.io.ByteArrayInputStream str = new java.io.ByteArrayInputStream(
						sb.toString().getBytes());
				Doc doc = new SimpleDoc(str, flavor, null);
				job.print(doc, pras);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    return stringBuilder.toString();  
	}  
	
	public static byte[] getBytesFromFile( File file ) throws IOException {  
        
		FileImageInputStream is = new FileImageInputStream(file);  
      
        //long length = file.length();  
      
        ByteArrayOutputStream out = new  ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int pos = 0;
        while( (pos=is.read(buffer))!=-1){
        	out.write(buffer, 0, pos);
        }
        is.close();  
        return out.toByteArray();  
    }  
}
