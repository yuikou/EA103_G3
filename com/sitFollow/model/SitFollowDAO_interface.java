package com.sitFollow.model;

import java.util.*;

public interface SitFollowDAO_interface {
	// 此介面定義了對資料庫的資料存取抽象方法
	Boolean add(String memNo, String sitNo);
	Boolean del(String memNo, String sitNo);
	List<String> getAllByMemNo(String memNo);

}
