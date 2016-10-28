#include <iostream>
#include <vector>
#include <string>

#include <grpc++/grpc++.h>

#include "native-service.pb.h"
#include "native-service.grpc.pb.h"

#include "face/detect.hpp"

using std::cout;
using std::endl;
using std::unique_ptr;
using std::vector;
using std::min;
using std::max;
using std::uint8_t; // essentially 'byte' type

using grpc::Server;
using grpc::ServerBuilder;
using grpc::ServerContext;
using grpc::Status;

using service::Printer;
using service::PrintRequest;
using service::PrintReply;

class PrinterService final : public Printer::Service {
    Status PrintMsg(ServerContext* context, const PrintRequest* req, PrintReply* resp) override {
        cout << req->msg() << endl;
        //resp->set_msg("!");
        resp->set_payload("neat");
        // Convert types so we get a vector, which is convertable to cv::InputArray
        // http://docs.opencv.org/2.4/modules/core/doc/basic_structures.html?highlight=inputarray#InputArray
        auto bytes = vector<uint8_t>(req->payload().begin(), req->payload().end());
        int faceCount = fc::countFaces(bytes);
        resp->set_msg(std::to_string(faceCount));
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
