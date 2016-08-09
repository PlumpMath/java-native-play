#include "example.hpp"

int main(int argc, char** argv) {
    for (int i = 0; i < argc; i++) {
        test(argv[i]);
    }
    return 0;
}
