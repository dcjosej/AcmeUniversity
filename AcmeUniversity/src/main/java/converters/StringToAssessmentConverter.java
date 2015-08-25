package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.AssessmentRepository;
import domain.Assessment;


@Component
@Transactional
public class StringToAssessmentConverter implements Converter<String, Assessment>{

	@Autowired
	private AssessmentRepository repository;

	@Override
	public Assessment convert(String text) {
		Assessment result;
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
