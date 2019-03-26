package app.riskapp.filetrans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;



public class FtpClient {

	private static FTPClient ftpClient = new FTPClient();

	/**
	 * 连接到FTP服务器
	 * 
	 * @param hostname
	 *            主机名或主机ip
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @return 是否连接成功
	 * @throws IOException
	 */

	public static boolean connect(String hostname, int port, String username,String password){
		try{
			ftpClient.connect(hostname, port);
			ftpClient.setControlEncoding("GBK");
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				if (ftpClient.login(username, password)) {
					System.out.println("ftp连接成功！");
					return true;
				}
			}
			System.out.println("ftp连接失败！");
			disconnect();
			return false;
		}catch(IOException ioe){
			System.out.println("ftp连接失败！");
			ioe.printStackTrace();
			return false;
		}
		
	}

	/**
	 * 
	 * 断开与远程服务器的连接
	 * 
	 * 
	 * 
	 * @throws IOException
	 */

	public static void disconnect() {
		try{
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
				System.out.println("ftp断开成功！");
			}else{
				System.out.println("ftp断开操作时，发现不存在连接！");
			}
		}catch(Exception e){
			System.out.println("ftp断开失败！");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 获取指定目录下的文件名称列表：不包含隐藏文件
	 * 
	 * @param currentDir
	 *            需要获取的远程目录名称--如果参数为null，则以之前指定的远程目录获取
	 * @return 返回子目录或文件名字符串数组【注意文件名是 包含完全路径的字符串】
	 */

	public static String[] GetFileNames_all(String currentDir) {
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
	 * 获取指定目录下的文件名称列表：不包含隐藏文件
	 * 
	 * @param currentDir
	 *            需要获取的远程目录名称--如果参数为null，则以之前指定的远程目录获取
	 * @return 返回子目录或文件名字符串数组【如果指定目录不存在则返回null】
	 */

	public static String[] GetFileNames(String currentDir) {
		FTPFile[] files = null;
		String[] file_names = null;
		try {
			if (currentDir == null){
				files = ftpClient.listFiles();
			}else{
				files = ftpClient.listFiles(currentDir);
				}
			if(files!=null && files.length>0){
				file_names=new String[files.length];
				for(int i=0;i<files.length;i++){
					file_names[i]=files[i].getName();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file_names;
	}

	/**
	 * 
	 * 获取指定目录下的文件与目录信息集合:不含隐藏文件
	 ** <br>--返回的内容转换成String后 是linux下运行【ls -la】命令的返回结果
	 * <br>--例如一行：【-rw-------    1 ftp      ftps         3881 Oct 29 14:25 .viminfo】
	 * @param currentDir
	 *            指定的远程目录--如果参数为null，则以之前指定的远程目录获取
	 * 
	 * @return 返回的文件数组
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
	
	/**
	 * 上传单个文件
	 * 
	 * @param directory   要上传到的远程服务器目录【 完全路径】
	 * @param  filename   上传到FTP服务器后的文件名  
	 * @param uploadFileName   要上传的本地文件名【包含全路径+文件名】
	 * @return 【boolean】true:成功，false:失败
	 */
	public static boolean upload(String directory, String filename, String uploadFileName) {
		try {
			FileInputStream in=new FileInputStream(new File(uploadFileName));     
			ftpClient.changeWorkingDirectory(directory);      
			ftpClient.storeFile(filename, in);               
			in.close();
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 下载单个文件
	 * 
	 * @param remotePath
	 *            要下载的远程服务器目录【 完全路径】
	 * @param downloadFileName
	 *            要下载的文件名
	 * @param saveFile
	 *            存在本地的 文件全路径(绝对路径+文件名<因此支持下载文件的同时改名>)
	 * @param sftp
	 */
	public static boolean download(String remotePath, String downloadFileName,String saveFile) {
		try {
			ftpClient.changeWorkingDirectory(remotePath);//转移到FTP服务器目录       

			 FTPFile[] fs = ftpClient.listFiles();      

			for(FTPFile ff:fs){      
				if(ff.getName().equals(downloadFileName)){   
					File localFile = new File(saveFile);      
					OutputStream os = new FileOutputStream(localFile);       
					ftpClient.retrieveFile(ff.getName(), os);      
					os.close();      
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * 判断ftp服务器端指定目录下是否文件夹
	 * @param directory
	 *            服务器端指定目录--最内层目录后可加【/】，也可不加，效果一样
	 * @param fileName 指定文件或文件夹的名字
	 * @return  true/false 【如果指定目录都还不存在，直接返回false】
	 */
	public static boolean  isFileExist(String directory) {
		boolean is_exist=false; 
		String[] Filenames=GetFileNames(directory);
		if(Filenames!=null){
			is_exist = true;
		}
		System.out.println(directory+"目录下"+(is_exist?"已经有":"还没有")+"文件夹");
		return is_exist;
	}
	/**
	 * 判断ftp服务器端指定目录下是否有某文件或文件夹
	 * @param directory
	 *            服务器端指定目录--最内层目录后可加【/】，也可不加，效果一样
	 * @param fileName 指定文件或文件夹的名字
	 * @return  true/false 【如果指定目录都还不存在，直接返回false】
	 */
	public static boolean  isFileExist(String directory, String fileName) {
		boolean is_exist=false; 
		String[] Filenames=GetFileNames(directory);
		if(Filenames==null){
			return false;
		}
		for(int i=0;i<Filenames.length;i++){
			if(Filenames[i].equals(fileName)){
				is_exist=true;
				break;
			}
		}
		System.out.println(directory+"目录下"+(is_exist?"已经有":"还没有")+"名为"+fileName+"的文件或文件夹");
		return is_exist;
	}
	
	public static void main(String[] args) {
		FtpClient.connect("192.24.16.3", 21, "ftpput", "1");
		String dir="/odsdata/sfts/send";
		FTPFile[] files=FtpClient.GetDirAndFilesInfo(dir);
		for(int i=0;i<files.length;i++){
			//System.out.println(files[i].toString());
		}
		
		System.out.println("指定文件是否存在="+isFileExist(dir,"test.txt"));
		System.out.println("######################################");
		String[] file_names=FtpClient.GetFileNames(dir);
		for(int i=0;i<file_names.length;i++){
			System.out.println(file_names[i]);
		}
		
		FtpClient.download("/odsdata/sfts/send/cbus2ftp/20131127/811", "CBUS_SAACNTXN.del", "F:/DELS/1.del");
		
		FtpClient.disconnect();
	}

}
