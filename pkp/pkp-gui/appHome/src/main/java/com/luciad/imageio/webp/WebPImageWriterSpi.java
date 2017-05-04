/*     */ package com.luciad.imageio.webp;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.ComponentColorModel;
/*     */ import java.awt.image.ComponentSampleModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ import java.awt.image.SampleModel;
/*     */ import java.awt.image.SinglePixelPackedSampleModel;
/*     */ import java.io.IOException;
/*     */ import java.util.Locale;
/*     */ import javax.imageio.ImageTypeSpecifier;
/*     */ import javax.imageio.ImageWriter;
/*     */ import javax.imageio.spi.ImageWriterSpi;
/*     */ import javax.imageio.stream.ImageOutputStream;
/*     */ 
/*     */ public class WebPImageWriterSpi extends ImageWriterSpi
/*     */ {
/*     */   public WebPImageWriterSpi()
/*     */   {
/*  38 */     super("Luciad", "1.0", new String[] { "WebP", "webp" }, new String[] { "webp" }, new String[] { "image/webp" }, WebPReader.class.getName(), new Class[] { ImageOutputStream.class }, new String[] { WebPImageReaderSpi.class.getName() }, false, null, null, null, null, false, null, null, null, null);
/*     */   }
/*     */ 
/*     */   public boolean canEncodeImage(ImageTypeSpecifier type)
/*     */   {
/*  62 */     ColorModel colorModel = type.getColorModel();
/*  63 */     SampleModel sampleModel = type.getSampleModel();
/*  64 */     int transferType = sampleModel.getTransferType();
/*     */ 
/*  66 */     if ((colorModel instanceof ComponentColorModel)) {
/*  67 */       if (!(sampleModel instanceof ComponentSampleModel)) {
/*  68 */         return false;
/*     */       }
/*     */ 
/*  71 */       if ((transferType != 0) && (transferType != 3)) {
/*  72 */         return false;
/*     */       }
/*     */     }
/*  75 */     else if ((colorModel instanceof DirectColorModel)) {
/*  76 */       if (!(sampleModel instanceof SinglePixelPackedSampleModel)) {
/*  77 */         return false;
/*     */       }
/*     */ 
/*  80 */       if (transferType != 3) {
/*  81 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*  85 */     ColorSpace colorSpace = colorModel.getColorSpace();
/*  86 */     if (!colorSpace.isCS_sRGB()) {
/*  87 */       return false;
/*     */     }
/*     */ 
/*  90 */     int[] sampleSize = sampleModel.getSampleSize();
/*  91 */     for (int i = 0; i < sampleSize.length; i++) {
/*  92 */       if (sampleSize[i] > 8) {
/*  93 */         return false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  98 */     return true;
/*     */   }
/*     */ 
/*     */   public ImageWriter createWriterInstance(Object extension) throws IOException
/*     */   {
/* 103 */     return new WebPWriter(this);
/*     */   }
/*     */ 
/*     */   public String getDescription(Locale locale)
/*     */   {
/* 108 */     return "WebP Writer";
/*     */   }
/*     */ }

/* Location:           E:\Desktop\webp-imageio-0.4.2\webp-imageio.jar
 * Qualified Name:     com.luciad.imageio.webp.WebPImageWriterSpi
 * JD-Core Version:    0.6.0
 */