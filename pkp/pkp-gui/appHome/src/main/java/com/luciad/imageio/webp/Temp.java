/*    */ package com.luciad.imageio.webp;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.Locale;
/*    */ import javax.imageio.stream.FileImageOutputStream;
/*    */ 
/*    */ public class Temp
/*    */ {
/*    */   public static void main(String[] args)
/*    */     throws IOException
/*    */   {
/* 20 */     BufferedImage i = new BufferedImage(100, 100, 2);
/* 21 */     WebPReadParam rp = new WebPReadParam();
/* 22 */     WebPWriteParam wp = new WebPWriteParam(Locale.getDefault());
/*    */ 
/* 24 */     WebPWriter w = new WebPWriter(new WebPImageWriterSpi());
/* 25 */     FileImageOutputStream out = new FileImageOutputStream(new File("test.webp"));
/* 26 */     w.setOutput(out);
/* 27 */     w.write(i);
/* 28 */     out.close();
/*    */   }
/*    */ }

/* Location:           E:\Desktop\webp-imageio-0.4.2\webp-imageio.jar
 * Qualified Name:     com.luciad.imageio.webp.Temp
 * JD-Core Version:    0.6.0
 */