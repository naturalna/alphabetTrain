package my.startproject.datalayer;

import java.util.ArrayList;
import java.util.List;

import my.testproject.allevents.EventManagerImageDownload;
import my.testproject.allevents.EventManagerTest;
import my.testproject.allevents.ITestRecived;
import my.testproject.allevents.IdownloadedImage;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class LetterRequests {

	private EventManagerImageDownload manager = new EventManagerImageDownload();
	private EventManagerTest managerTests = new EventManagerTest();
	private ArrayList<Item> list = new ArrayList<Item>();
	private String letter = "no";

	public void getImagecollection(IdownloadedImage listener) {
		manager.addListener(listener);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AlphabetImages");
		query.orderByAscending("Name");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> images, ParseException e) {
				if (e == null) {
					for (ParseObject item : images) {
						try {
							letter = item.getString("Name");
							ParseFile image = (ParseFile) item.get("Image");
							byte[] data = image.getData();
							Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,
									data.length);
							Item view = new Item(bmp, letter);
							manager.saySucceed(view);

						} catch (ParseException ex) {
							manager.Faild();
							System.out.println(ex.toString());
							ex.printStackTrace();
						} catch (Exception exception) {
							manager.Faild();
							System.out.println(exception.toString());
						}
					}
				} else {
					System.out.println(e.toString());
					manager.Faild();
				}
				manager.Clear();
			}
		});
	}

	public void getImagecollectionTest(ITestRecived listener) {
		managerTests.addListener(listener);
		ParseQuery<ParseObject> query = ParseQuery.getQuery("AlphabetImages");
		query.findInBackground(new FindCallback<ParseObject>() {
			public void done(List<ParseObject> images, ParseException e) {
				if (e == null) {
					for (ParseObject item : images) {
						try {
							letter = item.getString("Name");
							ParseFile image = (ParseFile) item.get("Image");
							try {
								byte[] data = image.getData();
								Bitmap bmp = BitmapFactory.decodeByteArray(
										data, 0, data.length);
								Item view = new Item(bmp, letter);
								list.add(view);

							} catch (ParseException e1) {
								managerTests.Faild();
								e1.printStackTrace();
							}
						} catch (Exception ex) {
							System.out.println(ex.toString());
						}
					}

					managerTests.saySucceed(list);
				} else {
					System.out.println(e.toString());
				}
				managerTests.Clear();
			}
		});
	}
}
