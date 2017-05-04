/*     */ package com.luciad.imageio.webp;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import javax.imageio.ImageWriteParam;
/*     */ 
/*     */ public class WebPWriteParam extends ImageWriteParam
/*     */ {
/*     */   long fPointer;
/*     */   private final int defaultLossless;
/*     */ 
/*     */   public WebPWriteParam(Locale aLocale)
/*     */   {
/*  30 */     super(aLocale);
/*  31 */     this.fPointer = createConfig();
/*  32 */     if (this.fPointer == 0L) {
/*  33 */       throw new OutOfMemoryError();
/*     */     }
/*  35 */     this.defaultLossless = getLossless(this.fPointer);
/*  36 */     this.canWriteCompressed = true;
/*  37 */     this.compressionTypes = new String[] { "Lossy", "Lossless" };
/*     */ 
/*  41 */     this.compressionType = this.compressionTypes[this.defaultLossless];
/*  42 */     this.compressionQuality = (getQuality(this.fPointer) / 100.0F);
/*  43 */     this.compressionMode = 2;
/*     */   }
/*     */ 
/*     */   protected void finalize() throws Throwable
/*     */   {
/*  48 */     super.finalize();
/*  49 */     deleteConfig(this.fPointer);
/*  50 */     this.fPointer = 0L;
/*     */   }
/*     */   private static native long createConfig();
/*     */ 
/*     */   private static native void deleteConfig(long paramLong);
/*     */ 
/*  58 */   long getPointer() { return this.fPointer;
/*     */   }
/*     */ 
/*     */   public float getCompressionQuality()
/*     */   {
/*  63 */     return super.getCompressionQuality();
/*     */   }
/*     */ 
/*     */   public void setCompressionQuality(float quality)
/*     */   {
/*  68 */     super.setCompressionQuality(quality);
/*  69 */     setQuality(this.fPointer, quality * 100.0F);
/*     */   }
/*     */ 
/*     */   public void setCompressionType(String compressionType)
/*     */   {
/*  74 */     super.setCompressionType(compressionType);
/*  75 */     for (int i = 0; i < this.compressionTypes.length; i++)
/*  76 */       if (this.compressionTypes[i].equals(compressionType)) {
/*  77 */         setLossless(this.fPointer, i);
/*  78 */         break;
/*     */       }
/*     */   }
/*     */ 
/*     */   public void unsetCompression()
/*     */   {
/*  86 */     super.unsetCompression();
/*  87 */     setLossless(this.fPointer, this.defaultLossless);
/*     */   }
/*     */ 
/*     */   public int getTargetSize() {
/*  91 */     return getTargetSize(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setTargetSize(int aTargetSize) {
/*  95 */     setTargetSize(this.fPointer, aTargetSize);
/*     */   }
/*     */ 
/*     */   public float getTargetPSNR() {
/*  99 */     return getTargetPSNR(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setTargetPSNR(float aTargetPSNR) {
/* 103 */     setTargetPSNR(this.fPointer, aTargetPSNR);
/*     */   }
/*     */ 
/*     */   public int getMethod() {
/* 107 */     return getMethod(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setMethod(int aMethod) {
/* 111 */     setMethod(this.fPointer, aMethod);
/*     */   }
/*     */ 
/*     */   public int getSegments() {
/* 115 */     return getSegments(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setSegments(int aSegments) {
/* 119 */     setSegments(this.fPointer, aSegments);
/*     */   }
/*     */ 
/*     */   public int getSnsStrength() {
/* 123 */     return getSnsStrength(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setSnsStrength(int aSnsStrength) {
/* 127 */     setSnsStrength(this.fPointer, aSnsStrength);
/*     */   }
/*     */ 
/*     */   public int getFilterStrength() {
/* 131 */     return getFilterStrength(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setFilterStrength(int aFilterStrength) {
/* 135 */     setFilterStrength(this.fPointer, aFilterStrength);
/*     */   }
/*     */ 
/*     */   public int getFilterSharpness() {
/* 139 */     return getFilterSharpness(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setFilterSharpness(int aFilterSharpness) {
/* 143 */     setFilterSharpness(this.fPointer, aFilterSharpness);
/*     */   }
/*     */ 
/*     */   public int getFilterType() {
/* 147 */     return getFilterType(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setFilterType(int aFilterType) {
/* 151 */     setFilterType(this.fPointer, aFilterType);
/*     */   }
/*     */ 
/*     */   public boolean isAutoAdjustFilterStrength() {
/* 155 */     return getAutofilter(this.fPointer) != 0;
/*     */   }
/*     */ 
/*     */   public void setAutoAdjustFilterStrength(boolean aAutofilter) {
/* 159 */     setAutofilter(this.fPointer, aAutofilter ? 1 : 0);
/*     */   }
/*     */ 
/*     */   public int getEntropyAnalysisPassCount() {
/* 163 */     return getPass(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setEntropyAnalysisPassCount(int aPass) {
/* 167 */     setPass(this.fPointer, aPass);
/*     */   }
/*     */ 
/*     */   public boolean isShowCompressed() {
/* 171 */     return getShowCompressed(this.fPointer) != 0;
/*     */   }
/*     */ 
/*     */   public void setShowCompressed(boolean aShowCompressed) {
/* 175 */     setShowCompressed(this.fPointer, aShowCompressed ? 1 : 0);
/*     */   }
/*     */ 
/*     */   public int getPreprocessing() {
/* 179 */     return getPreprocessing(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setPreprocessing(int aPreprocessing) {
/* 183 */     setPreprocessing(this.fPointer, aPreprocessing);
/*     */   }
/*     */ 
/*     */   public int getPartitions() {
/* 187 */     return getPartitions(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setPartitions(int aPartitions) {
/* 191 */     setPartitions(this.fPointer, aPartitions);
/*     */   }
/*     */ 
/*     */   public int getPartitionLimit() {
/* 195 */     return getPartitionLimit(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setPartitionLimit(int aPartitionLimit) {
/* 199 */     setPartitionLimit(this.fPointer, aPartitionLimit);
/*     */   }
/*     */ 
/*     */   public int getAlphaCompression() {
/* 203 */     return getAlphaCompression(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setAlphaCompression(int aAlphaCompression) {
/* 207 */     setAlphaCompression(this.fPointer, aAlphaCompression);
/*     */   }
/*     */ 
/*     */   public int getAlphaFiltering() {
/* 211 */     return getAlphaFiltering(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setAlphaFiltering(int aAlphaFiltering) {
/* 215 */     setAlphaFiltering(this.fPointer, aAlphaFiltering);
/*     */   }
/*     */ 
/*     */   public int getAlphaQuality() {
/* 219 */     return getAlphaQuality(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setAlphaQuality(int aAlphaQuality) {
/* 223 */     setAlphaQuality(this.fPointer, aAlphaQuality);
/*     */   }
/*     */ 
/*     */   public boolean isEmulateJpegSize() {
/* 227 */     return getEmulateJpegSize(this.fPointer) != 0;
/*     */   }
/*     */ 
/*     */   public void setEmulateJpegSize(boolean aEmulateJpegSize) {
/* 231 */     setEmulateJpegSize(this.fPointer, aEmulateJpegSize ? 1 : 0);
/*     */   }
/*     */ 
/*     */   public int getThreadLevel() {
/* 235 */     return getThreadLevel(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setThreadLevel(int aThreadLevel) {
/* 239 */     setThreadLevel(this.fPointer, aThreadLevel);
/*     */   }
/*     */ 
/*     */   public boolean isReduceMemoryUsage() {
/* 243 */     return getLowMemory(this.fPointer) != 0;
/*     */   }
/*     */ 
/*     */   public void setReduceMemoryUsage(boolean aLowMemory) {
/* 247 */     setLowMemory(this.fPointer, aLowMemory ? 1 : 0);
/*     */   }
/*     */ 
/*     */   private static native float getQuality(long paramLong);
/*     */ 
/*     */   private static native void setQuality(long paramLong, float paramFloat);
/*     */ 
/*     */   private static native int getTargetSize(long paramLong);
/*     */ 
/*     */   private static native void setTargetSize(long paramLong, int paramInt);
/*     */ 
/*     */   private static native float getTargetPSNR(long paramLong);
/*     */ 
/*     */   private static native void setTargetPSNR(long paramLong, float paramFloat);
/*     */ 
/*     */   private static native int getMethod(long paramLong);
/*     */ 
/*     */   private static native void setMethod(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getSegments(long paramLong);
/*     */ 
/*     */   private static native void setSegments(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getSnsStrength(long paramLong);
/*     */ 
/*     */   private static native void setSnsStrength(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getFilterStrength(long paramLong);
/*     */ 
/*     */   private static native void setFilterStrength(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getFilterSharpness(long paramLong);
/*     */ 
/*     */   private static native void setFilterSharpness(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getFilterType(long paramLong);
/*     */ 
/*     */   private static native void setFilterType(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getAutofilter(long paramLong);
/*     */ 
/*     */   private static native void setAutofilter(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getPass(long paramLong);
/*     */ 
/*     */   private static native void setPass(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getShowCompressed(long paramLong);
/*     */ 
/*     */   private static native void setShowCompressed(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getPreprocessing(long paramLong);
/*     */ 
/*     */   private static native void setPreprocessing(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getPartitions(long paramLong);
/*     */ 
/*     */   private static native void setPartitions(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getPartitionLimit(long paramLong);
/*     */ 
/*     */   private static native void setPartitionLimit(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getAlphaCompression(long paramLong);
/*     */ 
/*     */   private static native void setAlphaCompression(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getAlphaFiltering(long paramLong);
/*     */ 
/*     */   private static native void setAlphaFiltering(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getAlphaQuality(long paramLong);
/*     */ 
/*     */   private static native void setAlphaQuality(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getLossless(long paramLong);
/*     */ 
/*     */   private static native void setLossless(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getEmulateJpegSize(long paramLong);
/*     */ 
/*     */   private static native void setEmulateJpegSize(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getThreadLevel(long paramLong);
/*     */ 
/*     */   private static native void setThreadLevel(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getLowMemory(long paramLong);
/*     */ 
/*     */   private static native void setLowMemory(long paramLong, int paramInt);
/*     */ 
/*     */   static
/*     */   {
/*  23 */     WebP.loadNativeLibrary();
/*     */   }
/*     */ }

/* Location:           E:\Desktop\webp-imageio-0.4.2\webp-imageio.jar
 * Qualified Name:     com.luciad.imageio.webp.WebPWriteParam
 * JD-Core Version:    0.6.0
 */