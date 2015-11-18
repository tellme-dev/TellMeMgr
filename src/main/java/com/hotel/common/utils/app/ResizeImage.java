/**
 * copy right 2012 sctiyi all rights reserved
 * create time:上午10:15:11
 * author:ftd
 */
package com.hotel.common.utils.app;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * @author ftd
 * 缩略图片
 */
public class ResizeImage {
	public static BufferedImage resize(BufferedImage source, int targetW,
			int targetH) {
		// targetW，targetH分别表示目标长和�?   
		int type = source.getType();
		BufferedImage target = null;
		double sx = (double) targetW / source.getWidth();
		double sy = (double) targetH / source.getHeight();
		//这里想实现在targetW，targetH范围内实现等比缩放�?如果不需要等比缩�?   
		//则将下面的if else语句注释即可    
		if (sx > sy) {
			sx = sy;
			targetW = (int) (sx * source.getWidth());
		} else {
			sy = sx;
			targetH = (int) (sy * source.getHeight());
		}
		if (type == BufferedImage.TYPE_CUSTOM) { //handmade    
			ColorModel cm = source.getColorModel();
			WritableRaster raster = cm.createCompatibleWritableRaster(targetW,
					targetH);
			boolean alphaPremultiplied = cm.isAlphaPremultiplied();
			target = new BufferedImage(cm, raster, alphaPremultiplied, null);
		} else
			target = new BufferedImage(targetW, targetH, type);
		Graphics2D g = target.createGraphics();
		//smoother than exlax:    
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
		g.dispose();
		return target;
	}

	public static void saveImageAsJpg(String fromFileStr, String saveToFileStr,
			int width, int hight) throws Exception {
		BufferedImage srcImage;
		// String ex = fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());    
		String imgType = "JPEG";
		if (fromFileStr.toLowerCase().endsWith(".png")) {
			imgType = "PNG";
		}
		// System.out.println(ex);    
		File saveFile = new File(saveToFileStr);
		File fromFile = new File(fromFileStr);
		srcImage = ImageIO.read(fromFile);
		if (width > 0 || hight > 0) {
			srcImage = resize(srcImage, width, hight);
		}
		ImageIO.write(srcImage, imgType, saveFile);

	}
	
	public static void saveImageAsFile(File fromFile, String imgType,String saveToFileStr,
			int width, int hight) throws Exception {
		BufferedImage srcImage;
		if (imgType.equalsIgnoreCase("png")) {
			imgType = "PNG";
		}else if (imgType.equalsIgnoreCase("bmp")) {
			imgType = "BMP";
		}else{
			imgType = "JPEG";
		}
		File saveFile = new File(saveToFileStr);
		srcImage = ImageIO.read(fromFile);
		if (width > 0 || hight > 0) {
			srcImage = resize(srcImage, width, hight);
		}
		ImageIO.write(srcImage, imgType, saveFile);

	}

	public static void main(String argv[]) {
		try {
			//参数1(from),参数2(to),参数3(�?,参数4(�?    
			ResizeImage.saveImageAsJpg("G:\\images\\201209\\20120914\\sdfgghhhhhhhhhhhh_1347588312406.jpg",
					"G:\\images\\201209\\20120914\\sdfgghhhhhhhhhhhh_1347588312406_small.jpg", 100, 100);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
