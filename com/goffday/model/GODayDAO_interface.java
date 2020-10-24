package com.goffday.model;

import java.util.*;

public interface GODayDAO_interface {
	public void insert(GODayVO godayVO);
	public void insertHoliday(GODayVO godayVO);
    public void update(GODayVO godayVO);
    public void delete(String offno);
    //找出一間店所有美容師的off 
    public List<GODayVO> getAll(String groomerno);
    public GODayVO findByPrimaryKey(String offno);
}
