package com.project.sam.bargrocery.Utilities;

import com.project.sam.bargrocery.Pages.Results;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samantha Hamor on 5/5/2015.
 * @params inputArr - array to be sorted
 * @params mode - use 0 for price sort and 1 for number of items sort
 */
public class MergeSort {
        private List<Results.headerHolder> arr;
        private List<Results.headerHolder> temp;
        private int size;
        private int mode;


        public void sort(List<Results.headerHolder> inputArr, int mode) {
            this.arr = inputArr;
            this.size = inputArr.size();
            this.temp = new ArrayList<Results.headerHolder>();
            for(int numnulls = 0; numnulls < size; numnulls++){
                temp.add(null);
            }
            this.mode = mode;
            doMergeSort(0, size - 1);
        }

        private void doMergeSort(int lowerIndex, int higherIndex) {

            if (lowerIndex < higherIndex) {
                int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
                //Sort left side
                doMergeSort(lowerIndex, middle);
                //Sorts right side
                doMergeSort(middle + 1, higherIndex);
                //Merge Left and Right
                mergeParts(lowerIndex, middle, higherIndex);
            }
        }

        private void mergeParts(int lowerIndex, int middle, int higherIndex) {

            for (int i = lowerIndex; i <= higherIndex; i++) {
                    temp.set(i, arr.get(i));
            }
            int i = lowerIndex;
            int j = middle + 1;
            int k = lowerIndex;
            while (i <= middle && j <= higherIndex) {
                if(mode == 1){
                    if (temp.get(i).itemsAndPrices.size() >= temp.get(j).itemsAndPrices.size()) { //sorting by num items
                        arr.set(k, temp.get(i));
                        i++;
                    } else {
                        arr.set(k, temp.get(j));
                        j++;
                    }
                }else {
                    if (temp.get(i).total <= temp.get(j).total) {//sorting by price
                        arr.set(k, temp.get(i));
                        i++;
                    } else {
                        arr.set(k, temp.get(j));
                        j++;
                    }
                }k++;

            }
            while (i <= middle) {
                arr.set(k,temp.get(i));
                k++;
                i++;
            }
        }
    }
