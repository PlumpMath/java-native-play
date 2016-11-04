#include <fstream>

#include "gtest/gtest.h"
#include "face/detect.hpp"
#include "test_vars.h"

using std::string;
using std::ifstream;
using std::vector;

TEST(detect, one) {
    // Open the test file and proceed if successful
    // Test image from http://www.cs.cmu.edu/~chuck/lennapg/lenna.shtml
    string img_file = string(DATA_PATH) + "/len_std.jpg";
    ifstream img_file_stream(img_file);
    if (img_file_stream.is_open()) {
        // Might be a cleaner/faster way to do this, but this is negligibly small
        string byteString(std::istreambuf_iterator<char>(img_file_stream), {});
        auto bytes = vector<uint8_t>(byteString.begin(), byteString.end());
        int faceCount = fc::countFaces(bytes);
        EXPECT_EQ(1, faceCount);
    } else {
        FAIL() << "Unable to open file " << img_file;
    }
}
