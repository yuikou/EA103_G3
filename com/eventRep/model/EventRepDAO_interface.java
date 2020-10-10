package com.eventRep.model;

import java.util.List;

public interface EventRepDAO_interface {
	Boolean add(EventRepVO eventRep);
	Boolean update(EventRepVO eventRep);
	EventRepVO getOne(String eventRepNo);
	List<EventRepVO> getAll(Integer eventRepStatus, Integer eventRepResult);
}
