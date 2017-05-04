
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.spi.IIORegistry;

import com.luciad.imageio.webp.WebPImageReaderSpi;
import com.luciad.imageio.webp.WebPImageWriterSpi;
public class Test  {
	static{
		inilibWebp();
		try {
			IIORegistry r = javax.imageio.spi.IIORegistry.getDefaultInstance();
			WebPImageReaderSpi s = new WebPImageReaderSpi();
			WebPImageWriterSpi s2 = new WebPImageWriterSpi();
			r.registerServiceProvider(s);
			r.registerServiceProvider(s2);
		}
		catch (NoClassDefFoundError e) {
			
		}
		
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		File file1= new File("d:\\yundan33.webp");  
	    File file2= new File("d:\\haha.png");  
	   
	    try {  

	    	 /*URL url = new URL("http://192.168.10.132:8084/pkp-pda-itf/obtainPictureServlet?filePath=/app01/ocb/2015-01-16/W06070238/130031035/eff28f1e-2aaf-4d36-aaac-3ad8e7d30d93/130031035.webp");  
		    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();  
		    urlConnection.connect();
		    InputStream in = urlConnection.getInputStream();
		   
		    BufferedInputStream bis = new BufferedInputStream(in);
		    
		    FileOutputStream  os=new FileOutputStream(file1);
		    BufferedOutputStream bos = new BufferedOutputStream(os);
		    int c;
		    while ((c = bis.read()) != -1) {
		    	bos.write(c);
		    	bos.flush();
		    	}
		    
		    
		    bis.close();
		    bos.close(); */
		    
		    
		   /* BufferedImage image = ImageIO.read(in);
		    ImageIO.write(image, "png", file2); */
	    	
	    	
	        BufferedImage im = ImageIO.read(file1);   
	        ImageIO.write(im, "png", file2);  


	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
		
	    
	   /* BufferedImage i = new BufferedImage(100, 100, 2);
	    WebPReadParam rp = new WebPReadParam();
	    WebPWriteParam wp = new WebPWriteParam(Locale.getDefault());
	
	    WebPWriter2 w = new WebPWriter2(new WebPImageWriterSpi());
	    FileImageOutputStream out = new FileImageOutputStream(new File("d://026113//Desktop//yundan100_et.webp"));
	    w.setOutput(out);
	    w.write(i);
	    out.close();
	    */
	    
	    
	}
	public static byte[] loadFile2Bytes(String fileName) {
		try {
		
		File file = new File(fileName);
		// 寰楀埌涓�釜杈撳叆娴�
		FileInputStream inStream = new FileInputStream(file);
		// 寰楀埌涓�釜瀛楄妭娴�
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		long l = file.length();
		// 閫氳繃鏌ョ湅FILE鐨勯暱搴︿笉鍒濆鍖朆YTE鏁扮粍
		byte[] buffer = new byte[(int) l];
		int length = -1;
		// 璇诲彇鏂囦欢鏀句簬鏁扮粍涓�
		while ((length = inStream.read(buffer)) != -1) {
		stream.write(buffer, 0, length);
		}
		stream.close();
		inStream.close();
		return buffer;
		} catch (FileNotFoundException e) {
		e.printStackTrace();
		return null;
		} catch (IOException e) {
		e.printStackTrace();
		return null;
		}
		}
	
	public static void inilibWebp(){
String currentVersion = System.getProperty("sun.arch.data.model");
		
		String path=System.getProperty("java.class.path");
		String url=com.luciad.imageio.webp.Temp.class.getResource("/").getPath();
		url=url.substring(1, url.length());
		String foss_home=System.getProperty("foss_home");
		
		if (foss_home == null || "".equals(foss_home)) {
			if (currentVersion != null) {
				if ("64".equals(currentVersion)) {
					System.load(url + "windows-x64/webp-imageio.dll");
				} else {

					System.load(url + "windows-x86//webp-imageio.dll");
				}
			}

		}else {
			if (currentVersion != null) {
				if ("64".equals(currentVersion)) {
					System.load(foss_home + "windows-x64/webp-imageio.dll");
				} else {

					System.load(foss_home + "windows-x86//webp-imageio.dll");
				}
			}
		}
		
	}
	
}
