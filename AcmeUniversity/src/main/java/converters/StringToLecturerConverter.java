package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.LecturerRepository;
import domain.Lecturer;


@Component
@Transactional
public class StringToLecturerConverter implements Converter<String, Lecturer>{

	@Autowired
	private LecturerRepository repository;

	@Override
	public Lecturer convert(String text) {
		Lecturer result;
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
