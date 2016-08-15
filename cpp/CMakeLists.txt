cmake_minimum_required(VERSION 3.2)

set(PROTO_PATH "" CACHE PATH "Directory containing CMakeLists.txt for protobuf definitions.")

# https://github.com/grpc/homebrew-grpc
# https://github.com/grpc/grpc/blob/release-0_14/INSTALL.md
include(findGRPC/FindGRPC.cmake)

include(FindProtobuf)
find_package(Protobuf REQUIRED)

# gRPC code requires c++11 (also c++11 is great)
# On OSX, this requires cmake 3.2+
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

add_subdirectory(${PROTO_PATH} "${CMAKE_CURRENT_BINARY_DIR}/proto")
include_directories("${CMAKE_CURRENT_BINARY_DIR}/proto")
include_directories(${Protobuf_INCLUDE_DIRS})

add_executable(runner main.cpp ${SOURCE})
target_link_libraries(runner nativeService protobuf grpc++)
