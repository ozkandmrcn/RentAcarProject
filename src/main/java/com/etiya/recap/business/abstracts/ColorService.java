package com.etiya.recap.business.abstracts;

import java.util.List;

import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.entities.concretes.Color;
import com.etiya.recap.entities.requests.create.CreateColorRequest;
import com.etiya.recap.entities.requests.delete.DeleteColorRequest;
import com.etiya.recap.entities.requests.update.UpdateColorRequest;

public interface ColorService {
	
	DataResult<List<Color>> getAll();
	
	DataResult<Color> getById(int id);
	
	Result add(CreateColorRequest createColorRequest);
	
	Result delete(DeleteColorRequest deleteColorRequest);
	
	Result update(UpdateColorRequest updateColorRequest);

}
