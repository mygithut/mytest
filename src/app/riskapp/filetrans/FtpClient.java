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
	 * ���ӵ�FTP������
	 * 
	 * @param hostname
	 *            ������������ip
	 * @param port
	 *            �˿�
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @return �Ƿ����ӳɹ�
	 * @throws IOException
	 */

	public static boolean connect(String hostname, int port, String username,String password){
		try{
			ftpClient.connect(hostname, port);
			ftpClient.setControlEncoding("GBK");
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				if (ftpClient.login(username, password)) {
					System.out.println("ftp���ӳɹ���");
					return true;
				}
			}
			System.out.println("ftp����ʧ�ܣ�");
			disconnect();
			return false;
		}catch(IOException ioe){
			System.out.println("ftp����ʧ�ܣ�");
			ioe.printStackTrace();
			return false;
		}
		
	}

	/**
	 * 
	 * �Ͽ���Զ�̷�����������
	 * 
	 * 
	 * 
	 * @throws IOException
	 */

	public static void disconnect() {
		try{
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
				System.out.println("ftp�Ͽ��ɹ���");
			}else{
				System.out.println("ftp�Ͽ�����ʱ�����ֲ��������ӣ�");
			}
		}catch(Exception e){
			System.out.println("ftp�Ͽ�ʧ�ܣ�");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * ��ȡָ��Ŀ¼�µ��ļ������б������������ļ�
	 * 
	 * @param currentDir
	 *            ��Ҫ��ȡ��Զ��Ŀ¼����--�������Ϊnull������֮ǰָ����Զ��Ŀ¼��ȡ
	 * @return ������Ŀ¼���ļ����ַ������顾ע���ļ����� ������ȫ·�����ַ�����
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
	 * ��ȡָ��Ŀ¼�µ��ļ������б������������ļ�
	 * 
	 * @param currentDir
	 *            ��Ҫ��ȡ��Զ��Ŀ¼����--�������Ϊnull������֮ǰָ����Զ��Ŀ¼��ȡ
	 * @return ������Ŀ¼���ļ����ַ������顾���ָ��Ŀ¼�������򷵻�null��
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
	 * ��ȡָ��Ŀ¼�µ��ļ���Ŀ¼��Ϣ����:���������ļ�
	 ** <br>--���ص�����ת����String�� ��linux�����С�ls -la������ķ��ؽ��
	 * <br>--����һ�У���-rw-------    1 ftp      ftps         3881 Oct 29 14:25 .viminfo��
	 * @param currentDir
	 *            ָ����Զ��Ŀ¼--�������Ϊnull������֮ǰָ����Զ��Ŀ¼��ȡ
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
	
	/**
	 * �ϴ������ļ�
	 * 
	 * @param directory   Ҫ�ϴ�����Զ�̷�����Ŀ¼�� ��ȫ·����
	 * @param  filename   �ϴ���FTP����������ļ���  
	 * @param uploadFileName   Ҫ�ϴ��ı����ļ���������ȫ·��+�ļ�����
	 * @return ��boolean��true:�ɹ���false:ʧ��
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
	 * ���ص����ļ�
	 * 
	 * @param remotePath
	 *            Ҫ���ص�Զ�̷�����Ŀ¼�� ��ȫ·����
	 * @param downloadFileName
	 *            Ҫ���ص��ļ���
	 * @param saveFile
	 *            ���ڱ��ص� �ļ�ȫ·��(����·��+�ļ���<���֧�������ļ���ͬʱ����>)
	 * @param sftp
	 */
	public static boolean download(String remotePath, String downloadFileName,String saveFile) {
		try {
			ftpClient.changeWorkingDirectory(remotePath);//ת�Ƶ�FTP������Ŀ¼       

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
	 * �ж�ftp��������ָ��Ŀ¼���Ƿ��ļ���
	 * @param directory
	 *            ��������ָ��Ŀ¼--���ڲ�Ŀ¼��ɼӡ�/����Ҳ�ɲ��ӣ�Ч��һ��
	 * @param fileName ָ���ļ����ļ��е�����
	 * @return  true/false �����ָ��Ŀ¼���������ڣ�ֱ�ӷ���false��
	 */
	public static boolean  isFileExist(String directory) {
		boolean is_exist=false; 
		String[] Filenames=GetFileNames(directory);
		if(Filenames!=null){
			is_exist = true;
		}
		System.out.println(directory+"Ŀ¼��"+(is_exist?"�Ѿ���":"��û��")+"�ļ���");
		return is_exist;
	}
	/**
	 * �ж�ftp��������ָ��Ŀ¼���Ƿ���ĳ�ļ����ļ���
	 * @param directory
	 *            ��������ָ��Ŀ¼--���ڲ�Ŀ¼��ɼӡ�/����Ҳ�ɲ��ӣ�Ч��һ��
	 * @param fileName ָ���ļ����ļ��е�����
	 * @return  true/false �����ָ��Ŀ¼���������ڣ�ֱ�ӷ���false��
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
		System.out.println(directory+"Ŀ¼��"+(is_exist?"�Ѿ���":"��û��")+"��Ϊ"+fileName+"���ļ����ļ���");
		return is_exist;
	}
	
	public static void main(String[] args) {
		FtpClient.connect("192.24.16.3", 21, "ftpput", "1");
		String dir="/odsdata/sfts/send";
		FTPFile[] files=FtpClient.GetDirAndFilesInfo(dir);
		for(int i=0;i<files.length;i++){
			//System.out.println(files[i].toString());
		}
		
		System.out.println("ָ���ļ��Ƿ����="+isFileExist(dir,"test.txt"));
		System.out.println("######################################");
		String[] file_names=FtpClient.GetFileNames(dir);
		for(int i=0;i<file_names.length;i++){
			System.out.println(file_names[i]);
		}
		
		FtpClient.download("/odsdata/sfts/send/cbus2ftp/20131127/811", "CBUS_SAACNTXN.del", "F:/DELS/1.del");
		
		FtpClient.disconnect();
	}

}
