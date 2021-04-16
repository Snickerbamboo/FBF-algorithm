package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
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
public class FileUtils {

	// ����ͼƬ·����ȡͼƬ��С�õ�
	public static String sampleImgPath = "E:\\FAFresult\\lightData\\in000001.jpg";
	// ����ģ�ͽ���ͼƬ
	public static String backsetJpgPath = "E:\\FAFresult\\lightData2\\dynamicBackground\\canoe\\input\\in000002.jpg";
	// ����ģ��csvд�뼰����
	public static String backsetCSVPath = "E:\\FAFresult\\lightData2\\dynamicBackground\\canoe\\csv\\picBack.csv";
	public static String backsetCSVBeiFenPath = "E:\\FAFresult\\lightData2\\dynamicBackground\\canoe\\picBack.csv";
	// ǰ��ͼƬ�ļ���
	public static String intputDir = "E:\\FAFresult\\lightData2\\dynamicBackground\\canoe\\input";
	public static String intputCSVDir = "E:\\FAFresult\\lightData2\\dynamicBackground\\canoe\\csv";
	public static String FAFresultDir = "E:\\FAFresult\\lightData2\\dynamicBackground\\canoe\\FAFresult";

	// ����ͼƬ����Ϊ��������Ϊ����
	public static Mat imgSample = Imgcodecs.imread(FileUtils.sampleImgPath);
	public static int height = imgSample.rows();
	public static int width = imgSample.cols();

// ����

	// ����������ֵ����20���������Աȣ�ԽСԽ�����ж�Ϊǰ����СһЩЧ���ã�Ŀǰ20��ã�10���У�40����
	// �б߿�͵���yuMeanQianjing������yuMean
	public static int yuMean = 15;
	public static int yuFangcha = 15;
	// ������ֵ�ĸ�������
	public static int yuMeanCountQianjing = 0;
	public static int yuFangchaCountQianjing = 0;
	public static int yuMeanCountBeijing = 0;
	public static int yuFangchaCountBeijing = 0;
	// ��������ֵ��ԽСԽ�����ж�Ϊǰ�������ڸø�������ǰ��
	public static int yuMeanQianjing = 18;
	public static int yuFangchaQianjing = 18;
	// ���±���ʱ�ĸ���,seita��16��֮һ���ʸ��¸õ㣬updateM�Ǵ�20��������ѡһ�����£�8�Ƕ����ϱ��˸������ĸ�����
	public static int seita = 16;
	// ���ڸ���ͼƬ��������seita
	public static int seitaChange = 16;
	public static int seitaChangePicNum = 773;

	public static int updateM = 20;
	public static int updateNeig = 8;

	public static int countQianjingPinLv = 0;
	public static int countQianjingPinLvYuZhi = 6;
	public static int[][] countQianjingPinLvArray = new int[height][width];

	public static int countQianjingPanDuan = 0;
	public static int countQianjingPanDuanYuZhi = 5;
	public static int[][] countQianjingPanDuanArray = new int[height][width];

	public static int countQianjingImage = 0;
	public static int countQianjingImageYuZhi = 10;
	public static int[][] countQianjingImageArray = new int[height][width];

	public static int XINHAO = 0;
	public static int imgPicCHANGE = 0;
	public static int countQianjingPanDuanYuZhiCHANGE = 5;
	// private static String backSetJpgPath = "dddd";

// ����Ķ��Ĳ���
	public static int IOUse = 0;
	public static int PANDUAN = 0;
	public static int CSVDeleteGhost = 0;

// �����������
	private static void updateBackset(String csvpathFront, String fileName) {

		List<String[]> dataBackset = new ArrayList<String[]>();
		dataBackset = readCsv(backsetCSVPath);
		List<String[]> dataFrontset = new ArrayList<String[]>();
		dataFrontset = readCsv(csvpathFront);
		// ����ȡ��������img����ʵ����Ҫ��һ�����ӣ����������أ�ֻҪ��С�Ծ���,Ȼ���½�һ��mat���������ͣ�ԭ�ȵ�mat��byte�ͣ��Ų���255
		Mat simple = new Mat();
		simple = Imgcodecs.imread(sampleImgPath);
		Mat img = new Mat(simple.rows(),simple.cols(),CvType.CV_8UC3);
		byte[] White = new byte[] {(byte) 255,(byte) 255,(byte) 255};
		byte[] Black = new byte[] {0,0,0};

		for (int i = 0; i < dataFrontset.size(); i++) {
			for (int k = 0; k < 20; k++) {
				if (Math.abs(Double.valueOf(dataFrontset.get(i)[1])
						- Double.valueOf(dataBackset.get(20 * i + k)[1])) > yuMean) {
					yuMeanCountQianjing = yuMeanCountQianjing + 1;
				} else {
					yuMeanCountBeijing = yuMeanCountBeijing + 1;
				}
				if (Math.abs(Double.valueOf(dataFrontset.get(i)[2])
						- Double.valueOf(dataBackset.get(20 * i + k)[2])) > yuFangcha) {
					yuFangchaCountQianjing = yuFangchaCountQianjing + 1;
				} else {
					yuFangchaCountBeijing = yuFangchaCountBeijing + 1;
				}

			}
			String[] tempSplit = dataFrontset.get(i)[0].split(",", 0);
			int x = Integer.valueOf(tempSplit[0]);
			int y = Integer.valueOf(tempSplit[1]);
			countQianjingImageArray[x][y] = countQianjingImageArray[x][y] + 1;

			if ((yuMeanCountQianjing > yuMeanQianjing) && (yuFangchaCountQianjing > yuFangchaQianjing)) {
				
				//Ϳ�ף�ǰ��255255255
				img.put(x,y, White);

				if (countQianjingPinLvArray[x][y] < countQianjingPinLvYuZhi) {
					countQianjingPinLvArray[x][y] = countQianjingPinLvArray[x][y] + 1;
				}
				if (countQianjingPinLv < countQianjingPinLvArray[x][y]) {
					countQianjingPinLv = countQianjingPinLvArray[x][y];
				}
			} else {
				//Ϳ�ڣ�����000
				img.put(x,y, Black);


				// ȡ��λ���Ա�
				int panduanFilename = Integer.valueOf(fileName.substring(fileName.length() - 3));
				if (panduanFilename > seitaChangePicNum) {
					seita = seitaChange;
				}

				// ������һ�������У���������1/seita�ĸ��ʣ�һ����1�ȽϺ�
				int[] randomseita = randomArray(1, seita);
				if (randomseita[0] == 1) {
					int[] randomupdateM = randomArray(1, updateM);
					if (XINHAO == 0) {
						// ���±���csv��mean�ͷ���
						String[] tempStrArrMeanFangCha = { dataBackset.get(i)[0], dataFrontset.get(i)[1],
								dataFrontset.get(i)[2] };
						dataBackset.set(20 * i + randomupdateM[0], tempStrArrMeanFangCha);
					}

					// ��ȡ�ռ���·��򣬱�֤�ռ�һ����
					int x1 = 0;
					int y1 = 0;
					// 0-7֮������һ����
					int[] randomupdateNeig = randomArray(1, 8);
					if (randomupdateNeig[0] == 0) {
						x1 = x - 1;
						y1 = y - 1;
					} else if (randomupdateNeig[0] == 1) {
						x1 = x - 1;
						y1 = y;
					} else if (randomupdateNeig[0] == 2) {
						x1 = x - 1;
						y1 = y + 1;
					} else if (randomupdateNeig[0] == 3) {
						x1 = x;
						y1 = y - 1;
					} else if (randomupdateNeig[0] == 4) {
						x1 = x;
						y1 = y + 1;
					} else if (randomupdateNeig[0] == 5) {
						x1 = x + 1;
						y1 = y - 1;
					} else if (randomupdateNeig[0] == 6) {
						x1 = x + 1;
						y1 = y;
					} else if (randomupdateNeig[0] == 7) {
						x1 = x + 1;
						y1 = y + 1;
					}
					int ikong = x1 * width + y1;
					if (x1 >= 0 && x1 <= height - 1 && y1 >= 0 && y1 <= width - 1) {
						String[] tempStrArrMeanFangChaKong = { dataBackset.get(i)[0], dataFrontset.get(i)[1],
								dataFrontset.get(i)[2] };
						dataBackset.set(20 * ikong + randomupdateM[0], tempStrArrMeanFangChaKong);
					}
				}

			}

			// ���õ㵽����ֵ7��ͼ�������ʮ�����ڣ������ж�����
			if (countQianjingPinLvArray[x][y] == countQianjingPinLvYuZhi
					&& countQianjingImageArray[x][y] <= countQianjingImageYuZhi && IOUse == 0) {
				// �õ���ж�+1,��IOUse��Ϊ1��ֱ���¸�ʮ���ٿ�ʼ������ж��������
				countQianjingPanDuanArray[x][y] = countQianjingPanDuanArray[x][y] + 1;
				IOUse = 1;
			}

			// �������ʮ�ţ��õ㻹δ�ﵽ��ֵ7���õ��ж�����
			if (countQianjingPinLvArray[x][y] < countQianjingPinLvYuZhi
					&& countQianjingImageArray[x][y] == countQianjingImageYuZhi) {
				countQianjingPanDuanArray[x][y] = 0;
			}

			// ������ô��������ʮ�ţ�Ƶ�ʺ�ͼ�����������,�����źŽ���һ�ι�Ӱ�ж������жϣ���Ϊ3�ĵ��Ӱ����
			if (countQianjingImageArray[x][y] == countQianjingImageYuZhi) {
				countQianjingPinLvArray[x][y] = 0;
				countQianjingImageArray[x][y] = 0;
				IOUse = 0; // ����IOUse�������µ��ж�
				PANDUAN = 1;
			}

			yuMeanCountQianjing = 0;
			yuFangchaCountQianjing = 0;
			yuMeanCountBeijing = 0;
			yuFangchaCountBeijing = 0;

			// ��Ӱ����
			if (PANDUAN == 1) {
				if (countQianjingPanDuanArray[x][y] == countQianjingPanDuanYuZhi) {
					// �ѱ�����20�����ֵȫ���滻Ϊ��ǰ֡ͼ����Ӧλ�õ�����ֵ
					for (int k = 0; k < 20; k++) {
						String[] tempStrArrMeanFangChaGui = { dataBackset.get(i)[0], dataFrontset.get(i)[1],
								dataFrontset.get(i)[2] };
						dataBackset.set(20 * i + k, tempStrArrMeanFangChaGui);
					}
					countQianjingPanDuanArray[x][y] = 0; // �ж�����
				}
				if (countQianjingPanDuanArray[x][y] > countQianjingPanDuanYuZhi) {
					countQianjingPanDuanArray[x][y] = 0; // �ж�����
				}
				PANDUAN = 0;
			}

		}

		// ������
		String resultJpgPath = FAFresultDir + "\\" + fileName + ".png";

		// ��̬ѧȥ��
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
		Mat dst = img.clone();
		Mat erosion = img.clone();
		Imgproc.dilate(img, dst, kernel, new Point(-1, -1), 1);
		Imgproc.erode(dst, erosion, kernel, new Point(-1, -1), 1);
		Imgcodecs.imwrite(resultJpgPath, erosion);

		System.out.println(resultJpgPath);
		System.out.println(seita);

		// csv�ļ�д��
		try {
			CsvWriter csvWriter = new CsvWriter(backsetCSVPath);
			String[] writeLineHead = { "name", "mean", "fangcha" };
			csvWriter.writeRecord(writeLineHead);

			for (int i = 0; i < dataBackset.size(); i++) {
				String[] writeLineBackset = { dataBackset.get(i)[0], dataBackset.get(i)[1], dataBackset.get(i)[2] };
				csvWriter.writeRecord(writeLineBackset);
			}
			csvWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

// csv����
	public static List<String[]> readCsv(String path) {
		List<String[]> csvList = new ArrayList<String[]>();
		try {
			CsvReader csvReader = new CsvReader(path, ',', Charset.forName("GBK"));
			// ����ͷ
			csvReader.readHeaders();
			while (csvReader.readRecord()) {
				csvList.add(csvReader.getValues());
			}
			csvReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("��ȡcsv�ļ�<{}>ʧ�ܣ����쳣����" + path);
		} catch (IOException e) {
			System.out.println("��ȡcsv�ļ�<{}>ʧ�ܣ����쳣����" + path);
		}
		return csvList;
	}

// ǰ�����
	public static void getFrontSet() {
		File fileInput = new File(intputDir);
		File[] inputList = fileInput.listFiles();
		for (int i = 0; i < inputList.length; i++) {
			if (i <= imgPicCHANGE) {
			} else {
				countQianjingPanDuanYuZhi = countQianjingPanDuanYuZhiCHANGE;
			}

			// temp1���Ϊin0001.jpg
			String[] fileNameIMG = inputList[i].toString().split("\\\\", 0);
			String Temp1 = fileNameIMG[fileNameIMG.length - 1];
			// fileName���Ϊin0001
			String[] fileNameReal = Temp1.split("\\.", 0);
			String fileName = fileNameReal[0];

			String imgpathFront = intputDir + "\\" + fileName + ".jpg";
			String csvpathFront = intputCSVDir + "\\" + fileName + ".csv";
			System.out.println(imgpathFront);
			runFront(imgpathFront, csvpathFront);

			// ��������
			updateBackset(csvpathFront, fileName);

		}
	}

	public static void runFront(String imgpath, String csvpath) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = new Mat();
		Mat frame = new Mat();
		img = Imgcodecs.imread(imgpath);
		Imgproc.cvtColor(img, frame, Imgproc.COLOR_BGR2GRAY);

		ArrayList<String> name = new ArrayList<String>(20 * height * width);
		ArrayList<String> mean = new ArrayList<String>(20 * height * width);
		ArrayList<String> fangcha = new ArrayList<String>(20 * height * width);
		int N = 9;
		int x = 0;
		int y = 0;
		for (x = 0; x < height; x++) {
			for (y = 0; y < width; y++) {
				double[] jisuan = getJiSuan(x, y, frame);
				name.add(x + "," + y);
				// ƽ����
				String meansum = String.format("%.4f", meancal(jisuan, N)).toString();
				mean.add(meansum);

				// ����
				String fangchasum = String.format("%.4f", fangchacal(jisuan, N)).toString();
				fangcha.add(fangchasum);
			}
		}

		// csv�ļ�д��
		try {
			CsvWriter csvWriter = new CsvWriter(csvpath);
			String[] writeLineHead = { "name", "mean", "fangcha" };
			csvWriter.writeRecord(writeLineHead);

			for (int i = 0; i < name.size(); i++) {
				String[] writeLineBackset = { name.get(i), mean.get(i), fangcha.get(i) };
				csvWriter.writeRecord(writeLineBackset);
			}
			csvWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

// �������
	public static void getBackSet() {
		// ����opencv
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat img = new Mat();
		Mat frame = new Mat();
		img = Imgcodecs.imread(backsetJpgPath);
		Imgproc.cvtColor(img, frame, Imgproc.COLOR_BGR2GRAY);

		ArrayList<String> name = new ArrayList<String>(20 * height * width);
		ArrayList<String> mean = new ArrayList<String>(20 * height * width);
		ArrayList<String> fangcha = new ArrayList<String>(20 * height * width);
		int N = 9;
		int x1 = 0;
		int y1 = 0;

		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				int[] MYrandomseed = randomArray(20, 8);
				for (int count = 0; count < 20; count++) {
					if (MYrandomseed[count] == 0) {
						x1 = x - 1;
						y1 = y - 1;
					} else if (MYrandomseed[count] == 1) {
						x1 = x - 1;
						y1 = y;
					} else if (MYrandomseed[count] == 2) {
						x1 = x - 1;
						y1 = y + 1;
					} else if (MYrandomseed[count] == 3) {
						x1 = x;
						y1 = y - 1;
					} else if (MYrandomseed[count] == 4) {
						x1 = x;
						y1 = y + 1;
					} else if (MYrandomseed[count] == 5) {
						x1 = x + 1;
						y1 = y - 1;
					} else if (MYrandomseed[count] == 6) {
						x1 = x + 1;
						y1 = y;
					} else if (MYrandomseed[count] == 7) {
						x1 = x + 1;
						y1 = y + 1;
					}

					double[] jisuan = getJiSuan(x1, y1, frame);

					name.add(x + "," + y);
					// ƽ����
					String meansum = String.format("%.4f", meancal(jisuan, N)).toString();
					mean.add(meansum);

					// ����
					String fangchasum = String.format("%.4f", fangchacal(jisuan, N)).toString();
					fangcha.add(fangchasum);

				}
			}
		}
		// csv�ļ�д��
		try {
			CsvWriter csvWriter = new CsvWriter(backsetCSVPath);
			String[] writeLineHead = { "name", "mean", "fangcha" };
			csvWriter.writeRecord(writeLineHead);

			for (int i = 0; i < name.size(); i++) {
				String[] writeLineBackset = { name.get(i), mean.get(i), fangcha.get(i) };
				csvWriter.writeRecord(writeLineBackset);
			}
			csvWriter.close();
			// ����csv
			File source = new File(backsetCSVPath);
			File dest = new File(backsetCSVBeiFenPath);
			copyFileUsingFileChannels(source, dest);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static int[] randomArray(int len, int max) {
		int[] arr = new int[len];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * max);
		}
		return arr;
	}

	private static double[] getJiSuan(int x, int y, Mat frame) {
		double[] linshi = null;
		if (x == -1 && y == -1) {
			linshi = new double[] { 0, 0, 0, 0, 0, 0, 0, 0, frame.get(0, 0)[0] };

		} else if (x == 0 && y == -1) {
			linshi = new double[] { 0, 0, 0, 0, 0, frame.get(0, 0)[0], 0, 0, frame.get(1, 0)[0] };

		} else if (x > 0 && x < height - 1 && y == -1) {
			linshi = new double[] { 0, 0, frame.get(x - 1, 0)[0], 0, 0, frame.get(x, 0)[0], 0, 0,
					frame.get(x + 1, 0)[0] };

		} else if (x == height - 1 && y == -1) {
			linshi = new double[] { 0, 0, frame.get(x - 1, 0)[0], 0, 0, frame.get(x, 0)[0], 0, 0, 0 };

		} else if (x == height && y == -1) {
			linshi = new double[] { 0, 0, frame.get(x - 1, 0)[0], 0, 0, 0, 0, 0, 0 };

		} else if (x == -1 && y == 0) {
			linshi = new double[] { 0, 0, 0, 0, 0, 0, 0, frame.get(0, 0)[0], frame.get(0, 1)[0] };

		} else if (x == -1 && y > 0 && y < width - 1) {
			linshi = new double[] { 0, 0, 0, 0, 0, 0, frame.get(0, y - 1)[0], frame.get(0, y)[0],
					frame.get(0, y + 1)[0] };

		} else if (x == -1 && y == width - 1) {
			linshi = new double[] { 0, 0, 0, 0, 0, 0, frame.get(0, y - 1)[0], frame.get(0, y)[0], 0 };

		} else if (x == -1 && y == width) {
			linshi = new double[] { 0, 0, 0, 0, 0, 0, frame.get(0, y - 1)[0], 0, 0 };

		} else if (x == 0 && y == width) {
			linshi = new double[] { 0, 0, 0, frame.get(0, y - 1)[0], 0, 0, frame.get(1, y - 1)[0], 0, 0 };

		} else if (x > 0 && x < height - 1 && y == width) {
			linshi = new double[] { frame.get(x - 1, y - 1)[0], 0, 0, frame.get(x, y - 1)[0], 0, 0,
					frame.get(x + 1, y - 1)[0], 0, 0 };

		} else if (x == height - 1 && y == width) {
			linshi = new double[] { frame.get(x - 1, y - 1)[0], 0, 0, frame.get(x, y - 1)[0], 0, 0, 0, 0, 0 };

		} else if (x == height && y == 0) {
			linshi = new double[] { 0, frame.get(x - 1, y)[0], frame.get(x - 1, y + 1)[0], 0, 0, 0, 0, 0, 0 };

		} else if (x == height && y > 0 && y < width - 1) {
			linshi = new double[] { frame.get(x - 1, y - 1)[0], frame.get(x - 1, y)[0], frame.get(x - 1, y + 1)[0], 0,
					0, 0, 0, 0, 0 };

		} else if (x == height && y == width - 1) {
			linshi = new double[] { frame.get(x - 1, y - 1)[0], frame.get(x - 1, y)[0], 0, 0, 0, 0, 0, 0, 0 };

		} else if (x == height && y == width) {
			linshi = new double[] { frame.get(x - 1, y - 1)[0], 0, 0, 0, 0, 0, 0, 0, 0 };

			// ��Ȧ
		} else if (x == 0 && y == 0) {
			linshi = new double[] { 0, 0, 0, 0, frame.get(x, y)[0], frame.get(x, y + 1)[0], 0, frame.get(x + 1, y)[0],
					frame.get(x + 1, y + 1)[0] };

		} else if (x > 0 && x < height - 1 && y == 0) {
			linshi = new double[] { 0, frame.get(x - 1, y)[0], frame.get(x - 1, y + 1)[0], 0, frame.get(x, y)[0],
					frame.get(x, y + 1)[0], 0, frame.get(x + 1, y)[0], frame.get(x + 1, y + 1)[0] };

		} else if (x == height - 1 && y == 0) {
			linshi = new double[] { 0, frame.get(x - 1, y)[0], frame.get(x - 1, y + 1)[0], 0, frame.get(x, y)[0],
					frame.get(x, y + 1)[0], 0, 0, 0 };

		} else if (x == 0 && y > 0 && y < width - 1) {
			linshi = new double[] { 0, 0, 0, frame.get(x, y - 1)[0], frame.get(x, y)[0], frame.get(x, y + 1)[0],
					frame.get(x + 1, y - 1)[0], frame.get(x + 1, y)[0], frame.get(x + 1, y + 1)[0] };

		} else if (x == 0 && y == width - 1) {
			linshi = new double[] { 0, 0, 0, frame.get(x, y - 1)[0], frame.get(x, y)[0], 0, frame.get(x + 1, y - 1)[0],
					frame.get(x + 1, y)[0], 0 };

		} else if (x > 0 && x < height - 1 && y == width - 1) {
			linshi = new double[] { frame.get(x - 1, y - 1)[0], frame.get(x - 1, y)[0], 0, frame.get(x, y - 1)[0],
					frame.get(x, y)[0], 0, frame.get(x + 1, y - 1)[0], frame.get(x + 1, y)[0], 0 };

		} else if (x == height - 1 && y == width - 1) {
			linshi = new double[] { frame.get(x - 1, y - 1)[0], frame.get(x - 1, y)[0], 0, frame.get(x, y - 1)[0],
					frame.get(x, y)[0], 0, 0, 0, 0 };

		} else if (x == height - 1 && y > 0 && y < width - 1) {
			linshi = new double[] { frame.get(x - 1, y - 1)[0], frame.get(x - 1, y)[0], frame.get(x - 1, y + 1)[0],
					frame.get(x, y - 1)[0], frame.get(x, y)[0], frame.get(x, y + 1)[0], 0, 0, 0 };

			// �м�,����ifelse�Ǵ�������ִ�У���˱�ע�͵Ĳ��ֲ���Ӱ������x��y��Ϊ0������ᱻ�������ifelseִ�е�������
			// } else if (x > -1 && x < height && y > -1 && y < width) {
		} else if (x > 0 && x < height - 1 && y > 0 && y < width - 1) {
			linshi = new double[] { frame.get(x - 1, y - 1)[0], frame.get(x - 1, y)[0], frame.get(x - 1, y + 1)[0],
					frame.get(x, y - 1)[0], frame.get(x, y)[0], frame.get(x, y + 1)[0], frame.get(x + 1, y - 1)[0],
					frame.get(x + 1, y)[0], frame.get(x + 1, y + 1)[0] };

		}
		return linshi;

	}

	private static double meancal(double[] jisuan, int N) {
		double k = 0;
		for (int i = 0; i < N; i++) {
			k = k + jisuan[i];
		}
		return k / N;
	}

	private static double fangchacal(double[] jisuan, int N) {
		double k = 0;
		double fenmu = 0;
		for (int i = 0; i < N; i++) {
			k = k + jisuan[i];
		}
		k = k / N;
		for (int i = 0; i < N; i++) {
			fenmu = fenmu + (jisuan[i] - k) * (jisuan[i] - k);
		}
		double fangcha = fenmu / N;
		return fangcha;
	}

	private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(source).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}
}
