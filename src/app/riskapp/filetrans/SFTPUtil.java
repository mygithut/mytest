package app.riskapp.filetrans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;


public class SFTPUtil {
	
	private Session sshSession=null;

	/**
	 * ����sftp������
	 * 
	 * @param host
	 *            ����
	 * @param port
	 *            �˿�
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @return
	 */
	public ChannelSftp connect(String host, int port, String username,
			String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			//jsch.getSession(username, host, port);
			this.sshSession = jsch.getSession(username, host, port);
			System.out.println("Session created.");
			this.sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			this.sshSession.setConfig(sshConfig);
			this.sshSession.connect();
			System.out.println("Session connected.");
			System.out.println("Opening Channel.");
			Channel channel = this.sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			System.out.println("Connected to " + host + ".");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sftp;
	}
	
	public void disconnect() {
		this.sshSession.disconnect();
		System.out.println("��Զ�̷�������sftp���ӶϿ��ɹ���");
	}
	
	

	/**
	 * �ϴ��ļ�
	 * 
	 * @param directory
	 *            �ϴ���Ŀ¼
	 * @param uploadFileName
	 *            Ҫ�ϴ����ļ�
	 * @param sftp
	 */
	public static void upload(String directory, String uploadFileName, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(uploadFileName);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ftp�������ϵ��ļ�
	 * <br>-�����Ҫ���浽�ı���Ŀ¼(saveFile��·������)�����ڣ�ϵͳ��������ʧ�ܵ����򲢲���ֹ����������Ѿ���ͬ���ļ����Զ����ǡ�
	 * 
	 * @param directory
	 *            Ҫ�����ļ���Ŀ¼(��������)
	 * @param downloadFileName
	 *            ���ص��ļ�ȫ��(����׺)
	 * @param saveFile
	 *            ���ڱ��ص� �ļ�ȫ·��(����·��+�ļ���<���֧�������ļ���ͬʱ����>)
	 * @param sftp
	 */
	public static boolean download(String directory, String downloadFileName,
			String saveFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFileName, new FileOutputStream(file));
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage()+":"+saveFile);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * ɾ��ftp�������ϵ��ļ�
	 * 
	 * @param directory
	 *            Ҫɾ���ļ�����Ŀ¼
	 * @param deleteFile
	 *            Ҫɾ�����ļ�
	 * @param sftp
	 */
	public static void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �г�ftp��������ָ��Ŀ¼�µ��ļ��б�(�����ļ���)
	 * <br>--���ص�����ת����String�� ��linux�����С�ls -la������ķ��ؽ��������һ������add��vector��һ��Ԫ����.
	 * <br>--����һ�У���-rw-------    1 ftp      ftps         3881 Oct 29 14:25 .viminfo��
	 * @param directory
	 *            Ҫ�г���Ŀ¼--���ڲ�Ŀ¼��ɼӡ�/����Ҳ�ɲ��ӣ�Ч��һ����
	 * @param sftp
	 * @return  �������Ŀ¼�����ڣ�����null��
	 * @throws SftpException 
	 */
	public static Vector<LsEntry> listFiles(String directory, ChannelSftp sftp){
		Vector<LsEntry> v_files=null;
		try{
			v_files=(Vector<LsEntry>)(sftp.ls(directory));
		}catch(SftpException se){
			System.out.println(se.getMessage()+":"+directory);
			return null;
		}
		return v_files;
	}
	
	/**
	 * �г�ftp��������ָ��Ŀ¼�µ��ļ����б�(�����ļ���-Ҳ���������ļ�����������.���롾..���������ļ���)
	 * @param directory
	 *            Ҫ�г���Ŀ¼--���ڲ�Ŀ¼��ɼӡ�/����Ҳ�ɲ��ӣ�Ч��һ��
	 * @param sftp
	 * @return �������Ŀ¼�����ڣ�����null��
	 * @throws SftpException
	 */
	public static String[]  listFileNames(String directory, ChannelSftp sftp){
		Vector<LsEntry> v_Files=new Vector<LsEntry>();
		v_Files=SFTPUtil.listFiles(directory, sftp);
		if(v_Files==null){
			return null;
		}
		String[] Filenames=new String[v_Files.size()];
		for(int i=0;i<v_Files.size();i++){
			Filenames[i]=v_Files.get(i).getFilename();
		}
		return Filenames;
	}
	
	/**
	 * �ж�ftp��������ָ��Ŀ¼���Ƿ���ĳ�ļ����ļ���
	 * @param directory
	 *            ��������ָ��Ŀ¼--���ڲ�Ŀ¼��ɼӡ�/����Ҳ�ɲ��ӣ�Ч��һ��
	 * @param fileName ָ���ļ����ļ��е�����
	 * * @param sftp
	 * @return  true/false �����ָ��Ŀ¼���������ڣ�ֱ�ӷ���false��
	 */
	public static boolean  isFileExist(String directory, String fileName,ChannelSftp sftp) {
		boolean is_exist=false; 
		String[] Filenames=SFTPUtil.listFileNames(directory, sftp);
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
	
	/**
	 * �ϴ��ļ�
	 * @param destdirectory �ϴ���Ŀ��Ŀ¼
	 * @param souredirectory ���ϴ��ı���Ŀ¼
	 * @param sftp ChannelSftp��
	 * @param filename ���ϴ��ı����ļ�����
	 * @return
	 * @throws SftpException
	 */
	public static void putFile(String destdirectory,String souredirectory, ChannelSftp sftp,String filename){
		
		try {
			sftp.cd(destdirectory);
			sftp.lcd(souredirectory);
			sftp.put(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		SFTPUtil sftp_util =  new SFTPUtil();
		ChannelSftp sftp=sftp_util.connect("192.24.16.87", 22, "ftp", "ftp");
		
		
		
		System.out.println("file.encoding="+System.getProperty("file.encoding"));
		//System.getProperties().list(System.out);

		//dir="/home/ftp/";
		
		try{
			//����cd��һ��������Ŀ¼ʱ��
			String dir_notExist="/home/ftp/������Ŀ¼";
			dir_notExist=new String(dir_notExist.getBytes("UTF-8"));
			//sftp.cd(dir_notExist);//���Ŀ¼�����ڣ����쳣�ҳ�����ֹ
			
			String dir="/home/ftp/����Ŀ¼";
			String dir_utf8 =new String(dir.getBytes("UTF-8"));//��������֧�� Ŀ¼·���� �����ġ�
			//System.out.println(dir);
			String[] fileNames=SFTPUtil.listFileNames(dir_utf8, sftp);
			
			if(fileNames==null){
				System.out.println("Ŀ¼"+dir+"��������!");
			}else{
				System.out.println("#######��"+dir+"���µ��ļ����£�");
				System.out.println("------------------");
				for(int i=0;i<fileNames.length;i++){
					//System.out.println(fileNames[i]);
					String file_name=new String(fileNames[i].getBytes(),"UTF-8");
					System.out.println(file_name);
					/////----String str=new String(fileNames[i].getBytes());
					/////----System.out.println(str);
					/////----System.out.println(new String(str.getBytes("UTF-8")));
					//System.out.println(new String(fileNames[i].getBytes(),"UTF-8"));//��fileNames[i]��û���κ�(Ĭ��)���뷽ʽ��byte[]��ʽ����һ��UTF-8����
					/////----System.out.println(new String(str.getBytes("UTF-8"),"UTF-8"));
					System.out.println("------------------");
				}
			}
			
			String file_name="fa_assetbas.ctl";
			boolean is_exist=SFTPUtil.isFileExist(dir_utf8, file_name, sftp);
			System.out.println("Ŀ¼["+dir+"]���Ƿ�����ļ�]"+file_name+"]: "+is_exist);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		sftp_util.disconnect();
		
	}
}
