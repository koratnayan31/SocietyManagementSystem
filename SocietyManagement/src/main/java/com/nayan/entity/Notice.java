package com.nayan.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;

import com.nayan.helper.Utility;

@Entity
@Table(name = "Notice_Board")
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank(message = "Please Add Title For Notice")
	@Size(min = 10, message = "Notice Title must have 10 Charecters")
	private String title;

	@NotBlank(message = "Please Write content for Notice")
	@Size(min = 20, message = "Notice description must have atleast 20 Charecters")
	private String description;

	private Boolean isEmergency;

	@CreationTimestamp
	private Date noticeOn;

	public Notice() {
		super();
	}

	public Notice(int id,
			@NotBlank(message = "Please Add Title For Notice") @Size(min = 10, message = "Notice Title must have 10 Charecters") String title,
			@NotBlank(message = "Please Write content for Notice") @Size(min = 20, message = "Notice description must have atleast 20 Charecters") String description,
			boolean isEmergency, Date noticeOn) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.isEmergency = isEmergency;
		this.noticeOn = noticeOn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title =Utility.toCamelCase(title);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description =Utility.toCamelCase(description);
	}

	public Boolean isEmergency() {
		return isEmergency;
	}

	public void setEmergency(Boolean isEmergency) {
		this.isEmergency = isEmergency;
	}

	public String getNoticeOn() {
		Calendar calender=Calendar.getInstance();
		calender.setTime(noticeOn);
		
		String time=calender.get(Calendar.DAY_OF_MONTH)+"/"+(calender.get(Calendar.MONTH)+1)+"/"+calender.get(Calendar.YEAR);
		return time;
	}

	public void setNoticeOn(Date noticeOn) {
		this.noticeOn = noticeOn;
	}

	@Override
	public String toString() {
		return "Notice [id=" + id + ", title=" + title + ", description=" + description + ", isEmergency=" + isEmergency
				+ ", noticeOn=" + noticeOn + "]";
	}
	
	
}
