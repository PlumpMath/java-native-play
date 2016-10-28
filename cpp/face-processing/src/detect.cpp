#include <dlib/image_processing/frontal_face_detector.h>
#include "dlib/opencv/cv_image.h"

#include <opencv2/opencv.hpp>
#include <opencv2/core/mat.hpp>

#include <algorithm>

#include "face/detect.hpp"

using std::vector;
using std::uint8_t;
using std::min;
using std::max;

int fc::countFaces(const vector<uint8_t>& bytes) {
        auto img = cv::imdecode(bytes, CV_LOAD_IMAGE_GRAYSCALE);
        dlib::cv_image<unsigned char> dlibImg(img);
        dlib::array2d<unsigned char> tempImage;
        dlib::assign_image(tempImage, dlibImg);

        //scale the image up until its smallest side is greater than 500 pixels,
        //but just in case we are fed some funky images, stop scaling up if the
        //largest side is bigger that 7500 pixels
        //For future reference, the larger the image is, the better the face detector works
        //(precision and recall both increase slightly) but the time it takes to run is directly
        //proportional to the total number of pixels in an image
        while(min(tempImage.nc(),tempImage.nr())<500 && max(tempImage.nc(),tempImage.nr()) < 7500){
            //Pyramid_up effectively doubles the width and height of the image
            dlib::pyramid_up(tempImage);
        }

        dlib::frontal_face_detector detector = dlib::get_frontal_face_detector();
        std::vector<dlib::rectangle> dets = detector(tempImage);
        return dets.size();
}
