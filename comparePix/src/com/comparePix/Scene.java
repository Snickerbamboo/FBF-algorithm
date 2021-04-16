package com.comparePix;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: Chuansheng Zhong
 * @create: 2020-07-15 15:39
 **/
public class Scene {
    private String name;
    private List<ImageAlgorithm> algorithmList = new ArrayList<>();

    public Scene(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImageAlgorithm> getAlgorithmList() {
        return algorithmList;
    }

    public void setAlgorithmList(List<ImageAlgorithm> algorithmList) {
        this.algorithmList = algorithmList;
    }

    @Override
    public String toString() {
        return "Scene{" +
                "name='" + name + '\'' +
                ", algorithmList=" + algorithmList +
                '}';
    }
}
