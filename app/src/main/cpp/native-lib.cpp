#include <jni.h>

extern "C"
JNIEXPORT jfloat JNICALL
Java_com_example_app_ConvertActivity_addTwoNumbers(JNIEnv *env, jobject thiz, jint usd) {
    return usd * 3.28;
}