#include <iostream>

#include <grpc++/grpc++.h>

#include <dlib/image_processing/frontal_face_detector.h>

#include "native-service.pb.h"
#include "native-service.grpc.pb.h"

using std::cout;
using std::endl;
using std::unique_ptr;
using grpc::Server;
using grpc::ServerBuilder;
using grpc::ServerContext;
using grpc::Status;
using service::Printer;
using service::PrintRequest;
using service::PrintReply;

class PrinterService final : public Printer::Service {
    Status PrintMsg(ServerContext* context, const PrintRequest* req, PrintReply* resp) override {
        cout << req->msg() << " : " << req->payload() << endl;
        resp->set_msg("!");
        resp->set_payload("neat");
        dlib::frontal_face_detector detector = dlib::get_frontal_face_detector();
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
