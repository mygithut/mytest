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
	 * 连接sftp服务器
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
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
		System.out.println("与远程服务器的sftp连接断开成功！");
	}
	
	

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFileName
	 *            要上传的文件
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
	 * 下载ftp服务器上的文件
	 * <br>-【如果要保存到的本地目录(saveFile的路径部分)不存在，系统报错，下载失败但程序并不终止；如果本地已经有同名文件则自动覆盖】
	 * 
	 * @param directory
	 *            要下载文件的目录(服务器端)
	 * @param downloadFileName
	 *            下载的文件全名(含后缀)
	 * @param saveFile
	 *            存在本地的 文件全路径(绝对路径+文件名<因此支持下载文件的同时改名>)
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
	 * 删除ftp服务器上的文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
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
	 * 列出ftp服务器端指定目录下的文件列表(包含文件夹)
	 * <br>--返回的内容转换成String后 是linux下运行【ls -la】命令的返回结果，整个一行内容add进vector的一个元素中.
	 * <br>--例如一行：【-rw-------    1 ftp      ftps         3881 Oct 29 14:25 .viminfo】
	 * @param directory
	 *            要列出的目录--最内层目录后可加【/】，也可不加，效果一样。
	 * @param sftp
	 * @return  【如果此目录不存在，返回null】
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
	 * 列出ftp服务器端指定目录下的文件名列表(包含文件夹-也包含隐藏文件件，乃至【.】与【..】这两个文件夹)
	 * @param directory
	 *            要列出的目录--最内层目录后可加【/】，也可不加，效果一样
	 * @param sftp
	 * @return 【如果此目录不存在，返回null】
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
	 * 判断ftp服务器端指定目录下是否有某文件或文件夹
	 * @param directory
	 *            服务器端指定目录--最内层目录后可加【/】，也可不加，效果一样
	 * @param fileName 指定文件或文件夹的名字
	 * * @param sftp
	 * @return  true/false 【如果指定目录都还不存在，直接返回false】
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
		System.out.println(directory+"目录下"+(is_exist?"已经有":"还没有")+"名为"+fileName+"的文件或文件夹");
		return is_exist;
	}
	
	/**
	 * 上传文件
	 * @param destdirectory 上传的目标目录
	 * @param souredirectory 被上传的本地目录
	 * @param sftp ChannelSftp类
	 * @param filename 被上传的本地文件名称
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
			//测试cd到一个不存在目录时的
			String dir_notExist="/home/ftp/不存在目录";
			dir_notExist=new String(dir_notExist.getBytes("UTF-8"));
			//sftp.cd(dir_notExist);//如果目录不存在，则报异常且程序终止
			
			String dir="/home/ftp/创建目录";
			String dir_utf8 =new String(dir.getBytes("UTF-8"));//这样就能支持 目录路径中 带中文。
			//System.out.println(dir);
			String[] fileNames=SFTPUtil.listFileNames(dir_utf8, sftp);
			
			if(fileNames==null){
				System.out.println("目录"+dir+"并不存在!");
			}else{
				System.out.println("#######【"+dir+"】下的文件如下：");
				System.out.println("------------------");
				for(int i=0;i<fileNames.length;i++){
					//System.out.println(fileNames[i]);
					String file_name=new String(fileNames[i].getBytes(),"UTF-8");
					System.out.println(file_name);
					/////----String str=new String(fileNames[i].getBytes());
					/////----System.out.println(str);
					/////----System.out.println(new String(str.getBytes("UTF-8")));
					//System.out.println(new String(fileNames[i].getBytes(),"UTF-8"));//把fileNames[i]的没有任何(默认)编码方式的byte[]形式进行一次UTF-8解码
					/////----System.out.println(new String(str.getBytes("UTF-8"),"UTF-8"));
					System.out.println("------------------");
				}
			}
			
			String file_name="fa_assetbas.ctl";
			boolean is_exist=SFTPUtil.isFileExist(dir_utf8, file_name, sftp);
			System.out.println("目录["+dir+"]下是否存在文件]"+file_name+"]: "+is_exist);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		sftp_util.disconnect();
		
	}
}
