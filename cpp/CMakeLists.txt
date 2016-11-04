cmake_minimum_required(VERSION 3.2)

# Set up CMake scaffolding for test running -- generates `make test' target
# Must come before add_subdirectory calls so that add_test calls therein work
enable_testing()

set(PROTO_PATH "" CACHE PATH "Directory containing CMakeLists.txt for protobuf definitions.")
set(DLIB_PATH "" CACHE PATH "Directory containing dlib's 'cmake' file.")
set(GTEST_PATH "" CACHE PATH "Directory containing CMakeLists.txt for googletest.")

# https://github.com/grpc/homebrew-grpc
# https://github.com/grpc/grpc/blob/release-0_14/INSTALL.md
include(findGRPC/FindGRPC.cmake)

include(FindProtobuf)
find_package(Protobuf REQUIRED)

include(${DLIB_PATH}/cmake)
find_package(OpenCV REQUIRED)

# gRPC code requires c++11 (also c++11 is great)
# On OSX, this requires cmake 3.2+
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

add_subdirectory(${PROTO_PATH} "${CMAKE_CURRENT_BINARY_DIR}/proto")
add_subdirectory(${DLIB_PATH} "${CMAKE_CURRENT_BINARY_DIR}/dlib")
add_subdirectory(face-processing "${CMAKE_CURRENT_BINARY_DIR}/face-processing")
add_subdirectory(${GTEST_PATH} "${CMAKE_CURRENT_BINARY_DIR}/googletest")
include_directories(${CMAKE_CURRENT_BINARY_DIR}/proto)
include_directories(${Protobuf_INCLUDE_DIRS})
include_directories(${OpenCV_INCLUDE_DIRS})
include_directories(face-processing/include)

add_executable(service src/service.cpp ${SOURCE})
target_link_libraries(service face-proc nativeService protobuf grpc++ dlib ${OpenCV_LIBS})

add_executable(detector src/detector.cpp ${SOURCE})
target_link_libraries(detector face-proc dlib ${OpenCV_LIBS})
