package com.ksyun.ks3.dto;
/**
 * @author lijunwei[13810414122@163.com]  
 * 
 * @date 2014年10月23日 上午10:30:46
 * 
 * @description 
 **/
public class InitiateMultipartUploadResult {
	private String bucket;
	private String key;
	private String uploadId;
	public String getBucket() {
		return bucket;
	}
	public void setBucket(String bucket) {
		this.bucket = bucket;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUploadId() {
		return uploadId;
	}
	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}
	public String toString()
	{
		return "InitiateMultipartUploadResult[bucket="+this.bucket+";key="+this.key+";uploadId="+this.uploadId+"]";
	}
}