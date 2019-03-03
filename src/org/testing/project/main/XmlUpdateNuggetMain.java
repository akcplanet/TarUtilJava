package org.testing.project.main;

import org.testing.project.entity.Nugget;
import org.testing.project.service.XmlUpdateNuggetService;

public class XmlUpdateNuggetMain {

	public static void main(String[] args) {
		Nugget nugget = new Nugget();
		nugget.setCusip("500TESTCUSIPTEST");
		nugget.setInvnum("500");
		nugget.setPortfoliosPortfolioName("500portfoliosPortfolioName");
		nugget.setTouchCount("500touchCount");
		nugget.setInputFolderPath("E:/xml2");
		nugget.setOutputFolderPath("E:/xml3/new/test");
		nugget.setNuggetCount(10);
		XmlUpdateNuggetService.updateXMLNugget(nugget);
	}

}
