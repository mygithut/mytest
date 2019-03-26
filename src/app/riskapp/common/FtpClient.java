package app.riskapp.common;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class FtpClient {

	private static FTPClient ftpClient = new FTPClient();

	/**
	 * ���ӵ�FTP������
	 * 
	 * @param hostname
	 *            ������
	 * @param port
	 *            �˿�
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @return �Ƿ����ӳɹ�
	 * @throws IOException
	 */

	public static boolean connect(String hostname, int port, String username,
			String password) throws IOException {
		ftpClient.connect(hostname, port);
		ftpClient.setControlEncoding("GBK");
		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			if (ftpClient.login(username, password)) {
				return true;
			}
		}
		disconnect();
		return false;
	}

	/**
	 * 
	 * �Ͽ���Զ�̷�����������
	 * 
	 * 
	 * 
	 * @throws IOException
	 */

	public static void disconnect() throws IOException {
		if (ftpClient.isConnected()) {
			ftpClient.disconnect();
		}
	}

	/**
	 * 
	 * ��ȡָ��Ŀ¼�µ��ļ������б�
	 * 
	 * @author HQQW510_64
	 * @param currentDir
	 *            ��Ҫ��ȡ����Ŀ¼�ĵ�ǰĿ¼����
	 * @return ������Ŀ¼�ַ�������
	 */

	public static String[] GetFileNames(String currentDir) {
		String[] dirs = null;
		try {
			if (currentDir == null)
				dirs = ftpClient.listNames();
			else
				dirs = ftpClient.listNames(currentDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dirs;
	}

	/**
	 * 
	 * ��ȡָ��Ŀ¼�µ��ļ���Ŀ¼��Ϣ����
	 * 
	 * @param currentDir
	 *            ָ���ĵ�ǰĿ¼
	 * 
	 * @return ���ص��ļ�����
	 */

	public static FTPFile[] GetDirAndFilesInfo(String currentDir) {
		FTPFile[] files = null;
		try {
			if (currentDir == null)
				files = ftpClient.listFiles();
			else
				files = ftpClient.listFiles(currentDir);
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		return files;
	}

	/**
	 * 
	 * ��ָ��Ŀ¼�´����ļ�
	 * 
	 * @param currentDir
	 *            ָ���ĵ�ǰĿ¼
	 * @param filename
	 *            ָ�����ļ���
	 */

	public static void makeFile(String currentDir, String filename) {
		FTPFile file = null;
		try {
			if (currentDir != null && currentDir.compareTo("") != 0
					&& ftpClient.changeWorkingDirectory(currentDir)) {
				String reTransmitFolderName = filename;
				ftpClient.makeDirectory(reTransmitFolderName);
			}
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}

	}

}
