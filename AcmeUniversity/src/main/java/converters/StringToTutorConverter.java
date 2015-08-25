package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.TutorRepository;
import domain.Tutor;


@Component
@Transactional
public class StringToTutorConverter implements Converter<String, Tutor>{

	@Autowired
	private TutorRepository repository;

	@Override
	public Tutor convert(String text) {
		Tutor result;
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
