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
public class Main {
    private static String FILE_NAME = "E:\\result\\resultTXT\\resultTXT-1\\";

    public static void main(String[] args) {
        List<Scene> listScene = new ArrayList<>();

        String filepath = "E:\\result\\resultTXT\\resultTXT.csv";
        
        
        String[] Class11 = { "badWeather" };
		String[] Class12 = { "blizzard", "skating", "snowFall", "wetSnow" };

		String[] Class21 = { "baseline" };
		String[] Class22 = { "highway", "office", "pedestrians", "PETS2006" };

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
            readFile(scene, new ImageAlgorithm("gmm"));
            readFile(scene, new ImageAlgorithm("kde"));
            readFile(scene, new ImageAlgorithm("lob"));
            readFile(scene, new ImageAlgorithm("subsen"));
            readFile(scene, new ImageAlgorithm("vibe"));
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
            long TPbwgmm = 0;
            long FPbwgmm = 0;
            long TNbwgmm = 0;
            long FNbwgmm = 0;

            long TPbwkde = 0;
            long FPbwkde = 0;
            long TNbwkde = 0;
            long FNbwkde = 0;

            long TPbwlob = 0;
            long FPbwlob = 0;
            long TNbwlob = 0;
            long FNbwlob = 0;

            long TPbwsub = 0;
            long FPbwsub = 0;
            long TNbwsub = 0;
            long FNbwsub = 0;

            long TPbwvibe = 0;
            long FPbwvibe = 0;
            long TNbwvibe = 0;
            long FNbwvibe = 0;
            
            long TPblgmm = 0;
            long FPblgmm = 0;
            long TNblgmm = 0;
            long FNblgmm = 0;

            long TPblkde = 0;
            long FPblkde = 0;
            long TNblkde = 0;
            long FNblkde = 0;

            long TPbllob = 0;
            long FPbllob = 0;
            long TNbllob = 0;
            long FNbllob = 0;

            long TPblsub = 0;
            long FPblsub = 0;
            long TNblsub = 0;
            long FNblsub = 0;

            long TPblvibe = 0;
            long FPblvibe = 0;
            long TNblvibe = 0;
            long FNblvibe = 0;
			
            long TPcamgmm = 0;
            long FPcamgmm = 0;
            long TNcamgmm = 0;
            long FNcamgmm = 0;

            long TPcamkde = 0;
            long FPcamkde = 0;
            long TNcamkde = 0;
            long FNcamkde = 0;

            long TPcamlob = 0;
            long FPcamlob = 0;
            long TNcamlob = 0;
            long FNcamlob = 0;

            long TPcamsub = 0;
            long FPcamsub = 0;
            long TNcamsub = 0;
            long FNcamsub = 0;

            long TPcamvibe = 0;
            long FPcamvibe = 0;
            long TNcamvibe = 0;
            long FNcamvibe = 0;
			
            long TPintergmm = 0;
            long FPintergmm = 0;
            long TNintergmm = 0;
            long FNintergmm = 0;

            long TPinterkde = 0;
            long FPinterkde = 0;
            long TNinterkde = 0;
            long FNinterkde = 0;

            long TPinterlob = 0;
            long FPinterlob = 0;
            long TNinterlob = 0;
            long FNinterlob = 0;

            long TPintersub = 0;
            long FPintersub = 0;
            long TNintersub = 0;
            long FNintersub = 0;

            long TPintervibe = 0;
            long FPintervibe = 0;
            long TNintervibe = 0;
            long FNintervibe = 0;
			
            long TPshadowgmm = 0;
            long FPshadowgmm = 0;
            long TNshadowgmm = 0;
            long FNshadowgmm = 0;

            long TPshadowkde = 0;
            long FPshadowkde = 0;
            long TNshadowkde = 0;
            long FNshadowkde = 0;

            long TPshadowlob = 0;
            long FPshadowlob = 0;
            long TNshadowlob = 0;
            long FNshadowlob = 0;

            long TPshadowsub = 0;
            long FPshadowsub = 0;
            long TNshadowsub = 0;
            long FNshadowsub = 0;

            long TPshadowvibe = 0;
            long FPshadowvibe = 0;
            long TNshadowvibe = 0;
            long FNshadowvibe = 0;
			
            long TPallgmm = 0;
            long FPallgmm = 0;
            long TNallgmm = 0;
            long FNallgmm = 0;

            long TPallkde = 0;
            long FPallkde = 0;
            long TNallkde = 0;
            long FNallkde = 0;

            long TPalllob = 0;
            long FPalllob = 0;
            long TNalllob = 0;
            long FNalllob = 0;

            long TPallsub = 0;
            long FPallsub = 0;
            long TNallsub = 0;
            long FNallsub = 0;

            long TPallvibe = 0;
            long FPallvibe = 0;
            long TNallvibe = 0;
            long FNallvibe = 0;
			
            for (Scene scene : listScene) {
            	for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
            		String[] writeLine = {scene.getName(),String.valueOf(algorithm.getAlgorithmName()),String.valueOf(algorithm.getRecall()),String.valueOf(algorithm.getSpecificity()),String.valueOf(algorithm.getFPR()),String.valueOf(algorithm.getFNR()),String.valueOf(algorithm.getPCC()),String.valueOf(algorithm.getPrecision()),String.valueOf(algorithm.getFmeasure()),String.valueOf(algorithm.getTP()),String.valueOf(algorithm.getTN()),String.valueOf(algorithm.getFP()),String.valueOf(algorithm.getFN()),String.valueOf(algorithm.getN()),String.valueOf(algorithm.getP())};
            		csvWriter.writeRecord(writeLine);
                }
            	
            	//写入整个类的数据
                for (int i = 0; i < Class12.length; i++) {
                	if (scene.getName().equals(Class12[i])) {
                		
                        for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
                            if(algorithm.getAlgorithmName().equals("gmm")){
                            	
                                TPbwgmm = TPbwgmm + algorithm.getTP();
                                FPbwgmm = FPbwgmm + algorithm.getFP();
                                TNbwgmm = TNbwgmm + algorithm.getTN();
                                FNbwgmm = FNbwgmm + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("kde")){
                                TPbwkde = TPbwkde + algorithm.getTP();
                                FPbwkde = FPbwkde + algorithm.getFP();
                                TNbwkde = TNbwkde + algorithm.getTN();
                                FNbwkde = FNbwkde + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("lob")){
                                TPbwlob = TPbwlob + algorithm.getTP();
                                FPbwlob = FPbwlob + algorithm.getFP();
                                TNbwlob = TNbwlob + algorithm.getTN();
                                FNbwlob = FNbwlob + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("subsen")){
                                TPbwsub = TPbwsub + algorithm.getTP();
                                FPbwsub = FPbwsub + algorithm.getFP();
                                TNbwsub = TNbwsub + algorithm.getTN();
                                FNbwsub = FNbwsub + algorithm.getFN();
                                
                            }
                            if(algorithm.getAlgorithmName().equals("vibe")){
                                TPbwvibe = TPbwvibe + algorithm.getTP();
                                FPbwvibe = FPbwvibe + algorithm.getFP();
                                TNbwvibe = TNbwvibe + algorithm.getTN();
                                FNbwvibe = FNbwvibe + algorithm.getFN();
                            }
                        }
                    }
    			}
                
                for (int i = 0; i < Class22.length; i++) {
                	if (scene.getName().equals(Class22[i])) {
                		
                        for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
                            if(algorithm.getAlgorithmName().equals("gmm")){
                            	
                                TPblgmm = TPblgmm + algorithm.getTP();
                                FPblgmm = FPblgmm + algorithm.getFP();
                                TNblgmm = TNblgmm + algorithm.getTN();
                                FNblgmm = FNblgmm + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("kde")){
                                TPblkde = TPblkde + algorithm.getTP();
                                FPblkde = FPblkde + algorithm.getFP();
                                TNblkde = TNblkde + algorithm.getTN();
                                FNblkde = FNblkde + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("lob")){
                                TPbllob = TPbllob + algorithm.getTP();
                                FPbllob = FPbllob + algorithm.getFP();
                                TNbllob = TNbllob + algorithm.getTN();
                                FNbllob = FNbllob + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("subsen")){
                                TPblsub = TPblsub + algorithm.getTP();
                                FPblsub = FPblsub + algorithm.getFP();
                                TNblsub = TNblsub + algorithm.getTN();
                                FNblsub = FNblsub + algorithm.getFN();
                                
                            }
                            if(algorithm.getAlgorithmName().equals("vibe")){
                                TPblvibe = TPblvibe + algorithm.getTP();
                                FPblvibe = FPblvibe + algorithm.getFP();
                                TNblvibe = TNblvibe + algorithm.getTN();
                                FNblvibe = FNblvibe + algorithm.getFN();
                            }
                        }
                    }
    			}
                
                for (int i = 0; i < Class32.length; i++) {
                	if (scene.getName().equals(Class32[i])) {
                		
                        for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
                            if(algorithm.getAlgorithmName().equals("gmm")){
                            	
                                TPcamgmm = TPcamgmm + algorithm.getTP();
                                FPcamgmm = FPcamgmm + algorithm.getFP();
                                TNcamgmm = TNcamgmm + algorithm.getTN();
                                FNcamgmm = FNcamgmm + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("kde")){
                                TPcamkde = TPcamkde + algorithm.getTP();
                                FPcamkde = FPcamkde + algorithm.getFP();
                                TNcamkde = TNcamkde + algorithm.getTN();
                                FNcamkde = FNcamkde + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("lob")){
                                TPcamlob = TPcamlob + algorithm.getTP();
                                FPcamlob = FPcamlob + algorithm.getFP();
                                TNcamlob = TNcamlob + algorithm.getTN();
                                FNcamlob = FNcamlob + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("subsen")){
                                TPcamsub = TPcamsub + algorithm.getTP();
                                FPcamsub = FPcamsub + algorithm.getFP();
                                TNcamsub = TNcamsub + algorithm.getTN();
                                FNcamsub = FNcamsub + algorithm.getFN();
                                
                            }
                            if(algorithm.getAlgorithmName().equals("vibe")){
                                TPcamvibe = TPcamvibe + algorithm.getTP();
                                FPcamvibe = FPcamvibe + algorithm.getFP();
                                TNcamvibe = TNcamvibe + algorithm.getTN();
                                FNcamvibe = FNcamvibe + algorithm.getFN();
                            }
                        }
                    }
    			}
                
                for (int i = 0; i < Class42.length; i++) {
                	if (scene.getName().equals(Class42[i])) {
                		
                        for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
                            if(algorithm.getAlgorithmName().equals("gmm")){
                            	
                                TPintergmm = TPintergmm + algorithm.getTP();
                                FPintergmm = FPintergmm + algorithm.getFP();
                                TNintergmm = TNintergmm + algorithm.getTN();
                                FNintergmm = FNintergmm + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("kde")){
                                TPinterkde = TPinterkde + algorithm.getTP();
                                FPinterkde = FPinterkde + algorithm.getFP();
                                TNinterkde = TNinterkde + algorithm.getTN();
                                FNinterkde = FNinterkde + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("lob")){
                                TPinterlob = TPinterlob + algorithm.getTP();
                                FPinterlob = FPinterlob + algorithm.getFP();
                                TNinterlob = TNinterlob + algorithm.getTN();
                                FNinterlob = FNinterlob + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("subsen")){
                                TPintersub = TPintersub + algorithm.getTP();
                                FPintersub = FPintersub + algorithm.getFP();
                                TNintersub = TNintersub + algorithm.getTN();
                                FNintersub = FNintersub + algorithm.getFN();
                                
                            }
                            if(algorithm.getAlgorithmName().equals("vibe")){
                                TPintervibe = TPintervibe + algorithm.getTP();
                                FPintervibe = FPintervibe + algorithm.getFP();
                                TNintervibe = TNintervibe + algorithm.getTN();
                                FNintervibe = FNintervibe + algorithm.getFN();
                            }
                        }
                    }
    			}
                
                for (int i = 0; i < Class52.length; i++) {
                	if (scene.getName().equals(Class52[i])) {
                		
                        for (ImageAlgorithm algorithm : scene.getAlgorithmList()) {
                            if(algorithm.getAlgorithmName().equals("gmm")){
                            	
                                TPshadowgmm = TPshadowgmm + algorithm.getTP();
                                FPshadowgmm = FPshadowgmm + algorithm.getFP();
                                TNshadowgmm = TNshadowgmm + algorithm.getTN();
                                FNshadowgmm = FNshadowgmm + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("kde")){
                                TPshadowkde = TPshadowkde + algorithm.getTP();
                                FPshadowkde = FPshadowkde + algorithm.getFP();
                                TNshadowkde = TNshadowkde + algorithm.getTN();
                                FNshadowkde = FNshadowkde + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("lob")){
                                TPshadowlob = TPshadowlob + algorithm.getTP();
                                FPshadowlob = FPshadowlob + algorithm.getFP();
                                TNshadowlob = TNshadowlob + algorithm.getTN();
                                FNshadowlob = FNshadowlob + algorithm.getFN();
                            }
                            if(algorithm.getAlgorithmName().equals("subsen")){
                                TPshadowsub = TPshadowsub + algorithm.getTP();
                                FPshadowsub = FPshadowsub + algorithm.getFP();
                                TNshadowsub = TNshadowsub + algorithm.getTN();
                                FNshadowsub = FNshadowsub + algorithm.getFN();
                                
                            }
                            if(algorithm.getAlgorithmName().equals("vibe")){
                                TPshadowvibe = TPshadowvibe + algorithm.getTP();
                                FPshadowvibe = FPshadowvibe + algorithm.getFP();
                                TNshadowvibe = TNshadowvibe + algorithm.getTN();
                                FNshadowvibe = FNshadowvibe + algorithm.getFN();
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
            float Recallbwgmm = (float) TPbwgmm / (TPbwgmm + FNbwgmm); // recall就是TPR
			float Specificitybwgmm = (float) TNbwgmm / (FPbwgmm + TNbwgmm); // TNR
			float FPRbwgmm = (float) FPbwgmm / (FPbwgmm + TNbwgmm);
			float FNRbwgmm = (float) FNbwgmm / (FNbwgmm + TPbwgmm);
			float PCCbwgmm = (float) (TPbwgmm + TNbwgmm) / (TPbwgmm + TNbwgmm + FPbwgmm + FNbwgmm); // Percentage of Correct Classification
			float Precisionbwgmm = (float) TPbwgmm / (TPbwgmm + FPbwgmm);
			float Fmeasurebwgmm = (2 * Precisionbwgmm * Recallbwgmm) / (Precisionbwgmm + Recallbwgmm);
			String[] writeLinebwgmm = {Class11[0],"bwgmm",String.valueOf(Recallbwgmm),String.valueOf(Specificitybwgmm),String.valueOf(FPRbwgmm),String.valueOf(FNRbwgmm),String.valueOf(PCCbwgmm),String.valueOf(Precisionbwgmm),String.valueOf(Fmeasurebwgmm),String.valueOf(TPbwgmm),String.valueOf(TNbwgmm),String.valueOf(FPbwgmm),String.valueOf(FNbwgmm)};
    		csvWriter.writeRecord(writeLinebwgmm);
            
    		float Recallbwkde = (float) TPbwkde / (TPbwkde + FNbwkde); // recall就是TPR
			float Specificitybwkde = (float) TNbwkde / (FPbwkde + TNbwkde); // TNR
			float FPRbwkde = (float) FPbwkde / (FPbwkde + TNbwkde);
			float FNRbwkde = (float) FNbwkde / (FNbwkde + TPbwkde);
			float PCCbwkde = (float) (TPbwkde + TNbwkde) / (TPbwkde + TNbwkde + FPbwkde + FNbwkde); // Percentage of Correct Classification
			float Precisionbwkde = (float) TPbwkde / (TPbwkde + FPbwkde);
			float Fmeasurebwkde = (2 * Precisionbwkde * Recallbwkde) / (Precisionbwkde + Recallbwkde);
			String[] writeLinebwkde = {Class11[0],"bwkde",String.valueOf(Recallbwkde),String.valueOf(Specificitybwkde),String.valueOf(FPRbwkde),String.valueOf(FNRbwkde),String.valueOf(PCCbwkde),String.valueOf(Precisionbwkde),String.valueOf(Fmeasurebwkde),String.valueOf(TPbwkde),String.valueOf(TNbwkde),String.valueOf(FPbwkde),String.valueOf(FNbwkde)};
    		csvWriter.writeRecord(writeLinebwkde);
    		
    		float Recallbwlob = (float) TPbwlob / (TPbwlob + FNbwlob); // recall就是TPR
			float Specificitybwlob = (float) TNbwlob / (FPbwlob + TNbwlob); // TNR
			float FPRbwlob = (float) FPbwlob / (FPbwlob + TNbwlob);
			float FNRbwlob = (float) FNbwlob / (FNbwlob + TPbwlob);
			float PCCbwlob = (float) (TPbwlob + TNbwlob) / (TPbwlob + TNbwlob + FPbwlob + FNbwlob); // Percentage of Correct Classification
			float Precisionbwlob = (float) TPbwlob / (TPbwlob + FPbwlob);
			float Fmeasurebwlob = (2 * Precisionbwlob * Recallbwlob) / (Precisionbwlob + Recallbwlob);
			String[] writeLinebwlob = {Class11[0],"bwlob",String.valueOf(Recallbwlob),String.valueOf(Specificitybwlob),String.valueOf(FPRbwlob),String.valueOf(FNRbwlob),String.valueOf(PCCbwlob),String.valueOf(Precisionbwlob),String.valueOf(Fmeasurebwlob),String.valueOf(TPbwlob),String.valueOf(TNbwlob),String.valueOf(FPbwlob),String.valueOf(FNbwlob)};
    		csvWriter.writeRecord(writeLinebwlob);
    		
    		float Recallbwsub = (float) TPbwsub / (TPbwsub + FNbwsub); // recall就是TPR
			float Specificitybwsub = (float) TNbwsub / (FPbwsub + TNbwsub); // TNR
			float FPRbwsub = (float) FPbwsub / (FPbwsub + TNbwsub);
			float FNRbwsub = (float) FNbwsub / (FNbwsub + TPbwsub);
			float PCCbwsub = (float) (TPbwsub + TNbwsub) / (TPbwsub + TNbwsub + FPbwsub + FNbwsub); // Percentage of Correct Classification
			float Precisionbwsub = (float) TPbwsub / (TPbwsub + FPbwsub);
			float Fmeasurebwsub = (2 * Precisionbwsub * Recallbwsub) / (Precisionbwsub + Recallbwsub);
			String[] writeLinebwsub = {Class11[0],"bwsub",String.valueOf(Recallbwsub),String.valueOf(Specificitybwsub),String.valueOf(FPRbwsub),String.valueOf(FNRbwsub),String.valueOf(PCCbwsub),String.valueOf(Precisionbwsub),String.valueOf(Fmeasurebwsub),String.valueOf(TPbwsub),String.valueOf(TNbwsub),String.valueOf(FPbwsub),String.valueOf(FNbwsub)};
    		csvWriter.writeRecord(writeLinebwsub);
    		
    		float Recallbwvibe = (float) TPbwvibe / (TPbwvibe + FNbwvibe); // recall就是TPR
			float Specificitybwvibe = (float) TNbwvibe / (FPbwvibe + TNbwvibe); // TNR
			float FPRbwvibe = (float) FPbwvibe / (FPbwvibe + TNbwvibe);
			float FNRbwvibe = (float) FNbwvibe / (FNbwvibe + TPbwvibe);
			float PCCbwvibe = (float) (TPbwvibe + TNbwvibe) / (TPbwvibe + TNbwvibe + FPbwvibe + FNbwvibe); // Percentage of Correct Classification
			float Precisionbwvibe = (float) TPbwvibe / (TPbwvibe + FPbwvibe);
			float Fmeasurebwvibe = (2 * Precisionbwvibe * Recallbwvibe) / (Precisionbwvibe + Recallbwvibe);
			String[] writeLinebwvibe = {Class11[0],"bwvibe",String.valueOf(Recallbwvibe),String.valueOf(Specificitybwvibe),String.valueOf(FPRbwvibe),String.valueOf(FNRbwvibe),String.valueOf(PCCbwvibe),String.valueOf(Precisionbwvibe),String.valueOf(Fmeasurebwvibe),String.valueOf(TPbwvibe),String.valueOf(TNbwvibe),String.valueOf(FPbwvibe),String.valueOf(FNbwvibe)};
    		csvWriter.writeRecord(writeLinebwvibe);
    		
			float Recallblgmm = (float) TPblgmm / (TPblgmm + FNblgmm); // recall就是TPR
			float Specificityblgmm = (float) TNblgmm / (FPblgmm + TNblgmm); // TNR
			float FPRblgmm = (float) FPblgmm / (FPblgmm + TNblgmm);
			float FNRblgmm = (float) FNblgmm / (FNblgmm + TPblgmm);
			float PCCblgmm = (float) (TPblgmm + TNblgmm) / (TPblgmm + TNblgmm + FPblgmm + FNblgmm); // Percentage of Correct Classification
			float Precisionblgmm = (float) TPblgmm / (TPblgmm + FPblgmm);
			float Fmeasureblgmm = (2 * Precisionblgmm * Recallblgmm) / (Precisionblgmm + Recallblgmm);
			String[] writeLineblgmm = {Class21[0],"blgmm",String.valueOf(Recallblgmm),String.valueOf(Specificityblgmm),String.valueOf(FPRblgmm),String.valueOf(FNRblgmm),String.valueOf(PCCblgmm),String.valueOf(Precisionblgmm),String.valueOf(Fmeasureblgmm),String.valueOf(TPblgmm),String.valueOf(TNblgmm),String.valueOf(FPblgmm),String.valueOf(FNblgmm)};
    		csvWriter.writeRecord(writeLineblgmm);
            
    		float Recallblkde = (float) TPblkde / (TPblkde + FNblkde); // recall就是TPR
			float Specificityblkde = (float) TNblkde / (FPblkde + TNblkde); // TNR
			float FPRblkde = (float) FPblkde / (FPblkde + TNblkde);
			float FNRblkde = (float) FNblkde / (FNblkde + TPblkde);
			float PCCblkde = (float) (TPblkde + TNblkde) / (TPblkde + TNblkde + FPblkde + FNblkde); // Percentage of Correct Classification
			float Precisionblkde = (float) TPblkde / (TPblkde + FPblkde);
			float Fmeasureblkde = (2 * Precisionblkde * Recallblkde) / (Precisionblkde + Recallblkde);
			String[] writeLineblkde = {Class21[0],"blkde",String.valueOf(Recallblkde),String.valueOf(Specificityblkde),String.valueOf(FPRblkde),String.valueOf(FNRblkde),String.valueOf(PCCblkde),String.valueOf(Precisionblkde),String.valueOf(Fmeasureblkde),String.valueOf(TPblkde),String.valueOf(TNblkde),String.valueOf(FPblkde),String.valueOf(FNblkde)};
    		csvWriter.writeRecord(writeLineblkde);
    		
    		float Recallbllob = (float) TPbllob / (TPbllob + FNbllob); // recall就是TPR
			float Specificitybllob = (float) TNbllob / (FPbllob + TNbllob); // TNR
			float FPRbllob = (float) FPbllob / (FPbllob + TNbllob);
			float FNRbllob = (float) FNbllob / (FNbllob + TPbllob);
			float PCCbllob = (float) (TPbllob + TNbllob) / (TPbllob + TNbllob + FPbllob + FNbllob); // Percentage of Correct Classification
			float Precisionbllob = (float) TPbllob / (TPbllob + FPbllob);
			float Fmeasurebllob = (2 * Precisionbllob * Recallbllob) / (Precisionbllob + Recallbllob);
			String[] writeLinebllob = {Class21[0],"bllob",String.valueOf(Recallbllob),String.valueOf(Specificitybllob),String.valueOf(FPRbllob),String.valueOf(FNRbllob),String.valueOf(PCCbllob),String.valueOf(Precisionbllob),String.valueOf(Fmeasurebllob),String.valueOf(TPbllob),String.valueOf(TNbllob),String.valueOf(FPbllob),String.valueOf(FNbllob)};
    		csvWriter.writeRecord(writeLinebllob);
    		
    		float Recallblsub = (float) TPblsub / (TPblsub + FNblsub); // recall就是TPR
			float Specificityblsub = (float) TNblsub / (FPblsub + TNblsub); // TNR
			float FPRblsub = (float) FPblsub / (FPblsub + TNblsub);
			float FNRblsub = (float) FNblsub / (FNblsub + TPblsub);
			float PCCblsub = (float) (TPblsub + TNblsub) / (TPblsub + TNblsub + FPblsub + FNblsub); // Percentage of Correct Classification
			float Precisionblsub = (float) TPblsub / (TPblsub + FPblsub);
			float Fmeasureblsub = (2 * Precisionblsub * Recallblsub) / (Precisionblsub + Recallblsub);
			String[] writeLineblsub = {Class21[0],"blsub",String.valueOf(Recallblsub),String.valueOf(Specificityblsub),String.valueOf(FPRblsub),String.valueOf(FNRblsub),String.valueOf(PCCblsub),String.valueOf(Precisionblsub),String.valueOf(Fmeasureblsub),String.valueOf(TPblsub),String.valueOf(TNblsub),String.valueOf(FPblsub),String.valueOf(FNblsub)};
    		csvWriter.writeRecord(writeLineblsub);
    		
    		float Recallblvibe = (float) TPblvibe / (TPblvibe + FNblvibe); // recall就是TPR
			float Specificityblvibe = (float) TNblvibe / (FPblvibe + TNblvibe); // TNR
			float FPRblvibe = (float) FPblvibe / (FPblvibe + TNblvibe);
			float FNRblvibe = (float) FNblvibe / (FNblvibe + TPblvibe);
			float PCCblvibe = (float) (TPblvibe + TNblvibe) / (TPblvibe + TNblvibe + FPblvibe + FNblvibe); // Percentage of Correct Classification
			float Precisionblvibe = (float) TPblvibe / (TPblvibe + FPblvibe);
			float Fmeasureblvibe = (2 * Precisionblvibe * Recallblvibe) / (Precisionblvibe + Recallblvibe);
			String[] writeLineblvibe = {Class21[0],"blvibe",String.valueOf(Recallblvibe),String.valueOf(Specificityblvibe),String.valueOf(FPRblvibe),String.valueOf(FNRblvibe),String.valueOf(PCCblvibe),String.valueOf(Precisionblvibe),String.valueOf(Fmeasureblvibe),String.valueOf(TPblvibe),String.valueOf(TNblvibe),String.valueOf(FPblvibe),String.valueOf(FNblvibe)};
    		csvWriter.writeRecord(writeLineblvibe);
    		
			float Recallcamgmm = (float) TPcamgmm / (TPcamgmm + FNcamgmm); // recall就是TPR
			float Specificitycamgmm = (float) TNcamgmm / (FPcamgmm + TNcamgmm); // TNR
			float FPRcamgmm = (float) FPcamgmm / (FPcamgmm + TNcamgmm);
			float FNRcamgmm = (float) FNcamgmm / (FNcamgmm + TPcamgmm);
			float PCCcamgmm = (float) (TPcamgmm + TNcamgmm) / (TPcamgmm + TNcamgmm + FPcamgmm + FNcamgmm); // Percentage of Correct Classification
			float Precisioncamgmm = (float) TPcamgmm / (TPcamgmm + FPcamgmm);
			float Fmeasurecamgmm = (2 * Precisioncamgmm * Recallcamgmm) / (Precisioncamgmm + Recallcamgmm);
			String[] writeLinecamgmm = {Class31[0],"camgmm",String.valueOf(Recallcamgmm),String.valueOf(Specificitycamgmm),String.valueOf(FPRcamgmm),String.valueOf(FNRcamgmm),String.valueOf(PCCcamgmm),String.valueOf(Precisioncamgmm),String.valueOf(Fmeasurecamgmm),String.valueOf(TPcamgmm),String.valueOf(TNcamgmm),String.valueOf(FPcamgmm),String.valueOf(FNcamgmm)};
    		csvWriter.writeRecord(writeLinecamgmm);
            
    		float Recallcamkde = (float) TPcamkde / (TPcamkde + FNcamkde); // recall就是TPR
			float Specificitycamkde = (float) TNcamkde / (FPcamkde + TNcamkde); // TNR
			float FPRcamkde = (float) FPcamkde / (FPcamkde + TNcamkde);
			float FNRcamkde = (float) FNcamkde / (FNcamkde + TPcamkde);
			float PCCcamkde = (float) (TPcamkde + TNcamkde) / (TPcamkde + TNcamkde + FPcamkde + FNcamkde); // Percentage of Correct Classification
			float Precisioncamkde = (float) TPcamkde / (TPcamkde + FPcamkde);
			float Fmeasurecamkde = (2 * Precisioncamkde * Recallcamkde) / (Precisioncamkde + Recallcamkde);
			String[] writeLinecamkde = {Class31[0],"camkde",String.valueOf(Recallcamkde),String.valueOf(Specificitycamkde),String.valueOf(FPRcamkde),String.valueOf(FNRcamkde),String.valueOf(PCCcamkde),String.valueOf(Precisioncamkde),String.valueOf(Fmeasurecamkde),String.valueOf(TPcamkde),String.valueOf(TNcamkde),String.valueOf(FPcamkde),String.valueOf(FNcamkde)};
    		csvWriter.writeRecord(writeLinecamkde);
    		
    		float Recallcamlob = (float) TPcamlob / (TPcamlob + FNcamlob); // recall就是TPR
			float Specificitycamlob = (float) TNcamlob / (FPcamlob + TNcamlob); // TNR
			float FPRcamlob = (float) FPcamlob / (FPcamlob + TNcamlob);
			float FNRcamlob = (float) FNcamlob / (FNcamlob + TPcamlob);
			float PCCcamlob = (float) (TPcamlob + TNcamlob) / (TPcamlob + TNcamlob + FPcamlob + FNcamlob); // Percentage of Correct Classification
			float Precisioncamlob = (float) TPcamlob / (TPcamlob + FPcamlob);
			float Fmeasurecamlob = (2 * Precisioncamlob * Recallcamlob) / (Precisioncamlob + Recallcamlob);
			String[] writeLinecamlob = {Class31[0],"camlob",String.valueOf(Recallcamlob),String.valueOf(Specificitycamlob),String.valueOf(FPRcamlob),String.valueOf(FNRcamlob),String.valueOf(PCCcamlob),String.valueOf(Precisioncamlob),String.valueOf(Fmeasurecamlob),String.valueOf(TPcamlob),String.valueOf(TNcamlob),String.valueOf(FPcamlob),String.valueOf(FNcamlob)};
    		csvWriter.writeRecord(writeLinecamlob);
    		
    		float Recallcamsub = (float) TPcamsub / (TPcamsub + FNcamsub); // recall就是TPR
			float Specificitycamsub = (float) TNcamsub / (FPcamsub + TNcamsub); // TNR
			float FPRcamsub = (float) FPcamsub / (FPcamsub + TNcamsub);
			float FNRcamsub = (float) FNcamsub / (FNcamsub + TPcamsub);
			float PCCcamsub = (float) (TPcamsub + TNcamsub) / (TPcamsub + TNcamsub + FPcamsub + FNcamsub); // Percentage of Correct Classification
			float Precisioncamsub = (float) TPcamsub / (TPcamsub + FPcamsub);
			float Fmeasurecamsub = (2 * Precisioncamsub * Recallcamsub) / (Precisioncamsub + Recallcamsub);
			String[] writeLinecamsub = {Class31[0],"camsub",String.valueOf(Recallcamsub),String.valueOf(Specificitycamsub),String.valueOf(FPRcamsub),String.valueOf(FNRcamsub),String.valueOf(PCCcamsub),String.valueOf(Precisioncamsub),String.valueOf(Fmeasurecamsub),String.valueOf(TPcamsub),String.valueOf(TNcamsub),String.valueOf(FPcamsub),String.valueOf(FNcamsub)};
    		csvWriter.writeRecord(writeLinecamsub);
    		
    		float Recallcamvibe = (float) TPcamvibe / (TPcamvibe + FNcamvibe); // recall就是TPR
			float Specificitycamvibe = (float) TNcamvibe / (FPcamvibe + TNcamvibe); // TNR
			float FPRcamvibe = (float) FPcamvibe / (FPcamvibe + TNcamvibe);
			float FNRcamvibe = (float) FNcamvibe / (FNcamvibe + TPcamvibe);
			float PCCcamvibe = (float) (TPcamvibe + TNcamvibe) / (TPcamvibe + TNcamvibe + FPcamvibe + FNcamvibe); // Percentage of Correct Classification
			float Precisioncamvibe = (float) TPcamvibe / (TPcamvibe + FPcamvibe);
			float Fmeasurecamvibe = (2 * Precisioncamvibe * Recallcamvibe) / (Precisioncamvibe + Recallcamvibe);
			String[] writeLinecamvibe = {Class31[0],"camvibe",String.valueOf(Recallcamvibe),String.valueOf(Specificitycamvibe),String.valueOf(FPRcamvibe),String.valueOf(FNRcamvibe),String.valueOf(PCCcamvibe),String.valueOf(Precisioncamvibe),String.valueOf(Fmeasurecamvibe),String.valueOf(TPcamvibe),String.valueOf(TNcamvibe),String.valueOf(FPcamvibe),String.valueOf(FNcamvibe)};
    		csvWriter.writeRecord(writeLinecamvibe);
    		
			float Recallintergmm = (float) TPintergmm / (TPintergmm + FNintergmm); // recall就是TPR
			float Specificityintergmm = (float) TNintergmm / (FPintergmm + TNintergmm); // TNR
			float FPRintergmm = (float) FPintergmm / (FPintergmm + TNintergmm);
			float FNRintergmm = (float) FNintergmm / (FNintergmm + TPintergmm);
			float PCCintergmm = (float) (TPintergmm + TNintergmm) / (TPintergmm + TNintergmm + FPintergmm + FNintergmm); // Percentage of Correct Classification
			float Precisionintergmm = (float) TPintergmm / (TPintergmm + FPintergmm);
			float Fmeasureintergmm = (2 * Precisionintergmm * Recallintergmm) / (Precisionintergmm + Recallintergmm);
			String[] writeLineintergmm = {Class41[0],"intergmm",String.valueOf(Recallintergmm),String.valueOf(Specificityintergmm),String.valueOf(FPRintergmm),String.valueOf(FNRintergmm),String.valueOf(PCCintergmm),String.valueOf(Precisionintergmm),String.valueOf(Fmeasureintergmm),String.valueOf(TPintergmm),String.valueOf(TNintergmm),String.valueOf(FPintergmm),String.valueOf(FNintergmm)};
    		csvWriter.writeRecord(writeLineintergmm);
            
    		float Recallinterkde = (float) TPinterkde / (TPinterkde + FNinterkde); // recall就是TPR
			float Specificityinterkde = (float) TNinterkde / (FPinterkde + TNinterkde); // TNR
			float FPRinterkde = (float) FPinterkde / (FPinterkde + TNinterkde);
			float FNRinterkde = (float) FNinterkde / (FNinterkde + TPinterkde);
			float PCCinterkde = (float) (TPinterkde + TNinterkde) / (TPinterkde + TNinterkde + FPinterkde + FNinterkde); // Percentage of Correct Classification
			float Precisioninterkde = (float) TPinterkde / (TPinterkde + FPinterkde);
			float Fmeasureinterkde = (2 * Precisioninterkde * Recallinterkde) / (Precisioninterkde + Recallinterkde);
			String[] writeLineinterkde = {Class41[0],"interkde",String.valueOf(Recallinterkde),String.valueOf(Specificityinterkde),String.valueOf(FPRinterkde),String.valueOf(FNRinterkde),String.valueOf(PCCinterkde),String.valueOf(Precisioninterkde),String.valueOf(Fmeasureinterkde),String.valueOf(TPinterkde),String.valueOf(TNinterkde),String.valueOf(FPinterkde),String.valueOf(FNinterkde)};
    		csvWriter.writeRecord(writeLineinterkde);
    		
    		float Recallinterlob = (float) TPinterlob / (TPinterlob + FNinterlob); // recall就是TPR
			float Specificityinterlob = (float) TNinterlob / (FPinterlob + TNinterlob); // TNR
			float FPRinterlob = (float) FPinterlob / (FPinterlob + TNinterlob);
			float FNRinterlob = (float) FNinterlob / (FNinterlob + TPinterlob);
			float PCCinterlob = (float) (TPinterlob + TNinterlob) / (TPinterlob + TNinterlob + FPinterlob + FNinterlob); // Percentage of Correct Classification
			float Precisioninterlob = (float) TPinterlob / (TPinterlob + FPinterlob);
			float Fmeasureinterlob = (2 * Precisioninterlob * Recallinterlob) / (Precisioninterlob + Recallinterlob);
			String[] writeLineinterlob = {Class41[0],"interlob",String.valueOf(Recallinterlob),String.valueOf(Specificityinterlob),String.valueOf(FPRinterlob),String.valueOf(FNRinterlob),String.valueOf(PCCinterlob),String.valueOf(Precisioninterlob),String.valueOf(Fmeasureinterlob),String.valueOf(TPinterlob),String.valueOf(TNinterlob),String.valueOf(FPinterlob),String.valueOf(FNinterlob)};
    		csvWriter.writeRecord(writeLineinterlob);
    		
    		float Recallintersub = (float) TPintersub / (TPintersub + FNintersub); // recall就是TPR
			float Specificityintersub = (float) TNintersub / (FPintersub + TNintersub); // TNR
			float FPRintersub = (float) FPintersub / (FPintersub + TNintersub);
			float FNRintersub = (float) FNintersub / (FNintersub + TPintersub);
			float PCCintersub = (float) (TPintersub + TNintersub) / (TPintersub + TNintersub + FPintersub + FNintersub); // Percentage of Correct Classification
			float Precisionintersub = (float) TPintersub / (TPintersub + FPintersub);
			float Fmeasureintersub = (2 * Precisionintersub * Recallintersub) / (Precisionintersub + Recallintersub);
			String[] writeLineintersub = {Class41[0],"intersub",String.valueOf(Recallintersub),String.valueOf(Specificityintersub),String.valueOf(FPRintersub),String.valueOf(FNRintersub),String.valueOf(PCCintersub),String.valueOf(Precisionintersub),String.valueOf(Fmeasureintersub),String.valueOf(TPintersub),String.valueOf(TNintersub),String.valueOf(FPintersub),String.valueOf(FNintersub)};
    		csvWriter.writeRecord(writeLineintersub);
    		
    		float Recallintervibe = (float) TPintervibe / (TPintervibe + FNintervibe); // recall就是TPR
			float Specificityintervibe = (float) TNintervibe / (FPintervibe + TNintervibe); // TNR
			float FPRintervibe = (float) FPintervibe / (FPintervibe + TNintervibe);
			float FNRintervibe = (float) FNintervibe / (FNintervibe + TPintervibe);
			float PCCintervibe = (float) (TPintervibe + TNintervibe) / (TPintervibe + TNintervibe + FPintervibe + FNintervibe); // Percentage of Correct Classification
			float Precisionintervibe = (float) TPintervibe / (TPintervibe + FPintervibe);
			float Fmeasureintervibe = (2 * Precisionintervibe * Recallintervibe) / (Precisionintervibe + Recallintervibe);
			String[] writeLineintervibe = {Class41[0],"intervibe",String.valueOf(Recallintervibe),String.valueOf(Specificityintervibe),String.valueOf(FPRintervibe),String.valueOf(FNRintervibe),String.valueOf(PCCintervibe),String.valueOf(Precisionintervibe),String.valueOf(Fmeasureintervibe),String.valueOf(TPintervibe),String.valueOf(TNintervibe),String.valueOf(FPintervibe),String.valueOf(FNintervibe)};
    		csvWriter.writeRecord(writeLineintervibe);
    		
			float Recallshadowgmm = (float) TPshadowgmm / (TPshadowgmm + FNshadowgmm); // recall就是TPR
			float Specificityshadowgmm = (float) TNshadowgmm / (FPshadowgmm + TNshadowgmm); // TNR
			float FPRshadowgmm = (float) FPshadowgmm / (FPshadowgmm + TNshadowgmm);
			float FNRshadowgmm = (float) FNshadowgmm / (FNshadowgmm + TPshadowgmm);
			float PCCshadowgmm = (float) (TPshadowgmm + TNshadowgmm) / (TPshadowgmm + TNshadowgmm + FPshadowgmm + FNshadowgmm); // Percentage of Correct Classification
			float Precisionshadowgmm = (float) TPshadowgmm / (TPshadowgmm + FPshadowgmm);
			float Fmeasureshadowgmm = (2 * Precisionshadowgmm * Recallshadowgmm) / (Precisionshadowgmm + Recallshadowgmm);
			String[] writeLineshadowgmm = {Class51[0],"shadowgmm",String.valueOf(Recallshadowgmm),String.valueOf(Specificityshadowgmm),String.valueOf(FPRshadowgmm),String.valueOf(FNRshadowgmm),String.valueOf(PCCshadowgmm),String.valueOf(Precisionshadowgmm),String.valueOf(Fmeasureshadowgmm),String.valueOf(TPshadowgmm),String.valueOf(TNshadowgmm),String.valueOf(FPshadowgmm),String.valueOf(FNshadowgmm)};
    		csvWriter.writeRecord(writeLineshadowgmm);
            
    		float Recallshadowkde = (float) TPshadowkde / (TPshadowkde + FNshadowkde); // recall就是TPR
			float Specificityshadowkde = (float) TNshadowkde / (FPshadowkde + TNshadowkde); // TNR
			float FPRshadowkde = (float) FPshadowkde / (FPshadowkde + TNshadowkde);
			float FNRshadowkde = (float) FNshadowkde / (FNshadowkde + TPshadowkde);
			float PCCshadowkde = (float) (TPshadowkde + TNshadowkde) / (TPshadowkde + TNshadowkde + FPshadowkde + FNshadowkde); // Percentage of Correct Classification
			float Precisionshadowkde = (float) TPshadowkde / (TPshadowkde + FPshadowkde);
			float Fmeasureshadowkde = (2 * Precisionshadowkde * Recallshadowkde) / (Precisionshadowkde + Recallshadowkde);
			String[] writeLineshadowkde = {Class51[0],"shadowkde",String.valueOf(Recallshadowkde),String.valueOf(Specificityshadowkde),String.valueOf(FPRshadowkde),String.valueOf(FNRshadowkde),String.valueOf(PCCshadowkde),String.valueOf(Precisionshadowkde),String.valueOf(Fmeasureshadowkde),String.valueOf(TPshadowkde),String.valueOf(TNshadowkde),String.valueOf(FPshadowkde),String.valueOf(FNshadowkde)};
    		csvWriter.writeRecord(writeLineshadowkde);
    		
    		float Recallshadowlob = (float) TPshadowlob / (TPshadowlob + FNshadowlob); // recall就是TPR
			float Specificityshadowlob = (float) TNshadowlob / (FPshadowlob + TNshadowlob); // TNR
			float FPRshadowlob = (float) FPshadowlob / (FPshadowlob + TNshadowlob);
			float FNRshadowlob = (float) FNshadowlob / (FNshadowlob + TPshadowlob);
			float PCCshadowlob = (float) (TPshadowlob + TNshadowlob) / (TPshadowlob + TNshadowlob + FPshadowlob + FNshadowlob); // Percentage of Correct Classification
			float Precisionshadowlob = (float) TPshadowlob / (TPshadowlob + FPshadowlob);
			float Fmeasureshadowlob = (2 * Precisionshadowlob * Recallshadowlob) / (Precisionshadowlob + Recallshadowlob);
			String[] writeLineshadowlob = {Class51[0],"shadowlob",String.valueOf(Recallshadowlob),String.valueOf(Specificityshadowlob),String.valueOf(FPRshadowlob),String.valueOf(FNRshadowlob),String.valueOf(PCCshadowlob),String.valueOf(Precisionshadowlob),String.valueOf(Fmeasureshadowlob),String.valueOf(TPshadowlob),String.valueOf(TNshadowlob),String.valueOf(FPshadowlob),String.valueOf(FNshadowlob)};
    		csvWriter.writeRecord(writeLineshadowlob);
    		
    		float Recallshadowsub = (float) TPshadowsub / (TPshadowsub + FNshadowsub); // recall就是TPR
			float Specificityshadowsub = (float) TNshadowsub / (FPshadowsub + TNshadowsub); // TNR
			float FPRshadowsub = (float) FPshadowsub / (FPshadowsub + TNshadowsub);
			float FNRshadowsub = (float) FNshadowsub / (FNshadowsub + TPshadowsub);
			float PCCshadowsub = (float) (TPshadowsub + TNshadowsub) / (TPshadowsub + TNshadowsub + FPshadowsub + FNshadowsub); // Percentage of Correct Classification
			float Precisionshadowsub = (float) TPshadowsub / (TPshadowsub + FPshadowsub);
			float Fmeasureshadowsub = (2 * Precisionshadowsub * Recallshadowsub) / (Precisionshadowsub + Recallshadowsub);
			String[] writeLineshadowsub = {Class51[0],"shadowsub",String.valueOf(Recallshadowsub),String.valueOf(Specificityshadowsub),String.valueOf(FPRshadowsub),String.valueOf(FNRshadowsub),String.valueOf(PCCshadowsub),String.valueOf(Precisionshadowsub),String.valueOf(Fmeasureshadowsub),String.valueOf(TPshadowsub),String.valueOf(TNshadowsub),String.valueOf(FPshadowsub),String.valueOf(FNshadowsub)};
    		csvWriter.writeRecord(writeLineshadowsub);
    		
    		float Recallshadowvibe = (float) TPshadowvibe / (TPshadowvibe + FNshadowvibe); // recall就是TPR
			float Specificityshadowvibe = (float) TNshadowvibe / (FPshadowvibe + TNshadowvibe); // TNR
			float FPRshadowvibe = (float) FPshadowvibe / (FPshadowvibe + TNshadowvibe);
			float FNRshadowvibe = (float) FNshadowvibe / (FNshadowvibe + TPshadowvibe);
			float PCCshadowvibe = (float) (TPshadowvibe + TNshadowvibe) / (TPshadowvibe + TNshadowvibe + FPshadowvibe + FNshadowvibe); // Percentage of Correct Classification
			float Precisionshadowvibe = (float) TPshadowvibe / (TPshadowvibe + FPshadowvibe);
			float Fmeasureshadowvibe = (2 * Precisionshadowvibe * Recallshadowvibe) / (Precisionshadowvibe + Recallshadowvibe);
			String[] writeLineshadowvibe = {Class51[0],"shadowvibe",String.valueOf(Recallshadowvibe),String.valueOf(Specificityshadowvibe),String.valueOf(FPRshadowvibe),String.valueOf(FNRshadowvibe),String.valueOf(PCCshadowvibe),String.valueOf(Precisionshadowvibe),String.valueOf(Fmeasureshadowvibe),String.valueOf(TPshadowvibe),String.valueOf(TNshadowvibe),String.valueOf(FPshadowvibe),String.valueOf(FNshadowvibe)};
    		csvWriter.writeRecord(writeLineshadowvibe);
    		
    		TPallgmm = TPbwgmm + TPblgmm + TPcamgmm + TPintergmm + TPshadowgmm;
    		TNallgmm = TNbwgmm + TNblgmm + TNcamgmm + TNintergmm + TNshadowgmm;
    		FPallgmm = FPbwgmm + FPblgmm + FPcamgmm + FPintergmm + FPshadowgmm;
    		FNallgmm = FNbwgmm + FNblgmm + FNcamgmm + FNintergmm + FNshadowgmm;
    		
    		TPallkde = TPbwkde + TPblkde + TPcamkde + TPinterkde + TPshadowkde;
            TNallkde = TNbwkde + TNblkde + TNcamkde + TNinterkde + TNshadowkde;
            FPallkde = FPbwkde + FPblkde + FPcamkde + FPinterkde + FPshadowkde;
            FNallkde = FNbwkde + FNblkde + FNcamkde + FNinterkde + FNshadowkde;
            
            TPalllob = TPbwlob + TPbllob + TPcamlob + TPinterlob + TPshadowlob;
            TNalllob = TNbwlob + TNbllob + TNcamlob + TNinterlob + TNshadowlob;
            FPalllob = FPbwlob + FPbllob + FPcamlob + FPinterlob + FPshadowlob;
            FNalllob = FNbwlob + FNbllob + FNcamlob + FNinterlob + FNshadowlob;
            
            TPallsub = TPbwsub + TPblsub + TPcamsub + TPintersub + TPshadowsub;
            TNallsub = TNbwsub + TNblsub + TNcamsub + TNintersub + TNshadowsub;
            FPallsub = FPbwsub + FPblsub + FPcamsub + FPintersub + FPshadowsub;
            FNallsub = FNbwsub + FNblsub + FNcamsub + FNintersub + FNshadowsub;
            
            TPallvibe = TPbwvibe + TPblvibe + TPcamvibe + TPintervibe + TPshadowvibe;
            TNallvibe = TNbwvibe + TNblvibe + TNcamvibe + TNintervibe + TNshadowvibe;
            FPallvibe = FPbwvibe + FPblvibe + FPcamvibe + FPintervibe + FPshadowvibe;
            FNallvibe = FNbwvibe + FNblvibe + FNcamvibe + FNintervibe + FNshadowvibe;
            
            float Recallallgmm = (float) TPallgmm / (TPallgmm + FNallgmm); 
            float Specificityallgmm = (float) TNallgmm / (FPallgmm + TNallgmm); 
            float FPRallgmm = (float) FPallgmm / (FPallgmm + TNallgmm);
            float FNRallgmm = (float) FNallgmm / (FNallgmm + TPallgmm);
            float PCCallgmm = (float) (TPallgmm + TNallgmm) / (TPallgmm + TNallgmm + FPallgmm + FNallgmm); // Percentage of Correct Classification
            float Precisionallgmm = (float) TPallgmm / (TPallgmm + FPallgmm);
            float Fmeasureallgmm = (2 * Precisionallgmm * Recallallgmm) / (Precisionallgmm + Recallallgmm);
            String[] writeLineallgmm = {"ALL","allgmm",String.valueOf(Recallallgmm),String.valueOf(Specificityallgmm),String.valueOf(FPRallgmm),String.valueOf(FNRallgmm),String.valueOf(PCCallgmm),String.valueOf(Precisionallgmm),String.valueOf(Fmeasureallgmm),String.valueOf(TPallgmm),String.valueOf(TNallgmm),String.valueOf(FPallgmm),String.valueOf(FNallgmm)};
            csvWriter.writeRecord(writeLineallgmm);
            
            float Recallallkde = (float) TPallkde / (TPallkde + FNallkde); 
            float Specificityallkde = (float) TNallkde / (FPallkde + TNallkde); 
            float FPRallkde = (float) FPallkde / (FPallkde + TNallkde);
            float FNRallkde = (float) FNallkde / (FNallkde + TPallkde);
            float PCCallkde = (float) (TPallkde + TNallkde) / (TPallkde + TNallkde + FPallkde + FNallkde); // Percentage of Correct Classification
            float Precisionallkde = (float) TPallkde / (TPallkde + FPallkde);
            float Fmeasureallkde = (2 * Precisionallkde * Recallallkde) / (Precisionallkde + Recallallkde);
            String[] writeLineallkde = {"ALL","allkde",String.valueOf(Recallallkde),String.valueOf(Specificityallkde),String.valueOf(FPRallkde),String.valueOf(FNRallkde),String.valueOf(PCCallkde),String.valueOf(Precisionallkde),String.valueOf(Fmeasureallkde),String.valueOf(TPallkde),String.valueOf(TNallkde),String.valueOf(FPallkde),String.valueOf(FNallkde)};
            csvWriter.writeRecord(writeLineallkde);
            
            float Recallalllob = (float) TPalllob / (TPalllob + FNalllob); 
            float Specificityalllob = (float) TNalllob / (FPalllob + TNalllob); 
            float FPRalllob = (float) FPalllob / (FPalllob + TNalllob);
            float FNRalllob = (float) FNalllob / (FNalllob + TPalllob);
            float PCCalllob = (float) (TPalllob + TNalllob) / (TPalllob + TNalllob + FPalllob + FNalllob); // Percentage of Correct Classification
            float Precisionalllob = (float) TPalllob / (TPalllob + FPalllob);
            float Fmeasurealllob = (2 * Precisionalllob * Recallalllob) / (Precisionalllob + Recallalllob);
            String[] writeLinealllob = {"ALL","alllob",String.valueOf(Recallalllob),String.valueOf(Specificityalllob),String.valueOf(FPRalllob),String.valueOf(FNRalllob),String.valueOf(PCCalllob),String.valueOf(Precisionalllob),String.valueOf(Fmeasurealllob),String.valueOf(TPalllob),String.valueOf(TNalllob),String.valueOf(FPalllob),String.valueOf(FNalllob)};
            csvWriter.writeRecord(writeLinealllob);
            
            float Recallallsub = (float) TPallsub / (TPallsub + FNallsub); 
            float Specificityallsub = (float) TNallsub / (FPallsub + TNallsub); 
            float FPRallsub = (float) FPallsub / (FPallsub + TNallsub);
            float FNRallsub = (float) FNallsub / (FNallsub + TPallsub);
            float PCCallsub = (float) (TPallsub + TNallsub) / (TPallsub + TNallsub + FPallsub + FNallsub); // Percentage of Correct Classification
            float Precisionallsub = (float) TPallsub / (TPallsub + FPallsub);
            float Fmeasureallsub = (2 * Precisionallsub * Recallallsub) / (Precisionallsub + Recallallsub);
            String[] writeLineallsub = {"ALL","allsub",String.valueOf(Recallallsub),String.valueOf(Specificityallsub),String.valueOf(FPRallsub),String.valueOf(FNRallsub),String.valueOf(PCCallsub),String.valueOf(Precisionallsub),String.valueOf(Fmeasureallsub),String.valueOf(TPallsub),String.valueOf(TNallsub),String.valueOf(FPallsub),String.valueOf(FNallsub)};
            csvWriter.writeRecord(writeLineallsub);
            
            float Recallallvibe = (float) TPallvibe / (TPallvibe + FNallvibe); 
            float Specificityallvibe = (float) TNallvibe / (FPallvibe + TNallvibe); 
            float FPRallvibe = (float) FPallvibe / (FPallvibe + TNallvibe);
            float FNRallvibe = (float) FNallvibe / (FNallvibe + TPallvibe);
            float PCCallvibe = (float) (TPallvibe + TNallvibe) / (TPallvibe + TNallvibe + FPallvibe + FNallvibe); // Percentage of Correct Classification
            float Precisionallvibe = (float) TPallvibe / (TPallvibe + FPallvibe);
            float Fmeasureallvibe = (2 * Precisionallvibe * Recallallvibe) / (Precisionallvibe + Recallallvibe);
            String[] writeLineallvibe = {"ALL","allvibe",String.valueOf(Recallallvibe),String.valueOf(Specificityallvibe),String.valueOf(FPRallvibe),String.valueOf(FNRallvibe),String.valueOf(PCCallvibe),String.valueOf(Precisionallvibe),String.valueOf(Fmeasureallvibe),String.valueOf(TPallvibe),String.valueOf(TNallvibe),String.valueOf(FPallvibe),String.valueOf(FNallvibe)};
            csvWriter.writeRecord(writeLineallvibe);
    		
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
