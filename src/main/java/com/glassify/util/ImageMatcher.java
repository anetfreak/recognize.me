package com.glassify.util;

import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
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
		private static final String cascades_file= "cascades.txt";
		private IplImage convertBtyeToIplImagebyte(byte[] bytes){
			InputStream in = new ByteArrayInputStream(bytes);
			BufferedImage buffImage = null;;
			try {
				buffImage = ImageIO.read(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		    //byte[] data = ((DataBufferByte) buffImage.getRaster().getDataBuffer()).getData();
		    IplImage image = IplImage.createFrom(buffImage);
		    return image;
		}
		
		public void matchImage(byte[] bytes){
			IplImage image = convertBtyeToIplImagebyte(bytes);
			List<String> cascades = getAllCascade(cascades_file);
            while(!cascades.isEmpty()){
                    detect(cascades.remove(0), image);
            }
		}

        public List getAllCascade(String cascades_file){
                List<String> cascades = new ArrayList<String>();
            try(BufferedReader br = new BufferedReader(new FileReader(cascades_file))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                        System.out.println(line);
                        cascades.add(line);
                    line = br.readLine();
                }
            }
            catch(IOException e){
                System.out.println("IOException");
            }
            return cascades;
        }
        
        public void detect(String XML_FILE, IplImage src){

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
            if (total_Faces > 0)
                System.out.println("Success : " + XML_FILE);

    }
}

