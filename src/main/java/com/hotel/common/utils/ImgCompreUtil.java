package com.hotel.common.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

import sun.misc.BASE64Encoder;

public class ImgCompreUtil {

 	private Image img;  
    private int width;  
    private int height;
    private String savePath;
    private String fileName;
    private File file;
    
    private static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    
    /** 
     * 构造函数 
     */  
    public ImgCompreUtil(String fileNames,String savePathtemp) throws IOException {
        File file = new File(fileNames);// 读入文件  
        img = ImageIO.read(file);      // 构造Image对象  
        width = img.getWidth(null);    // 得到源图宽  
        height = img.getHeight(null);  // 得到源图长  
        savePath = savePathtemp;
        fileName = fileNames;
    }  
    /** 
     * 构造函数 
     */  
    public ImgCompreUtil(File file,String fileNames,String savePathtemp) throws IOException {
    	this.file = file;
        img = ImageIO.read(file);      // 构造Image对象  
        width = img.getWidth(null);    // 得到源图宽  
        height = img.getHeight(null);  // 得到源图长  
        savePath = savePathtemp;
        fileName = fileNames;
    }  
    /** 
     * 按照宽度还是高度进行压缩 
     * @param w int 最大宽度 
     * @param h int 最大高度 
     */  
    public void resizeFix(int w, int h) throws IOException {  
        if (width / height > w / h) {  
            resizeByWidth(w);  
        } else {  
            resizeByHeight(h);  
        }  
    }  
    /** 
     * 以宽度为基准，等比例放缩图片 
     * @param w int 新宽度 
     */  
    public void resizeByWidth(int w) throws IOException {  
        int h = height * w / width;  
        resize(w, h);  
    }  
    /** 
     * 以高度为基准，等比例缩放图片 
     * @param h int 新高度 
     */  
    public void resizeByHeight(int h) throws IOException {  
        int w = width * h / height;  
        resize(w, h);  
    }
    /** 
     * 自动等比例缩放图片 
     * @param h int 新高度 
     */  
    public void resizePIC(boolean fas)throws IOException {  
    	if(fas){
    		BufferedImage sourceImg =ImageIO.read(new FileInputStream(fileName));
       		 int widths = sourceImg.getWidth(); 
       		 int hights = sourceImg.getHeight();
    		 // 为等比缩放计算输出的图片宽度及高度 
			 double rate1 = ((double) img.getWidth(null)) / (double) widths + 0.1; 
			 double rate2 = ((double) img.getHeight(null)) / (double) hights + 0.1; 
			 // 根据缩放比率大的进行缩放控制 
			 double rate = rate1 > rate2 ? rate1 : rate2; 
			 width = (int) ((img.getWidth(null)) / rate); 
			 height = (int) ((img.getHeight(null)) / rate); 
    	}
    	resize(width, height);  
    }
    /** 
     * 自动等比例缩放图片 
     * @param h int 新高度 
     */  
    public void resizeappPIC(boolean fas)throws IOException {  
    	if(fas){
    		BufferedImage sourceImg =ImageIO.read(this.file);
       		 int widths = sourceImg.getWidth(); 
       		 int hights = sourceImg.getHeight();
    		 // 为等比缩放计算输出的图片宽度及高度 
			 double rate1 = ((double) img.getWidth(null)) / (double) widths + 0.1; 
			 double rate2 = ((double) img.getHeight(null)) / (double) hights + 0.1; 
			 // 根据缩放比率大的进行缩放控制 
			 double rate = rate1 > rate2 ? rate1 : rate2; 
			 width = (int) ((img.getWidth(null)) / rate); 
			 height = (int) ((img.getHeight(null)) / rate); 
    	}
    	resize(width, height);  
    }
    /** 
     * 强制压缩/放大图片到固定的大小 
     * @param w int 新宽度 
     * @param h int 新高度 
     */  
    @SuppressWarnings("restriction")
	public void resize(int w, int h) throws IOException {  
        // SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢  
        BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB );   
//        image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图  
        image.getGraphics().drawImage(img.getScaledInstance(w, h, Image.SCALE_SMOOTH), 0, 0, null);
        File destFile = new File(savePath);  
        FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流  
        // 可以正常实现bmp、png、gif转jpg  
       
		com.sun.image.codec.jpeg.JPEGImageEncoder encoder = com.sun.image.codec.jpeg.JPEGCodec.createJPEGEncoder(out);  
        encoder.encode(image); // JPEG编码  
        out.close();  
    }
    /** 
     * 强制压缩/放大图片到固定的大小 
     * @param imagePath 图片路径
     * @param imageType 图片类型
     */ 
    @SuppressWarnings("restriction")
	public static String getImageBinary(String imagePath,String imageType){      
        File f = new File(imagePath);             
        BufferedImage bi;      
        try {      
            bi = ImageIO.read(f);      
            ByteArrayOutputStream baos = new ByteArrayOutputStream();      
            ImageIO.write(bi, imageType, baos);      
            byte[] bytes = baos.toByteArray();      
                  
            return encoder.encodeBuffer(bytes).trim();      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        return null;    
    }   
    
	public Image getImg() {
		return img;
	}
	public void setImg(Image img) {
		this.img = img;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
