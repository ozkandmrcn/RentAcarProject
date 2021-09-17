package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.Color;
import com.etiya.recap.entities.requests.CreateColorRequest;

public interface ColorService {
	
	DataResult<List<Color>> getAll();
	
	Result add(CreateColorRequest createColorRequest);
	
	DataResult<Color> getById(int id);
	
	Result delete(Color color);
	
	Result update(Color color);

}
