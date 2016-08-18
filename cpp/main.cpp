#include <iostream>
#include <vector>
#include <algorithm>
#include <string>

#include <grpc++/grpc++.h>

#include <dlib/image_processing/frontal_face_detector.h>
#include "dlib/opencv/cv_image.h"

#include <opencv2/opencv.hpp>
#include <opencv2/core/mat.hpp>

#include "native-service.pb.h"
#include "native-service.grpc.pb.h"

using std::cout;
using std::endl;
using std::unique_ptr;
using std::vector;
using std::min;
using std::max;

using grpc::Server;
using grpc::ServerBuilder;
using grpc::ServerContext;
using grpc::Status;

using service::Printer;
using service::PrintRequest;
using service::PrintReply;

cv::Mat cvread(const char* bytes, int len) {
    return cv::imdecode(vector<char>(bytes, bytes + len), CV_LOAD_IMAGE_GRAYSCALE);
}

class PrinterService final : public Printer::Service {
    Status PrintMsg(ServerContext* context, const PrintRequest* req, PrintReply* resp) override {
        cout << req->msg() << endl;
        //resp->set_msg("!");
        resp->set_payload("neat");
        auto img = cvread(req->payload().c_str(), req->payload().size());
        dlib::cv_image<unsigned char> dlibImg(img);
        dlib::array2d<unsigned char> tempImage;
        dlib::assign_image(tempImage, dlibImg);

        //scale the image up until its smallest side is greater than 1000 pixels,
        //but just in case we are fed some funky images, stop scaling up if the
        //largest side is bigger that 7500 pixels
        //For future reference, the larger the image is, the better the face detector works
        //(precision and recall both increase slightly) but the time it takes to run is directly
        //proportional to the total number of pixels in an image
        while(min(tempImage.nc(),tempImage.nr())<1000 && max(tempImage.nc(),tempImage.nr()) < 7500){
            //Pyramid_up effectively doubles the width and height of the image
            dlib::pyramid_up(tempImage);
        }

        dlib::frontal_face_detector detector = dlib::get_frontal_face_detector();
        std::vector<dlib::rectangle> dets = detector(tempImage);
        resp->set_msg(std::to_string(dets.size()));
        return Status::OK;
    }
};

int main(int argc, char** argv) {

    PrinterService service;

    ServerBuilder builder;
    builder.AddListeningPort("0.0.0.0:50051", grpc::InsecureServerCredentials());
    builder.RegisterService(&service);

    unique_ptr<Server> server(builder.BuildAndStart());
    cout << "Server up on " << "0.0.0.0:50051" << endl;

    server->Wait();

    return 0;
}
