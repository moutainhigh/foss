/*    */ package com.luciad.imageio.webp;
/*    */ 
/*    */ import java.awt.image.RenderedImage;
/*    */ import java.io.IOException;
/*    */ import javax.imageio.IIOImage;
/*    */ import javax.imageio.ImageTypeSpecifier;
/*    */ import javax.imageio.ImageWriteParam;
/*    */ import javax.imageio.ImageWriter;
/*    */ import javax.imageio.metadata.IIOMetadata;
/*    */ import javax.imageio.spi.ImageWriterSpi;
/*    */ import javax.imageio.stream.ImageOutputStream;
/*    */ 
/*    */ class WebPWriter extends ImageWriter
/*    */ {
/*    */   WebPWriter(ImageWriterSpi originatingProvider)
/*    */   {
/* 30 */     super(originatingProvider);
/*    */   }
/*    */ 
/*    */   public ImageWriteParam getDefaultWriteParam()
/*    */   {
/* 35 */     return new WebPWriteParam(getLocale());
/*    */   }
/*    */ 
/*    */   public IIOMetadata convertImageMetadata(IIOMetadata inData, ImageTypeSpecifier imageType, ImageWriteParam param)
/*    */   {
/* 40 */     return null;
/*    */   }
/*    */ 
/*    */   public IIOMetadata convertStreamMetadata(IIOMetadata inData, ImageWriteParam param)
/*    */   {
/* 45 */     return null;
/*    */   }
/*    */ 
/*    */   public IIOMetadata getDefaultImageMetadata(ImageTypeSpecifier imageType, ImageWriteParam param)
/*    */   {
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */   public IIOMetadata getDefaultStreamMetadata(ImageWriteParam param)
/*    */   {
/* 55 */     return null;
/*    */   }
/*    */ 
/*    */   public void write(IIOMetadata streamMetadata, IIOImage image, ImageWriteParam param) throws IOException
/*    */   {
/* 60 */     if (param == null) {
/* 61 */       param = getDefaultWriteParam();
/*    */     }
/*    */ 
/* 64 */     WebPWriteParam writeParam = (WebPWriteParam)param;
/*    */ 
/* 66 */     ImageOutputStream output = (ImageOutputStream)getOutput();
/* 67 */     RenderedImage ri = image.getRenderedImage();
/*    */ 
/* 69 */     byte[] encodedData = WebP.encode(writeParam, ri);
/* 70 */     output.write(encodedData);
/*    */   }
/*    */ }

/* Location:           E:\Desktop\webp-imageio-0.4.2\webp-imageio.jar
 * Qualified Name:     com.luciad.imageio.webp.WebPWriter
 * JD-Core Version:    0.6.0
 */