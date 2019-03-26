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
	 * 连接到FTP服务器
	 * 
	 * @param hostname
	 *            主机名
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 是否连接成功
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
	 * 断开与远程服务器的连接
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
	 * 获取指定目录下的文件名称列表
	 * 
	 * @author HQQW510_64
	 * @param currentDir
	 *            需要获取其子目录的当前目录名称
	 * @return 返回子目录字符串数组
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
	 * 获取指定目录下的文件与目录信息集合
	 * 
	 * @param currentDir
	 *            指定的当前目录
	 * 
	 * @return 返回的文件集合
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
	 * 在指定目录下创建文件
	 * 
	 * @param currentDir
	 *            指定的当前目录
	 * @param filename
	 *            指定的文件名
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
