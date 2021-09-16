package util;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;

/**
 *
 * @author Ferz Código Lite - https://codigolite.com
 */
public class Thumbnail {

	private String imgOriginal;
	private String imgResult;
	private int calidad;
	private int width;
	private int height;
	private Image img;

	public Thumbnail(Image img, String result) {
		this.img = img;
		imgResult = result;
	}

	public void resize() throws Exception {
		// Cargamos la imagen y la preparamos para manipularla
		Image image = Toolkit.getDefaultToolkit().getImage(imgOriginal);
		MediaTracker mediaTracker = new MediaTracker(new Container());
		mediaTracker.addImage(image, 0);
		mediaTracker.waitForID(0);
		// Sacamos la proporcion de las nuevas medidas
		double tnsPro = (double) width / (double) height;
		// Calculamos la proporcion de la imagen original
		int imgWidth = image.getWidth(null);
		int imgHeight = image.getHeight(null);
		double imgPro = (double) imgWidth / (double) imgHeight;
		// comprobamos cual de las dos medidas introducidas esta desproporcionada
		// para asi corregirla
		if (tnsPro < imgPro) {
			// asignamos una nueva height a la imagen generada
			// para mantener la proporcion con respecto a la original
			height = (int) (width / imgPro);
		} else {
			// lo mismo pero con el width
			width = (int) (height * imgPro);
		}
		BufferedImage tnsImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		BufferedImage thumbnail = Scalr.resize((BufferedImage) image, Scalr.Mode.AUTOMATIC, width, height,
				Scalr.OP_ANTIALIAS);

		// Creamos la imagen a generar, con los atributos requeridos, ancho, algo, y
		// el ultimo parametro especifica que vamos a guardar la imagen en formato
		// "8-bit RGB"
		// que es un formato compatible con sistemas basados en Solaris o Windows
		// hay mas formatos, ver
		// http://java.sun.com/j2se/1.4.2/docs/api/java/awt/image/BufferedImage.html
		// BufferedImage tnsImg = new BufferedImage(width,height,
		// BufferedImage.TYPE_INT_RGB);
		/*
		 * Graphics2D graphics2D = tnsImg.createGraphics();
		 * graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.
		 * VALUE_INTERPOLATION_BILINEAR); graphics2D.drawImage(image, 0, 0, width,
		 * height, null);
		 * 
		 * //Preparamos para escribir la imagen generada en el disco
		 * BufferedOutputStream out = new BufferedOutputStream(new
		 * FileOutputStream(imgResult)); //creamos el "parseador" para guardar la imagen
		 * en formato JPG JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		 * JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(tnsImg);
		 */

		// Asignamos la calidad con la que se va a guardar la imagen de 0-100
		/*
		 * calidad = Math.max(0, Math.min(calidad, 100));
		 * param.setQuality((float)calidad / 100.0f, false);
		 * encoder.setJPEGEncodeParam(param); encoder.encode(tnsImg); out.close();
		 */
		// there you go =D
	}

	public FileInputStream generarThumbnail() {
		BufferedImage tnsImg = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = tnsImg.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(img, 0, 0, img.getWidth(null), img.getHeight(null), null);

		// Preparamos para escribir la imagen generada en el disco
		BufferedOutputStream out;
		try {
			out = new BufferedOutputStream(new FileOutputStream(imgResult));

			ImageIO.write(tnsImg, "jpg", out);

			out.close();
			return new FileInputStream(imgResult);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Thumbnail.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
		}

		return null;

	}

	/**
	 * @return Returns the calidad.
	 */
	public int getCalidad() {
		return calidad;
	}

	/**
	 * @param calidad The calidad to set.
	 */
	public void setCalidad(int calidad) {
		this.calidad = calidad;
	}

	/**
	 * @return Returns the height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height The height to set.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return Returns the imgOriginal.
	 */
	public String getImgOriginal() {
		return imgOriginal;
	}

	/**
	 * @param imgOriginal The imgOriginal to set.
	 */
	public void setImgOriginal(String imgOriginal) {
		this.imgOriginal = imgOriginal;
	}

	/**
	 * @return Returns the imgResult.
	 */
	public String getImgResult() {
		return imgResult;
	}

	/**
	 * @param imgResult The imgResult to set.
	 */
	public void setImgResult(String imgResult) {
		this.imgResult = imgResult;
	}

	/**
	 * @return Returns the width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width The width to set.
	 */
	public void setWidth(int width) {
		this.width = width;
	}

}
