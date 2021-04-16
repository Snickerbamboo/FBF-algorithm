package com.comparePix;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
@SuppressWarnings("all")
public class TestFile {
	public static String sampleImgPath = "E:\\in000471.png";
	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		ArrayList<String> a = new ArrayList();
		a.add("123");
		a.add("1234");
		Mat img = new Mat();
		img = Imgcodecs.imread(sampleImgPath);
		System.out.println(img.get(0, 0)[1]);
		Mat c = new Mat(img.rows(),img.cols(),CvType.CV_8UC3);
		byte[] data = new byte[] {(byte) 255,(byte) 255,(byte) 255};
		System.out.println(c.type());
		System.out.println(img.type());
		c.put(0,0, data);
		
		String resultJpgPath = "E:\\in0000751111.jpg";
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
		Mat dst = c.clone();
		Mat erosion = c.clone();
		Imgproc.dilate(c, dst, kernel, new Point(-1, -1), 1);
		Imgproc.erode(dst, erosion, kernel, new Point(-1, -1), 1);
		Imgcodecs.imwrite(resultJpgPath, erosion);
		
		System.out.println(c.type());
		System.out.println(c.get(0, 0)[0]);
		/*
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = new Mat();
		Mat frame = new Mat();
		img = Imgcodecs.imread(sampleImgPath);
		Imgproc.cvtColor(img, frame, Imgproc.COLOR_BGR2GRAY);
		
		File fileInput = new File("E:\\FAFresult\\lightData\\badWeather\\blizzard\\input\\");
		File[] inputList = fileInput.listFiles();
		
		String abs = "E:\\FAFresult\\lightData\\badWeather\\blizzard\\input\\";
		String[] fileName = inputList[1].toString().split("\\\\", 0);
		String aaaa =fileName[fileName.length-1];
		String[] fileName11 = aaaa.split("\\.", 0);
		*/
		

		/*
		File source = new File("E:\\in000075.jpg");
	    File dest = new File("E:\\in0000751.jpg");
	    try {
			copyFileUsingFileChannels(source, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		/*
		double[] arr = randomArray();
		for (int i = 0; i < 1; i++) {
			//bgr图像三通道像素值
			System.out.println(img.get(160,70)[0]);
			System.out.println(img.get(160,70)[1]);
			System.out.println(img.get(160,70)[2]);
			//灰度图像像素值只有一个
			System.out.println(frame.get(160,70)[0]);
		}
		*/
		
		/*
		String fileName = "a.txt";
		try {
			FileWriter writer = new FileWriter(fileName, true);
			long a = 10;
			long b = 5;
			float f = (float) b / a;
			System.out.println(f);
			writer.write("abc" + f + "\n");
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

}
