package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Slot;

@Component
@Transactional
public class SlotToStringConverter implements Converter<Slot, String>{

	@Override
	public String convert(Slot object) {
		String result;

		if (object == null)
			result = null;
		else
			result = String.valueOf(object.getId());

		return result;

	}
}
