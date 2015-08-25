package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Tutorial;


@Component
@Transactional
public class TutorialToStringConverter implements Converter<Tutorial, String>{

	@Override
	public String convert(Tutorial object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
