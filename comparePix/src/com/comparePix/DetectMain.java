package com.comparePix;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import utils.FileUtils;

public class DetectMain {

	public static void main(String[] args) {
		//主函数
		// 加载opencv
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		//建立背景模型
		FileUtils.getBackSet();
		
		//前景
		FileUtils.getFrontSet();
		
	}
}
