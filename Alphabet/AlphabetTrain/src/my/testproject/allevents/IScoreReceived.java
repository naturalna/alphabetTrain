package my.testproject.allevents;

import java.util.List;

import my.startproject.models.ScoreModel;
import my.startproject.models.UserScoreModel;

public interface IScoreReceived {
	public void scoreReceivedSucceed(ScoreModel model);
	public void scoreReceivedFaild(String errorMessage);
	public void rankListReceivedSucceed(List<UserScoreModel> model);
	public void rankListReceivedFaild(String errorMessage);
}
