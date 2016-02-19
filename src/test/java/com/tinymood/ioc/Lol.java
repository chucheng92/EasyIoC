package com.tinymood.ioc;

import com.tinymood.ioc.annotation.Autowired;
import com.tinymood.ioc.FaceService;

public class Lol {
	@Autowired
	private FaceService faceService;
	
	public void work() {
		faceService.buy("剑圣", 5);
	}

	public FaceService getFaceService() {
		return faceService;
	}
}
