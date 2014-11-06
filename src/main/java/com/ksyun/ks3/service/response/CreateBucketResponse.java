package com.ksyun.ks3.service.response;
import com.ksyun.ks3.dto.Bucket;

/**
 * @author lijunwei[13810414122@163.com]  
 * 
 * @date 2014年10月15日 下午5:09:30
 * 
 * @description 
 **/
public class CreateBucketResponse extends Ks3WebServiceDefaultResponse<Bucket> {
	public int[] expectedStatus() {
		return new int[]{200};
	}

	@Override
	public void preHandle() {
		this.result = new Bucket();
	}
}