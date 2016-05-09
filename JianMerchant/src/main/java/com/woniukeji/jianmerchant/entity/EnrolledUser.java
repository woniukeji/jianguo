package com.woniukeji.jianmerchant.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 冯景润
 * @version 创建时间：2015-10-16 上午3:01:39
 **/
public class EnrolledUser extends Entity {

	private static final long serialVersionUID = 6996000719247958877L;

	public static final int REFUSED_USER = -1;
	public static final int ENROLLED_USER = 3;
	public static final int HIRED_USER = 4;
	
	private long id;
	private String name;
	private String enrolledTime;
	private String school;
	private String phone;
	private int sex;
	private int status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnrolledTime() {
		return enrolledTime;
	}

	public void setEnrolledTime(String enrolledTime) {
		this.enrolledTime = enrolledTime;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public static EnrolledUser fromJSONObject(JSONObject json) throws JSONException{
		EnrolledUser user = new EnrolledUser();
		user.setId(json.getLong("id"));
		user.setEnrolledTime(json.getString("time"));
		user.setName(json.getString("name"));
		user.setPhone(json.getString("tel"));
		user.setSchool(json.getString("school"));
		user.setSex(json.getInt("sex"));
		return user;
	}

}
