package app.riskapp.dbimport;

import java.util.List;

import app.riskapp.common.CommonFunctions;
import app.riskapp.common.DaoFactory;

/**
 * 
 * ɾ��һ��ǰ����Դ��ϸ����
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
		System.out.println("��ʱ "+CostFen+"��"+CostMiao+"��");

	}
	
	/**
	 * ִ������:������ˮ��ϸ����һ��ǰ����
	 * @param cmpt_date  ���ݿ�ϵͳ���ڣ�8λ�ַ���
	 */
	public static void doExcute_DetailClear(String cmpt_date){
		System.out.println("��ʼ����һ��ǰ����ϸ����");
		String sql="";
		
		//ɾ��һ��ǰ����ʷ��������
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
