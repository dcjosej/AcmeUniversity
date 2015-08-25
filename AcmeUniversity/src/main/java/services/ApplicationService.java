package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ApplicationRepository;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Application;
import domain.Lecturer;
import domain.Subject;

@Service
@Transactional
public class ApplicationService {

	@Autowired
	private ApplicationRepository applicationRepository;
	@Autowired
	private LecturerService lecturerService;

	public Application findOne(int id) {
		Application result = applicationRepository.findOne(id);

		if (result == null) {
			throw new DPNotFoundException();
		}

		return result;
	}

	public Integer isLecturerInSubject(Lecturer l, Subject s) {
		return applicationRepository.isLecturerInSubject(l, s);
	}

	public Application save(Application application) {
		Lecturer principal = lecturerService.findByPrincipal();
		if (principal != null) {
			if (applicationRepository.isLecturerInSubject(principal,
					application.getSubject()) == 1) {
				throw new DPForbiddenException();
			}
		}
		Application result = applicationRepository.save(application);
		return result;
	}

	public Integer getNumApplications(Lecturer lecturer, Subject subject) {
		Integer result = applicationRepository.getNumApplications(lecturer,
				subject);
		return result;
	}

	public Collection<Application> findPending() {
		Collection<Application> result = applicationRepository.findPending();
		return result;
	}

	public Application accept(int id) {
		Application application = findOne(id);
		application.setAccepted(true);
		Application dbApplication = applicationRepository.save(application);
		return dbApplication;
	}

	public void decline(int id) {
		Application application = findOne(id);
		applicationRepository.delete(application);
	}

	public boolean canApply(Subject subject) {
		// TODO Auto-generated method stub
		return false;
	}
}
