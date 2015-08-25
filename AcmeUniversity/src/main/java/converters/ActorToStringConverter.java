package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Actor;

@Component
@Transactional
public class ActorToStringConverter implements Converter<Actor, String> {
	@Override
	public String convert(Actor object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
