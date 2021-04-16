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

	// 样例图片路径，取图片大小用的
	public static String sampleImgPath = "E:\\FAFresult\\lightData\\in000001.jpg";
	// 背景模型建立图片
	public static String backsetJpgPath = "E:\\FAFresult\\lightData2\\dynamicBackground\\canoe\\input\\in000002.jpg";
	// 背景模型csv写入及备份
	public static String backsetCSVPath = "E:\\FAFresult\\lightData2\\dynamicBackground\\canoe\\csv\\picBack.csv";
	public static String backsetCSVBeiFenPath = "E:\\FAFresult\\lightData2\\dynamicBackground\\canoe\\picBack.csv";
	// 前景图片文件夹
	public static String intputDir = "E:\\FAFresult\\lightData2\\dynamicBackground\\canoe\\input";
	public static String intputCSVDir = "E:\\FAFresult\\lightData2\\dynamicBackground\\canoe\\csv";
	public static String FAFresultDir = "E:\\FAFresult\\lightData2\\dynamicBackground\\canoe\\FAFresult";

	// 读入图片，高为行数，宽为列数
	public static Mat imgSample = Imgcodecs.imread(FileUtils.sampleImgPath);
	public static int height = imgSample.rows();
	public static int width = imgSample.cols();

// 参数

	// 设置两个阈值，与20个背景作对比，越小越容易判断为前景，小一些效果好，目前20最好，10不行，40不行
	// 有边框就调高yuMeanQianjing，调低yuMean
	public static int yuMean = 15;
	public static int yuFangcha = 15;
	// 大于阈值的个数计数
	public static int yuMeanCountQianjing = 0;
	public static int yuFangchaCountQianjing = 0;
	public static int yuMeanCountBeijing = 0;
	public static int yuFangchaCountBeijing = 0;
	// 个数的阈值，越小越容易判断为前景，大于该个数算作前景
	public static int yuMeanQianjing = 18;
	public static int yuFangchaQianjing = 18;
	// 更新背景时的概率,seita是16分之一概率更新该点，updateM是从20个背景中选一个更新，8是东西南北八个方向哪个更新
	public static int seita = 16;
	// 用于根据图片个数调整seita
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

// 无需改动的参数
	public static int IOUse = 0;
	public static int PANDUAN = 0;
	public static int CSVDeleteGhost = 0;

// 背景更新入口
	private static void updateBackset(String csvpathFront, String fileName) {

		List<String[]> dataBackset = new ArrayList<String[]>();
		dataBackset = readCsv(backsetCSVPath);
		List<String[]> dataFrontset = new ArrayList<String[]>();
		dataFrontset = readCsv(csvpathFront);
		// 这里取了样例的img，其实就是要拿一个框子，往里填像素，只要大小对就行,然后新建一个mat来更改类型，原先的mat是byte型，放不了255
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
				
				//涂白，前景255255255
				img.put(x,y, White);

				if (countQianjingPinLvArray[x][y] < countQianjingPinLvYuZhi) {
					countQianjingPinLvArray[x][y] = countQianjingPinLvArray[x][y] + 1;
				}
				if (countQianjingPinLv < countQianjingPinLvArray[x][y]) {
					countQianjingPinLv = countQianjingPinLvArray[x][y];
				}
			} else {
				//涂黑，背景000
				img.put(x,y, Black);


				// 取三位数对比
				int panduanFilename = Integer.valueOf(fileName.substring(fileName.length() - 3));
				if (panduanFilename > seitaChangePicNum) {
					seita = seitaChange;
				}

				// 随便等于一个数就行，反正都是1/seita的概率，一般用1比较好
				int[] randomseita = randomArray(1, seita);
				if (randomseita[0] == 1) {
					int[] randomupdateM = randomArray(1, updateM);
					if (XINHAO == 0) {
						// 更新背景csv的mean和方差
						String[] tempStrArrMeanFangCha = { dataBackset.get(i)[0], dataFrontset.get(i)[1],
								dataFrontset.get(i)[2] };
						dataBackset.set(20 * i + randomupdateM[0], tempStrArrMeanFangCha);
					}

					// 抽取空间更新方向，保证空间一致性
					int x1 = 0;
					int y1 = 0;
					// 0-7之间任意一个数
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

			// 检测该点到了阈值7且图像计数在十张以内，操作判断数组
			if (countQianjingPinLvArray[x][y] == countQianjingPinLvYuZhi
					&& countQianjingImageArray[x][y] <= countQianjingImageYuZhi && IOUse == 0) {
				// 该点的判断+1,且IOUse变为1，直到下个十张再开始允许对判断数组操作
				countQianjingPanDuanArray[x][y] = countQianjingPanDuanArray[x][y] + 1;
				IOUse = 1;
			}

			// 如果到了十张，该点还未达到阈值7，该点判断清零
			if (countQianjingPinLvArray[x][y] < countQianjingPinLvYuZhi
					&& countQianjingImageArray[x][y] == countQianjingImageYuZhi) {
				countQianjingPanDuanArray[x][y] = 0;
			}

			// 不论怎么样，到了十张，频率和图像计数都清零,发出信号进行一次鬼影判断数组判断，把为3的点鬼影覆盖
			if (countQianjingImageArray[x][y] == countQianjingImageYuZhi) {
				countQianjingPinLvArray[x][y] = 0;
				countQianjingImageArray[x][y] = 0;
				IOUse = 0; // 开启IOUse，进行新的判断
				PANDUAN = 1;
			}

			yuMeanCountQianjing = 0;
			yuFangchaCountQianjing = 0;
			yuMeanCountBeijing = 0;
			yuFangchaCountBeijing = 0;

			// 鬼影消除
			if (PANDUAN == 1) {
				if (countQianjingPanDuanArray[x][y] == countQianjingPanDuanYuZhi) {
					// 把背景集20个点的值全部替换为当前帧图像相应位置的像素值
					for (int k = 0; k < 20; k++) {
						String[] tempStrArrMeanFangChaGui = { dataBackset.get(i)[0], dataFrontset.get(i)[1],
								dataFrontset.get(i)[2] };
						dataBackset.set(20 * i + k, tempStrArrMeanFangChaGui);
					}
					countQianjingPanDuanArray[x][y] = 0; // 判断清零
				}
				if (countQianjingPanDuanArray[x][y] > countQianjingPanDuanYuZhi) {
					countQianjingPanDuanArray[x][y] = 0; // 判断清零
				}
				PANDUAN = 0;
			}

		}

		// 结果输出
		String resultJpgPath = FAFresultDir + "\\" + fileName + ".png";

		// 形态学去噪
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
		Mat dst = img.clone();
		Mat erosion = img.clone();
		Imgproc.dilate(img, dst, kernel, new Point(-1, -1), 1);
		Imgproc.erode(dst, erosion, kernel, new Point(-1, -1), 1);
		Imgcodecs.imwrite(resultJpgPath, erosion);

		System.out.println(resultJpgPath);
		System.out.println(seita);

		// csv文件写入
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

// csv读入
	public static List<String[]> readCsv(String path) {
		List<String[]> csvList = new ArrayList<String[]>();
		try {
			CsvReader csvReader = new CsvReader(path, ',', Charset.forName("GBK"));
			// 读表头
			csvReader.readHeaders();
			while (csvReader.readRecord()) {
				csvList.add(csvReader.getValues());
			}
			csvReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("读取csv文件<{}>失败，有异常发生" + path);
		} catch (IOException e) {
			System.out.println("读取csv文件<{}>失败，有异常发生" + path);
		}
		return csvList;
	}

// 前景入口
	public static void getFrontSet() {
		File fileInput = new File(intputDir);
		File[] inputList = fileInput.listFiles();
		for (int i = 0; i < inputList.length; i++) {
			if (i <= imgPicCHANGE) {
			} else {
				countQianjingPanDuanYuZhi = countQianjingPanDuanYuZhiCHANGE;
			}

			// temp1输出为in0001.jpg
			String[] fileNameIMG = inputList[i].toString().split("\\\\", 0);
			String Temp1 = fileNameIMG[fileNameIMG.length - 1];
			// fileName输出为in0001
			String[] fileNameReal = Temp1.split("\\.", 0);
			String fileName = fileNameReal[0];

			String imgpathFront = intputDir + "\\" + fileName + ".jpg";
			String csvpathFront = intputCSVDir + "\\" + fileName + ".csv";
			System.out.println(imgpathFront);
			runFront(imgpathFront, csvpathFront);

			// 背景更新
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
				// 平均数
				String meansum = String.format("%.4f", meancal(jisuan, N)).toString();
				mean.add(meansum);

				// 方差
				String fangchasum = String.format("%.4f", fangchacal(jisuan, N)).toString();
				fangcha.add(fangchasum);
			}
		}

		// csv文件写入
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

// 背景入口
	public static void getBackSet() {
		// 加载opencv
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
					// 平均数
					String meansum = String.format("%.4f", meancal(jisuan, N)).toString();
					mean.add(meansum);

					// 方差
					String fangchasum = String.format("%.4f", fangchacal(jisuan, N)).toString();
					fangcha.add(fangchasum);

				}
			}
		}
		// csv文件写入
		try {
			CsvWriter csvWriter = new CsvWriter(backsetCSVPath);
			String[] writeLineHead = { "name", "mean", "fangcha" };
			csvWriter.writeRecord(writeLineHead);

			for (int i = 0; i < name.size(); i++) {
				String[] writeLineBackset = { name.get(i), mean.get(i), fangcha.get(i) };
				csvWriter.writeRecord(writeLineBackset);
			}
			csvWriter.close();
			// 复制csv
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

			// 内圈
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

			// 中间,由于ifelse是从上往下执行，因此被注释的部分不会影响结果，x和y都为0的情况会被更上面的ifelse执行掉并跳出
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
