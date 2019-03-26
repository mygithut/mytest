package app.riskapp.dbimport;

import app.riskapp.common.CommonFunctions;

/**
 * 关键分账户贴源数据的增量备份的 相关处理 使得以后能以此表恢复任意一天的分账户贴源数据表。
 *<br> 共6个贴源分账户表需要这样处理
 * @author Administrator
 *
 */
public class DataMERGE_AfterLDR {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String STime=CommonFunctions.GetCurrentTime();
		
		do_excute_DataMERGE_AfterLDR("20141014");
		
		String ETime=CommonFunctions.GetCurrentTime();
		int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
		int CostFen=costTime/60;
		int CostMiao=costTime%60;
		System.out.println("耗时 "+CostFen+"分"+CostMiao+"秒");

	}
	
	/**
	 * 关键分账户贴源数据的增量备份的相关处理 使得以后能以此来恢复任意一天的分账户贴源数据表。
	 */
	public static void do_excute_DataMERGE_AfterLDR(String sys_date){
		String sql="";
		System.out.println("开始进行【6个关键分账户贴源数据的增量备份的相关处理】...");
		//(1/6)ods.CMS_MIR_LISTLOANBALANCE_HISTORY
		//ods.CMS_MIR_LISTLOANBALANCE_HISTORY表的插入操作
		sql = "delete from ods.CMS_MIR_LISTLOANBALANCE_HISTORY where startdate = '"+sys_date+"'";
		CommonFunctions.mydao.execute1(sql);
	/*	sql = "delete from ods.SOP_MIR_LDMLA_HISTORY where startdate = '"+sys_date+"'";
		CommonFunctions.mydao.execute1(sql);*/
		sql = "delete from ods.SOP_MIR_PSMSA_HISTORY where startdate = '"+sys_date+"'";
		CommonFunctions.mydao.execute1(sql);
		sql = "delete from ods.SOP_MIR_TDMDA_HISTORY where startdate = '"+sys_date+"'";
		CommonFunctions.mydao.execute1(sql);
		sql = "delete from ods.SOP_MIR_UTMCA_HISTORY where startdate = '"+sys_date+"'";
		CommonFunctions.mydao.execute1(sql);
		sql = "delete from ods.SOP_MIR_WAMIA_HISTORY where startdate = '"+sys_date+"'";
		CommonFunctions.mydao.execute1(sql);
		
		
		sql=" merge into ods.CMS_MIR_LISTLOANBALANCE_HISTORY l"
		+"  using (select * from ods.CMS_MIR_LISTLOANBALANCE_HISTORY_TEMP ) lv"
		+"  on (l.DUE_BILL_ID = lv.DUE_BILL_ID and l.startdate = '"+sys_date+"' )"
		+"  when not matched then insert values"
		+   "(" +
		            "lv.LOAN_BARGAIN_ID                ,"+
				    "lv.DUE_BILL_ID                    ,"+
				    "lv.ROW_ID                         ,"+
				    "lv.CUSTOMER_ID                    ,"+
				    "lv.CUST_NAME                      ,"+
			        "lv.LOAN_ACCOUNT                   ,"+
			        "lv.LOAN_ITEM                      ,"+
			        "lv.STATE4_LOAN                    ,"+
			        "lv.STATE5_LOAN                    ,"+
			        "lv.LOAN_DATE                      ,"+
			        "lv.END_DATE                       ,"+
			        "lv.LOAN_MONEY                     ,"+
			        "lv.INTEREST_RATE                  ,"+
			        "lv.OVERDUE_RATE                   ,"+
			        "lv.LOAN_BALANCE                   ,"+
			        "lv.OVERDUE_BALANCE                ,"+
			        "lv.CANCEL_BALANCE                 ,"+
			        "lv.HAVE_CLEAR_NUMBER              ,"+
			        "lv.CHANGE_BALANCE                 ,"+
			        "lv.OWE_INTEREST                   ,"+
			        "lv.INTEREST_CYCLE                 ,"+
			        "lv.LOAN_MODE                      ,"+
			        "lv.ASSURE_TYPE                    ,"+
			        "lv.ASSURER_NAME                   ,"+
			        "lv.LOAN_USE                       ,"+
			        "lv.TELLER_ID                      ,"+
			        "lv.CREDIT_UNITE_ID                ,"+
			        "lv.MOST_CONTRACT_ID               ,"+
			        "lv.UNIT                           ,"+
			        "lv.USER_ID                        ,"+
			        "lv.INPUT_DATE                     ,"+
			        "lv.OPEN_UNIT                      ,"+
			        "lv.CUSTOMER_TYPE                  ,"+
			        "lv.LOAN_KIND_TYPE                 ,"+
			        "lv.TRADE_TYPE                     ,"+
			        "lv.LOAN_TYPE                      ,"+
			        "lv.GRANT_MODE                     ,"+
			        "lv.OPERATION_TYPE                 ,"+
			        "lv.JOB                            ,"+
			        "lv.SIZE_ENTERPRISE                ,"+
			        "lv.KIND_TYPE                      ,"+
			        "lv.LOAN_TERM                      ,"+
			        "lv.ORGNO                          ,"+
			        "lv.POST_CODE                      ,"+
			        "lv.ADDR_ID                        ,"+
			        "lv.LAW_END_DATE                   ,"+
			        "lv.ASSURE_END_DATE                ,"+
			        "lv.MORTAGAGE_END_DATE             ,"+
			        "lv.IMPAWN_END_DATE                ,"+
			        "lv.MAIN_BODY                      ,"+
			        "lv.CLASSIFY_FLOW                  ,"+
			        "lv.PLEDGE_PACT_ID                 ,"+
			        "lv.SEARCHER_MANAGER               ,"+
			        "lv.EXPIRE_TIMES                   ,"+
			        "lv.LOANPAPER_CODE                 ,"+
			        "lv.LOANPAPER_TYPE                 ,"+
			        "lv.IS_GROUP_LOAN                  ,"+
			        "lv.INPUT_DATES                    ,"+
			        "lv.LOAN_BALANCE_LAST              ,"+
			        "lv.TEL_CONTACT                    ,"+
			        "lv.UNIT_DATE                      ,"+
			        "lv.CHANGE_TELLER2                 ,"+
			        "lv.CHANGE_TELLER1                 ,"+
			        "lv.CHANGE_DATE                    ,"+
			        "lv.CHECK_LOAN_BALANCE             ,"+
			        "lv.FLAG                           ,"+
			        "lv.STATE5_BEFORE_LOAN             ,"+
			        "lv.PROVINCE_FLAG                  ,"+
			        "lv.SORT_ID                        ,"+
			        "lv.APPLY_ID                       ,"+
			        "lv.AMORTISATION_TYPE              ,"+
			        "lv.MEDCST                         ,"+
			        "lv.ASSURE_CREDIT_ID               ,"+
			        "lv.ENTERPRISE                     ,"+
			        "lv.ECONOMY_TYPE                   ,"+
			        "lv.QUA_STATE5_LOAN                ,"+
			        "lv.MANAGE_UNIT                    ,"+
			        "lv.OFFICE_UNIT                    ,"+
			        "lv.STATE4_LOAN_CHECK              ,"+
			        "lv.LOAN_ITEM_CHECK                ,"+
			        "lv.UNIT_CHECK                     ,"+
			        "lv.OWE_INTEREST_CHECK             ,"+
			        "lv.LEVEL5_FLAG                    ,"+
			        "lv.RECORD_TYPE                    ,"+
			        "lv.BF_LOAN_BALANCE                ,"+
			        "lv.BF_LOAN_BALANCE_LAST           ,"+
			        "lv.OLD_LOAN_ACCOUNT               ,"+
			        "lv.STANDBY1                       ,"+
			        "lv.STANDBY2                       ,"+
			        "lv.STANDBY3                       ,"+
			        "lv.STANDBY4                       ,"+
			        "lv.STANDBY5                       ,"+
			        "lv.STANDBY6                       ,"+
			        "lv.CUSTOMER_ACCOUNT               ,"+
			        "lv.RATE_FLAG                      ,"+
			        "lv.RETURN_TYPE                    ,"+
			        "lv.FIRST_DEDUCT_DATE              ,"+
			        "lv.WIDE_DAY                       ,"+
			        "lv.INTEREST_FLOAT                 ,"+
			        "'"+sys_date+"'                    ,"+
			        "'29990101'                         "+
		     ")";
		CommonFunctions.mydao.execute1(sql);
		
		//关联操作
		sql="merge into ods.CMS_MIR_LISTLOANBALANCE_HISTORY l"
			+"  using (select * from ods.CMS_MIR_LISTLOANBALANCE_HISTORY_TEMP ) lv"
			+"  on (l.DUE_BILL_ID = lv.DUE_BILL_ID and l.LOAN_BARGAIN_ID = lv.LOAN_BARGAIN_ID and l.CUSTOMER_ID = lv.CUSTOMER_ID and l.enddate = '29990101' and l.startdate<'"+sys_date+"' )"
			+"  when  matched then update set l.enddate = '"+sys_date+"'";
		CommonFunctions.mydao.execute1(sql);
		System.out.println("CMS_MIR_LISTLOANBALANCE_HISTORY表的merge结束！(1/6)\n");
		
	/*	//(2/6)ods.SOP_MIR_LDMLA_HISTORY
		//ods.SOP_MIR_LDMLA_HISTORY表的插入操作
		sql=" merge into ods.SOP_MIR_LDMLA_HISTORY l"+
			"  using (select * from ods.SOP_MIR_LDMLA_HISTORY_TEMP ) lv"+
			"  on (l.MLSBNO = lv.MLSBNO and l.MLCYNO = lv.MLCYNO and l.MLITCD = lv.MLITCD and l.MLACSQ = lv.MLACSQ and l.MLCKBT =lv.MLCKBT and l.startdate = '"+sys_date+"' )"+
			"  when not matched then insert values"+
		    "("+
		            "lv.MLSBNO ,"+ 
			        "lv.MLCYNO ,"+
			        "lv.MLITCD ,"+
			        "lv.MLACSQ ,"+
			        "lv.MLCKBT ,"+
			        "lv.MLACCN ,"+
			        "lv.MLBUCD ,"+
			        "lv.MLREKD ,"+
			        "lv.MLCTNO ,"+
			        "lv.MLCUNM ,"+
			        "lv.MLCRNO ,"+
			        "lv.MLLNLM ,"+
			        "lv.MLBLAT ,"+
			        "lv.MLITEM ,"+
			        "lv.MLLNTP ,"+
			        "lv.MLCONO ,"+
			        "lv.MLEVNO ,"+
			        "lv.MLLIDT ,"+
			        "lv.MLLEDT ,"+
			        "lv.MLLTDT ,"+
			        "lv.MLLABL ,"+
			        "lv.MLACBL ,"+
			        "lv.MLGVVL ,"+
			        "lv.MLLLVL ,"+
			        "lv.MLPERD ,"+
			        "lv.MLMADT ,"+
			        "lv.MLINCM ,"+
			        "lv.MLDIFG ,"+
			        "lv.MLDAMK ,"+
			        "lv.MLIOFG ,"+
			        "lv.MLINRT ,"+
			        "lv.MLYEMO ,"+
			        "lv.MLTIIN ,"+
			        "lv.MLTOIN ,"+
			        "lv.MLAMBL ,"+
			        "lv.MLMDIC ,"+
			        "lv.MLMDAL ,"+
			        "lv.MLLNAT ,"+
			        "lv.MLLIAC ,"+
			        "lv.MLLOAC ,"+
			        "lv.MLNIAC ,"+
			        "lv.MLNOAC ,"+
			        "lv.MLDTAC ,"+
			        "lv.MLCLAC ,"+
			        "lv.MLPPAC ,"+
			        "lv.MLPCBL ,"+
			        "lv.MLOBAC ,"+
			        "lv.MLOPDT ,"+
			        "lv.MLOPUS ,"+
			        "lv.MLCLDT ,"+
			        "lv.MLCLUS ,"+
			        "lv.MLMTDT ,"+
			        "lv.MLMTUS ,"+
			        "lv.MLASMD ,"+
			        "lv.MLCAAC ,"+
			        "lv.MLREAC ,"+
			        "lv.MLLNUS ,"+
			        "lv.MLASCN ,"+
			        "lv.MLASCO ,"+
			        "lv.MLMOTP ,"+
			        "lv.MLAMAO ,"+
			        "lv.MLERDT ,"+
			        "lv.MLFVLV ,"+
			        "lv.MLMEMO ,"+
			        "lv.MLSTCD ,"+
			        "'"+sys_date+"',"+
			        "'29990101' "+
			    ")";   
		CommonFunctions.mydao.execute1(sql);
		//关联操作
		sql="  merge into ods.SOP_MIR_LDMLA_HISTORY l"
			+"   using (select * from ods.SOP_MIR_LDMLA_HISTORY_TEMP ) lv"
			+"   on (l.MLSBNO = lv.MLSBNO and l.MLCYNO = lv.MLCYNO and l.MLITCD = lv.MLITCD and l.MLACSQ = lv.MLACSQ and l.MLCKBT =lv.MLCKBT and l.enddate = '29990101' and l.startdate<'"+sys_date+"')"
			+"   when  matched then update set l.enddate = '"+sys_date+"'";
		CommonFunctions.mydao.execute1(sql);
		System.out.println("SOP_MIR_LDMLA_HISTORY表的merge结束！(2/6)\n");
		*/
		//(3/6)ods.SOP_MIR_PSMSA_HISTORY
		sql="  merge into ods.SOP_MIR_PSMSA_HISTORY l"+
		    "  using (select * from ods.SOP_MIR_PSMSA_HISTORY_TEMP) lv"+
		    "  on (l.MSACCN = lv.MSACCN and l.startdate = '"+sys_date+"' )"+
		    "  when not matched then insert values"+
		    "( "+
		            "lv.MSSBNO, "+
			        "lv.MSCYNO, "+
			        "lv.MSITCD, "+
			        "lv.MSACSQ, "+
			        "lv.MSCKBT, "+
			        "lv.MSACCN, "+
			        "lv.MSSVAC, "+
			        "lv.MSACC1, "+
			        "lv.MSSBSQ, "+
			        "lv.MSCTNO, "+
			        "lv.MSINCN, "+
			        "lv.MSITEM, "+
			        "lv.MSBLAT, "+
			        "lv.MSLEDT, "+
			        "lv.MSLSBD, "+
			        "lv.MSLABL, "+
			        "lv.MSLIDT, "+
			        "lv.MSHOBL, "+
			        "lv.MSFZBL, "+
			        "lv.MSCTBL, "+
			        "lv.MSACBL, "+
			        "lv.MSBLDE, "+
			        "lv.MSINCM, "+
			        "lv.MSINMK, "+
			        "lv.MSINKD, "+
			        "lv.MSACIN, "+
			        "lv.MSAMBL, "+
			        "lv.MSMDIC, "+
			        "lv.MSMDAL, "+
			        "lv.MSTHFG, "+
			        "lv.MSOPDT, "+
			        "lv.MSOPUS, "+
			        "lv.MSMTDT, "+
			        "lv.MSMTUS, "+
			        "lv.MSCLDT, "+
			        "lv.MSCLUS, "+
			        "lv.MSLADT, "+
			        "lv.MSITRL, "+
			        "lv.MSTDNU, "+
			        "lv.MSACAM, "+
			        "lv.MSSTCD, "+
			        "'"+sys_date+"',"+
			        "'29990101' "+
		    " )";  
		
		CommonFunctions.mydao.execute1(sql);
		//关联操作
		sql="  merge into ods.SOP_MIR_PSMSA_HISTORY l"
			+"  using (select * from ods.SOP_MIR_PSMSA_HISTORY_TEMP ) lv"
			+"  on (l.MSACCN = lv.MSACCN  and l.enddate = '29990101' and l.startdate<'"+sys_date+"')"
			+"  when  matched then update set l.enddate = '"+sys_date+"'";
		CommonFunctions.mydao.execute1(sql);
		System.out.println("ods.SOP_MIR_PSMSA_HISTORY表的merge结束！(3/6)\n");
		
		//(4/6)ods.SOP_MIR_TDMDA_HISTORY
		sql=" merge into ods.SOP_MIR_TDMDA_HISTORY l"+
		    "  using (select * from ods.SOP_MIR_TDMDA_HISTORY_TEMP ) lv"+
		    "  on (l.MDACCN = lv.MDACCN and l.MDSBSQ = lv.MDSBSQ and l.startdate = '"+sys_date+"' )"+
		    "  when not matched then insert values"+
		    "( "+
				    "lv.MDSBNO , "+
			        "lv.MDCYNO , "+
			        "lv.MDITCD , "+
			        "lv.MDACSQ , "+
			        "lv.MDCKBT , "+
			        "lv.MDACCN , "+
			        "lv.MDSVAC , "+
			        "lv.MDACC1 , "+
			        "lv.MDSBSQ , "+
			        "lv.MDCTNO , "+
			        "lv.MDCUNM , "+
			        "lv.MDITEM , "+
			        "lv.MDBLAT , "+
			        "lv.MDPERD , "+
			        "lv.MDLEDT , "+
			        "lv.MDLABL , "+
			        "lv.MDLIDT , "+
			        "lv.MDVLDT , "+
			        "lv.MDMADT , "+
			        "lv.MDINKD , "+
			        "lv.MDINRT , "+
			        "lv.MDYEMO , "+
			        "lv.MDACBL , "+
			        "lv.MDCTBL , "+
			        "lv.MDFZBL , "+
			        "lv.MDEVAM , "+
			        "lv.MDACAM , "+
			        "lv.MDACIN , "+
			        "lv.MDAMBL , "+
			        "lv.MDMDIC , "+
			        "lv.MDMDAL , "+
			        "lv.MDTHFG , "+
			        "lv.MDDINO , "+
			        "lv.MDAUCD , "+
			        "lv.MDAUNU , "+
			        "lv.MDAUAC , "+
			        "lv.MDOPAM , "+
			        "lv.MDIFDT , "+
			        "lv.MDOPDT , "+
			        "lv.MDOPUS , "+
			        "lv.MDMTDT , "+
			        "lv.MDMTUS , "+
			        "lv.MDCLDT , "+
			        "lv.MDCLUS , "+
			        "lv.MDGTKD , "+
			        "lv.MDSTCD , "+
			        "'"+sys_date+"', "+
			        "'29990101'  "+
		    " )";  
		CommonFunctions.mydao.execute1(sql);
		//关联操作
		sql="  merge into ods.SOP_MIR_TDMDA_HISTORY l"
			+"  using (select * from ods.SOP_MIR_TDMDA_HISTORY_TEMP ) lv"
			+"  on (l.MDACCN = lv.MDACCN and l.MDSBSQ = lv.MDSBSQ  and l.enddate = '29990101' and l.startdate<'"+sys_date+"')"
			+"  when  matched then update set l.enddate = '"+sys_date+"'";
		CommonFunctions.mydao.execute1(sql);
		System.out.println("ods.SOP_MIR_TDMDA_HISTORY表的merge结束！(4/6)\n");
		
		//(5/6)ods.SOP_MIR_UTMCA_HISTORY
		sql="  merge into ods.SOP_MIR_UTMCA_HISTORY l"+
		    "  using (select * from ods.SOP_MIR_UTMCA_HISTORY_TEMP ) lv"+
		    "  on (l.MCACCN = lv.MCACCN and l.startdate = '"+sys_date+"' )"+
		    "  when not matched then insert values"+
		    " ( "+
	            "lv.MCSBNO        ,"+
		        "lv.MCCYNO        ,"+
		        "lv.MCITCD        ,"+
		        "lv.MCACSQ        ,"+
		        "lv.MCCKBT        ,"+
		        "lv.MCACCN        ,"+
		        "lv.MCCTNO        ,"+
		        "lv.MCCUNM        ,"+
		        "lv.MCCTFG        ,"+
		        "lv.MCOVFG        ,"+
		        "lv.MCBLAT        ,"+
		        "lv.MCBUCD        ,"+
		        "lv.MCECQL        ,"+
		        "lv.MCITEM        ,"+
		        "lv.MCLEDT        ,"+
		        "lv.MCLSBD        ,"+
		        "lv.MCLABL        ,"+
		        "lv.MCLIDT        ,"+
		        "lv.MCINPR        ,"+
		        "lv.MCHOBL        ,"+
		        "lv.MCFZBL        ,"+
		        "lv.MCCTBL        ,"+
		        "lv.MCACBL        ,"+
		        "lv.MCBLDE        ,"+
		        "lv.MCTHFG        ,"+
		        "lv.MCINKD        ,"+
		        "lv.MCPFIR        ,"+
		        "lv.MCINCM        ,"+
		        "lv.MCINMK        ,"+
		        "lv.MCACIN        ,"+
		        "lv.MCAMBL        ,"+
		        "lv.MCMDIC        ,"+
		        "lv.MCMDAL        ,"+
		        "lv.MCOVBL        ,"+
		        "lv.MCDAMK        ,"+
		        "lv.MCBAFG        ,"+
		        "lv.MCPKFG        ,"+
		        "lv.MCPRTP        ,"+
		        "lv.MCNXLN        ,"+
		        "lv.MCWDTP        ,"+
		        "lv.MCOPDT        ,"+
		        "lv.MCOPUS        ,"+
		        "lv.MCMTDT        ,"+
		        "lv.MCMTUS        ,"+
		        "lv.MCCLDT        ,"+
		        "lv.MCCLUS        ,"+
		        "lv.MCLADT        ,"+
		        "lv.MCITRL        ,"+
		        "lv.MCTDNU        ,"+
		        "lv.MCACAM        ,"+
		        "lv.MCSTCD        ,"+
		        "'"+sys_date+"'   ,"+
		        "'29990101'        "+
		    ")";  
		CommonFunctions.mydao.execute1(sql);
		//关联操作
		sql="  merge into ods.SOP_MIR_UTMCA_HISTORY l"
			+"   using (select * from ods.SOP_MIR_UTMCA_HISTORY_TEMP ) lv"
			+"   on (l.MCACCN = lv.MCACCN  and l.enddate = '29990101' and l.startdate<'"+sys_date+"')"
			+"   when  matched then update set l.enddate = '"+sys_date+"'";
		CommonFunctions.mydao.execute1(sql);
		System.out.println("ods.SOP_MIR_UTMCA_HISTORY表的merge结束！(5/6)\n");
		
		//(6/6)ods.SOP_MIR_WAMIA_HISTORY
		sql= "  merge into ods.SOP_MIR_WAMIA_HISTORY l"+
		     "   using (select * from ods.SOP_MIR_WAMIA_HISTORY_TEMP ) lv"+
		     "   on (l.MISBNO = lv.MISBNO and l.MICYNO = lv.MICYNO and l.MIITCD = lv.MIITCD and l.MIACSQ =lv.MIACSQ and l.MICKBT = lv.MICKBT and l.startdate = '"+sys_date+"' )"+
		     "   when not matched then insert values"+
		      "("+
	            "lv.MISBNO ,"+ 
		        "lv.MICYNO ,"+
		        "lv.MIITCD ,"+
		        "lv.MIACSQ ,"+
		        "lv.MICKBT ,"+
		        "lv.MIITEM ,"+
		        "lv.MIBLAT ,"+
		        "lv.MIACNM ,"+
		        "lv.MILSBD ,"+
		        "lv.MIBLDE ,"+
		        "lv.MIOVFG ,"+
		        "lv.MIOWBL ,"+
		        "lv.MILEDT ,"+
		        "lv.MILIDT ,"+
		        "lv.MILABL ,"+
		        "lv.MIACBL ,"+
		        "lv.MIINCM ,"+
		        "lv.MIINRT ,"+
		        "lv.MIYEMO ,"+
		        "lv.MIACIN ,"+
		        "lv.MIAMBL ,"+
		        "lv.MIODAL ,"+
		        "lv.MIMDIC ,"+
		        "lv.MIMDAL ,"+
		        "lv.MIIAFG ,"+
		        "lv.MIOPDT ,"+
		        "lv.MIOPUS ,"+
		        "lv.MIMTDT ,"+
		        "lv.MIMTUS ,"+
		        "lv.MICLDT ,"+
		        "lv.MICLUS ,"+
		        "lv.MISTCD ,"+
		        "'"+sys_date+"',"+
		        "'29990101' "+
		    ")";  
		CommonFunctions.mydao.execute1(sql);
		//关联操作
		sql="  merge into ods.SOP_MIR_WAMIA_HISTORY l"
			+"   using (select * from ods.SOP_MIR_WAMIA_HISTORY_TEMP ) lv"
			+"   on (l.MISBNO = lv.MISBNO and l.MICYNO = lv.MICYNO and l.MIITCD = lv.MIITCD and l.MIACSQ =lv.MIACSQ and l.MICKBT = lv.MICKBT and l.enddate = '29990101' and l.startdate<'"+sys_date+"' )"
			+"   when  matched then update set l.enddate = '"+sys_date+"'";
		CommonFunctions.mydao.execute1(sql);
		System.out.println("ods.SOP_MIR_WAMIA_HISTORY表的merge结束！(6/6)");
		
		System.out.println("【关键分账户贴源数据的增量备份的相关处理】结束！\n");
	}

}
