package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.LectureRepository;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNoFileException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Lecture;
import domain.Student;

@Service
@Transactional
public class LectureService {
	// Managed repository ------------------
	@Autowired
	private LectureRepository lectureRepository;

	// Supporting service ---------------------
	@Autowired
	private StudentService studentService;

	// Simple CRUD methods --------------------
	public Collection<Lecture> findByIdSubject(int idSubject) {
		Collection<Lecture> res = lectureRepository.findByIdSubject(idSubject);
		return res;
	}

	public Lecture findOne(int idLecture) {
		Lecture result = lectureRepository.findOne(idLecture);

		if (result == null) {
			throw new DPNotFoundException();
		}

		return result;
	}

	public void register(int idLecture) {
		Lecture lecture = findOne(idLecture);
		Student principal = studentService.findByPrincipal();

		if (!principal.getDegrees().contains(lecture.getSubject().getDegree())) {
			throw new DPForbiddenException();
		}

		if (lecture.getStudents().contains(principal)) {
			throw new DPForbiddenException();
		}

		lecture.getStudents().add(principal);
		lectureRepository.save(lecture);
	}
}