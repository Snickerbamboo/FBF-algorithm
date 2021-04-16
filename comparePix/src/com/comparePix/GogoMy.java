package com.comparePix;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

@SuppressWarnings("all")
class MyThreadMy implements Runnable {
	/*
	 * private long TPR = 0; private long FPR = 0; private long P = 0; private long
	 * N = 0; private long TP = 0; private long FN = 0; private long FP = 0; private
	 * long TN = 0;
	 */

	private String[] stringThread1 = {};
	private String[] stringThread2 = {};

	public MyThreadMy(String[] String1, String[] String2) {
		this.stringThread1 = String1;
		this.stringThread2 = String2;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// 加载opencv
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		String[] String3 = { "FAFresult" };
		String inputdirAll = "D:\\zhaoShuo\\calculateAccuracy\\calculateAccuracy\\csvJiHe\\";
		String inputdirAll2 = "D:\\zhaoShuo\\calculateAccuracy\\calculateAccuracy\\csvJiHeTianchong\\";
		
		try {
			for (int i = 0; i < stringThread1.length; i++) {
				for (int j = 0; j < stringThread2.length; j++) {
					for (int k = 0; k < String3.length; k++) {
						// 每个文件夹都是重新计算
						float TPR = 0;
						float FPR = 0;

						long P = 0;
						long N = 0;

						long TP = 0;
						long FN = 0;
						long FP = 0;
						long TN = 0;

						String inputdir = inputdirAll2 + stringThread1[i] + "\\" + stringThread2[j] + "\\" + String3[k]
								+ "\\";
						String grounddir = inputdirAll + stringThread1[i] + "\\" + stringThread2[j] + "\\groundtruth\\";

						File fileInput = new File(inputdir);
						File[] inputList = fileInput.listFiles();

						File fileGround = new File(grounddir);
						File[] groundList = fileGround.listFiles();

						

						// System.out.println(inputList[1].getName());
						// System.out.println(inputdir);
						// System.out.println(groundList[10].getName());

						for (int w = 0; w < inputList.length; w++) {
							String imgpathInputFront = (inputList[w]).toString();
							Mat imgInput = new Mat();
							imgInput = Imgcodecs.imread(imgpathInputFront);
							// System.out.println(imgpathInputFront);
							// System.out.println(imgInput.get(100,100)[1]);

							String imgpathGroundFront = (groundList[w]).toString();
							Mat imgGround = new Mat();
							imgGround = Imgcodecs.imread(imgpathGroundFront);

							// height
							int img_rows = imgInput.rows();
							// width
							int img_colums = 0;
							img_colums = imgInput.cols();

							double[] white = imgGround.get(0, 0);
							white[0] = 255;
							white[1] = 255;
							white[2] = 255;

							double[] black = imgGround.get(0, 0);
							black[0] = 0;
							black[1] = 0;
							black[2] = 0;

							for (int x = 0; x < img_rows; x++) {
								for (int y = 0; y < img_colums; y++) {
									if (imgGround.get(x, y)[0] == white[0] && imgInput.get(x, y)[0] == white[0]) {
										TP = TP + 1;
									}
									if (imgGround.get(x, y)[0] == black[0] && imgInput.get(x, y)[0] == white[0]) {
										FP = FP + 1;
									}
									if (imgGround.get(x, y)[0] == white[0]) {
										P = P + 1;
									}
									if (imgGround.get(x, y)[0] == black[0]) {
										N = N + 1;
									}

								}
							}

						}

						System.out.println(
								Thread.currentThread().getName() + "," + inputdir + "," + Long.valueOf(TP).toString());
						FN = P - TP;
						TN = N - FP;

						float Recall = (float) TP / (TP + FN); // recall就是TPR
						float Specificity = (float) TN / (FP + TN); // TNR
						FPR = (float) FP / (FP + TN);
						float FNR = (float) FN / (FN + TP);
						float PCC = (float) (TP + TN) / (TP + TN + FP + FN); // Percentage of Correct Classification
						float Precision = (float) TP / (TP + FP);
						float Fmeasure = (2 * Precision * Recall) / (Precision + Recall);

						String resultTXT = "";
						resultTXT = "D:\\zhaoShuo\\calculateAccuracy\\calculateAccuracy\\resulttxtTianchong\\" + stringThread2[j] + String3[k] + ".txt";
						FileWriter writer = new FileWriter(resultTXT, true);
						String str1 = "Recall is " + Float.valueOf(Recall).toString() + "\n";
						String str2 = "Specificity is " + Float.valueOf(Specificity).toString() + "\n";
						String str3 = "FPR is " + Float.valueOf(FPR).toString() + "\n";
						String str4 = "FNR is " + Float.valueOf(FNR).toString() + "\n";
						String str5 = "PCC is " + Float.valueOf(PCC).toString() + "\n";
						String str6 = "Precision is " + Float.valueOf(Precision).toString() + "\n";
						String str7 = "Fmeasure is " + Float.valueOf(Fmeasure).toString() + "\n";
						String str8 = "TP is" + Long.valueOf(TP).toString() + "\n";
						String str9 = "TN is" + Long.valueOf(TN).toString() + "\n";
						String str10 = "FP is" + Long.valueOf(FP).toString() + "\n";
						String str11 = "FN is" + Long.valueOf(FN).toString() + "\n";
						String str12 = "N is" + Long.valueOf(N).toString() + "\n";
						String str13 = "P is" + Long.valueOf(P).toString() + "\n";
						String strWrite = str1 + str2 + str3 + str4 + str5 + str6 + str7 + str8 + str9 + str10 + str11
								+ str12 + str13;
						writer.write(strWrite);
						writer.flush();
						writer.close();
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.toString());
		}

	}
};

public class GogoMy {
	public static void main(String[] args) {
		
		String[] stringThread11 = { "badWeather" };
		String[] stringThread12 = { "blizzard", "skating", "snowFall", "wetSnow" };

		String[] stringThread21 = { "baseline" };
		String[] stringThread22 = { "highway", "office", "pedestrians", "PETS2006" };

		String[] stringThread31 = { "cameraJitter" };
		String[] stringThread32 = { "badminton", "boulevard", "sidewalk", "traffic" };

		String[] stringThread41 = { "intermittentObjectMotion" };
		String[] stringThread42 = { "abandonedBox", "parking", "sofa", "streetLight", "tramstop", "winterDriveway" };

		String[] stringThread51 = { "shadow" };
		String[] stringThread52 = { "backdoor", "bungalows", "busStation", "copyMachine", "cubicle", "peopleInShade" };

		MyThreadMy mt1 = new MyThreadMy(stringThread11, stringThread12);
		MyThreadMy mt2 = new MyThreadMy(stringThread21, stringThread22);
		MyThreadMy mt3 = new MyThreadMy(stringThread31, stringThread32);
		MyThreadMy mt4 = new MyThreadMy(stringThread41, stringThread42);
		MyThreadMy mt5 = new MyThreadMy(stringThread51, stringThread52);

		Thread t1 = new Thread(mt1);
		Thread t2 = new Thread(mt2);
		Thread t3 = new Thread(mt3);
		Thread t4 = new Thread(mt4);
		Thread t5 = new Thread(mt5);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();

	}
};