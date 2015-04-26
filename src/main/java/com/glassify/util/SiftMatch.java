package com.glassify.util;


public class SiftMatch{
/*		private static final String cascades_file= "/opt/project/cascades.xml";
		private static final String templateCommand = "/Users/amitagra/Downloads/workspace/opencv/machinelearning/template";
		private static final String templates = "/Users/amitagra/Downloads/workspace/opencv/machinelearning/templates/";
		private static final String templates_sift = "/Users/amitagra/Downloads/workspace/opencv/machinelearning/templates_sift/";
		private static final String siftCommand = "/Users/amitagra/Downloads/workspace/opencv/machinelearning/siftMatch";
		private static int fileNameCounter = 1;
		private static Logger logger = Logger.getLogger(ImageMatcher.class.getName());
		private static PrintWriter writer = MyLogger.getWriter();
		
		public static void main(String[] args) {
			System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			SiftMatch sm = new SiftMatch();
			sm.matcher(null);
		}
		
		private Mat byteToMat(byte[] bytes) {
			Mat mat = new Mat();
			mat.put(0, 0, bytes);
			return mat;
		}
		
		private byte[] matToByte(Mat mat) {
			byte[] buff = new byte[(int) (mat.total() * mat.channels())];
			mat.get(0, 0, buff);
			return buff;
		}
        
		private void matcher(byte[] bytes) {
			//Mat scene = byteToMat(bytes);
			Mat scene = Highgui.imread("/tmp/glassImages/scan_8.jpg");
			
			FeatureDetector siftDetector = FeatureDetector.create(FeatureDetector.SIFT);
			DescriptorExtractor siftExtractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
			
			//Calculations for Scene
			MatOfKeyPoint keyPointScene = new MatOfKeyPoint();
			siftDetector.detect(scene, keyPointScene);
			
			Mat descriptersScene = new Mat(scene.rows(), scene.cols(), scene.type());
			siftExtractor.compute(scene, keyPointScene, descriptersScene);
			
			//TODO Calculations for Template
			Mat template = Highgui.imread("templates_sift/StarbucksCoffee 1.png");
			MatOfKeyPoint keyPointTemplate = new MatOfKeyPoint();
			siftDetector.detect(template, keyPointTemplate);
			
			Mat descriptersTemplate = new Mat(template.rows(), template.cols(), template.type());
			siftExtractor.compute(scene, keyPointTemplate, descriptersTemplate);
			
			//Matching the descriptors
			MatOfDMatch matches = new MatOfDMatch();
			DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.FLANNBASED);
			matcher.match(descriptersTemplate, descriptersScene, matches);
			List<DMatch> matchesList = matches.toList();
		
			MatVector vmatches = new MatVector();
			FlannBasedMatcher fbm;
			
			//fbm.match(descriptersTemplate, descriptersScene, vmatches);
			
			//-- Quick calculation of max and min distances between keypoints
			double max_dist = 0; double min_dist = 100;
		    for( int i = 0; i < descriptersTemplate.rows(); i++ )
		    { double dist = matchesList.get(i).distance;
		      if( dist < min_dist ) min_dist = dist;
		      if( dist > max_dist ) max_dist = dist;
		    }		
			System.out.println("Minimum distance: " + min_dist);
		}
		
        private String matchSift(String fileName) {
        	try
            {
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec( siftCommand + " " + templates_sift + " " + fileName);
                int exitVal = proc.waitFor();
                
                logger.info("Process exitValue: " + exitVal);
                return getBrand(exitVal);
            } catch (Throwable t)
            {
                t.printStackTrace();
            }
        	return null;
        }
        
		public String match(byte[] bytes){
				String tmpFileName = "/tmp/filename.txt";
				final File tmpImageFile = new File(tmpFileName);
		        FileOutputStream tmpOutputStream = null;
		        try {
		            tmpOutputStream = new FileOutputStream(tmpImageFile);
		            tmpOutputStream.write("in Image match, witing file to disk\n".getBytes());
		        	String fileName = writeByteToFile(bytes);
		        	tmpOutputStream.write("file written to disk: \n".getBytes());
		        	String resultBrand = "Not Found";
		
		        	if (fileName != null) {
		        		tmpOutputStream.write("going to do template matching\n".getBytes());
		        	    resultBrand = matchSift(fileName);
		        	    tmpOutputStream.write("match result: \n".getBytes());
		        	}
		        	tmpOutputStream.write("matched Brand: \n".getBytes());
		        	tmpOutputStream.close();
		        	return resultBrand;

		        }
		        catch (FileNotFoundException e) {
		            logger.info("FileNotFoundException: " + e);
		            return null;
		        }
		        catch (IOException e) {
		            logger.info( "IOException: " + e);
		            return null;
		        }
		        finally {
		            if(tmpOutputStream != null) {
		                try {
		                    tmpOutputStream.close();
		                } catch (IOException e) {
		                    logger.info("IOException: " + e);
		                }
		            }
		        }
		}

		private String getBrand(int value) {
			String brandName = "Not Found";
			if(value == 1) {
            	brandName = "Adobe Reader";
            }
            else if(value == 2) {
            	brandName =  "Walmart";
            }
            else if(value == 3) {
            	brandName =  "Starbucks Coffee";
            }
            else if(value == 4) {
            	brandName =  "Amazon";
            }
			return brandName;
		}

        private String writeByteToFile(byte[] imageData) {
	        // Write bytes to tmp file.
        	System.out.println("Writing image to disk");
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
	            logger.info("File successfully written to tmp file [" + tmpFileName + "]");
	            return tmpFileName;
	        }
	        catch (FileNotFoundException e) {
	            logger.info("FileNotFoundException: " + e);
	            return null;
	        }
	        catch (IOException e) {
	            logger.info( "IOException: " + e);
	            return null;
	        }
	        finally {
	            if(tmpOutputStream != null) {
	                try {
	                    tmpOutputStream.close();
	                } catch (IOException e) {
	                    logger.info("IOException: " + e);
	                }
	            }
	        }
        }*/
}

