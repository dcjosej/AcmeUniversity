package services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SlotRepository;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Activity;
import domain.Lecture;
import domain.Slot;
import domain.Student;
import domain.Tutorial;
import forms.SlotTimetableForm;

@Service
@Transactional
public class SlotService {

	@Autowired
	private SlotRepository slotRepository;

	@Autowired
	private StudentService studentService;

	public Collection<SlotTimetableForm> getStudentTimetableDay(String dayOfTheWeek)
			throws DPForbiddenException {
		if (!dayOfTheWeek
				.matches("^Monday|Thursday|Wednesday|Tuesday|Friday|Saturday|Sunday$")) {
			throw new DPForbiddenException();
		}

		Student student = studentService.findByPrincipal();

		if (student == null) {
			throw new DPForbiddenException();
		}
		Collection<Slot> slots = slotRepository.getStudentTimetableDay(
				student, dayOfTheWeek);
		Collection<SlotTimetableForm> result = new HashSet<SlotTimetableForm>();
		
		for(Slot s : slots){
			SlotTimetableForm slotTimetableForm = new SlotTimetableForm();
			slotTimetableForm.setSlot(s);
			Activity a = s.getActivity();
			if(a instanceof Lecture){
				slotTimetableForm.setType("Lecture");
			}else if(a instanceof Tutorial){
				slotTimetableForm.setType("Tutorial");
			}
			
			result.add(slotTimetableForm);
		}
		
		
		return result;
	}

	public Collection<Slot> findByIdActivity(int idActivity) {
		Collection<Slot> res = slotRepository.findByActivityId(idActivity);
		return res;
	}

	public void saveAll(Collection<Slot> slots) {
		slotRepository.save(slots);
	}

	public Slot findOne(int id) {
		Slot slot = slotRepository.findOne(id);
		
		if(slot == null){
			throw new DPNotFoundException();
		}
		
		return slot;
	}
}
