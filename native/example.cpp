#include<iostream>
#include "example.hpp"

using namespace std;

// Must extern the function to avoid symbol mangling
 extern "C" void test(char str[]) {
    cout << str << endl;
}
