package com.etiya.recap.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etiya.recap.business.abstracts.ColorService;
import com.etiya.recap.business.constants.Messages;
import com.etiya.recap.core.utilities.results.DataResult;
import com.etiya.recap.core.utilities.results.Result;
import com.etiya.recap.core.utilities.results.SuccessDataResult;
import com.etiya.recap.core.utilities.results.SuccessResult;
import com.etiya.recap.dataAccess.abstracts.ColorDao;
import com.etiya.recap.entities.concretes.Color;
import com.etiya.recap.entities.requests.create.CreateColorRequest;
import com.etiya.recap.entities.requests.delete.DeleteColorRequest;
import com.etiya.recap.entities.requests.update.UpdateColorRequest;

@Service
public class ColorManager implements ColorService{

	private ColorDao colorDao;
	
	@Autowired
	public ColorManager(ColorDao colorDao) {
		this.colorDao = colorDao;
	}

	@Override
	public DataResult<List<Color>> getAll() {
		return new SuccessDataResult<List<Color>>(this.colorDao.findAll(),  Messages.GetAll);
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) {
		Color color=new Color();
		color.setColorName(createColorRequest.getColorName());
		
		this.colorDao.save(color);
		return new SuccessResult(true, Messages.Add); 
	}

	@Override
	public DataResult<Color> getById(int id) {
		return new SuccessDataResult<Color>(this.colorDao.getById(id),  Messages.GetById);
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) {
		Color color=new Color();
		color.setColorId(deleteColorRequest.getId());
		
		this.colorDao.delete(color);
		return new SuccessResult(true,  Messages.Delete);
		
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {
		Color color=this.colorDao.getById(updateColorRequest.getId());
		color.setColorId(updateColorRequest.getId());
		color.setColorName(updateColorRequest.getColorName());
		
		this.colorDao.save(color);
		return new SuccessResult(true,  Messages.Update);
	}

}
