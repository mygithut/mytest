package app.riskapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import app.riskapp.entity.Ftp1PrdtCkzbjAdjust;
import app.riskapp.entity.Ftp1PrdtClAdjust;
import app.riskapp.entity.Ftp1PrdtIrAdjust;
import app.riskapp.entity.Ftp1PrdtLdxAdjust;
import app.riskapp.entity.Ftp1PrepayAdjust;

import app.riskapp.common.CommonFunctions;
import app.riskapp.common.DaoFactory;
import app.riskapp.entity.BrMst;
import app.riskapp.entity.FtpMidItem;
/**
 * 
 * Company: 东华软件股份公司金融风险产品部
 * 
 * @author 孙红玉
 * 
 * @date Apr 29, 2011 4:40:30 PM
 * 
 * @version 1.0
 */
public class FtpUtil {
	
	/**
	 * 扣除营业税及附加的盈利性资产回报率={[利息收入-(表内应收利息期末余额-表内应收利息期初余额) +手续费收入+投资收益（扣除股权性投资收益）+汇兑损益]*（1-综合税率）+（金融机构往来利息收入-系统内往来利息收入}/[各项贷款（不含委托贷款）月均余额+各类债券投资月均余额+拆放同业月均余额+存放同业月均余额+存放央央银行准备金、备付金、特种存款和财政性存款月均余额]
	 * @param currencySelectId
	 * @param brNo
	 * @return
	 */
	public static void main(String[] args) {
		/*Double[] x={1/30.0,7/30.0,14/30.0,1.0,3.0};
		Double[] y={1/15.0,7/15.0,14/15.0,2.0,6.0};
		Double[] re=Spline(x,y,6);
		for(int i=0;i<re.length;i++){
			System.out.print(re[i]+", ");
		}
		System.out.println();*/
		
		/*SCYTCZlineF f= getSCYTCZlineF_fromDB("0102","20120413","1801038009");
		SCYTCZlineF.print_SCYTCZline(f);
		for(int i=0;i<f.X.length;i++){
			double y=f.getY_SCYTCZline(f.X[i]);
			System.out.println(f.X[i]+"="+y);
		}*/
		
		
		
		
		
	}
	
	/**
	 * 扣除营业税及附加的盈利性资产回报率
	 * {[利息收入-(表内应收利息期末余额-表内应收利息期初余额) +手续费收入+投资收益（扣除股权性投资收益）+汇兑损益]*（1-综合税率）+金融机构往来利息收入}/[各项贷款（不含委托贷款）月均余额+各类债券投资月均余额+拆放同业月均余额+存放同业月均余额+存放央央银行款项月均余额]
	 * @param currencySelectId
	 * @param brNo
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Double getYlxzchbl(String currencySelectId, String brNo, String date) throws ParseException {
		//利息收入-年度月均余额--每月累计增加额
        Double lxsr = getYMRaiseAverage6(currencySelectId, brNo, date, "10001");
		System.out.println("利息收入lxsr#10001:"+lxsr);
		//表内应收利息期末余额-表内应收利息期初余额=表内应收利息累计增加额
		Double yslxljzje = getYMRaiseAverage(currencySelectId, brNo, date, "10002");
		System.out.println("表内应收利息累计增加额yslxljzje#10002:"+yslxljzje);
        //手续费收入-年度月均余额--每月累计增加额
		Double sxfsr = getYMRaiseAverage6(currencySelectId, brNo, date, "10003");
		System.out.println("手续费收入sxfsr#10003:"+sxfsr);
		//投资收益（股权性投资收益）-年度月均余额--每月累计增加额
		Double tzsy = getYMRaiseAverage6(currencySelectId, brNo, date, "10004");
		System.out.println("投资收益（股权性投资收益）tzsy#10004:"+tzsy);
		//汇兑损益
		Double hdsy = getYMRaiseAverage6(currencySelectId, brNo, date, "10005");
		System.out.println("汇兑损益hdsy#10005:"+hdsy);
		//综合税率
		Double zhsl = getItemSum("10007", brNo, currencySelectId, date, "");
		System.out.println("综合税率zhsl#10007:"+zhsl);
		//金融机构往来利息收入
		Double jrjgwllxsr = getYMRaiseAverage6(currencySelectId, brNo, date, "10006");
		System.out.println("金融机构往来利息收入jrjgwllxsr#10006:"+jrjgwllxsr);
		//月均余额为每月汇总一次
		//各项贷款年度月均余额
		Double gxdkndyjye = getYMAverage(currencySelectId, brNo, date, "10008");
		System.out.println("各项贷款gxdkndyjye#10008:"+gxdkndyjye);
		//拆放同业年度月均余额
		Double cftyndyjye = getYMAverage(currencySelectId, brNo, date, "10009");
		System.out.println("拆放同业cftyndyjye#10009:"+cftyndyjye);
		//存放同业年度月均余额
		Double cunftyndyjye = getYMAverage(currencySelectId, brNo, date, "10010");
		System.out.println("存放同业cunftyndyjye#10110:"+cunftyndyjye);
		//存放中央银行款项年度月均余额
		Double cfzyyhkxndyjye = getYMAverage(currencySelectId, brNo, date, "10011");
		System.out.println("存放中央银行款项cfzyyhkxndyjye#10011:"+cfzyyhkxndyjye);
		//各类债券投资年度月均余额
		Double glzqtzndyjye = getYMAverage(currencySelectId, brNo, date, "10012");
		System.out.println("各类债券投资glzqtzndyjye#10012:"+glzqtzndyjye);
		
		//{[利息收入+(表内应收利息期末余额-表内应收利息期初余额) +手续费收入+投资收益（扣除股权性投资收益）+汇兑损益]*（1-综合税率）+金融机构往来利息收入}/[各项贷款（不含委托贷款）月均余额+各类债券投资月均余额+拆放同业月均余额+存放同业月均余额+存放央央银行款项月均余额]
		Double b = ((lxsr+yslxljzje+sxfsr+tzsy+hdsy)*(1-zhsl)+jrjgwllxsr)/(gxdkndyjye+glzqtzndyjye+cftyndyjye+cunftyndyjye+cfzyyhkxndyjye);
		System.out.println("扣除营业税及附加的盈利性资产回报率"+"(("+lxsr+"+"+yslxljzje+"+"+sxfsr+"+"+tzsy+"+"+hdsy+")*(1-"+zhsl+")+"+jrjgwllxsr+")/("+gxdkndyjye+"+"+glzqtzndyjye+"+"+cftyndyjye+"+"+cunftyndyjye+"+"+cfzyyhkxndyjye+")");
		return (b.isInfinite() || b.isNaN())?0.0:b;
	}
	
	/**
	 * //资产损失率（含信用风险因素）=核定的呆坏账准备金提取额/各项盈利性资产月均余额
	 * @param currencySelectId
	 * @param brNo
	 * @return
	 * @throws ParseException 
	 */
	public static Double getZcssl(String currencySelectId, String brNo, String date) throws ParseException {
		//核定的呆坏账准备金提取额--年度月均余额
		Double dhzzbjtqe = getYMAverage(currencySelectId, brNo, date, "10013");
		System.out.println("核定的呆坏账准备金提取额dhzzbjtqe#10013:"+dhzzbjtqe);
		//月均余额为每月汇总一次
		//各项盈利性资产月均余额
		Double gxylxzcyjye = getYMAverage(currencySelectId, brNo, date, "10014");
		System.out.println("各项盈利性资产年度月均余额gxylxzcyjye#10014:"+gxylxzcyjye);
		
		//核定的呆坏账准备金提取额/各项盈利性资产月均余额
		Double e = dhzzbjtqe/gxylxzcyjye;
		System.out.println("资产损失率"+dhzzbjtqe+"/"+gxylxzcyjye+"");
		return (e.isInfinite() || e.isNaN())?0.0:e;
	}
	
	/**
	 * 筹资成本率=[利息支出+金融机构往来利息支出-（表内应付利息期末余额-表内应付利息期初余额）+手续费支出+发行债券利息支出]/（各项存款月均余额+同业存放月均余额+同业拆入月均余额+中央银行借款月均余额+发行债券月均余额+贴现、交易性金融负债及卖出回购金融资产款）
	 * @param currencySelectId
	 * @param brNo
	 * @return
	 * @throws ParseException 
	 */
	public static Double getCzcbl(String currencySelectId, String brNo, String date) throws ParseException { 
        //利息支出--每月累计增加额
		Double lxzc = getYMRaiseAverage6(currencySelectId, brNo, date, "10015");
		System.out.println("利息支出lxzc#10015:"+lxzc);
		//金融机构往来利息支出--每月累计增加额
		Double jrjgwllxzc = getYMRaiseAverage6(currencySelectId, brNo, date, "10016");
		System.out.println("金融机构往来利息支出jrjgwllxzc#10016:"+jrjgwllxzc);
		//表内应付利息期末余额-表内应付利息期初余额=表内应付利息累计增加额
		Double yflxljzje = getYMRaiseAverage(currencySelectId, brNo, date, "10017");
		System.out.println("表内应付利息期末余额yflxljzje#10017:"+yflxljzje);
		//手续费支出--每月累计增加额
		Double sxfzc = getYMRaiseAverage6(currencySelectId, brNo, date, "10018");
		System.out.println("手续费支出sxfzc#10018:"+sxfzc);
		//发行债券利息支出--每月累计增加额
		//Double fxzqlxzc = getYMRaiseAverage(currencySelectId, brNo, date, "10019");
		Double fxzqlxzc = 0.0;
		System.out.println("发行债券利息支出fxzqlxzc#10019:"+fxzqlxzc);
		//各项存款年度月均余额
		Double gxckndyjye = getYMAverage(currencySelectId, brNo, date, "10020");
		System.out.println("各项存款年度月均余额gxckndyjye#10020:"+gxckndyjye);
		//同业存放年度月均余额
		Double tycfndyjye = getYMAverage(currencySelectId, brNo, date, "10022");
		System.out.println("同业存放年度月均余额tycfndyjye#10022:"+tycfndyjye);
		//同业拆入年度月均余额
		Double tycrndyjye = getYMAverage(currencySelectId, brNo, date, "10023");
		System.out.println("同业拆入年度月均余额tycrndyjye#10023:"+tycrndyjye);
		//中央银行借款项年度月均余额
		Double zyyhjkndyjye = getYMAverage(currencySelectId, brNo, date, "10021");
		System.out.println("中央银行借款项年度月均余额zyyhjkndyjye#10021:"+zyyhjkndyjye);
		//发行债券年度月均余额
		Double fxzqndyjye = getYMAverage(currencySelectId, brNo, date, "10024");
		System.out.println("发行债券年度月均余额fxzqndyjye#10024:"+fxzqndyjye);
		//卖出回购金融资产年度月均余额
		Double mchgjrzcndyjye = getYMAverage(currencySelectId, brNo, date, "10025");
		System.out.println("卖出回购金融资产年度月均余额mchgjrzcndyjye#10025:"+mchgjrzcndyjye);
		//贴现负债年度月均余额
		Double txfzndyjye = getYMAverage(currencySelectId, brNo, date, "10026");
		System.out.println("贴现负债年度月均余额txfzndyjye#10026:"+txfzndyjye);
		//交易性金融负债年度月均余额
		Double jyxjrfzndyjye = getYMAverage(currencySelectId, brNo, date, "10027");
		System.out.println("交易性金融负债年度月均余额jyxjrfzndyjye#10027:"+jyxjrfzndyjye);
		
		//[利息支出+金融机构往来利息支出+（表内应付利息期末余额-表内应付利息期初余额）+手续费支出+发行债券利息支出]/（各项存款月均余额+同业存放月均余额+同业拆入月均余额+中央银行借款月均余额+发行债券月均余额+贴现、交易性金融负债及卖出回购金融资产款）
		Double a = (lxzc+jrjgwllxzc+yflxljzje+fxzqlxzc+sxfzc)/(gxckndyjye+tycfndyjye+tycrndyjye+zyyhjkndyjye+fxzqndyjye+mchgjrzcndyjye+txfzndyjye+jyxjrfzndyjye);
		System.out.println("筹资成本率"+"("+lxzc+"+"+jrjgwllxzc+"+"+yflxljzje+"+"+fxzqlxzc+"+"+sxfzc+")/("+gxckndyjye+"+"+tycfndyjye+"+"+tycrndyjye+"+"+zyyhjkndyjye+"+"+fxzqndyjye+"+"+mchgjrzcndyjye+"+"+txfzndyjye+"+"+jyxjrfzndyjye+")");
		return (a.isInfinite() || a.isNaN())?0.0:a;
	}
	
	/**
	 * 筹资费用率=业务及管理费*拉取存款所需费用占比/各项存款月均余额
	 * @param currencySelectId
	 * @param brNo
	 * @return
	 * @throws ParseException 
	 */
	public static Double getCzfyl(String currencySelectId, String brNo, String date) throws ParseException {
		//业务及管理费--每月累计增加额
		Double ywjglf = getYMRaiseAverage6(currencySelectId, brNo, date, "10028");
		System.out.println("业务及管理费ywjglf#10028:"+ywjglf);
		//拉取存款所需费用占比
		Double lqcksxfyzb = getItemSum("10029", brNo, currencySelectId, date, "");
		System.out.println("拉取存款所需费用占比lqcksxfyzb#10029:"+lqcksxfyzb);
		//各项存款年度月均余额
		Double gxckndyjye = getYMAverage(currencySelectId, brNo, date, "10020");
		System.out.println("各项存款年度月均余额gxckndyjye#10020:"+gxckndyjye);
		
		//业务及管理费*拉取存款所需费用占比/各项存款月均余额
		Double f = ywjglf * lqcksxfyzb / gxckndyjye;
		System.out.println("筹资费用率="+ywjglf+"*"+lqcksxfyzb+"/"+gxckndyjye);
		
		return (f.isInfinite() || f.isNaN())?0.0:f;
	}
	
	/**
	 * 存款平均成本率=存款加权平均利率+筹资费用率
	 * 存款加权平均利率=个人存款加权平均利率c1*权数w1+单位存款加权平均利率c2*权数w2+同业存款利率c3*权数w3
	 * 在这里没有分类，直接对 所有的产品进行加权平均计算
	 * 筹资费用率=同成本收益法
	 * @param currencySelectId
	 * @param brNo
	 * @return 存款平均成本率
	 * @throws ParseException 
	 */
	public static Double getCkpjcbl(String currencySelectId, String brNo, String date) throws ParseException {
		//所有存款产品
//		String ckPrdtNo = "'P2010','P2038','P2011','P2039','P2012','P2040','P2013','P2041','P2014','P2042','P2015'," +
//				"'P2043','P2016','P2044','P2017','P2045','P2018','P2046','P2019','P2047','P2020','P2048','P2021'," +
//				"'P2049','P2022','P2050','P2023','P2051','P2024','P2052','P2025','P2053','P2026','P2054','P2027'," +
//				"'P2028','P2029','P2030','P2056','P2001','P2031','P2002','P2032','P2003','P2033','P2004','P2034'," +
//				"'P2005','P2035','P2006','P2036','P2007','P2037','P2008','P2009','P2055','P2057','P2059','P2062'," +
//				"'P2063','P2065','P2066','P2067','P2068'";
		String ckPrdtNo = "'P2010','P2038','P2011','P2039','P2012','P2040','P2013','P2041','P2014','P2042','P2015'," +
		"'P2043','P2016','P2044','P2017','P2045','P2018','P2046','P2019','P2047','P2020','P2048','P2021'," +
		"'P2049','P2022','P2050','P2023','P2051','P2024','P2052','P2025','P2053','P2026','P2054','P2027'," +
		"'P2028','P2029','P2030','P2056','P2001','P2031','P2002','P2032','P2003','P2033','P2004','P2034'," +
		"'P2005','P2035','P2006','P2036','P2007','P2037','P2008','P2009','P2055'";
		//存款加权平均利率
		String brSql = LrmUtil.getBrSql(brNo);
		Double ckjqpjlv = getWeightedAveRate(brSql, date, ckPrdtNo, "3", -12, false);//年度月均余额
		System.out.println("存款加权平均利率:"+ckjqpjlv);
		//筹资费用率
		Double czfyl = getCzfyl(currencySelectId, brNo, date);
		System.out.println("筹资费用率:"+czfyl);
		//存款平均成本率
		Double ckpjcbl = ckjqpjlv + czfyl;
		System.out.println("存款平均成本率:"+ckpjcbl);
		return (ckpjcbl.isInfinite() || ckpjcbl.isNaN())?0.0:ckpjcbl;
	}
	
	/**
	 * 贷款平均收益率=农户贷款平均收益率d1*权数z1+农村经济组织贷款平均收益率d2*权数z2+农村企业贷款平均收益率d3*权数z3+非农贷款平均收益率d4*权数z4+信用卡透支平均收益率d5*权数z5+贴现资产平均收益率d6*权数d6+垫款平均收益率d7*权数z7+存放中央银行款项平均收益率d8*权数z8+存放同业平均收益率d9*权数z9+拆放同业平均收益率d10*权数z10+投资业务d11*权数z11
	 * 在这里没有分类，直接对 所有的产品进行加权平均计算
	 * 资产损失率 = 同成本收益法
	 * @param currencySelectId
	 * @param brNo
	 * @return 贷款平均收益率
	 * @throws ParseException 
	 */
	public static Double getDkpjsyl(String currencySelectId, String brNo, String date) throws ParseException {
		//所有贷款产品
//		String dkprdtNo = "'P1018','P1019','P1020','P1021','P1022','P1023','P1024','P1025','P1026','P1027','P1028'," +
//				"'P1029','P1030','P1031','P1032','P1033','P1034','P1035','P1036','P1037','P1038','P1039','P1040'," +
//				"'P1041','P1042','P1043','P1044','P1045','P1046','P1047','P1048','P1049','P1050','P1051','P1052'," +
//				"'P1053','P1054','P1055','P1056','P1057','P1058','P1059','P1060','P1061','P1062','P1063','P1064'," +
//				"'P1065','P1066','P1067','P1068','P1069','P1070','P1071','P1072','P1073','P1074','P1075','P1076'," +
//				"'P1077','P1078','P1079','P1080','P1081','P1082','P1083','P1084','P1085','P1086','P1087','P1088'," +
//				"'P1089','P1090','P1091','P1092','P1093','P1094','P1095','P1096','P1097','P1098','P1099','P1100'," +
//				"'P1101','P1102','P1103','P1104','P1106','P1002','P1005','P1006','P1009','P1007','P1008','P1011'," +
//				"'P1012','P1114','P1116','P1123'";
		String dkprdtNo = "'P1018','P1019','P1020','P1021','P1022','P1023','P1024','P1025','P1026','P1027','P1028'," +
		"'P1029','P1030','P1031','P1032','P1033','P1034','P1035','P1036','P1037','P1038','P1039','P1040'," +
		"'P1041','P1042','P1043','P1044','P1045','P1046','P1047','P1048','P1049','P1050','P1051','P1052'," +
		"'P1053','P1054','P1055','P1056','P1057','P1058','P1059','P1060','P1061','P1062','P1063','P1064'," +
		"'P1065','P1066','P1067','P1068','P1069','P1070','P1071','P1072','P1073','P1074','P1075','P1076'," +
		"'P1077','P1078','P1079','P1080','P1081','P1082','P1083','P1084','P1085','P1086','P1087','P1088'," +
		"'P1089','P1090','P1091','P1092','P1093','P1094','P1095','P1096','P1097','P1098','P1099','P1100'," +
		"'P1101','P1102'";
		String brSql = LrmUtil.getBrSql(brNo);
		//贷款平均利率
		Double dkpjll = getWeightedAveRate(brSql, date, dkprdtNo, "3", -12, false);//年度月均
		System.out.println("贷款平均利率:"+dkpjll);
		//资产损失率
		Double zcssl = getZcssl(currencySelectId, brNo, date);
		System.out.println("资产损失率:"+zcssl);
		//贷款平均收益率
		Double dkpjsyl = dkpjll - zcssl;
		System.out.println("贷款平均收益率:"+dkpjsyl);
		
		return (dkpjsyl.isInfinite() || dkpjsyl.isNaN())?0.0:dkpjsyl;
	}
	//平均存贷比
	public static Double getPjcdb(String currencySelectId, String brNo, String date) throws ParseException {
		//各项存款年度月均余额
		Double gxckndyjye = getYMAverage(currencySelectId, brNo, date, "10020");
		System.out.println("各项存款gxckndyjye#10020:"+gxckndyjye);
		//各项贷款年度月均余额
		Double gxdkndyjye = getYMAverage(currencySelectId, brNo, date, "10008");
		System.out.println("各项贷款gxdkndyjye#10008:"+gxdkndyjye);
		
        //平均存贷比:存贷比（Loan-to-depositRatio），即银行贷款总额/存款总额
        Double pjcdb = gxdkndyjye/gxckndyjye;
        System.out.println("平均存贷比:"+pjcdb);
        return (pjcdb.isInfinite() || pjcdb.isNaN())?0.0:pjcdb;
	}
	/**
	 * 信用风险修正   ：正常、关注、次级、可疑、损失，五类贷款
	 * @param currencySelectId
	 * @param brNo
	 * @return
	 * @throws ParseException 
	 */
	public static Double getXyfxxz(String currencySelectId, String brNo, String date) throws ParseException {
		Double zcldkye = 0.0, gzldkye = 0.0, cjldkye = 0.0, kydkye = 0.0, ssldkye = 0.0;
		//正常类贷款月度日均余额
		zcldkye = getMDAverage(currencySelectId, brNo, date, "10030");
	    //关注类贷款月度日均余额
		gzldkye = getMDAverage(currencySelectId, brNo, date, "10031");
	    //次级类贷款月度日均余额
		cjldkye = getMDAverage(currencySelectId, brNo, date, "10032");
	    //可疑类贷款月度日均余额
		kydkye = getMDAverage(currencySelectId, brNo, date, "10033");
	    //损失类贷款月度日均余额
		ssldkye = getMDAverage(currencySelectId, brNo, date, "10034");
	    System.out.println("正常类贷款月度日均余额"+zcldkye);
	    System.out.println("关注类贷款月度日均余额"+gzldkye);
	    System.out.println("次级类贷款月度日均余额"+cjldkye);
	    System.out.println("可疑类贷款月度日均余额"+kydkye);
	    System.out.println("损失类贷款月度日均余额"+ssldkye);
	    //正常类贷款计提比例
		Double zcldkjtbl = getItemSum("10035", brNo, currencySelectId, date, "");
		System.out.println("正常类贷款计提比例zcldkjtbl#10035:"+zcldkjtbl);
		//关注类贷款计提比例
		Double gzldkjtbl = getItemSum("10036", brNo, currencySelectId, date, "");
		System.out.println("关注类贷款计提比例gzldkjtbl#10036:"+gzldkjtbl);
		//次级类贷款计提比例
		Double cjldkjtbl = getItemSum("10037", brNo, currencySelectId, date, "");
		System.out.println("次级类贷款计提比例cjldkjtbl#10037:"+cjldkjtbl);
		//可疑类贷款计提比例
		Double kyldkjtbl = getItemSum("10038", brNo, currencySelectId, date, "");
		System.out.println("可疑类贷款计提比例kyldkjtbl#10038:"+kyldkjtbl);
		//损失类贷款计提比例
		Double ssldkjtbl = getItemSum("10039", brNo, currencySelectId, date, "");
		System.out.println("损失类贷款计提比例ssldkjtbl#10039:"+ssldkjtbl);
	    Double xyfxxz = (zcldkye * zcldkjtbl + gzldkye * gzldkjtbl + cjldkye * cjldkjtbl + kydkye * kyldkjtbl + ssldkye * ssldkjtbl) /(zcldkye + gzldkye + cjldkye + kydkye + ssldkye);
	    return (xyfxxz.isInfinite() || xyfxxz.isNaN())?0.0:xyfxxz;
	}
	
	/**
	 * 流动性风险修正
	 * @param currencySelectId
	 * @param brNo
	 * @return
	 * @throws ParseException 
	 */
	public static Double getLdxfxxz(String currencySelectId, String brNo, String date) throws ParseException {
        DaoFactory df = new DaoFactory();
		
        //各项存款月度日均余额
		Double gxckydrjye = getMDAverage(currencySelectId, brNo, date, "10020");
		System.out.println("各项存款gxckydrjye#10020:"+gxckydrjye);
		//各项贷款月度日均余额
		Double gxdkydrjye = getMDAverage(currencySelectId, brNo, date, "10008");
		System.out.println("各项贷款gxdkydrjye#10008:"+gxdkydrjye);
		//日均存贷款余额之差
        Double rjcdkyezc = gxckydrjye - gxdkydrjye;
        System.out.println("日均存贷款余额之差:"+rjcdkyezc);
		String date2 = CommonFunctions.dateModifyD(date, -30);//往前数30天
        //获取存款和贷款每天的余额之差，默认存款和贷款的条数相对应
		String hsql = "select nvl((t.item_amount-u.item_amount),0) as item_amount from ftp_mid_item t " +
				"left join ftp_mid_item u on t.cur_no = u.cur_no and t.br_no = u.br_no " +
				"and t.item_date=u.item_date and t.ftp_Term=u.ftp_Term and u.item_no='10008' " +
				"where t.item_no='10020' " +
				"and t.ftp_Term = '3' and t.cur_No = '"+currencySelectId+"' and t.br_No = '"+brNo+"' " +
		        "and to_date(t.item_Date, 'yyyymmdd') <= to_date('"+date+"', 'yyyymmdd') " +
		        "and to_date(t.item_Date, 'yyyymmdd') > to_date('"+date2+"', 'yyyymmdd') " +
		        "order by t.item_Date desc";
		List list = df.query1(hsql);
		Double sumrcdkyezc = 0.0;
        for(int i = 0; i < list.size(); i++) {
			Double cdkyezc = Double.valueOf(list.get(i).toString());//日存贷款余额之差
			sumrcdkyezc += (cdkyezc-rjcdkyezc)*(cdkyezc-rjcdkyezc);
		}
        System.out.println("流动性风险修正分子sumrcdkyezc"+sumrcdkyezc);
        //流动性风险修正
        Double ldxfxxz = Math.sqrt(sumrcdkyezc/list.size())/rjcdkyezc;
        return (ldxfxxz.isInfinite() || ldxfxxz.isNaN())?0.0:ldxfxxz;
	}
	
	
	
	/**
	 * 期限风险修正： 期限不匹配的风险由总行承担
              一年期国债与1年期存款利率的差值
	 * @return
	 * @throws ParseException 
	 */
	public static Double getQxfxxz(String brNo, String date) throws ParseException {
        DaoFactory df = new DaoFactory();
		
        //往前数第30天
        String before = CommonFunctions.dateModifyD(date, -30);//往前数30天
		Double gzsyl = 0.0;
		//一年期国债前30天的均值
		String hsql = "select nvl(sum(stock_yield),0),count(*) from ftp_stock_yield where stock_term = '1' " +
				"and to_date(stock_Date, 'yyyymmdd') > to_date('"+before+"', 'yyyymmdd') " +
				"and to_date(stock_Date, 'yyyymmdd') <= to_date('"+date+"', 'yyyymmdd')";
		List list1 = df.query1(hsql);
		Iterator it1 = list1.iterator();
	    while (it1.hasNext()) {
	        Object[] o = (Object[]) it1.next();
	        int count = Integer.valueOf(o[1].toString());
	        if (count != 0) gzsyl = Double.valueOf(o[0].toString())/count;
	    }
	    //1年期的存款产品
		String prdtNo = "'P2004','P2013','P2017','P2020','P2023','P2028','P2034','P2041','P2045','P2048','P2051'";
		//1年期的存款产品加权平均利率
		String brSql = LrmUtil.getBrSql(brNo);
		Double ckcpjqpjll = getWeightedAveRate(brSql, date, prdtNo, "3", -12, false);//年度月均
		System.out.println("1年期国债的平均投资收益率:"+gzsyl);
		System.out.println("1年期的存款产品加权平均利率:"+ckcpjqpjll);
		//Double qxfxcs = gzsyl - ckcpjqpjll;
		Double qxfxcs = ckcpjqpjll - gzsyl;
		return (qxfxcs.isInfinite() || qxfxcs.isNaN())?0.0:qxfxcs;
	}
//	/**
//	 * 年度月均余额
//	 * 年平均=12个月平均余额之和÷12={(年初+年末)÷2+1月+2月+……+11月}÷12 
//                   季平均={(季初+季末)÷2+前面两个月}÷3 
//                   月平均=(月初+月末)÷2
//	 * @param currencySelectId
//	 * @param brNo
//	 * @param lastYear
//	 * @param itemNo
//	 * @return
//	 */
//	public static Double getYMAverage(String currencySelectId, String brNo, int lastYear, String itemNo) {
//		DaoFactory df = new DaoFactory();
//		int beforeLastYear = lastYear-1;
//		//获取年初余额，即前年12月月末余额
//		String hsql = "from FtpMidItem where curNo = '" + currencySelectId
//		+ "' and brNo = '" + brNo + "' and itemDate like '" + beforeLastYear + "12%' and ftpTerm = '5' and itemNo='"+itemNo+"'";
//		FtpMidItem ftpMidItem = (FtpMidItem)df.getBean(hsql, null);
//		Double ncye = ftpMidItem ==null ? 0.0 : ftpMidItem.getItemAmount();
//		//获取年末余额，即去年12月月末余额
//		hsql = "from FtpMidItem where curNo = '" + currencySelectId
//		+ "' and brNo = '" + brNo + "' and itemDate like '" + lastYear + "12%' and ftpTerm = '5' and itemNo='"+itemNo+"'";
//		ftpMidItem = (FtpMidItem)df.getBean(hsql, null);
//		Double nmye = ftpMidItem ==null ? 0.0 : ftpMidItem.getItemAmount();
//		//获取1-11月余额的和
//		hsql = "select sum(item_Amount) from Ftp_Mid_Item where cur_No = '" + currencySelectId
//		+ "' and br_No = '" + brNo + "' and item_Date like '" + lastYear + "%' and ftp_Term = '5' and item_No='"+itemNo+"' and rownum <12 order by item_date";
//		List list = df.query1(hsql);
//		Double sum11 = 0.0;
//		if (list != null && list.get(0) != null) {
//			sum11 = Double.valueOf(list.get(0).toString());
//		}
//		Double nypjye = (ncye/2+nmye/2+sum11)/12;
//		System.out.println(""+itemNo+"年度月均余额：("+ncye+"/2+"+nmye+"/2+"+sum11+")/12");
//		return (nypjye.isNaN()?0.0:nypjye;
//	}
	
	/**
	 * 年度月均余额，从上个月往前数12个月的月底余额的和/12
	 * @param currencySelectId
	 * @param brNo
	 * @param date
	 * @param itemNo
	 * @return
	 */
	public static Double getYMAverage(String currencySelectId, String brNo, String date, String itemNo) {
		DaoFactory df = new DaoFactory();
		Double returnValue = 0.0;
		String date2 = CommonFunctions.dateModifyM(date, -12);//往前数12个月
		String hsql = "select nvl(sum(item_amount),0), count(*) from Ftp_Mid_Item where item_No = '"+itemNo+"' " +
				"and ftp_Term = '3' and cur_No = '"+currencySelectId+"' and br_No = '"+brNo+"' " +
		        "and to_date(item_Date, 'yyyymmdd') <= to_date('"+date+"', 'yyyymmdd') " +
		        "and to_date(item_Date, 'yyyymmdd') > to_date('"+date2+"', 'yyyymmdd')";
		//期限为5-月，取前12个月的数据
		
		List list = df.query1(hsql);
		Object obj = list.get(0);
		Object[] o = (Object[])obj;
		returnValue = Double.valueOf(o[0].toString())/Double.valueOf(o[1].toString());
		return (returnValue.isInfinite() || returnValue.isNaN()) ? 0.0 : returnValue;
	}
	
	/**
	 * 月度日均余额，从昨日前数30天的月底余额的和/30
	 * @param currencySelectId
	 * @param brNo
	 * @param date
	 * @param itemNo
	 * @return
	 */
	public static Double getMDAverage(String currencySelectId, String brNo, String date, String itemNo) {
		DaoFactory df = new DaoFactory();
		Double returnValue = 0.0;
		String date2 = CommonFunctions.dateModifyD(date, -30);//往前数30天
		String hsql = "select nvl(sum(item_amount),0), count(*) from Ftp_Mid_Item where item_No = '"+itemNo+"' " +
				"and ftp_Term = '3' and cur_No = '"+currencySelectId+"' and br_No = '"+brNo+"' " +
		        "and to_date(item_Date, 'yyyymmdd') <= to_date('"+date+"', 'yyyymmdd') " +
		        "and to_date(item_Date, 'yyyymmdd') > to_date('"+date2+"', 'yyyymmdd')";
		//期限为0-天，取前数30天的数据
		
		List list = df.query1(hsql);
		Object obj = list.get(0);
		Object[] o = (Object[])obj;
		returnValue = Double.valueOf(o[0].toString())/Double.valueOf(o[1].toString());
		return (returnValue.isInfinite() || returnValue.isNaN()) ? 0.0 : returnValue;
	}
	/**
	 * 年度累计增加额月均余额
	 * 科目号6开头的，年初总账会清零，所以取数不能超过年初
	 * @param currencySelectId
	 * @param brNo
	 * @param lastYear
	 * @param itemNo
	 * @return
	 */
	public static Double getYMRaiseAverage6(String currencySelectId, String brNo, String date, String itemNo) {
		//"SELECT to_char(last_day(to_date('20080902','yyyymmdd' )),'yyyymmdd') FROM DUAL";
		DaoFactory df = new DaoFactory();
		Double returnValue = 0.0;
		String hsql = "from FtpMidItem t where t.itemNo = '"+itemNo+"' and t.ftpTerm = '5' " +
				"and t.curNo = '"+currencySelectId+"' and t.brNo = '"+brNo+"' " +
				"and to_date(t.itemDate, 'yyyymmdd') <= to_date('"+date+"', 'yyyymmdd')" +
				"order by t.itemDate desc";//期限为5-月
		List<FtpMidItem> list = df.query(hsql, null);
		if(list == null || list.size() == 0) {//如果没有数据，或者只有一条记录，则直接返回0(因为要获取每月的增量)
			return 0.0;
		} 
		FtpMidItem ftpMidItem = list.get(0);
		//(上个月余额-前13个月的月底余额)/12
		returnValue = ftpMidItem.getItemAmount()/(Long.valueOf(date)%10000/100)*12;
		System.out.println(list.get(0).getItemName()+"年度累计增加额月均余额："+returnValue);
		return (returnValue.isInfinite() || returnValue.isNaN()) ? 0.0 : returnValue;
	}
	
	/**
	 * 年度累计增加额月均余额
	 * 获取前12个月的每月累计增加额/12*12 = (当前月余额-前13个月的月底余额)/月份差*12
	 * @param currencySelectId
	 * @param brNo
	 * @param date
	 * @param itemNo
	 * @return
	 */
	public static Double getYMRaiseAverage(String currencySelectId, String brNo, String date, String itemNo) {
		//"SELECT to_char(last_day(to_date('20080902','yyyymmdd' )),'yyyymmdd') FROM DUAL";
		String date2 = CommonFunctions.dateModifyM(date, -13);//往前数13个月
		DaoFactory df = new DaoFactory();
		Double returnValue = 0.0;
		String hsql = "from FtpMidItem t where t.itemNo = '"+itemNo+"' and t.ftpTerm = '5' " +
				"and t.curNo = '"+currencySelectId+"' and t.brNo = '"+brNo+"' " +
				"and to_date(t.itemDate, 'yyyymmdd') <= to_date('"+date+"', 'yyyymmdd')" +
				"and to_date(t.itemDate, 'yyyymmdd') > to_date('"+date2+"', 'yyyymmdd')" +
				"order by t.itemDate desc";//期限为5-月，取前13个月的数据
		List<FtpMidItem> list = df.query(hsql, null);
		if(list == null || list.size() <= 1) {//如果没有数据，或者只有一条记录，则直接返回0(因为要获取每月的增量)
			return 0.0;
		}
		FtpMidItem ftpMidItem1 = list.get(0);//当月
		FtpMidItem ftpMidItem2 = list.get(list.size() - 1);
		int months = CommonFunctions.monthsSubtract(Long.valueOf(ftpMidItem1.getItemDate()), Long.valueOf(ftpMidItem2.getItemDate()));
		System.out.println("月份差：months"+months);
		//(当前月余额-前13个月的月底余额)/月份差*12
		returnValue = (ftpMidItem1.getItemAmount() - ftpMidItem2.getItemAmount())/months*12;
		System.out.println(list.get(0).getItemName()+"年度累计增加额月均余额："+returnValue);
		return (returnValue.isInfinite() || returnValue.isNaN()) ? 0.0 : returnValue;
	}
	/**
	 * 获取某类产品的加权实际平均利率--月均或日均
	 * @param brSql 已经拼接好的机构查询条件
	 * @param date
	 * @param prdtNo 产品编号sql用字符串编码 ： 'prdtNo1','prdtNo2'...
	 * @param termType 期限
	 * @param num 往前数的日期，例 num=-12，即往前数12个月 
	 * @param isCrossYear 是否跨年
	 * @return
	 */
	public static Double getWeightedAveRate(String brSql, String date, String prdtNo, String termType, Integer num, boolean isCrossYear) {
		DaoFactory df = new DaoFactory();
		String date2 = CommonFunctions.dateModifyM(date, num);//往前数num月
		String dateSql = "";
		if(isCrossYear){//如果是跨年，则截止到今年年初
			if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
		    	date2 = date2.substring(0,4)+"1231";
		}
		dateSql = " and to_date(cyc_date, 'yyyyMMdd') > to_date('"+date2+"', 'yyyyMMdd')";
		
		//先获取总的余额
		String hsql1 = "select nvl(sum(bal),0.0) from ftp_averate_basic where " +
	       "term_type = '"+termType+"' and br_no "+brSql+" " +
	       	"and prdt_no in ("+prdtNo+")" +
		   "and to_date(cyc_date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') " +dateSql;
		List yeList = df.query1(hsql1);
		Double sumYe = Double.valueOf(yeList.get(0).toString());
		if(sumYe == 0.0) return 0.0;
		//加权平均
		String hsql2 = "select nvl(sum(bal * rate)/"+sumYe+",0.0) from ftp_averate_basic where " +
	       "term_type = '"+termType+"' and br_no "+brSql+" " +
	       	"and prdt_no in ("+prdtNo+")" +
		   "and to_date(cyc_date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') " +dateSql;
		List jqyeList = df.query1(hsql2);
		Double returnValue = Double.valueOf(jqyeList.get(0).toString());
		
		return (returnValue.isInfinite() || returnValue.isNaN()) ? 0.0 : returnValue;
	}
	/**
	 * 获取某类存贷款产品的月均余额或者日均余额
	 * @param currencySelectId
	 * @param brSql 已经拼接好的机构查询条件
	 * @param date
	 * @param prdtNo 产品编号
	 * @param termType 期限
	 * @param num 往前数的月份，例： num=-12，即往前数12个月
	 * @param isCrossYear 是否跨年
	 * @return
	 */
	public static Double getAverageAmount(String brSql, String date, String prdtNo, String termType, Integer num, boolean isCrossYear) {
		DaoFactory df = new DaoFactory();
		String date2 = CommonFunctions.dateModifyM(date, num);//往前数num月
		String dateSql = "";
		if(isCrossYear){//如果是跨年，则截止到今年年初
			if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
		    	date2 = date2.substring(0,4)+"1231";
		}
		dateSql = " and to_date(cyc_date, 'yyyyMMdd') > to_date('"+date2+"', 'yyyyMMdd')";
		String hsql1 = "select sum(bal), count(*) from ftp_averate_basic where " +
	        "term_type = '"+termType+"' and br_no "+brSql+" " +
	        "and prdt_no in ("+prdtNo+") " +
	       	"and to_date(cyc_date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') "+dateSql;
		System.out.println("hsql"+hsql1);
		List list = df.query1(hsql1);
		Object obj = list.get(0);
	 	Object[] o = (Object[])obj;
		String[] prdtNos = prdtNo.split(",");
		int prdtNum = prdtNos.length;//产品数量
		String[] brNos = brSql.split(",");
		int brNum = brNos.length;//机构数量
		
		int dateNum = Integer.valueOf(o[1].toString())/(prdtNum*brNum);//汇总了多少期的数据，--默认所有产品每期都进行了汇总
		if(dateNum == 0){
			return 0.0;
		}
		Double returnValue = Double.valueOf(o[0].toString())/dateNum;//求平均值
		return (returnValue.isInfinite() || returnValue.isNaN()) ? 0.0 : returnValue;
	}
	/**
	 * 多资金池：获取国债期望收益率=(I(bond)-I(deposit)) 
	 * (I(bond)=加权1至5年期国债收益率
	 * I(deposit)=加权1至5年期存款实际利率
	 * @param brNo
	 * @param date
	 * @param prdtNo
	 * @return
	 * @throws ParseException
	 */
	public static Double getGzqwsyl(String brNo, String date) throws ParseException {
		//国债平均收益率(获取前90的平均)
		Map<String, Double> map = FtpUtil.getGzpjsyl(-90, date);
		Double gzpjsyl1 = map.get("1");//一年期
		Double gzpjsyl2 = map.get("2");//二年期
		Double gzpjsyl3 = map.get("3");//三年期
		Double gzpjsyl4 = map.get("5");//五年期
		System.out.println("一年期国债平均收益率"+gzpjsyl1);
		System.out.println("二年期国债平均收益率"+gzpjsyl2);
		System.out.println("三年期国债平均收益率"+gzpjsyl3);
		System.out.println("五年期国债平均收益率"+gzpjsyl4);
		String brSql = LrmUtil.getBrSql(brNo);
		//一年期定期存款年度月均余额
		Double dqckndyjey1 = getAverageAmount(brSql, date, "'P2004','P2013','P2017','P2020','P2023','P2028','P2034','P2041','P2045','P2048','P2051'", "3", -12, false);
		//二年期定期存款年度月均余额
		Double dqckndyjey2 = getAverageAmount(brSql, date, "'P2005','P2014','P2035','P2042'", "3", -12, false);
		//三年期定期存款年度月均余额
		Double dqckndyjey3 = getAverageAmount(brSql, date, "'P2006','P2015','P2018','P2021','P2024','P2029','P2036','P2043','P2046','P2049','P2052'", "3", -12, false);
		//五年期定期存款年度月均余额
		Double dqckndyjey4 = getAverageAmount(brSql, date, "'P2007','P2016','P2019','P2022','P2025','P2030','P2037','P2044','P2047','P2050','P2053'", "3", -12, false);
		System.out.println("一年期定期存款年度月均余额"+dqckndyjey1);
		System.out.println("二年期定期存款年度月均余额"+dqckndyjey2);
		System.out.println("三年期定期存款年度月均余额"+dqckndyjey3);
		System.out.println("五年期定期存款年度月均余额"+dqckndyjey4);
		//加权1至5年期国债收益率
		Double jqgzsyl = (gzpjsyl1*dqckndyjey1+gzpjsyl2*dqckndyjey2+gzpjsyl3*dqckndyjey3+gzpjsyl4*dqckndyjey4)/(dqckndyjey1+dqckndyjey2+dqckndyjey3+dqckndyjey4);
		//1至5年期的存款产品
		String prdtNo = "'P2004','P2005','P2013','P2014','P2017','P2020','P2023','P2028','P2034','P2035'," +
				"'P2041','P2042','P2045','P2048','P2051','P2006','P2007','P2015','P2016','P2018','P2019'," +
				"'P2021','P2022','P2024','P2025','P2029','P2030','P2036','P2037','P2043','P2044','P2046'," +
				"'P2047','P2049','P2050','P2052','P2053'";
		//1至5年期的存款产品加权平均利率
		Double ckcpjqpjll = getWeightedAveRate(brSql, date, prdtNo, "3", -12, false);//年度月均
		System.out.println("加权1至5年期国债收益率"+jqgzsyl);
		System.out.println("1至5年期的存款产品加权平均利率"+ckcpjqpjll);
		//Double gzqwsyl = jqgzsyl - ckcpjqpjll;
		Double gzqwsyl = ckcpjqpjll - jqgzsyl;
		return (gzqwsyl.isInfinite() || gzqwsyl.isNaN()) ? 0.0 : gzqwsyl;
	}
	/**
	 * 国债收益率
	 * 获取<=date最近一天的国债收益率
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getGzsyl(String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
		
        String hsql = "select stock_Yield, stock_Term from Ftp_Stock_Yield " +
        		"where stock_Date = (select max(stock_Date) from Ftp_Stock_Yield where to_date(stock_Date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd'))";
        List list = df.query1(hsql);
        if(list==null || list.size()==0){
        	return null;
        }
        for(int i = 0; i < list.size(); i++) {
        	Object object = list.get(i);
            Object[] obj = (Object[])object;
            double yield = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
        	map.put(obj[1].toString(), yield);
        }
        return map;
	}
	/**
	 * 国债平均收益率
	 * 获取前num天的不同期限的平均国债收益率
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getGzpjsyl(int num, String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
		
		//往前数第num天
		String before = CommonFunctions.dateModifyD(date, num);
		
		Double yield = 0.0;
        String hsql = "select count(*), sum(stock_Yield), stock_Term from Ftp_Stock_Yield " +
        		"where to_date(stock_Date,'yyyymmdd') > to_date('"+before+"','yyyymmdd') " +
        		"and to_date(stock_Date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') group by stock_Term";
        List list = df.query1(hsql);
        
        if(list.size()==0){//如果目标时段区间内没有录入国债数据则直接使用20121029往前的数据
        	date="20121029";
        	before = CommonFunctions.dateModifyD(date, num);
        	hsql = "select count(*), sum(stock_Yield), stock_Term from Ftp_Stock_Yield " +
    		"where to_date(stock_Date,'yyyymmdd') > to_date('"+before+"','yyyymmdd') " +
    		"and to_date(stock_Date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') group by stock_Term";
        	list = df.query1(hsql);
        }
        
        Iterator it = list.iterator();
		try{
		    while (it.hasNext()) {
		        Object[] o = (Object[]) it.next();
		        int count = Integer.valueOf(o[0].toString());
		        if (count != 0) {
		        	yield = o[1] == null ? 0.0 : Double.valueOf(o[1].toString())/count;
		        	map.put(o[2].toString(), yield.isNaN()?0.0:yield);
		        }
		    }
		}catch (Exception e) {
	    	e.printStackTrace();
	    }
        return map;
	}
	/**
	 * shibor利率
	 * 获取<=date最近一天的shibor利率
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getShiborRate(String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
		
        String hsql = "select SHIBOR_RATE, SHIBOR_TERM from FTP_SHIBOR where " +
        		"SHIBOR_DATE = (select max(SHIBOR_DATE) from FTP_SHIBOR where to_date(SHIBOR_DATE,'yyyymmdd') <= to_date('"+date+"','yyyymmdd'))";
        List list = df.query1(hsql);
        if(list==null || list.size()==0){
        	return null;
        }
        for(int i = 0; i < list.size(); i++) {
        	Object object = list.get(i);
            Object[] obj = (Object[])object;
            double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
        	map.put(obj[1].toString(), rate);
        }
        return map;
	}
	/**
	 * shibor平均利率
	 * 获取前num天的不同期限的平均shibor利率
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getShiborRate(int num, String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
		
		//往前数第num天
		String before = CommonFunctions.dateModifyD(date, num);
		
		Double rate = 0.0;
        String hsql = "select count(*), sum(SHIBOR_RATE), SHIBOR_TERM from FTP_SHIBOR where " +
        		"to_date(SHIBOR_DATE,'yyyymmdd') > to_date('"+before+"','yyyymmdd') " +
        		"and to_date(SHIBOR_DATE,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') group by SHIBOR_TERM";
        List list = df.query1(hsql);
        if(list==null || list.size()==0){
        	return null;
        }
        Iterator it = list.iterator();
		try{
		    while (it.hasNext()) {
		        Object[] o = (Object[]) it.next();
		        int count = Integer.valueOf(o[0].toString());
		        if (count != 0) {
		        	rate = o[1] == null ? 0.0 : Double.valueOf(o[1].toString())/count;
		        	map.put(o[2].toString(), rate.isNaN()?0.0:rate);
		        }
		    }
		}catch (Exception e) {
	    	e.printStackTrace();
	    }
        return map;
	}
	/**
	 * 金融债平均收益率
	 * 获取前num天的不同期限的平均金融债收益率
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getJrzpjsyl(int num, String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
		
		//往前数第num天
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = date2Calendar(sdf.parse(date));
		cal.add(Calendar.DATE, num);
		String before = sdf.format(cal.getTime());

		Double yield = 0.0;
        String hsql = "select count(*), sum(FINANCIAL_RATE), FINANCIAL_TERM from FTP_FINANCIAL_RATE where " +
        		"to_date(FINANCIAL_DATE,'yyyymmdd') > to_date('"+before+"','yyyymmdd') " +
        		"and to_date(FINANCIAL_DATE,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') group by FINANCIAL_TERM";
        List list = df.query1(hsql);
        if(list.size()==0){//如果目标时段区间内没有录入金融债数据则直接使用20121030往前的数据
        	date="20121030";
        	before = CommonFunctions.dateModifyD(date, num);
        	hsql = "select count(*), sum(FINANCIAL_RATE), FINANCIAL_TERM from FTP_FINANCIAL_RATE where " +
    		"to_date(FINANCIAL_DATE,'yyyymmdd') > to_date('"+before+"','yyyymmdd') " +
    		"and to_date(FINANCIAL_DATE,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') group by FINANCIAL_TERM";
        	list = df.query1(hsql);
        }
        Iterator it = list.iterator();
		try{
		    while (it.hasNext()) {
		        Object[] o = (Object[]) it.next();
		        int count = Integer.valueOf(o[0].toString());
		        if (count != 0) {
		        	yield = o[1] == null ? 0.0 : Double.valueOf(o[1].toString())/count;
		        	map.put(o[2].toString(), yield.isNaN()?0.0:yield);
		        }
		    }
		}catch (Exception e) {
	    	e.printStackTrace();
	    }
        return map;
	}
	
	/*
	  * 获取公共利率的期限
	  */
	 public static String getRateTermType(String id){
		 String name="";
		 if (id == null || "null".equalsIgnoreCase(id) || "".equals(id))
				return name;
		if(id.equals("2D1")){        name = "一天通知";
		}else if(id.equals("2D7")){        name = "七天通知";
		}else if(id.equals("2M0")){        name = "活期";
		}else if(id.equals("2M3")) { name = "三个月";
		}else if(id.equals("2M6")){ name = "六个月";
		}else if(id.equals("2Y1")){ name = "一年";
		}else if(id.equals("2Y2")){ name = "二年";
		}else if(id.equals("2Y3")){name = "三年";
		}else if(id.equals("2Y5")){name = "五年";
		}else if(id.equals("1M1")){name = "六个月以内(含)";
		}else if(id.equals("1M2")){name = "六个月至一年(含)";
		}else if(id.equals("1Y1")){name = "一年至三年(含)";
		}else if(id.equals("1Y2")){name = "三年至五年(含)";
		}else if(id.equals("1Y3")){name = "五年以上";
		}
		 return name;
	 }
	 
	 /*
	  * 获取国债收益率的期限
	  */
	 public static String getStockTermType(String id){
		 String name="";
		 if (id == null || "null".equalsIgnoreCase(id) || "".equals(id))
				return name;
		if(id.equals("1")){       name = "1年";
		}else if(id.equals("2")){ name = "2年";
		}else if(id.equals("3")){ name = "3年";
		}else if(id.equals("5")){ name = "5年";
		}else if(id.equals("7")){ name = "7年";
		}else if(id.equals("10")){name = "10年";
		}else if(id.equals("15")){name = "15年";
		}else if(id.equals("20")){name = "20年";
		}else if(id.equals("30")){name = "30年";
		}
		 return name;
	 }
	 
	 /*
	  * 根据期限获取关键利率点所在的位置
	  */
	 public static int getKeyPoint(String curveType, String term){
		 int num=0;
		 if (term == null || "null".equalsIgnoreCase(term) || "".equals(term))
				return num;
		 if (curveType.equals("1")) {
			 if(term.equals("0M")){       num = 0;
			 }else if(term.equals("3M")){ num = 4;
			 }else if(term.equals("6M")){ num = 5;
			 }else if(term.equals("1Y")){ num = 6;
			 }else if(term.equals("2Y")){ num = 7;
			 }else if(term.equals("3Y")){num = 8;
		  	 }else if(term.equals("5Y")){num = 9;
			 }else if(term.equals("7Y")){num = 10;
		 	 }else if(term.equals("10Y")){num = 11;
			 }else if(term.equals("15Y")){num = 12;
			 }else if(term.equals("20Y")){num = 13;
			 }else if(term.equals("30Y")){num = 14;
			 }
		 }else if (curveType.equals("2")) {
			 if(term.equals("O/N")){       num = 0;
			 }else if(term.equals("1W")){ num = 1;
			 }else if(term.equals("2W")){ num = 2;
			 }else if(term.equals("1M")){ num = 3;
			 }else if(term.equals("3M")){ num = 4;
			 }else if(term.equals("6M")){num = 5;
		  	 }else if(term.equals("9M")){num = 6;
			 }else if(term.equals("1YS")){num = 7;
		 	 }else if(term.equals("1YG")){num = 8;
		 	 }else if(term.equals("2Y")){num = 9;
			 }else if(term.equals("3Y")){num = 10;
			 }else if(term.equals("5Y")){num = 11;
			 }else if(term.equals("7Y")){num = 12;
			 }else if(term.equals("10Y")){num = 13;
			 }else if(term.equals("15Y")){num = 14;
			 }else if(term.equals("20Y")){num = 15;
			 }else if(term.equals("30Y")){num = 16;
			 }
		 }
		
		 return num;
	 }
	 /*
	  * 根据期限类型获取期限名称
	  */
	 public static String getTermNameByType(String curveType, String term){
		 String termName = "";
		 if (term == null || "null".equalsIgnoreCase(term) || "".equals(term))
				return termName;
		 if (curveType.equals("01")) {
			 if(term.equals("0M")){       termName = "3个月以内";
			 }else if(term.equals("3M")){ termName = "3个月";
			 }else if(term.equals("6M")){ termName = "6个月";
			 }else if(term.equals("1Y")){ termName = "1年";
			 }else if(term.equals("2Y")){ termName = "2年";
			 }else if(term.equals("3Y")){termName = "3年";
		  	 }else if(term.equals("5Y")){termName = "5年";
			 }else if(term.equals("7Y")){termName = "7年";
		 	 }else if(term.equals("10Y")){termName = "10年";
			 }else if(term.equals("15Y")){termName = "15年";
			 }else if(term.equals("20Y")){termName = "20年";
			 }else if(term.equals("30Y")){termName = "30年";
			 }
		 }else if (curveType.equals("02")) {
			 if(term.equals("O/N")){       termName = "隔夜";
			 }else if(term.equals("1W")){ termName = "7天";
			 }else if(term.equals("2W")){ termName = "14天";
			 }else if(term.equals("1M")){ termName = "1个月";
			 }else if(term.equals("3M")){ termName = "3个月";
			 }else if(term.equals("6M")){termName = "6个月";
		  	 }else if(term.equals("9M")){termName = "9个月";
			 }else if(term.equals("1YS")){termName = "1年shibor";
		 	 }else if(term.equals("1YG")){termName = "1年国债";
		 	 }else if(term.equals("2Y")){termName = "2年";
			 }else if(term.equals("3Y")){termName = "3年";
			 }else if(term.equals("5Y")){termName = "5年";
			 }else if(term.equals("7Y")){termName = "7年";
			 }else if(term.equals("10Y")){termName = "10年";
			 }else if(term.equals("15Y")){termName = "15年";
			 }else if(term.equals("20Y")){termName = "20年";
			 }else if(term.equals("30Y")){termName = "30年";
			 }
		 }
		
		 return termName;
	 }
	 /*
	  * 根据业务编号获取业务名称
	  */
	 public static String getBusinessName(String businessNo){
		 if (businessNo == null || "null".equalsIgnoreCase(businessNo) || "".equals(businessNo))
				return "";
		 String name = "";
		 String[] business_names={"贷款","存放中央银行款项","存放同业","拆放同业","投资","逆回购","现金","其他资产",
		 "活期存款","定期存款","其他存款","同业存放","同业拆入","向央行借款","贴现负债","正回购","其他负债"};

		 if(businessNo.equals("YW101")){       name = business_names[0];
		 }else if(businessNo.equals("YW102")){ name = business_names[1];
		 }else if(businessNo.equals("YW103")){ name = business_names[2];
		 }else if(businessNo.equals("YW104")){ name = business_names[3];
		 }else if(businessNo.equals("YW105")){ name = business_names[4];
		 }else if(businessNo.equals("YW106")){ name = business_names[5];
	  	 }else if(businessNo.equals("YW107")){ name = business_names[6];
	  	 }else if(businessNo.equals("YW108")){ name = business_names[7];
	  	 }else if(businessNo.equals("YW201")){ name = business_names[8];
	  	 }else if(businessNo.equals("YW202")){ name = business_names[9];
	  	 }else if(businessNo.equals("YW203")){ name = business_names[10];
	  	 }else if(businessNo.equals("YW204")){ name = business_names[11];
	  	 }else if(businessNo.equals("YW205")){ name = business_names[12];
	  	 }else if(businessNo.equals("YW206")){ name = business_names[13];
	  	 }else if(businessNo.equals("YW207")){ name = business_names[14];
	  	 }else if(businessNo.equals("YW208")){ name = business_names[15];
	  	 }else if(businessNo.equals("YW209")){ name = business_names[16];
	  	 }
		 return name;
	 }
	 
	 /**
	  * 三次样条插值
	  * 
	  * @param x
	  * @param y
	  * @return
	  */
	 public static Double[] Spline(Double[] x, Double[] y, int nLength)
	 {
		    Double xpoint[],ypoint[];
		    double h[],a[],c[],d[],b[],s2[],s1,s;
		    double m;
		    
		    int N=x.length,i=0;
		    xpoint=new Double[nLength];
	    	ypoint=new Double[nLength];
	        
	        h=new double[N];
	        a=new double[N];
	        c=new double[N];
	        d=new double[N];
	        b=new double[N];
	        //s1=new double[N];
	        s2=new double[N];
	        
	        for(int k=0;k<N-1;k++)
	        {
	        	h[k]=x[k+1]-x[k];	
	        }
	        a[1]=2*(h[0]+h[1]);
	        
	        for(int k=2;k<N-1;k++)
	        {
	        	a[k]=2*(h[k-1]+h[k])-h[k-1]*h[k-1]/a[k-1];
	        }
	        for(int k=1;k<N;k++)
	        {
	        	c[k]=(y[k]-y[k-1])/h[k-1];
	        }
	        for(int k=0;k<N-1;k++)
	        {
	        	d[k]=6*(c[k+1]-c[k]);
	        }
	        b[1]=d[1];
	        for(int k=2;k<N-1;k++)
	        {
	        	b[k]=d[k]-b[k-1]*h[k-1]/a[k];
	        }
	        /*s2[N-1]=b[N-1]/a[N-1];
	        for(int k=N-2;k>0;k--)
	        {
	        	s2[k]=(b[k]-h[k]*s2[k+1])/a[k];
	        }*/
	        s2[0]=0;s2[N-1]=0;
	        for(int k=0;k<N-1;k++)
	        {
	        	for(m=x[k];m<=x[k+1];m++)
	        	{
	        		xpoint[i]=m;
	        		 if (i > 0 && xpoint[i].doubleValue() == xpoint[i - 1].doubleValue()) {
	        			continue;
	        		}
	        		s1=c[k+1]-s2[k+1]*h[k]/6-s2[k]*h[k]/3;
	        		//s=y[k]+s1*(m-x[k])+s2[k]*(m-x[k])*(m-x[k])+(s2[k+1]-s2[k])*(m-x[k])*(m-x[k])*(m-x[k])/(6*h[k]);
	        		s=y[k] + s1 * (m - x[k]) + s2[k] * (m - x[k]) * (m - x[k]) / 2 + (s2[k + 1] - s2[k]) * (m - x[k]) * (m - x[k]) * (m - x[k]) / (6 * h[k]);
	        		ypoint[i]=s;
	        		System.out.println("xpoint["+i+"]"+xpoint[i]);
	        		System.out.println("ypoint["+i+"]"+ypoint[i]);
	        		i++;
	        	}
	        }
	        System.out.println("------------------------------------");
	        return ypoint;
	 }
	 // Date 转换为 Calendar 
	 public static Calendar date2Calendar(Date date) {  
	     if (date == null) {  
	         return null;  
	     }  
	     Calendar calendar = Calendar.getInstance();  
	     calendar.setTime(date);  
	     return calendar;  
	 }
	 
	
	
	 /**
	  * 根据产品编号查询‘产品-期限匹配定价方法关联配置表’FTP_product_method_rel获取其定价方法组合：
	  * <br>具体定价方法编号+参考期限+调整利率+收益率曲线编号+指定利率+固定利差+流动性风险加点+敞口风险加点
	  * @param prdt_no
	  * @param br_no
	  * @return “具体定价方法编号+参考期限+调整利率+收益率曲线编号+指定利率+固定利差+流动性风险加点+敞口风险加点 ”依序构成的字符串数组
	  */ 
	 public static String[] getFTPMethod_byPrdtNo(String prdt_no,String br_no ){
		 String[] result=new String[8];
		 String sql="select method_no,assign_term,Adjust_rate,Curve_no,appoint_rate,Appoint_delta_rate,lr_adjust_rate,eps_adjust_rate from ftp.FTP_product_method_rel" +
		 		" where product_no='"+prdt_no+"'";
		 DaoFactory df = new DaoFactory();
		 List list=df.query1(sql);
		 
		 if(list.size()!=0 && list.get(0)!=null){
			 if(list.size()>1){
				 System.out.println("产品"+prdt_no+"对应"+list.size()+"条配置记录！");
			 }
			 Object obj=list.get(0);
			 Object[] objs=(Object[])obj;
			 result[0]=objs[0].toString();
			 result[1]=objs[1].toString();
			 result[2]=objs[2].toString();
			 result[3]=objs[3].toString();
			 result[4]=objs[4].toString();
			 result[5]=objs[5].toString();
			 result[6]=objs[6].toString();
			 result[7]=objs[7].toString();
		 }else{
			 System.out.println("没有找到产品编号为"+prdt_no+"的ftp定价方法！返回null");
			 return null;
		 }
		 
		 return result;
	 }
	 
    /**
	 * 根据县联社机构编号查询‘产品-期限匹配定价方法关联配置表’FTP_product_method_rel获取其所有产品定价方法组合的map： <br>
	 * 【product_no --> String[]“具体定价方法编号+参考期限+调整利率+收益率曲线编号+指定利率+固定利差+产品编号+流动性风险加点+敞口风险加点 ”依序构成的字符串数组】
	 * @param br_no
	 * @return Map 【product_no,String[]“具体定价方法编号+参考期限+调整利率+收益率曲线编号+指定利率+固定利差+产品编号+流动性风险加点+敞口风险加点 ”依序构成的字符串数组】
	 */
	 public static Map<String, String[]> getMap_FTPMethod(String br_no) {
		 Map<String, String[]> ftp_methodComb_map = new HashMap<String, String[]>();
		 String sql = "select method_no,assign_term,Adjust_rate,Curve_no,appoint_rate,Appoint_delta_rate, product_no,lr_adjust_rate,eps_adjust_rate from ftp.FTP_product_method_rel"
			 + " order by product_no";
		 DaoFactory df = new DaoFactory();
		 List list = df.query1(sql);
		 if (list.size() != 0) {		 
			 for (int i = 0; i < list.size(); i++) {
				 String[] result = new String[9];
				 Object obj = list.get(i);
				 Object[] objs = (Object[]) obj;	
				 result[0] = objs[0].toString();
				 result[1] = objs[1].toString();
				 result[2] = objs[2].toString();
				 result[3] = objs[3].toString();
				 result[4] = objs[4].toString();
				 result[5] = objs[5].toString();
				 result[6] = objs[6].toString();
				 result[7] = objs[7].toString();
				 result[8] = objs[8].toString();
				 ftp_methodComb_map.put(result[6], result);
				 
			}
		 }else{			 
			 System.out.println("没有找到ftp定价方法！返回null");
			 return null;
		 }
		 return ftp_methodComb_map;
	 }
		
	 /**
	  * 根据ftp具体定价方法编号 获取 其定价方法名称
	  * @param method_no
	  * @return
	  */
	 public static String getMethodName_byMethodNo(String method_no){
		 String method_name="";
		 if(method_no==null){
			 return null;
		 }
		 if(method_no.equals("01")){
			 method_name="原始期限匹配法";
		 }else if(method_no.equals("02")){
			 method_name="指定利率法";
		 }else if(method_no.equals("03")){
			 method_name="重定价期限匹配法";
		 }else if(method_no.equals("04")){
			 method_name="现金流法";
		 }else if(method_no.equals("05")){
			 method_name="久期法";
		 }else if(method_no.equals("06")){
			 method_name="利率代码差额法";
		 }else if(method_no.equals("07")){
			 method_name="加权利率法";
		 }else if(method_no.equals("08")){
			 method_name="固定利差法";
		 }else{
			 method_name="未知";
		 }
		 return method_name;
	 }
	 /**
	  * 根据收益率曲线编号 获取 其收益率曲线名称
	  * @param method_no
	  * @return
	  */
	 public static String getCurveName_byCurveNo(String curve_no){
		 String method_name="还未发布";
		 if (curve_no == null) {
			 return method_name;
		 }
		 if(curve_no.equals("无")){
			 return "无";
		 }
		 
		 if(curve_no.equals("0100")){
			 method_name="内部收益率曲线-基准化曲线";
		 }else if(curve_no.equals("0101")){
			 method_name="内部收益率曲线-贷款COF";
		 }else if(curve_no.equals("0102")){
			 method_name="内部收益率曲线-存款VOF";
		 }else if(curve_no.equals("0200_01")){
			 method_name="市场收益率曲线-基准化曲线(1年期以内)";
		 }else if(curve_no.equals("0200_02")){
			 method_name="市场收益率曲线-基准化曲线(1年期以上)";
		 }else if(curve_no.equals("0201_01")){
			 method_name="市场收益率曲线-资产COF(1年期以内)";
		 }else if(curve_no.equals("0201_02")){
			 method_name="市场收益率曲线-资产COF(1年期以上)";
		 }else if(curve_no.equals("0202_01")){
			 method_name="市场收益率曲线-负债VOF(1年期以内)";
		 }else if(curve_no.equals("0202_02")){
			 method_name="市场收益率曲线-负债VOF(1年期以上)";
		 }
		 return method_name;
	 } 
	 /**
	  * 对二维数组进行排序
	  * @param ob 该二维数组
	  * @param order 按第几列进行排序
	  */
	 public static void sort(long[][] ob, final int[] order) {    
		 Arrays.sort(ob, new Comparator<Object>() {    
	            public int compare(Object o1, Object o2) {    
	            	long[] one = (long[]) o1;    
	                long[] two = (long[]) o2;    
	                for (int i = 0; i < order.length; i++) {    
	                    int k = order[i];    
	                    if (one[k] > two[k]) {    
	                        return 1;    
	                    } else if (one[k] < two[k]) {    
	                        return -1;    
	                    } else {    
	                        continue;  //如果按一条件比较结果相等，就使用第二个条件进行比较。  
	                    }    
	                }    
	                return 0;    
	            }    
	        });   
	    }
	 /**
	  * 根据第一列的一个值和第二列的一个值，获取二维数组对应所在行第三列的值
	  * @param Array 二维数组
	  * @param a1 第一列的某值
	  * @param a2第二列的某值
	  */
	 public static long getValueFrom(long[][] arrayStr, int a1, int a2) {
		 for (int m = 0; m < arrayStr.length; m++) {
			 if (arrayStr[m][0] == a1 && arrayStr[m][1] == a2){
				 return arrayStr[m][2];
			 }
		 }
		 return 0;
	 }
	 
	 /**
		 * Description:定价配置结果
		 * 
		 * @param d
		 * @return
		 */
		public static String CastSetResult(Integer id, String obj) {
			if (obj == null || "null".equalsIgnoreCase(obj))
				obj = "";
			if (id == null || "null".equalsIgnoreCase(id.toString())
					|| "".equals(id.toString()))
				return obj;
			switch (id.intValue()) {
			case 0:
				obj = "未配置";
				break;
			case 1:
				obj = "单资金池";
				break;
			case 2:
				obj = "双资金池";
				break;
			case 3:
				obj = "多资金池";
				break;
			case 4:
				obj = "期限匹配";
			}
			return obj;
		}
		/**
		 * Description:根据资金池编号获取其资金池名称
		 * 
		 * @param d
		 * @return
		 */
		public static String CastPool_NameByPool_no(String id, String obj) {
			if (obj == null || "null".equalsIgnoreCase(obj))
				obj = "";
			if (id == null || "null".equalsIgnoreCase(id.toString())
					|| "".equals(id.toString()))
				return obj;
			String sql="select pool_name from ftp_pool_info where pool_no='"+id+"'";
			List list=CommonFunctions.mydao.query1(sql);
			if(list==null||list.size()==0){
				return obj;
			}
			obj=list.get(0).toString();
			/*switch (id.intValue()) {
			case 31:
				obj = "1年以内贷款";
				break;
			case 32:
				obj = "1年期及以上期限贷款";
				break;
			case 33:
				obj = "贴现贷款";
				break;
			case 34:
				obj = "其它资产";
				break;
			case 35:
				obj = "活期储蓄存款";
				break;
			case 36:
				obj = "单位活期存款";
				break;
			case 37:
				obj = "同业活期存款";
				break;
			case 38:
				obj = "统筹住房资金活期存款";
				break;
			case 39:
				obj = "1年以内一般性定期存款";
				break;
			case 310:
				obj = "1年期一般性定期存款";
				break;
			case 311:
				obj = "2年期及以上一般性定期存款";
				break;
			case 312:
				obj = "同业定期存款";
			}*/
			return obj;
		}
		
		/**
		 * 获取项目汇总值某item_no的项目金额值 
		 * @author 
		 * 
		 * @param item_no (String) 项目编号，不可为null
		 * @param br_no (String)机构编号，不可为null
		 * @param cur_no (String)币种号，不可为null
		 * @param riqi (long)目标日期，不可为null；格式为8位字符串;获取当前日期条件下最新的项目金额值
		 * @param term_type (String)周期期限，可为null，表示周期期限任意
		 * @return  cnt(long)笔数， amt (double)金额值
		 * 
		 */
		public static double getItemSum(String item_no,String br_no,String cur_no,String riqi,String term_type) {
			if(item_no==null||br_no==null||cur_no==null||riqi==null){
				System.out.println("输入参数不足，无法获取科目值！");
				return Double.NaN;
			}
			String sql = "select to_acc from ftp.Ftp_Item_To_Acc where item_No = '"+item_no+"'";
			List list = CommonFunctions.mydao.query1(sql);
			String toAcc  = (String)list.get(0);
			System.out.println("对应科目:"+toAcc);
			double amt=0;
			long cnt=0;
			String hsql="";
			if(term_type==null || "".equals(term_type)){
				hsql =  "select ITEM_AMOUNT from("
					+" select row_number() over (order by t.item_date desc) as rownumber,t.* from ftp.ftp_mid_item t "	
					+" where t.item_no = '"+item_no+"'"
					+" and to_date(t.item_date, 'yyyymmdd')<= to_date('"+riqi+"', 'yyyymmdd')";
				if(!toAcc.equals("补录")) hsql += " and t.br_no = '"+br_no+"'";//如果是补录，则不加机构这个查询条件
				hsql += " and t.cur_no = '"+cur_no+"' " +
						"order by t.item_date desc ) " +
						"where rownumber <2";
			}else{
				hsql =  "select ITEM_AMOUNT from("
					+" select row_number() over (order by t.item_date desc) as rownumber,t.* from ftp.ftp_mid_item t "
					+" where t.item_no = '"+item_no+"'"
					+" and to_date(t.item_date, 'yyyymmdd')<= to_date('"+riqi+"', 'yyyymmdd')";
				if(!toAcc.equals("补录")) hsql += " and t.br_no = '"+br_no+"'";//如果是补录，则不加机构这个查询条件
				hsql += " and t.cur_no = '"+cur_no+"'"
					+" and t.ftp_term ='"+term_type+"'"
					+" order by t.item_date desc )"
					+" where rownumber <2";
			}	
			List item_sumList = CommonFunctions.mydao.query1(hsql);
			
			if(item_sumList!=null && item_sumList.size()!=0){
				Object obj=item_sumList.get(0);
				amt=Double.valueOf(obj.toString());
			}
			return amt;
		}
		
		/**
		 * 期限匹配：获取盈利分析日期范围内的定价周期次数
		 * @param dateSql  日期范围sql条件语句
		 * @return
		 */
		public static Integer getQXPPcountTerm(String dateSql){
			String sql0 = "select count(distinct t.wrk_sys_date) from ftp.ftp_qxpp_result t where rownum >= 0 "+dateSql;
			List countTermList = CommonFunctions.mydao.query1(sql0);
			Integer countTerm = Integer.valueOf(countTermList.get(0).toString());//获取实际定价的周期次数
			return  countTerm;
		}
		
	
		
		/**
		 * 解决导出excel中文名乱码的问题
		 * @param s
		 * @return
		 */
	public static String toUtf8String(String fileName) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fileName.length(); i++) {
			char c = fileName.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 根据机构号和机构级别，获取其对应的县联社brno
	 * 
	 * @param brNo
	 * @param manageLvl
	 * @return
	 */
	public static String getXlsBrNo(String brNo, String manageLvl) {
		if (manageLvl.equals("2")) {
			return brNo;
		} else if (manageLvl.equals("1")) {// 如果是1级，获取它的父级即为它对应的县联社
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst)  CommonFunctions.mydao.getBean(hsql, null);
			return brMst.getSuperBrNo();
		} else if (manageLvl.equals("0")) {// 如果是0级，要循环两次获取它的父级的父级即为它对应的县联社
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst)  CommonFunctions.mydao.getBean(hsql, null);
			String hsql2 = "from BrMst where brNo = '" + brMst.getSuperBrNo()
					+ "'";
			brMst = (BrMst)  CommonFunctions.mydao.getBean(hsql2, null);
			return brMst.getSuperBrNo();
		}
		return "";
	}
	
	/**
	 * 贷款产品的存款准备金调整
	 * @return
	 */
	public static Map<Integer, Double> getCkzbjAdjustValue() {
		Map<Integer, Double> ckzbjAdjustMap = new HashMap<Integer, Double>();
		String hsql = "from Ftp1PrdtCkzbjAdjust order by termType";
		List<Ftp1PrdtCkzbjAdjust> list = CommonFunctions.mydao.query(hsql, null);
		for(Ftp1PrdtCkzbjAdjust ftp1PrdtCkzbjAdjust : list) {
			ckzbjAdjustMap.put(ftp1PrdtCkzbjAdjust.getTermType(), ftp1PrdtCkzbjAdjust.getAdjustValue());
		}
		return ckzbjAdjustMap;
	}
	
	/**
	 * 贷款产品的流动性调整
	 * @return
	 */
	public static Map<Integer, Double> getLdxAdjustValue() {
		Map<Integer, Double> ldxAdjustMap = new HashMap<Integer, Double>();
		String hsql = "from Ftp1PrdtLdxAdjust order by termType";
		List<Ftp1PrdtLdxAdjust> list = CommonFunctions.mydao.query(hsql, null);
		for(Ftp1PrdtLdxAdjust ftp1PrdtLdxAdjust : list) {
			ldxAdjustMap.put(ftp1PrdtLdxAdjust.getTermType(), ftp1PrdtLdxAdjust.getAdjustValue());
		}
		return ldxAdjustMap;
	}
	
	/**
	 * 贷款产品的信用风险调整
	 * @return
	 */
	public static Map<String, Double> getIrAdjustValue() {
		Map<String, Double> irAdjustMap = new HashMap<String, Double>();
		String hsql = "from Ftp1PrdtIrAdjust order by custCreditLvl";
		List<Ftp1PrdtIrAdjust> list = CommonFunctions.mydao.query(hsql, null);
		for(Ftp1PrdtIrAdjust ftp1PrdtIrAdjust : list) {
			irAdjustMap.put(ftp1PrdtIrAdjust.getCustCreditLvl(), ftp1PrdtIrAdjust.getAdjustValue());
		}
		return irAdjustMap;
	}
	
	/**
	 * 存贷款产品的提前还款/提前支取调整
	 * @return
	 */
	public static List<Ftp1PrepayAdjust> getPrepayAdjustValue() {
		String hsql = "from Ftp1PrepayAdjust order by assetsType,maxTermType desc";
		List<Ftp1PrepayAdjust> list = CommonFunctions.mydao.query(hsql, null);
		return list;
	}
	
	/**
	 * 存贷款产品的策略调整
	 * @return
	 */
	public static Map<String, Double> getClAdjustValue() {
		Map<String, Double> clAdjustMap = new HashMap<String, Double>();
		String hsql = "from Ftp1PrdtClAdjust order by productNo";
		List<Ftp1PrdtClAdjust> list = CommonFunctions.mydao.query(hsql, null);
		for(Ftp1PrdtClAdjust ftp1PrdtClAdjust : list) {
			clAdjustMap.put(ftp1PrdtClAdjust.getProductNo(), ftp1PrdtClAdjust.getAdjustValue());
		}
		return clAdjustMap;
	}
	
	/**
	  * 计算使用存贷款收益率曲线的产品的FTP调整值
	  * @param businessNo
	  * @param productNo
	  * @param term
	  * @param ckzbjAdjustMap
	  * @param ldxAdjustMap
	  * @param irAdjustMap
	  * @param prepayList
	  * @param clAdjustMap
	  * @return
	  */
	public static double getCdkFtpAdjustValue(String businessNo, String productNo,
			int term, Map<Integer, Double> ckzbjAdjustMap,
			Map<Integer, Double> ldxAdjustMap, Map<String, Double> irAdjustMap,
			List<Ftp1PrepayAdjust> prepayList, Map<String, Double> clAdjustMap) {
		double adjustValue = 0;
		String assetsType = "";// 资产类型,01贷款02存款
		if (businessNo.equals("YW101")) {// 贷款产品
			// 准备金调整
			adjustValue += ckzbjAdjustMap.get((term / 30) < 61 ? (term / 30)
					: 61) == null ? 0 : ckzbjAdjustMap.get((term / 30) < 61 ? (term / 30) : 61);// 转化为月，如果>60M，则获取61M的数据
			// 流动性调整
			adjustValue += ldxAdjustMap.get((term / 30) < 61 ? (term / 30) : 61) == null ? 0
					: ldxAdjustMap.get((term / 30) < 61 ? (term / 30) : 61);// 转化为月，如果>60M，则获取61M的数据
			// 信用风险调整
			adjustValue += irAdjustMap.get("未评级") == null ? 0 : irAdjustMap.get("未评级");// 需要获取客户信用等级
			assetsType = "01";
		} else {
			assetsType = "02";
		}
		// 提前还款/支取调整
		for (Ftp1PrepayAdjust ftp1PrepayAdjust : prepayList) {
			// 资产类型相同，最小值<term<=最大值
			if (ftp1PrepayAdjust.getAssetsType().equals(assetsType)
					&& (term / 30) > ftp1PrepayAdjust.getMinTermType()
					&& (term / 30) <= ftp1PrepayAdjust.getMaxTermType()) {
				adjustValue += ftp1PrepayAdjust.getAdjustValue();// 转化为月，如果>60M，则获取61M的数据
				break;
			}
		}
		adjustValue += clAdjustMap.get(productNo) == null ? 0 : clAdjustMap.get(productNo);// 根据产品获取对应的数据
		return adjustValue;
	}
	
}