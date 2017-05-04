package com.deppon.foss.print.jasper;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.Renderable;
import net.sf.jasperreports.engine.RenderableUtil;
import net.sf.jasperreports.engine.type.ImageTypeEnum;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

/**
 * 
 * @author niujian
 *
 */
public class ImageServlet extends BaseHttpServlet
{
	private static final long serialVersionUID = JRConstants.SERIAL_VERSION_UID;

	public static final String IMAGE_NAME_REQUEST_PARAMETER = "image";

	public void service(
		HttpServletRequest request,
		HttpServletResponse response
		) throws IOException, ServletException
	{
		byte[] imageData = null;
		String imageMimeType = null;
		
		String cachejpid = request.getParameter(FossPrintConst.KEY_JASPER_PRINT_CACHE_ID);
		String prtkey = request.getParameter(FossPrintConst.KEY_JAPSER_PRINT_KEY);

		String imageName = request.getParameter(IMAGE_NAME_REQUEST_PARAMETER);
		if ("px".equals(imageName))
		{
			try
			{
				Renderable pxRenderer = 
					RenderableUtil.getInstance(getJasperReportsContext()).getRenderable("net/sf/jasperreports/engine/images/pixel.GIF");
				imageData = pxRenderer.getImageData(getJasperReportsContext());
				imageMimeType = ImageTypeEnum.GIF.getMimeType();
			}
			catch (JRException e)
			{
				throw new ServletException(e);
			}
		}
		else
		{
			// jrxml images 
			//JasperPrintCacher cacher = JasperPrintCacher.getInstance();
			//String imgkey_imageMimeType = prtkey +"$" +imageName+"$imageMimeType";
			//String imgkey_imageData = prtkey +"$" +imageName+"$imageData";
			//imageMimeType = (String)cacher.get(cachejpid,imgkey_imageMimeType);
			//imageData = (byte[])cacher.get(cachejpid,imgkey_imageData);
			
			/*
			JasperPrintCacher cacher = JasperPrintCacher.getInstance();
			JasperPrint jp = (JasperPrint)cacher.get(cachejpid,prtkey);
				
			if (jp != null)
			{
					//throw new ServletException("No JasperPrint found from jp cacher, by id:"+cachejpid);
					List<JasperPrint> jasperPrintList  = new ArrayList<JasperPrint>();
					jasperPrintList.add(jp);
					JRPrintImage image = JRHtmlExporter.getImage(jasperPrintList, imageName);
					
					Renderable renderer = image.getRenderable();
					if (renderer.getTypeValue() == RenderableTypeEnum.SVG)
					{
						renderer = 
							new JRWrappingSvgRenderer(
								renderer, 
								new Dimension(image.getWidth(), image.getHeight()),
								ModeEnum.OPAQUE == image.getModeValue() ? image.getBackcolor() : null
								);
					}

					imageMimeType = renderer.getImageTypeValue().getMimeType();
					//cacher.putImg(imgkey_imageMimeType,imageMimeType);
					try
					{
						imageData = renderer.getImageData(getJasperReportsContext());
						//cacher.putImg(imgkey_imageData,imageData);
					}
					catch (JRException e)
					{
						throw new ServletException(e);
					}	
					
					//cacher.removeJasperPrint(cachejpid, prtkey);
			}*/

		}

		if (imageData != null && imageData.length > 0)
		{
			if (imageMimeType != null) 
			{
				response.setHeader("Content-Type", imageMimeType);
			}
			response.setContentLength(imageData.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(imageData, 0, imageData.length);
			ouputStream.flush();
			ouputStream.close();
		}
	}


}
