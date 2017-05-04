package com.deppon.foss.module.pickup.waybill.server.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ObtainPictureServlet extends HttpServlet {

	private static final long serialVersionUID = 2072768379502827325L;
	
	private static final int NUMBER_1024 = 1024;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = null;
		FileInputStream inStream = null;
		try {
			String filePath = request.getParameter("filePath");
			out = response.getOutputStream();
			//ByteArrayOutputStream os = new ByteArrayOutputStream();
			File picFile = new File(filePath);
			if(!picFile.exists()){
				out.write(null);
			}else{
				/*BufferedImage image = ImageIO.read(picFile);
				ImageIO.write(image, "png", os);  */
				
				// 得到一个输入流
				inStream = new FileInputStream(picFile);
				
				byte[] buffer = new byte[NUMBER_1024];
				int length = -1;
				// 读取文件放于数组中
				while ((length = inStream.read(buffer)) != -1) {
					out.write(buffer);
				}
				//out.write(loadFile2Bytes(filePath));
			}
			out.flush();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				out.close();
			}
			if(inStream != null){
				inStream.close();
			}
			//os.close();
		}
	}

/*	public static byte[] loadFile2Bytes(String fileName) {
		try {
		
		File file = new File(fileName);
		// 得到一个输入流
		FileInputStream inStream = new FileInputStream(file);
		// 得到一个字节流
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		long l = file.length();
		// 通过查看FILE的长度不初始化BYTE数组
		byte[] buffer = new byte[1024];
		int length = -1;
		// 读取文件放于数组中
		while ((length = inStream.read(buffer)) != -1) {
		stream.write(buffer, 0, length);
		}
		stream.close();
		inStream.close();
		return stream.toByteArray();
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		return null;
		} catch (IOException e) {
		e.printStackTrace();
		return null;
		}
		}
*/
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
