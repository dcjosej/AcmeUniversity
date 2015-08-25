package services;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import repositories.TeacherRepository;
import security.LoginService;
import security.UserAccount;
import utilities.dpexceptions.DPForbiddenException;
import utilities.dpexceptions.DPNotFoundException;
import domain.Teacher;
import domain.Tutorial;
import forms.TeacherForm;

@Service
@Transactional
public class TeacherService {

	// Managed respository ---------------------------
	@Autowired
	private TeacherRepository teacherRepository;

	// Simple CRUD methods ----------------------------
	public Teacher findOne(int idTeacher) {
		Teacher res = teacherRepository.findOne(idTeacher);
		if (res == null) {
			throw new DPNotFoundException();
		}

		return res;
	}

	public Teacher save(Teacher teacher) {
		Teacher res = teacherRepository.save(teacher);
		return res;
	}

	public TeacherForm createForm(int idTeacher) {
		Teacher principal = findByPrincipal();
		Teacher teacher = findOne(idTeacher);
		if(!teacher.equals(principal)){
			throw new DPForbiddenException();
		}
		TeacherForm res = new TeacherForm();
		res.setCurriculum(teacher.getCurriculum());
		res.setDescription(teacher.getDescription());
		res.setEmail(teacher.getEmail());
		res.setId(teacher.getId());
		res.setVersion(teacher.getVersion());
		res.setPhoneNumber(teacher.getPhoneNumber());
		res.setOldPhoto(teacher.getPhoto());

		return res;
	}

	// Other methods ------------------------------------
	public Teacher recontruct(TeacherForm form) throws SerialException,
			SQLException, IOException {
		Teacher res = findOne(form.getId());
		Teacher principal = findByPrincipal();
		if(!res.equals(principal)){
			throw new DPForbiddenException();
		}
		res.setEmail(form.getEmail());
		res.setDescription(form.getDescription());
		res.setCurriculum(form.getCurriculum());
		res.setPhoneNumber(form.getPhoneNumber());

		
		MultipartFile photo = form.getNewPhoto();
		if(photo != null && photo.getSize() != 0){
			Blob newPhoto = new SerialBlob(photo.getBytes());
			res.setPhoto(newPhoto);
		}
		
		return res;
	}

	public Teacher findByPrincipal() {
		UserAccount account = LoginService.getPrincipal();
		int id = account.getId();
		Teacher res = teacherRepository.findByUserAccountId(id);
		return res;
	}

	public boolean canEdit(Teacher teacher) {
		Teacher principal = findByPrincipal();
		boolean result = false;
		
		if(principal != null && teacher.equals(principal)){
			result = true;
		}
		return result;
	}
}
