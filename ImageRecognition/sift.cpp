#include <iomanip>
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
void readme();

void ransacTest(const std::vector<cv::DMatch> matches,const std::vector<cv::KeyPoint>&keypoints1,const std::vector<cv::KeyPoint>& keypoints2,std::vector<cv::DMatch>& goodMatches,double distance,double confidence,double minInlierRatio)
{
    goodMatches.clear();
    // Convert keypoints into Point2f
    std::vector<cv::Point2f> points1, points2;
    for (std::vector<cv::DMatch>::const_iterator it= matches.begin();it!= matches.end(); ++it)
    {
        // Get the position of left keypoints
        float x= keypoints1[it->queryIdx].pt.x;
        float y= keypoints1[it->queryIdx].pt.y;
        points1.push_back(cv::Point2f(x,y));
        // Get the position of right keypoints
        x= keypoints2[it->trainIdx].pt.x;
        y= keypoints2[it->trainIdx].pt.y;
        points2.push_back(cv::Point2f(x,y));
    }
    // Compute F matrix using RANSAC
    std::vector<uchar> inliers(points1.size(),0);
    cv::Mat fundemental= cv::findFundamentalMat(cv::Mat(points1),cv::Mat(points2),inliers,CV_FM_RANSAC,distance,confidence); // confidence probability
    // extract the surviving (inliers) matches
    std::vector<uchar>::const_iterator
    itIn= inliers.begin();
    std::vector<cv::DMatch>::const_iterator
    itM= matches.begin();
    // for all matches
    for ( ;itIn!= inliers.end(); ++itIn, ++itM)
    {
        if (*itIn)
        { // it is a valid match
            goodMatches.push_back(*itM);
        }
    }
}

/** @function main */
int siftMatch( const char* template1, const char* source1, fstream& file, std::vector<KeyPoint> keypoints_scene, Mat descriptors_scene )
{
  //cout<<"starting with sift match..."<<endl;
  //-- Step 1: Detect the keypoints using SURF Detector
  int minHessian = 400;
  SiftFeatureDetector detector( minHessian );
  std::vector<KeyPoint> keypoints_object ;
  string keypointFile = string("/Users/amitagra/ImageMatching/trained/keypoints/")+template1;
  FileStorage storage(keypointFile, FileStorage::READ);
  read(storage["tag"],keypoints_object);
  storage.release();
  //cout<<"get keypoints complete..."<<endl;

  //-- Step 2: Calculate descriptors (feature vectors)
  SiftDescriptorExtractor extractor;
  Mat descriptors_object;
  string descriptorFile = string("/Users/amitagra/ImageMatching/trained/descriptors/")+template1;
  FileStorage storage1(descriptorFile, FileStorage::READ);            // load it again
  storage1["tag"] >> descriptors_object;
  storage1.release();
  //cout<<"get descriptors complete..."<<endl;

  //-- Step 3: Matching descriptor vectors using FLANN matcher
  FlannBasedMatcher matcher;
  std::vector< DMatch > matches;
  matcher.match( descriptors_object, descriptors_scene, matches );
  //cout<<"Match complete..."<<endl;
  double max_dist = 0; double min_dist = 300;

  //-- Quick calculation of max and min distances between keypoints
  for( int i = 0; i < descriptors_object.rows; i++ )
  { double dist = matches[i].distance;
    if( dist < min_dist ) min_dist = dist;
    if( dist > max_dist ) max_dist = dist;
  }

  //-- Draw only "good" matches (i.e. whose distance is less than 3*min_dist )
  std::vector< DMatch > good_matches;
  ransacTest(matches,keypoints_object,keypoints_scene,good_matches,1,0.99,0.8);
  cout<<setw(40)<<template1<<" | "<<setw(8)<<min_dist<<" | "<<setw(8)<< max_dist << " | "<<setw(8)<< keypoints_object.size() <<" | "<<setw(8)<< matches.size() <<" | "<<setw(8)<<good_matches.size()<<endl;
  return min_dist;
}

/** @function readme */
void readme()
{ std::cout << " Usage: ./SURF_descriptor <img1> <img2>" << std::endl; }

int is_regular_file(const char *path)
{
    struct stat path_stat;
    stat(path, &path_stat);
    return S_ISREG(path_stat.st_mode);
}

int readData(std::string templatePath, std::string sourcePath){

    DIR* FD;
    struct dirent* in_file;
    /* Scanning the in directory */
    int isfile = is_regular_file(sourcePath.c_str());
    
    fstream file("result.txt",ios::app);
    if(isfile)
    {
      if (NULL == (FD = opendir (templatePath.c_str())))
      {
        fprintf(stderr, "Error : Failed to open input directory\n");
        file.close();
        return 1;
      }
      
      Mat img_scene = imread( sourcePath.c_str(), CV_LOAD_IMAGE_GRAYSCALE );
      cout<<"Image read complete, start with matching...";
      int minHessian = 400;

      SiftFeatureDetector detector( minHessian );
      std::vector<KeyPoint>  keypoints_scene;

      detector.detect( img_scene, keypoints_scene );
      cout<<"feature detection for image completed...";
      SiftDescriptorExtractor extractor;
      Mat descriptors_scene;
 
      extractor.compute( img_scene, keypoints_scene, descriptors_scene );
      cout<<"computing extractor for image completed...";
      int adobeAvg, adobeCount, walmartAvg, walmartCount, starbucksAvg, starbucksCount, amazonAvg, amazonCount, appleAvg, appleCount;
      adobeAvg=0; adobeCount=0; walmartAvg=0; walmartCount=0; starbucksAvg=0; starbucksCount=0; amazonAvg=0; amazonCount=0; appleAvg=0; appleCount=0;
      int minimum = 100, min_match = 0;
      while ((in_file = readdir(FD)))
      {
           if (!strcmp (in_file->d_name, "."))
                continue;
            if (!strcmp (in_file->d_name, ".."))
                continue;
            if (!strcmp (in_file->d_name, ".DS_Store"))
                continue;
            string templateFile = templatePath+"/"+in_file->d_name;
            string sourceFile = sourcePath;
            int result = siftMatch(in_file->d_name, sourceFile.c_str(), file, keypoints_scene, descriptors_scene);
            
            istringstream iss(in_file->d_name);
            string sub;
            iss >> sub;
            if( result < 90)
            {
              if(sub == "Walmart")
               {    walmartAvg += result; walmartCount++;  if(result<minimum){minimum=result;min_match=2;}}
              if(sub == "AdobeReader")
               {    adobeAvg += result; adobeCount++; if(result<minimum){minimum=result;min_match=1;}}
              if(sub == "StarbucksCoffee")
               {    starbucksAvg += result; starbucksCount++; if(result<minimum){minimum=result;min_match=3;}}
              if(sub == "amazon")
               {    amazonAvg += result; amazonCount++; if(result<minimum){minimum=result;min_match=4;}}
              if(sub == "Apple")
               {    appleAvg += result; appleCount++;  if(result<minimum){minimum=result;min_match=5;}}
            }
      }
      
      int match_return = -1;
      int match_count = 0;
      string match_brand = "No Match";
      if(adobeCount > 0) 
      { adobeAvg = adobeAvg/adobeCount; }
      if(walmartCount > 0)
      { walmartAvg = walmartAvg/walmartCount; }
      if(starbucksCount > 0)
      { starbucksAvg = starbucksAvg/starbucksCount; }
      if(amazonCount > 0)
      { amazonAvg = amazonAvg/amazonCount; }
      if(appleCount > 0)
      { appleAvg = appleAvg/appleCount; }
      int minAvg = 100;
      //if( adobeAvg < minAvg && adobeAvg > 0 ) { minAvg = adobeAvg;  match_return = 1; match_brand  = "Adobe Reader"; match_count = adobeCount;}
      if( walmartAvg < minAvg && walmartAvg > 0 ) { minAvg = walmartAvg; match_return = 2; match_brand = "Walmart"; match_count = walmartCount;}
      if( starbucksAvg < minAvg && starbucksAvg > 0 ) { minAvg = starbucksAvg; match_return = 3; match_brand = "Startbucks Coffee"; match_count = starbucksCount;}
      //if( amazonAvg < minAvg && amazonAvg > 0 ) { minAvg = amazonAvg; match_return = 4; match_brand = "Amazon"; match_count = amazonCount;}
      if( minAvg < 70 ) 
      {
          cout<<endl<<"[adobeAvg: "<<adobeAvg <<"][walmartAvg: "<<walmartAvg<<"][starbucksAvg: "<<starbucksAvg<<"][amazonAvg: "<<amazonAvg<<"][appleAvg: "<<appleAvg<<"]"<<endl; 
          cout<<endl<<"Your Match: "<<match_brand<<endl;
          cout<<endl<<"Minimum Dist : "<< minimum << "   Matched: "<< min_match<<endl;
          if(minimum <= 30 ) {
              cout<<"****Minimum distance taking priority****"<<endl;
              return min_match;
          }
          file.close();
          if(match_count > 1){
              return match_return;
          }
          cout << "****Match count not enough, not matched taking priority****"<<endl;
          return 0;
      }
      else 
      {
          cout<<endl<<"[adobeAvg: "<<adobeAvg <<"][walmartAvg: "<<walmartAvg<<"][starbucksAvg: "<<starbucksAvg<<"][amazonAvg: "<<amazonAvg<<"][appleAvg: "<<appleAvg<<"]"<<endl; 
          cout<<endl<<"Your Match: "<<"not matched"<<endl;
          cout<<endl<<"Minimum Dist : "<< minimum << "   Matched: "<< min_match<<endl;
          if(minimum <= 30 ) {
              cout<<"Minimum distance taking priority"<<endl;
              return min_match;
          }
          file.close();
          return -1;
      } 
    }
    return 0; 
}

int main(int argc, char** argv){
  if( argc != 3 )
  { readme(); return -1; }
    return readData(argv[1], argv[2]);
}
