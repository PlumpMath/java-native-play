cmake_minimum_required(VERSION 3.2)

include_directories(${gtest_SOURCE_DIR}/include)
include_directories(${gtest_SOURCE_DIR})

# Make test data files available to test binaries via configured header
configure_file(test_vars.h.in test_vars.h)
include_directories(${CMAKE_CURRENT_BINARY_DIR})

add_executable(detectFaceTest detectFaceTest.cpp)

# OpenCV has a lib opencv_ts which exposes identical symbols as googletest; this
# causes 'duplicate symbol' errors at the linking step if we link ${OpenCV_LIBS}
# rather than the specific opencv libs we need.
# http://code.opencv.org/issues/1608
target_link_libraries(detectFaceTest gtest_main face-proc dlib opencv_core opencv_highgui)

add_test(DetectFaceTest detectFaceTest)
