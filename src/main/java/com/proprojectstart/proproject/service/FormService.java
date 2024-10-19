package com.proprojectstart.proproject.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.proprojectstart.proproject.model.FormModel;
import com.proprojectstart.proproject.respository.FormRepository;

@Service
public class FormService {
@Autowired
		private FormRepository formrepo;
			public void saveRegForm(FormModel model,MultipartFile photo) throws IOException {
				
				formrepo.save(model);
			}
			
}
