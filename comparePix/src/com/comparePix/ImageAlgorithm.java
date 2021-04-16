package com.comparePix;

/**
 * @description:
 * @author: Chuansheng Zhong
 * @create: 2020-07-15 15:41
 **/
public class ImageAlgorithm {
    private String algorithmName;
    private double recall;
    private double specificity;
    private double FPR;
    private double FNR;
    private double PCC;
    private double Precision;
    private double Fmeasure;
    private long TP;
    private long TN;
    private long FP;
    private long FN;
    private long N;
    private long P;

    @Override
    public String toString() {
        return "ImageAlgorithm{" +
                "algorithmName='" + algorithmName + '\'' +
                ", recall=" + recall +
                ", specificity=" + specificity +
                ", FPR=" + FPR +
                ", FNR=" + FNR +
                ", PCC=" + PCC +
                ", Precision=" + Precision +
                ", Fmeasure=" + Fmeasure +
                ", TP=" + TP +
                ", TN=" + TN +
                ", FP=" + FP +
                ", FN=" + FN +
                ", N=" + N +
                ", P=" + P +
                '}';
    }

    public ImageAlgorithm(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public double getRecall() {
        return recall;
    }

    public void setRecall(double recall) {
        this.recall = recall;
    }

    public double getSpecificity() {
        return specificity;
    }

    public void setSpecificity(double specificity) {
        this.specificity = specificity;
    }

    public double getFPR() {
        return FPR;
    }

    public void setFPR(double FPR) {
        this.FPR = FPR;
    }

    public double getFNR() {
        return FNR;
    }

    public void setFNR(double FNR) {
        this.FNR = FNR;
    }

    public double getPCC() {
        return PCC;
    }

    public void setPCC(double PCC) {
        this.PCC = PCC;
    }

    public double getPrecision() {
        return Precision;
    }

    public void setPrecision(double precision) {
        Precision = precision;
    }

    public double getFmeasure() {
        return Fmeasure;
    }

    public void setFmeasure(double fmeasure) {
        Fmeasure = fmeasure;
    }

    public long getTP() {
        return TP;
    }

    public void setTP(long TP) {
        this.TP = TP;
    }

    public long getTN() {
        return TN;
    }

    public void setTN(long TN) {
        this.TN = TN;
    }

    public long getFP() {
        return FP;
    }

    public void setFP(long FP) {
        this.FP = FP;
    }
    
    public long getFN() {
        return FN;
    }

    public void setFN(long FN) {
        this.FN = FN;
    }

    public long getN() {
        return N;
    }

    public void setN(long n) {
        N = n;
    }

    public long getP() {
        return P;
    }

    public void setP(long p) {
        P = p;
    }
}
