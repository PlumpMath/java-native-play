#include <iostream>
#include <fstream>
#include <vector>
#include <string>

#include "face/detect.hpp"

using std::cout;
using std::cerr;
using std::endl;
using std::string;
using std::ifstream;
using std::vector;

int main(int argc, char* argv[]) {
    // Quick sanity check on args list
    if (argc != 2) {
        cerr << "Invalid arguments list!" << endl;
        cout << "Usage:" << endl;
        cout << "\tdetector [img_file]" << endl;
        return 1;
    }
    // Open the file and proceed if successful
    string img_file = argv[1];
    ifstream img_file_stream(img_file);
    if (img_file_stream.is_open()) {
        // Might be a cleaner/faster way to do this, but this is negligibly small
        string byteString(std::istreambuf_iterator<char>(img_file_stream), {});
        auto bytes = vector<uint8_t>(byteString.begin(), byteString.end());
        int faceCount = fc::countFaces(bytes);
        cout << faceCount << endl;
        return 0;
    } else {
        cerr << "Unable to open file " << img_file << endl;
        return 1;
    }
}
