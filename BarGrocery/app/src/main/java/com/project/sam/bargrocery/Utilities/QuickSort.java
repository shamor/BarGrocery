package com.project.sam.bargrocery.Utilities;

import com.project.sam.bargrocery.Pages.Results;

import java.util.List;

/**
 * Created by Samantha Hamor on 4/27/2015.
 */
public class QuickSort {
    private List<Results.headerHolder> arr;
    private int size;

    public void sort(List<Results.headerHolder> headerArr) {

        if (headerArr == null || headerArr.size() == 0) {
            return;
        }
        this.arr = headerArr;
        size = headerArr.size();
        quickSort(0, size - 1);
    }

    private void quickSort(int lowerIndex, int higherIndex) {

        int i = lowerIndex;
        int j = higherIndex;
        // calculate pivot number, I am taking pivot as middle index number
        double pivot = arr.get(lowerIndex+(higherIndex-lowerIndex)/2).total;
        // Divide array in half and check pivot value
        while (i <= j) {
            while (arr.get(i).total < pivot) {
                i++;
            }
            while (arr.get(j).total > pivot) {
                j--;
            }
            if (i <= j) {
                exchangeTotals(i, j);
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j)
            quickSort(lowerIndex, j);
        if (i < higherIndex)
            quickSort(i, higherIndex);
    }

    private void exchangeTotals(int i, int j) {
        Results.headerHolder temp = arr.get(i);
        arr.set(i,arr.get(j));
        arr.set(j,temp);
    }

}
