package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CommentRepository;
import utilities.dpexceptions.DPForbiddenException;
import domain.Activity;
import domain.Actor;
import domain.Comment;
import domain.Lecture;
import domain.Lecturer;
import domain.Student;
import domain.Tutor;
import domain.Tutorial;
import forms.CommentForm;

@Transactional
@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private TutorialService tutorialService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private LecturerService lecturerService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private LectureService lectureService;
	@Autowired
	private TutorService tutorService;
	@Autowired
	private ApplicationService applicationService;
	@Autowired
	private ActivityService activityService;

	public boolean canCommentTutorial(int idTutorial) {
		boolean result = true;
		try {
			checkCommentTutorial(idTutorial);
		} catch (Exception exc) {
			result = false;
		}

		return result;
	}

	public void checkCommentTutorial(int idTutorial) {
		Tutorial tutorial = tutorialService.findOne(idTutorial);

		Student student = studentService.findByPrincipal();
		if (student != null && !tutorial.getStudents().contains(student)) {
			throw new DPForbiddenException();
		}

		Lecturer lecturer = lecturerService.findByPrincipal();
		if (lecturer != null) {
			throw new DPForbiddenException();
		}

		Tutor tutor = tutorService.findByPrincipal();
		if (tutor != null) {
			if (!tutorial.getTutor().equals(tutor)) {
				throw new DPForbiddenException();
			}
		}
	}

	public boolean canCommentLecture(int idLecture) {
		boolean result = true;
		try {
			checkCommentLecture(idLecture);
		} catch (Exception exc) {
			result = false;
		}

		return result;
	}

	public void checkCommentLecture(int idLecture) {
		Lecture lecture = lectureService.findOne(idLecture);

		Student student = studentService.findByPrincipal();
		if (student != null && !lecture.getStudents().contains(student)) {
			throw new DPForbiddenException();
		}

		Lecturer lecturer = lecturerService.findByPrincipal();
		int lecturerRegistered = applicationService.isLecturerInSubject(lecturer, lecture.getSubject());
		if (lecturer != null && !(lecturerRegistered > 0)) {
			throw new DPForbiddenException();
		}

		Tutor tutor = tutorService.findByPrincipal();
		if (tutor != null) {
			throw new DPForbiddenException();
		}
	}
	
	public void checkCommentActivity(Activity activity){
		if(activity instanceof Tutorial){
			checkCommentTutorial(activity.getId());
		}else if(activity instanceof Lecture){
			checkCommentLecture(activity.getId());
		}
	}

	public CommentForm createForm(int id) {
		CommentForm result = new CommentForm();
		result.setActivity(id);
		return result;
	}

	public Comment reconstruct(CommentForm commentForm) {
		Activity activity = activityService.findOne(commentForm.getActivity());
		checkCommentActivity(activity);
		Comment result = new Comment();
		
		Actor actor = actorService.findByPrincipal();
		result.setActor(actor);
		result.setActivity(activity);
		result.setId(commentForm.getId());
		result.setVersion(commentForm.getVersion());
		result.setText(commentForm.getText());
		
		return result;
	}
	
	public Collection<Comment> findByActivityId(int idActivity){
		return commentRepository.findByActivityId(idActivity);
	}

	public Comment save(Comment comment) {
		Comment result = commentRepository.save(comment);
		return result;
	}
}
