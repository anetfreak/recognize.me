package com.glassify.util;

import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;

public class ImageMatcher{
		private static final String cascades_file= "/opt/project/cascades.xml";
		
		public static void main(String[] args){
			ImageMatcher matcher = new ImageMatcher();
			File file = new File("/opt/project/test_samples/test2.jpg");
	        FileInputStream fin = null;
            try {
				fin = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
 
            byte fileContent[] = new byte[(int)file.length()];
             
            try {
				fin.read(fileContent);
			} catch (IOException e) {
				e.printStackTrace();
			}

			matcher.matchImage(fileContent);
		}
		
		private IplImage convertBtyeToIplImagebyte(byte[] bytes){
			InputStream in = new ByteArrayInputStream(bytes);
			BufferedImage buffImage = null;;
			try {
				buffImage = ImageIO.read(in);
			} catch (IOException e) {
				e.printStackTrace();
			}

		    //byte[] data = ((DataBufferByte) buffImage.getRaster().getDataBuffer()).getData();
		    IplImage image = IplImage.createFrom(buffImage);
		    return image;
		}
		
		public String matchImage(byte[] bytes){
			IplImage image = convertBtyeToIplImagebyte(bytes);
			//Write image to Disk
			try {
				FileOutputStream fos = new FileOutputStream("/opt/project/" + Math.random() + ".jpg");
				fos.write(bytes);
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<String> cascades = getAllCascade(cascades_file);
            while(!cascades.isEmpty()){
            	//System.out.println(cascades.remove(0));
                String result = detect(cascades.remove(0), image);
                if (result != null)
                	return result;
            }
            return "Failed";
		}

        public List getAllCascade(String cascades_file){
                List<String> cascades = new ArrayList<String>();
            try(BufferedReader br = new BufferedReader(new FileReader(cascades_file))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
//                        System.out.println(line);
                        cascades.add(line);
                    line = br.readLine();
                }
            }
            catch(IOException e){
                System.out.println("IOException");
            }
            return cascades;
        }
        
        public String detect(String XML_FILE, IplImage src){
        	//System.out.println("Loading file: " + XML_FILE);
            CvHaarClassifierCascade cascade = new
            CvHaarClassifierCascade(cvLoad(XML_FILE));
            
            CvMemStorage storage = CvMemStorage.create();
            CvSeq sign = cvHaarDetectObjects(
                    src,
                    cascade,
                    storage,
                    2,
                    3,
                    0);

            cvClearMemStorage(storage);
            
            int total_Faces = sign.total();
            if (total_Faces > 1){
                //System.out.println("Success : " + XML_FILE);
                String[] parts=XML_FILE.split("/");
                if((parts != null) && (parts.length > 0)){
                	String file = parts[parts.length-1];
                	System.out.println("File Path: "+ XML_FILE);
                	parts = file.split("[.]");
                	if((parts != null) && (parts.length > 0)){
                		file = parts[0];
                		System.out.println("BrandName : " + file);
                		return file;
                	}
                }
            }
            return null;
        }
        
        public String matchTemplate(byte[] bytes){
        	writeByteToFile(bytes);
        	try
            {            
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec("/home/ec2-user/files/ImageMatching/templateMatch /home/ec2-user/files/templates /tmp/scan.jpg");
                int exitVal = proc.waitFor();
                System.out.println("Process exitValue: " + exitVal);
                if(exitVal == 100){
                	return "Adobe Reader";
                }
                else {
                	return "Walmart";
                }
            } catch (Throwable t)
            {
                t.printStackTrace();
            }	
        	return null;
        }
        
        private String writeByteToFile(byte[] imageData) {
	     // Write bytes to tmp file.
	        final File tmpImageFile = new File("/tmp/scan.jpg");
	        FileOutputStream tmpOutputStream = null;
	        try {
	            tmpOutputStream = new FileOutputStream(tmpImageFile);
	            tmpOutputStream.write(imageData);
	            System.out.println("File successfully written to tmp file");
	            return "success";
	        }
	        catch (FileNotFoundException e) {
	            System.out.println("FileNotFoundException: " + e);
	            return null;
	        }
	        catch (IOException e) {
	            System.out.println( "IOException: " + e);
	            return null;
	        }
	        finally {
	            if(tmpOutputStream != null)
	                try {
	                    tmpOutputStream.close();
	                } catch (IOException e) {
	                    System.out.println("IOException: " + e);
	                }
	        }
        }
}

