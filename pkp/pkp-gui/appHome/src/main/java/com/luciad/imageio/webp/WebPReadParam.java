/*     */ package com.luciad.imageio.webp;
/*     */ 
/*     */ import javax.imageio.ImageReadParam;
/*     */ 
/*     */ public final class WebPReadParam extends ImageReadParam
/*     */ {
/*     */   long fPointer;
/*     */ 
/*     */   public WebPReadParam()
/*     */   {
/*  28 */     this.fPointer = createDecoderOptions();
/*  29 */     if (this.fPointer == 0L)
/*  30 */       throw new OutOfMemoryError();
/*     */   }
/*     */ 
/*     */   protected void finalize()
/*     */     throws Throwable
/*     */   {
/*  36 */     super.finalize();
/*  37 */     deleteDecoderOptions(this.fPointer);
/*  38 */     this.fPointer = 0L;
/*     */   }
/*     */ 
/*     */   public int getCropHeight() {
/*  42 */     return getCropHeight(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setCropHeight(int aCropHeight) {
/*  46 */     setCropHeight(this.fPointer, aCropHeight);
/*     */   }
/*     */ 
/*     */   public int getCropLeft() {
/*  50 */     return getCropLeft(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setCropLeft(int aCropLeft) {
/*  54 */     setCropLeft(this.fPointer, aCropLeft);
/*     */   }
/*     */ 
/*     */   public int getCropTop() {
/*  58 */     return getCropTop(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setCropTop(int aCropTop) {
/*  62 */     setCropTop(this.fPointer, aCropTop);
/*     */   }
/*     */ 
/*     */   public int getCropWidth() {
/*  66 */     return getCropWidth(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setCropWidth(int aCropWidth) {
/*  70 */     setCropWidth(this.fPointer, aCropWidth);
/*     */   }
/*     */ 
/*     */   public boolean isForceRotation() {
/*  74 */     return isForceRotation(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setForceRotation(boolean aForceRotation) {
/*  78 */     setForceRotation(this.fPointer, aForceRotation);
/*     */   }
/*     */ 
/*     */   public boolean isEnhancement() {
/*  82 */     return !isNoEnhancement(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setEnhancement(boolean aEnhancement) {
/*  86 */     setNoEnhancement(this.fPointer, !aEnhancement);
/*     */   }
/*     */ 
/*     */   public boolean isFancyUpsampling() {
/*  90 */     return !isNoFancyUpsampling(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setFancyUpsampling(boolean aFancyUpsampling) {
/*  94 */     setNoFancyUpsampling(this.fPointer, !aFancyUpsampling);
/*     */   }
/*     */ 
/*     */   public int getScaledHeight() {
/*  98 */     return getScaledHeight(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setScaledHeight(int aScaledHeight) {
/* 102 */     setScaledHeight(this.fPointer, aScaledHeight);
/*     */   }
/*     */ 
/*     */   public int getScaledWidth() {
/* 106 */     return getScaledWidth(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setScaledWidth(int aScaledWidth) {
/* 110 */     setScaledWidth(this.fPointer, aScaledWidth);
/*     */   }
/*     */ 
/*     */   public boolean isUseCropping() {
/* 114 */     return isUseCropping(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setUseCropping(boolean aUseCropping) {
/* 118 */     setUseCropping(this.fPointer, aUseCropping);
/*     */   }
/*     */ 
/*     */   public boolean isUseScaling() {
/* 122 */     return isUseScaling(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setUseScaling(boolean aUseScaling) {
/* 126 */     setUseScaling(this.fPointer, aUseScaling);
/*     */   }
/*     */ 
/*     */   public boolean isUseThreads() {
/* 130 */     return isUseThreads(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setUseThreads(boolean aUseThreads) {
/* 134 */     setUseThreads(this.fPointer, aUseThreads);
/*     */   }
/*     */ 
/*     */   public boolean isBypassFiltering() {
/* 138 */     return isBypassFiltering(this.fPointer);
/*     */   }
/*     */ 
/*     */   public void setBypassFiltering(boolean aBypassFiltering) {
/* 142 */     setBypassFiltering(this.fPointer, aBypassFiltering);
/*     */   }
/*     */ 
/*     */   private static native long createDecoderOptions();
/*     */ 
/*     */   private static native void deleteDecoderOptions(long paramLong);
/*     */ 
/*     */   private static native int getCropHeight(long paramLong);
/*     */ 
/*     */   private static native void setCropHeight(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getCropLeft(long paramLong);
/*     */ 
/*     */   private static native void setCropLeft(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getCropTop(long paramLong);
/*     */ 
/*     */   private static native void setCropTop(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getCropWidth(long paramLong);
/*     */ 
/*     */   private static native void setCropWidth(long paramLong, int paramInt);
/*     */ 
/*     */   private static native boolean isForceRotation(long paramLong);
/*     */ 
/*     */   private static native void setForceRotation(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   private static native boolean isNoEnhancement(long paramLong);
/*     */ 
/*     */   private static native void setNoEnhancement(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   private static native boolean isNoFancyUpsampling(long paramLong);
/*     */ 
/*     */   private static native void setNoFancyUpsampling(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   private static native int getScaledHeight(long paramLong);
/*     */ 
/*     */   private static native void setScaledHeight(long paramLong, int paramInt);
/*     */ 
/*     */   private static native int getScaledWidth(long paramLong);
/*     */ 
/*     */   private static native void setScaledWidth(long paramLong, int paramInt);
/*     */ 
/*     */   private static native boolean isUseCropping(long paramLong);
/*     */ 
/*     */   private static native void setUseCropping(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   private static native boolean isUseScaling(long paramLong);
/*     */ 
/*     */   private static native void setUseScaling(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   private static native boolean isUseThreads(long paramLong);
/*     */ 
/*     */   private static native void setUseThreads(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   private static native boolean isBypassFiltering(long paramLong);
/*     */ 
/*     */   private static native void setBypassFiltering(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   static
/*     */   {
/*  22 */     WebP.loadNativeLibrary();
/*     */   }
/*     */ }

/* Location:           E:\Desktop\webp-imageio-0.4.2\webp-imageio.jar
 * Qualified Name:     com.luciad.imageio.webp.WebPReadParam
 * JD-Core Version:    0.6.0
 */