package com.bike.util;

import java.security.MessageDigest;

public class UserSHA256 {
	public static String getSHA256(String str) {
		StringBuffer sbuf = new StringBuffer();
		MessageDigest mDigest = null;
		try {
			mDigest = MessageDigest.getInstance("SHA-256");//객체 생성
		} catch (Exception e) {
			e.printStackTrace();
		}
		mDigest.update(str.getBytes()); //해시데이터
		byte[] msgStr = mDigest.digest(); //바이틀 배열의 바이너리로 변환
		for(int x=0; x<msgStr.length; x++) {
			byte tmpStrByte = msgStr[x];
			String tmpEncTxt = Integer.toString((tmpStrByte & 0xff)+0x100, 16).substring(1);
			sbuf.append(tmpEncTxt);
		}
		return sbuf.toString();
	}
}
