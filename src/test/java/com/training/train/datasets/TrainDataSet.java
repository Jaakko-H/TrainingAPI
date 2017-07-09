package com.training.train.datasets;

import java.util.HashMap;
import java.util.Map;

import com.training.train.model.Train;

public class TrainDataSet {
	public static Map<Long, Train[]> trainDataMap = new HashMap<Long, Train[]>();
	public final static Train[] TRAIN_DATA_SET = {
			new Train(1, "foo"),
			new Train(3, "bar"),
			new Train(1337, "test")
	};
	public final static int[] TRAIN_NUMBERS_DATA_SET = {
			1,
			1337
	};
}
