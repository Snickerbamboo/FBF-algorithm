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
class MyThreadOne implements Runnable {
	/*
	 * private long TPR = 0; private long FPR = 0; private long P = 0; private long
	 * N = 0; private long TP = 0; private long FN = 0; private long FP = 0; private
	 * long TN = 0;
	 */

	private String[] stringThread1 = {};
	private String[] stringThread2 = {};

	public MyThreadOne(String[] String1, String[] String2) {
		this.stringThread1 = String1;
		this.stringThread2 = String2;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		// 加载opencv
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		String[] String3 = { "gmm" };
		String inputdirAll = "E:\\result\\";

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

						String inputdir = inputdirAll + stringThread1[i] + "\\" + stringThread2[j] + "\\" + String3[k]
								+ "\\fore\\";
						String grounddir = inputdirAll + stringThread1[i] + "\\" + stringThread2[j] + "\\groundtruth\\";

						File fileInput = new File(inputdir);
						File[] inputList = fileInput.listFiles();

						File fileGround = new File(grounddir);
						File[] groundList = fileGround.listFiles();

						List fileList = Arrays.asList(inputList);
						Collections.sort(fileList, new Comparator<File>() {
							@Override
							public int compare(File o1, File o2) {
								if (o1.isDirectory() && o2.isFile())
									return -1;
								if (o1.isFile() && o2.isDirectory())
									return 1;

								String fileToint1 = o1.getName();
								String fileToint2 = o2.getName();
								fileToint1 = fileToint1.substring(0, fileToint1.indexOf('.'));
								fileToint2 = fileToint2.substring(0, fileToint2.indexOf('.'));
								int a = Integer.parseInt(fileToint1);
								int b = Integer.parseInt(fileToint2);
								int panduan = 0;
								if (a > b) {
									panduan = 1;
								} else if (a == b) {
									panduan = 0;
								} else {
									panduan = -1;
								}
								return panduan;
							}
						});

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
						resultTXT = "E:\\result\\resultTXT\\resultTXT-1\\" + stringThread2[j] + String3[k] + ".txt";
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

public class GogoOne {
	public static void main(String[] args) {
		String[] stringThread11 = { "intermittentObjectMotion" };
		String[] stringThread12 = { "sofa" };

		MyThreadOne mt1 = new MyThreadOne(stringThread11, stringThread12);


		Thread t1 = new Thread(mt1);


		t1.start();


	}
};