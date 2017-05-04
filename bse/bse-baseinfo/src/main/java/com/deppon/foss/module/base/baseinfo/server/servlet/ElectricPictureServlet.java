package com.deppon.foss.module.base.baseinfo.server.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deppon.foss.base.util.define.NumberConstants;

public class ElectricPictureServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6740443948138018293L;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = null;
		FileInputStream inStream = null;
		try {
			String filePath = request.getParameter("imageUrl");
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
				
				byte[] buffer = new byte[NumberConstants.NUMBER_1024];
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
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
}
