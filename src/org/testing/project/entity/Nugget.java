package org.testing.project.entity;

import java.io.Serializable;

public class Nugget implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cusip;
	private String invnum;
	private String portfoliosPortfolioName;
	private String touchCount;
	private String inputFolderPath;
	private String outputFolderPath;
	private int nuggetCount = 1;

	public String getCusip() {
		return cusip;
	}

	public void setCusip(String cusip) {
		this.cusip = cusip;
	}

	public String getInvnum() {
		return invnum;
	}

	public void setInvnum(String invnum) {
		this.invnum = invnum;
	}

	public String getPortfoliosPortfolioName() {
		return portfoliosPortfolioName;
	}

	public void setPortfoliosPortfolioName(String portfoliosPortfolioName) {
		this.portfoliosPortfolioName = portfoliosPortfolioName;
	}

	public String getTouchCount() {
		return touchCount;
	}

	public void setTouchCount(String touchCount) {
		this.touchCount = touchCount;
	}

	public String getInputFolderPath() {
		return inputFolderPath;
	}

	public void setInputFolderPath(String inputFolderPath) {
		this.inputFolderPath = inputFolderPath;
	}

	public String getOutputFolderPath() {
		return outputFolderPath;
	}

	public void setOutputFolderPath(String outputFolderPath) {
		this.outputFolderPath = outputFolderPath;
	}

	public int getNuggetCount() {
		return nuggetCount;
	}

	public void setNuggetCount(int nuggetCount) {
		this.nuggetCount = nuggetCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cusip == null) ? 0 : cusip.hashCode());
		result = prime * result + ((inputFolderPath == null) ? 0 : inputFolderPath.hashCode());
		result = prime * result + ((invnum == null) ? 0 : invnum.hashCode());
		result = prime * result + nuggetCount;
		result = prime * result + ((outputFolderPath == null) ? 0 : outputFolderPath.hashCode());
		result = prime * result + ((portfoliosPortfolioName == null) ? 0 : portfoliosPortfolioName.hashCode());
		result = prime * result + ((touchCount == null) ? 0 : touchCount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nugget other = (Nugget) obj;
		if (cusip == null) {
			if (other.cusip != null)
				return false;
		} else if (!cusip.equals(other.cusip))
			return false;
		if (inputFolderPath == null) {
			if (other.inputFolderPath != null)
				return false;
		} else if (!inputFolderPath.equals(other.inputFolderPath))
			return false;
		if (invnum == null) {
			if (other.invnum != null)
				return false;
		} else if (!invnum.equals(other.invnum))
			return false;
		if (nuggetCount != other.nuggetCount)
			return false;
		if (outputFolderPath == null) {
			if (other.outputFolderPath != null)
				return false;
		} else if (!outputFolderPath.equals(other.outputFolderPath))
			return false;
		if (portfoliosPortfolioName == null) {
			if (other.portfoliosPortfolioName != null)
				return false;
		} else if (!portfoliosPortfolioName.equals(other.portfoliosPortfolioName))
			return false;
		if (touchCount == null) {
			if (other.touchCount != null)
				return false;
		} else if (!touchCount.equals(other.touchCount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nugget [cusip=" + cusip + ", invnum=" + invnum + ", portfoliosPortfolioName=" + portfoliosPortfolioName
				+ ", touchCount=" + touchCount + ", inputFolderPath=" + inputFolderPath + ", outputFolderPath="
				+ outputFolderPath + ", nuggetCount=" + nuggetCount + "]";
	}

}
