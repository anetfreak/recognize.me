#include <dirent.h>
#include <sys/stat.h>
#include <stdio.h>
#include <iostream>
#include <fstream>
#include "opencv2/core/core.hpp"
#include "opencv2/features2d/features2d.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/calib3d/calib3d.hpp"
#include "opencv2/nonfree/nonfree.hpp"

using namespace cv;
using namespace std;
//FileStorage storage1("keypoints.yml", FileStorage::WRITE);
//FileStorage storage2("descriptors.yml", FileStorage::WRITE);
/** @function main */
int siftMatch( const char* template1, const char* tag, fstream& file )
{
  cout<<" image read start: ";
  Mat img_object = imread( template1, CV_LOAD_IMAGE_GRAYSCALE );
  if( !img_object.data )
  { std::cout<< " --(!) Error reading images " << std::endl; return -1; }

  //-- Step 1: Detect the keypoints using SURF Detector
  int minHessian = 400;

  SiftFeatureDetector detector( minHessian );

  std::vector<KeyPoint> keypoints_object, keypoints_scene;
  
  detector.detect( img_object, keypoints_object );
  string keypointFile = string("/Users/amitagra/ImageMatching/trained/keypoints/")+tag;
  FileStorage storage1(keypointFile, FileStorage::WRITE);
  write(storage1, "tag", keypoints_object);
  storage1.release();

  //-- Step 2: Calculate descriptors (feature vectors)
  SiftDescriptorExtractor extractor;

  Mat descriptors_object, descriptors_scene;
  extractor.compute( img_object, keypoints_object, descriptors_object );
 
  //Change by amit
  string descriptorFile = string("/Users/amitagra/ImageMatching/trained/descriptors/")+tag;
  FileStorage storage2(descriptorFile, FileStorage::WRITE);            // load it again
  write(storage2, "tag", descriptors_object);
  storage2.release();

  return 0;


  //change by amit 
  //FileStorage storage("keypoints.yml", FileStorage::READ);
  //read(storage["img1"],keypoints_object);
  //storage.release();
  

  //extractor.compute( img_object, keypoints_object, descriptors_object );
  
  //Change by amit
  //FileStorage storage1("descriptor.yml", FileStorage::READ);            // load it again
  //storage1["img1"] >> descriptors_object;
  //storage1.release();  

  //FileStorage storage2("keypoints1.yml", FileStorage::WRITE);
  //write(storage2, "img1",descriptors_object);
 // storage2.release();
  
}

int readData(std::string templatePath){

    DIR* FD;
    struct dirent* in_file;
    
    fstream file("result.txt",ios::app);
      if (NULL == (FD = opendir (templatePath.c_str())))
      {
        fprintf(stderr, "Error : Failed to open input directory\n");
        file.close();
        return 1;
      }
      while ((in_file = readdir(FD)))
      {
           if (!strcmp (in_file->d_name, "."))
                continue;
            if (!strcmp (in_file->d_name, ".."))
                continue;
            if (!strcmp (in_file->d_name, ".DS_Store"))
                continue;
            string templateFile = templatePath+"/"+in_file->d_name;
            istringstream iss(in_file->d_name);
            string sub;
            iss >> sub;
            cout << sub <<endl;
            int result = siftMatch(templateFile.c_str(),in_file->d_name, file);
      }
  file.close();
  return 0;
}

int main(int argc, char** argv){
//    FileStorage storage1 = new FileStorage("keypoints.yml", FileStorage::WRITE);
  //  FileStorage storage2 = new FileStorage("descriptors.yml", FileStorage::WRITE);
    readData(argv[1]);
//    storage1.release();
//    storage2.release();
    return 0;
}
