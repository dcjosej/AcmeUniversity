package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.DegreeRepository;
import domain.Degree;


@Component
@Transactional
public class StringToDegreeConverter implements Converter<String, Degree>{

	@Autowired
	private DegreeRepository repository;

	@Override
	public Degree convert(String text) {
		Degree result;
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
