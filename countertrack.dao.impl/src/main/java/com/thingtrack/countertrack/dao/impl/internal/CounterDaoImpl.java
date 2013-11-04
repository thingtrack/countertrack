package com.thingtrack.countertrack.dao.impl.internal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.thingtrack.konekti.dao.template.JpaDao;

import com.thingtrack.countertrack.domain.Counter;
import com.thingtrack.countertrack.dao.api.CounterDao;

public class CounterDaoImpl extends JpaDao<Counter, Integer> implements CounterDao {
	
	private Calendar calendar = Calendar.getInstance();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public SimpleDateFormat getDateFormat() {
		return dateFormat;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Counter> getAllWeek(int weeks) throws Exception {
		
		// Set the first and last calendar of the current week
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		calendar.add(Calendar.DAY_OF_MONTH, - weeks * 7);
		
		Date firstDayOfWeek = calendar.getTime();
			
	    /*if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
	    	calendar.add(Calendar.DAY_OF_MONTH, 7);	
	    else*/
	    	calendar.add(Calendar.DAY_OF_MONTH, 7);
		
		Date lastDayOfWeek = calendar.getTime();
		
		String queryString  =  "SELECT p FROM " + getEntityName() + " p";
			   queryString += " WHERE p.countDate >= :firstWeekDay AND  p.countDate <= :lastWeekDay";
			   queryString += " ORDER BY p.countDate";
		
		Query query = (Query) getEntityManager().createQuery(queryString);
		
		query.setParameter("firstWeekDay", firstDayOfWeek, TemporalType.DATE);
		query.setParameter("lastWeekDay", lastDayOfWeek, TemporalType.DATE);
		
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap<String, Integer> getAllWeekGrouped(int weeks) throws Exception {
		LinkedHashMap<String, Integer> results = new LinkedHashMap<String, Integer>();
		
		// Set the first and last calendar of the current week
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		calendar.add(Calendar.DAY_OF_MONTH, - weeks * 7);
		
		Date firstDayOfWeek = calendar.getTime();
		
		 /*if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			 calendar.add(Calendar.DAY_OF_MONTH, 7);	
		 else*/
			 calendar.add(Calendar.DAY_OF_MONTH, 7);
				
		Date lastDayOfWeek = calendar.getTime();
		
		String queryString  =  "SELECT p FROM " + getEntityName() + " p";
			   queryString += " WHERE cast(p.countDate AS date) >= :firstWeekDay AND cast(p.countDate AS date) <= :lastWeekDay";

		Query query = (Query) getEntityManager().createQuery(queryString);
		
		query.setParameter("firstWeekDay", firstDayOfWeek, TemporalType.DATE);
		query.setParameter("lastWeekDay", lastDayOfWeek, TemporalType.DATE);
		
		// get all counts registers in the current week
		List<Counter> counts = query.getResultList();
				
		// resume counts in the current week
		List<String> days = getAllWeekDays(weeks);
		
		for (String day : days) {	
			int in = 0;
			int out = 0;
			
			for (Counter counter : counts) {
				if (dateFormat.format(counter.getCountDate()).equals(day)) {
					if (counter.getWay() == 0)
						out++;
					else if (counter.getWay() == 1)
						in++;
				}
			}
			
			results.put(day + "|0", out);
			results.put(day + "|1", in);
			
		}
		
		return results;
	}
	
	@Override
	public List<String> getAllWeekDays(int weeks) throws Exception {
		List<String> weekDays = new ArrayList<String>();
		
		// Set the first and last calendar of the current week
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		calendar.add(Calendar.DAY_OF_MONTH, - weeks * 7);
			
		weekDays.add(dateFormat.format(calendar.getTime()));
		for (int i=0; i<6; i++) {
			calendar.add(Calendar.DATE, 1);
			weekDays.add(dateFormat.format(calendar.getTime()));
		}
		
		return weekDays;
		
	}

}
