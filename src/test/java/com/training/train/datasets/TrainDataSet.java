package com.training.train.datasets;

import com.training.train.model.Train;

public class TrainDataSet {
	public final static Train[] train1 		= new Train[] { new Train(1, "foo") };
	public final static Train[] train3 		= new Train[] { new Train(3, "bar") };
	public final static Train[] train1337 	= new Train[] { new Train(1337, "test") };
	public final static Train[] TRAIN_DATA_SET = {
			new Train(1, "foo"),
			new Train(1337, "test") 
	};
}
