package com.novel.util;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片裁剪缩放类
 * @author Aroceee
 *
 */
@SuppressWarnings("restriction")
public class DwindlePic {
	
	private static final Logger log = LoggerFactory.getLogger(DwindlePic.class);
	static Properties prop = PropertiesUtil.getPropertyFile("/prop.properties");

	private Image img;
	private InputStream in; // 输入流
	private String OutputDir; // 输出图路径
	private String OutputFileName; // 输出图文件名
	private int OutputWidth; // 默认输出图片宽
	private int OutputHeight; // 默认输出图片高
	private boolean proportion = false; // 是否等比缩放标记(默认不等比缩放)

	public DwindlePic() {
		// 初始化变量
		this.img = null;
		this.in = null;
		this.OutputDir = prop.getProperty("photo.path");
		this.OutputFileName = "";
		this.OutputWidth = Integer.parseInt(prop.getProperty("photo.width"));
		this.OutputHeight = Integer.parseInt(prop.getProperty("photo.height"));
	}
	
	public String handle(InputStream i, float l, float t, float areaW, float areaH, float w, float h, String extention, String username) throws Exception {
		try {
			if(outOfImage(w, h, l, t, areaW, areaH)){
				throw new RuntimeException("outOfImage");
			}
			
			this.in = i;
			this.img = this.cutImage(this.in, l, t, areaW, areaH, w, h);
		} catch (Exception e) {
			log.error("头像上传======头像裁剪失败！");
			throw e;
		}
		
		extention = extention == null ? "jpg" : extention; // 默认JPG格式
		
		this.OutputFileName = username + new Date().getTime() + "." + extention;
		
		if(this.s_pic(l, t, w, h)){
			return this.OutputFileName;
		} else {
			return "";
		}
	}
	
	public void deleteFile(String path) throws Exception {
		File file = new File(OutputDir + path);
		file.delete();
	}

	@SuppressWarnings("resource")
	private boolean s_pic(float l, float t, float w, float h) throws Exception {
		// 建立输出文件对象
		File file = new File(OutputDir + OutputFileName);
		FileOutputStream tempout = new FileOutputStream(file);
		Applet app = new Applet();
		MediaTracker mt = new MediaTracker(app);
		try {
			mt.addImage(img, 0);
			mt.waitForID(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (img.getWidth(null) == -1) {
			return false;
		} else {
			int new_w;
			int new_h;
			if (this.proportion == true) { // 判断是否是等比缩放.
				// 为等比缩放计算输出的图片宽度及高度
				double rate1 = ((double) img.getWidth(null))
						/ (double) OutputWidth + 0.1;
				double rate2 = ((double) img.getHeight(null))
						/ (double) OutputHeight + 0.1;
				double rate = rate1 > rate2 ? rate1 : rate2;
				new_w = (int) (((double) img.getWidth(null)) / rate);
				new_h = (int) (((double) img.getHeight(null)) / rate);
			} else {
				new_w = OutputWidth; // 输出的图片宽度
				new_h = OutputHeight; // 输出的图片高度
			}
			BufferedImage buffImg = new BufferedImage(new_w, new_h,
					BufferedImage.TYPE_INT_BGR);

			Graphics g = buffImg.createGraphics();

			g.setColor(Color.white);
			g.fillRect(0, 0, new_w, new_h);

			g.drawImage(img, 0, 0, new_w, new_h, null);
			g.dispose();

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(tempout);
			// 设置图片质量
			JPEGEncodeParam encoderParam = encoder.getDefaultJPEGEncodeParam(buffImg);
			encoderParam.setQuality(1, true);
			encoder.setJPEGEncodeParam(encoderParam);
			try {
				encoder.encode(buffImg);
				tempout.close();
			} catch (IOException ex) {
				log.error("头像上传======头像缩放失败！");
			}
		}
		return true;
	}
	
	// 对图片进行裁剪
	private Image cutImage(InputStream in, float left, float top, float areaW, float areaH, float width, float height) throws Exception{
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		
		BufferedImage image = ImageIO.read(iis);
		
		int w = image.getWidth();
		int h = image.getHeight();
		
		int x = Math.round(w * (left / width));
		int y = Math.round(h * (top / height));
		
		int OutputWidth = Math.round(w * (areaW / width));
		int OutputHeight = Math.round(h * (areaH / height));
		
		// 目标图片
		ImageFilter cropFilter = new CropImageFilter(x, y, OutputWidth, OutputHeight);
		Image cutImage = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
		
		return cutImage;
	}
	
	// 是否超界
	private boolean outOfImage(float width, float height, float left, float top, float areaWidth, float areaHeight) throws Exception {
		
		if(left < 0 || top < 0 || areaWidth <= 0 || areaHeight <= 0 || width <= 0 || height <= 0){
			return true;
		}
		if(areaWidth > width || areaHeight > height) {
			return true;
		}
		return false;
	}

	public void setOutputDir(String OutputDir) {
		this.OutputDir = OutputDir;
	}

	public void setOutputFileName(String OutputFileName) {
		this.OutputFileName = OutputFileName;
	}

	public void setOutputWidth(int OutputWidth) {
		this.OutputWidth = OutputWidth;
	}

	public void setOutputHeight(int OutputHeight) {
		this.OutputHeight = OutputHeight;
	}

	public void setW_H(int width, int height) {
		this.OutputWidth = width;
		this.OutputHeight = height;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
}
