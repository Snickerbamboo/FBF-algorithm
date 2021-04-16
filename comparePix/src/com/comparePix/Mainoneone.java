package com.comparePix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvWriter;

/**
 * @description:
 * @author: Chuansheng Zhong
 * @create: 2020-07-15 15:44
 **/
public class Mainoneone {
    private static String FILE_NAME = "E:\\result\\resultTXT\\resulttxtTianchongKuai\\";

    public static void main(String[] args) {
        List<Scene> listScene = new ArrayList<>();

        String filepath = "E:\\result\\resultTXT\\resultTXTmyoffice10.csv";
        
        
        String[] Class11 = { "baseline" };
		String[] Class12 = { "office" };

		
        Scene winterDriveway = new Scene("office");
        
        /*
        Scene ALLbadWeather = new Scene("ALLbadWeather");
        Scene ALLbaseline = new Scene("ALLbaseline");
        Scene ALLcameraJitter = new Scene("ALLcameraJitter");
        Scene ALLintermittentObjectMotion = new Scene("ALLintermittentObjectMotion");
        Scene ALLshadow = new Scene("ALLshadow");
        Scene ALLALL = new Scene("ALLALL");
        */
		

        listScene.add(winterDriveway);
        
        /*
        listScene.add(ALLbadWeather);
        listScene.add(ALLbaseline);
        listScene.add(ALLcameraJitter);
        listScene.add(ALLintermittentObjectMotion);
        listScene.add(ALLshadow);
        listScene.add(ALLALL);
        */
        

        for (Scene scene : listScene) {
            readFile(scene, new ImageAlgorithm("FAFresult"));
            
            System.out.println(scene.getName());
            for (ImageAlgorithm imageAlgorithm : scene.getAlgorithmList()) {
                System.out.println(imageAlgorithm.toString());
            }
        }
        
        try {
        	CsvWriter csvWriter = new CsvWriter(filepath);
            String[] headers = {"SCname","AGname","recall","specificity","FPR","FNR","PCC","Precision","Fmeasure","TP","TN","FP","FN","N","P"};
            csvWriter.writeRecord(headers);
                    
            //类参数
            long TPbwFAF = 0;
            long FPbwFAF = 0;
            long TNbwFAF = 0;
            long FNbwFAF = 0;

			
            for (Scene scene : listScene) {
            	for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
            		String[] writeLine = {scene.getName(),String.valueOf(algorithm.getAlgorithmName()),String.valueOf(algorithm.getRecall()),String.valueOf(algorithm.getSpecificity()),String.valueOf(algorithm.getFPR()),String.valueOf(algorithm.getFNR()),String.valueOf(algorithm.getPCC()),String.valueOf(algorithm.getPrecision()),String.valueOf(algorithm.getFmeasure()),String.valueOf(algorithm.getTP()),String.valueOf(algorithm.getTN()),String.valueOf(algorithm.getFP()),String.valueOf(algorithm.getFN()),String.valueOf(algorithm.getN()),String.valueOf(algorithm.getP())};
            		csvWriter.writeRecord(writeLine);
                }
            	
                //写入整个类的数据
                for (int i = 0; i < Class12.length; i++) {
                    if (scene.getName().equals(Class12[i])) {
                        
                        for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
                            if(algorithm.getAlgorithmName().equals("FAFresult")){
                                
                                TPbwFAF = TPbwFAF + algorithm.getTP();
                                FPbwFAF = FPbwFAF + algorithm.getFP();
                                TNbwFAF = TNbwFAF + algorithm.getTN();
                                FNbwFAF = FNbwFAF + algorithm.getFN();
                            }
                           
                        }
                    }
                }
                

                /*
                if (scene.getName().equals("backdoor")) {
                    for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
                        if(algorithm.getAlgorithmName().equals("kde")){
                            System.out.println(algorithm.getFNR());
                        }
                    }
                }
                */
                
                
            }
            float RecallbwFAF = (float) TPbwFAF / (TPbwFAF + FNbwFAF); // recall就是TPR
            float SpecificitybwFAF = (float) TNbwFAF / (FPbwFAF + TNbwFAF); // TNR
            float FPRbwFAF = (float) FPbwFAF / (FPbwFAF + TNbwFAF);
            float FNRbwFAF = (float) FNbwFAF / (FNbwFAF + TPbwFAF);
            float PCCbwFAF = (float) (TPbwFAF + TNbwFAF) / (TPbwFAF + TNbwFAF + FPbwFAF + FNbwFAF); // Percentage of Correct Classification
            float PrecisionbwFAF = (float) TPbwFAF / (TPbwFAF + FPbwFAF);
            float FmeasurebwFAF = (2 * PrecisionbwFAF * RecallbwFAF) / (PrecisionbwFAF + RecallbwFAF);
            String[] writeLinebwFAF = {Class11[0],"bwFAF",String.valueOf(RecallbwFAF),String.valueOf(SpecificitybwFAF),String.valueOf(FPRbwFAF),String.valueOf(FNRbwFAF),String.valueOf(PCCbwFAF),String.valueOf(PrecisionbwFAF),String.valueOf(FmeasurebwFAF),String.valueOf(TPbwFAF),String.valueOf(TNbwFAF),String.valueOf(FPbwFAF),String.valueOf(FNbwFAF)};
            csvWriter.writeRecord(writeLinebwFAF);
      
            
            
    		
            csvWriter.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        
    }

    public static void readFile(Scene scene, ImageAlgorithm algorithm) {
        String fileName = FILE_NAME + scene.getName() + algorithm.getAlgorithmName() + ".txt";
        List<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String recallStr = arrayList.get(0);
        String doubleStrrecall = recallStr.substring(9, recallStr.length());
        double recallData = Double.parseDouble(doubleStrrecall);
        algorithm.setRecall(recallData);
        
        String specificityStr = arrayList.get(1);
        String doubleStrspecificity = specificityStr.substring(14, specificityStr.length());
        double specificityData = Double.parseDouble(doubleStrspecificity);
        algorithm.setSpecificity(specificityData);
        
        String FPRStr = arrayList.get(2);
        String doubleStrFPR = FPRStr.substring(6, FPRStr.length());
        double FPRData = Double.parseDouble(doubleStrFPR);
        algorithm.setFPR(FPRData);
        
        String FNRStr = arrayList.get(3);
        String doubleStrFNR = FNRStr.substring(6, FNRStr.length());
        double FNRData = Double.parseDouble(doubleStrFNR);
        algorithm.setFNR(FNRData);

        String PCCStr = arrayList.get(4);
        String doubleStrPCC = PCCStr.substring(6, PCCStr.length());
        double PCCData = Double.parseDouble(doubleStrPCC);
        algorithm.setPCC(PCCData);
        
        String PrecisionStr = arrayList.get(5);
        String doubleStrPrecision = PrecisionStr.substring(12, PrecisionStr.length());
        double PrecisionData = Double.parseDouble(doubleStrPrecision);
        algorithm.setPrecision(PrecisionData);
        
        String FmeasureStr = arrayList.get(6);
        String doubleStrFmeasure = FmeasureStr.substring(11, FmeasureStr.length());
        double FmeasureData = Double.parseDouble(doubleStrFmeasure);
        algorithm.setFmeasure(FmeasureData);
        
        String TPStr = arrayList.get(7);
        String longStrTP = TPStr.substring(5, TPStr.length());
        long TPData = Long.parseLong(longStrTP);
        algorithm.setTP(TPData);
        
        String TNStr = arrayList.get(8);
        String longStrTN = TNStr.substring(5, TNStr.length());
        long TNData = Long.parseLong(longStrTN);
        algorithm.setTN(TNData);
        
        String FPStr = arrayList.get(9);
        String longStrFP = FPStr.substring(5, FPStr.length());
        long FPData = Long.parseLong(longStrFP);
        algorithm.setFP(FPData);
        
        String FNStr = arrayList.get(10);
        String longStrFN = FNStr.substring(5, FNStr.length());
        long FNData = Long.parseLong(longStrFN);
        algorithm.setFN(FNData);
        
        String NStr = arrayList.get(11);
        String longStrN = NStr.substring(4, NStr.length());
        long NData = Long.parseLong(longStrN);
        algorithm.setN(NData);
        
        String PStr = arrayList.get(12);
        String longStrP = PStr.substring(4, PStr.length());
        long PData = Long.parseLong(longStrP);
        algorithm.setP(PData);

        scene.getAlgorithmList().add(algorithm);
    }

}
