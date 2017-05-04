package com.deppon.pda.bdm.module.core.shared.constants.version;

/**
 * 卸车
 * @author mt
 * 2013年7月17日18:42:36
 */
public interface UnLoadConstant {
	/**
	 * 获取卸车指令
	 */
	interface OPER_TYPE_UNLD_TASK_GET{
		public final static String VERSION = "UNLD_01";
	}

	/**
	 * 检查标签
	 */
	interface OPER_TYPE_UNLD_SEALS_CHK{
		public final static String VERSION = "UNLD_02";
	}
	
	/**
	 * 检查标签新
	 */
	interface OPER_TYPE_UNLD_SEALS_NEW_CHK{
		public final static String VERSION = "UNLD_NEW_02";
	}

	/**
	 * 建立卸车任务
	 */
	interface OPER_TYPE_UNLD_TASK_CREATE{
		public final static String VERSION = "UNLD_03";
	}

	/**
	 * 刷新卸车任务
	 */
	interface OPER_TYPE_UNLD_TASK_RFSH{
		public final static String VERSION = "UNLD_04";
	}

	/**
	 * 卸车扫描
	 */
	interface OPER_TYPE_UNLD_SCAN{
		public final static String VERSION = "UNLD_05";
	}

	/**
	 * 撤销卸车扫描
	 */
	interface OPER_TYPE_UNLD_SCAN_CANCEL{
		public final static String VERSION = "UNLD_06";
	}

	/**
	 * 补录重量体积
	 */
	interface OPER_TYPE_UNLD_VOL_WGT_MKP{ 
		public final static String VERSION = "UNLD_07";
	}

	/**
	 * 完成卸车任务
	 */
	interface OPER_TYPE_UNLD_TASK_FINISH{
		public final static String VERSION = "UNLD_08";
	}

	/**
	 * 撤销卸车任务
	 */
	interface OPER_TYPE_UNLD_TASK_CANCEL{
		public final static String VERSION = "UNLD_09";
	}

	/**
	 * 增加/删除理货员
	 */
	interface OPER_TYPE_UNLD_OPR_DEL_ADD{
		public final static String VERSION = "UNLD_10";
	}

	/**
	 * 提交卸车任务
	 */
	interface OPER_TYPE_UNLD_TASK_SUBMIT{
		public final static String VERSION = "UNLD_11";
	}
	
	/**
	 * 多货卸车扫描
	 */
	interface OPER_TYPE_FORCE_UNLD_SCAN{
		public final static String VERSION = "UNLD_12";
	}

	/**
	 * 查询补码结果接口
	 */
	interface OPER_TYPE_UNLD_COMP_QUERY{
		public final static String VERSION = "UNLD_13";
	}
	
	/**
	 * 分拣扫描
	 */
	interface OPER_TYPE_UNLD_SORTING_SCAN{
		public final static String VERSION = "UNLD_14";
	}
	
	/**
	 * 卸车托盘绑定扫描
	 */
	interface OPER_TYPE_UNLD_PALLET_BOUND_SCAN{
		public final static String VERSION = "UNLD_15";
	}
	
	/**
	 * 
	  * @ClassName OPER_TYPE_UNLD_GET_BOUND_TASK 
	  * @Description TODO 获取托盘任务接口扫描
	  * @author mt hyssmt@vip.qq.com
	  * @date 2013-9-16 上午11:25:22
	 */
	interface OPER_TYPE_UNLD_GET_BOUND_TASK{
		public final static String VERSION = "UNLD_16";
	}
	
	/**
	 * 
	  * @ClassName OPER_TYPE_UNLD_FINISH_BOUND_TASK 
	  * @Description TODO 叉车完成扫描托盘任务
	  * @author mt hyssmt@vip.qq.com
	  * @date 2013-9-16 上午11:25:11
	 */
	interface OPER_TYPE_UNLD_FINISH_BOUND_TASK{
		public final static String VERSION = "UNLD_17";
	}
	/**
	 * 
	 * TODO(叉车异常绑定)
	 * <p style="display:none">modifyRecord</p>
	 * <p style="display:none">version:V1.0,author:Administrator,date:2014-1-3 下午2:07:06,content:TODO </p>
	 * @author Administrator
	 * @date 2014-1-3 下午2:07:06
	 * @since
	 * @version
	 */
	interface OPER_TYPE_EXC_UNLD_PALLET_BOUND_SCAN{
		public final static String VERSION = "UNLD_18";
	}
	/**
	 * 
	 * TODO(叉车票统计)
	 * <p style="display:none">modifyRecord</p>
	 * <p style="display:none">version:V1.0,author:Administrator,date:2014-1-13 上午9:23:40,content:TODO </p>
	 * @author Administrator
	 * @date 2014-1-13 上午9:23:40
	 * @since
	 * @version
	 */
	interface OPER_TYPE_UNLD_PALLET_BOUND_COUNT{
		public final static String VERSION = "UNLD_19";
	}
	
	 

    /** 
      * @ClassName OPER_TYPE_UNLD_EXCE_BOUND_COUNT 
      * @Description 异常 叉车票统计
      * @author 092038 
      * @date 2014-5-4 上午10:34:55 
    */ 
    interface OPER_TYPE_UNLD_EXCE_BOUND_COUNT{
        public final static String VERSION = "UNLD_20";
    }
    
    
    /**
    * 获取卸车差异报告接口
    */
      interface OPER_TYPE_UNLD_EXCE_REPORT{
       public final static String VERSION = "UNLD_21";
   }
   
    /**
    * 获取卸车差异报告明细接口
    */
      interface OPER_TYPE_UNLD_EXCE__DETAIL{
       public final static String VERSION = "UNLD_22";
   }
   
   /**
   * 上传卸车差异报告扫描明细
   */
   interface OPER_TYPE_UNLD_EXCE_SCAN{
      public final static String VERSION = "UNLD_23";
   }
   /**
   * 提交卸车差异报告
   */
   interface OPER_TYPE_UNLD_EXCE_SUBMIT{
      public final static String VERSION = "UNLD_24";
   }
   /**
    * 分拣扫描与查询补码
    */
   interface OPER_TYPE_COMP_QUERY_SORTING_SCAN{
  	 public final static String VERSION="UNLD_25";
  }
   /**
    * 补录重量体积接口
    */
    interface OPER_TYPE_UNLD_VOLUMEANDWEIGHT_SUBMIT{
       public final static String VERSION = "UNLD_VO_KG";
    } 
    
    /**
     * 刷新包明细（卸车）
     */
     interface OPER_TYPE_UNLD_TASK_RFSH_PAKAGE{
        public final static String VERSION = "UNLOAD_REF_PACK_LIST";
     } 
     
     /**
      * 获取库区定位编号信息
      */
      interface OPER_TYPE_UNLD_QSTOCKPOSITION_NUMBER{
         public final static String VERSION = "QUERY_STOCK_POSITION";
      } 
    
      /**
       * 保存库区定位编号信息
       */
       interface OPER_TYPE_UNLD_SSTOCKPOSITION_NUMBER{
          public final static String VERSION = "SAVE_STOCK_POSITION";
       } 
       /**
        * 快进快出模式，根据快递或提示对应零担货区
        */
        interface OPER_TYPE_UNLD_SSTOCKPOSITION_GET{
           public final static String VERSION = "STOCK_POSITION_GET";
        } 
        
        
      //司机PDA接驳卸车--二程接驳   
        /**
         * 获取接驳卸车指令任务
         */
        interface OPER_TYPE_UNLOAD_TRAN_GET{
     	   public final static String VERSION="UNLOAD_01";
        }
        /**
         * 创建卸车任务
         */
        interface OPER_TYPE_UNLOAD_TRAN_CREATE{
     	   public final static String VERSION="UNLOAD_02";
        }
        /**
         * 下拉接驳卸车明细
         */
        interface OPER_TYPE_UNLOAD_TRAN_DOWN{
     	   public final static String VERSION="UNLOAD_03";
        }
        /**
         * 接驳卸车扫描
         */
        interface OPER_TYPE_UNLOAD_TRAN_SCAN{
     	   public final static String VERSION="UNLOAD_04";
        }
        /**
         * 提交接驳卸车任务
         */
        interface OPER_TYPE_UNLOAD_TRAN_SUBMIT{
     	   public final static String VERSION="UNLOAD_05";
        }
        /**
         * 记录第一次扫描为司机到达时间
         */
        interface OPER_TYPE_UNLOAD_TRAN_COUNT{
     	   public final static String VERSION="UNLOAD_06";
        }
       
        
        //司机PDA卸车差异报告--二程接驳
        /**
         * 获取差异报告指令
         */
        interface OPER_TYPE_UNLODA_EXCE__GET{
            public final static String VERSION = "TWOWAY_DIFF_01";
        }
        /**
         * 根据条件查询卸车差异报告
         */
        interface OPER_TYPE_UNLODA_EXCE__QUERY{
            public final static String VERSION = "TWOWAY_DIFF_02";
        }
        /**
         * 查询明细
         */
        interface OPER_TYPE_UNLODA_EXCE__DETAIL_QUERY{
            public final static String VERSION = "TWOWAY_DIFF_03";
        }
        /**
         * 提交差异处理
         */
        interface OPER_TYPE_UNLODA_EXCE_SUBMIT{
            public final static String VERSION = "TWOWAY_DIFF_04";
        }
        /**
         * 差异报告扫描
         */
        interface OPER_TYPE_UNLODA_EXCE_SCAN{
            public final static String VERSION = "TWOWAY_DIFF_05";
        }
        //合伙人需求
        /**
         * 点单任务刷新
         */
        interface OPER_TYPE_ORDER_TASK_REF{
        	 public final static String VERSION = "CHECK_REF";
        }
        /**
         * 任务更新
         */
        interface OPER_TYPE_ORDER_TASK_UPDATE{
       	     public final static String VERSION = "CHECK_UPD";
       }
        /**
         * 扫描任务上传
         */
	    interface OPER_TYPE_SCAN_TASK_UPLOAD {
		    public final static String VERSION = "CHECK_SCAN";
	    } 
	    /**
	     * 扫描任务提交
	     */
	    interface OPER_TYPE_SCAN_TASK_SUBMIT{
		    public final static String VERSION = "CHECK_SUB";

	    }
	    /**
	     * 运单明细
	     */
	    interface OPER_TYPE_WAY_DETAIL{
	    	public final static String VERSION = "CHECK_DET";
	    }
	    
	    /**
	     * 查询交接单号
	     */
	    
	   interface  OPER_TYPE_HANDER_BY_TASK{
		   public final static String VERSION = "CHECK_HANDER";
	   }
}
