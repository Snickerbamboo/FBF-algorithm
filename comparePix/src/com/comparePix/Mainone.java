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
public class Mainone {
    private static String FILE_NAME = "E:\\result\\resultTXT\\resulttxtTianchongKuai\\";

    public static void main(String[] args) {
        List<Scene> listScene = new ArrayList<>();

        String filepath = "E:\\result\\resultTXT\\resultTXTmyMatlab.csv";
        
        
        String[] Class11 = { "badWeather" };
		String[] Class12 = { "blizzard", "skating", "snowFall", "wetSnow" };

		String[] Class21 = { "baseline" };
		String[] Class22 = { "highway", "pedestrians", "PETS2006", "office" };

		String[] Class31 = { "cameraJitter" };
		String[] Class32 = { "badminton", "boulevard", "sidewalk", "traffic" };

		String[] Class41 = { "intermittentObjectMotion" };
		String[] Class42 = { "abandonedBox", "parking", "sofa", "streetLight", "tramstop", "winterDriveway" };

		String[] Class51 = { "shadow" };
		String[] Class52 = { "backdoor", "bungalows", "busStation", "copyMachine", "cubicle", "peopleInShade" };
		

        Scene abandonedBox = new Scene("abandonedBox");
        Scene backDoor = new Scene("backdoor");
        Scene badminton = new Scene("badminton");
        Scene blizzard = new Scene("blizzard");
        Scene boulevard = new Scene("boulevard");
        Scene bungalows = new Scene("bungalows");
        Scene busStation = new Scene("busStation");
        Scene copyMachine = new Scene("copyMachine");
        Scene cubicle = new Scene("cubicle");
        Scene highway = new Scene("highway");
        Scene office = new Scene("office");
        Scene parking = new Scene("parking");
        Scene pedestrians = new Scene("pedestrians");
        Scene peopleInShade = new Scene("peopleInShade");
        Scene PETS2006 = new Scene("PETS2006");
        Scene sidewalk = new Scene("sidewalk");
        Scene skating = new Scene("skating");
        Scene snowFall = new Scene("snowFall");
        Scene sofa = new Scene("sofa");
        Scene streetLight = new Scene("streetLight");
        Scene traffic = new Scene("traffic");
        Scene tramstop = new Scene("tramstop");
        Scene wetSnow = new Scene("wetSnow");
        Scene winterDriveway = new Scene("winterDriveway");
        
        /*
        Scene ALLbadWeather = new Scene("ALLbadWeather");
        Scene ALLbaseline = new Scene("ALLbaseline");
        Scene ALLcameraJitter = new Scene("ALLcameraJitter");
        Scene ALLintermittentObjectMotion = new Scene("ALLintermittentObjectMotion");
        Scene ALLshadow = new Scene("ALLshadow");
        Scene ALLALL = new Scene("ALLALL");
        */
		

        listScene.add(abandonedBox);
        listScene.add(backDoor);
        listScene.add(badminton);
        listScene.add(blizzard);
        listScene.add(boulevard);
        listScene.add(bungalows);
        listScene.add(busStation);
        listScene.add(copyMachine);
        listScene.add(cubicle);
        listScene.add(highway);
        listScene.add(office);
        listScene.add(parking);
        listScene.add(pedestrians);
        listScene.add(peopleInShade);
        listScene.add(PETS2006);
        listScene.add(sidewalk);
        listScene.add(skating);
        listScene.add(snowFall);
        listScene.add(sofa);
        listScene.add(streetLight);
        listScene.add(traffic);
        listScene.add(tramstop);
        listScene.add(wetSnow);
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

            
            long TPblFAF = 0;
            long FPblFAF = 0;
            long TNblFAF = 0;
            long FNblFAF = 0;

           
            
            long TPcamFAF = 0;
            long FPcamFAF = 0;
            long TNcamFAF = 0;
            long FNcamFAF = 0;

            
            
            long TPinterFAF = 0;
            long FPinterFAF = 0;
            long TNinterFAF = 0;
            long FNinterFAF = 0;

            
            
            long TPshadowFAF = 0;
            long FPshadowFAF = 0;
            long TNshadowFAF = 0;
            long FNshadowFAF = 0;

            
            
            long TPallFAF = 0;
            long FPallFAF = 0;
            long TNallFAF = 0;
            long FNallFAF = 0;

            
			
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
                
                for (int i = 0; i < Class22.length; i++) {
                    if (scene.getName().equals(Class22[i])) {
                        
                        for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
                            if(algorithm.getAlgorithmName().equals("FAFresult")){
                                
                                TPblFAF = TPblFAF + algorithm.getTP();
                                FPblFAF = FPblFAF + algorithm.getFP();
                                TNblFAF = TNblFAF + algorithm.getTN();
                                FNblFAF = FNblFAF + algorithm.getFN();
                            }
                            
                        }
                    }
                }
                
                for (int i = 0; i < Class32.length; i++) {
                    if (scene.getName().equals(Class32[i])) {
                        
                        for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
                            if(algorithm.getAlgorithmName().equals("FAFresult")){
                                
                                TPcamFAF = TPcamFAF + algorithm.getTP();
                                FPcamFAF = FPcamFAF + algorithm.getFP();
                                TNcamFAF = TNcamFAF + algorithm.getTN();
                                FNcamFAF = FNcamFAF + algorithm.getFN();
                            }
                            
                        }
                    }
                }
                
                for (int i = 0; i < Class42.length; i++) {
                    if (scene.getName().equals(Class42[i])) {
                        
                        for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
                            if(algorithm.getAlgorithmName().equals("FAFresult")){
                                
                                TPinterFAF = TPinterFAF + algorithm.getTP();
                                FPinterFAF = FPinterFAF + algorithm.getFP();
                                TNinterFAF = TNinterFAF + algorithm.getTN();
                                FNinterFAF = FNinterFAF + algorithm.getFN();
                            }
                            
                        }
                    }
                }
                
                for (int i = 0; i < Class52.length; i++) {
                    if (scene.getName().equals(Class52[i])) {
                        
                        for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
                            if(algorithm.getAlgorithmName().equals("FAFresult")){
                                
                                TPshadowFAF = TPshadowFAF + algorithm.getTP();
                                FPshadowFAF = FPshadowFAF + algorithm.getFP();
                                TNshadowFAF = TNshadowFAF + algorithm.getTN();
                                FNshadowFAF = FNshadowFAF + algorithm.getFN();
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
            
            
            
            float RecallblFAF = (float) TPblFAF / (TPblFAF + FNblFAF); // recall就是TPR
            float SpecificityblFAF = (float) TNblFAF / (FPblFAF + TNblFAF); // TNR
            float FPRblFAF = (float) FPblFAF / (FPblFAF + TNblFAF);
            float FNRblFAF = (float) FNblFAF / (FNblFAF + TPblFAF);
            float PCCblFAF = (float) (TPblFAF + TNblFAF) / (TPblFAF + TNblFAF + FPblFAF + FNblFAF); // Percentage of Correct Classification
            float PrecisionblFAF = (float) TPblFAF / (TPblFAF + FPblFAF);
            float FmeasureblFAF = (2 * PrecisionblFAF * RecallblFAF) / (PrecisionblFAF + RecallblFAF);
            String[] writeLineblFAF = {Class21[0],"blFAF",String.valueOf(RecallblFAF),String.valueOf(SpecificityblFAF),String.valueOf(FPRblFAF),String.valueOf(FNRblFAF),String.valueOf(PCCblFAF),String.valueOf(PrecisionblFAF),String.valueOf(FmeasureblFAF),String.valueOf(TPblFAF),String.valueOf(TNblFAF),String.valueOf(FPblFAF),String.valueOf(FNblFAF)};
            csvWriter.writeRecord(writeLineblFAF);
            
            
            
            float RecallcamFAF = (float) TPcamFAF / (TPcamFAF + FNcamFAF); // recall就是TPR
            float SpecificitycamFAF = (float) TNcamFAF / (FPcamFAF + TNcamFAF); // TNR
            float FPRcamFAF = (float) FPcamFAF / (FPcamFAF + TNcamFAF);
            float FNRcamFAF = (float) FNcamFAF / (FNcamFAF + TPcamFAF);
            float PCCcamFAF = (float) (TPcamFAF + TNcamFAF) / (TPcamFAF + TNcamFAF + FPcamFAF + FNcamFAF); // Percentage of Correct Classification
            float PrecisioncamFAF = (float) TPcamFAF / (TPcamFAF + FPcamFAF);
            float FmeasurecamFAF = (2 * PrecisioncamFAF * RecallcamFAF) / (PrecisioncamFAF + RecallcamFAF);
            String[] writeLinecamFAF = {Class31[0],"camFAF",String.valueOf(RecallcamFAF),String.valueOf(SpecificitycamFAF),String.valueOf(FPRcamFAF),String.valueOf(FNRcamFAF),String.valueOf(PCCcamFAF),String.valueOf(PrecisioncamFAF),String.valueOf(FmeasurecamFAF),String.valueOf(TPcamFAF),String.valueOf(TNcamFAF),String.valueOf(FPcamFAF),String.valueOf(FNcamFAF)};
            csvWriter.writeRecord(writeLinecamFAF);
            
            
            
            float RecallinterFAF = (float) TPinterFAF / (TPinterFAF + FNinterFAF); // recall就是TPR
            float SpecificityinterFAF = (float) TNinterFAF / (FPinterFAF + TNinterFAF); // TNR
            float FPRinterFAF = (float) FPinterFAF / (FPinterFAF + TNinterFAF);
            float FNRinterFAF = (float) FNinterFAF / (FNinterFAF + TPinterFAF);
            float PCCinterFAF = (float) (TPinterFAF + TNinterFAF) / (TPinterFAF + TNinterFAF + FPinterFAF + FNinterFAF); // Percentage of Correct Classification
            float PrecisioninterFAF = (float) TPinterFAF / (TPinterFAF + FPinterFAF);
            float FmeasureinterFAF = (2 * PrecisioninterFAF * RecallinterFAF) / (PrecisioninterFAF + RecallinterFAF);
            String[] writeLineinterFAF = {Class41[0],"interFAF",String.valueOf(RecallinterFAF),String.valueOf(SpecificityinterFAF),String.valueOf(FPRinterFAF),String.valueOf(FNRinterFAF),String.valueOf(PCCinterFAF),String.valueOf(PrecisioninterFAF),String.valueOf(FmeasureinterFAF),String.valueOf(TPinterFAF),String.valueOf(TNinterFAF),String.valueOf(FPinterFAF),String.valueOf(FNinterFAF)};
            csvWriter.writeRecord(writeLineinterFAF);
            
            
            
            float RecallshadowFAF = (float) TPshadowFAF / (TPshadowFAF + FNshadowFAF); // recall就是TPR
            float SpecificityshadowFAF = (float) TNshadowFAF / (FPshadowFAF + TNshadowFAF); // TNR
            float FPRshadowFAF = (float) FPshadowFAF / (FPshadowFAF + TNshadowFAF);
            float FNRshadowFAF = (float) FNshadowFAF / (FNshadowFAF + TPshadowFAF);
            float PCCshadowFAF = (float) (TPshadowFAF + TNshadowFAF) / (TPshadowFAF + TNshadowFAF + FPshadowFAF + FNshadowFAF); // Percentage of Correct Classification
            float PrecisionshadowFAF = (float) TPshadowFAF / (TPshadowFAF + FPshadowFAF);
            float FmeasureshadowFAF = (2 * PrecisionshadowFAF * RecallshadowFAF) / (PrecisionshadowFAF + RecallshadowFAF);
            String[] writeLineshadowFAF = {Class51[0],"shadowFAF",String.valueOf(RecallshadowFAF),String.valueOf(SpecificityshadowFAF),String.valueOf(FPRshadowFAF),String.valueOf(FNRshadowFAF),String.valueOf(PCCshadowFAF),String.valueOf(PrecisionshadowFAF),String.valueOf(FmeasureshadowFAF),String.valueOf(TPshadowFAF),String.valueOf(TNshadowFAF),String.valueOf(FPshadowFAF),String.valueOf(FNshadowFAF)};
            csvWriter.writeRecord(writeLineshadowFAF);
            
            
            
            TPallFAF = TPbwFAF + TPblFAF + TPcamFAF + TPinterFAF + TPshadowFAF;
            TNallFAF = TNbwFAF + TNblFAF + TNcamFAF + TNinterFAF + TNshadowFAF;
            FPallFAF = FPbwFAF + FPblFAF + FPcamFAF + FPinterFAF + FPshadowFAF;
            FNallFAF = FNbwFAF + FNblFAF + FNcamFAF + FNinterFAF + FNshadowFAF;
            
            
            
            float RecallallFAF = (float) TPallFAF / (TPallFAF + FNallFAF); 
            float SpecificityallFAF = (float) TNallFAF / (FPallFAF + TNallFAF); 
            float FPRallFAF = (float) FPallFAF / (FPallFAF + TNallFAF);
            float FNRallFAF = (float) FNallFAF / (FNallFAF + TPallFAF);
            float PCCallFAF = (float) (TPallFAF + TNallFAF) / (TPallFAF + TNallFAF + FPallFAF + FNallFAF); // Percentage of Correct Classification
            float PrecisionallFAF = (float) TPallFAF / (TPallFAF + FPallFAF);
            float FmeasureallFAF = (2 * PrecisionallFAF * RecallallFAF) / (PrecisionallFAF + RecallallFAF);
            String[] writeLineallFAF = {"ALL","allFAF",String.valueOf(RecallallFAF),String.valueOf(SpecificityallFAF),String.valueOf(FPRallFAF),String.valueOf(FNRallFAF),String.valueOf(PCCallFAF),String.valueOf(PrecisionallFAF),String.valueOf(FmeasureallFAF),String.valueOf(TPallFAF),String.valueOf(TNallFAF),String.valueOf(FPallFAF),String.valueOf(FNallFAF)};
            csvWriter.writeRecord(writeLineallFAF);
            
            
    		
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
