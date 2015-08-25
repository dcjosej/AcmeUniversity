package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Activity;

import repositories.ActivityRepository;
import utilities.dpexceptions.DPForbiddenException;

@Service
@Transactional
public class ActivityService {

	@Autowired
	private ActivityRepository activityRepository;
	
	public Activity findOne(int id){
		Activity result = activityRepository.findOne(id);
		
		if(result == null){
			throw new DPForbiddenException();
		}
		
		return result;
	}
}
