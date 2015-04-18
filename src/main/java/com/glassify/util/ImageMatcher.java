package com.glassify.util;

import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;

import java.awt.image.BufferedImage;
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

import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;

public class ImageMatcher{
		private static final String cascades_file= "/opt/project/cascades.xml";
		private static final String templateCommand = "/home/ec2-user/files/ImageMatching/templateMatch";
		private static final String templates = "/home/ec2-user/files/templates/";
		private static final String siftCommand = "/home/ec2-user/files/ImageMatching/siftMatch";
		private static int fileNameCounter = 1;
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

			matcher.matchHarr(fileContent);
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
		
		public String matchHarr(byte[] bytes){
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

        private List<String> getAllCascade(String cascades_file){
                List<String> cascades = new ArrayList<String>();
            try(BufferedReader br = new BufferedReader(new FileReader(cascades_file))) {
                String line = br.readLine();

                while (line != null) {
                        cascades.add(line);
                    line = br.readLine();
                }
            }
            catch(IOException e){
                System.out.println("IOException");
            }
            return cascades;
        }
        
        private String detect(String XML_FILE, IplImage src){
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

        private String matchTemplate(String fileName){
        	
        	try
            {            
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec(templateCommand + " " + templates + " " + fileName);
                int exitVal = proc.waitFor();
                System.out.println("Process exitValue: " + exitVal);
                return getBrand(exitVal);
            } catch (Throwable t)
            {
                t.printStackTrace();
            }	
        	return null;
        }
        
        private String matchSift(String fileName) {
        	try
            {
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec( siftCommand + " " + templates + " " + fileName);
                int exitVal = proc.waitFor();
                System.out.println("Process exitValue: " + exitVal);
                return getBrand(exitVal);
            } catch (Throwable t)
            {
                t.printStackTrace();
            }
        	return null;
        }
        
		public String match(byte[] bytes){
        	String fileName = writeByteToFile(bytes);
        	String resultBrand = "Not Found";

        	if (fileName != null) {
        	    resultBrand = matchTemplate(fileName);
        	}
        	return resultBrand;
		}

		private String getBrand(int value) {
			String brandName = "Not Found";
			if(value == 100) {
            	brandName = "Adobe Reader";
            }
            else {
            	brandName =  "Walmart";
            }
			return brandName;
		}

        private String writeByteToFile(byte[] imageData) {
	        // Write bytes to tmp file.
            if(fileNameCounter == 1000) {
                fileNameCounter = 0;
            }
            fileNameCounter++;
            String tmpFileName = "/tmp/scan_" + fileNameCounter + ".jpg";
	        final File tmpImageFile = new File(tmpFileName);
	        FileOutputStream tmpOutputStream = null;
	        try {
	            tmpOutputStream = new FileOutputStream(tmpImageFile);
	            tmpOutputStream.write(imageData);
	            System.out.println("File successfully written to tmp file [" + tmpFileName + "]");
	            return tmpFileName;
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
	            if(tmpOutputStream != null) {
	                try {
	                    tmpOutputStream.close();
	                } catch (IOException e) {
	                    System.out.println("IOException: " + e);
	                }
	            }
	        }
        }
}

