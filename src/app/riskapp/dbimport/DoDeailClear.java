package app.riskapp.dbimport;

import java.util.List;

import app.riskapp.common.CommonFunctions;
import app.riskapp.common.DaoFactory;

/**
 * 
 * 删除一年前的贴源明细数据
 * @author Administrator
 *
 */
public class DoDeailClear {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String STime=CommonFunctions.GetCurrentTime();
		
		//String sys_date=String.valueOf(CommonFunctions.GetDBSysDate());
		String cmpt_date="20141014";
		doExcute_DetailClear(cmpt_date);
		
		String ETime=CommonFunctions.GetCurrentTime();
		int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
		int CostFen=costTime/60;
		int CostMiao=costTime%60;
		System.out.println("耗时 "+CostFen+"分"+CostMiao+"秒");

	}
	
	/**
	 * 执行批量:清理流水明细类表的一年前数据
	 * @param cmpt_date  数据库系统日期，8位字符串
	 */
	public static void doExcute_DetailClear(String cmpt_date){
		System.out.println("开始清理一年前的明细数据");
		String sql="";
		
		//删除一年前的历史批量数据
		sql="delete from bips.SOP_MIR_OAVCA where VCTRDT<='"+CommonFunctions.pub_base_deadlineD(Long.valueOf(cmpt_date), -366)+"'";
		CommonFunctions.mydao.execute1(sql);
		sql="delete from bips.SOP_MIR_TDDFA where DFTRDT<='"+CommonFunctions.pub_base_deadlineD(Long.valueOf(cmpt_date), -366)+"'";
		CommonFunctions.mydao.execute1(sql);
		sql="delete from bips.SOP_MIR_UTDCA where DCTRDT<='"+CommonFunctions.pub_base_deadlineD(Long.valueOf(cmpt_date), -366)+"'";
		CommonFunctions.mydao.execute1(sql);
		sql="delete from bips.SOP_MIR_PSDSA where DSTRDT<='"+CommonFunctions.pub_base_deadlineD(Long.valueOf(cmpt_date), -366)+"'";
		CommonFunctions.mydao.execute1(sql);
		sql="delete from bips.SOP_MIR_LDDLA where DLTRDT<='"+CommonFunctions.pub_base_deadlineD(Long.valueOf(cmpt_date), -366)+"'";
		CommonFunctions.mydao.execute1(sql);
		sql="delete from bips.SOP_MIR_CLTJB where TJTRDT<='"+CommonFunctions.pub_base_deadlineD(Long.valueOf(cmpt_date), -366)+"'";
		CommonFunctions.mydao.execute1(sql);
		sql="delete from bips.SAP_MIR_ZTFICOLLECT2 where BUDAT<='"+CommonFunctions.pub_base_deadlineD(Long.valueOf(cmpt_date), -366)+"'";
		CommonFunctions.mydao.execute1(sql);
	}
}
