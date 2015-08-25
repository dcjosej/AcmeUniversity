package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Activity;

@Component
@Transactional
public class ActivityToStringConverter implements Converter<Activity, String>{

	@Override
	public String convert(Activity object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
