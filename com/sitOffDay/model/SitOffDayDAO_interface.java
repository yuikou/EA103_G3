package com.sitOffDay.model;

import java.util.*;

//此介面定義了對資料庫的資料存取抽象方法
public interface SitOffDayDAO_interface {

	Boolean add(SitOffDayVO sod);
	Boolean update(SitOffDayVO sod);
	Boolean del(String SODNo);
	
	// 查詢已設定的不可服務日期，進而檢視、修改、刪除
	SitOffDayVO getByPK(String offDayNo);
	
	// 搜尋某服務項目【不】提供服務的保姆(一對多)(回傳 Set)--會員搜尋、預約用--
	Set<String> getSitByDate(String sitSrvNo, String start_date, String end_date, String time);
	
	
}
