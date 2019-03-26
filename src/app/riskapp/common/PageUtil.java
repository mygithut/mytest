package app.riskapp.common;

import java.util.List;

public class PageUtil {
	private List list;
	private String pageLine;
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public String getPageLine() {
		return pageLine;
	}
	public void setPageLine(String pageLine) {
		this.pageLine = pageLine;
	}
	public PageUtil(List list, String pageLine) {
		this.list = list;
		this.pageLine = pageLine;
	}
	public PageUtil() {
	}
}
