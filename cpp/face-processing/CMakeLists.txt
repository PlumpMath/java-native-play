cmake_minimum_required(VERSION 3.2)

include(${DLIB_PATH}/cmake)

find_package(OpenCV REQUIRED)

add_subdirectory(${DLIB_PATH} "${CMAKE_CURRENT_BINARY_DIR}/dlib")

include_directories(include)

add_library(face-proc src/detect.cpp)
