package com.vbashur.grava.game;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.vbashur.grava.game.GravaEvent.OnCapturingStone;

@Service
public class StoneCaptureHandler  implements ApplicationListener<GravaEvent.OnCapturingStone>  {

	@Override
	public void onApplicationEvent(OnCapturingStone arg0) {
		System.out.println("stone is captured");
		
	}

}
