package com.comparePix;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import utils.FileUtils;

public class DetectMain {

	public static void main(String[] args) {
		//������
		// ����opencv
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		//��������ģ��
		FileUtils.getBackSet();
		
		//ǰ��
		FileUtils.getFrontSet();
		
	}
}
