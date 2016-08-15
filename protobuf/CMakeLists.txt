cmake_minimum_required(VERSION 3.2)

# brew install --devel protobuf

# gRPC code requires c++11 (also c++11 is great)
# On OSX, this requires cmake 3.2+
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

include(FindProtobuf)
#include(FindGRPC.cmake)

find_package(Protobuf REQUIRED)

include_directories(${Protobuf_INCLUDE_DIRS})

set(PROTO_FILES src/main/proto/native-service.proto)

protobuf_generate_cpp(PROTO_SRCS PROTO_HDRS ${PROTO_FILES})
protobuf_generate_grpc_cpp(GRPC_SRCS GRPC_HDRS ${PROTO_FILES})

add_library(nativeService ${PROTO_SRCS} ${PROTO_HDRS} ${GRPC_SRCS} ${GRPC_HDRS})
target_link_libraries(nativeService grpc++ grpc gpr grpc_cronet grpc_unsecure grpc++_unsecure grpc++_reflection)

# propagate relevant variables to the parent scope(s), if any exist
get_directory_property(hasParent PARENT_DIRECTORY)
if(hasParent)
    set(PROTOBUF_INCLUDE_DIRS ${PROTOBUF_INCLUDE_DIRS} PARENT_SCOPE)
    set(PROTOBUF_LIBRARIES ${PROTOBUF_LIBRARIES} PARENT_SCOPE)
    set(PROTO_SRCS ${PROTO_SRCS} PARENT_SCOPE)
    set(PROTO_HDRS ${PROTO_HDRS} PARENT_SCOPE)
    set(GRPC_SRCS ${GRPC_SRCS} PARENT_SCOPE)
    set(GRPC_HDRS ${GRPC_HDRS} PARENT_SCOPE)
    set(PROTO_INC_DIRS ${Project_BINARY_DIR} PARENT_SCOPE)
else()
endif()
