package com.etiya.recap.core.filehelper;

import java.io.IOException;

import com.etiya.recap.entities.requests.create.CreateCarImagesRequest;
import com.etiya.recap.entities.requests.update.UpdateCarImagesRequest;


public interface FileHelperService {
	
	  void  createCarImagePathName(CreateCarImagesRequest createCarImagesRequest,String imagePathName) throws IOException;
	  
	  void  updateCarImagePathName(UpdateCarImagesRequest updateCarImagesRequest,String imagePathName) throws IOException;


}
