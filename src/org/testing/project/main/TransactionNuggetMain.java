package org.testing.project.main;

import org.testing.project.entity.Nugget;
import org.testing.project.service.TransactionNuggetService;

public class TransactionNuggetMain {

	public static void main(String[] args) {
		Nugget nugget = new Nugget();
		nugget.setInputFolderPath("E:/xml2");
		nugget.setOutputFolderPath("E:/xml3/new/test");
		nugget.setNuggetCount(100);
		TransactionNuggetService.updateTransactionNugget(nugget);
	}

}
