package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.TutorialRepository;
import domain.Tutorial;


@Component
@Transactional
public class StringToTutorialConverter implements Converter<String, Tutorial>{

	@Autowired
	private TutorialRepository repository;

	@Override
	public Tutorial convert(String text) {
		Tutorial result;
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
