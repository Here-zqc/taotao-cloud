package com.itszt.common;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FastDFSClientUtil {

	private TrackerClient trackerClient = null;
	private TrackerServer trackerServer = null;
	private StorageServer storageServer = null;
	private StorageClient1 storageClient = null;
//	public static final String FDFS_URL="http://192.168.3.235:8888/";

	static {

		String configPath=FastDFSClientUtil.class.getResource("/").getPath()+"fdfs_client.conf";
		System.out.println("configPath = " + configPath);
		try {
			ClientGlobal.init(configPath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MyException e) {
			e.printStackTrace();
		}
	}

	
	public FastDFSClientUtil(String conf) throws Exception {
		if (conf.contains("classpath:")) {
			conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
		}
		ClientGlobal.init(conf);
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient1(trackerServer, storageServer);
	}
	public FastDFSClientUtil() throws Exception {
		trackerClient = new TrackerClient();
		trackerServer = trackerClient.getConnection();
		storageServer = null;
		storageClient = new StorageClient1(trackerServer, storageServer);
	}

	/**
	 * 上传文件方法
	 * <p>Title: uploadFile</p>
	 * <p>Description: </p>
	 * @param fileName 文件全路径
	 * @param extName 文件扩展名，不包含（.）
	 * @param metas 文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(String fileName, String extName, NameValuePair[] metas) throws Exception {
		String result = storageClient.upload_file1(fileName, extName, metas);
		return result;
	}
	
	public String uploadFile(String fileName) throws Exception {
		return uploadFile(fileName, null, null);
	}
	
	public String uploadFile(String fileName, String extName) throws Exception {
		return uploadFile(fileName, extName, null);
	}
	
	/**
	 * 上传文件方法
	 * <p>Title: uploadFile</p>
	 * <p>Description: </p>
	 * @param fileContent 文件的内容，字节数组
	 * @param extName 文件扩展名
	 * @param metas 文件扩展信息
	 * @return
	 * @throws Exception
	 */
	public String uploadFile(byte[] fileContent, String extName, NameValuePair[] metas) throws Exception {
		
		String result = storageClient.upload_file1(fileContent, extName, metas);
		return result;
	}
	
	public String uploadFile(byte[] fileContent) throws Exception {
		return uploadFile(fileContent, null, null);
	}
	
	public String uploadFile(byte[] fileContent, String extName) throws Exception {
		return uploadFile(fileContent, extName, null);
	}

	public static void main(String[] args) throws Exception {


		//客户端配置文件
		 String conf_filename = "fdfs_client.conf";
		//本地文件，要上传的文件
		 String local_filename = "/Users/shijian/";
		FastDFSClientUtil fastDFSClientUtil=new FastDFSClientUtil();
		File dir=new File("/Users/shijian/商品图片");

		File[] files = dir.listFiles();
		StringBuilder stringBuilder=new StringBuilder();
		for (File file : files) {
//			String path = fastDFSClientUtil.uploadFile(file.getAbsolutePath(), file.getName().split("\\.")[1]);

			FileInputStream fileInputStream=new FileInputStream(file);
			ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
			byte[] bs=new byte[1024];
			int total=-1;
			while ((total=fileInputStream.read(bs))!=-1){

				byteArrayOutputStream.write(bs,0,total);

			}

			String path=fastDFSClientUtil.uploadFile(byteArrayOutputStream.toByteArray(), file.getName().split("\\.")[1]);
			System.out.println("path = " + path);
			stringBuilder.append(path);
			stringBuilder.append(",");
		}
		System.out.println("stringBuilder = " + stringBuilder);

	}
}
