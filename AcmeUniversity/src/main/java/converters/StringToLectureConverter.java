package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.LectureRepository;
import domain.Lecture;


@Component
@Transactional
public class StringToLectureConverter implements Converter<String, Lecture>{

	@Autowired
	private LectureRepository repository;

	@Override
	public Lecture convert(String text) {
		Lecture result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = repository.findOne(id);
			}

		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
