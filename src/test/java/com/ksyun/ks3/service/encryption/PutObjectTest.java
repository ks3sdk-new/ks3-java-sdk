package com.ksyun.ks3.service.encryption;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.ksyun.ks3.dto.GetObjectResult;
import com.ksyun.ks3.service.request.PutObjectRequest;
import com.ksyun.ks3.service.transfer.Ks3UploadClient;
import com.ksyun.ks3.utils.Md5Utils;

/**
 * @author lijunwei[lijunwei@kingsoft.com]  
 * 
 * @date 2015年4月9日 下午2:31:45
 * 
 * @description 
 **/
public class PutObjectTest extends EncryptionClientTest{
	//String filepath = "D://IMG.jpg";
	//String filedownpath = "D://IMG-down.jpg";
	String filepath = "D://test.txt";
	String filedownpath = "D://test-down.txt";
	String key = "IMG.jpg";
	String instruction = "IMG.jpg.instruction";
	@Test
	public void testPutEO_Meta() throws IOException{
		checkSimplePutAndGet(eo_meta);
	}
	@Test
	public void testPutEO_File() throws IOException{
		checkSimplePutAndGet(eo_file);
	}
	@Test
	public void testPutAE_Meta() throws IOException{
		checkSimplePutAndGet(ae_meta);
	}
	@Test
	public void testPutAE_File() throws IOException{
		checkSimplePutAndGet(ae_file);
	}
	@Test
	public void testPutSAE_Meta() throws IOException{
		checkSimplePutAndGet(sae_meta);
	}
	@Test
	public void testPutSAE_File() throws IOException{
		checkSimplePutAndGet(sae_file);
	}
	
	private void checkSimplePutAndGet(Ks3EncryptionClient client) throws IOException{
		PutObjectRequest req = new PutObjectRequest(bucket,key,new File(filepath));
		client.putObject(req);	
		
		GetObjectResult result = client.getObject(bucket, key);
		super.writeToFile(result.getObject().getObjectContent(), new File(filedownpath));
		assertEquals(Md5Utils.md5AsBase64(new File(filepath)),
				Md5Utils.md5AsBase64(new File(filedownpath)));
	}
	
	@Test
	public void testDeleteObject_File(){
		PutObjectRequest req = new PutObjectRequest(bucket,key,new File(filepath));
		ae_file.putObject(req);	
		assertTrue(ae_file.objectExists(bucket,key));
		assertTrue(ae_file.objectExists(bucket, instruction));
		ae_file.deleteObject(bucket, "IMG.jpg");
		assertFalse(ae_file.objectExists(bucket, key));
		assertFalse(ae_file.objectExists(bucket, instruction));
	}	
	@Test
	public void testDeleteObject_Meta(){
		PutObjectRequest req = new PutObjectRequest(bucket,key,new File(filepath));
		ae_meta.putObject(req);	
		assertTrue(ae_meta.objectExists(bucket, key));
		assertFalse(ae_meta.objectExists(bucket, instruction));
		ae_meta.deleteObject(bucket, key);
		assertFalse(ae_meta.objectExists(bucket, key));
		assertFalse(ae_meta.objectExists(bucket, instruction));
	}
	@Test
	public void testMultiEO_Meta() throws IOException{
		checkMultipartUpload(eo_meta);
	}
	@Test
	public void testMultiEO_File() throws IOException{
		checkMultipartUpload(eo_file);
	}
	@Test
	public void testMultiAE_Meta() throws IOException{
		checkMultipartUpload(ae_meta);
	}
	@Test
	public void testMultiAE_File() throws IOException{
		checkMultipartUpload(ae_file);
	}
	@Test
	public void testMultiSAE_Meta() throws IOException{
		checkMultipartUpload(sae_meta);
	}
	@Test
	public void testMultiSAE_File() throws IOException{
		checkMultipartUpload(sae_file);
	}
	private void checkMultipartUpload(Ks3EncryptionClient client) throws IOException{
		Ks3UploadClient upClient = new Ks3UploadClient(client);
		upClient.uploadFile(bucket, key,new File(filepath));
		
		GetObjectResult result = client.getObject(bucket, key);
		super.writeToFile(result.getObject().getObjectContent(), new File(filedownpath));
		assertEquals(Md5Utils.md5AsBase64(new File(filepath)),
				Md5Utils.md5AsBase64(new File(filedownpath)));
	}
}
