package com.nayan.repo.projection;

import java.util.Date;

public class CreationTime {
	private final Date recordDate;

	public Date getRecordDate() {
		return recordDate;
	}

	public CreationTime(Date recordDate) {
		super();
		this.recordDate = recordDate;
	}
	
}
