package com.demo.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class ImageUtils {
	public static String encodeFileImageToBase64Binary(String filePath) throws IOException {
        byte[] fileContent = FileUtils.readFileToByteArray(new File(filePath));
        return Base64.getEncoder().encodeToString(fileContent);
	}
	
	public static BufferedImage applyCompression(BufferedImage image) {
		try {
			int newWidth=image.getWidth();
			int newHeight=image.getHeight();
			
			BufferedImage compressImage=new BufferedImage(newWidth, newHeight,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2d=compressImage.createGraphics();
			graphics2d.drawImage(image,0,0,newWidth,newHeight, null);
			graphics2d.dispose();
			return compressImage;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String convertImageToBase64(BufferedImage image) {
		try {
			ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
			ImageIO.write(image, ".jpg", outputStream);
			byte[] imageBytes=outputStream.toByteArray();
			outputStream.close();
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void takeScrShotCompressImage(TakesScreenshot takesScreenshot,ArrayList<String> scrShots) {
		try {
			File scFile=takesScreenshot.getScreenshotAs(OutputType.FILE);
			// 取得當前時間
			SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HHmmss");
			String timeStamp=simpleDateFormat.format(new Date());
			// 截圖檔名
			File location=new File("screenshots");
			String screenShotName=location.getAbsolutePath()+File.separator+timeStamp+".jpg";
			// 將檔案放入資料夾
			FileUtils.copyFile(scFile, new File(screenShotName));
			
			// 讀取原有圖片 並壓縮
			BufferedImage orImage=ImageIO.read(new File(screenShotName));
			BufferedImage compressImage=applyCompression(orImage);
			String base64Image=convertImageToBase64(compressImage);
			scrShots.add(base64Image);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Boolean isPhotoFile(File file) {
		String name=file.getName().toLowerCase();
		return name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".jpeg");
	}
	
	public static void clearScrShotFile() {
		try {
			File folder=new File("screenshots");
			if (folder.exists() && folder.isDirectory()) {
				File[] files=folder.listFiles();
				if (files!=null) {
					for (File file : files) {
						if (isPhotoFile(file)) {
							if (file.delete()) {
								System.out.println("已刪除的文件"+file.getName());
							}else {
								System.out.println("無法刪除文件"+file.getName());
							}
						}
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
